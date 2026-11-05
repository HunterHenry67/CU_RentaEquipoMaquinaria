package presentacion.control;

import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import presentacion.mensajes.Mensaje;

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

    // El periodo que quedó al cambiarlo desde la propuesta: la captura de la solicitud también lo muestra
    public abstract void mostrarPeriodoSolicitud(PeriodoSolicitadoDTO periodo);

    public abstract void mostrarAnalisisRequerimiento(SolicitudRentaDTO solicitud);

    public abstract void mostrarSolicitudDatosFaltantes(SolicitudRentaDTO solicitud);

    public abstract void mostrarEquiposCompatibles(SolicitudRentaDTO solicitud, Map<EquipoDTO, String> evaluados, List<EquipoDTO> pedidos);

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

    public abstract void mostrarVerificacionCondicion(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion, String horarioEntrega);

    public abstract void mostrarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion);

    public abstract void mostrarOrdenRechazada(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion, CotizacionDTO cotizacion, String motivo);

    // Le pasa el mensaje a la ventana que se está viendo; los datos van en lugar de cada %s del texto
    public abstract void mostrarAviso(Mensaje mensaje, Object... datos);

    public abstract void mostrarMensaje(Mensaje mensaje, Object... datos);

    // Pregunta sí o no sobre la ventana visible; regresa true si el usuario dijo que sí
    public abstract boolean confirmar(Mensaje mensaje);
}
