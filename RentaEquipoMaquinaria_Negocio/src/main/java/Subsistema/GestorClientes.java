/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import Utilidades.ValidadorCliente;
import java.util.List;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import objetosdto.RequisitoRentaDTO;

/**
 *
 * @author BALAMRUSH
 */
public class GestorClientes {
    
    private ValidadorCliente validador;
    
    public List<ClienteDTO> buscarPorFiltro(){
        
    }
    
    public ClienteDTO registrar(ClienteDTO cliente) throws NegocioException{
        
       if(!validador.correoValido(cliente.getCorreo())){
           throw new NegocioException("Favor de colocar un correo válido.");                    
       }
       
       if(!validador.telefonoValido(cliente.getTelefono())){
           throw new NegocioException("Favor de colocar un teléfono válido.");
       }
       
       if(!validador.rfcValido(cliente.getRfc())){
           throw new NegocioException("Favor de colocar un RFC válido.");
       }
       
       ClienteEmpresaDTO empresa = cliente instanceof ClienteEmpresaDTO e ? e : null;
       
       if(empresa != null){
           if(validador.telefonoValido(empresa.getTelefonoEncargadoEmpresa())){
               throw new NegocioException("Favor de colocar un teléfono válido.");
           }
           if(validador.textoVacio(empresa.getArchivoIdentificacionEncargado())){
               throw new NegocioException("Favor de colocar el archivo de su identificación.");
           }
       }
    }
    
    public boolean cumpleRequisitos(RequisitoRentaDTO requisitos){
        
    }
}
