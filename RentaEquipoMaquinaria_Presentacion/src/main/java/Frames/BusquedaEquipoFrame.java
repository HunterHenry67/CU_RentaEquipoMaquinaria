/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import DTOs.EquipoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class BusquedaEquipoFrame extends javax.swing.JFrame {

    private IContratacionRenta contratacionRenta;
    // el catálogo completo y, en el mismo orden que la tabla, los equipos que se están mostrando
    private final List<EquipoDTO> catalogo = new ArrayList<>();
    private final List<EquipoDTO> equiposEnTabla = new ArrayList<>();

    public BusquedaEquipoFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        initComponents();
        this.setLocationRelativeTo(null);
    }

    // El coordinador la llama al abrir la pantalla con los equipos del catálogo (los mismos
    // que se comparan después en el análisis del requerimiento técnico)
    public void cargarEquiposEnTabla(List<EquipoDTO> listaEquipos) {
        catalogo.clear();
        catalogo.addAll(listaEquipos);
        txtFieldBusqueda.setText("");
        mostrarEquipos(catalogo);
    }

    private void mostrarEquipos(List<EquipoDTO> equipos) {
        DefaultTableModel modelo = (DefaultTableModel) tableEquipoCatalogo.getModel();
        modelo.setRowCount(0);
        equiposEnTabla.clear();
        for (DTOs.EquipoDTO e : equipos) {
            Object[] fila = {e.getModelo(), detalle(e)};
            modelo.addRow(fila);
            equiposEnTabla.add(e);
        }
    }

    // Lo que se sabe del equipo; lo que no está registrado no se muestra
    private String detalle(EquipoDTO e) {
        List<String> datos = new ArrayList<>();
        if (e.getPotencia() != null) {
            datos.add("Potencia: " + RequerimientosTecnicos.formatoNumero(e.getPotencia()) + " HP");
        }
        if (e.getCostoDiario() != null) {
            datos.add("Costo diario: " + String.format("$%,.0f", e.getCostoDiario()));
        }
        return String.join(" · ", datos);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFieldBusqueda = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEquipoCatalogo = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnRegistrarEquipo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Búsqueda Equipo");
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(217, 217, 217));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Búsqueda de Equipo");

        txtFieldBusqueda.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldBusqueda.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldBusqueda.setForeground(new java.awt.Color(0, 0, 0));

        btnBuscar.setBackground(new java.awt.Color(44, 44, 44));
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setOpaque(true);
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tableEquipoCatalogo.setBackground(new java.awt.Color(255, 255, 255));
        tableEquipoCatalogo.setForeground(new java.awt.Color(0, 0, 0));
        tableEquipoCatalogo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipo Catálogo ", "Detalle"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEquipoCatalogo);

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

        btnRegistrarEquipo.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrarEquipo.setBorderPainted(false);
        btnRegistrarEquipo.setFocusPainted(false);
        btnRegistrarEquipo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrarEquipo.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarEquipo.setOpaque(true);
        btnRegistrarEquipo.setText("Registrar Equipo");
        btnRegistrarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEquipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txtFieldBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 208, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(800, 800, 800)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 800, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(756, 756, 756)
                .addComponent(btnRegistrarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 622, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(txtFieldBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 566, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 566, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 110, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnRegistrarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String texto = txtFieldBusqueda.getText().trim().toLowerCase();
        List<EquipoDTO> encontrados = new ArrayList<>();
        for (EquipoDTO e : catalogo) {
            if (texto.isEmpty() || (e.getModelo() != null && e.getModelo().toLowerCase().contains(texto))) {
                encontrados.add(e);
            }
        }
        mostrarEquipos(encontrados);
        if (encontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún equipo con ese nombre.", "Búsqueda de Equipo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        contratacionRenta.abrirSolicitudRenta();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnRegistrarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEquipoActionPerformed
        int filaSeleccionada = tableEquipoCatalogo.getSelectedRow();
        if (filaSeleccionada != -1) {
            EquipoDTO equipo = equiposEnTabla.get(tableEquipoCatalogo.convertRowIndexToModel(filaSeleccionada));
            DTOs.EquipoSolicitadoDTO equipoSolicitado = new DTOs.EquipoSolicitadoDTO();
            equipoSolicitado.setDescripcionEquipo(equipo.getModelo());
            equipoSolicitado.setCantidad(1); 
            contratacionRenta.pasarDatosEquipoSolicitado(equipoSolicitado);
            contratacionRenta.abrirSolicitudRenta();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un equipo de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarEquipoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegistrarEquipo;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable tableEquipoCatalogo;
    private javax.swing.JTextField txtFieldBusqueda;
    // End of variables declaration//GEN-END:variables
}
