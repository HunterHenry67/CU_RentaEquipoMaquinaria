/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frames;

import DTOs.ClienteDTO;
import DTOs.CotizacionDTO;
import DTOs.DetalleCotizacionDTO;
import DTOs.EquipoSolicitadoDTO;
import DTOs.PeriodoSolicitadoDTO;
import DTOs.RequisitoRentaDTO;
import DTOs.SolicitudRentaDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Home
 */
public class ContratacionRenta implements IContratacionRenta {

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
    private VerificacionDeDisponibilidadFrame verificacionDeDisponibilidadFrame;
    private VerificacionInventarioFrame verificacionInventarioFrame;
    private UnidadMantenimientoFrame unidadMantenimientoFrame;
    private UnidadOcupadaFrame unidadOcupadaFrame;
    private CalendarioUnidadFrame calendarioUnidadFrame;
    private CondicionesContratacionFrame condicionesContratacionFrame;
    private CotizacionFrame cotizacionFrame;
    private RequisitosRentaFrame requisitosRentaFrame;
    private ClienteDTO clienteActual;
    private SolicitudRentaDTO solicitudActual;
    private CotizacionDTO cotizacionActual;

    // Registrar decisión del cliente y nuevas pantallas de orden de entrega
    private PropuestaRentaFrame propuestaRentaFrame;
    private PropuestaPendienteFrame propuestaPendienteFrame;
    private VerificacionCondicionFrame verificacionCondicionFrame;
    private OrdenEntregaFrame ordenEntregaFrame;

    public ContratacionRenta() {
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
        this.verificacionDeDisponibilidadFrame = new VerificacionDeDisponibilidadFrame(this);
        this.verificacionInventarioFrame = new VerificacionInventarioFrame(this);
        this.unidadMantenimientoFrame = new UnidadMantenimientoFrame(this);
        this.unidadOcupadaFrame = new UnidadOcupadaFrame(this);
        this.calendarioUnidadFrame = new CalendarioUnidadFrame(this);
        this.condicionesContratacionFrame = new CondicionesContratacionFrame(this);

        // Inicialización unificada de todas las pantallas
        this.cotizacionFrame = new CotizacionFrame(this);
        this.requisitosRentaFrame = new RequisitosRentaFrame(this);
        this.propuestaRentaFrame = new PropuestaRentaFrame(this);
        this.propuestaPendienteFrame = new PropuestaPendienteFrame(this);
        this.verificacionCondicionFrame = new VerificacionCondicionFrame(this);
        this.ordenEntregaFrame = new OrdenEntregaFrame(this);

        this.abrirInciarSesión();
    }

    // Estos métodos ayudarán a que se carguen las pantallas dentro de la memoria
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
    public void abrirVerificacionDeDisponibilidad() {
        ocultarTodo();
        verificacionDeDisponibilidadFrame.setVisible(true);
    }

