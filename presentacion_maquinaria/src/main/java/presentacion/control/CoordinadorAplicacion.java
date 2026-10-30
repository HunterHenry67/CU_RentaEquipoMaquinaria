package presentacion.control;

import java.awt.Window;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
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
 * Coordinador de Aplicación. Guarda las ventanas (se crean una sola vez, así cada una conserva lo
 * que se capturó cuando se regresa a ella), les pasa los DTO que deben mostrar y deja visible solo
 * la que toca. No valida, no usa el almacén temporal y no conoce al Control de Aplicación.
 */
public class CoordinadorAplicacion implements ICoordinadorAplicacion {

    private final InicioSesionFrame inicioSesionFrame;
    private final MenuPrincipalFrame menuPrincipalFrame;
    private final IdentificacionClienteFrame identificacionClienteFrame;
    private final RegistroClienteFrame registroClienteFrame;
    private final BusquedaEquipoFrame busquedaEquipoFrame;
    private final SolicitudRentaFrame solicitudRentaFrame;
    private final AnalisisRequerimientoFrame analisisRequerimientoFrame;
    private final SolicitudDatosFaltantesFrame solicitudDatosFaltantesFrame;
    private final EquiposCompatiblesFrame equiposCompatiblesFrame;
    private final SinEquipoCompatibleFrame sinEquipoCompatibleFrame;
    private final SeleccionAccesoriosFrame seleccionAccesoriosFrame;
    private final EvaluacionTecnicaFrame evaluacionTecnicaFrame;
    private final CambiarPeriodoFrame cambiarPeriodoFrame;
    private final VerificacionInventarioFrame verificacionInventarioFrame;
    private final UnidadMantenimientoFrame unidadMantenimientoFrame;
    private final UnidadOcupadaFrame unidadOcupadaFrame;
    private final CalendarioUnidadFrame calendarioUnidadFrame;
    private final CondicionesContratacionFrame condicionesContratacionFrame;
    private final CotizacionFrame cotizacionFrame;
    private final RequisitosRentaFrame requisitosRentaFrame;
    private final PropuestaRentaFrame propuestaRentaFrame;
    private final PropuestaPendienteFrame propuestaPendienteFrame;
    private final MotivoRechazoFrame motivoRechazoFrame;
    private final VerificacionCondicionFrame verificacionCondicionFrame;
    private final OrdenEntregaFrame ordenEntregaFrame;
    private final JFrame[] ventanas;

    /**
     * Recibe las ventanas ya creadas (las crea Main, cada una con el Control de Aplicación) y solo
     * guarda sus referencias: el coordinador no conoce al control ni al almacén temporal.
     */
    public CoordinadorAplicacion(JFrame... ventanas) {
        this.ventanas = ventanas;
        inicioSesionFrame = ventana(InicioSesionFrame.class);
        menuPrincipalFrame = ventana(MenuPrincipalFrame.class);
        identificacionClienteFrame = ventana(IdentificacionClienteFrame.class);
        registroClienteFrame = ventana(RegistroClienteFrame.class);
        busquedaEquipoFrame = ventana(BusquedaEquipoFrame.class);
        solicitudRentaFrame = ventana(SolicitudRentaFrame.class);
        analisisRequerimientoFrame = ventana(AnalisisRequerimientoFrame.class);
        solicitudDatosFaltantesFrame = ventana(SolicitudDatosFaltantesFrame.class);
        equiposCompatiblesFrame = ventana(EquiposCompatiblesFrame.class);
        sinEquipoCompatibleFrame = ventana(SinEquipoCompatibleFrame.class);
        seleccionAccesoriosFrame = ventana(SeleccionAccesoriosFrame.class);
        evaluacionTecnicaFrame = ventana(EvaluacionTecnicaFrame.class);
        cambiarPeriodoFrame = ventana(CambiarPeriodoFrame.class);
        verificacionInventarioFrame = ventana(VerificacionInventarioFrame.class);
        unidadMantenimientoFrame = ventana(UnidadMantenimientoFrame.class);
        unidadOcupadaFrame = ventana(UnidadOcupadaFrame.class);
        calendarioUnidadFrame = ventana(CalendarioUnidadFrame.class);
        condicionesContratacionFrame = ventana(CondicionesContratacionFrame.class);
        cotizacionFrame = ventana(CotizacionFrame.class);
        requisitosRentaFrame = ventana(RequisitosRentaFrame.class);
        propuestaRentaFrame = ventana(PropuestaRentaFrame.class);
        propuestaPendienteFrame = ventana(PropuestaPendienteFrame.class);
        motivoRechazoFrame = ventana(MotivoRechazoFrame.class);
        verificacionCondicionFrame = ventana(VerificacionCondicionFrame.class);
        ordenEntregaFrame = ventana(OrdenEntregaFrame.class);
    }

