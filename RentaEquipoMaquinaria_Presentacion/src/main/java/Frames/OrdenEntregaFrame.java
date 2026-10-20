package Frames;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class OrdenEntregaFrame extends JFrame {

    private IContratacionRenta contratacionRenta;

    private JLabel lblTitulo, lblCliente, lblEstado;
    private JLabel lblEquipoTitulo, lblEquipo, lblUnidad, lblPrecioEquipo;
    private JLabel lblAccesorios, lblPrecioAccesorios, lblDireccion;
    private JLabel lblFechaEntregaTit, lblFechaEntrega, lblFinTit, lblFin;
    private JLabel lblTotal, lblDeposito, lblDepositoValor;
    private JLabel lblIdentificacion, lblIdentificacionEstado;
    private JLabel lblDepositoEstadoTit, lblDepositoEstado;
    private JSeparator linea1, linea2, linea3;

    private final Color verde = new Color(0, 180, 0);
    private final Color naranja = new Color(230, 130, 0);

    public OrdenEntregaFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;

        setTitle("Orden de Entrega");
        setSize(420, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(217, 217, 217));

        crearComponentes();
        configurar(true, true);
    }

    // El controlador lo llama cada vez que se abre la pantalla
    public void configurar(boolean conCucharon, boolean finalizada) {
        // Accesorios y total
        if (conCucharon) {
            lblAccesorios.setText("Kit de señalización y cucharón");
            lblPrecioAccesorios.setText("$500");
            lblTotal.setText("Total de la renta:  $11,020");
        } else {
            lblAccesorios.setText("");
            lblPrecioAccesorios.setText("");
            lblTotal.setText("Total de la renta:  $10,440");
        }

        // Estado de la orden
        if (finalizada) {
            lblEstado.setText("Finalizada");
            lblEstado.setForeground(verde);
            lblIdentificacionEstado.setText("Cumplida");
            lblIdentificacionEstado.setForeground(verde);
        } else {
            lblEstado.setText("Pendiente");
            lblEstado.setForeground(naranja);
            lblIdentificacionEstado.setText("Pendiente");
            lblIdentificacionEstado.setForeground(naranja);
        }
    }

    private void crearComponentes() {
        // Título
        lblTitulo = new JLabel("Orden de Entrega", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(0, 10, 420, 30);
        add(lblTitulo);

        // Cliente y estado
        lblCliente = new JLabel("Julián Martínez García");
        lblCliente.setFont(new Font("Arial", Font.BOLD, 20));
        lblCliente.setBounds(55, 80, 260, 30);
        add(lblCliente);

        lblEstado = new JLabel("Finalizada");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstado.setForeground(verde);
        lblEstado.setBounds(305, 85, 90, 25);
        add(lblEstado);

        // Equipo en renta
        lblEquipoTitulo = new JLabel("Equipo en Renta:");
        lblEquipoTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblEquipoTitulo.setBounds(20, 145, 200, 25);
        add(lblEquipoTitulo);

        linea1 = new JSeparator();
        linea1.setBounds(15, 180, 380, 2);
        add(linea1);

        lblEquipo = new JLabel("Excavadora CAT 320");
        lblEquipo.setFont(new Font("Arial", Font.BOLD, 15));
        lblEquipo.setBounds(30, 188, 220, 22);
        add(lblEquipo);

        lblUnidad = new JLabel("Unidad M-001");
        lblUnidad.setFont(new Font("Arial", Font.BOLD, 15));
        lblUnidad.setBounds(38, 210, 200, 22);
        add(lblUnidad);

        lblPrecioEquipo = new JLabel("$9,000", SwingConstants.RIGHT);
        lblPrecioEquipo.setFont(new Font("Arial", Font.BOLD, 15));
        lblPrecioEquipo.setBounds(280, 195, 95, 22);
        add(lblPrecioEquipo);

        // Accesorios
        lblAccesorios = new JLabel("Kit de señalización y cucharón");
        lblAccesorios.setFont(new Font("Arial", Font.BOLD, 14));
        lblAccesorios.setBounds(33, 250, 260, 22);
        add(lblAccesorios);

        lblPrecioAccesorios = new JLabel("$500", SwingConstants.RIGHT);
        lblPrecioAccesorios.setFont(new Font("Arial", Font.BOLD, 15));
        lblPrecioAccesorios.setBounds(280, 248, 95, 22);
        add(lblPrecioAccesorios);

        lblDireccion = new JLabel("Entrega en: Av. Guerrero #1105, Plaza Goya, Ciudad Obregón");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 10));
        lblDireccion.setForeground(Color.DARK_GRAY);
        lblDireccion.setBounds(22, 280, 360, 18);
        add(lblDireccion);

        linea2 = new JSeparator();
        linea2.setBounds(15, 300, 380, 2);
        add(linea2);

        // Fechas
        lblFechaEntregaTit = new JLabel("Fecha de entrega:");
        lblFechaEntregaTit.setFont(new Font("Arial", Font.BOLD, 15));
        lblFechaEntregaTit.setBounds(55, 315, 250, 25);
        add(lblFechaEntregaTit);

        lblFechaEntrega = new JLabel("10/09/2026, antes de las 8:00 AM");
        lblFechaEntrega.setFont(new Font("Arial", Font.BOLD, 15));
        lblFechaEntrega.setBounds(55, 350, 300, 25);
        add(lblFechaEntrega);

        lblFinTit = new JLabel("Fin de la renta:");
        lblFinTit.setFont(new Font("Arial", Font.BOLD, 15));
        lblFinTit.setBounds(55, 390, 250, 25);
        add(lblFinTit);

        lblFin = new JLabel("15/09/2026");
        lblFin.setFont(new Font("Arial", Font.BOLD, 15));
        lblFin.setBounds(55, 425, 250, 25);
        add(lblFin);

        // Total y depósito
        lblTotal = new JLabel("Total de la renta:  $11,020");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 11));
        lblTotal.setBounds(22, 455, 250, 18);
        add(lblTotal);

        lblDeposito = new JLabel("Depósito de garantía");
        lblDeposito.setFont(new Font("Arial", Font.BOLD, 15));
        lblDeposito.setBounds(55, 485, 220, 25);
        add(lblDeposito);

        lblDepositoValor = new JLabel("$3,200", SwingConstants.RIGHT);
        lblDepositoValor.setFont(new Font("Arial", Font.BOLD, 15));
        lblDepositoValor.setBounds(280, 485, 95, 25);
        add(lblDepositoValor);

        linea3 = new JSeparator();
        linea3.setBounds(15, 525, 380, 2);
        add(linea3);

        // Estados
        lblIdentificacion = new JLabel("Identificación");
        lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 15));
        lblIdentificacion.setBounds(55, 540, 200, 25);
        add(lblIdentificacion);

        lblIdentificacionEstado = new JLabel("Cumplida");
        lblIdentificacionEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblIdentificacionEstado.setForeground(verde);
        lblIdentificacionEstado.setBounds(290, 538, 100, 25);
        add(lblIdentificacionEstado);

        lblDepositoEstadoTit = new JLabel("Depósito de garantía");
        lblDepositoEstadoTit.setFont(new Font("Arial", Font.BOLD, 15));
        lblDepositoEstadoTit.setBounds(55, 575, 220, 25);
        add(lblDepositoEstadoTit);

        lblDepositoEstado = new JLabel("Cubierto");
        lblDepositoEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblDepositoEstado.setForeground(verde);
        lblDepositoEstado.setBounds(290, 573, 100, 25);
        add(lblDepositoEstado);
    }

}
