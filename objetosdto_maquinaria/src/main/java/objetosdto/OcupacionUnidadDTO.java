/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

import java.time.LocalDate;

/**
 *
 * @author alejandromontijo
 */
public class OcupacionUnidadDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivoOcupacion;
    private String estadoOcupacion;

    public OcupacionUnidadDTO() {
    }

    public OcupacionUnidadDTO(LocalDate fechaInicio, LocalDate fechaFin, String motivoOcupacion, String estadoOcupacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.motivoOcupacion = motivoOcupacion;
        this.estadoOcupacion = estadoOcupacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMotivoOcupacion() {
        return motivoOcupacion;
    }

    public void setMotivoOcupacion(String motivoOcupacion) {
        this.motivoOcupacion = motivoOcupacion;
    }

    public String getEstadoOcupacion() {
        return estadoOcupacion;
    }

    public void setEstadoOcupacion(String estadoOcupacion) {
        this.estadoOcupacion = estadoOcupacion;
    }

}
