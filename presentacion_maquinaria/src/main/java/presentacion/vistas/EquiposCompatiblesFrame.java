package presentacion.vistas;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import objetosdto.EquipoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.SolicitudRentaDTO;
import presentacion.control.IContratacionRenta;

/**
 *
 * @author alejandromontijo
 */
public class EquiposCompatiblesFrame extends javax.swing.JFrame {

    private static final int CUMPLE = 1;
    private static final int RECOMENDADO = 3;
    private static final int ALTERNATIVA = 4;
    private static final String SI = "Sí";
    private static final Color VERDE = new Color(0, 130, 0);
    private static final Color ROJO = new Color(170, 20, 20);

    private IContratacionRenta contratacionRenta;
    // los equipos en el orden de los renglones de la tabla
    private final List<EquipoDTO> equipos = new ArrayList<>();
    // Solo se pueden marcar los que cumplen, uno por columna, y un equipo no puede ser las dos cosas
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Equipo", "¿Cumple?", "Por qué no cumple", "Recomendado", "Alternativa"}, 0) {
        @Override
        public Class<?> getColumnClass(int columna) {
            return columna >= RECOMENDADO ? Boolean.class : Object.class;
        }

        @Override
        public boolean isCellEditable(int fila, int columna) {
            return columna >= RECOMENDADO && SI.equals(getValueAt(fila, CUMPLE));
        }

