/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
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

    @Override
    public List<ClienteDTO> buscarCliente(String rfc, String nombre, String tipo, String telefono) {
        return gestorClientes.buscarPorFiltro(rfc, nombre, tipo, telefono);
    }

    @Override
    public ClienteDTO registrarClienteNuevo(ClienteDTO cliente) throws NegocioException {
        return gestorClientes.registrar(cliente);
    }

    @Override
    public SolicitudRentaDTO validarSolicitudRenta(String idSolicitud) throws NegocioException{
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean registrarSeleccionAccesorios(SolicitudRentaDTO solicitud, List<AccesorioDTO> accesorios) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CotizacionDTO generarCotizacion(SolicitudRentaDTO solicitudRenta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean verificarRequisitosRenta(RequisitoRentaDTO cliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean aceptarPropuestaRenta(SolicitudRentaDTO solicitud, CotizacionDTO cotizacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean rechazarPropuestaRenta(SolicitudRentaDTO solicitud, String motivo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean generarOrdenEntrega(SolicitudRentaDTO solicitud, ContratacionRentaDTO contratacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public EquipoSolicitadoDTO recuperarEquipoSeleccionado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
