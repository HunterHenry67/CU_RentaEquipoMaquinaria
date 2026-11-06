package Subsistema;

import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.ResultadoContratacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Contrato del subsistema: lo que el Control de Aplicación le puede pedir sobre la contratación de
 * renta. Cómo se resuelve cada operación queda adentro del subsistema.
 */
public interface IAdminContratacionRenta {

    // Clientes
    public abstract List<ClienteDTO> obtenerClientes();

    public abstract List<ClienteDTO> buscarClientes(String filtro, String texto);

    public abstract ClienteDTO buscarClientePorRfc(String rfc);

    // Lanza IllegalArgumentException si ya hay un cliente con ese RFC
    public abstract ClienteDTO registrarClienteNuevo(ClienteDTO cliente);

    public abstract void actualizarCliente(ClienteDTO cliente);

    // Si el RFC ya lo tiene otro cliente (registrado es el que se encontró con ese RFC, o null)
    public abstract boolean rfcEnUso(ClienteDTO cliente, ClienteDTO registrado);

    public abstract boolean verificarRequisitosRenta(List<RequisitoRentaDTO> requisitos);

    // Solicitudes
    public abstract String siguienteFolio();

    public abstract SolicitudRentaDTO registrarSolicitud(SolicitudRentaDTO solicitud);

    // Lanza IllegalArgumentException si no hay ninguna solicitud con ese folio
    public abstract SolicitudRentaDTO consultarSolicitud(String folio);

    public abstract void actualizarSolicitud(SolicitudRentaDTO solicitud);

    public abstract ResultadoCancelacionDTO cancelarSolicitudRenta(SolicitudRentaDTO solicitud);

    public abstract List<RequisitoRentaDTO> obtenerRequisitosRenta();

    public abstract int diasRenta(PeriodoSolicitadoDTO periodo);

    // Equipos
    public abstract List<EquipoDTO> obtenerCatalogoEquipo();

    public abstract List<EquipoDTO> buscarEquipos(String texto);

    public abstract EquipoDTO buscarEquipoPorId(Long idEquipo);

    // Cada equipo del catálogo con el motivo por el que no cumple (null si cumple)
    public abstract Map<EquipoDTO, String> buscarEquiposCompatibles(List<RequerimientoTecnicoDTO> requerimientos);

    // Los accesorios que se proponen con un equipo: los indispensables más los opcionales elegidos
    public abstract List<AccesorioDTO> obtenerAccesoriosPropuestos(EquipoDTO equipo, List<AccesorioDTO> elegidos);

    // Unidades
    public abstract UnidadFisicaDTO buscarUnidadPorId(Long idUnidad);

    // Las unidades del equipo con su estado en el periodo y solo las ocupaciones que caen en él.
    // Lanza IllegalArgumentException si el equipo no tiene unidades
    public abstract List<UnidadFisicaDTO> verificarDisponibilidadUnidades(EquipoDTO equipo, PeriodoSolicitadoDTO periodo);

    public abstract boolean estaDisponible(UnidadFisicaDTO unidad);

    // Aparta la unidad para el periodo y la deja registrada
    public abstract UnidadFisicaDTO ocuparUnidad(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo);

    // Cotización. Las dos primeras lanzan IllegalStateException si el equipo no tiene tarifa registrada
    public abstract double tarifaDiaria(EquipoDTO equipo);

    public abstract CotizacionDTO generarCotizacion(String folio, EquipoDTO equipo, int dias, List<AccesorioDTO> accesorios);

    public abstract Double depositoGarantia(List<RequisitoRentaDTO> requisitos);

    public abstract CotizacionDTO registrarCotizacion(CotizacionDTO cotizacion);

    // null si la solicitud todavía no tiene cotización
    public abstract CotizacionDTO consultarCotizacion(String folio);

    public abstract void actualizarCotizacion(CotizacionDTO cotizacion);

    // Contratación
    public abstract List<AccesorioDTO> accesoriosAceptados(String tipoDecision, List<AccesorioDTO> propuestos);

    public abstract String estadoContratacion(String tipoDecision);

    public abstract String horarioEntrega();

    public abstract ResultadoContratacionDTO registrarContratacion(ContratacionRentaDTO contratacion);

    // null si la solicitud todavía no tiene contratación
    public abstract ContratacionRentaDTO consultarContratacion(String folio);

    public abstract OrdenEntregaDTO generarOrdenEntrega(SolicitudRentaDTO solicitud);
}
