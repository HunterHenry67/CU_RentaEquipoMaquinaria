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
public class OrdenEntregaDTO {

    private Long idOrdenEntrega;
    private LocalDate fechaGeneracion;
    private LocalDate fechaEntrega;
    private String direccionEntrega;
    private String horario;
    private String estadoOrden;

    public OrdenEntregaDTO() {
    }

    public OrdenEntregaDTO(Long idOrdenEntrega, LocalDate fechaGeneracion, LocalDate fechaEntrega, String direccionEntrega, String horario, String estadoOrden) {
        this.idOrdenEntrega = idOrdenEntrega;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaEntrega = fechaEntrega;
        this.direccionEntrega = direccionEntrega;
        this.horario = horario;
        this.estadoOrden = estadoOrden;
    }

    public Long getIdOrdenEntrega() {
        return idOrdenEntrega;
    }

    public void setIdOrdenEntrega(Long idOrdenEntrega) {
        this.idOrdenEntrega = idOrdenEntrega;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

}
