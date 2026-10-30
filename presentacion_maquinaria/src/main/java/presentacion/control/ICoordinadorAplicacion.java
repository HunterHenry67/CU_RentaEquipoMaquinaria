package presentacion.control;

import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Coordinador de Aplicación: administra las ventanas. Muestra, oculta y actualiza cada pantalla
 * con los DTO que le da el Control de Aplicación. No valida, no consulta el almacén temporal y
 * no sabe nada del negocio.
 */
public interface ICoordinadorAplicacion {

    public abstract void mostrarInicioSesion();

    public abstract void mostrarMenuPrincipal();

    public abstract void mostrarIdentificacionCliente(List<ClienteDTO> clientes);

    public abstract void mostrarClientesEncontrados(List<ClienteDTO> clientes);

    public abstract void mostrarRegistroCliente(ClienteDTO cliente);

    public abstract void mostrarBusquedaEquipo(List<EquipoDTO> catalogo);

    public abstract void mostrarEquiposEncontrados(List<EquipoDTO> equipos);

    // Una solicitud en blanco para el cliente, con su folio
    public abstract void mostrarSolicitudNueva(ClienteDTO cliente, String folio);

    public abstract void mostrarSolicitudRenta();

    // La parte de la pantalla de solicitud donde se escribe la razón de la cancelación
    public abstract void mostrarCancelacionSolicitud();

    public abstract void mostrarEquiposSolicitados(List<EquipoSolicitadoDTO> equipos);

    public abstract void mostrarAnalisisRequerimiento(SolicitudRentaDTO solicitud);

    public abstract void mostrarSolicitudDatosFaltantes(SolicitudRentaDTO solicitud);

    public abstract void mostrarEquiposCompatibles(SolicitudRentaDTO solicitud, Map<EquipoDTO, String> evaluados, EquipoDTO pedido);

    public abstract void mostrarSinEquipoCompatible(SolicitudRentaDTO solicitud);

    public abstract void mostrarSeleccionAccesorios(SolicitudRentaDTO solicitud, List<AccesorioDTO> accesorios);

    public abstract void mostrarEvaluacionTecnica(SolicitudRentaDTO solicitud);

    public abstract void mostrarCambiarPeriodo(SolicitudRentaDTO solicitud);

    public abstract void mostrarVerificacionInventario(SolicitudRentaDTO solicitud, List<UnidadFisicaDTO> unidades);

    public abstract void mostrarUnidadMantenimiento(SolicitudRentaDTO solicitud, UnidadFisicaDTO unidad);

    public abstract void mostrarUnidadOcupada(SolicitudRentaDTO solicitud, UnidadFisicaDTO unidad);

    public abstract void mostrarCalendarioUnidad(SolicitudRentaDTO solicitud);

    public abstract void mostrarCondicionesContratacion(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion);

    public abstract void mostrarCotizacion(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion);

    public abstract void mostrarRequisitosRenta(SolicitudRentaDTO solicitud);

    // Requisitos con algo obligatorio pendiente: la pantalla pasa a su estado de error
    public abstract void mostrarRequisitosPendientes();

    public abstract void mostrarPropuestaRenta(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion);

    public abstract void mostrarPropuestaPendiente(SolicitudRentaDTO solicitud);

    public abstract void mostrarMotivoRechazo();

    public abstract void mostrarVerificacionCondicion(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion);

    public abstract void mostrarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion);

    public abstract void mostrarOrdenRechazada(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion, String motivo);

    // Avisos sobre la pantalla que se está viendo
    public abstract void mostrarAviso(String titulo, String mensaje);

    public abstract void mostrarMensaje(String titulo, String mensaje);

    // Pregunta sí o no sobre la ventana visible; regresa true si el usuario dijo que sí
    public abstract boolean confirmar(String titulo, String mensaje);
}
