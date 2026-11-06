package presentacion;

import java.util.Locale;
import javax.swing.UIManager;
import presentacion.controles.ControlContratacionRenta;
import presentacion.controles.CoordinadorAplicacion;
import presentacion.controles.IContratacionRenta;
import presentacion.vistas.AnalisisRequerimientoView;
import presentacion.vistas.BusquedaEquipoView;
import presentacion.vistas.CalendarioUnidadView;
import presentacion.vistas.CambiarPeriodoView;
import presentacion.vistas.CondicionesContratacionView;
import presentacion.vistas.CotizacionView;
import presentacion.vistas.EquiposCompatiblesView;
import presentacion.vistas.EvaluacionTecnicaView;
import presentacion.vistas.IdentificacionClienteView;
import presentacion.vistas.InicioSesionView;
import presentacion.vistas.MenuPrincipalView;
import presentacion.vistas.MotivoRechazoView;
import presentacion.vistas.OrdenEntregaView;
import presentacion.vistas.PropuestaPendienteView;
import presentacion.vistas.PropuestaRentaView;
import presentacion.vistas.RegistroClienteView;
import presentacion.vistas.RequisitosRentaView;
import presentacion.vistas.SeleccionAccesoriosView;
import presentacion.vistas.SinEquipoCompatibleView;
import presentacion.vistas.SolicitudDatosFaltantesView;
import presentacion.vistas.SolicitudRentaView;
import presentacion.vistas.UnidadMantenimientoView;
import presentacion.vistas.UnidadOcupadaView;
import presentacion.vistas.VerificacionCondicionView;
import presentacion.vistas.VerificacionInventarioView;
import Subsistema.AdminContratacionRenta;
import subsistemaInicioSesion.SubsistemaInicioSesion;

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
            prepararAspecto();
            iniciar().abrirIniciarSesion();
        });
    }

    /**
     * El mismo aspecto en Windows y en Mac (con el de Mac los botones de color salen en blanco) y
     * el calendario y el selector de archivos en español.
     */
    public static void prepararAspecto() {
        Locale.setDefault(Locale.forLanguageTag("es-MX"));
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("No se pudo cambiar el aspecto: " + ex.getMessage());
        }
    }

    /**
     * Arma la capa de presentación. El Control de Aplicación le pide lo del negocio a los dos
     * subsistemas; cada ventana recibe el control para pedirle lo que necesite, y el Coordinador de
     * Aplicación recibe las ventanas para mostrarlas. El control le pide al coordinador cada cambio.
     */
    public static IContratacionRenta iniciar() {
        ControlContratacionRenta control = new ControlContratacionRenta(new AdminContratacionRenta(), new SubsistemaInicioSesion());
        CoordinadorAplicacion coordinador = new CoordinadorAplicacion(
                new InicioSesionView(control),
                new MenuPrincipalView(control),
                new IdentificacionClienteView(control),
                new RegistroClienteView(control),
                new BusquedaEquipoView(control),
                new SolicitudRentaView(control),
                new AnalisisRequerimientoView(control),
                new SolicitudDatosFaltantesView(control),
                new EquiposCompatiblesView(control),
                new SinEquipoCompatibleView(control),
                new SeleccionAccesoriosView(control),
                new EvaluacionTecnicaView(control),
                new CambiarPeriodoView(control),
                new VerificacionInventarioView(control),
                new UnidadMantenimientoView(control),
                new UnidadOcupadaView(control),
                new CalendarioUnidadView(control),
                new CondicionesContratacionView(control),
                new CotizacionView(control),
                new RequisitosRentaView(control),
                new PropuestaRentaView(control),
                new PropuestaPendienteView(control),
                new MotivoRechazoView(control),
                new VerificacionCondicionView(control),
                new OrdenEntregaView(control));
        control.setCoordinador(coordinador);
        return control;
    }

}
