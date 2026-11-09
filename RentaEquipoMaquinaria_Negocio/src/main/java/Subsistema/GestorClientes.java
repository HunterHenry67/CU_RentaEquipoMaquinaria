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
    
    public ClienteDTO registrar(ClienteDTO cliente){
        if (faltanDatos(Mensaje.FALTAN_DATOS_CLIENTE, validador.faltantesCliente(cliente))) {
            return;
        }
        ClienteEmpresaDTO empresa = cliente instanceof ClienteEmpresaDTO e ? e : null;
        if (empresa != null && faltanDatos(Mensaje.FALTAN_DATOS_CLIENTE, validador.faltantesEncargado(empresa))) {
            return;
        }
        if (empresa != null && validador.textoVacio(empresa.getArchivoIdentificacionEncargado())) {
            coordinador.mostrarAviso(Mensaje.FALTA_IDENTIFICACION_ENCARGADO);
            return;
        }
        if (!validador.rfcValido(cliente.getRfc())) {
            throw new NegocioException("Favor de colocar un RFC válido");
        }
        if (!validador.telefonoValido(cliente.getTelefono())) {
            throw new NegocioException("Favor de colocar un teléfono válido.");
        }
        if (!validador.correoValido(cliente.getCorreo())) {
            throw new NegocioException("Favor de colocar un correo válido.");
        }
        if (empresa != null && !validador.telefonoValido(empresa.getTelefonoEncargadoEmpresa())) {
            coordinador.mostrarAviso(Mensaje.TELEFONO_ENCARGADO_INVALIDO);
            return;
        }
        if (negocio.rfcEnUso(cliente, almacen.buscarClientePorRfc(cliente.getRfc()))) {
            coordinador.mostrarAviso(Mensaje.RFC_REPETIDO);
            return;
        }
    }
    
    public boolean cumpleRequisitos(RequisitoRentaDTO requisitos){
        
    }
}
