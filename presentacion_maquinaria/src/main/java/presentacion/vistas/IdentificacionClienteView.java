package presentacion.vistas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import presentacion.controles.IContratacionRenta;

/**
 *
 * @author Home
 */
public class IdentificacionClienteView extends javax.swing.JFrame {

    private IContratacionRenta contratacionRenta;  

    
    private List<ClienteDTO> clientesMostrados = new ArrayList<>();

    public IdentificacionClienteView(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        initComponents();
        Ventanas.cerrarCon(this, btnCancelar);
        this.setLocationRelativeTo(null);
    }

    
    public void cargarClientes(List<ClienteDTO> listaClientes) {
        txtFieldBusquedaCliente.setText("");
        cargarClientesEnTabla(listaClientes);
    }

    public void cargarClientesEnTabla(List<ClienteDTO> listaClientes) {
        clientesMostrados = new ArrayList<>(listaClientes);
        DefaultTableModel modelo = (DefaultTableModel) tableCliente.getModel();
        modelo.setRowCount(0);
        for (ClienteDTO c : listaClientes) {
            String tipo = (c instanceof ClienteEmpresaDTO) ? "Empresa" : "Particular";
            Object[] fila = {c.getRfc(), c.getNombre(), tipo, c.getTelefono()};
            modelo.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboFiltroBusquedaCliente = new javax.swing.JComboBox<>();
        txtFieldBusquedaCliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCliente = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnRegistrarNuevoCliente = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRegistrarNuevaSolicitud = new javax.swing.JButton();
        btnModificarDatos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Identificación Cliente");
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(217, 217, 217));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Identificación de Cliente");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Búsqueda de Cliente Por:");

        comboFiltroBusquedaCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        comboFiltroBusquedaCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RFC", "Nombre", "Teléfono" }));

        txtFieldBusquedaCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldBusquedaCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldBusquedaCliente.setForeground(new java.awt.Color(0, 0, 0));

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

        tableCliente.setBackground(new java.awt.Color(255, 255, 255));
        tableCliente.setForeground(new java.awt.Color(0, 0, 0));
        tableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RFC", "Nombre", "Tipo de Cliente", "Teléfono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableCliente);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("¿Es un cliente nuevo?");

        btnRegistrarNuevoCliente.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrarNuevoCliente.setBorderPainted(false);
        btnRegistrarNuevoCliente.setFocusPainted(false);
        btnRegistrarNuevoCliente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrarNuevoCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarNuevoCliente.setOpaque(true);
        btnRegistrarNuevoCliente.setText("Registrar Cliente Nuevo");
        btnRegistrarNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarNuevoClienteActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(236, 34, 31));
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setOpaque(true);
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnRegistrarNuevaSolicitud.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrarNuevaSolicitud.setBorderPainted(false);
        btnRegistrarNuevaSolicitud.setFocusPainted(false);
        btnRegistrarNuevaSolicitud.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrarNuevaSolicitud.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarNuevaSolicitud.setOpaque(true);
        btnRegistrarNuevaSolicitud.setText("Registrar Nueva Solicitud");
        btnRegistrarNuevaSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarNuevaSolicitudActionPerformed(evt);
            }
        });

        btnModificarDatos.setBackground(new java.awt.Color(44, 44, 44));
        btnModificarDatos.setBorderPainted(false);
        btnModificarDatos.setFocusPainted(false);
        btnModificarDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnModificarDatos.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarDatos.setOpaque(true);
        btnModificarDatos.setText("Modificar Datos");
        btnModificarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDatosActionPerformed(evt);
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
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 729, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(comboFiltroBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 740, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(txtFieldBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 204, Short.MAX_VALUE))
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
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 754, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(btnRegistrarNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 532, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 800, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(693, 693, 693)
                .addComponent(btnRegistrarNuevaSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(518, 518, 518)
                .addComponent(btnModificarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 283, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 622, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 580, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(comboFiltroBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 542, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(txtFieldBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 542, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 542, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 294, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(412, 412, 412)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 234, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(412, 412, 412)
                .addComponent(btnRegistrarNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 234, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnRegistrarNuevaSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnModificarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        // la búsqueda la hace el control en el almacén temporal; aquí solo se manda lo escrito
        contratacionRenta.buscarClientes(comboFiltroBusquedaCliente.getSelectedItem().toString(), txtFieldBusquedaCliente.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed




    private void btnRegistrarNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarNuevoClienteActionPerformed
        this.contratacionRenta.pasarDatosRegistroCliente(null);
    }//GEN-LAST:event_btnRegistrarNuevoClienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        contratacionRenta.abrirMenuPrincipal();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarNuevaSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarNuevaSolicitudActionPerformed
        int fila = tableCliente.getSelectedRow();
        contratacionRenta.pasarDatosSolicitudRenta(fila == -1 ? null : clientesMostrados.get(fila));
    }//GEN-LAST:event_btnRegistrarNuevaSolicitudActionPerformed

    private void btnModificarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatosActionPerformed
        int fila = tableCliente.getSelectedRow();
        contratacionRenta.modificarCliente(fila == -1 ? null : clientesMostrados.get(fila));
    }//GEN-LAST:event_btnModificarDatosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnModificarDatos;
    private javax.swing.JButton btnRegistrarNuevoCliente;
    private javax.swing.JButton btnRegistrarNuevaSolicitud;
    private javax.swing.JComboBox<String> comboFiltroBusquedaCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable tableCliente;
    private javax.swing.JTextField txtFieldBusquedaCliente;
    // End of variables declaration//GEN-END:variables
}
