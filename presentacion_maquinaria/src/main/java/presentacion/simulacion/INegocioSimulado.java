package presentacion.simulacion;

import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
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
}