    @Override
    public void abrirVerificacionInventario() {
        ocultarTodo();
        verificacionInventarioFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadMantenimiento() {
        ocultarTodo();
        unidadMantenimientoFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadOcupada() {
        ocultarTodo();
        unidadOcupadaFrame.setVisible(true);
    }

    @Override
    public void abrirCalendarioUnidad() {
        ocultarTodo();
        calendarioUnidadFrame.setVisible(true);
    }

    @Override
    public void abrirCondicionesContratacion() {
        ocultarTodo();
        condicionesContratacionFrame.setVisible(true);
    }

    @Override
    public void abrirPropuestaRenta() {
        ocultarTodo();
        propuestaRentaFrame.reiniciar();
        propuestaRentaFrame.setVisible(true);
    }

    @Override
    public void abrirPropuestaPendiente() {
        ocultarTodo();
        propuestaPendienteFrame.setVisible(true);
    }

    @Override
    public void abrirVerificacionCondicion(boolean conCucharon) {
        ocultarTodo();
        verificacionCondicionFrame.configurar(conCucharon);
        verificacionCondicionFrame.setVisible(true);
    }

    @Override
    public void abrirOrdenEntrega(boolean conCucharon, boolean finalizada) {
        ordenEntregaFrame.configurar(conCucharon, finalizada);
        ordenEntregaFrame.setVisible(true);
    }

    @Override
    public void abrirCotizacion() {
        prepararDatosCotizacion();
        ocultarTodo();
        cotizacionFrame.cargarCotizacion(solicitudActual, cotizacionActual);
        cotizacionFrame.setVisible(true);
    }

    @Override
    public void abrirRequisitosRenta() {
        prepararDatosCotizacion();
        ocultarTodo();
        requisitosRentaFrame.cargarRequisitos(solicitudActual);
        requisitosRentaFrame.setVisible(true);
    }

    // Estos métodos no servirán para sobreecargar los datos en las pantallas
    @Override
    public void pasarDatosSolcitudRenta(ClienteDTO dto) {
        this.clienteActual = dto;
        solicitudRentaForm.cargarDatosCliente(clienteActual);
        ocultarTodo();
        solicitudRentaForm.setVisible(true);
    }

    @Override
    public void pasarDatoRegistroCliente(ClienteDTO dto) {
        this.clienteActual = dto;
        registrarClienteFrame.cargarDatosRegistroCliente(this.clienteActual);
        ocultarTodo();
        solicitudRentaForm.setVisible(true);
    }

    @Override
    public void pasarDatosAnalisisRequerimientos(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        ocultarTodo();
        analisisRequerimientoFrame.setVisible(true);
    }

    @Override
    public void pasarDatosEquipooSolicitado(EquipoSolicitadoDTO equipo) {
        if (this.solicitudActual == null) {
            this.solicitudActual = new SolicitudRentaDTO();
        }
        this.solicitudActual.getEquiposSolicitados().add(equipo);
        if (solicitudRentaForm != null) {
            solicitudRentaForm.cargarEquiposSolicitadosEnTabla(this.solicitudActual.getEquiposSolicitados());
        }
    }

    @Override
    public void pasarDatosRequisitosRenta(CotizacionDTO dto) {
        this.cotizacionActual = dto;
        abrirRequisitosRenta();
    }

    @Override
    public void pasarDatosPropuestaRenta(List<RequisitoRentaDTO> requisitos) {
        this.solicitudActual.setRequisitos(requisitos);
        abrirPropuestaRenta();
    }

    // Completa con datos simulados lo que todavía no viene de otros flujos,
    // sin reemplazar lo que ya se capturó (así se conserva al avanzar o regresar)
    private void prepararDatosCotizacion() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudRentaDTO();
        }
        if (solicitudActual.getCliente() == null) {
            //  solicitudActual.setCliente(clienteActual != null ? clienteActual
            //    : new ClienteDTO(1L, "Julián Martínez García", "MARJ8503158A1", "6449876543", "julian.martinez@correo.com", "Particular"));
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio("FOL-89241");
        }
        if (solicitudActual.getPeriodo() == null) {
            solicitudActual.setPeriodo(new PeriodoSolicitadoDTO(LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 15), 6));
        }
        if (solicitudActual.getRequisitos() == null || solicitudActual.getRequisitos().isEmpty()) {
            List<RequisitoRentaDTO> requisitos = new ArrayList<>();
            requisitos.add(new RequisitoRentaDTO("Identificación oficial", true, "Cumplida", null));
            requisitos.add(new RequisitoRentaDTO("Depósito de garantía", true, "Cubierto", 3200.0));
            solicitudActual.setRequisitos(requisitos);
        }
        if (cotizacionActual == null) {
            List<DetalleCotizacionDTO> detalles = new ArrayList<>();
            detalles.add(new DetalleCotizacionDTO("Equipo", "Excavadora CAT 320 (M-001)", 6, 1500.0, 9000.0, null, null));
            detalles.add(new DetalleCotizacionDTO("Accesorio", "kit", 1, 300.0, 300.0, null, null));
            detalles.add(new DetalleCotizacionDTO("Accesorio", "cucharón", 1, 200.0, 200.0, null, null));
            cotizacionActual = new CotizacionDTO(1L, LocalDate.now(), LocalDate.now().plusDays(15),
                    9500.0, 1520.0, 11020.0, detalles, null);
        }
    }

    @Override
    public void ocultarTodo() {
        if (iniciarSesionFrame != null) {
            iniciarSesionFrame.setVisible(false);
        }
        if (menuPrincipalFrame != null) {
            menuPrincipalFrame.setVisible(false);
        }
        if (identificacionClienteFrame != null) {
            identificacionClienteFrame.setVisible(false);
        }
        if (busquedaEquipoForm != null) {
            busquedaEquipoForm.setVisible(false);
        }
        if (registrarClienteFrame != null) {
            registrarClienteFrame.setVisible(false);
        }
        if (solicitudRentaForm != null) {
            solicitudRentaForm.setVisible(false);
        }
        if (analisisRequerimientoFrame != null) {
            analisisRequerimientoFrame.setVisible(false);
        }
        if (solicitudDatosFaltantesFrame != null) {
            solicitudDatosFaltantesFrame.setVisible(false);
        }
        if (equiposCompatiblesFrame != null) {
            equiposCompatiblesFrame.setVisible(false);
        }
        if (sinEquipoCompatibleFrame != null) {
            sinEquipoCompatibleFrame.setVisible(false);
        }
        if (seleccionAccesoriosFrame != null) {
            seleccionAccesoriosFrame.setVisible(false);
        }
        if (evaluacionTecnicaFrame != null) {
            evaluacionTecnicaFrame.setVisible(false);
        }
        if (verificacionDeDisponibilidadFrame != null) {
            verificacionDeDisponibilidadFrame.setVisible(false);
        }
        if (verificacionInventarioFrame != null) {
            verificacionInventarioFrame.setVisible(false);
        }
        if (unidadMantenimientoFrame != null) {
            unidadMantenimientoFrame.setVisible(false);
        }
        if (unidadOcupadaFrame != null) {
            unidadOcupadaFrame.setVisible(false);
        }
        if (calendarioUnidadFrame != null) {
            calendarioUnidadFrame.setVisible(false);
        }
        if (condicionesContratacionFrame != null) {
            condicionesContratacionFrame.setVisible(false);
        }

        if (cotizacionFrame != null) {
            cotizacionFrame.setVisible(false);
        }
        if (requisitosRentaFrame != null) {
            requisitosRentaFrame.setVisible(false);
        }
        if (propuestaRentaFrame != null) {
            propuestaRentaFrame.setVisible(false);
        }
        if (propuestaPendienteFrame != null) {
            propuestaPendienteFrame.setVisible(false);
        }
        if (verificacionCondicionFrame != null) {
            verificacionCondicionFrame.setVisible(false);
        }
        if (ordenEntregaFrame != null) {
            ordenEntregaFrame.setVisible(false);
        }
    }

}
