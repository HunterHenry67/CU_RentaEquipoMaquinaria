/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.List;

/**
 *
 * @author alejandromontijo
 */
public class EquipoDTO {

    private Long idEquipo;
    private String marca;
    private String modelo;
    private Double potencia;
    private Double peso;
    private Double anchoOperacion;
    private Double profundidadExcavacion;
    private Double costoDiario;
    private List<AccesorioDTO> accesorios;

    public EquipoDTO() {
    }

    public EquipoDTO(Long idEquipo, String marca, String modelo, Double potencia, Double peso, Double anchoOperacion, Double profundidadExcavacion, Double costoDiario, List<AccesorioDTO> accesorios) {
        this.idEquipo = idEquipo;
        this.marca = marca;
        this.modelo = modelo;
        this.potencia = potencia;
        this.peso = peso;
        this.anchoOperacion = anchoOperacion;
        this.profundidadExcavacion = profundidadExcavacion;
        this.costoDiario = costoDiario;
        this.accesorios = accesorios;
    }

    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPotencia() {
        return potencia;
    }

    public void setPotencia(Double potencia) {
        this.potencia = potencia;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAnchoOperacion() {
        return anchoOperacion;
    }

    public void setAnchoOperacion(Double anchoOperacion) {
        this.anchoOperacion = anchoOperacion;
    }

    public Double getProfundidadExcavacion() {
        return profundidadExcavacion;
    }

    public void setProfundidadExcavacion(Double profundidadExcavacion) {
        this.profundidadExcavacion = profundidadExcavacion;
    }

    public Double getCostoDiario() {
        return costoDiario;
    }

    public void setCostoDiario(Double costoDiario) {
        this.costoDiario = costoDiario;
    }

    public List<AccesorioDTO> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<AccesorioDTO> accesorios) {
        this.accesorios = accesorios;
    }

}
