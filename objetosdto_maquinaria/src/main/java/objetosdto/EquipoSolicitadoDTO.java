package objetosdto;

/**
 *
 * @author alejandromontijo
 */
public class EquipoSolicitadoDTO {

    private String descripcionEquipo;
    private Integer cantidad;
    private EvaluacionTecnicaDTO evaluacion;

    public EquipoSolicitadoDTO() {
    }

    public EquipoSolicitadoDTO(String descripcionEquipo, Integer cantidad, EvaluacionTecnicaDTO evaluacion) {
        this.descripcionEquipo = descripcionEquipo;
        this.cantidad = cantidad;
        this.evaluacion = evaluacion;
    }

    public String getDescripcionEquipo() {
        return descripcionEquipo;
    }

    public void setDescripcionEquipo(String descripcionEquipo) {
        this.descripcionEquipo = descripcionEquipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public EvaluacionTecnicaDTO getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(EvaluacionTecnicaDTO evaluacion) {
        this.evaluacion = evaluacion;
    }

}
