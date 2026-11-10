package presentacion.vistas;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import presentacion.controles.IContratacionRenta;

/**
 * Qué hace la X de las pantallas intermedias (en su .form no cierran solas: DO_NOTHING_ON_CLOSE).
 * Así cerrar una de ellas no cierra la aplicación: la ventana le pide al Control de Aplicación lo
 * mismo que pediría con sus botones. También muestra sobre la ventana los mensajes que le pasa el
 * Coordinador de Aplicación.
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

    // La X deja la operación: el control pregunta antes de volver al menú
    public static void cerrarAlMenu(JFrame ventana, IContratacionRenta contratacionRenta) {
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                contratacionRenta.dejarOperacion();
            }
        });
    }

}
