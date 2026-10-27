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
public class AlmacenTemporal implements IAlmacenTemporal {

    List<ClienteDTO> listaEjemplo;
    List<EquipoDTO> listaEquipos;
    List<RequisitoRentaDTO> requisitos;
    List<DetalleCotizacionDTO> detalles;
    ClienteDTO cliente;
    List<AccesorioDTO> accesorios;
    List<EquipoDTO> catalogoEquipos;

    public AlmacenTemporal() {
        this.listaEjemplo = new ArrayList<>();
        this.listaEquipos = new ArrayList<>();
        this.requisitos = new ArrayList<>();
        this.detalles = new ArrayList<>();
        this.cliente = new ClienteDTO();
        this.accesorios = new ArrayList<>();
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
    public List<EquipoDTO> getListaEquipoTemp() {
        listaEquipos.add(new EquipoDTO(1L, "CAT", "320", 140.0, 21500.0, 2.5, 6.5, 1500.0, null));
        listaEquipos.add(new EquipoDTO(2L, "JCB", "3CX", 92.0, 7500.0, 2.3, 4.3, 1200.0, null));
        return listaEquipos;
    }

    @Override
    public List<DetalleCotizacionDTO> getListaDetalleCotizacionTemp() {
        detalles.add(new DetalleCotizacionDTO("Equipo", "Excavadora CAT 320 (M-001)", 6, 1500.0, 9000.0, null, null));
        detalles.add(new DetalleCotizacionDTO("Accesorio", "kit", 1, 300.0, 300.0, null, null));
        detalles.add(new DetalleCotizacionDTO("Accesorio", "cucharón", 1, 200.0, 200.0, null, null));
        return detalles;
    }

    @Override
    public List<RequisitoRentaDTO> getListaRequisitosRentaTemp() {
        requisitos.add(new RequisitoRentaDTO("Identificación oficial", true, "Cumplida", null));
        requisitos.add(new RequisitoRentaDTO("Depósito de garantía", true, "Cubierto", 3200.0));
        return requisitos;
    }

    @Override
    public ClienteDTO getClienteEscenario() {
        cliente.setIdCliente(1L);
        cliente.setNombre("Julián Martínez García");
        cliente.setRfc("MARJ8503158A1");
        cliente.setTelefono("6449876543");
        cliente.setCorreo("julianmar12@gmail.com");
        cliente.setTipoCliente("Particular");
        return cliente;
    }

    @Override
    public List<EquipoDTO> getCatalogoEquipo() {
        if (catalogoEquipos == null) {
            catalogoEquipos = new ArrayList<>();
            EquipoDTO jcb = equipo(1L, "JCB", "Retroexcavadora JCB");
            jcb.setPotencia(109.0);
            EquipoDTO cat320 = equipo(2L, "CAT", "Excavadora CAT 320");
            cat320.setCostoDiario(1500.0);
            cat320.setAccesorios(getAccesoriosExcavadora());
            EquipoDTO cat336 = equipo(3L, "CAT", "Excavadora CAT 336");
            cat336.setAccesorios(getAccesoriosExcavadora());
            catalogoEquipos.add(jcb);
            catalogoEquipos.add(cat320);
            catalogoEquipos.add(cat336);
        }
        return catalogoEquipos;
    }

    @Override
    public List<AccesorioDTO> getAccesoriosExcavadora() {
        accesorios.add(accesorio(1L, "Kit de señalización", 300.0, true));
        accesorios.add(accesorio(2L, "Cucharón adicional 1.20 m", 200.0, false));
        return accesorios;
    }
    
    
    // No estoy seguro si es temporal 
    private EquipoDTO equipo(Long id, String marca, String modelo) {
        EquipoDTO equipo = new EquipoDTO();
        equipo.setIdEquipo(id);
        equipo.setMarca(marca);
        equipo.setModelo(modelo);
        equipo.setAccesorios(new ArrayList<>());
        return equipo;
    }
    // No estoy seguro si es temporal
    private AccesorioDTO accesorio(Long id, String nombre, Double costo, boolean obligatorio) {
        AccesorioDTO accesorio = new AccesorioDTO();
        accesorio.setIdAccesorio(id);
        accesorio.setNombre(nombre);
        accesorio.setCostoRenta(costo);
        accesorio.setEsObligatorio(obligatorio);
        return accesorio;
    }
}
