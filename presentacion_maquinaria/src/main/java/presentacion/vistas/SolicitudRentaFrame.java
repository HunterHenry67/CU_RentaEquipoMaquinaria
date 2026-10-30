/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion.vistas;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.CardLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import objetosdto.ClienteDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;
import presentacion.control.IContratacionRenta;
import presentacion.control.RequerimientosTecnicos;

/**
 *
 * @author Home
 */
public class SolicitudRentaFrame extends javax.swing.JFrame {

    private IContratacionRenta contratacionRenta;
    private String folio;

    private DatePicker datePickerInicio;
    private DatePicker datePickerFinal;

    public SolicitudRentaFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        initComponents();
        Ventanas.cerrarAlMenu(this, contratacionRenta);

        inicializarSelectoresFecha();

        CardLayout card = (CardLayout) this.getContentPane().getLayout();
        card.show(this.getContentPane(), "card1");
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void inicializarSelectoresFecha() {

        DatePickerSettings settingsInicio = new DatePickerSettings();
        settingsInicio.setFormatForDatesCommonEra("yyyy-MM-dd");

        DatePickerSettings settingsFinal = new DatePickerSettings();
        settingsFinal.setFormatForDatesCommonEra("yyyy-MM-dd");

        datePickerInicio = new DatePicker(settingsInicio);
        datePickerFinal = new DatePicker(settingsFinal);
        // las fechas se eligen en el calendario, así siempre llegan con un formato válido
        settingsInicio.setAllowKeyboardEditing(false);
        settingsFinal.setAllowKeyboardEditing(false);

        datePickerInicio.setBounds(formtxtFechaInicio.getBounds());
        datePickerFinal.setBounds(formtxtFechaFinal.getBounds());

        formtxtFechaInicio.setVisible(false);
        formtxtFechaFinal.setVisible(false);

        panelSolictudNormal.add(datePickerInicio);
        panelSolictudNormal.add(datePickerFinal);

        datePickerInicio.addDateChangeListener(event -> {
            LocalDate nuevaInicio = event.getNewDate();
            if (nuevaInicio != null) {
                datePickerFinal.getSettings().setDateRangeLimits(nuevaInicio, null);
            }
        });

        panelSolictudNormal.repaint();
    }

    public LocalDate getFechaInicio() {
        return datePickerInicio.getDate();
    }

    public LocalDate getFechaFinal() {
        return datePickerFinal.getDate();
    }

