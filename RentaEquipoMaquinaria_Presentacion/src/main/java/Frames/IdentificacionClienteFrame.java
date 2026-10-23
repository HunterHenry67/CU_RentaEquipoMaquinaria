/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import DTOs.ClienteDTO;
import DTOs.ClienteEmpresaDTO;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class IdentificacionClienteFrame extends javax.swing.JFrame {

    private IContratacionRenta contratacionRenta;
    // todos los clientes registrados y, en el mismo orden que la tabla, los que se están mostrando
    private final List<ClienteDTO> clientes = new ArrayList<>();
    private final List<ClienteDTO> clientesEnTabla = new ArrayList<>();

    public IdentificacionClienteFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        initComponents();
        this.setLocationRelativeTo(null);
        List<DTOs.ClienteDTO> listaEjemplo = new ArrayList<>();
        DTOs.ClienteDTO c1 = new DTOs.ClienteDTO();
        c1.setIdCliente(1L);
        c1.setNombre("Julián Martínez García");
        c1.setRfc("MARJ8503158A1");
        c1.setTelefono("6449876543");
        c1.setCorreo("julianmar12@gmail.com");
        c1.setTipoCliente("Particular");
        DTOs.ClienteEmpresaDTO c2 = new DTOs.ClienteEmpresaDTO();
        c2.setIdCliente(2L);
        c2.setNombre("Constructora del Yaqui S.A. de C.V.");
        c2.setRfc("CYA050212H84");
        c2.setTelefono("6441234567");
        c2.setCorreo("contacto@constructora.com");
        c2.setTipoCliente("Empresa");
        DTOs.ClienteDTO c3 = new DTOs.ClienteDTO();
        c3.setIdCliente(3L);
        c3.setNombre("María Fernanda López");
        c3.setRfc("LOMF920410H12");
        c3.setTelefono("6447654321");
        c3.setCorreo("mafe@correo.com");
        c3.setTipoCliente("Particular");
        listaEjemplo.add(c1);
        listaEjemplo.add(c2);
        listaEjemplo.add(c3);
        clientes.addAll(listaEjemplo);
        cargarClientesEnTabla(clientes);
    }

    public void cargarClientesEnTabla(java.util.List<DTOs.ClienteDTO> listaClientes) {
        DefaultTableModel modelo = (DefaultTableModel) tableCliente.getModel();
        modelo.setRowCount(0);
        List<ClienteDTO> mostrados = new ArrayList<>(listaClientes);
        clientesEnTabla.clear();
        for (DTOs.ClienteDTO c : mostrados) {
            String tipo = (c instanceof ClienteEmpresaDTO) ? "Empresa" : "Particular";
            Object[] fila = {c.getRfc(), c.getNombre(), tipo, c.getTelefono()};
            modelo.addRow(fila);
            clientesEnTabla.add(c);
        }
    }

    // Guarda el cliente que se registró o se modificó: si ya estaba (mismo id) se reemplaza
    // con sus datos nuevos y si es nuevo se agrega, para que aparezca en la siguiente búsqueda
    public void guardarCliente(ClienteDTO cliente) {
        if (cliente == null) {
            return;
        }
        if (cliente.getIdCliente() == null) {
            long siguiente = 1;
            for (ClienteDTO c : clientes) {
                if (c.getIdCliente() != null && c.getIdCliente() >= siguiente) {
                    siguiente = c.getIdCliente() + 1;
                }
            }
            cliente.setIdCliente(siguiente);
        }
        boolean yaEstaba = false;
        for (int i = 0; i < clientes.size(); i++) {
            if (Objects.equals(clientes.get(i).getIdCliente(), cliente.getIdCliente())) {
                clientes.set(i, cliente);
                yaEstaba = true;
            }
        }
        if (!yaEstaba) {
            clientes.add(cliente);
        }
        txtFieldBusqCliente.setText("");
        cargarClientesEnTabla(clientes);
    }

    // Al abrir la pantalla se quita la búsqueda anterior y se ven todos los clientes
    public void limpiarBusqueda() {
        txtFieldBusqCliente.setText("");
        cargarClientesEnTabla(clientes);
    }

    // El cliente con ese RFC, o null si no hay
    public ClienteDTO buscarPorRfc(String rfc) {
        for (ClienteDTO c : clientes) {
            if (c.getRfc() != null && c.getRfc().equalsIgnoreCase(rfc)) {
                return c;
            }
        }
        return null;
    }

    // Para buscar sin importar mayúsculas ni acentos ("julian" encuentra a "Julián")
    private String sinAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }

    private ClienteDTO clienteSeleccionado() {
        int fila = tableCliente.getSelectedRow();
        return fila == -1 ? null : clientesEnTabla.get(tableCliente.convertRowIndexToModel(fila));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboFiltroBusqCliente = new javax.swing.JComboBox<>();
        txtFieldBusqCliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCliente = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnRegistrarNuevCliente = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRegistrarNuevSoli = new javax.swing.JButton();
        btnModificarDatos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Identificación Cliente");
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(217, 217, 217));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Identificación de Cliente");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Búsqueda de Cliente Por:");

        comboFiltroBusqCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        comboFiltroBusqCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RFC", "Nombre", "Teléfono" }));

        txtFieldBusqCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldBusqCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldBusqCliente.setForeground(new java.awt.Color(0, 0, 0));

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

        btnRegistrarNuevCliente.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrarNuevCliente.setBorderPainted(false);
        btnRegistrarNuevCliente.setFocusPainted(false);
        btnRegistrarNuevCliente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrarNuevCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarNuevCliente.setOpaque(true);
        btnRegistrarNuevCliente.setText("Registrar Cliente Nuevo");
        btnRegistrarNuevCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarNuevClienteActionPerformed(evt);
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

        btnRegistrarNuevSoli.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrarNuevSoli.setBorderPainted(false);
        btnRegistrarNuevSoli.setFocusPainted(false);
        btnRegistrarNuevSoli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrarNuevSoli.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarNuevSoli.setOpaque(true);
        btnRegistrarNuevSoli.setText("Registrar Nueva Solicitud");
        btnRegistrarNuevSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarNuevSoliActionPerformed(evt);
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
                .addComponent(comboFiltroBusqCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 740, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(txtFieldBusqCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(btnRegistrarNuevCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 532, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 800, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(693, 693, 693)
                .addComponent(btnRegistrarNuevSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(comboFiltroBusqCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 542, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(txtFieldBusqCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(btnRegistrarNuevCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 234, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnRegistrarNuevSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        String texto = sinAcentos(txtFieldBusqCliente.getText().trim());
        String filtro = String.valueOf(comboFiltroBusqCliente.getSelectedItem());
        List<ClienteDTO> encontrados = new ArrayList<>();
        for (ClienteDTO c : clientes) {
            String valor = filtro.equals("RFC") ? c.getRfc() : filtro.equals("Teléfono") ? c.getTelefono() : c.getNombre();
            if (texto.isEmpty() || (valor != null && sinAcentos(valor).contains(texto))) {
                encontrados.add(c);
            }
        }
        cargarClientesEnTabla(encontrados);
        if (encontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese " + filtro + ". Si es un cliente nuevo, favor de registrarlo.",
                    "Identificación de Cliente", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRegistrarNuevClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarNuevClienteActionPerformed
        this.contratacionRenta.pasarDatoRegistroCliente(null);
    }//GEN-LAST:event_btnRegistrarNuevClienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        contratacionRenta.abrirMenuPrincipal();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarNuevSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarNuevSoliActionPerformed
        ClienteDTO cliente = clienteSeleccionado();
        if (cliente != null) {
            contratacionRenta.pasarDatosSolcitudRenta(cliente);
        } else {
            JOptionPane.showMessageDialog(this, "Favor de Seleccionar un Cliente para Registrar la Solicitud.");
        }
    }//GEN-LAST:event_btnRegistrarNuevSoliActionPerformed

    private void btnModificarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatosActionPerformed
        ClienteDTO cliente = clienteSeleccionado();
        if (cliente != null) {
            this.contratacionRenta.pasarDatoRegistroCliente(cliente);
        } else {
            JOptionPane.showMessageDialog(this, "Favor de Seleccionar un Cliente para Modificar.");
        }
    }//GEN-LAST:event_btnModificarDatosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnModificarDatos;
    private javax.swing.JButton btnRegistrarNuevCliente;
    private javax.swing.JButton btnRegistrarNuevSoli;
    private javax.swing.JComboBox<String> comboFiltroBusqCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable tableCliente;
    private javax.swing.JTextField txtFieldBusqCliente;
    // End of variables declaration//GEN-END:variables
}
