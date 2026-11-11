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
import objetosdto.AccesorioDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DecisionClienteDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.OcupacionUnidadDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 *
 * @author BALAMRUSH
 */
public class GestorContratacion {
    
    private IAlmacenTemporal almacenTemp;
    
    public GestorContratacion(IAlmacenTemporal almacenTemp){
        this.almacenTemp = almacenTemp;
    }
    
    public boolean registrarDecision(String folio, DecisionClienteDTO decision) throws NegocioException{
        if(folio == null || folio.isBlank() || decision == null){
            throw new NegocioException("");
        }
        
        if(decision.getTipoDecision() == null || decision.getTipoDecision().isBlank()){
            throw new NegocioException("");
        }
        String tipoDecision = decision.getTipoDecision().trim();
        if (!tipoDecision.equalsIgnoreCase("Aceptada") && !tipoDecision.equalsIgnoreCase("Rechazada")) {
            return false;
        }
        if (decision.getFechaDecision() == null) {
            decision.setFechaDecision(LocalDate.now());
        }
        CotizacionDTO cotizacion = almacenTemp.buscarCotizacionPorFolio(folio.trim());
        if (cotizacion == null) {
            return false;
        }
        cotizacion.setDecision(decision);
        almacenTemp.actualizarCotizacion(cotizacion);
        return true;
    }
    
    
    public boolean validarCondiciones(String folio, UnidadFisicaDTO unidad) throws NegocioException{
        if (folio == null || folio.isBlank() || unidad == null || unidad.getIdUnidad() == null) {
            return false;
        }
        String folioValidar = folio.trim();
        SolicitudRentaDTO solicitud = almacenTemp.buscarSolicitudPorFolio(folioValidar);

        if (solicitud == null || solicitud.getCliente() == null || solicitud.getPeriodo() == null) {
            return false;
        }
        LocalDate fechaInicio = solicitud.getPeriodo().getFechaInicio();
        LocalDate fechaFin = solicitud.getPeriodo().getFechaFin();
        
        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            return false;
        }

        CotizacionDTO cotizacion = almacenTemp.buscarCotizacionPorFolio(folioValidar);
        if (cotizacion == null || cotizacion.getDecision() == null) {
            return false;
        }
        if (cotizacion.getDecision().getTipoDecision() == null || !cotizacion.getDecision().getTipoDecision()
                        .equalsIgnoreCase("Aceptada")) {
            return false;
        }
        if (cotizacion.getVigencia() == null || cotizacion.getVigencia().isBefore(LocalDate.now())) {
            return false;
        }
        if (cotizacion.getTotal() == null || cotizacion.getDepositoGarantia() == null) {
            return false;
        }
     
        if (solicitud.getRequisitos() == null) {
            return false;
        }
        for (RequisitoRentaDTO requisito : solicitud.getRequisitos()) {
            if (requisito == null) {
                return false;
            }
            if (Boolean.TRUE.equals(requisito.getEsObligatorio())) {
                String estado = requisito.getEstadoRequisito();
                if (estado == null || estado.isBlank() || estado.equalsIgnoreCase("Pendiente") || estado.equalsIgnoreCase("No cumplido") || estado.equalsIgnoreCase("Rechazado")) {
                    return false;
                }
            }
        }
        
