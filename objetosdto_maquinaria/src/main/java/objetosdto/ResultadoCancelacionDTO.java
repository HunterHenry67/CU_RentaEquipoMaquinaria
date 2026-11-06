package objetosdto;

/**
 * Lo que responde el subsistema al pedirle cancelar una solicitud: si se canceló y, si no, el motivo.
 */
public class ResultadoCancelacionDTO {

    private Boolean cancelada;
    private String estadoSolicitud;
    private String motivo;

    public ResultadoCancelacionDTO() {
    }

    public ResultadoCancelacionDTO(Boolean cancelada, String estadoSolicitud, String motivo) {
        this.cancelada = cancelada;
        this.estadoSolicitud = estadoSolicitud;
        this.motivo = motivo;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
