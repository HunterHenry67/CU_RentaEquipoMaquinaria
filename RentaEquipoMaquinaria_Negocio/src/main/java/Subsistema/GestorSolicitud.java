/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import almacentemporal.IAlmacenTemporal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.PeriodoSolicitadoDTO;
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
            conservarLoCapturado(anterior, solicitud);
            solicitud.setIdSolicitud(anterior.getIdSolicitud());

            if (solicitud.getFechaSolicitud() == null) {
                solicitud.setFechaSolicitud(anterior.getFechaSolicitud());
            }

            if (solicitud.getEstadoSolicitud() == null) {
                solicitud.setEstadoSolicitud(anterior.getEstadoSolicitud());
            }

            return almacenTemp.actualizarSolicitud(solicitud) ? solicitud : null;
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
        return almacenTemp.actualizarSolicitud(solicitud);
    }
    
    public boolean actualizarEstado(String folio, String estado){
        SolicitudRentaDTO solicitud = buscarPorId(folio);
        if (solicitud == null || estado == null || estado.isBlank()) {
            return false;
        }
        solicitud.setEstadoSolicitud(estado.trim());
        return almacenTemp.actualizarSolicitud(solicitud);
    }
}