        @Override
        public void setValueAt(Object valor, int fila, int columna) {
            super.setValueAt(valor, fila, columna);
            if (Boolean.TRUE.equals(valor)) {
                for (int otra = 0; otra < getRowCount(); otra++) {
                    if (otra != fila) {
                        super.setValueAt(false, otra, columna);
                    }
                }
                super.setValueAt(false, fila, columna == RECOMENDADO ? ALTERNATIVA : RECOMENDADO);
            }
        }
    };

    public EquiposCompatiblesFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        initComponents();
        tableEquipos.setModel(modelo);
        Tablas.preparar(tableEquipos, 180, 84, 374, 120, 120);
        Tablas.casillas(tableEquipos, RECOMENDADO, ALTERNATIVA);
        tableEquipos.getColumnModel().getColumn(CUMPLE).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object valor) {
                setText(String.valueOf(valor));
                setFont(getFont().deriveFont(Font.BOLD));
                setForeground(SI.equals(valor) ? VERDE : ROJO);
            }
        });
        Ventanas.cerrarAlMenu(this, contratacionRenta);
        this.setLocationRelativeTo(null);
    }

    // El coordinador la llama cada vez que se abre la pantalla con los equipos que cumplen y los que pidió
    // el cliente (para cada uno, por qué no cumple o null si cumple). Si ya se habían elegido el recomendado
    // y la alternativa y siguen cumpliendo, los vuelve a marcar.
    public void cargarEquipos(SolicitudRentaDTO solicitud, Map<EquipoDTO, String> evaluados) {
        lblFolio.setText("Folio: " + Formato.texto(solicitud.getFolio()));
        lblRequerido.setText("<html>Lo requerido: " + Formato.requerimientos(solicitud) + "</html>");
        lblEquipoPedido.setText("El cliente pidió: " + Formato.equiposPedidos(solicitud));
        EvaluacionTecnicaDTO evaluacion = Formato.evaluacion(solicitud);
        EquipoDTO recomendado = evaluacion == null ? null : evaluacion.getRecomendado();
        EquipoDTO alternativa = evaluacion == null ? null : evaluacion.getAlternativo();
        equipos.clear();
        modelo.setRowCount(0);
        for (Map.Entry<EquipoDTO, String> evaluado : evaluados.entrySet()) {
            EquipoDTO equipo = evaluado.getKey();
            String motivo = evaluado.getValue();
            boolean cumple = motivo == null;
            equipos.add(equipo);
            modelo.addRow(new Object[]{Formato.texto(equipo.getModelo()), cumple ? SI : "No", cumple ? "" : Formato.conMayuscula(motivo),
                cumple && esElMismo(equipo, recomendado), cumple && esElMismo(equipo, alternativa)});
        }
    }

    private boolean esElMismo(EquipoDTO equipo, EquipoDTO elegido) {
        return elegido != null && Objects.equals(equipo.getIdEquipo(), elegido.getIdEquipo());
    }

    private EquipoDTO marcado(int columna) {
        for (int fila = 0; fila < equipos.size(); fila++) {
            if (Boolean.TRUE.equals(modelo.getValueAt(fila, columna))) {
                return equipos.get(fila);
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFolio = new javax.swing.JLabel();
        lblEquipoPedido = new javax.swing.JLabel();
        lblRequerido = new javax.swing.JLabel();
        lblEquipos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEquipos = new javax.swing.JTable();
        lblNota = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnContinuarAccesorios = new javax.swing.JButton();
        btnNingunoCumple = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Equipos Compatibles");
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(217, 217, 217));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Equipos Compatibles");

        lblFolio.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        lblFolio.setForeground(new java.awt.Color(0, 0, 0));
        lblFolio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFolio.setText("Folio: FOL-89241");

        lblEquipoPedido.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        lblEquipoPedido.setForeground(new java.awt.Color(0, 0, 0));
        lblEquipoPedido.setText("El cliente pidió: Retroexcavadora JCB");

        lblRequerido.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        lblRequerido.setForeground(new java.awt.Color(0, 0, 0));
        lblRequerido.setText("<html>Lo requerido: Profundidad 3.50 m · Potencia 120 HP mín. · Peso 22 t máx. · Ancho 4.20 m máx. · Terreno: Terracería compactada</html>");
        lblRequerido.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblEquipos.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        lblEquipos.setForeground(new java.awt.Color(0, 0, 0));
        lblEquipos.setText("EQUIPOS QUE CUMPLEN Y LOS QUE PIDIÓ EL CLIENTE");

        tableEquipos.setBackground(new java.awt.Color(255, 255, 255));
        tableEquipos.setForeground(new java.awt.Color(0, 0, 0));
        tableEquipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipo", "¿Cumple?", "Por qué no cumple", "Recomendado", "Alternativa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEquipos);

        lblNota.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNota.setForeground(new java.awt.Color(70, 70, 70));
        lblNota.setText("<html>Marca el equipo recomendado y, si hay, la alternativa. Que un equipo cumpla no significa que esté disponible: las unidades se revisan después.</html>");
        lblNota.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnRegresar.setBackground(new java.awt.Color(236, 34, 31));
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setOpaque(true);
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnContinuarAccesorios.setBackground(new java.awt.Color(44, 44, 44));
        btnContinuarAccesorios.setBorderPainted(false);
        btnContinuarAccesorios.setFocusPainted(false);
        btnContinuarAccesorios.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnContinuarAccesorios.setForeground(new java.awt.Color(255, 255, 255));
        btnContinuarAccesorios.setOpaque(true);
        btnContinuarAccesorios.setText("Continuar a accesorios");
        btnContinuarAccesorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarAccesoriosActionPerformed(evt);
            }
        });

        btnNingunoCumple.setBackground(new java.awt.Color(255, 255, 255));
        btnNingunoCumple.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(44, 44, 44)));
        btnNingunoCumple.setFocusPainted(false);
        btnNingunoCumple.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnNingunoCumple.setForeground(new java.awt.Color(44, 44, 44));
        btnNingunoCumple.setOpaque(true);
        btnNingunoCumple.setText("Ninguno cumple");
        btnNingunoCumple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNingunoCumpleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 330, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(640, 640, 640)
                .addComponent(lblFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblEquipoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblRequerido, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblEquipos, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblNota, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 800, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(712, 712, 712)
                .addComponent(btnContinuarAccesorios, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(534, 534, 534)
                .addComponent(btnNingunoCumple, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 264, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 626, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 630, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(lblEquipoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 590, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(lblRequerido, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 544, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(lblEquipos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 512, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 378, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(lblNota, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 324, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnContinuarAccesorios, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnNingunoCumple, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContinuarAccesoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarAccesoriosActionPerformed
        // lo que marcó el asesor; el control revisa la selección y arma la evaluación técnica
        EvaluacionTecnicaDTO seleccion = new EvaluacionTecnicaDTO();
        seleccion.setRecomendado(marcado(RECOMENDADO));
        seleccion.setAlternativo(marcado(ALTERNATIVA));
        contratacionRenta.pasarDatosSeleccionAccesorios(seleccion);
    }//GEN-LAST:event_btnContinuarAccesoriosActionPerformed

    private void btnNingunoCumpleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNingunoCumpleActionPerformed
        contratacionRenta.ningunEquipoCumple();
    }//GEN-LAST:event_btnNingunoCumpleActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        contratacionRenta.abrirAnalisisRequerimiento();
    }//GEN-LAST:event_btnRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinuarAccesorios;
    private javax.swing.JButton btnNingunoCumple;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEquipoPedido;
    private javax.swing.JLabel lblEquipos;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblRequerido;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable tableEquipos;
    // End of variables declaration//GEN-END:variables
}
