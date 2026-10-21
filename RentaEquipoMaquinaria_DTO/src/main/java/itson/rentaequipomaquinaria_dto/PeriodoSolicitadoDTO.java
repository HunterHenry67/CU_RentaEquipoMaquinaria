/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rentaequipomaquinaria_dto;

import java.time.LocalDate;

/**
 *
 * @author alejandromontijo
 */
public class PeriodoSolicitadoDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer diasRenta;

    public PeriodoSolicitadoDTO() {
    }

    public PeriodoSolicitadoDTO(LocalDate fechaInicio, LocalDate fechaFin, Integer diasRenta) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasRenta = diasRenta;
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

    public Integer getDiasRenta() {
        return diasRenta;
    }

    public void setDiasRenta(Integer diasRenta) {
        this.diasRenta = diasRenta;
    }

}
