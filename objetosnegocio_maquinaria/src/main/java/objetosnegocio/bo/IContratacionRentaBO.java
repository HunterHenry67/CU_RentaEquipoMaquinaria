package objetosnegocio.bo;

import objetosdto.ContratacionRentaDTO;
import objetosdto.OrdenEntregaDTO;

/**
 * Operaciones de las contrataciones y sus órdenes de entrega que necesita el negocio.
 */
public interface IContratacionRentaBO {

    public abstract ContratacionRentaDTO guardarContratacion(ContratacionRentaDTO contratacion);

    // null si esa solicitud todavía no tiene contratación
    public abstract ContratacionRentaDTO consultarContratacion(String folio);

    public abstract OrdenEntregaDTO guardarOrdenEntrega(OrdenEntregaDTO orden);
}
