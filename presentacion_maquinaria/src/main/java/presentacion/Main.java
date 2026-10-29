/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package presentacion;

import javax.swing.UIManager;
import presentacion.control.ContratacionRenta;
import presentacion.control.IContratacionRenta;

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
            IContratacionRenta control = new ContratacionRenta();
            control.abrirIniciarSesion();
        });
    }
    
}
