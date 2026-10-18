/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frames;

/**
 *
 * @author Home
 */
public class ContratacionRenta implements IContratacionRenta{
    
    private InicioSesionFrame iniciarSesionFrame;
    private MenuPrincipalFrame menuPrincipalFrame;
    private IdentificacionClienteFrame identificacionClienteFrame;
    private RegistroClienteFrame registrarClienteFrame;
    private BusquedaEquipoFrame busquedaEquipoForm;
    private SolicitudRentaFrame solicitudRentaForm;
    
    public ContratacionRenta(){
        this.iniciarSesionFrame = new InicioSesionFrame(this);
        this.menuPrincipalFrame = new MenuPrincipalFrame(this);
        this.identificacionClienteFrame = new IdentificacionClienteFrame(this);
        this.registrarClienteFrame = new RegistroClienteFrame(this);
        this.busquedaEquipoForm = new BusquedaEquipoFrame(this);
        this.solicitudRentaForm = new SolicitudRentaFrame(this);
        this.abrirInciarSesión();
    }

    @Override
    public void abrirInciarSesión() {
        iniciarSesionFrame.setVisible(true);
    }

    @Override
    public void abrirMenuPrincipal() {
        menuPrincipalFrame.setVisible(true);
    }

    @Override
    public void abrirIdentificacionCliente() {
        identificacionClienteFrame.setVisible(true);
    }

    @Override
    public void abrirBusquedaEquipo() {
        busquedaEquipoForm.setVisible(true);
    }

    @Override
    public void abrirRegistrarCliente() {
        registrarClienteFrame.setVisible(true);
    }

    @Override
    public void abrirSolicitudRenta() {
        solicitudRentaForm.setVisible(true);
    }

    @Override
    public void ocultarTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
