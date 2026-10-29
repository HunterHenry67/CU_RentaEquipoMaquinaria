/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

/**
 *
 * @author alejandromontijo
 */
public class RequisitoRentaDTO {

    private String descripcion;
    private Boolean esObligatorio;
    private String estadoRequisito;
    private Double monto;

    public RequisitoRentaDTO() {
    }

    public RequisitoRentaDTO(String descripcion, Boolean esObligatorio, String estadoRequisito, Double monto) {
        this.descripcion = descripcion;
        this.esObligatorio = esObligatorio;
        this.estadoRequisito = estadoRequisito;
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsObligatorio() {
        return esObligatorio;
    }

    public void setEsObligatorio(Boolean esObligatorio) {
        this.esObligatorio = esObligatorio;
    }

    public String getEstadoRequisito() {
        return estadoRequisito;
    }

    public void setEstadoRequisito(String estadoRequisito) {
        this.estadoRequisito = estadoRequisito;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

}
