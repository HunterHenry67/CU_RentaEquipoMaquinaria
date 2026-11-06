package objetosnegocio.bo;

import almacentemporal.AlmacenTemporal;
import objetosdto.CotizacionDTO;

/**
 * Las cotizaciones. Por ahora se guardan en el almacén temporal, mientras no haya persistencia real.
 */
public class CotizacionBO implements ICotizacionBO {

    private static CotizacionBO instance;

    private CotizacionBO() {
    }

    public static CotizacionBO getInstance() {
        if (instance == null) {
            instance = new CotizacionBO();
        }
        return instance;
    }

    @Override
    public CotizacionDTO guardarCotizacion(CotizacionDTO cotizacion) {
        return AlmacenTemporal.getInstance().registrarCotizacion(cotizacion);
    }

    @Override
    public CotizacionDTO consultarCotizacion(String folio) {
        return AlmacenTemporal.getInstance().buscarCotizacionPorFolio(folio);
    }

    @Override
    public void actualizarCotizacion(CotizacionDTO cotizacion) {
        AlmacenTemporal.getInstance().actualizarCotizacion(cotizacion);
    }
}
