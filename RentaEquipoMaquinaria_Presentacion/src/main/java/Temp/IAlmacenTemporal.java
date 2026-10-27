/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Temp;

import DTOs.AccesorioDTO;
import DTOs.ClienteDTO;
import DTOs.DetalleCotizacionDTO;
import DTOs.EquipoDTO;
import DTOs.RequisitoRentaDTO;
import java.util.List;

/**
 *
 * @author BALAMRUSH
 */
public interface IAlmacenTemporal {
    
    
    public abstract List<ClienteDTO> getListaClientesTemp();
    
    public abstract List<ClienteDTO> getListaEquipoTemp();
    
    public abstract List<DetalleCotizacionDTO> getListaDetalleCotizacionTemp();
    
    public abstract List<RequisitoRentaDTO> getListaRequisitosRentaTemp();
    
    public abstract ClienteDTO getClienteEscenario();
    
    public abstract List<EquipoDTO> getCatalogoEquipo();
    
    public abstract List<AccesorioDTO> getAccesoriosExcavadora();
    
    
        
}
