package presentacion.simulacion;

import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Respuestas simuladas de las reglas de negocio. El Control de Aplicación las pide aquí
 * mientras no existe el subsistema de negocio (admContratacionRenta); cuando exista, el
 * control le pedirá lo mismo a ese subsistema y las pantallas no cambian.
 */
public interface INegocioSimulado {

    // Cada equipo del catálogo con el motivo por el que no cumple (null si cumple)
    public abstract Map<EquipoDTO, String> compararEquipos(List<EquipoDTO> catalogo, List<RequerimientoTecnicoDTO> requerimientos);

    // Las unidades con su estado para el periodo: Disponible, En mantenimiento u Ocupada del … al …
    public abstract List<UnidadFisicaDTO> disponibilidad(List<UnidadFisicaDTO> unidades, PeriodoSolicitadoDTO periodo);

    public abstract boolean estaDisponible(UnidadFisicaDTO unidad);

    public abstract double tarifaDiaria(EquipoDTO equipo);

    public abstract CotizacionDTO cotizar(EquipoDTO equipo, int dias, List<AccesorioDTO> accesorios);

    public abstract Double depositoGarantia(List<RequisitoRentaDTO> requisitos);

    public abstract boolean requisitosCumplidos(List<RequisitoRentaDTO> requisitos);

    public abstract OrdenEntregaDTO generarOrdenEntrega(SolicitudRentaDTO solicitud);

    // La unidad apartada para el periodo de la renta que se formalizó
    public abstract UnidadFisicaDTO ocuparUnidad(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo);

    // Cuántos días de renta se cobran en un periodo
    public abstract int diasRenta(PeriodoSolicitadoDTO periodo);

    // Si el RFC ya lo tiene otro cliente (registradoConEseRfc es el que encontró el almacén, o null)
    public abstract boolean rfcEnUso(ClienteDTO cliente, ClienteDTO registradoConEseRfc);

    // Los accesorios que se proponen con un equipo: los indispensables más los opcionales elegidos
    public abstract List<AccesorioDTO> accesoriosPropuestos(List<AccesorioDTO> delEquipo, List<AccesorioDTO> elegidos);

    // Con la decisión del cliente: qué accesorios entran a la contratación y en qué estado queda
    public abstract List<AccesorioDTO> accesoriosAceptados(String tipoDecision, List<AccesorioDTO> propuestos);

    public abstract String estadoContratacion(String tipoDecision);
}
