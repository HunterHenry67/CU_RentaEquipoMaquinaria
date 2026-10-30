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
import presentacion.vistas.AnalisisRequerimientoFrame;
import presentacion.vistas.BusquedaEquipoFrame;
import presentacion.vistas.CalendarioUnidadFrame;
import presentacion.vistas.CambiarPeriodoFrame;
import presentacion.vistas.CondicionesContratacionFrame;
import presentacion.vistas.CotizacionFrame;
import presentacion.vistas.EquiposCompatiblesFrame;
import presentacion.vistas.EvaluacionTecnicaFrame;
import presentacion.vistas.IdentificacionClienteFrame;
import presentacion.vistas.InicioSesionFrame;
import presentacion.vistas.MenuPrincipalFrame;
import presentacion.vistas.MotivoRechazoFrame;
import presentacion.vistas.OrdenEntregaFrame;
import presentacion.vistas.PropuestaPendienteFrame;
import presentacion.vistas.PropuestaRentaFrame;
import presentacion.vistas.RegistroClienteFrame;
import presentacion.vistas.RequisitosRentaFrame;
import presentacion.vistas.SeleccionAccesoriosFrame;
import presentacion.vistas.SinEquipoCompatibleFrame;
import presentacion.vistas.SolicitudDatosFaltantesFrame;
import presentacion.vistas.SolicitudRentaFrame;
import presentacion.vistas.UnidadMantenimientoFrame;
import presentacion.vistas.UnidadOcupadaFrame;
import presentacion.vistas.VerificacionCondicionFrame;
import presentacion.vistas.VerificacionInventarioFrame;

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

    /**
     * Arma la capa de presentación. El Control de Aplicación usa el almacén temporal y el negocio
     * simulado; cada ventana recibe el control para pedirle lo que necesite, y el Coordinador de
     * Aplicación recibe las ventanas para mostrarlas. El control le pide al coordinador cada cambio.
     */
    public static IContratacionRenta iniciar() {
        ContratacionRenta control = new ContratacionRenta(new AlmacenTemporal(), new NegocioSimulado());
        CoordinadorAplicacion coordinador = new CoordinadorAplicacion(
                new InicioSesionFrame(control),
                new MenuPrincipalFrame(control),
                new IdentificacionClienteFrame(control),
                new RegistroClienteFrame(control),
                new BusquedaEquipoFrame(control),
                new SolicitudRentaFrame(control),
                new AnalisisRequerimientoFrame(control),
                new SolicitudDatosFaltantesFrame(control),
                new EquiposCompatiblesFrame(control),
                new SinEquipoCompatibleFrame(control),
                new SeleccionAccesoriosFrame(control),
                new EvaluacionTecnicaFrame(control),
                new CambiarPeriodoFrame(control),
                new VerificacionInventarioFrame(control),
                new UnidadMantenimientoFrame(control),
                new UnidadOcupadaFrame(control),
                new CalendarioUnidadFrame(control),
                new CondicionesContratacionFrame(control),
                new CotizacionFrame(control),
                new RequisitosRentaFrame(control),
                new PropuestaRentaFrame(control),
                new PropuestaPendienteFrame(control),
                new MotivoRechazoFrame(control),
                new VerificacionCondicionFrame(control),
                new OrdenEntregaFrame(control));
        control.setCoordinador(coordinador);
        return control;
    }

}
