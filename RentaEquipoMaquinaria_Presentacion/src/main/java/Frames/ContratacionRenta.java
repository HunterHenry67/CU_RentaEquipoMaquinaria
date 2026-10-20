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
    private AnalisisRequerimientoFrame analisisRequerimientoFrame;
    private SolicitudDatosFaltantesFrame solicitudDatosFaltantesFrame;
    private EquiposCompatiblesFrame equiposCompatiblesFrame;
    private SinEquipoCompatibleFrame sinEquipoCompatibleFrame;
    private SeleccionAccesoriosFrame seleccionAccesoriosFrame;
    private EvaluacionTecnicaFrame evaluacionTecnicaFrame;
    
    public ContratacionRenta(){
        this.iniciarSesionFrame = new InicioSesionFrame(this);
        this.menuPrincipalFrame = new MenuPrincipalFrame(this);
        this.identificacionClienteFrame = new IdentificacionClienteFrame(this);
        this.registrarClienteFrame = new RegistroClienteFrame(this);
        this.busquedaEquipoForm = new BusquedaEquipoFrame(this);
        this.solicitudRentaForm = new SolicitudRentaFrame(this);
        this.analisisRequerimientoFrame = new AnalisisRequerimientoFrame(this);
        this.solicitudDatosFaltantesFrame = new SolicitudDatosFaltantesFrame(this);
        this.equiposCompatiblesFrame = new EquiposCompatiblesFrame(this);
        this.sinEquipoCompatibleFrame = new SinEquipoCompatibleFrame(this);
        this.seleccionAccesoriosFrame = new SeleccionAccesoriosFrame(this);
        this.evaluacionTecnicaFrame = new EvaluacionTecnicaFrame(this);
        this.abrirInciarSesión();
    }

    @Override
    public void abrirInciarSesión() {
        ocultarTodo();
        iniciarSesionFrame.setVisible(true);
    }

    @Override
    public void abrirMenuPrincipal() {
        ocultarTodo();
        menuPrincipalFrame.setVisible(true);
    }

    @Override
    public void abrirIdentificacionCliente() {
        ocultarTodo();
        identificacionClienteFrame.setVisible(true);
    }

    @Override
    public void abrirBusquedaEquipo() {
        ocultarTodo();
        busquedaEquipoForm.setVisible(true);
    }

    @Override
    public void abrirRegistrarCliente() {
        ocultarTodo();
        registrarClienteFrame.setVisible(true);
    }

    @Override
    public void abrirSolicitudRenta() {
        ocultarTodo();
        solicitudRentaForm.setVisible(true);
    }

    @Override
    public void abrirAnalisisRequerimiento() {
        ocultarTodo();
        analisisRequerimientoFrame.setVisible(true);
    }

    @Override
    public void abrirSolicitudDatosFaltantes() {
        ocultarTodo();
        solicitudDatosFaltantesFrame.setVisible(true);
    }

    @Override
    public void abrirEquiposCompatibles() {
        ocultarTodo();
        equiposCompatiblesFrame.setVisible(true);
    }

    @Override
    public void abrirSinEquipoCompatible() {
        ocultarTodo();
        sinEquipoCompatibleFrame.setVisible(true);
    }

    @Override
    public void abrirSeleccionAccesorios() {
        ocultarTodo();
        seleccionAccesoriosFrame.setVisible(true);
    }

    @Override
    public void abrirEvaluacionTecnica() {
        ocultarTodo();
        evaluacionTecnicaFrame.setVisible(true);
    }

    @Override
    public void ocultarTodo() {
        if(iniciarSesionFrame != null) iniciarSesionFrame.setVisible(false);
        if(menuPrincipalFrame != null) menuPrincipalFrame.setVisible(false);
        if(identificacionClienteFrame != null) identificacionClienteFrame.setVisible(false);
        if(busquedaEquipoForm != null) busquedaEquipoForm.setVisible(false);
        if(registrarClienteFrame != null) registrarClienteFrame.setVisible(false);
        if(solicitudRentaForm != null) solicitudRentaForm.setVisible(false);
        if(analisisRequerimientoFrame != null) analisisRequerimientoFrame.setVisible(false);
        if(solicitudDatosFaltantesFrame != null) solicitudDatosFaltantesFrame.setVisible(false);
        if(equiposCompatiblesFrame != null) equiposCompatiblesFrame.setVisible(false);
        if(sinEquipoCompatibleFrame != null) sinEquipoCompatibleFrame.setVisible(false);
        if(seleccionAccesoriosFrame != null) seleccionAccesoriosFrame.setVisible(false);
        if(evaluacionTecnicaFrame != null) evaluacionTecnicaFrame.setVisible(false);
    }
    
    
    
}
