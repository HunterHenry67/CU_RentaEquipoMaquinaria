package presentacion.vistas;

import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.RequisitoRentaDTO;

/**
 * Lee los importes de la cotización que armó el control para mostrarlos. Las pantallas no
 * calculan precios ni impuestos: solo los toman de aquí.
 */
public class Montos {

    private Montos() {
    }

    // El renglón del equipo (cantidad = días, precio unitario = tarifa por día)
    public static DetalleCotizacionDTO equipo(CotizacionDTO cotizacion) {
        if (cotizacion != null && cotizacion.getDetalles() != null) {
            for (DetalleCotizacionDTO detalle : cotizacion.getDetalles()) {
                if ("Equipo".equals(detalle.getTipoConcepto())) {
                    return detalle;
                }
            }
        }
        return null;
    }

    public static double renta(CotizacionDTO cotizacion) {
        DetalleCotizacionDTO detalle = equipo(cotizacion);
        return detalle == null || detalle.getImporte() == null ? 0 : detalle.getImporte();
    }

    public static int dias(CotizacionDTO cotizacion) {
        DetalleCotizacionDTO detalle = equipo(cotizacion);
        return detalle == null || detalle.getCantidad() == null ? 0 : detalle.getCantidad();
    }

    public static double tarifaDiaria(CotizacionDTO cotizacion) {
        DetalleCotizacionDTO detalle = equipo(cotizacion);
        return detalle == null || detalle.getPrecioUnitario() == null ? 0 : detalle.getPrecioUnitario();
    }

    // Lo que suman los renglones de accesorios: todos (null), los indispensables (true) o los opcionales (false)
    public static double accesorios(CotizacionDTO cotizacion, Boolean indispensables) {
        double suma = 0;
        if (cotizacion != null && cotizacion.getDetalles() != null) {
            for (DetalleCotizacionDTO detalle : cotizacion.getDetalles()) {
                boolean esAccesorio = "Accesorio".equals(detalle.getTipoConcepto());
                boolean obligatorio = detalle.getAccesorio() != null && Boolean.TRUE.equals(detalle.getAccesorio().getEsObligatorio());
                if (esAccesorio && detalle.getImporte() != null && (indispensables == null || indispensables == obligatorio)) {
                    suma += detalle.getImporte();
                }
            }
        }
        return suma;
    }

    public static double subtotal(CotizacionDTO cotizacion) {
        return cotizacion == null || cotizacion.getSubtotal() == null ? 0 : cotizacion.getSubtotal();
    }

    public static double iva(CotizacionDTO cotizacion) {
        return cotizacion == null || cotizacion.getIva() == null ? 0 : cotizacion.getIva();
    }

    public static double total(CotizacionDTO cotizacion) {
        return cotizacion == null || cotizacion.getTotal() == null ? 0 : cotizacion.getTotal();
    }

    public static String nombres(List<AccesorioDTO> accesorios) {
        StringBuilder nombres = new StringBuilder();
        if (accesorios != null) {
            for (AccesorioDTO accesorio : accesorios) {
                if (nombres.length() > 0) {
                    nombres.append(", ");
                }
                nombres.append(accesorio.getNombre());
            }
        }
        return nombres.length() == 0 ? "Ninguno" : nombres.toString();
    }

    // El requisito con monto es el depósito de garantía
    public static double deposito(List<RequisitoRentaDTO> requisitos) {
        RequisitoRentaDTO requisito = requisitoDeposito(requisitos);
        return requisito == null ? 0 : requisito.getMonto();
    }

    public static String estadoDeposito(List<RequisitoRentaDTO> requisitos) {
        RequisitoRentaDTO requisito = requisitoDeposito(requisitos);
        return requisito == null || requisito.getEstadoRequisito() == null ? "Cubierto" : requisito.getEstadoRequisito();
    }

    private static RequisitoRentaDTO requisitoDeposito(List<RequisitoRentaDTO> requisitos) {
        RequisitoRentaDTO deposito = null;
        if (requisitos != null) {
            for (RequisitoRentaDTO requisito : requisitos) {
                if (requisito.getMonto() != null) {
                    deposito = requisito;
                }
            }
        }
        return deposito;
    }

    public static String moneda(double valor) {
        return String.format("$%,.0f", valor);
    }
}