    // El control la llama al volver al menú: la siguiente solicitud empieza en blanco
    public void limpiar() {
        txtFieldNombreObra.setText("");
        txtFieldDireccion.setText("");
        txtAreaActividadRealizar.setText("");
        txtAreaCondicionExtra.setText("");
        txtAreaRazonCancelacion.setText("");
        datePickerInicio.setDate(null);
        datePickerFinal.setDate(null);
        datePickerFinal.getSettings().setDateRangeLimits(null, null);
        comboTipoTrabajo.setSelectedIndex(0);
        comboTipoTerreno.setSelectedIndex(0);
        ((DefaultTableModel) tableEquipoSoli.getModel()).setRowCount(0);
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "card1");
    }

    // El control ya confirmó la cancelación: se muestra la parte para escribir la razón
    public void mostrarCancelacion() {
        CardLayout card = (CardLayout) this.getContentPane().getLayout();
        card.show(this.getContentPane(), "card2");
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    public void cargarFolio(String folio) {
        this.folio = folio;
        lblFolio.setText("Folio: " + folio);
    }

    public void cargarDatosCliente(ClienteDTO clienteCargado) {
        if (clienteCargado != null) {
            lblNombreCliente.setText("Nombre del Cliente: " + clienteCargado.getNombre());
            lblRFC.setText("RFC: " + clienteCargado.getRfc());
        }
        if (lblRFCancelacion != null) {
            lblRFCancelacion.setText("RFC: " + clienteCargado.getRfc());
        }
    }

    public void cargarEquiposSolicitadosEnTabla(List<EquipoSolicitadoDTO> listaEquipos) {
        DefaultTableModel modelo = (DefaultTableModel) tableEquipoSoli.getModel();
        modelo.setRowCount(0);
        for (objetosdto.EquipoSolicitadoDTO eq : listaEquipos) {
            Object[] fila = {eq.getDescripcionEquipo(), eq.getCantidad()
            };
            modelo.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSolictudNormal = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblFolio = new javax.swing.JLabel();
        lblRFC = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        formtxtFechaInicio = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        formtxtFechaFinal = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtFieldNombreObra = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtFieldDireccion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnAgregarEquipo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEquipoSoli = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaActividadRealizar = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaCondicionExtra = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        comboTipoTrabajo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        comboTipoTerreno = new javax.swing.JComboBox<>();
        btnCancelarSoli = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        panelCancelacionSolicitud = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblRFCancelacion = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaRazonCancelacion = new javax.swing.JTextArea();
        btnRegistrarCancelacion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setTitle("Solicitud de Renta");
        getContentPane().setLayout(new java.awt.CardLayout());

        panelSolictudNormal.setBackground(new java.awt.Color(217, 217, 217));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Solicitud de Renta");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("DATOS DEL CLIENTE");

        lblNombreCliente.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        lblNombreCliente.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreCliente.setText("Nombre del Cliente:");

        lblFolio.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        lblFolio.setForeground(new java.awt.Color(0, 0, 0));
        lblFolio.setText("Folio:");

        lblRFC.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        lblRFC.setForeground(new java.awt.Color(0, 0, 0));
        lblRFC.setText("RFC:");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("PERIODO DE USO DE EQUIPO");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Inicio:");

        formtxtFechaInicio.setBackground(new java.awt.Color(255, 255, 255));
        formtxtFechaInicio.setForeground(new java.awt.Color(0, 0, 0));
        formtxtFechaInicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Final:");

        formtxtFechaFinal.setBackground(new java.awt.Color(255, 255, 255));
        formtxtFechaFinal.setForeground(new java.awt.Color(0, 0, 0));
        formtxtFechaFinal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("UBICACIÓN DE LA OBRA");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Nombre de la Obra:");

        txtFieldNombreObra.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldNombreObra.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldNombreObra.setForeground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Dirección:");

        txtFieldDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFieldDireccion.setForeground(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("SELECCIÓN DE EQUIPO");

        btnAgregarEquipo.setBackground(new java.awt.Color(44, 44, 44));
        btnAgregarEquipo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAgregarEquipo.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarEquipo.setText("Agregar un Equipo");
        btnAgregarEquipo.setBorderPainted(false);
        btnAgregarEquipo.setFocusPainted(false);
        btnAgregarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEquipoActionPerformed(evt);
            }
        });

        tableEquipoSoli.setBackground(new java.awt.Color(255, 255, 255));
        tableEquipoSoli.setForeground(new java.awt.Color(0, 0, 0));
        tableEquipoSoli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equipo Solicitado", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEquipoSoli);

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(70, 70, 70));
        jLabel13.setText("<html>Nota: El equipo seleccionado puede ser modificado por profesionales dependiendo de la necesidad requerida.</html>");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("ACTIVIDAD QUE VA A REALIZAR EL CLIENTE");

        txtAreaActividadRealizar.setBackground(new java.awt.Color(255, 255, 255));
        txtAreaActividadRealizar.setColumns(20);
        txtAreaActividadRealizar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAreaActividadRealizar.setForeground(new java.awt.Color(0, 0, 0));
        txtAreaActividadRealizar.setLineWrap(true);
        txtAreaActividadRealizar.setRows(3);
        txtAreaActividadRealizar.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtAreaActividadRealizar);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("CONDICIONES A TOMAR EN CUENTA");

        txtAreaCondicionExtra.setBackground(new java.awt.Color(255, 255, 255));
        txtAreaCondicionExtra.setColumns(20);
        txtAreaCondicionExtra.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAreaCondicionExtra.setForeground(new java.awt.Color(0, 0, 0));
        txtAreaCondicionExtra.setLineWrap(true);
        txtAreaCondicionExtra.setRows(3);
        txtAreaCondicionExtra.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtAreaCondicionExtra);

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Tipo de Trabajo:");

        comboTipoTrabajo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        comboTipoTrabajo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Excavación de cepas", "Sin especificar" }));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Tipo de Terreno:");

        comboTipoTerreno.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        comboTipoTerreno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sin especificar", "Firme", "Arcilloso", "Arenoso", "Rocoso" }));

        btnCancelarSoli.setBackground(new java.awt.Color(236, 34, 31));
        btnCancelarSoli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancelarSoli.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarSoli.setText("Cancelar Solicitud");
        btnCancelarSoli.setBorderPainted(false);
        btnCancelarSoli.setFocusPainted(false);
        btnCancelarSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarSoliActionPerformed(evt);
            }
        });

        btnRegistrar.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSolictudNormalLayout = new javax.swing.GroupLayout(panelSolictudNormal);
        panelSolictudNormal.setLayout(panelSolictudNormalLayout);
        panelSolictudNormalLayout.setHorizontalGroup(
            panelSolictudNormalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                .addGroup(panelSolictudNormalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(lblRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(formtxtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(formtxtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(txtFieldNombreObra, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(txtFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(743, 743, 743)
                        .addComponent(btnAgregarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(comboTipoTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(500, 500, 500)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(636, 636, 636)
                        .addComponent(comboTipoTerreno, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnCancelarSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(800, 800, 800)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 40, Short.MAX_VALUE))
        );
        panelSolictudNormalLayout.setVerticalGroup(
            panelSolictudNormalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                .addGroup(panelSolictudNormalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(lblFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(lblRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(formtxtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(formtxtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(txtFieldNombreObra, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(txtFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(btnAgregarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(432, 432, 432)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(460, 460, 460)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(432, 432, 432)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(460, 460, 460)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(538, 538, 538)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(538, 538, 538)
                        .addComponent(comboTipoTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(538, 538, 538)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(538, 538, 538)
                        .addComponent(comboTipoTerreno, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(604, 604, 604)
                        .addComponent(btnCancelarSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSolictudNormalLayout.createSequentialGroup()
                        .addGap(604, 604, 604)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 32, Short.MAX_VALUE))
        );

        getContentPane().add(panelSolictudNormal, "card1");

        panelCancelacionSolicitud.setBackground(new java.awt.Color(217, 217, 217));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Solicitud de Renta");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("DATOS DEL CLIENTE");

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Folio:");

        lblRFCancelacion.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        lblRFCancelacion.setForeground(new java.awt.Color(0, 0, 0));
        lblRFCancelacion.setText("RFC:");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("RAZÓN DE CANCELACIÓN DE PROCESO DE SOLICITUD");

        txtAreaRazonCancelacion.setBackground(new java.awt.Color(255, 255, 255));
        txtAreaRazonCancelacion.setColumns(20);
        txtAreaRazonCancelacion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAreaRazonCancelacion.setForeground(new java.awt.Color(0, 0, 0));
        txtAreaRazonCancelacion.setLineWrap(true);
        txtAreaRazonCancelacion.setRows(3);
        txtAreaRazonCancelacion.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txtAreaRazonCancelacion);

        btnRegistrarCancelacion.setBackground(new java.awt.Color(44, 44, 44));
        btnRegistrarCancelacion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrarCancelacion.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarCancelacion.setText("Registrar Cancelación");
        btnRegistrarCancelacion.setBorderPainted(false);
        btnRegistrarCancelacion.setFocusPainted(false);
        btnRegistrarCancelacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarCancelacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCancelacionSolicitudLayout = new javax.swing.GroupLayout(panelCancelacionSolicitud);
        panelCancelacionSolicitud.setLayout(panelCancelacionSolicitudLayout);
        panelCancelacionSolicitudLayout.setHorizontalGroup(
            panelCancelacionSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                .addGroup(panelCancelacionSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblRFCancelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(720, 720, 720)
                        .addComponent(btnRegistrarCancelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 40, Short.MAX_VALUE))
        );
        panelCancelacionSolicitudLayout.setVerticalGroup(
            panelCancelacionSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                .addGroup(panelCancelacionSolicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(lblRFCancelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCancelacionSolicitudLayout.createSequentialGroup()
                        .addGap(604, 604, 604)
                        .addComponent(btnRegistrarCancelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 32, Short.MAX_VALUE))
        );

        getContentPane().add(panelCancelacionSolicitud, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEquipoActionPerformed
        contratacionRenta.abrirBusquedaEquipo();
    }//GEN-LAST:event_btnAgregarEquipoActionPerformed

    private void btnCancelarSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarSoliActionPerformed
        contratacionRenta.abrirCancelacionSolicitud();
    }//GEN-LAST:event_btnCancelarSoliActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // se manda lo capturado; el control revisa los campos obligatorios, las fechas y el equipo
        SolicitudRentaDTO solicitudDTO = new SolicitudRentaDTO();
        solicitudDTO.setFolio(folio);
        solicitudDTO.setNombreObra(txtFieldNombreObra.getText().trim());
        solicitudDTO.setDireccionObra(txtFieldDireccion.getText().trim());
        String terreno = comboTipoTerreno.getSelectedItem().toString();
        if (!terreno.equals("Sin especificar")) {
            RequerimientosTecnicos.capturar(solicitudDTO.getRequerimientos(), RequerimientosTecnicos.TERRENO, terreno);
        }
        String tipoTrabajo = comboTipoTrabajo.getSelectedItem().toString();
        String actividad = txtAreaActividadRealizar.getText().trim();
        if (!tipoTrabajo.equals("Sin especificar")) {
            actividad = actividad.isEmpty() ? tipoTrabajo : tipoTrabajo + ". " + actividad;
        }
        solicitudDTO.setDescripcionActividad(actividad);
        solicitudDTO.setCondicionesEspeciales(txtAreaCondicionExtra.getText());
        PeriodoSolicitadoDTO periodo = new PeriodoSolicitadoDTO();
        periodo.setFechaInicio(datePickerInicio.getDate());
        periodo.setFechaFin(datePickerFinal.getDate());
        solicitudDTO.setPeriodo(periodo);
        for (int i = 0; i < tableEquipoSoli.getRowCount(); i++) {
            EquipoSolicitadoDTO equipo = new EquipoSolicitadoDTO();
            equipo.setDescripcionEquipo(tableEquipoSoli.getValueAt(i, 0).toString());
            equipo.setCantidad(Integer.parseInt(tableEquipoSoli.getValueAt(i, 1).toString()));
            solicitudDTO.getEquiposSolicitados().add(equipo);
        }
        this.contratacionRenta.registrarSolicitud(solicitudDTO);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnRegistrarCancelacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarCancelacionActionPerformed
        SolicitudRentaDTO cancelada = new SolicitudRentaDTO();
        cancelada.setFolio(folio);
        cancelada.setMotivoCancelacion(txtAreaRazonCancelacion.getText());
        contratacionRenta.pasarDatosCancelacion(cancelada);
    }//GEN-LAST:event_btnRegistrarCancelacionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEquipo;
    private javax.swing.JButton btnCancelarSoli;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRegistrarCancelacion;
    private javax.swing.JComboBox<String> comboTipoTerreno;
    private javax.swing.JComboBox<String> comboTipoTrabajo;
    private javax.swing.JFormattedTextField formtxtFechaFinal;
    private javax.swing.JFormattedTextField formtxtFechaInicio;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblRFC;
    private javax.swing.JLabel lblRFCancelacion;
    private javax.swing.JPanel panelCancelacionSolicitud;
    private javax.swing.JPanel panelSolictudNormal;
    private javax.swing.JTable tableEquipoSoli;
    private javax.swing.JTextArea txtAreaActividadRealizar;
    private javax.swing.JTextArea txtAreaCondicionExtra;
    private javax.swing.JTextArea txtAreaRazonCancelacion;
    private javax.swing.JTextField txtFieldDireccion;
    private javax.swing.JTextField txtFieldNombreObra;
    // End of variables declaration//GEN-END:variables
}
