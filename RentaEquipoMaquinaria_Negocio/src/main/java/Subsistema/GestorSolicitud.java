/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import almacentemporal.IAlmacenTemporal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import objetosdto.AccesorioDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.SolicitudRentaDTO;

/**
 *
 * @author BALAMRUSH
 */
public class GestorSolicitud {

    private IAlmacenTemporal almacenTemp;

    public GestorSolicitud(IAlmacenTemporal almacenTemp) {
        this.almacenTemp = almacenTemp;
    }

    public SolicitudRentaDTO crear(SolicitudRentaDTO solicitud) {
        if (solicitud == null || solicitud.getCliente() == null || solicitud.getPeriodo() == null) {
            return null;
        }
        PeriodoSolicitadoDTO periodo = solicitud.getPeriodo();
        if (periodo.getFechaInicio() == null || periodo.getFechaFin() == null || periodo.getFechaFin().isBefore(periodo.getFechaInicio())) {
            return null;
        }
        if (solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty()) {
            return null;
        }
        for (EquipoSolicitadoDTO equipo : solicitud.getEquiposSolicitados()) {
            if (equipo == null || equipo.getDescripcionEquipo() == null || equipo.getDescripcionEquipo().isBlank()) {
                return null;
            }
        }

        periodo.setDiasRenta((int) ChronoUnit.DAYS.between(periodo.getFechaInicio(), periodo.getFechaFin()) + 1);
        
        SolicitudRentaDTO anterior = null;
        if (solicitud.getFolio() != null) {
            anterior = almacenTemp.buscarSolicitudPorFolio(solicitud.getFolio());
        }
        if (anterior != null) {
            solicitud.setIdSolicitud(anterior.getIdSolicitud());

            if (solicitud.getFechaSolicitud() == null) {
                solicitud.setFechaSolicitud(anterior.getFechaSolicitud());
            }

            if (solicitud.getEstadoSolicitud() == null) {
                solicitud.setEstadoSolicitud(anterior.getEstadoSolicitud());
            }
            solicitud.setRequerimientos(anterior.getRequerimientos());
            solicitud.setRequisitos(anterior.getRequisitos());
            solicitud.setUnidadSeleccionada(anterior.getUnidadSeleccionada());
            
            for (EquipoSolicitadoDTO equipo : solicitud.getEquiposSolicitados()) {
                for (EquipoSolicitadoDTO equipoAnterior : anterior.getEquiposSolicitados()) {
                    if (Objects.equals(equipo.getDescripcionEquipo(),
                            equipoAnterior.getDescripcionEquipo())) {
                        equipo.setEvaluacion(equipoAnterior.getEvaluacion());
                        break;
                    }
                }
            }
            almacenTemp.actualizarSolicitud(solicitud);
            return solicitud;
        }

        if (solicitud.getFolio() == null || solicitud.getFolio().isBlank()) {
            solicitud.setFolio(almacenTemp.siguienteFolio());
        }

        if (solicitud.getFechaSolicitud() == null) {
            solicitud.setFechaSolicitud(LocalDate.now());
        }

        if (solicitud.getEstadoSolicitud() == null) {
            solicitud.setEstadoSolicitud("Pendiente");
        }

        return almacenTemp.registrarSolicitud(solicitud);
    }

    
    public SolicitudRentaDTO buscarPorId(String folio) throws NegocioException {
        if (folio == null || folio.isBlank()) {
            throw new NegocioException("El folio que se encuentra buscando no existe.");
        }

        return almacenTemp.buscarSolicitudPorFolio(folio.trim().toUpperCase());
    }
    

    public boolean agregarUnEQuipo(String folio, EquipoSolicitadoDTO equipo) throws NegocioException {
        SolicitudRentaDTO solicitud = buscarPorId(folio);

        if (solicitud == null || equipo == null || equipo.getDescripcionEquipo() == null || equipo.getDescripcionEquipo().isBlank()) {
            throw new NegocioException("");
        }
        List<EquipoSolicitadoDTO> equipos = solicitud.getEquiposSolicitados();
        if (equipos == null) {
            equipos = new ArrayList<>();
            solicitud.setEquiposSolicitados(equipos);
        }
        for (EquipoSolicitadoDTO existente : equipos) {
            if (existente != null && existente.getDescripcionEquipo() != null && existente.getDescripcionEquipo().equalsIgnoreCase(equipo.getDescripcionEquipo().trim())) {
                return false;
            }
        }
        equipo.setDescripcionEquipo(equipo.getDescripcionEquipo().trim());
        equipos.add(equipo);
        almacenTemp.actualizarSolicitud(solicitud);
        return true;
    }

    public boolean asignarAccesorio(String folio, List<AccesorioDTO> accesorios) throws NegocioException {
        SolicitudRentaDTO solicitud = buscarPorId(folio);
        if (solicitud == null || solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty()) {
            throw new NegocioException("");
        }
        EquipoSolicitadoDTO solicitado = solicitud.getEquiposSolicitados().get(0);
        if (solicitado == null || solicitado.getEvaluacion() == null || solicitado.getEvaluacion().getRecomendado() == null) {
            throw new NegocioException("");
        }
        EquipoDTO recomendado = solicitado.getEvaluacion().getRecomendado();

        EquipoDTO equipoCatalogo = almacenTemp.buscarEquipoPorId(recomendado.getIdEquipo());

        if (equipoCatalogo == null) {
            throw new NegocioException("");
        }

        List<AccesorioDTO> asignados = new ArrayList<>();
        if (accesorios != null) {
            for (AccesorioDTO accesorio : accesorios) {
                if (accesorio != null && !asignados.contains(accesorio)) {
                    asignados.add(accesorio);
                }
            }
        }
        recomendado.setAccesorios(asignados);
        almacenTemp.actualizarSolicitud(solicitud);
        return true;
    }

    public boolean actualizarEstado(String folio, String estado) throws NegocioException {
        SolicitudRentaDTO solicitud = buscarPorId(folio);
        if (solicitud == null || estado == null || estado.isBlank()) {
            return false;
        }
        solicitud.setEstadoSolicitud(estado.trim());
        almacenTemp.actualizarSolicitud(solicitud);

        return true;
    }
    
    public ResultadoCancelacionDTO cancelar(String folio, String motivo){
        if(folio == null || folio.isBlank() || motivo == null || motivo.isBlank()){
            return new ResultadoCancelacionDTO(false , null , "El folio y el motivo son obligatorios.");
        }
        
        SolicitudRentaDTO solicitud = almacenTemp.buscarSolicitudPorFolio(folio.trim().toUpperCase());
        
        if(solicitud == null){
            return new ResultadoCancelacionDTO(false, null, "No fue posible encontrar la solicitud.");
        }
        
        solicitud.setEstadoSolicitud("Canceladad");
        solicitud.setMotivoCancelacion(motivo.trim());
        almacenTemp.actualizarSolicitud(solicitud);
        return new ResultadoCancelacionDTO( true, solicitud.getEstadoSolicitud(), "La solicitud se canceló correctamente.");
    }
}
