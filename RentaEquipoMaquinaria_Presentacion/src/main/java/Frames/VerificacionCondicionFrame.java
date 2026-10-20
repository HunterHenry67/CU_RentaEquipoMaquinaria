package Frames;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class VerificacionCondicionFrame extends JFrame {

    private IContratacionRenta contratacionRenta;
    private boolean conCucharon = true;

    private JLabel lblTitulo, lblSubtitulo;
    private JCheckBox chkCliente, chkEquipo, chkAccesorios, chkPeriodo, chkTarifa;
    private JLabel lblCliente, lblEquipo, lblAccesorios, lblPeriodo, lblTarifa;
    private JLabel lblDeposito, lblDepositoValor, lblTotal;
    private JLabel lblEntrega;
    private JSeparator linea1, linea2;
    private JButton btnGenerar, btnNoGenerar;

    public VerificacionCondicionFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;

        setTitle("Verificación de Condiciones");
        setSize(480, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(217, 217, 217));

        crearComponentes();
        agregarEventos();
    }

    // El controlador lo llama cada vez que se abre la pantalla
    public void configurar(boolean conCucharon) {
        this.conCucharon = conCucharon;

        if (conCucharon) {
            lblAccesorios.setText("Kit de señalización y cucharón");
            lblTotal.setText("Total de la renta: $11,020 (renta, accesorios e impuestos)");
        } else {
            lblAccesorios.setText("");
            lblTotal.setText("Total de la renta: $10,440");
        }
    }

    private void crearComponentes() {
        // Título
        lblTitulo = new JLabel("Verificación de Condiciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(0, 15, 480, 30);
        add(lblTitulo);

        lblSubtitulo = new JLabel("Condiciones Registradas");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblSubtitulo.setBounds(30, 80, 300, 25);
        add(lblSubtitulo);

        // Cliente
        chkCliente = new JCheckBox("Cliente", true);
        chkCliente.setFont(new Font("Arial", Font.BOLD, 16));
        chkCliente.setOpaque(false);
        chkCliente.setBounds(30, 115, 180, 30);
        add(chkCliente);

        lblCliente = new JLabel("Julián Martínez García", SwingConstants.RIGHT);
        lblCliente.setBounds(220, 115, 220, 30);
        add(lblCliente);

        // Equipo y unidad
        chkEquipo = new JCheckBox("Equipo y Unidad", true);
        chkEquipo.setFont(new Font("Arial", Font.BOLD, 16));
        chkEquipo.setOpaque(false);
        chkEquipo.setBounds(30, 150, 200, 30);
        add(chkEquipo);

        lblEquipo = new JLabel("Excavadora CAT 320", SwingConstants.RIGHT);
        lblEquipo.setBounds(240, 150, 200, 30);
        add(lblEquipo);

        // Accesorios
        chkAccesorios = new JCheckBox("Accesorios aceptados", true);
        chkAccesorios.setFont(new Font("Arial", Font.BOLD, 16));
        chkAccesorios.setOpaque(false);
        chkAccesorios.setBounds(30, 190, 220, 30);
        add(chkAccesorios);

        lblAccesorios = new JLabel("Kit de señalización y cucharón", SwingConstants.RIGHT);
        lblAccesorios.setBounds(240, 190, 200, 30);
        add(lblAccesorios);

        // Periodo
        chkPeriodo = new JCheckBox("Periodo", true);
        chkPeriodo.setFont(new Font("Arial", Font.BOLD, 16));
        chkPeriodo.setOpaque(false);
        chkPeriodo.setBounds(30, 228, 180, 30);
        add(chkPeriodo);

        lblPeriodo = new JLabel("10/09/2026 - 15/09/2026", SwingConstants.RIGHT);
        lblPeriodo.setBounds(240, 228, 200, 30);
        add(lblPeriodo);

        // Tarifa
        chkTarifa = new JCheckBox("Tarifa", true);
        chkTarifa.setFont(new Font("Arial", Font.BOLD, 16));
        chkTarifa.setOpaque(false);
        chkTarifa.setBounds(30, 266, 180, 30);
        add(chkTarifa);

        lblTarifa = new JLabel("$9,000 MXN", SwingConstants.RIGHT);
        lblTarifa.setBounds(300, 266, 140, 30);
        add(lblTarifa);

        // Depósito
        lblDeposito = new JLabel("Depósito");
        lblDeposito.setFont(new Font("Arial", Font.BOLD, 16));
        lblDeposito.setBounds(58, 305, 180, 30);
        add(lblDeposito);

        lblDepositoValor = new JLabel("$3,200 MXN", SwingConstants.RIGHT);
        lblDepositoValor.setBounds(300, 305, 140, 30);
        add(lblDepositoValor);

        lblTotal = new JLabel("Total de la renta: $11,020 (renta, accesorios e impuestos)");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 11));
        lblTotal.setBounds(58, 330, 380, 20);
        add(lblTotal);

        // Línea 1
        linea1 = new JSeparator();
        linea1.setBounds(15, 365, 440, 2);
        add(linea1);

        // Entrega
        lblEntrega = new JLabel("<html>Entrega confirmada: 10/09/2026 antes de las 8:00 AM, "
                + "en Av. Guerrero #1105, Plaza Goya.<br>"
                + "Al generar la orden, la M-001 queda ocupada del 10 al 15/09.</html>");
        lblEntrega.setBounds(15, 380, 440, 45);
        add(lblEntrega);

        // Línea 2
        linea2 = new JSeparator();
        linea2.setBounds(15, 435, 440, 2);
        add(linea2);

        // Botones
        btnGenerar = new JButton("Generar Orden");
        btnGenerar.setBackground(new Color(43, 43, 43));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setBounds(175, 460, 140, 30);
        add(btnGenerar);

        btnNoGenerar = new JButton("No Generar Orden");
        btnNoGenerar.setBackground(new Color(238, 34, 34));
        btnNoGenerar.setForeground(Color.WHITE);
        btnNoGenerar.setBounds(175, 500, 140, 28);
        add(btnNoGenerar);
    }

    private void agregarEventos() {
        // Orden finalizada
        btnGenerar.addActionListener(e -> contratacionRenta.abrirOrdenEntrega(conCucharon, true));

        // Solicitud en estado pendiente
        btnNoGenerar.addActionListener(e -> contratacionRenta.abrirOrdenEntrega(conCucharon, false));
    }

}