        UnidadFisicaDTO unidadAlmacen = almacenTemp.buscarUnidadPorId(unidad.getIdUnidad());
        if (unidadAlmacen == null || !"Disponible".equalsIgnoreCase( unidadAlmacen.getEstadoUnidad())) {
            return false;
        }
        if (unidadAlmacen.getOcupaciones() != null) {
            for (OcupacionUnidadDTO ocupacion : unidadAlmacen.getOcupaciones()) {
                if (ocupacion == null || ocupacion.getFechaInicio() == null || ocupacion.getFechaFin() == null) {
                    continue;
                }
                boolean seCruzan = !fechaFin.isBefore(ocupacion.getFechaInicio()) && !fechaInicio.isAfter(ocupacion.getFechaFin());
                if (seCruzan && !"Cancelada".equalsIgnoreCase( ocupacion.getEstadoOcupacion()) && !"Finalizada".equalsIgnoreCase(  ocupacion.getEstadoOcupacion())) {
                    return false;
                }
            }
        }
        return true;          
    }
    
    public ContratacionRentaDTO crearContratacion( String folio, UnidadFisicaDTO unidad) throws NegocioException{
        if (!validarCondiciones(folio, unidad)) {
            return null;
        }
        ContratacionRentaDTO existente = almacenTemp.buscarContratacionPorFolio(folio);
        if (existente != null) {
            return existente;
        }
        SolicitudRentaDTO solicitud = almacenTemp.buscarSolicitudPorFolio(folio);
        CotizacionDTO cotizacion = almacenTemp.buscarCotizacionPorFolio(folio);
        ContratacionRentaDTO contratacion = new ContratacionRentaDTO();
        contratacion.setFolioSolicitud(folio);
        contratacion.setFechaFormalizacion(LocalDate.now());
        contratacion.setFechaInicio(solicitud.getPeriodo().getFechaInicio());
        contratacion.setFechaFin(solicitud.getPeriodo().getFechaFin());
        contratacion.setTarifaAcordada(cotizacion.getTotal());
        contratacion.setDepositoGarantia(cotizacion.getDepositoGarantia());
        contratacion.setEstado("Formalizada");
        contratacion.setCondicionesAcordadas("Cotización aceptada.");
        contratacion.setCliente(solicitud.getCliente());
        contratacion.setUnidad(unidad);
        List<AccesorioDTO> accesorios = new ArrayList<>();
        if (cotizacion.getDetalles() != null) {
            for (DetalleCotizacionDTO detalle : cotizacion.getDetalles()) {
                if (detalle.getAccesorio() != null) {
                    accesorios.add(detalle.getAccesorio());
                }
            }
        }
        contratacion.setAccesorios(accesorios);
        return almacenTemp.registrarContratacion(contratacion);
    }
    
     public OrdenEntregaDTO construirOrden( ContratacionRentaDTO contratacion, LocalDate fechaEntrega, String horario, String direccionEntrega) throws NegocioException{
         if(contratacion == null || fechaEntrega == null || horario == null || direccionEntrega == null){
             throw new NegocioException("");
         }
         if (fechaEntrega.isBefore(contratacion.getFechaInicio())
                || fechaEntrega.isAfter(contratacion.getFechaFin())) {
            return null;
        }
        OrdenEntregaDTO orden = new OrdenEntregaDTO();
        orden.setFolioSolicitud(contratacion.getFolioSolicitud());
        orden.setFechaGeneracion(LocalDate.now());
        orden.setFechaEntrega(fechaEntrega);
        orden.setHorario(horario);
        orden.setDireccionEntrega(direccionEntrega);
        orden.setEstadoOrden("Pendiente");
        return orden;
     }
     
     public OrdenEntregaDTO guardarOrden(OrdenEntregaDTO orden) {

        if (orden == null || orden.getFolioSolicitud() == null) {
            return null;
        }
        ContratacionRentaDTO contratacion = almacenTemp.buscarContratacionPorFolio(orden.getFolioSolicitud());
        if (contratacion == null) {
            return null;
        }
        orden.setIdOrdenEntrega(contratacion.getIdContratacion());
        contratacion.setOrdenEntrega(orden);
        almacenTemp.actualizarContratacion(contratacion);
        return orden;
    }
     
     public ContratacionRentaDTO registarContratacion(ContratacionRentaDTO contratacion) throws NegocioException{
         if(contratacion == null || contratacion.getFolioSolicitud() == null || contratacion.getFolioSolicitud().isBlank() || contratacion.getEstado() == null || contratacion.getEstado().isBlank()){
             throw new NegocioException("");
         }
         ContratacionRentaDTO existente = almacenTemp.buscarContratacionPorFolio(contratacion.getFolioSolicitud().trim());
         
         if(existente != null ){
             throw new NegocioException("La contratacion que intenta agregar ya existe.");
         }
         
         return almacenTemp.registrarContratacion(contratacion);
     }
    
    
    
    
}
