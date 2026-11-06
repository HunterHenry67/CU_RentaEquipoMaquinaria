package subsistemaContratacionRenta;

import java.time.temporal.ChronoUnit;
import java.util.List;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosnegocio.bo.ISolicitudRentaBO;
import objetosnegocio.bo.SolicitudRentaBO;

/**
 * Las solicitudes de renta: su folio, su registro, su cancelación y los días del periodo.
 */
class GestorSolicitud {

    private final ISolicitudRentaBO solicitudRentaBO = SolicitudRentaBO.getInstance();

    public String generarFolio() {
        return solicitudRentaBO.siguienteFolio();
    }

    public SolicitudRentaDTO crear(SolicitudRentaDTO solicitud) {
        return solicitudRentaBO.guardarSolicitud(solicitud);
    }

    public SolicitudRentaDTO obtenerPorFolio(String folio) {
        SolicitudRentaDTO solicitud = solicitudRentaBO.consultarSolicitud(folio);
        if (solicitud == null) {
            throw new IllegalArgumentException("No hay ninguna solicitud con el folio " + folio + ".");
        }
        return solicitud;
    }

    public void actualizar(SolicitudRentaDTO solicitud) {
        solicitudRentaBO.actualizarSolicitud(solicitud);
    }

    // Solo se cancela si no hay contratación formalizada. La solicitud puede no estar registrada
    // todavía (se cancela mientras se captura); en ese caso se registra ya cancelada.
    public ResultadoCancelacionDTO cancelar(SolicitudRentaDTO solicitud) {
        SolicitudRentaDTO registrada = solicitudRentaBO.consultarSolicitud(solicitud.getFolio());
        if (registrada != null && "Formalizada".equals(registrada.getEstadoSolicitud())) {
            return new ResultadoCancelacionDTO(false, registrada.getEstadoSolicitud(), "ya tiene una contratación formalizada");
        }
        solicitud.setEstadoSolicitud("Cancelada");
        if (registrada == null) {
            solicitudRentaBO.guardarSolicitud(solicitud);
        } else {
            solicitudRentaBO.actualizarSolicitud(solicitud);
        }
        return new ResultadoCancelacionDTO(true, solicitud.getEstadoSolicitud(), null);
    }

    public List<RequisitoRentaDTO> listarRequisitos() {
        return solicitudRentaBO.consultarRequisitos();
    }

    // Se cobra también el día en que termina la renta: del 10 al 15 son 6 días
    public int diasRenta(PeriodoSolicitadoDTO periodo) {
        return (int) ChronoUnit.DAYS.between(periodo.getFechaInicio(), periodo.getFechaFin()) + 1;
    }
}
