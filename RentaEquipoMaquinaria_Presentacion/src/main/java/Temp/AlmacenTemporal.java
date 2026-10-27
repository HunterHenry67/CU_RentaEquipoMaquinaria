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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BALAMRUSH
 */
public class AlmacenTemporal implements IAlmacenTemporal{

    List<ClienteDTO> listaEjemplo;
    public AlmacenTemporal(){
        this.listaEjemplo = new ArrayList<>();
    }
    
    @Override
    public List<ClienteDTO> getListaClientesTemp() {
        ClienteDTO c1 = new ClienteDTO();
        c1.setIdCliente(1L);
        c1.setNombre("Julián Martínez García");
        c1.setRfc("MARJ8503158A1");
        c1.setTelefono("6449876543");
        c1.setCorreo("julian@correo.com");
        c1.setTipoCliente("Particular");
        DTOs.ClienteEmpresaDTO empresaPrueba = new DTOs.ClienteEmpresaDTO();
        empresaPrueba.setIdCliente(4L);
        empresaPrueba.setNombre("Constructora del Norte S.A. de C.V.");
        empresaPrueba.setRfc("CNO081015H99");
        empresaPrueba.setTelefono("6445551234");
        empresaPrueba.setCorreo("contacto@constructoranorte.com");
        empresaPrueba.setTipoCliente("Empresa");
        empresaPrueba.setNombreEncargadoEmpresa("Ing. Carlos Alberto Sánchez");
        empresaPrueba.setPuestoEncargadoEmpresa("Gerente de Operaciones");
        empresaPrueba.setTelefonoEncargadoEmpresa("6445559876");
        ClienteDTO c3 = new ClienteDTO();
        c3.setIdCliente(3L);
        c3.setNombre("María Fernanda López");
        c3.setRfc("LOMF920410H12");
        c3.setTelefono("6447654321");
        c3.setCorreo("mafe@correo.com");
        c3.setTipoCliente("Particular");
        listaEjemplo.add(c1);
        listaEjemplo.add(empresaPrueba);
        listaEjemplo.add(c3);
        return listaEjemplo;
    }

    @Override
    public List<ClienteDTO> getListaEquipoTemp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DetalleCotizacionDTO> getListaDetalleCotizacionTemp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RequisitoRentaDTO> getListaRequisitosRentaTemp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ClienteDTO getClienteEscenario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<EquipoDTO> getCatalogoEquipo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AccesorioDTO> getAccesoriosExcavadora() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
