package objetosdto;

import java.time.LocalDate;

/**
 *
 * @author alejandromontijo
 */
public class DecisionClienteDTO {

    private LocalDate fechaDecision;
    private String tipoDecision;
    private String observaciones;

    public DecisionClienteDTO() {
    }

    public DecisionClienteDTO(LocalDate fechaDecision, String tipoDecision, String observaciones) {
        this.fechaDecision = fechaDecision;
        this.tipoDecision = tipoDecision;
        this.observaciones = observaciones;
    }

    public LocalDate getFechaDecision() {
        return fechaDecision;
    }

    public void setFechaDecision(LocalDate fechaDecision) {
        this.fechaDecision = fechaDecision;
    }

    public String getTipoDecision() {
        return tipoDecision;
    }

    public void setTipoDecision(String tipoDecision) {
        this.tipoDecision = tipoDecision;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