    private <T extends JFrame> T ventana(Class<T> tipo) {
        for (JFrame v : ventanas) {
            if (tipo.isInstance(v)) {
                return tipo.cast(v);
            }
        }
        throw new IllegalArgumentException("Falta la ventana " + tipo.getSimpleName());
    }

    // Deja a la vista solo la ventana que sigue
    private void mostrar(JFrame ventana) {
        for (JFrame v : ventanas) {
            if (v != ventana) {
                v.setVisible(false);
            }
        }
        ventana.setVisible(true);
    }

    @Override
    public void mostrarInicioSesion() {
        mostrar(inicioSesionFrame);
    }

    @Override
    public void mostrarMenuPrincipal() {
        mostrar(menuPrincipalFrame);
    }

    @Override
    public void mostrarIdentificacionCliente(List<ClienteDTO> clientes) {
        identificacionClienteFrame.cargarClientes(clientes);
        mostrar(identificacionClienteFrame);
    }

    @Override
    public void mostrarClientesEncontrados(List<ClienteDTO> clientes) {
        identificacionClienteFrame.cargarClientesEnTabla(clientes);
    }

    @Override
    public void mostrarRegistroCliente(ClienteDTO cliente) {
        registroClienteFrame.cargarDatosRegistroCliente(cliente);
        mostrar(registroClienteFrame);
    }

    @Override
    public void mostrarBusquedaEquipo(List<EquipoDTO> catalogo) {
        busquedaEquipoFrame.cargarEquiposEnTabla(catalogo);
        mostrar(busquedaEquipoFrame);
    }

    @Override
    public void mostrarEquiposEncontrados(List<EquipoDTO> equipos) {
        busquedaEquipoFrame.mostrarEquipos(equipos);
    }

    @Override
    public void mostrarSolicitudNueva(ClienteDTO cliente, String folio) {
        solicitudRentaFrame.limpiar();
        solicitudRentaFrame.cargarDatosCliente(cliente);
        solicitudRentaFrame.cargarFolio(folio);
        mostrar(solicitudRentaFrame);
    }

    @Override
    public void mostrarSolicitudRenta() {
        mostrar(solicitudRentaFrame);
    }

    @Override
    public void mostrarCancelacionSolicitud() {
        solicitudRentaFrame.mostrarCancelacion();
        mostrar(solicitudRentaFrame);
    }

    @Override
    public void mostrarEquiposSolicitados(List<EquipoSolicitadoDTO> equipos) {
        solicitudRentaFrame.cargarEquiposSolicitadosEnTabla(equipos);
    }

    @Override
    public void mostrarAnalisisRequerimiento(SolicitudRentaDTO solicitud) {
        analisisRequerimientoFrame.cargarSolicitud(solicitud);
        mostrar(analisisRequerimientoFrame);
    }

    @Override
    public void mostrarSolicitudDatosFaltantes(SolicitudRentaDTO solicitud) {
        solicitudDatosFaltantesFrame.cargarSolicitud(solicitud);
        mostrar(solicitudDatosFaltantesFrame);
    }

    @Override
    public void mostrarEquiposCompatibles(SolicitudRentaDTO solicitud, Map<EquipoDTO, String> evaluados, EquipoDTO pedido) {
        equiposCompatiblesFrame.cargarEquipos(solicitud, evaluados, pedido);
        mostrar(equiposCompatiblesFrame);
    }

    @Override
    public void mostrarSinEquipoCompatible(SolicitudRentaDTO solicitud) {
        sinEquipoCompatibleFrame.cargarSolicitud(solicitud);
        mostrar(sinEquipoCompatibleFrame);
    }

