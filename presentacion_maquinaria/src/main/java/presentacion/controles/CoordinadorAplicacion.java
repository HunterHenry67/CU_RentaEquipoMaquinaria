package presentacion.controles;

import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.UIManager;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
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
import presentacion.vistas.MensajeDialog;

/**
 * Coordinador de Aplicación. Guarda las ventanas (se crean una sola vez, así cada una conserva lo
 * que se capturó cuando se regresa a ella), les pasa los DTO que deben mostrar y deja visible solo
 * la que toca. No valida, no usa el almacén temporal y no conoce al Control de Aplicación.
 */
public class CoordinadorAplicacion implements ICoordinadorAplicacion {

    private final InicioSesionView inicioSesionView;
    private final MenuPrincipalView menuPrincipalView;
    private final IdentificacionClienteView identificacionClienteView;
    private final RegistroClienteView registroClienteView;
    private final BusquedaEquipoView busquedaEquipoView;
    private final SolicitudRentaView solicitudRentaView;
    private final AnalisisRequerimientoView analisisRequerimientoView;
    private final SolicitudDatosFaltantesView solicitudDatosFaltantesView;
    private final EquiposCompatiblesView equiposCompatiblesView;
    private final SinEquipoCompatibleView sinEquipoCompatibleView;
    private final SeleccionAccesoriosView seleccionAccesoriosView;
    private final EvaluacionTecnicaView evaluacionTecnicaView;
    private final CambiarPeriodoView cambiarPeriodoView;
    private final VerificacionInventarioView verificacionInventarioView;
    private final UnidadMantenimientoView unidadMantenimientoView;
    private final UnidadOcupadaView unidadOcupadaView;
    private final CalendarioUnidadView calendarioUnidadView;
    private final CondicionesContratacionView condicionesContratacionView;
    private final CotizacionView cotizacionView;
    private final RequisitosRentaView requisitosRentaView;
    private final PropuestaRentaView propuestaRentaView;
    private final PropuestaPendienteView propuestaPendienteView;
    private final MotivoRechazoView motivoRechazoView;
    private final VerificacionCondicionView verificacionCondicionView;
    private final OrdenEntregaView ordenEntregaView;
    private final JFrame[] ventanas;

