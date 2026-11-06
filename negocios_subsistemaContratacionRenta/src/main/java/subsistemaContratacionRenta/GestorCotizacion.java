package subsistemaContratacionRenta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosnegocio.bo.CotizacionBO;
import objetosnegocio.bo.EquipoBO;
import objetosnegocio.bo.ICotizacionBO;
import objetosnegocio.bo.IEquipoBO;

/**
 * Los importes de la renta: la tarifa, el total con IVA y el depósito de garantía.
 */
class GestorCotizacion {

    private static final double IVA = 0.16;

    private final ICotizacionBO cotizacionBO = CotizacionBO.getInstance();
    private final IEquipoBO equipoBO = EquipoBO.getInstance();

    public double tarifaDiaria(EquipoDTO equipo) {
        Double tarifa = equipoBO.consultarTarifa(equipo.getIdEquipo());
        if (tarifa == null) {
            throw new IllegalStateException("El equipo " + equipo.getModelo() + " no tiene tarifa registrada.");
        }
        return tarifa;
    }

    public CotizacionDTO calcularTotal(String folio, EquipoDTO equipo, int dias, List<AccesorioDTO> accesorios) {
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
        double iva = subtotal * IVA;
        CotizacionDTO cotizacion = new CotizacionDTO(null, LocalDate.now(), null, subtotal, iva, subtotal + iva, detalles, null);
        cotizacion.setFolioSolicitud(folio);
        return cotizacion;
    }

    // El depósito es el monto del requisito que lo pide
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

    public CotizacionDTO guardar(CotizacionDTO cotizacion) {
        return cotizacionBO.guardarCotizacion(cotizacion);
    }

    public CotizacionDTO obtenerPorFolio(String folio) {
        return cotizacionBO.consultarCotizacion(folio);
    }

    public void actualizar(CotizacionDTO cotizacion) {
        cotizacionBO.actualizarCotizacion(cotizacion);
    }
}
