/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.control;

import almacentemporal.AlmacenTemporal;
import almacentemporal.IAlmacenTemporal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DecisionClienteDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.OcupacionUnidadDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import objetosdto.UsuarioDTO;
import presentacion.vistas.AnalisisRequerimientoFrame;
import presentacion.vistas.BusquedaEquipoFrame;
import presentacion.vistas.CalendarioUnidadFrame;
import presentacion.vistas.CambiarPeriodoFrame;
import presentacion.vistas.CondicionesContratacionFrame;
import presentacion.vistas.CotizacionFrame;
import presentacion.vistas.EquiposCompatiblesFrame;
import presentacion.vistas.EvaluacionTecnicaFrame;
import presentacion.vistas.IdentificacionClienteFrame;
import presentacion.vistas.InicioSesionFrame;
import presentacion.vistas.MenuPrincipalFrame;
import presentacion.vistas.MotivoRechazoFrame;
import presentacion.vistas.OrdenEntregaFrame;
import presentacion.vistas.PropuestaPendienteFrame;
import presentacion.vistas.PropuestaRentaFrame;
import presentacion.vistas.RegistroClienteFrame;
import presentacion.vistas.RequisitosRentaFrame;
import presentacion.vistas.SeleccionAccesoriosFrame;
import presentacion.vistas.SinEquipoCompatibleFrame;
import presentacion.vistas.SolicitudDatosFaltantesFrame;
import presentacion.vistas.SolicitudRentaFrame;
import presentacion.vistas.UnidadMantenimientoFrame;
import presentacion.vistas.UnidadOcupadaFrame;
import presentacion.vistas.VerificacionCondicionFrame;
import presentacion.vistas.VerificacionInventarioFrame;

/**
 *
 * @author Home
 */
public class ContratacionRenta implements IContratacionRenta {

    private static final double TARIFA_DIARIA_SIMULADA = 1500.0;
    private static final String FOLIO_SIMULADO = "FOL-89241";
    private static final double IVA_SIMULADO = 0.16;

    private InicioSesionFrame inicioSesionFrame;
    private MenuPrincipalFrame menuPrincipalFrame;
    private IdentificacionClienteFrame identificacionClienteFrame;
    private RegistroClienteFrame registroClienteFrame;
    private BusquedaEquipoFrame busquedaEquipoFrame;
    private SolicitudRentaFrame solicitudRentaFrame;
    private AnalisisRequerimientoFrame analisisRequerimientoFrame;
    private SolicitudDatosFaltantesFrame solicitudDatosFaltantesFrame;
    private EquiposCompatiblesFrame equiposCompatiblesFrame;
    private SinEquipoCompatibleFrame sinEquipoCompatibleFrame;
    private SeleccionAccesoriosFrame seleccionAccesoriosFrame;
    private EvaluacionTecnicaFrame evaluacionTecnicaFrame;
    private CambiarPeriodoFrame cambiarPeriodoFrame;
    private VerificacionInventarioFrame verificacionInventarioFrame;
    private UnidadMantenimientoFrame unidadMantenimientoFrame;
    private UnidadOcupadaFrame unidadOcupadaFrame;
    private CalendarioUnidadFrame calendarioUnidadFrame;
    private CondicionesContratacionFrame condicionesContratacionFrame;
    private CotizacionFrame cotizacionFrame;
    private RequisitosRentaFrame requisitosRentaFrame;
    private ClienteDTO clienteActual;
    private UsuarioDTO usuarioDTO;
    private SolicitudRentaDTO solicitudActual;
    private CotizacionDTO cotizacionActual;
    private ContratacionRentaDTO contratacionActual;
    private List<UnidadFisicaDTO> unidadesCat320;
    private List<UnidadFisicaDTO> unidadesCat336;
    private List<UnidadFisicaDTO> unidadesJcb;
    private IAlmacenTemporal almacenTemporal;
    private MotivoRechazoFrame motivoRechazoFrame;

    private PropuestaRentaFrame propuestaRentaFrame;
    private PropuestaPendienteFrame propuestaPendienteFrame;
    private VerificacionCondicionFrame verificacionCondicionFrame;
    private OrdenEntregaFrame ordenEntregaFrame;

