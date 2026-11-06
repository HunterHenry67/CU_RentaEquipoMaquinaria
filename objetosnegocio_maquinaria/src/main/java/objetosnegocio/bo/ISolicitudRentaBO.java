package objetosnegocio.bo;

import java.util.List;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;

/**
 * Operaciones de las solicitudes de renta que necesita el negocio.
 */
public interface ISolicitudRentaBO {

    public abstract String siguienteFolio();

    public abstract SolicitudRentaDTO guardarSolicitud(SolicitudRentaDTO solicitud);

    public abstract SolicitudRentaDTO consultarSolicitud(String folio);

    public abstract void actualizarSolicitud(SolicitudRentaDTO solicitud);

    public abstract List<RequisitoRentaDTO> consultarRequisitos();
}
