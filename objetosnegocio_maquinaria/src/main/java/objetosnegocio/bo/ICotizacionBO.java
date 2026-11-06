package objetosnegocio.bo;

import objetosdto.CotizacionDTO;

/**
 * Operaciones de las cotizaciones que necesita el negocio.
 */
public interface ICotizacionBO {

    public abstract CotizacionDTO guardarCotizacion(CotizacionDTO cotizacion);

    // null si esa solicitud todavía no tiene cotización
    public abstract CotizacionDTO consultarCotizacion(String folio);

    public abstract void actualizarCotizacion(CotizacionDTO cotizacion);
}
