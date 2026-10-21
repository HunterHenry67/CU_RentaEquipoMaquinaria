/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rentaequipomaquinaria_dto;

import java.util.List;

/**
 *
 * @author alejandromontijo
 */
public class UnidadFisicaDTO {

    private Long idUnidad;
    private String numeroEconomico;
    private String estadoUnidad;
    private String ubicacion;
    private Integer horasUso;
    private List<OcupacionUnidadDTO> ocupaciones;

    public UnidadFisicaDTO() {
    }

    public UnidadFisicaDTO(Long idUnidad, String numeroEconomico, String estadoUnidad, String ubicacion, Integer horasUso, List<OcupacionUnidadDTO> ocupaciones) {
        this.idUnidad = idUnidad;
        this.numeroEconomico = numeroEconomico;
        this.estadoUnidad = estadoUnidad;
        this.ubicacion = ubicacion;
        this.horasUso = horasUso;
        this.ocupaciones = ocupaciones;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNumeroEconomico() {
        return numeroEconomico;
    }

    public void setNumeroEconomico(String numeroEconomico) {
        this.numeroEconomico = numeroEconomico;
    }

    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getHorasUso() {
        return horasUso;
    }

    public void setHorasUso(Integer horasUso) {
        this.horasUso = horasUso;
    }

    public List<OcupacionUnidadDTO> getOcupaciones() {
        return ocupaciones;
    }

    public void setOcupaciones(List<OcupacionUnidadDTO> ocupaciones) {
        this.ocupaciones = ocupaciones;
    }

}
