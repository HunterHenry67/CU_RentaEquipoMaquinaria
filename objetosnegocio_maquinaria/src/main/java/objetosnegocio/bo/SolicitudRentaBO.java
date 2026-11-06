package objetosnegocio.bo;

import almacentemporal.AlmacenTemporal;
import java.util.List;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;

/**
 * Las solicitudes de renta y sus requisitos. Por ahora se guardan en el almacén temporal, mientras
 * no haya persistencia real.
 */
public class SolicitudRentaBO implements ISolicitudRentaBO {

    private static SolicitudRentaBO instance;

    private SolicitudRentaBO() {
    }

    public static SolicitudRentaBO getInstance() {
        if (instance == null) {
            instance = new SolicitudRentaBO();
        }
        return instance;
    }

    @Override
    public String siguienteFolio() {
        return AlmacenTemporal.getInstance().siguienteFolio();
    }

    @Override
    public SolicitudRentaDTO guardarSolicitud(SolicitudRentaDTO solicitud) {
        return AlmacenTemporal.getInstance().registrarSolicitud(solicitud);
    }

    @Override
    public SolicitudRentaDTO consultarSolicitud(String folio) {
        return AlmacenTemporal.getInstance().buscarSolicitudPorFolio(folio);
    }

    @Override
    public void actualizarSolicitud(SolicitudRentaDTO solicitud) {
        AlmacenTemporal.getInstance().actualizarSolicitud(solicitud);
    }

    @Override
    public List<RequisitoRentaDTO> consultarRequisitos() {
        return AlmacenTemporal.getInstance().obtenerRequisitosRenta();
    }
}
