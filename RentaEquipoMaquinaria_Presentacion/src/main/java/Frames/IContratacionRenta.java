/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Frames;

import DTOs.ClienteDTO;
import DTOs.CotizacionDTO;
import DTOs.EquipoSolicitadoDTO;
import DTOs.RequisitoRentaDTO;
import DTOs.SolicitudRentaDTO;
import java.util.List;

/**
 *
 * @author Home
 */
public interface IContratacionRenta {  
    
    public abstract void abrirInciarSesión();
    
    public abstract void abrirMenuPrincipal();
    
    public abstract void abrirIdentificacionCliente();
    
    public abstract void abrirBusquedaEquipo();
    
    public abstract void abrirRegistrarCliente();
    
    public abstract void abrirSolicitudRenta();
    
    public abstract void abrirAnalisisRequerimiento();
    
    public abstract void abrirSolicitudDatosFaltantes();
    
    public abstract void abrirEquiposCompatibles();
    
    public abstract void abrirSinEquipoCompatible();
    
    public abstract void abrirSeleccionAccesorios();
    
    public abstract void abrirEvaluacionTecnica();
    
    public abstract void abrirVerificacionDeDisponibilidad();
    
    public abstract void abrirVerificacionInventario();
    
    public abstract void abrirUnidadMantenimiento();
    
    public abstract void abrirUnidadOcupada();
    
    public abstract void abrirCalendarioUnidad();
    
    public abstract void abrirCondicionesContratacion();
    
    public abstract void abrirCotizacion();
    
    public abstract void abrirRequisitosRenta();
    
    public abstract void abrirPropuestaRenta();
    
    public abstract void abrirPropuestaPendiente();
    
    public abstract void abrirVerificacionCondicion(boolean conCucharon);
    
    public abstract void abrirOrdenEntrega(boolean conCucharon, boolean finalizada);
    
    public abstract void pasarDatosSolcitudRenta(ClienteDTO dto);
    
    public abstract void pasarDatoRegistroCliente(ClienteDTO dto);
    
    public abstract void pasarDatosAnalisisRequerimientos(SolicitudRentaDTO dto);

    public abstract void pasarDatosRequisitosRenta(CotizacionDTO dto);

    public abstract void pasarDatosPropuestaRenta(List<RequisitoRentaDTO> requisitos);
    
    public abstract void pasarDatosEquipooSolicitado(EquipoSolicitadoDTO equipo);

    public abstract void ocultarTodo();
    
}