package presentacion.vistas;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;
import presentacion.control.ValidadorPresentacion;

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

    // "Remodelación Plaza Goya · Av. Guerrero #1105, Ciudad Obregón, Sonora"
    public static String ubicacion(SolicitudRentaDTO solicitud) {
        String obra = texto(solicitud.getNombreObra());
        String direccion = texto(solicitud.getDireccionObra());
        return obra.isEmpty() || direccion.isEmpty() ? obra + direccion : obra + " · " + direccion;
    }

    public static String equiposPedidos(SolicitudRentaDTO solicitud) {
        List<String> nombres = new ArrayList<>();
        if (solicitud.getEquiposSolicitados() != null) {
            for (EquipoSolicitadoDTO pedido : solicitud.getEquiposSolicitados()) {
                nombres.add(texto(pedido.getDescripcionEquipo()));
            }
        }
        return nombres.isEmpty() ? "ninguno en particular" : String.join(", ", nombres);
    }

    // "Profundidad 3.50 m · Potencia 120 HP mín. · Peso 22 t máx. · Ancho 4.20 m máx. · Terreno: Firme"
    public static String requerimientos(SolicitudRentaDTO solicitud) {
        List<String> partes = new ArrayList<>();
        agregar(partes, solicitud, ValidadorPresentacion.PROFUNDIDAD, "Profundidad ", "");
        agregar(partes, solicitud, ValidadorPresentacion.POTENCIA, "Potencia ", " mín.");
        agregar(partes, solicitud, ValidadorPresentacion.PESO, "Peso ", " máx.");
        agregar(partes, solicitud, ValidadorPresentacion.ANCHO, "Ancho ", " máx.");
        agregar(partes, solicitud, ValidadorPresentacion.TERRENO, "Terreno: ", "");
        return String.join(" · ", partes);
    }

    private static void agregar(List<String> partes, SolicitudRentaDTO solicitud, String caracteristica, String antes, String despues) {
        String valor = ValidadorPresentacion.texto(solicitud.getRequerimientos(), caracteristica);
        if (!valor.isEmpty()) {
            partes.add(antes + valor + despues);
        }
    }

    public static String conMayuscula(String texto) {
        return texto == null || texto.isEmpty() ? "" : texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    // La evaluación técnica de la solicitud: es la misma para todos los equipos que pidió el cliente
    public static EvaluacionTecnicaDTO evaluacion(SolicitudRentaDTO solicitud) {
        return solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty() ? null
                : solicitud.getEquiposSolicitados().get(0).getEvaluacion();
    }

    public static EquipoDTO equipoRecomendado(SolicitudRentaDTO solicitud) {
        EvaluacionTecnicaDTO evaluacion = evaluacion(solicitud);
        return evaluacion == null ? null : evaluacion.getRecomendado();
    }
}
