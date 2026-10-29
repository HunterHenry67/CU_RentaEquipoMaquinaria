/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandromontijo
 */
public class SolicitudRentaDTO {

    private Long idSolicitud;
    private String folio;
    private LocalDate fechaSolicitud;
    private String nombreObra;
    private String direccionObra;
    private String descripcionActividad;
    private String tipoTrabajo;
    private String condicionesEspeciales;
    private String estadoSolicitud;
    private String motivoCancelacion;
    private ClienteDTO cliente;
    private PeriodoSolicitadoDTO periodo;
    private List<EquipoSolicitadoDTO> equiposSolicitados;
    private List<RequerimientoTecnicoDTO> requerimientos;
    private List<RequisitoRentaDTO> requisitos;
    private UnidadFisicaDTO unidadSeleccionada;

    public SolicitudRentaDTO() {
        this.equiposSolicitados = new ArrayList<>();
        this.requerimientos = new ArrayList<>();
        this.requisitos = new ArrayList<>();
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public UnidadFisicaDTO getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(UnidadFisicaDTO unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNombreObra() {
        return nombreObra;
    }

    public void setNombreObra(String nombreObra) {
        this.nombreObra = nombreObra;
    }

    public String getDireccionObra() {
        return direccionObra;
    }

    public void setDireccionObra(String direccionObra) {
        this.direccionObra = direccionObra;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public String getCondicionesEspeciales() {
        return condicionesEspeciales;
    }

    public void setCondicionesEspeciales(String condicionesEspeciales) {
        this.condicionesEspeciales = condicionesEspeciales;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public PeriodoSolicitadoDTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoSolicitadoDTO periodo) {
        this.periodo = periodo;
    }

    public List<EquipoSolicitadoDTO> getEquiposSolicitados() {
        return equiposSolicitados;
    }

    public void setEquiposSolicitados(List<EquipoSolicitadoDTO> equiposSolicitados) {
        this.equiposSolicitados = equiposSolicitados;
    }

    public List<RequerimientoTecnicoDTO> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<RequerimientoTecnicoDTO> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public List<RequisitoRentaDTO> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<RequisitoRentaDTO> requisitos) {
        this.requisitos = requisitos;
    }

    
    
}
