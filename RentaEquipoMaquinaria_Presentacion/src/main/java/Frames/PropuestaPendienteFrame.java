package Frames;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PropuestaPendienteFrame extends JFrame {

    private IContratacionRenta contratacionRenta;

    private JLabel lblTitulo, lblMensaje;
    private JButton btnAceptar, btnNoContinuar;

    public PropuestaPendienteFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;

        setTitle("Accesorio indispensable");
        setSize(800, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(217, 217, 217));

        crearComponentes();
        agregarEventos();
    }

    private void crearComponentes() {
        // Título
        lblTitulo = new JLabel("El accesorio indispensable no se puede quitar", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setBounds(0, 20, 800, 40);
        add(lblTitulo);

        // Mensaje
        lblMensaje = new JLabel("<html>El kit de señalización es indispensable para operar la excavadora. "
                + "Sin él, la contratación no cumple las condiciones necesarias "
                + "y no se puede generar una orden de entrega.</html>");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 26));
        lblMensaje.setBounds(45, 80, 710, 190);
        add(lblMensaje);

        // Botones
        btnAceptar = new JButton("Aceptar el accesorio");
        btnAceptar.setBackground(new Color(43, 43, 43));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setBounds(95, 290, 170, 48);
        add(btnAceptar);

        btnNoContinuar = new JButton("No continuar");
        btnNoContinuar.setBackground(new Color(238, 34, 34));
        btnNoContinuar.setForeground(Color.WHITE);
        btnNoContinuar.setBounds(560, 290, 140, 48);
        add(btnNoContinuar);
    }

    private void agregarEventos() {
        // Sin cucharón, orden finalizada
        btnAceptar.addActionListener(e -> contratacionRenta.abrirOrdenEntrega(false, true));

        // Con cucharón, orden pendiente
        btnNoContinuar.addActionListener(e -> contratacionRenta.abrirOrdenEntrega(true, false));
    }

}
