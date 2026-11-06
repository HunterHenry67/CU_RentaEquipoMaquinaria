package objetosdto;

/**
 * Lo que responde el subsistema al registrar una contratación: si quedó formalizada y, si no, el motivo.
 */
public class ResultadoContratacionDTO {

    private Boolean formalizada;
    private String motivo;
    private ContratacionRentaDTO contratacion;

    public ResultadoContratacionDTO() {
    }

    public ResultadoContratacionDTO(Boolean formalizada, String motivo, ContratacionRentaDTO contratacion) {
        this.formalizada = formalizada;
        this.motivo = motivo;
        this.contratacion = contratacion;
    }

    public Boolean getFormalizada() {
        return formalizada;
    }

    public void setFormalizada(Boolean formalizada) {
        this.formalizada = formalizada;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public ContratacionRentaDTO getContratacion() {
        return contratacion;
    }

    public void setContratacion(ContratacionRentaDTO contratacion) {
        this.contratacion = contratacion;
    }
}
