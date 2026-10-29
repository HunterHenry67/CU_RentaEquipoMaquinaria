/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

/**
 *
 * @author alejandromontijo
 */
public class RequerimientoTecnicoDTO {

    private String caracteristica;
    private Double valorRequerido;
    private String unidadMedida;
    private String tipoLimite;

    public RequerimientoTecnicoDTO() {
    }

    public RequerimientoTecnicoDTO(String caracteristica, Double valorRequerido, String unidadMedida, String tipoLimite) {
        this.caracteristica = caracteristica;
        this.valorRequerido = valorRequerido;
        this.unidadMedida = unidadMedida;
        this.tipoLimite = tipoLimite;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Double getValorRequerido() {
        return valorRequerido;
    }

    public void setValorRequerido(Double valorRequerido) {
        this.valorRequerido = valorRequerido;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getTipoLimite() {
        return tipoLimite;
    }

    public void setTipoLimite(String tipoLimite) {
        this.tipoLimite = tipoLimite;
    }

}