    public ContratacionRenta() {
        this.almacenTemporal = new AlmacenTemporal();
        this.inicioSesionFrame = new InicioSesionFrame(this);
        this.menuPrincipalFrame = new MenuPrincipalFrame(this);
        this.identificacionClienteFrame = new IdentificacionClienteFrame(this);
        this.identificacionClienteFrame.cargarClientes(almacenTemporal.getListaClientesTemp());
        this.registroClienteFrame = new RegistroClienteFrame(this);
        this.busquedaEquipoFrame = new BusquedaEquipoFrame(this);
        this.busquedaEquipoFrame.cargarEquiposEnTabla(almacenTemporal.getCatalogoEquipo());
        this.solicitudRentaFrame = new SolicitudRentaFrame(this);
        this.analisisRequerimientoFrame = new AnalisisRequerimientoFrame(this);
        this.solicitudDatosFaltantesFrame = new SolicitudDatosFaltantesFrame(this);
        this.equiposCompatiblesFrame = new EquiposCompatiblesFrame(this);
        this.sinEquipoCompatibleFrame = new SinEquipoCompatibleFrame(this);
        this.seleccionAccesoriosFrame = new SeleccionAccesoriosFrame(this);
        this.evaluacionTecnicaFrame = new EvaluacionTecnicaFrame(this);
        this.cambiarPeriodoFrame = new CambiarPeriodoFrame(this);
        this.verificacionInventarioFrame = new VerificacionInventarioFrame(this);
        this.unidadMantenimientoFrame = new UnidadMantenimientoFrame(this);
        this.unidadOcupadaFrame = new UnidadOcupadaFrame(this);
        this.calendarioUnidadFrame = new CalendarioUnidadFrame(this);
        this.condicionesContratacionFrame = new CondicionesContratacionFrame(this);

        this.cotizacionFrame = new CotizacionFrame(this);
        this.requisitosRentaFrame = new RequisitosRentaFrame(this);
        this.propuestaRentaFrame = new PropuestaRentaFrame(this);
        this.propuestaPendienteFrame = new PropuestaPendienteFrame(this);
        this.verificacionCondicionFrame = new VerificacionCondicionFrame(this);
        this.ordenEntregaFrame = new OrdenEntregaFrame(this);
        this.motivoRechazoFrame = new MotivoRechazoFrame(this);

    }

    @Override
    public void abrirIniciarSesion() {
        ocultarTodo();
        inicioSesionFrame.setVisible(true);
    }

    @Override
    public void abrirMenuPrincipal() {

        solicitudActual = null;
        cotizacionActual = null;
        contratacionActual = null;
        clienteActual = null;
        solicitudRentaFrame.limpiar();
        ocultarTodo();
        menuPrincipalFrame.setVisible(true);
    }

    @Override
    public void abrirIdentificacionCliente() {
        identificacionClienteFrame.cargarClientes(almacenTemporal.getListaClientesTemp());
        ocultarTodo();
        identificacionClienteFrame.setVisible(true);
    }

    @Override
    public void abrirBusquedaEquipo() {
        busquedaEquipoFrame.cargarEquiposEnTabla(almacenTemporal.getCatalogoEquipo());
        ocultarTodo();
        busquedaEquipoFrame.setVisible(true);
    }

    @Override
    public void abrirRegistrarCliente() {
        ocultarTodo();
        registroClienteFrame.setVisible(true);
    }

    @Override
    public void abrirSolicitudRenta() {
        ocultarTodo();
        solicitudRentaFrame.setVisible(true);
    }

    @Override
    public void abrirAnalisisRequerimiento() {
        prepararDatosSolicitud();
        ocultarTodo();
        analisisRequerimientoFrame.cargarSolicitud(solicitudActual);
        analisisRequerimientoFrame.setVisible(true);
    }

    @Override
    public void abrirSolicitudDatosFaltantes() {
        prepararDatosSolicitud();
        ocultarTodo();
        solicitudDatosFaltantesFrame.cargarSolicitud(solicitudActual);
        solicitudDatosFaltantesFrame.setVisible(true);
    }

