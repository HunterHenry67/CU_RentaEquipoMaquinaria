package Subsistema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import objetosdto.AccesorioDTO;
import objetosdto.EquipoDTO;
import objetosdto.OcupacionUnidadDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.UnidadFisicaDTO;
import objetosnegocio.bo.EquipoBO;
import objetosnegocio.bo.IEquipoBO;

/**
 * Los equipos que cumplen con lo que se requiere y las unidades que están libres en un periodo.
 */
class GestorDisponibilidad {

    private final IEquipoBO equipoBO = EquipoBO.getInstance();

    public List<EquipoDTO> listarCatalogo() {
        return equipoBO.consultarCatalogo();
    }

    public List<EquipoDTO> buscarPorNombre(String texto) {
        return equipoBO.buscarEquipos(texto);
    }

    public EquipoDTO buscarPorId(Long idEquipo) {
        return equipoBO.consultarEquipo(idEquipo);
    }

    public Map<EquipoDTO, String> buscarCompatibles(List<RequerimientoTecnicoDTO> requerimientos) {
        Map<EquipoDTO, String> evaluados = new LinkedHashMap<>();
        for (EquipoDTO equipo : equipoBO.consultarCatalogo()) {
            evaluados.put(equipo, motivoDeIncumplimiento(equipo, requerimientos));
        }
        return evaluados;
    }

    // Por qué el equipo no cumple (null si cumple)
    private String motivoDeIncumplimiento(EquipoDTO equipo, List<RequerimientoTecnicoDTO> requerimientos) {
        List<String> motivos = new ArrayList<>();
        if (requerimientos != null) {
            for (RequerimientoTecnicoDTO requerimiento : requerimientos) {
                String motivo = comparar(equipo, requerimiento);
                if (motivo != null) {
                    motivos.add(motivo);
                }
            }
        }
        return motivos.isEmpty() ? null : String.join(" · ", motivos);
    }

    // Lo que el equipo no tiene registrado no se puede comparar y no lo descarta
    private String comparar(EquipoDTO equipo, RequerimientoTecnicoDTO requerimiento) {
        String caracteristica = requerimiento.getCaracteristica();
        Double pedido = requerimiento.getValorRequerido();
        Double tiene = datoDelEquipo(equipo, caracteristica);
        if (pedido == null || tiene == null) {
            return null;
        }
        boolean esMinimo = "Mínimo".equals(requerimiento.getTipoLimite());
        if (esMinimo ? tiene >= pedido : tiene <= pedido) {
            return null;
        }
        String unidad = requerimiento.getUnidadMedida();
        return nombreDelDato(caracteristica) + " de " + numero(tiene) + " " + unidad + "; se piden " + numero(pedido) + " " + unidad
                + " como " + (esMinimo ? "mínimo" : "máximo");
    }

    private Double datoDelEquipo(EquipoDTO equipo, String caracteristica) {
        switch (caracteristica == null ? "" : caracteristica) {
            case "Profundidad de trabajo":
                return equipo.getProfundidadExcavacion();
            case "Potencia mínima":
                return equipo.getPotencia();
            case "Peso máximo permitido":
                return equipo.getPeso();
            case "Ancho de acceso":
                return equipo.getAnchoOperacion();
            default:
                return null;
        }
    }

    private String nombreDelDato(String caracteristica) {
        switch (caracteristica) {
            case "Profundidad de trabajo":
                return "profundidad";
            case "Potencia mínima":
                return "potencia";
            case "Peso máximo permitido":
                return "peso";
            default:
                return "ancho";
        }
    }

    private String numero(Double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf(valor.longValue());
        }
        BigDecimal numero = BigDecimal.valueOf(valor).stripTrailingZeros();
        return numero.scale() < 2 ? numero.setScale(2).toPlainString() : numero.toPlainString();
    }

    public List<AccesorioDTO> accesoriosPropuestos(EquipoDTO equipo, List<AccesorioDTO> elegidos) {
        List<AccesorioDTO> propuestos = new ArrayList<>();
        for (AccesorioDTO accesorio : equipoBO.consultarAccesorios(equipo.getIdEquipo())) {
            if (Boolean.TRUE.equals(accesorio.getEsObligatorio()) || estaEn(accesorio, elegidos)) {
                propuestos.add(accesorio);
            }
        }
        return propuestos;
    }

    private boolean estaEn(AccesorioDTO accesorio, List<AccesorioDTO> lista) {
        if (lista != null) {
            for (AccesorioDTO otro : lista) {
                if (Objects.equals(otro.getIdAccesorio(), accesorio.getIdAccesorio())) {
                    return true;
                }
            }
        }
        return false;
    }

    public UnidadFisicaDTO buscarUnidad(Long idUnidad) {
        return equipoBO.consultarUnidad(idUnidad);
    }

    // Cada unidad del equipo con su estado en el periodo y solo las ocupaciones que caen en él
    public List<UnidadFisicaDTO> unidadesLibres(EquipoDTO equipo, PeriodoSolicitadoDTO periodo) {
        List<UnidadFisicaDTO> unidades = equipoBO.consultarUnidades(equipo.getIdEquipo());
        if (unidades.isEmpty()) {
            throw new IllegalArgumentException("El equipo " + equipo.getModelo() + " no tiene unidades registradas.");
        }
        for (UnidadFisicaDTO unidad : unidades) {
            List<OcupacionUnidadDTO> enElPeriodo = ocupacionesEnPeriodo(unidad, periodo);
            unidad.setOcupaciones(enElPeriodo);
            unidad.setEstadoUnidad(estadoEnPeriodo(enElPeriodo));
        }
        return unidades;
    }

    private List<OcupacionUnidadDTO> ocupacionesEnPeriodo(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo) {
        List<OcupacionUnidadDTO> enElPeriodo = new ArrayList<>();
        for (OcupacionUnidadDTO ocupacion : unidad.getOcupaciones()) {
            if (!ocupacion.getFechaFin().isBefore(periodo.getFechaInicio()) && !ocupacion.getFechaInicio().isAfter(periodo.getFechaFin())) {
                enElPeriodo.add(ocupacion);
            }
        }
        return enElPeriodo;
    }

    private String estadoEnPeriodo(List<OcupacionUnidadDTO> ocupaciones) {
        if (ocupaciones.isEmpty()) {
            return "Disponible";
        }
        for (OcupacionUnidadDTO ocupacion : ocupaciones) {
            if (ocupacion.getMotivoOcupacion() != null && ocupacion.getMotivoOcupacion().startsWith("Mantenimiento")) {
                return "En mantenimiento";
            }
        }
        return "Ocupada";
    }

    public boolean estaDisponible(UnidadFisicaDTO unidad) {
        return unidad != null && "Disponible".equals(unidad.getEstadoUnidad());
    }

    public UnidadFisicaDTO ocupar(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo) {
        unidad.getOcupaciones().add(new OcupacionUnidadDTO(periodo.getFechaInicio(), periodo.getFechaFin(), "Renta", "Confirmada"));
        unidad.setEstadoUnidad("Ocupada");
        equipoBO.actualizarUnidad(unidad);
        return unidad;
    }
}
