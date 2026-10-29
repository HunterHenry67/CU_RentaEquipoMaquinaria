package presentacion.simulacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.OcupacionUnidadDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import presentacion.control.RequerimientosTecnicos;

/**
 * Simula lo que después hará el subsistema de negocio. Usa los datos del escenario: la tarifa
 * de $1,500 por día para los equipos que no tienen costo registrado y el IVA del 16 %.
 */
public class NegocioSimulado implements INegocioSimulado {

    private static final double TARIFA_DIARIA_SIMULADA = 1500.0;
    private static final double IVA_SIMULADO = 0.16;

    // Si el equipo no tiene registrado un dato, ese requerimiento no lo descarta (no se
    // puede decir que no cumple con un dato que no se tiene)
    @Override
    public Map<EquipoDTO, String> compararEquipos(List<EquipoDTO> catalogo, List<RequerimientoTecnicoDTO> requerimientos) {
        Map<EquipoDTO, String> evaluados = new LinkedHashMap<>();
        for (EquipoDTO equipo : catalogo) {
            evaluados.put(equipo, incumplimiento(equipo, requerimientos));
        }
        return evaluados;
    }

    private String incumplimiento(EquipoDTO equipo, List<RequerimientoTecnicoDTO> requerimientos) {
        List<String> motivos = new ArrayList<>();
        Double potencia = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.POTENCIA);
        if (potencia != null && equipo.getPotencia() != null && equipo.getPotencia() < potencia) {
            motivos.add("potencia de " + RequerimientosTecnicos.formatoNumero(equipo.getPotencia()) + " HP; se piden " + RequerimientosTecnicos.formatoNumero(potencia) + " HP como mínimo");
        }
        Double profundidad = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.PROFUNDIDAD);
        if (profundidad != null && equipo.getProfundidadExcavacion() != null && equipo.getProfundidadExcavacion() < profundidad) {
            motivos.add("excava hasta " + RequerimientosTecnicos.formatoNumero(equipo.getProfundidadExcavacion()) + " m; se piden " + RequerimientosTecnicos.formatoNumero(profundidad) + " m");
        }
        Double peso = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.PESO);
        if (peso != null && equipo.getPeso() != null && equipo.getPeso() > peso) {
            motivos.add("pesa " + RequerimientosTecnicos.formatoNumero(equipo.getPeso()) + " t; el máximo es " + RequerimientosTecnicos.formatoNumero(peso) + " t");
        }
        Double ancho = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.ANCHO);
        if (ancho != null && equipo.getAnchoOperacion() != null && equipo.getAnchoOperacion() > ancho) {
            motivos.add("mide " + RequerimientosTecnicos.formatoNumero(equipo.getAnchoOperacion()) + " m de ancho; el acceso es de " + RequerimientosTecnicos.formatoNumero(ancho) + " m");
        }
        return motivos.isEmpty() ? null : String.join(", ", motivos);
    }

    @Override
    public List<UnidadFisicaDTO> disponibilidad(List<UnidadFisicaDTO> unidades, PeriodoSolicitadoDTO periodo) {
        for (UnidadFisicaDTO u : unidades) {
            if (periodo == null || u.getOcupaciones() == null) {
                continue;
            }
            // a cada unidad solo le quedan las ocupaciones que caen en el periodo; las pantallas las muestran tal cual
            List<OcupacionUnidadDTO> enElPeriodo = new ArrayList<>();
            for (OcupacionUnidadDTO o : u.getOcupaciones()) {
                boolean seTraslapa = !o.getFechaFin().isBefore(periodo.getFechaInicio()) && !o.getFechaInicio().isAfter(periodo.getFechaFin());
                if (seTraslapa) {
                    enElPeriodo.add(o);
                }
            }
            u.setOcupaciones(enElPeriodo);
            if ("Disponible".equals(u.getEstadoUnidad()) && !enElPeriodo.isEmpty()) {
                OcupacionUnidadDTO o = enElPeriodo.get(0);
                u.setEstadoUnidad("Ocupada del " + o.getFechaInicio().getDayOfMonth() + " al " + o.getFechaFin().getDayOfMonth());
            }
        }
        return unidades;
    }

    @Override
    public boolean estaDisponible(UnidadFisicaDTO unidad) {
        return unidad != null && "Disponible".equals(unidad.getEstadoUnidad());
    }

    @Override
    public double tarifaDiaria(EquipoDTO equipo) {
        return equipo.getCostoDiario() != null ? equipo.getCostoDiario() : TARIFA_DIARIA_SIMULADA;
    }

    @Override
    public CotizacionDTO cotizar(EquipoDTO equipo, int dias, List<AccesorioDTO> accesorios) {
        double tarifa = tarifaDiaria(equipo);
        double subtotal = tarifa * dias;
        List<DetalleCotizacionDTO> detalles = new ArrayList<>();
        detalles.add(new DetalleCotizacionDTO("Equipo", equipo.getModelo(), dias, tarifa, subtotal, equipo, null));
        if (accesorios != null) {
            for (AccesorioDTO accesorio : accesorios) {
                double costo = accesorio.getCostoRenta() != null ? accesorio.getCostoRenta() : 0;
                detalles.add(new DetalleCotizacionDTO("Accesorio", accesorio.getNombre(), 1, costo, costo, null, accesorio));
                subtotal += costo;
            }
        }
        double iva = subtotal * IVA_SIMULADO;
        return new CotizacionDTO(null, LocalDate.now(), LocalDate.now().plusDays(15), subtotal, iva, subtotal + iva, detalles, null);
    }

    @Override
    public Double depositoGarantia(List<RequisitoRentaDTO> requisitos) {
        Double deposito = null;
        if (requisitos != null) {
            for (RequisitoRentaDTO requisito : requisitos) {
                if (requisito.getMonto() != null) {
                    deposito = requisito.getMonto();
                }
            }
        }
        return deposito;
    }

    @Override
    public boolean requisitosCumplidos(List<RequisitoRentaDTO> requisitos) {
        if (requisitos != null) {
            for (RequisitoRentaDTO requisito : requisitos) {
                if (Boolean.TRUE.equals(requisito.getEsObligatorio()) && "Pendiente".equals(requisito.getEstadoRequisito())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public OrdenEntregaDTO generarOrdenEntrega(SolicitudRentaDTO solicitud) {
        OrdenEntregaDTO orden = new OrdenEntregaDTO();
        orden.setFechaGeneracion(LocalDate.now());
        orden.setFechaEntrega(solicitud.getPeriodo().getFechaInicio());
        orden.setDireccionEntrega(solicitud.getDireccionObra());
        orden.setHorario("antes de las 8:00 AM");
        orden.setEstadoOrden("Finalizada");
        return orden;
    }
}
