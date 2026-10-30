package presentacion.vistas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import presentacion.control.IContratacionRenta;

/**
 * Qué hace la X de las pantallas intermedias (en su .form no cierran solas: DO_NOTHING_ON_CLOSE).
 * Así cerrar una de ellas no cierra la aplicación: la ventana le pide al Control de Aplicación lo
 * mismo que pediría con sus botones.
 */
public class Ventanas {

    private Ventanas() {
    }

    // La X hace lo mismo que el botón para regresar o cancelar de la pantalla
    public static void cerrarCon(JFrame ventana, AbstractButton boton) {
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                boton.doClick();
            }
        });
    }

    // La pantalla no tiene botón para regresar: la X pregunta si se deja la operación y vuelve al menú
    public static void cerrarAlMenu(JFrame ventana, IContratacionRenta contratacionRenta) {
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                int respuesta = JOptionPane.showConfirmDialog(ventana, "¿Dejar la operación y volver al menú principal?",
                        ventana.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    contratacionRenta.abrirMenuPrincipal();
                }
            }
        });
    }
}
