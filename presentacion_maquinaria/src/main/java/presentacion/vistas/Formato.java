package presentacion.vistas;

import java.time.format.DateTimeFormatter;
import objetosdto.EquipoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;

/**
 * Textos que varias pantallas muestran igual a partir de la solicitud que les pasa el
 * Coordinador de Aplicación. Solo da formato: no decide nada del flujo ni del negocio.
 */
public class Formato {

    private Formato() {
    }

    public static String texto(String valor) {
        return valor == null ? "" : valor;
    }

    public static String nombreCliente(SolicitudRentaDTO solicitud) {
        return solicitud.getCliente() == null ? "" : texto(solicitud.getCliente().getNombre());
    }

    // "10/09/2026 al 15/09/2026 · 6 días"
    public static String periodo(SolicitudRentaDTO solicitud) {
        PeriodoSolicitadoDTO periodo = solicitud.getPeriodo();
        if (periodo == null || periodo.getFechaInicio() == null || periodo.getFechaFin() == null) {
            return "";
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return periodo.getFechaInicio().format(formato) + " al " + periodo.getFechaFin().format(formato) + (periodo.getDiasRenta() == null ? "" : " · " + periodo.getDiasRenta() + " días");
    }

    // La evaluación técnica del equipo que pidió el cliente (el primero de la solicitud)
    public static EvaluacionTecnicaDTO evaluacion(SolicitudRentaDTO solicitud) {
        return solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty() ? null
                : solicitud.getEquiposSolicitados().get(0).getEvaluacion();
    }

    public static EquipoDTO equipoRecomendado(SolicitudRentaDTO solicitud) {
        EvaluacionTecnicaDTO evaluacion = evaluacion(solicitud);
        return evaluacion == null ? null : evaluacion.getRecomendado();
    }
}
