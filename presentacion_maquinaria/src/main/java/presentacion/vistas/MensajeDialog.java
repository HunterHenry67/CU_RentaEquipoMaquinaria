package presentacion.vistas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Muestra sobre la pantalla visible los avisos, mensajes y confirmaciones que le pasa el Coordinador.
 */
public class MensajeDialog {

    private MensajeDialog() {
    }

    public static void mostrarAviso(JFrame ventana, String titulo, String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public static void mostrarMensaje(JFrame ventana, String titulo, String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmar(JFrame ventana, String titulo, String mensaje) {
        return JOptionPane.showConfirmDialog(ventana, mensaje, titulo, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }
}
