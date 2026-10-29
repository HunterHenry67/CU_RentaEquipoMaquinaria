/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package presentacion;

import almacentemporal.AlmacenTemporal;
import javax.swing.UIManager;
import presentacion.control.ContratacionRenta;
import presentacion.control.CoordinadorAplicacion;
import presentacion.control.IContratacionRenta;
import presentacion.simulacion.NegocioSimulado;

/**
 *
 * @author BALAMRUSH
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {     
            // El mismo aspecto en Windows y en Mac: con el de Mac los botones de color salen en blanco
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("No se pudo cambiar el aspecto: " + ex.getMessage());
            }
            iniciar().abrirIniciarSesion();
        });
    }

    // Arma la capa de presentación: el control usa el almacén temporal y el negocio simulado,
    // y el coordinador es el único que abre y cierra pantallas
    public static IContratacionRenta iniciar() {
        ContratacionRenta control = new ContratacionRenta(new AlmacenTemporal(), new NegocioSimulado());
        control.setCoordinador(new CoordinadorAplicacion(control));
        return control;
    }

}
