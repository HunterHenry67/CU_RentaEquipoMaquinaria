/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rentaequipomaquinaria_dto;

/**
 *
 * @author alejandromontijo
 */
public class AccesorioDTO {

    private Long idAccesorio;
    private String nombre;
    private String tipo;
    private Double costoRenta;
    private Boolean esObligatorio;

    public AccesorioDTO() {
    }

    public AccesorioDTO(Long idAccesorio, String nombre, String tipo, Double costoRenta, Boolean esObligatorio) {
        this.idAccesorio = idAccesorio;
        this.nombre = nombre;
        this.tipo = tipo;
        this.costoRenta = costoRenta;
        this.esObligatorio = esObligatorio;
    }

    public Long getIdAccesorio() {
        return idAccesorio;
    }

    public void setIdAccesorio(Long idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCostoRenta() {
        return costoRenta;
    }

    public void setCostoRenta(Double costoRenta) {
        this.costoRenta = costoRenta;
    }

    public Boolean getEsObligatorio() {
        return esObligatorio;
    }

    public void setEsObligatorio(Boolean esObligatorio) {
        this.esObligatorio = esObligatorio;
    }

}
