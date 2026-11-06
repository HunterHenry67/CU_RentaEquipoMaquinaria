package objetosnegocio.bo;

import almacentemporal.AlmacenTemporal;
import objetosdto.ContratacionRentaDTO;
import objetosdto.OrdenEntregaDTO;

/**
 * Las contrataciones y sus órdenes de entrega. Por ahora se guardan en el almacén temporal, mientras
 * no haya persistencia real.
 */
public class ContratacionRentaBO implements IContratacionRentaBO {

    private static ContratacionRentaBO instance;

    private ContratacionRentaBO() {
    }

    public static ContratacionRentaBO getInstance() {
        if (instance == null) {
            instance = new ContratacionRentaBO();
        }
        return instance;
    }

    @Override
    public ContratacionRentaDTO guardarContratacion(ContratacionRentaDTO contratacion) {
        return AlmacenTemporal.getInstance().registrarContratacion(contratacion);
    }

    @Override
    public ContratacionRentaDTO consultarContratacion(String folio) {
        return AlmacenTemporal.getInstance().buscarContratacionPorFolio(folio);
    }

    @Override
    public OrdenEntregaDTO guardarOrdenEntrega(OrdenEntregaDTO orden) {
        return AlmacenTemporal.getInstance().registrarOrdenEntrega(orden);
    }
}
