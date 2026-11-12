/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import almacentemporal.IAlmacenTemporal;
import java.time.LocalDate;
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
public class AdminContratacionRenta implements IAdminContratacionRenta {

    private GestorClientes gestorClientes;
    private GestorContratacion gestorContratacion;
    private GestorCotizacion gestorCotizacion;
    private GestorDisponibilidad gestorDisponibilidad;
    private GestorSolicitud gestorSolicitud;

    public AdminContratacionRenta(IAlmacenTemporal almacenTemp) {
        this.gestorClientes = new GestorClientes(almacenTemp);
        this.gestorSolicitud = new GestorSolicitud(almacenTemp);
        this.gestorDisponibilidad = new GestorDisponibilidad(almacenTemp);
        this.gestorCotizacion = new GestorCotizacion(almacenTemp);
        this.gestorContratacion = new GestorContratacion(almacenTemp);
    }

    @Override
    public List<ClienteDTO> buscarCliente(String rfc, String nombre, String tipo, String telefono) {
        return gestorClientes.buscarPorFiltro(rfc, nombre, tipo, telefono);
    }

    @Override
    public ClienteDTO registrarClienteNuevo(ClienteDTO cliente) throws NegocioException {
        return gestorClientes.registrar(cliente);
    }

    @Override
    public SolicitudRentaDTO registrarSolcitud(SolicitudRentaDTO solicitud) throws NegocioException {
        return gestorSolicitud.crear(solicitud);
    }
    
    @Override
    public SolicitudRentaDTO validarSolicitudRenta(String idSolicitud) throws NegocioException {
        return gestorSolicitud.buscarPorId(idSolicitud);
    }

    @Override
    public ResultadoCancelacionDTO cancelarSolicitudRenta(String folio, String motivo) {
        return gestorSolicitud.cancelar(folio, motivo);
    }

    @Override
    public boolean agregarEquipoSolicitud(SolicitudRentaDTO solicitud, EquipoSolicitadoDTO equipo) throws NegocioException {
        return gestorSolicitud.agregarUnEQuipo(solicitud.getFolio(), equipo);
    }

    @Override
    public List<EquipoDTO> buscarEquipoCompatibles(RequerimientoTecnicoDTO requerimiento) {
        return gestorDisponibilidad.buscarPorNombre(ree);
    }

    @Override
    public boolean evaluarRequerimiento(EvaluacionTecnicaDTO evaluacionTecnica) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AccesorioDTO> obtenerAccesoriosDisponibles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UnidadFisicaDTO> verificarDisponibilidadUnidades(EquipoDTO equipo, PeriodoSolicitadoDTO periodo) {
        return gestorDisponibilidad.unidadesLibres(equipo, periodo);
    }

    @Override
    public boolean registrarSeleccionAccesorios(SolicitudRentaDTO solicitud, List<AccesorioDTO> accesorios) throws NegocioException {
        return gestorSolicitud.asignarAccesorio(solicitud.getFolio(), accesorios);
    }

    @Override
    public CotizacionDTO generarCotizacion(SolicitudRentaDTO solicitudRenta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean verificarRequisitosRenta(List<RequisitoRentaDTO> requisitos) {
        return gestorClientes.cumpleRequisitos(requisitos);
    }

    @Override
    public boolean aceptarPropuestaRenta(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) throws NegocioException {
        return gestorContratacion.registrarDecision(solicitud.getFolio(), cotizacion.getDecision());
    }

    @Override
    public boolean rechazarPropuestaRenta(SolicitudRentaDTO solicitud, DecisionClienteDTO motivo) throws NegocioException {
        return gestorContratacion.registrarDecision(solicitud.getFolio(), motivo);
    }

    @Override
    public boolean generarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion) {
        return gestorContratacion.construirOrden(contratacion, LocalDate.MIN, , solicitud.getDireccionObra());
    }

    @Override
    public EquipoSolicitadoDTO recuperarEquipoSeleccionado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ContratacionRentaDTO registrarContratacion(ContratacionRentaDTO contratacion) throws NegocioException{
        return gestorContratacion.registarContratacion(contratacion);
    }

    

}
