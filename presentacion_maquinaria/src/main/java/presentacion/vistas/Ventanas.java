package presentacion.vistas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
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

    // La pantalla no tiene botón para regresar: el control pregunta si se deja la operación
    public static void cerrarAlMenu(JFrame ventana, IContratacionRenta contratacionRenta) {
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                contratacionRenta.dejarOperacion();
            }
        });
    }
}
