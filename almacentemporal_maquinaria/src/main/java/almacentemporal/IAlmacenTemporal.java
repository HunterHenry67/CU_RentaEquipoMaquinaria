/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package almacentemporal;

import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.RequisitoRentaDTO;

/**
 *
 * @author BALAMRUSH
 */
public interface IAlmacenTemporal {
    
    
    public abstract List<ClienteDTO> getListaClientesTemp();
    
    public abstract List<DetalleCotizacionDTO> getListaDetalleCotizacionTemp();
    
    public abstract List<RequisitoRentaDTO> getListaRequisitosRentaTemp();
    
    public abstract ClienteDTO getClienteEscenario();
    
    public abstract List<EquipoDTO> getCatalogoEquipo();
    
    public abstract List<AccesorioDTO> getAccesoriosExcavadora();
    
    
        
}
