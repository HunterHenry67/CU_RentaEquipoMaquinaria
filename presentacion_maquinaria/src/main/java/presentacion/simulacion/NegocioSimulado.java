package presentacion.simulacion;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
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

/**
 * Simula lo que después hará el subsistema de negocio. Usa los datos del escenario: la tarifa
 * de $1,500 por día para los equipos que no tienen costo registrado y el IVA del 16 %.
 */
public class NegocioSimulado implements INegocioSimulado {

    private static final double TARIFA_DIARIA_SIMULADA = 1500.0;
    private static final double IVA_SIMULADO = 0.16;

    // Respuesta simulada del escenario: la retroexcavadora JCB (id 1) no cumple porque tiene 109 HP y se
    // piden 120 HP como mínimo; las excavadoras CAT 320 y CAT 336 sí cumplen. Comparar de verdad las
    // características del equipo con los requerimientos es una regla del subsistema de negocio y aquí no se hace.
    private static final Map<Long, String> INCUMPLIMIENTO_SIMULADO = Map.of(1L, "potencia de 109 HP; se piden 120 HP como mínimo");

    @Override
    public Map<EquipoDTO, String> compararEquipos(List<EquipoDTO> catalogo, List<RequerimientoTecnicoDTO> requerimientos) {
        Map<EquipoDTO, String> evaluados = new LinkedHashMap<>();
        for (EquipoDTO equipo : catalogo) {
            evaluados.put(equipo, INCUMPLIMIENTO_SIMULADO.get(equipo.getIdEquipo()));
        }
        return evaluados;
    }

    // Respuesta simulada: cada unidad se queda con el estado que tiene registrado en el almacén (en el
    // escenario la -001 disponible, la -002 en mantenimiento y la -003 ocupada del 13 al 15). Revisar de
    // verdad si la unidad está libre en el periodo es una regla del subsistema de negocio y aquí no se hace.
    @Override
    public List<UnidadFisicaDTO> disponibilidad(List<UnidadFisicaDTO> unidades, PeriodoSolicitadoDTO periodo) {
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
        return new CotizacionDTO(null, LocalDate.now(), null, subtotal, iva, subtotal + iva, detalles, null);
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

    @Override
    public UnidadFisicaDTO ocuparUnidad(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo) {
        unidad.getOcupaciones().add(new OcupacionUnidadDTO(periodo.getFechaInicio(), periodo.getFechaFin(), "Renta", "Confirmada"));
        unidad.setEstadoUnidad("Ocupada");
        return unidad;
    }

    // Se cobra también el día en que termina la renta: del 10 al 15 son 6 días
    @Override
    public int diasRenta(PeriodoSolicitadoDTO periodo) {
        return (int) ChronoUnit.DAYS.between(periodo.getFechaInicio(), periodo.getFechaFin()) + 1;
    }

    @Override
    public boolean rfcEnUso(ClienteDTO cliente, ClienteDTO registradoConEseRfc) {
        return registradoConEseRfc != null && !Objects.equals(registradoConEseRfc.getIdCliente(), cliente.getIdCliente());
    }

    @Override
    public List<AccesorioDTO> accesoriosPropuestos(List<AccesorioDTO> delEquipo, List<AccesorioDTO> elegidos) {
        List<AccesorioDTO> propuestos = new ArrayList<>();
        if (delEquipo != null) {
            for (AccesorioDTO accesorio : delEquipo) {
                if (Boolean.TRUE.equals(accesorio.getEsObligatorio()) || estaEn(accesorio, elegidos)) {
                    propuestos.add(accesorio);
                }
            }
        }
        return propuestos;
    }

    // Respuesta simulada del escenario: con ACEPTA entran todos, con ACEPTA PARCIAL solo el indispensable
    // y si el cliente no acepta el indispensable (o rechaza) no entra ninguno
    @Override
    public List<AccesorioDTO> accesoriosAceptados(String tipoDecision, List<AccesorioDTO> propuestos) {
        List<AccesorioDTO> aceptados = new ArrayList<>();
        if (propuestos != null && ("ACEPTA".equals(tipoDecision) || "ACEPTA PARCIAL".equals(tipoDecision))) {
            for (AccesorioDTO accesorio : propuestos) {
                if ("ACEPTA".equals(tipoDecision) || Boolean.TRUE.equals(accesorio.getEsObligatorio())) {
                    aceptados.add(accesorio);
                }
            }
        }
        return aceptados;
    }

    // Sin el accesorio indispensable la renta queda pendiente; si acepta, faltan por verificar las condiciones
    @Override
    public String estadoContratacion(String tipoDecision) {
        if ("RECHAZA".equals(tipoDecision)) {
            return "RECHAZADA";
        }
        return "NO ACEPTA INDISPENSABLE".equals(tipoDecision) ? "PENDIENTE POR ACCESORIO" : "POR VERIFICAR";
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
}
