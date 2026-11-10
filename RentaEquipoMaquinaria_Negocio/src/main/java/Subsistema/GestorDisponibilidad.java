/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

/**
 *
 * @author BALAMRUSH
 */

import almacentemporal.IAlmacenTemporal;
import java.util.ArrayList;
import java.util.List;
import objetosdto.EquipoDTO;
import objetosdto.UnidadFisicaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.AccesorioDTO;
import objetosdto.OcupacionUnidadDTO;

public class GestorDisponibilidad {

    private IAlmacenTemporal almacenTemp;

    public GestorDisponibilidad(IAlmacenTemporal almacenTemp) {
        this.almacenTemp = almacenTemp;
    }

    public List<EquipoDTO> buscarPorNombre(String nombreEquipo) {
        return almacenTemp.buscarEquipos(nombreEquipo);
    }

    public List<AccesorioDTO> listarAccesorios(EquipoDTO equipo) {
        if (equipo == null) {
            return new ArrayList<>();
        }

        return equipo.getAccesorios();
    }

    public List<UnidadFisicaDTO> unidadesLibres(EquipoDTO equipo, PeriodoSolicitadoDTO periodo) {

        if (equipo == null || periodo == null) {
            return new ArrayList<>();
        }

        List<UnidadFisicaDTO> unidades = almacenTemp.obtenerUnidadesDeEquipo(equipo.getIdEquipo());

        List<UnidadFisicaDTO> disponibles = new ArrayList<>();

        for (UnidadFisicaDTO unidad : unidades) {
            if (!unidadEnMantenimiento(unidad) && !existeConflicto(unidad, periodo)) {
                disponibles.add(unidad);
            }
        }

        return disponibles;
    }

    public boolean unidadEnMantenimiento(UnidadFisicaDTO unidad) {

        if (unidad == null || unidad.getEstadoUnidad() == null) {
            return false;
        }

        return unidad.getEstadoUnidad().trim().equalsIgnoreCase("En mantenimiento");
    }

    public boolean existeConflicto(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo) {

        if (unidad == null || periodo == null || periodo.getFechaInicio() == null || periodo.getFechaFin() == null) {
            return true;
        }

        if (periodo.getFechaFin().isBefore(periodo.getFechaInicio())) {
            return true;
        }

        List<OcupacionUnidadDTO> ocupaciones = unidad.getOcupaciones();

        if (ocupaciones == null || ocupaciones.isEmpty()) {
            return false;
        }

        for (OcupacionUnidadDTO ocupacion : ocupaciones) {

            if (ocupacion == null || ocupacion.getFechaInicio() == null || ocupacion.getFechaFin() == null) {
                continue;
            }

            if (ocupacionCancelada(ocupacion)) {
                continue;
            }

            boolean seTraslapa = !ocupacion.getFechaFin().isBefore(periodo.getFechaInicio()) && !ocupacion.getFechaInicio().isAfter(periodo.getFechaFin());

            if (seTraslapa) {
                return true;
            }
        }

        return false;
    }

    private boolean ocupacionCancelada(OcupacionUnidadDTO ocupacion) {

        String estado = ocupacion.getEstadoOcupacion();

        if (estado == null) {
            return false;
        }

        estado = estado.trim();

        return estado.equalsIgnoreCase("Cancelada") || estado.equalsIgnoreCase("Cancelado") || estado.equalsIgnoreCase("Rechazada") || estado.equalsIgnoreCase("Rechazado");
    }
}