    /**
     * Recibe las ventanas ya creadas (las crea Main, cada una con el Control de Aplicación) y solo
     * guarda sus referencias: el coordinador no conoce al control ni al almacén temporal.
     */
    public CoordinadorAplicacion(JFrame... ventanas) {
        this.ventanas = ventanas;
        // Los avisos en español aunque la computadora esté en otro idioma
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "Aceptar");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        inicioSesionView = ventana(InicioSesionView.class);
        menuPrincipalView = ventana(MenuPrincipalView.class);
        identificacionClienteView = ventana(IdentificacionClienteView.class);
        registroClienteView = ventana(RegistroClienteView.class);
        busquedaEquipoView = ventana(BusquedaEquipoView.class);
        solicitudRentaView = ventana(SolicitudRentaView.class);
        analisisRequerimientoView = ventana(AnalisisRequerimientoView.class);
        solicitudDatosFaltantesView = ventana(SolicitudDatosFaltantesView.class);
        equiposCompatiblesView = ventana(EquiposCompatiblesView.class);
        sinEquipoCompatibleView = ventana(SinEquipoCompatibleView.class);
        seleccionAccesoriosView = ventana(SeleccionAccesoriosView.class);
        evaluacionTecnicaView = ventana(EvaluacionTecnicaView.class);
        cambiarPeriodoView = ventana(CambiarPeriodoView.class);
        verificacionInventarioView = ventana(VerificacionInventarioView.class);
        unidadMantenimientoView = ventana(UnidadMantenimientoView.class);
        unidadOcupadaView = ventana(UnidadOcupadaView.class);
        calendarioUnidadView = ventana(CalendarioUnidadView.class);
        condicionesContratacionView = ventana(CondicionesContratacionView.class);
        cotizacionView = ventana(CotizacionView.class);
        requisitosRentaView = ventana(RequisitosRentaView.class);
        propuestaRentaView = ventana(PropuestaRentaView.class);
        propuestaPendienteView = ventana(PropuestaPendienteView.class);
        motivoRechazoView = ventana(MotivoRechazoView.class);
        verificacionCondicionView = ventana(VerificacionCondicionView.class);
        ordenEntregaView = ventana(OrdenEntregaView.class);
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
        inicioSesionView.limpiar();
        mostrar(inicioSesionView);
    }

    @Override
    public void mostrarMenuPrincipal() {
        menuPrincipalView.limpiar();
        mostrar(menuPrincipalView);
    }

    @Override
    public void mostrarIdentificacionCliente(List<ClienteDTO> clientes) {
        identificacionClienteView.cargarClientes(clientes);
        mostrar(identificacionClienteView);
    }

    @Override
    public void mostrarClientesEncontrados(List<ClienteDTO> clientes) {
        identificacionClienteView.cargarClientesEnTabla(clientes);
    }

    @Override
    public void mostrarRegistroCliente(ClienteDTO cliente) {
        registroClienteView.cargarDatosRegistroCliente(cliente);
        mostrar(registroClienteView);
    }

    @Override
    public void mostrarBusquedaEquipo(List<EquipoDTO> catalogo) {
        busquedaEquipoView.cargarEquiposEnTabla(catalogo);
        mostrar(busquedaEquipoView);
    }

    @Override
    public void mostrarEquiposEncontrados(List<EquipoDTO> equipos) {
        busquedaEquipoView.mostrarEquipos(equipos);
    }

    @Override
    public void mostrarSolicitudNueva(ClienteDTO cliente, String folio) {
        solicitudRentaView.limpiar();
        solicitudRentaView.cargarDatosCliente(cliente);
        solicitudRentaView.cargarFolio(folio);
        mostrar(solicitudRentaView);
    }

    @Override
    public void mostrarSolicitudRenta() {
        solicitudRentaView.mostrarCaptura();
        mostrar(solicitudRentaView);
    }

    @Override
    public void mostrarCancelacionSolicitud() {
        solicitudRentaView.mostrarCancelacion();
        mostrar(solicitudRentaView);
    }

    @Override
    public void mostrarEquiposSolicitados(List<EquipoSolicitadoDTO> equipos) {
        solicitudRentaView.cargarEquiposSolicitadosEnTabla(equipos);
    }

    @Override
    public void mostrarPeriodoSolicitud(PeriodoSolicitadoDTO periodo) {
        solicitudRentaView.cargarPeriodo(periodo);
    }

    @Override
    public void mostrarTerrenoSolicitud(String terreno) {
        solicitudRentaView.cargarTerreno(terreno);
    }

    @Override
    public void mostrarAnalisisRequerimiento(SolicitudRentaDTO solicitud) {
        analisisRequerimientoView.cargarSolicitud(solicitud);
        mostrar(analisisRequerimientoView);
    }

    @Override
    public void mostrarSolicitudDatosFaltantes(SolicitudRentaDTO solicitud) {
        solicitudDatosFaltantesView.cargarSolicitud(solicitud);
        mostrar(solicitudDatosFaltantesView);
    }

    @Override
    public void mostrarEquiposCompatibles(SolicitudRentaDTO solicitud, Map<EquipoDTO, String> evaluados) {
        equiposCompatiblesView.cargarEquipos(solicitud, evaluados);
        mostrar(equiposCompatiblesView);
    }

    @Override
    public void mostrarSinEquipoCompatible(SolicitudRentaDTO solicitud) {
        sinEquipoCompatibleView.cargarSolicitud(solicitud);
        mostrar(sinEquipoCompatibleView);
    }

    @Override
    public void mostrarSeleccionAccesorios(SolicitudRentaDTO solicitud, List<AccesorioDTO> accesorios) {
        seleccionAccesoriosView.cargarAccesorios(solicitud, accesorios);
        mostrar(seleccionAccesoriosView);
    }

    @Override
    public void mostrarEvaluacionTecnica(SolicitudRentaDTO solicitud) {
        evaluacionTecnicaView.cargarEvaluacion(solicitud);
        mostrar(evaluacionTecnicaView);
    }

    @Override
    public void mostrarCambiarPeriodo(SolicitudRentaDTO solicitud) {
        cambiarPeriodoView.cargarPeriodo(solicitud);
        mostrar(cambiarPeriodoView);
    }

    @Override
    public void mostrarVerificacionInventario(SolicitudRentaDTO solicitud, List<UnidadFisicaDTO> unidades) {
        verificacionInventarioView.cargarSolicitud(solicitud, unidades);
        mostrar(verificacionInventarioView);
    }

    @Override
    public void mostrarUnidadMantenimiento(SolicitudRentaDTO solicitud, UnidadFisicaDTO unidad) {
        unidadMantenimientoView.cargarMantenimiento(solicitud, unidad);
        mostrar(unidadMantenimientoView);
    }

    @Override
    public void mostrarUnidadOcupada(SolicitudRentaDTO solicitud, UnidadFisicaDTO unidad) {
        unidadOcupadaView.cargarOcupada(solicitud, unidad);
        mostrar(unidadOcupadaView);
    }

    @Override
    public void mostrarCalendarioUnidad(SolicitudRentaDTO solicitud) {
        calendarioUnidadView.cargarDatos(solicitud);
        mostrar(calendarioUnidadView);
    }

    @Override
    public void mostrarCondicionesContratacion(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        condicionesContratacionView.cargarCondiciones(solicitud, cotizacion);
        mostrar(condicionesContratacionView);
    }

    @Override
    public void mostrarCotizacion(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        cotizacionView.cargarCotizacion(solicitud, cotizacion);
        mostrar(cotizacionView);
    }

    @Override
    public void mostrarRequisitosRenta(SolicitudRentaDTO solicitud) {
        requisitosRentaView.cargarRequisitos(solicitud);
        mostrar(requisitosRentaView);
    }

    @Override
    public void mostrarRequisitosPendientes() {
        requisitosRentaView.mostrarPendientes(true);
        mostrar(requisitosRentaView);
    }

    @Override
    public void mostrarPropuestaRenta(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        propuestaRentaView.cargarDatos(solicitud, cotizacion);
        mostrar(propuestaRentaView);
    }

    @Override
    public void mostrarPropuestaPendiente(SolicitudRentaDTO solicitud) {
        propuestaPendienteView.cargarDatos(solicitud);
        mostrar(propuestaPendienteView);
    }

    @Override
    public void mostrarMotivoRechazo() {
        motivoRechazoView.limpiar();
        mostrar(motivoRechazoView);
    }

    @Override
    public void mostrarVerificacionCondicion(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion, String horarioEntrega) {
        verificacionCondicionView.cargarDatos(solicitud, contratacion, cotizacion, horarioEntrega);
        mostrar(verificacionCondicionView);
    }

    @Override
    public void mostrarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion) {
        ordenEntregaView.cargarDatos(solicitud, contratacion, cotizacion);
        mostrar(ordenEntregaView);
    }

    @Override
    public void mostrarOrdenRechazada(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion, String motivo) {
        ordenEntregaView.cargarDatosRechazada(solicitud, contratacion, cotizacion, motivo);
        mostrar(ordenEntregaView);
    }

    @Override
    public void mostrarAviso(Mensaje mensaje, Object... datos) {
        MensajeDialog.mostrarAviso(ventanaVisible(), mensaje.getTitulo(), mensaje.getTexto(datos));
    }

    @Override
    public void mostrarMensaje(Mensaje mensaje, Object... datos) {
        MensajeDialog.mostrarMensaje(ventanaVisible(), mensaje.getTitulo(), mensaje.getTexto(datos));
    }

    @Override
    public boolean confirmar(Mensaje mensaje) {
        return MensajeDialog.confirmar(ventanaVisible(), mensaje.getTitulo(), mensaje.getTexto());
    }

    private JFrame ventanaVisible() {
        for (JFrame v : ventanas) {
            if (v.isShowing()) {
                return v;
            }
        }
        return null;
    }
}