    @Override
    public void abrirEquiposCompatibles() {
        prepararDatosSolicitud();
        ocultarTodo();
        equiposCompatiblesFrame.cargarEquipos(solicitudActual, evaluarEquipos());
        equiposCompatiblesFrame.setVisible(true);
    }

    @Override
    public void abrirSinEquipoCompatible() {
        prepararDatosSolicitud();
        ocultarTodo();
        sinEquipoCompatibleFrame.cargarSolicitud(solicitudActual);
        sinEquipoCompatibleFrame.setVisible(true);
    }

    @Override
    public void abrirSeleccionAccesorios() {
        prepararDatosSolicitud();
        ocultarTodo();
        seleccionAccesoriosFrame.cargarAccesorios(solicitudActual, accesoriosDelRecomendado());
        seleccionAccesoriosFrame.setVisible(true);
    }

    @Override
    public void abrirEvaluacionTecnica() {
        prepararDatosSolicitud();
        ocultarTodo();
        evaluacionTecnicaFrame.cargarEvaluacion(solicitudActual);
        evaluacionTecnicaFrame.setVisible(true);
    }

    @Override
    public void abrirCambiarPeriodo() {
        prepararDatosSolicitud();
        ocultarTodo();
        cambiarPeriodoFrame.cargarPeriodo(solicitudActual);
        cambiarPeriodoFrame.setVisible(true);
    }

    @Override
    public void abrirVerificacionInventario() {
        prepararDatosSolicitud();
        ocultarTodo();
        verificacionInventarioFrame.cargarSolicitud(solicitudActual, unidadesSegunModelo());
        verificacionInventarioFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadMantenimiento(UnidadFisicaDTO unidad) {
        prepararDatosSolicitud();
        ocultarTodo();
        unidadMantenimientoFrame.cargarMantenimiento(solicitudActual, unidad);
        unidadMantenimientoFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadOcupada(UnidadFisicaDTO unidad) {
        prepararDatosSolicitud();
        ocultarTodo();
        unidadOcupadaFrame.cargarOcupada(solicitudActual, unidad);
        unidadOcupadaFrame.setVisible(true);
    }

    @Override
    public void abrirCalendarioUnidad() {
        prepararDatosSolicitud();
        ocultarTodo();
        calendarioUnidadFrame.cargarDatos(solicitudActual);
        calendarioUnidadFrame.setVisible(true);
    }

    @Override
    public void abrirCondicionesContratacion() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        ocultarTodo();
        condicionesContratacionFrame.cargarCondiciones(solicitudActual, cotizacionActual);
        condicionesContratacionFrame.setVisible(true);
    }

    @Override
    public void abrirPropuestaRenta() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        ocultarTodo();
        propuestaRentaFrame.cargarDatos(solicitudActual, cotizacionActual);
        propuestaRentaFrame.setVisible(true);
    }

