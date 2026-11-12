/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

import Excepciones.NegocioException;
import Utilidades.ValidadorCliente;
import almacentemporal.IAlmacenTemporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import objetosdto.RequisitoRentaDTO;

/**
 *
 * @author BALAMRUSH
 */
public class GestorClientes {

    private ValidadorCliente validador;
    private IAlmacenTemporal almacenTemp;

    public GestorClientes(IAlmacenTemporal almacenTemp) {
        this.almacenTemp = almacenTemp;
    }

    public List<ClienteDTO> buscarPorFiltro(String rfc, String nombre, String tipo, String telefono) {
        List<ClienteDTO> clientesFiltrados = new ArrayList<>();

        List<ClienteDTO> clientes = almacenTemp.obtenerClientes();

        for (ClienteDTO cliente : clientes) {
            boolean clienteEncontrado = true;

            if (rfc != null && !rfc.isBlank() && !cliente.getRfc().equalsIgnoreCase(rfc)) {
                clienteEncontrado = false;
            }

            if (nombre != null && !nombre.isEmpty() && !cliente.getNombre().equalsIgnoreCase(nombre)) {
                clienteEncontrado = false;
            }

            if (telefono != null && !telefono.isEmpty() && !cliente.getTelefono().equalsIgnoreCase(telefono)) {
                clienteEncontrado = false;
            }

            if (tipo != null && tipo.isBlank()) {

                if (tipo.equalsIgnoreCase("Empresa") && !(cliente instanceof ClienteEmpresaDTO)) {
                    clienteEncontrado = false;
                }

                if (tipo.equalsIgnoreCase("Particular") && cliente instanceof ClienteEmpresaDTO) {
                    clienteEncontrado = false;
                }
            }
            if (clienteEncontrado) {
                clientesFiltrados.add(cliente);
            }
        }

        return clientesFiltrados;
    }

    public void actualizar(ClienteDTO cliente) throws NegocioException {
        if (cliente == null || cliente.getIdCliente() == null) {
            throw new NegocioException("No es posible actualizar el cliente sin identificarse.");
        }
        ClienteDTO clienteExistente = almacenTemp.buscarClientePorId(cliente.getIdCliente());

        if (clienteExistente == null) {
            throw new NegocioException("El cliente que quiere actualizar no existe.");
        }

        ClienteDTO clienteMismoRFC = almacenTemp.buscarClientePorRfc(cliente.getRfc());

        if (clienteMismoRFC != null && !Objects.equals(clienteMismoRFC.getIdCliente(), cliente.getIdCliente())) {
            throw new NegocioException("Este RFC ya se encuentra registrado por otro cliente.");
        }

        almacenTemp.actualizarCliente(cliente);
    }

    public ClienteDTO registrar(ClienteDTO cliente) throws NegocioException {

        if (!ValidadorCliente.correoValido(cliente.getCorreo())) {
            throw new NegocioException("Favor de colocar un correo válido.");
        }

        if (!ValidadorCliente.telefonoValido(cliente.getTelefono())) {
            throw new NegocioException("Favor de colocar un teléfono válido.");
        }

        if (!ValidadorCliente.rfcValido(cliente.getRfc())) {
            throw new NegocioException("Favor de colocar un RFC válido.");
        }

        ClienteEmpresaDTO empresa = cliente instanceof ClienteEmpresaDTO e ? e : null;

        if (empresa != null) {
            if (!ValidadorCliente.telefonoValido(empresa.getTelefonoEncargadoEmpresa())) {
                throw new NegocioException("Favor de colocar un teléfono válido.");
            }
            if (ValidadorCliente.textoVacio(empresa.getArchivoIdentificacionEncargado())) {
                throw new NegocioException("Favor de colocar el archivo de su identificación.");
            }
        }
        ClienteDTO clienteExistente = almacenTemp.buscarClientePorRfc(cliente.getRfc());

        if (clienteExistente != null) {
            throw new NegocioException("El cliente con el RFC ingresado ya existe.");
        }

        return almacenTemp.registrarCliente(cliente);
    }

    public boolean cumpleRequisitos(List<RequisitoRentaDTO> requisitos) {
        if (requisitos == null) {
            return false;
        }

        for (RequisitoRentaDTO requisito : requisitos) {
            if (requisito.getEsObligatorio() && "Pendiente".equals(requisito.getEstadoRequisito())) {
                return false;
            }
        }
        return true;
    }

    public boolean rfcEnUso(ClienteDTO cliente) {
        if (cliente == null || cliente.getRfc() == null) {
            return false;
        }
        ClienteDTO registrado = almacenTemp.buscarClientePorRfc(cliente.getRfc());
        
        boolean estaEnUso = registrado != null && !Objects.equals(registrado.getIdCliente(), cliente.getIdCliente());
        
        return estaEnUso;
    }
}