    @Override
    public void mostrarSeleccionAccesorios(SolicitudRentaDTO solicitud, List<AccesorioDTO> accesorios) {
        seleccionAccesoriosFrame.cargarAccesorios(solicitud, accesorios);
        mostrar(seleccionAccesoriosFrame);
    }

    @Override
    public void mostrarEvaluacionTecnica(SolicitudRentaDTO solicitud) {
        evaluacionTecnicaFrame.cargarEvaluacion(solicitud);
        mostrar(evaluacionTecnicaFrame);
    }

    @Override
    public void mostrarCambiarPeriodo(SolicitudRentaDTO solicitud) {
        cambiarPeriodoFrame.cargarPeriodo(solicitud);
        mostrar(cambiarPeriodoFrame);
    }

    @Override
    public void mostrarVerificacionInventario(SolicitudRentaDTO solicitud, List<UnidadFisicaDTO> unidades) {
        verificacionInventarioFrame.cargarSolicitud(solicitud, unidades);
        mostrar(verificacionInventarioFrame);
    }

    @Override
    public void mostrarUnidadMantenimiento(SolicitudRentaDTO solicitud, UnidadFisicaDTO unidad) {
        unidadMantenimientoFrame.cargarMantenimiento(solicitud, unidad);
        mostrar(unidadMantenimientoFrame);
    }

    @Override
    public void mostrarUnidadOcupada(SolicitudRentaDTO solicitud, UnidadFisicaDTO unidad) {
        unidadOcupadaFrame.cargarOcupada(solicitud, unidad);
        mostrar(unidadOcupadaFrame);
    }

    @Override
    public void mostrarCalendarioUnidad(SolicitudRentaDTO solicitud) {
        calendarioUnidadFrame.cargarDatos(solicitud);
        mostrar(calendarioUnidadFrame);
    }

    @Override
    public void mostrarCondicionesContratacion(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        condicionesContratacionFrame.cargarCondiciones(solicitud, cotizacion);
        mostrar(condicionesContratacionFrame);
    }

    @Override
    public void mostrarCotizacion(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        cotizacionFrame.cargarCotizacion(solicitud, cotizacion);
        mostrar(cotizacionFrame);
    }

    @Override
    public void mostrarRequisitosRenta(SolicitudRentaDTO solicitud) {
        requisitosRentaFrame.cargarRequisitos(solicitud);
        mostrar(requisitosRentaFrame);
    }

    @Override
    public void mostrarRequisitosPendientes() {
        requisitosRentaFrame.mostrarPendientes(true);
        mostrar(requisitosRentaFrame);
    }

    @Override
    public void mostrarPropuestaRenta(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        propuestaRentaFrame.cargarDatos(solicitud, cotizacion);
        mostrar(propuestaRentaFrame);
    }

    @Override
    public void mostrarPropuestaPendiente(SolicitudRentaDTO solicitud) {
        propuestaPendienteFrame.cargarDatos(solicitud);
        mostrar(propuestaPendienteFrame);
    }

    @Override
    public void mostrarMotivoRechazo() {
        motivoRechazoFrame.limpiar();
        mostrar(motivoRechazoFrame);
    }

    @Override
    public void mostrarVerificacionCondicion(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion) {
        verificacionCondicionFrame.cargarDatos(solicitud, contratacion, cotizacion);
        mostrar(verificacionCondicionFrame);
    }

    @Override
    public void mostrarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion) {
        ordenEntregaFrame.cargarDatos(solicitud, contratacion, cotizacion);
        mostrar(ordenEntregaFrame);
    }

    @Override
    public void mostrarOrdenRechazada(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion, String motivo) {
        ordenEntregaFrame.cargarDatosRechazada(solicitud, contratacion, cotizacion, motivo);
        mostrar(ordenEntregaFrame);
    }

    @Override
    public void mostrarAviso(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(ventanaVisible(), mensaje, titulo, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void mostrarMensaje(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(ventanaVisible(), mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public boolean confirmar(String titulo, String mensaje) {
        return JOptionPane.showConfirmDialog(ventanaVisible(), mensaje, titulo, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    private Window ventanaVisible() {
        for (JFrame v : ventanas) {
            if (v.isShowing()) {
                return v;
            }
        }
        return null;
    }
}