    @Override
    public void abrirPropuestaPendiente() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        ocultarTodo();
        propuestaPendienteFrame.cargarDatos(solicitudActual);
        propuestaPendienteFrame.setVisible(true);
    }

    @Override
    public void pasarDatosDecisionCliente(DecisionClienteDTO decision) {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        cotizacionActual.setDecision(decision);
        contratacionActual = armarContratacion(decision);
        ocultarTodo();
        if ("RECHAZA".equals(decision.getTipoDecision())) {
            solicitudActual.setEstadoSolicitud("Rechazada");
            contratacionActual.setEstado("RECHAZADA");
            ordenEntregaFrame.cargarDatosRechazada(solicitudActual, contratacionActual, decision.getObservaciones());
            ordenEntregaFrame.setVisible(true);
        } else if ("NO ACEPTA INDISPENSABLE".equals(decision.getTipoDecision())) {
            // sin el accesorio indispensable no hay condiciones que revisar: la solicitud queda pendiente
            contratacionActual.setEstado("PENDIENTE POR ACCESORIO");
            ordenEntregaFrame.cargarDatos(solicitudActual, contratacionActual);
            ordenEntregaFrame.setVisible(true);
        } else {
            verificacionCondicionFrame.cargarDatos(solicitudActual, contratacionActual);
            verificacionCondicionFrame.setVisible(true);
        }
    }

    // La contratación con lo que decidió el cliente: con todos los accesorios, solo con el indispensable o sin ellos
    private ContratacionRentaDTO armarContratacion(DecisionClienteDTO decision) {
        String tipo = decision.getTipoDecision();
        ContratacionRentaDTO contratacion = new ContratacionRentaDTO();
        contratacion.setCliente(solicitudActual.getCliente());
        contratacion.setFechaInicio(solicitudActual.getPeriodo().getFechaInicio());
        contratacion.setFechaFin(solicitudActual.getPeriodo().getFechaFin());
        contratacion.setUnidad(solicitudActual.getUnidadSeleccionada());
        contratacion.setEstado("PENDIENTE");
        List<AccesorioDTO> accesorios = new ArrayList<>();
        EquipoDTO equipo = equipoRecomendado();
        if (equipo != null) {
            contratacion.setTarifaAcordada(tarifaDiaria(equipo));
            if (equipo.getAccesorios() != null && !"NO ACEPTA INDISPENSABLE".equals(tipo)) {
                for (AccesorioDTO accesorio : equipo.getAccesorios()) {
                    if (Boolean.TRUE.equals(accesorio.getEsObligatorio()) || !"ACEPTA PARCIAL".equals(tipo)) {
                        accesorios.add(accesorio);
                    }
                }
            }
        }
        contratacion.setAccesorios(accesorios);
        if (solicitudActual.getRequisitos() != null) {
            for (RequisitoRentaDTO requisito : solicitudActual.getRequisitos()) {
                if (requisito.getMonto() != null) {
                    contratacion.setDepositoGarantia(requisito.getMonto());
                }
            }
        }
        return contratacion;
    }


    @Override
    public void pasarDatosCancelacion(SolicitudRentaDTO solicitud) {
        prepararDatosSolicitud();
        solicitudActual.setEstadoSolicitud(solicitud.getEstadoSolicitud());
        solicitudActual.setMotivoCancelacion(solicitud.getMotivoCancelacion());
        abrirMenuPrincipal();
    }

    // Unidad en mantenimiento u ocupada: se toma el equipo alternativo de la evaluación y se buscan sus unidades
    @Override
    public void usarEquipoAlternativo() {
        prepararDatosSolicitud();
        List<EquipoSolicitadoDTO> pedidos = solicitudActual.getEquiposSolicitados();
        EvaluacionTecnicaDTO evaluacion = pedidos == null || pedidos.isEmpty() ? null : pedidos.get(0).getEvaluacion();
        if (evaluacion == null || evaluacion.getAlternativo() == null) {
            abrirEquiposCompatibles();
            return;
        }
        EquipoDTO recomendado = evaluacion.getRecomendado();
        EquipoDTO alternativo = evaluacion.getAlternativo();
        // el alternativo lleva los mismos accesorios que se eligieron para el recomendado
        if (recomendado != null && recomendado.getAccesorios() != null && alternativo.getAccesorios() != null) {
            List<AccesorioDTO> elegidos = new ArrayList<>();
            for (AccesorioDTO accesorio : alternativo.getAccesorios()) {
                for (AccesorioDTO elegido : recomendado.getAccesorios()) {
                    if (Objects.equals(accesorio.getIdAccesorio(), elegido.getIdAccesorio())) {
                        elegidos.add(accesorio);
                        break;
                    }
                }
            }
            alternativo.setAccesorios(elegidos);
        }
        evaluacion.setRecomendado(alternativo);
        evaluacion.setAlternativo(recomendado);
        solicitudActual.setUnidadSeleccionada(null);
        cotizacionActual = null;
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosOrdenEntrega(ContratacionRentaDTO contratacion) {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        contratacionActual = contratacion;
        if ("FORMALIZADA".equals(contratacion.getEstado()) && contratacion.getOrdenEntrega() == null) {
            contratacion.setFechaFormalizacion(LocalDate.now());
            contratacion.setOrdenEntrega(armarOrdenEntrega());
        }
        ocultarTodo();
        ordenEntregaFrame.cargarDatos(solicitudActual, contratacionActual);
        ordenEntregaFrame.setVisible(true);
    }

    private OrdenEntregaDTO armarOrdenEntrega() {
        OrdenEntregaDTO orden = new OrdenEntregaDTO();
        orden.setFechaGeneracion(LocalDate.now());
        orden.setFechaEntrega(solicitudActual.getPeriodo().getFechaInicio());
        orden.setDireccionEntrega(solicitudActual.getDireccionObra());
        orden.setHorario("antes de las 8:00 AM");
        orden.setEstadoOrden("Finalizada");
        return orden;
    }

    @Override
    public void abrirCotizacion() {
        prepararDatosCotizacion();
        // se arma de nuevo con lo elegido, por si el usuario regresó y cambió equipo, accesorios o periodo
        cotizacionActual = armarCotizacion();
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

    @Override
    public void abrirMotivoRechazo() {
        ocultarTodo();
        motivoRechazoFrame.limpiar();
        motivoRechazoFrame.setVisible(true);
    }


    @Override
    public void pasarDatosUsuario(UsuarioDTO dto) {
        this.usuarioDTO = dto;
        abrirMenuPrincipal();
    }

    @Override
    public void pasarDatosSolicitudRenta(ClienteDTO dto) {
        this.clienteActual = dto;
        solicitudRentaFrame.cargarDatosCliente(clienteActual);
        solicitudRentaFrame.cargarFolio(FOLIO_SIMULADO);
        ocultarTodo();
        solicitudRentaFrame.setVisible(true);
    }

    @Override
    public void pasarDatosCambioPeriodo(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosRegistroCliente(ClienteDTO dto) {
        this.clienteActual = dto;
        registroClienteFrame.cargarDatosRegistroCliente(this.clienteActual);
        ocultarTodo();
        registroClienteFrame.setVisible(true);
    }

    @Override
    public void pasarDatosAnalisisRequerimientos(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirAnalisisRequerimiento();
    }

    @Override
    public void pasarDatosSolicitudDatosFaltantes(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirSolicitudDatosFaltantes();
    }

    @Override
    public void pasarDatosEquiposCompatibles(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirEquiposCompatibles();
    }

    @Override
    public void pasarDatosSinEquipoCompatible(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirSinEquipoCompatible();
    }

    @Override
    public void pasarDatosSeleccionAccesorios(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirSeleccionAccesorios();
    }

    @Override
    public void pasarDatosEvaluacionTecnica(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirEvaluacionTecnica();
    }

    @Override
    public void pasarDatosVerificacionInventario(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosEquipoSolicitado(EquipoSolicitadoDTO equipo) {
        if (this.solicitudActual == null) {
            this.solicitudActual = new SolicitudRentaDTO();
        }
        for (EquipoSolicitadoDTO ya : this.solicitudActual.getEquiposSolicitados()) {
            if (Objects.equals(ya.getDescripcionEquipo(), equipo.getDescripcionEquipo())) {
                return;  // ya está en la solicitud
            }
        }
        this.solicitudActual.getEquiposSolicitados().add(equipo);
        if (solicitudRentaFrame != null) {
            solicitudRentaFrame.cargarEquiposSolicitadosEnTabla(this.solicitudActual.getEquiposSolicitados());
        }
    }

    @Override
    public void pasarDatosUnidadSeleccionada(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        // antes de las condiciones se ven los días del periodo de la unidad elegida
        abrirCalendarioUnidad();
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

    private void prepararDatosCotizacion() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudRentaDTO();
        }
        if (solicitudActual.getCliente() == null) {
            solicitudActual.setCliente(clienteActual != null ? clienteActual
                    : almacenTemporal.getClienteEscenario());
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio(FOLIO_SIMULADO);
        }
        if (solicitudActual.getPeriodo() == null) {
            solicitudActual.setPeriodo(new PeriodoSolicitadoDTO(LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 15), 6));
        }
        if (solicitudActual.getRequisitos() == null || solicitudActual.getRequisitos().isEmpty()) {
            solicitudActual.setRequisitos(almacenTemporal.getListaRequisitosRentaTemp());
        }
        if (cotizacionActual == null) {
            cotizacionActual = armarCotizacion();
        }
    }

    // Arma la cotización con lo que se eligió en la solicitud: equipo recomendado, sus accesorios
    // y los días del periodo. La tarifa (para equipos sin costo registrado) y el IVA son simulados
    // mientras no exista el subsistema de negocio que los calcule.
    // La misma tarifa en la cotización, la propuesta, la verificación y la orden
    private double tarifaDiaria(EquipoDTO equipo) {
        return equipo.getCostoDiario() != null ? equipo.getCostoDiario() : TARIFA_DIARIA_SIMULADA;
    }

    private CotizacionDTO armarCotizacion() {
        EquipoDTO equipo = equipoRecomendado();
        Integer dias = solicitudActual.getPeriodo().getDiasRenta();
        if (equipo == null || dias == null) {
            // sin evaluación técnica todavía, se usa el escenario del almacén
            return new CotizacionDTO(1L, LocalDate.now(), LocalDate.now().plusDays(15),
                    9500.0, 1520.0, 11020.0, almacenTemporal.getListaDetalleCotizacionTemp(), null);
        }
        double tarifa = tarifaDiaria(equipo);
        double subtotal = tarifa * dias;
        List<DetalleCotizacionDTO> detalles = new ArrayList<>();
        detalles.add(new DetalleCotizacionDTO("Equipo", equipo.getModelo(), dias, tarifa, subtotal, equipo, null));
        if (equipo.getAccesorios() != null) {
            for (AccesorioDTO accesorio : equipo.getAccesorios()) {
                double costo = accesorio.getCostoRenta() != null ? accesorio.getCostoRenta() : 0;
                detalles.add(new DetalleCotizacionDTO("Accesorio", accesorio.getNombre(), 1, costo, costo, null, accesorio));
                subtotal += costo;
            }
        }
        double iva = subtotal * IVA_SIMULADO;
        return new CotizacionDTO(1L, LocalDate.now(), LocalDate.now().plusDays(15),
                subtotal, iva, subtotal + iva, detalles, null);
    }

    // El equipo que se recomendó en la evaluación técnica (F2), con los accesorios que se eligieron
    private EquipoDTO equipoRecomendado() {
        List<EquipoSolicitadoDTO> pedidos = solicitudActual.getEquiposSolicitados();
        EvaluacionTecnicaDTO evaluacion = pedidos == null || pedidos.isEmpty() ? null : pedidos.get(0).getEvaluacion();
        return evaluacion == null ? null : evaluacion.getRecomendado();
    }

    private void prepararDatosSolicitud() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudRentaDTO();
        }
        if (solicitudActual.getCliente() == null) {
            solicitudActual.setCliente(clienteActual != null ? clienteActual : almacenTemporal.getClienteEscenario());
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio(FOLIO_SIMULADO);
        }
        if (solicitudActual.getNombreObra() == null) {
            solicitudActual.setNombreObra("Remodelación Plaza Goya");
        }
        if (solicitudActual.getDireccionObra() == null) {
            solicitudActual.setDireccionObra("Av. Guerrero #1105, Ciudad Obregón, Sonora");
        }
        if (solicitudActual.getDescripcionActividad() == null) {
            solicitudActual.setDescripcionActividad("Excavar cepas para la cimentación y retirar el material, a 3.50 m de profundidad.");
        }
        if (solicitudActual.getPeriodo() == null) {
            PeriodoSolicitadoDTO periodo = new PeriodoSolicitadoDTO();
            periodo.setFechaInicio(LocalDate.of(2026, 9, 10));
            periodo.setFechaFin(LocalDate.of(2026, 9, 15));
            periodo.setDiasRenta(6);
            solicitudActual.setPeriodo(periodo);
        }
        if (solicitudActual.getEquiposSolicitados() == null || solicitudActual.getEquiposSolicitados().isEmpty()) {
            EquipoSolicitadoDTO pedido = new EquipoSolicitadoDTO();
            pedido.setDescripcionEquipo("Retroexcavadora JCB");
            pedido.setCantidad(1);
            List<EquipoSolicitadoDTO> pedidos = new ArrayList<>();
            pedidos.add(pedido);
            solicitudActual.setEquiposSolicitados(pedidos);
        }
        if (solicitudActual.getRequerimientos() == null) {
            solicitudActual.setRequerimientos(new ArrayList<>());
        }
    }

    // El catálogo es el del almacén temporal (el mismo que ve Búsqueda de equipo)
    private List<EquipoDTO> catalogoEquipos() {
        return almacenTemporal.getCatalogoEquipo();
    }

    private UnidadFisicaDTO unidad(Long id, String numero, String estado, List<OcupacionUnidadDTO> ocupaciones) {
        UnidadFisicaDTO u = new UnidadFisicaDTO();
        u.setIdUnidad(id);
        u.setNumeroEconomico(numero);
        u.setEstadoUnidad(estado);
        u.setOcupaciones(ocupaciones);
        return u;
    }

    private void crearUnidades() {
        PeriodoSolicitadoDTO p = solicitudActual.getPeriodo();
        LocalDate inicio = p.getFechaInicio();
        LocalDate fin = p.getFechaFin();

        LocalDate ocupadaDesde = inicio.plusDays(1).isAfter(fin) ? inicio : inicio.plusDays(1);

        unidadesCat320 = new ArrayList<>();
        unidadesCat320.add(unidad(1L, "M-001", "Disponible", new ArrayList<>()));
        unidadesCat320.add(unidad(2L, "M-002", "En mantenimiento", mantenimiento(inicio, fin)));
        unidadesCat320.add(unidad(3L, "M-003", "Disponible", ocupacion(ocupadaDesde, fin)));

        unidadesCat336 = new ArrayList<>();
        unidadesCat336.add(unidad(4L, "N-001", "Disponible", new ArrayList<>()));
        unidadesCat336.add(unidad(5L, "N-002", "En mantenimiento", mantenimiento(inicio, fin)));
        unidadesCat336.add(unidad(6L, "N-003", "Disponible", ocupacion(ocupadaDesde, fin)));

        unidadesJcb = new ArrayList<>();
        unidadesJcb.add(unidad(7L, "J-001", "Disponible", new ArrayList<>()));
        unidadesJcb.add(unidad(8L, "J-002", "En mantenimiento", mantenimiento(inicio, fin)));
        unidadesJcb.add(unidad(9L, "J-003", "Disponible", ocupacion(ocupadaDesde, fin)));
    }

    private List<OcupacionUnidadDTO> ocupacion(LocalDate desde, LocalDate hasta) {
        List<OcupacionUnidadDTO> lista = new ArrayList<>();
        lista.add(new OcupacionUnidadDTO(desde, hasta, "Renta", "Confirmada"));
        return lista;
    }

    private List<OcupacionUnidadDTO> mantenimiento(LocalDate desde, LocalDate hasta) {
        List<OcupacionUnidadDTO> lista = new ArrayList<>();
        lista.add(new OcupacionUnidadDTO(desde, hasta, "Mantenimiento preventivo", "En curso"));
        return lista;
    }

    private List<UnidadFisicaDTO> unidadesSegunModelo() {
        crearUnidades();

        List<EquipoSolicitadoDTO> pedidos = solicitudActual.getEquiposSolicitados();
        EvaluacionTecnicaDTO evaluacion = pedidos == null || pedidos.isEmpty() ? null : pedidos.get(0).getEvaluacion();
        if (evaluacion == null || evaluacion.getRecomendado() == null || evaluacion.getRecomendado().getModelo() == null) {
            return new ArrayList<>();
        }

        String modelo = evaluacion.getRecomendado().getModelo();
        if (modelo.contains("320")) {
            return unidadesCat320;
        } else if (modelo.contains("336")) {
            return unidadesCat336;
        } else if (modelo.contains("JCB")) {
            return unidadesJcb;
        }
        return new ArrayList<>();
    }

    // Compara cada equipo del catálogo con los requerimientos de la solicitud. Regresa los
    // equipos en orden con el motivo por el que no cumplen (null si cumplen). Va aquí
    // mientras Negocio no tenga compararEquipos, como los datos simulados de la cotización.
    private Map<EquipoDTO, String> evaluarEquipos() {
        Map<EquipoDTO, String> evaluados = new LinkedHashMap<>();
        for (EquipoDTO equipo : catalogoEquipos()) {
            evaluados.put(equipo, incumplimiento(equipo, solicitudActual.getRequerimientos()));
        }
        return evaluados;
    }

    // Si el equipo no tiene registrado un dato, ese requerimiento no lo descarta (no se
    // puede decir que no cumple con un dato que no se tiene)
    private String incumplimiento(EquipoDTO equipo, List<RequerimientoTecnicoDTO> requerimientos) {
        List<String> motivos = new ArrayList<>();
        Double potencia = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.POTENCIA);
        if (potencia != null && equipo.getPotencia() != null && equipo.getPotencia() < potencia) {
            motivos.add("potencia de " + RequerimientosTecnicos.formatoNumero(equipo.getPotencia()) + " HP; se piden " + RequerimientosTecnicos.formatoNumero(potencia) + " HP como mínimo");
        }
        Double profundidad = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.PROFUNDIDAD);
        if (profundidad != null && equipo.getProfundidadExcavacion() != null && equipo.getProfundidadExcavacion() < profundidad) {
            motivos.add("excava hasta " + RequerimientosTecnicos.formatoNumero(equipo.getProfundidadExcavacion()) + " m; se piden " + RequerimientosTecnicos.formatoNumero(profundidad) + " m");
        }
        Double peso = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.PESO);
        if (peso != null && equipo.getPeso() != null && equipo.getPeso() > peso) {
            motivos.add("pesa " + RequerimientosTecnicos.formatoNumero(equipo.getPeso()) + " t; el máximo es " + RequerimientosTecnicos.formatoNumero(peso) + " t");
        }
        Double ancho = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.ANCHO);
        if (ancho != null && equipo.getAnchoOperacion() != null && equipo.getAnchoOperacion() > ancho) {
            motivos.add("mide " + RequerimientosTecnicos.formatoNumero(equipo.getAnchoOperacion()) + " m de ancho; el acceso es de " + RequerimientosTecnicos.formatoNumero(ancho) + " m");
        }
        return motivos.isEmpty() ? null : String.join(", ", motivos);
    }

    // Todos los accesorios que hay en el catálogo para el equipo recomendado en F2
    private List<AccesorioDTO> accesoriosDelRecomendado() {
        List<EquipoSolicitadoDTO> pedidos = solicitudActual.getEquiposSolicitados();
        EvaluacionTecnicaDTO evaluacion = pedidos == null || pedidos.isEmpty() ? null : pedidos.get(0).getEvaluacion();
        if (evaluacion == null || evaluacion.getRecomendado() == null) {
            return new ArrayList<>();
        }
        for (EquipoDTO equipo : catalogoEquipos()) {
            if (Objects.equals(equipo.getIdEquipo(), evaluacion.getRecomendado().getIdEquipo())) {
                return equipo.getAccesorios();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void ocultarTodo() {
        if (inicioSesionFrame != null) {
            inicioSesionFrame.setVisible(false);
        }
        if (menuPrincipalFrame != null) {
            menuPrincipalFrame.setVisible(false);
        }
        if (identificacionClienteFrame != null) {
            identificacionClienteFrame.setVisible(false);
        }
        if (busquedaEquipoFrame != null) {
            busquedaEquipoFrame.setVisible(false);
        }
        if (registroClienteFrame != null) {
            registroClienteFrame.setVisible(false);
        }
        if (solicitudRentaFrame != null) {
            solicitudRentaFrame.setVisible(false);
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
        if (cambiarPeriodoFrame != null) {
            cambiarPeriodoFrame.setVisible(false);
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
        if (motivoRechazoFrame != null) {
            motivoRechazoFrame.setVisible(false);
        }
    }

}
