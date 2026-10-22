/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import DTOs.ClienteDTO;
import DTOs.ClienteEmpresaDTO;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class RegistroClienteFrame extends javax.swing.JFrame {

    private IContratacionRenta contratacionRenta;

    public RegistroClienteFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        initComponents();
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioBtnClientePart);
        grupo.add(radioBtnEmpresa);
        radioBtnClientePart.setSelected(true);
        radioBtnEmpresa.setSelected(false);
        panelEncargado.setVisible(false);
        this.setLocationRelativeTo(null);
    }

    public void cargarDatosRegistroCliente(ClienteDTO dto) {
        if (dto != null) {
            txtFieldNombre.setText(dto.getNombre());
            txtFieldRFC.setText(dto.getRfc());
            txtFieldTelefono.setText(dto.getTelefono());
            txtFieldCorreoElectro.setText(dto.getCorreo());
            if (dto instanceof ClienteEmpresaDTO) {
                radioBtnEmpresa.setSelected(true);
                ClienteEmpresaDTO empresa = (ClienteEmpresaDTO) dto;         
                txtFileldNombreEncargado.setText(empresa.getNombreEncargadoEmpresa());
                txtFieldPuestoEncargado.setText(empresa.getPuestoEncargadoEmpresa());
                txtFieldTelefonoEncragado.setText(empresa.getTelefonoEncargadoEmpresa());
            } else {               
                radioBtnClientePart.setSelected(true);
                txtFileldNombreEncargado.setText("");
                txtFieldPuestoEncargado.setText("");
                txtFieldTelefonoEncragado.setText("");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        radioBtnClientePart = new javax.swing.JRadioButton();
        radioBtnEmpresa = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFieldNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFieldCorreoElectro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFieldTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFieldRFC = new javax.swing.JTextField();
        panelEncargado = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtFileldNombreEncargado = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFieldTelefonoEncragado = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtFieldPuestoEncargado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnSeleccionarArchivo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Cliente");
        setResizable(false);

        panelFondo.setBackground(new java.awt.Color(217, 217, 217));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Registro de Cliente");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tipo de Cliente:");

        radioBtnClientePart.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        radioBtnClientePart.setForeground(new java.awt.Color(0, 0, 0));
        radioBtnClientePart.setOpaque(false);
        radioBtnClientePart.setText("Cliente Particular");
        radioBtnClientePart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnClientePartActionPerformed(evt);
            }
        });

        radioBtnEmpresa.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        radioBtnEmpresa.setForeground(new java.awt.Color(0, 0, 0));
        radioBtnEmpresa.setOpaque(false);
        radioBtnEmpresa.setText("Empresa");
        radioBtnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnEmpresaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("DATOS BÁSICOS DE LA ORGANIZACIÓN");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre:");

        txtFieldNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldNombre.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Correo Electrónico:");

        txtFieldCorreoElectro.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldCorreoElectro.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldCorreoElectro.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Teléfono:");

        txtFieldTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldTelefono.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("RFC:");

        txtFieldRFC.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldRFC.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldRFC.setForeground(new java.awt.Color(0, 0, 0));

        panelEncargado.setBackground(new java.awt.Color(217, 217, 217));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("DATOS ENCARGADO DE OPERACIÓN");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Nombre:");

        txtFileldNombreEncargado.setBackground(new java.awt.Color(255, 255, 255));
        txtFileldNombreEncargado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFileldNombreEncargado.setForeground(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Teléfono:");

        txtFieldTelefonoEncragado.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldTelefonoEncragado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldTelefonoEncragado.setForeground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Puesto:");

        txtFieldPuestoEncargado.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldPuestoEncargado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldPuestoEncargado.setForeground(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Ingresar Identificación del Encargado");

        btnSeleccionarArchivo.setBackground(new java.awt.Color(44, 44, 44));
        btnSeleccionarArchivo.setBorderPainted(false);
        btnSeleccionarArchivo.setFocusPainted(false);
        btnSeleccionarArchivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSeleccionarArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionarArchivo.setOpaque(true);
        btnSeleccionarArchivo.setText("Seleccionar Archivo");
        btnSeleccionarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEncargadoLayout = new javax.swing.GroupLayout(panelEncargado);
        panelEncargado.setLayout(panelEncargadoLayout);
        panelEncargadoLayout.setHorizontalGroup(
            panelEncargadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 588, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 460, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtFileldNombreEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 460, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(460, 460, 460)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(460, 460, 460)
                .addComponent(txtFieldTelefonoEncragado, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 460, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtFieldPuestoEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 460, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(460, 460, 460)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(460, 460, 460)
                .addComponent(btnSeleccionarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 237, Short.MAX_VALUE))
        );
        panelEncargadoLayout.setVerticalGroup(
            panelEncargadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 140, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 110, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(txtFileldNombreEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 72, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 110, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(txtFieldTelefonoEncragado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 72, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(txtFieldPuestoEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelEncargadoLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(btnSeleccionarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

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

        btnRegistrar.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setOpaque(true);
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
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
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 794, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(radioBtnClientePart, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 615, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(radioBtnEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 485, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 605, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 500, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txtFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 500, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(500, 500, 500)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(500, 500, 500)
                .addComponent(txtFieldCorreoElectro, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 500, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txtFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 500, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(500, 500, 500)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(500, 500, 500)
                .addComponent(txtFieldRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panelEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 800, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(800, 800, 800)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 622, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 578, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(radioBtnClientePart, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 578, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(radioBtnEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 578, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 534, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 504, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(txtFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 466, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 504, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(txtFieldCorreoElectro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 466, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 434, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(txtFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 396, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 434, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(txtFieldRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 396, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(314, 314, 314)
                .addComponent(panelEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 204, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(604, 604, 604)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void radioBtnClientePartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnClientePartActionPerformed
        panelEncargado.setVisible(false);
        this.pack();
    }//GEN-LAST:event_radioBtnClientePartActionPerformed

    private void radioBtnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnEmpresaActionPerformed
        panelEncargado.setVisible(true);
        this.pack();
    }//GEN-LAST:event_radioBtnEmpresaActionPerformed

    private void btnSeleccionarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSeleccionarArchivoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        contratacionRenta.abrirIdentificacionCliente();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if(radioBtnEmpresa.isSelected()){
            ClienteEmpresaDTO empresa = new ClienteEmpresaDTO();
            empresa.setNombre(txtFieldNombre.getText());
            empresa.setCorreo(txtFieldCorreoElectro.getText());
            empresa.setRfc(txtFieldRFC.getText());
            empresa.setTelefono(txtFieldTelefono.getText());
            empresa.setNombreEncargadoEmpresa(txtFileldNombreEncargado.getText());
            empresa.setPuestoEncargadoEmpresa(txtFieldPuestoEncargado.getText());
            empresa.setTelefonoEncargadoEmpresa(txtFieldTelefonoEncragado.getText());
            JOptionPane.showMessageDialog(this, "La Empresa ha saido registrada correctamente.", "Cliente Registrado", JOptionPane.INFORMATION_MESSAGE);
            this.contratacionRenta.abrirIdentificacionCliente();
        }else if(radioBtnClientePart.isSelected()){
            ClienteDTO particular = new ClienteDTO();
            particular.setNombre(txtFieldNombre.getText());
            particular.setRfc(txtFieldRFC.getText());
            particular.setTelefono(txtFieldTelefono.getText());
            particular.setCorreo(txtFieldCorreoElectro.getText());
            JOptionPane.showMessageDialog(this, "El Cliente ha sido registrado correctamente.", "Cliente Registrado", JOptionPane.INFORMATION_MESSAGE);
            this.contratacionRenta.abrirIdentificacionCliente();
        }
        
        javax.swing.JOptionPane.showMessageDialog(this,
                "El cliente ha sido registrado correctamente.",
                "Registro Cliente",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        contratacionRenta.abrirSolicitudRenta();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnSeleccionarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelEncargado;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JRadioButton radioBtnClientePart;
    private javax.swing.JRadioButton radioBtnEmpresa;
    private javax.swing.JTextField txtFieldCorreoElectro;
    private javax.swing.JTextField txtFieldNombre;
    private javax.swing.JTextField txtFieldPuestoEncargado;
    private javax.swing.JTextField txtFieldRFC;
    private javax.swing.JTextField txtFieldTelefono;
    private javax.swing.JTextField txtFieldTelefonoEncragado;
    private javax.swing.JTextField txtFileldNombreEncargado;
    // End of variables declaration//GEN-END:variables
}
