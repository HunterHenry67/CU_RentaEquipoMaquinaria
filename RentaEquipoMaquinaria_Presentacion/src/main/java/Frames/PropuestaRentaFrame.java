package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PropuestaRentaFrame extends JFrame {

    private IContratacionRenta contratacionRenta;

    // Datos
    private int dias = 6;
    private double tarifaDia = 1500;
    private double senalizacion = 300;
    private double cucharon = 200;
    private double deposito = 3200;
    private boolean incluye = true;
    private static final double IVA = 0.16;

    private final DecimalFormat moneda = new DecimalFormat("$#,##0");

    // Componentes
    private JLabel lblCliente, lblEquipo, lblAccesorioOb, lblAccesorioOp, lblPeriodo;
    private JLabel lblRentatxt, lblRenta, lblSenalizacion, lblCucharon;
    private JLabel lblSubtotal, lblIva, lblTotal, lblDeposito;
    private JPanel filaCucharon;
    private JButton btnCambiarEquipo, btnCambiarPeriodo;
    private JButton btnAceptar, btnAceptarParcial, btnRechazar;

    public PropuestaRentaFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        configuracionVentana();
    }

    // El controlador lo llama cada vez que se abre la pantalla
    public void reiniciar() {
        incluye = true;
        recalcular();
    }

    private void configuracionVentana() {
        setTitle("Propuesta de la Renta");
        setSize(1000, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout(0, 15));
        raiz.setBorder(new EmptyBorder(25, 30, 20, 30));
        raiz.setBackground(new Color(0xD9D9D9));
        setContentPane(raiz);

        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(crearCentro(), BorderLayout.CENTER);
        raiz.add(crearInferior(), BorderLayout.SOUTH);

        configurarAcciones();
        recalcular();
    }

    // Encabezado
    private JPanel crearEncabezado() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Registrar decisión del cliente");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        lblCliente = new JLabel("Cliente: Julián Martínez García");
        lblCliente.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblCliente.setForeground(Color.GRAY.darker());
        lblCliente.setAlignmentX(LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setAlignmentX(LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        p.add(titulo);
        p.add(Box.createVerticalStrut(5));
        p.add(lblCliente);
        p.add(Box.createVerticalStrut(12));
        p.add(sep);
        return p;
    }

    // Centro: propuesta | importe | botones de cambio
    private JPanel crearCentro() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 0, 30);

        c.gridx = 0;
        c.weightx = 0.5;
        centro.add(crearPropuesta(), c);

        c.gridx = 1;
        c.weightx = 0.5;
        centro.add(crearImporte(), c);

        c.gridx = 2;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 0, 0);
        centro.add(crearBotonesCambio(), c);
        return centro;
    }

    private JPanel crearPropuesta() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(seccion("LA PROPUESTA"));
        p.add(etiquetaGris("Equipo"));
        lblEquipo = valorGrande("Excavadora CAT 320 · unidad M-001");
        p.add(lblEquipo);
        p.add(Box.createVerticalStrut(12));

        p.add(etiquetaGris("Accesorios"));
        lblAccesorioOb = valorGrande("Kit de señalización - indispensable");
        lblAccesorioOp = valorGrande("Cucharón adicional - opcional");
        p.add(lblAccesorioOb);
        p.add(lblAccesorioOp);
        p.add(Box.createVerticalStrut(12));

        p.add(etiquetaGris("Periodo"));
        lblPeriodo = valorGrande("10/09/2026 - 15/09/2026 · 6 días");
        p.add(lblPeriodo);
        p.add(Box.createVerticalGlue());

        return p;
    }

    private JPanel crearImporte() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(seccion("IMPORTE"));

        lblRentatxt = new JLabel();
        lblRenta = new JLabel();
        p.add(fila(lblRentatxt, lblRenta, true, 15));

        lblSenalizacion = new JLabel();
        p.add(fila(new JLabel("Kit de señalización"), lblSenalizacion, true, 15));

        lblCucharon = new JLabel();
        filaCucharon = fila(new JLabel("Cucharón adicional"), lblCucharon, true, 15);
        p.add(filaCucharon);

        p.add(Box.createVerticalStrut(6));
        p.add(linea());
        p.add(Box.createVerticalStrut(6));

        lblSubtotal = new JLabel();
        p.add(fila(new JLabel("Subtotal"), lblSubtotal, true, 15));
        lblIva = new JLabel();
        p.add(fila(new JLabel("Impuesto (16%)"), lblIva, true, 15));

        p.add(Box.createVerticalStrut(6));
        p.add(linea());
        p.add(Box.createVerticalStrut(6));

        lblTotal = new JLabel();
        p.add(fila(new JLabel("Total de la renta"), lblTotal, true, 20));

        lblDeposito = new JLabel();
        JLabel txtDep = new JLabel("Depósito de garantía, aparte");
        txtDep.setForeground(Color.GRAY.darker());
        lblDeposito.setForeground(Color.GRAY.darker());
        p.add(fila(txtDep, lblDeposito, false, 14));
        p.add(Box.createVerticalGlue());

        return p;
    }

    private JPanel crearBotonesCambio() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        btnCambiarEquipo = botonOscuro("Cambiar equipo");
        btnCambiarPeriodo = botonOscuro("Cambiar periodo");

        p.add(Box.createVerticalStrut(40));
        p.add(btnCambiarEquipo);
        p.add(Box.createVerticalStrut(95));
        p.add(btnCambiarPeriodo);
        p.add(Box.createVerticalGlue());
        return p;
    }

    // Decisión del cliente
    private JPanel crearInferior() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        sep.setAlignmentX(LEFT_ALIGNMENT);
        p.add(sep);
        p.add(Box.createVerticalStrut(12));

        JLabel sec = seccion("DECISIÓN DEL CLIENTE");
        sec.setAlignmentX(LEFT_ALIGNMENT);
        p.add(sec);
        JLabel texto = new JLabel("<html>«Aceptar parcial» significa que el cliente se queda con el equipo "
                + "y el accesorio indispensable,<br>pero quita el opcional. El importe se recalcula.</html>");
        texto.setForeground(Color.GRAY.darker());
        texto.setAlignmentX(LEFT_ALIGNMENT);
        p.add(texto);
        p.add(Box.createVerticalStrut(15));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 0));
        botones.setOpaque(false);
        botones.setAlignmentX(LEFT_ALIGNMENT);

        btnAceptar = botonOscuro("Aceptar");
        btnAceptarParcial = botonOscuro("Aceptar parcial");
        btnRechazar = botonOscuro("Rechazar");
        btnRechazar.setBackground(new Color(0xEE2222));

        botones.add(btnAceptar);
        botones.add(btnAceptarParcial);
        botones.add(btnRechazar);
        p.add(botones);
        return p;
    }

    private void configurarAcciones() {
        btnAceptar.addActionListener(e -> contratacionRenta.abrirVerificacionCondicion(true));

        btnAceptarParcial.addActionListener(e -> {
            incluye = false;
            recalcular();
            contratacionRenta.abrirPropuestaPendiente();
        });

        btnRechazar.addActionListener(e
                -> JOptionPane.showMessageDialog(this, "Propuesta rechazada"));

        btnCambiarEquipo.addActionListener(e
                -> JOptionPane.showMessageDialog(this, "Aquí irá la pantalla para cambiar equipo"));

        btnCambiarPeriodo.addActionListener(e
                -> JOptionPane.showMessageDialog(this, "Aquí irá la pantalla para cambiar periodo"));
    }

    private void recalcular() {
        double renta = dias * tarifaDia;
        double subtotal = renta + senalizacion + (incluye ? cucharon : 0);
        double iva = subtotal * IVA;
        double total = subtotal + iva;

        lblRentatxt.setText("Renta (" + dias + " días × " + moneda.format(tarifaDia) + ")");
        lblRenta.setText(moneda.format(renta));
        lblSenalizacion.setText(moneda.format(senalizacion));
        lblCucharon.setText(moneda.format(cucharon));
        lblSubtotal.setText(moneda.format(subtotal));
        lblIva.setText(moneda.format(iva));
        lblTotal.setText(moneda.format(total));
        lblDeposito.setText(moneda.format(deposito));

        filaCucharon.setVisible(incluye);
        lblAccesorioOp.setVisible(incluye);
    }

    // UI
    private JLabel seccion(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(Color.GRAY.darker());
        l.setBorder(new EmptyBorder(0, 0, 10, 0));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JLabel etiquetaGris(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(Color.GRAY.darker());
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JLabel valorGrande(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JPanel fila(JLabel izq, JLabel der, boolean negrita, int tam) {
        int estilo = negrita ? Font.BOLD : Font.PLAIN;
        izq.setFont(new Font("SansSerif", estilo, tam));
        der.setFont(new Font("SansSerif", estilo, tam));
        der.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel f = new JPanel(new BorderLayout());
        f.setOpaque(false);
        f.setBorder(new EmptyBorder(3, 0, 3, 0));
        f.setAlignmentX(LEFT_ALIGNMENT);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        f.add(izq, BorderLayout.WEST);
        f.add(der, BorderLayout.EAST);
        return f;
    }

    private JSeparator linea() {
        JSeparator s = new JSeparator();
        s.setAlignmentX(LEFT_ALIGNMENT);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        return s;
    }

    private JButton botonOscuro(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(0x2B2B2B));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(140, 38));
        b.setMaximumSize(new Dimension(140, 38));
        return b;
    }

}
