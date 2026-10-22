/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alejandromontijo
 */
public class ContratacionRentaDTO {

    private Long idContratacion;
    private LocalDate fechaFormalizacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double tarifaAcordada;
    private Double depositoGarantia;
    private String estado;
    private String condicionesAcordadas;
    private ClienteDTO cliente;
    private UnidadFisicaDTO unidad;
    private List<AccesorioDTO> accesorios;
    private OrdenEntregaDTO ordenEntrega;

    public ContratacionRentaDTO() {
    }


    public Long getIdContratacion() {
        return idContratacion;
    }

    public void setIdContratacion(Long idContratacion) {
        this.idContratacion = idContratacion;
    }

    public LocalDate getFechaFormalizacion() {
        return fechaFormalizacion;
    }

    public void setFechaFormalizacion(LocalDate fechaFormalizacion) {
        this.fechaFormalizacion = fechaFormalizacion;
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

    public Double getTarifaAcordada() {
        return tarifaAcordada;
    }

    public void setTarifaAcordada(Double tarifaAcordada) {
        this.tarifaAcordada = tarifaAcordada;
    }

    public Double getDepositoGarantia() {
        return depositoGarantia;
    }

    public void setDepositoGarantia(Double depositoGarantia) {
        this.depositoGarantia = depositoGarantia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCondicionesAcordadas() {
        return condicionesAcordadas;
    }

    public void setCondicionesAcordadas(String condicionesAcordadas) {
        this.condicionesAcordadas = condicionesAcordadas;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public UnidadFisicaDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadFisicaDTO unidad) {
        this.unidad = unidad;
    }

    public List<AccesorioDTO> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<AccesorioDTO> accesorios) {
        this.accesorios = accesorios;
    }

    public OrdenEntregaDTO getOrdenEntrega() {
        return ordenEntrega;
    }

    public void setOrdenEntrega(OrdenEntregaDTO ordenEntrega) {
        this.ordenEntrega = ordenEntrega;
    }

}
