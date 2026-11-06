package almacentemporal;

import java.util.List;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Almacén temporal: guarda la información mientras la aplicación está abierta, en lugar de la
 * base de datos. Solo registra, busca, consulta y actualiza DTO; no decide nada del negocio.
 *
 * @author BALAMRUSH
 */
public interface IAlmacenTemporal {

    // Clientes
    public abstract List<ClienteDTO> obtenerClientes();

    public abstract List<ClienteDTO> buscarClientes(String filtro, String texto);

    public abstract ClienteDTO buscarClientePorRfc(String rfc);

    public abstract ClienteDTO registrarCliente(ClienteDTO cliente);

    public abstract void actualizarCliente(ClienteDTO cliente);

    // Equipos y unidades
    public abstract List<EquipoDTO> obtenerCatalogoEquipo();

    public abstract List<EquipoDTO> buscarEquipos(String texto);

    public abstract EquipoDTO buscarEquipoPorId(Long idEquipo);

    public abstract List<UnidadFisicaDTO> obtenerUnidadesDeEquipo(Long idEquipo);

    public abstract UnidadFisicaDTO buscarUnidadPorId(Long idUnidad);

    public abstract void actualizarUnidad(UnidadFisicaDTO unidad);

    // Solicitudes
    public abstract String siguienteFolio();

    public abstract SolicitudRentaDTO registrarSolicitud(SolicitudRentaDTO solicitud);

    public abstract void actualizarSolicitud(SolicitudRentaDTO solicitud);

    public abstract SolicitudRentaDTO buscarSolicitudPorFolio(String folio);

    public abstract List<SolicitudRentaDTO> obtenerSolicitudes();

    // Cotizaciones y contrataciones
    public abstract CotizacionDTO registrarCotizacion(CotizacionDTO cotizacion);

    public abstract void actualizarCotizacion(CotizacionDTO cotizacion);

    public abstract CotizacionDTO buscarCotizacionPorFolio(String folio);

    public abstract ContratacionRentaDTO registrarContratacion(ContratacionRentaDTO contratacion);

    public abstract ContratacionRentaDTO buscarContratacionPorFolio(String folio);

    public abstract List<ContratacionRentaDTO> obtenerContrataciones();

    public abstract OrdenEntregaDTO registrarOrdenEntrega(OrdenEntregaDTO orden);

    // Requisitos de la renta
    public abstract List<RequisitoRentaDTO> obtenerRequisitosRenta();

}
