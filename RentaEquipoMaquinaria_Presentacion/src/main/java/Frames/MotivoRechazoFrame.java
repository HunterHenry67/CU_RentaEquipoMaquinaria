package Frames;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;


public class MotivoRechazoFrame extends JFrame{
    
    private IContratacionRenta contratacionRenta;

    private JPanel panelFondo;
    private JLabel lblTitulo;
    private JTextArea txtMotivo;
    private JScrollPane scrollMotivo;
    private JButton btnEnviar;
    private JButton btnRegresar;

    public MotivoRechazoFrame(IContratacionRenta contratacionRenta) {
        this.contratacionRenta = contratacionRenta;
        iniciar();
        this.setLocationRelativeTo(null);
    }

    private void iniciar() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Motivo del rechazo");
        setResizable(false);
        setSize(420, 320);

        panelFondo = new JPanel();
        panelFondo.setLayout(null);
        panelFondo.setBackground(new Color(217, 217, 217));
        setContentPane(panelFondo);

        lblTitulo = new JLabel("Motivo del rechazo");
        lblTitulo.setFont(new Font("Dialog", Font.BOLD, 24));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBounds(30, 20, 360, 35);
        panelFondo.add(lblTitulo);

        txtMotivo = new JTextArea();
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        txtMotivo.setFont(new Font("Dialog", Font.PLAIN, 14));
        scrollMotivo = new JScrollPane(txtMotivo);
        scrollMotivo.setBounds(30, 70, 360, 140);
        scrollMotivo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelFondo.add(scrollMotivo);

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(120, 120, 120));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFont(new Font("Dialog", Font.BOLD, 14));
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setOpaque(true);
        btnRegresar.setBounds(60, 235, 130, 40);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
    panelFondo.add(btnRegresar);
        
        btnEnviar = new JButton("Enviar");
        btnEnviar.setBackground(new Color(30, 30, 30));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Dialog", Font.BOLD, 14));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setOpaque(true);
        btnEnviar.setBounds(230, 235, 130, 40);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        panelFondo.add(btnEnviar);
    }

    // Se llama cada vez que se abre la pantalla para que el cuadro salga vacío
    public void limpiar() {
        txtMotivo.setText("");
    }
    
    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {
    contratacionRenta.abrirPropuestaRenta();
    }

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {
        contratacionRenta.abrirOrdenEntregaRechazada();
    }
    
}
