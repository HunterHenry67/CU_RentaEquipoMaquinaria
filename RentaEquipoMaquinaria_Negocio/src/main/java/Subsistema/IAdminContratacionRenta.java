/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DecisionClienteDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 *
 * @author BALAMRUSH
 */
public interface IAdminContratacionRenta {

    public abstract List<ClienteDTO> buscarCliente(String rfc, String nombre, String tipo, String telefono);

    public abstract ClienteDTO registrarClienteNuevo(ClienteDTO cliente) throws NegocioException;

    public abstract SolicitudRentaDTO validarSolicitudRenta(String idSolicitud) throws NegocioException;

    public abstract ResultadoCancelacionDTO cancelarSolicitudRenta(String folio, String motivo);

    public abstract boolean agregarEquipoSolicitud(SolicitudRentaDTO solicitud, EquipoSolicitadoDTO equipo) throws NegocioException;

    public abstract List<EquipoDTO> buscarEquipoCompatibles(RequerimientoTecnicoDTO requerimiento);

    public abstract boolean evaluarRequerimiento(EvaluacionTecnicaDTO evaluacionTecnica);

    public abstract List<AccesorioDTO> obtenerAccesoriosDisponibles();

    public abstract List<UnidadFisicaDTO> verificarDisponibilidadUnidades(EquipoDTO equipo, PeriodoSolicitadoDTO periodo);

    public abstract boolean registrarSeleccionAccesorios(SolicitudRentaDTO solicitud, List<AccesorioDTO> accesorios) throws NegocioException;

    public abstract CotizacionDTO generarCotizacion(SolicitudRentaDTO solicitudRenta);

    public abstract boolean verificarRequisitosRenta(List<RequisitoRentaDTO> requisitos);

    public abstract boolean aceptarPropuestaRenta(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) throws NegocioException;

    public abstract boolean rechazarPropuestaRenta(SolicitudRentaDTO solicitud, DecisionClienteDTO motivo) throws NegocioException;

    public abstract boolean generarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion) throws NegocioException;

    public abstract EquipoSolicitadoDTO recuperarEquipoSeleccionado();
}
