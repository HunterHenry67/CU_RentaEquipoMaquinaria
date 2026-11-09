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
        
       if(!ValidadorCliente.correoValido(cliente.getCorreo())){
           throw new NegocioException("Favor de colocar un correo válido.");                    
       }
       
       if(!ValidadorCliente.telefonoValido(cliente.getTelefono())){
           throw new NegocioException("Favor de colocar un teléfono válido.");
       }
       
       if(!ValidadorCliente.rfcValido(cliente.getRfc())){
           throw new NegocioException("Favor de colocar un RFC válido.");
       }
       
       ClienteEmpresaDTO empresa = cliente instanceof ClienteEmpresaDTO e ? e : null;
       
       if(empresa != null){
           if(ValidadorCliente.telefonoValido(empresa.getTelefonoEncargadoEmpresa())){
               throw new NegocioException("Favor de colocar un teléfono válido.");
           }
           if(ValidadorCliente.textoVacio(empresa.getArchivoIdentificacionEncargado())){
               throw new NegocioException("Favor de colocar el archivo de su identificación.");
           }
       }
       ClienteDTO clienteExistente = 
    }
    
    public boolean cumpleRequisitos(RequisitoRentaDTO requisitos){
        
    }
}
