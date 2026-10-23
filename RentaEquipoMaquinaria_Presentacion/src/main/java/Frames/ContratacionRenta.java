/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frames;

import DTOs.AccesorioDTO;
import DTOs.ClienteDTO;
import DTOs.CotizacionDTO;
import DTOs.DetalleCotizacionDTO;
import DTOs.EquipoDTO;
import DTOs.EquipoSolicitadoDTO;
import DTOs.EvaluacionTecnicaDTO;
import DTOs.OcupacionUnidadDTO;
import DTOs.PeriodoSolicitadoDTO;
import DTOs.RequerimientoTecnicoDTO;
import DTOs.RequisitoRentaDTO;
import DTOs.SolicitudRentaDTO;
import DTOs.UnidadFisicaDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private UnidadFisicaDTO unidadActual;
    private List<EquipoDTO> catalogoEquipos;
    private List<UnidadFisicaDTO> unidades;
    private static final double IVA = 0.16;
    // mientras no haya persistencia, cada solicitud lleva el folio del escenario
    private static final String FOLIO = "FOL-89241";

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

        this.abrirIniciarSesion();
    }

    // Estos métodos ayudarán a que se carguen las pantallas dentro de la memoria
    @Override
    public void abrirIniciarSesion() {
        iniciarSesionFrame.limpiarCampos();
        ocultarTodo();
        iniciarSesionFrame.setVisible(true);
    }

    @Override
    public void abrirMenuPrincipal() {
        // Al volver al menú termina la solicitud en curso; la siguiente empieza en blanco
        solicitudActual = null;
        cotizacionActual = null;
        clienteActual = null;
        unidadActual = null;
        ocultarTodo();
        menuPrincipalFrame.setVisible(true);
    }

    @Override
    public void abrirIdentificacionCliente() {
        identificacionClienteFrame.limpiarBusqueda();
        ocultarTodo();
        identificacionClienteFrame.setVisible(true);
    }

    @Override
    public void abrirBusquedaEquipo() {
        ocultarTodo();
        busquedaEquipoForm.cargarEquiposEnTabla(catalogoEquipos());
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
    public void abrirVerificacionDeDisponibilidad() {
        prepararDatosSolicitud();
        ocultarTodo();
        verificacionDeDisponibilidadFrame.cargarDatos(solicitudActual);
        verificacionDeDisponibilidadFrame.setVisible(true);
    }

    @Override
    public void abrirVerificacionInventario() {
        prepararDatosSolicitud();
        ocultarTodo();
        verificacionInventarioFrame.cargarUnidades(solicitudActual, evaluarDisponibilidad());
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
        prepararDatosCotizacion();
        ocultarTodo();
        condicionesContratacionFrame.cargarCondiciones(solicitudActual, unidadActual, cotizacionActual);
        condicionesContratacionFrame.setVisible(true);
    }

    @Override
    public void abrirPropuestaRenta() {
        if (cotizacionActual == null) {
            prepararDatosCotizacion();
        }
        ocultarTodo();
        propuestaRentaFrame.cargarPropuesta(solicitudActual, cotizacionActual, unidadActual);
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
        if (cotizacionActual == null) {
            prepararDatosCotizacion();
        }
        ocultarTodo();
        verificacionCondicionFrame.cargarDatos(solicitudActual, cotizacionDecidida(conCucharon), unidadActual);
        verificacionCondicionFrame.configurar(conCucharon);
        verificacionCondicionFrame.setVisible(true);
    }

    @Override
    public void abrirOrdenEntrega(boolean conCucharon, boolean finalizada) {
        if (cotizacionActual == null) {
            prepararDatosCotizacion();
        }
        solicitudActual.setEstadoSolicitud(finalizada ? "Formalizada" : "Pendiente");
        // la orden es la última pantalla: no se deja abierta la de atrás para que no se vuelva a decidir
        ocultarTodo();
        ordenEntregaFrame.cargarDatos(solicitudActual, cotizacionDecidida(conCucharon), unidadActual);
        ordenEntregaFrame.configurar(conCucharon, finalizada);
        ordenEntregaFrame.setVisible(true);
    }

    // La cotización con lo que aceptó el cliente (sin el opcional si lo quitó); se queda como
    // la actual junto con su decisión
    private CotizacionDTO cotizacionDecidida(boolean conOpcionales) {
        CotizacionDTO cotizacion = calcularCotizacion(conOpcionales);
        cotizacion.setDecision(cotizacionActual.getDecision());
        cotizacionActual = cotizacion;
        return cotizacion;
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

    // Estos métodos nos servirán para cargar los datos en las pantallas
    @Override
    public void pasarDatosSolicitudRenta(ClienteDTO dto) {
        // Con el cliente empieza una solicitud nueva: no se arrastra nada de la anterior
        this.clienteActual = dto;
        this.solicitudActual = null;
        this.cotizacionActual = null;
        this.unidadActual = null;
        identificacionClienteFrame.guardarCliente(dto);
        solicitudRentaForm.limpiarSolicitud();
        solicitudRentaForm.cargarFolio(FOLIO);
        solicitudRentaForm.cargarDatosCliente(clienteActual);
        ocultarTodo();
        solicitudRentaForm.setVisible(true);
    }

    @Override
    public void pasarDatosRegistroCliente(ClienteDTO dto) {
        this.clienteActual = dto;
        registrarClienteFrame.cargarDatosRegistroCliente(this.clienteActual);
        ocultarTodo();
        registrarClienteFrame.setVisible(true);
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
    public void pasarDatosVerificacionDeDisponibilidad(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        // la unidad se vuelve a elegir con el equipo y el periodo que queden
        this.unidadActual = null;
        abrirVerificacionDeDisponibilidad();
    }

    @Override
    public void pasarDatosVerificacionInventario(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        this.unidadActual = null;
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosUnidadMantenimiento(UnidadFisicaDTO unidad) {
        prepararDatosSolicitud();
        unidadMantenimientoFrame.cargarUnidad(unidad, estadoEnElPeriodo(unidad), solicitudActual);
        abrirUnidadMantenimiento();
    }

    @Override
    public void pasarDatosUnidadOcupada(UnidadFisicaDTO unidad) {
        prepararDatosSolicitud();
        unidadOcupadaFrame.cargarUnidad(unidad, estadoEnElPeriodo(unidad), solicitudActual);
        abrirUnidadOcupada();
    }

    @Override
    public void pasarDatosCondicionesContratacion(UnidadFisicaDTO unidad) {
        this.unidadActual = unidad;
        abrirCondicionesContratacion();
    }

    @Override
    public ClienteDTO consultarClientePorRfc(String rfc) {
        return identificacionClienteFrame.buscarPorRfc(rfc);
    }

    @Override
    public void pasarDatosEquipoSolicitado(EquipoSolicitadoDTO equipo) {
        if (this.solicitudActual == null) {
            this.solicitudActual = new SolicitudRentaDTO();
        }
        // la solicitud lleva un equipo, que es el que se evalúa en el análisis técnico y se
        // cotiza; si el asesor elige otro, reemplaza al anterior
        this.solicitudActual.getEquiposSolicitados().clear();
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

    // Completa con datos simulados lo que todavía no viene de otros flujos, sin reemplazar
    // lo que ya se capturó, y vuelve a calcular la cotización con lo elegido hasta ahora
    // (si se cambió el equipo, el periodo o la unidad, los importes cambian con ellos)
    private void prepararDatosCotizacion() {
        prepararDatosSolicitud();
        if (solicitudActual.getRequisitos() == null || solicitudActual.getRequisitos().isEmpty()) {
            List<RequisitoRentaDTO> requisitos = new ArrayList<>();
            requisitos.add(requisito("Identificación oficial", "Cumplida", null));
            requisitos.add(requisito("Depósito de garantía", "Cubierto", 3200.0));
            solicitudActual.setRequisitos(requisitos);
        }
        cotizacionActual = calcularCotizacion(true);
    }

    private RequisitoRentaDTO requisito(String descripcion, String estado, Double monto) {
        RequisitoRentaDTO requisito = new RequisitoRentaDTO();
        requisito.setDescripcion(descripcion);
        requisito.setEsObligatorio(true);
        requisito.setEstadoRequisito(estado);
        requisito.setMonto(monto);
        return requisito;
    }

    // Arma la cotización con lo que se eligió antes: el equipo recomendado en la evaluación
    // técnica, la unidad, los días del periodo y los accesorios propuestos (los opcionales
    // solo si el cliente se queda con ellos). Va aquí mientras Negocio no tenga generarCotizacion.
    private CotizacionDTO calcularCotizacion(boolean conOpcionales) {
        EquipoDTO equipo = equipoRecomendado();
        int dias = diasDelPeriodo();
        List<DetalleCotizacionDTO> detalles = new ArrayList<>();
        DetalleCotizacionDTO renta = new DetalleCotizacionDTO();
        renta.setTipoConcepto("Equipo");
        renta.setDescripcion(equipo.getModelo() + (unidadActual == null ? "" : " (" + unidadActual.getNumeroEconomico() + ")"));
        renta.setCantidad(dias);
        renta.setPrecioUnitario(equipo.getCostoDiario());
        renta.setImporte(equipo.getCostoDiario() == null ? null : dias * equipo.getCostoDiario());
        renta.setEquipo(equipo);
        detalles.add(renta);
        for (AccesorioDTO accesorio : equipo.getAccesorios()) {
            if (conOpcionales || Boolean.TRUE.equals(accesorio.getEsObligatorio())) {
                DetalleCotizacionDTO detalle = new DetalleCotizacionDTO();
                detalle.setTipoConcepto("Accesorio");
                detalle.setDescripcion(accesorio.getNombre());
                detalle.setCantidad(1);
                detalle.setPrecioUnitario(accesorio.getCostoRenta());
                detalle.setImporte(accesorio.getCostoRenta());
                detalle.setAccesorio(accesorio);
                detalles.add(detalle);
            }
        }
        // si a algún concepto le falta precio no se puede dar un total
        Double subtotal = 0.0;
        for (DetalleCotizacionDTO detalle : detalles) {
            subtotal = subtotal == null || detalle.getImporte() == null ? null : subtotal + detalle.getImporte();
        }
        CotizacionDTO cotizacion = new CotizacionDTO();
        cotizacion.setIdCotizacion(1L);
        cotizacion.setFechaEmision(LocalDate.now());
        cotizacion.setDetalles(detalles);
        cotizacion.setSubtotal(subtotal);
        if (subtotal != null) {
            double iva = Math.round(subtotal * IVA * 100) / 100.0;
            cotizacion.setIva(iva);
            cotizacion.setTotal(subtotal + iva);
        }
        return cotizacion;
    }

    // El equipo recomendado en la evaluación técnica con los accesorios que se propusieron. Si
    // la pantalla se abre sin pasar por la evaluación, se usa el del escenario (CAT 320).
    private EquipoDTO equipoRecomendado() {
        List<EquipoSolicitadoDTO> pedidos = solicitudActual.getEquiposSolicitados();
        EvaluacionTecnicaDTO evaluacion = pedidos == null || pedidos.isEmpty() ? null : pedidos.get(0).getEvaluacion();
        if (evaluacion != null && evaluacion.getRecomendado() != null) {
            return evaluacion.getRecomendado();
        }
        for (EquipoDTO equipo : catalogoEquipos()) {
            if (Long.valueOf(2L).equals(equipo.getIdEquipo())) {
                return equipo;
            }
        }
        return catalogoEquipos().get(0);
    }

    private int diasDelPeriodo() {
        PeriodoSolicitadoDTO periodo = solicitudActual.getPeriodo();
        if (periodo.getDiasRenta() != null) {
            return periodo.getDiasRenta();
        }
        if (periodo.getFechaInicio() != null && periodo.getFechaFin() != null) {
            return (int) ChronoUnit.DAYS.between(periodo.getFechaInicio(), periodo.getFechaFin()) + 1;
        }
        return 0;
    }

    // Completa con los datos del escenario lo que F1 todavía no manda a F2, sin reemplazar
    // lo que ya se capturó (así lo de F2 se conserva al avanzar o regresar)
    private void prepararDatosSolicitud() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudRentaDTO();
        }
        if (solicitudActual.getCliente() == null) {
            solicitudActual.setCliente(clienteActual != null ? clienteActual : clienteDeEscenario());
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio(FOLIO);
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

    private ClienteDTO clienteDeEscenario() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setIdCliente(1L);
        cliente.setNombre("Julián Martínez García");
        cliente.setRfc("MARJ8503158A1");
        cliente.setTelefono("6449876543");
        cliente.setCorreo("julianmar12@gmail.com");
        cliente.setTipoCliente("Particular");
        return cliente;
    }

    // Catálogo de equipos mientras no haya persistencia. Solo lleva los datos que da el
    // escenario (la JCB tiene 109 HP y la CAT 320 cuesta $1,500 por día); lo que no se
    // sabe se queda vacío y no descarta al equipo cuando se compara con lo requerido.
    private List<EquipoDTO> catalogoEquipos() {
        if (catalogoEquipos == null) {
            catalogoEquipos = new ArrayList<>();
            EquipoDTO jcb = equipo(1L, "JCB", "Retroexcavadora JCB");
            jcb.setPotencia(109.0);
            EquipoDTO cat320 = equipo(2L, "CAT", "Excavadora CAT 320");
            cat320.setCostoDiario(1500.0);
            cat320.setAccesorios(accesoriosDeExcavadora());
            EquipoDTO cat336 = equipo(3L, "CAT", "Excavadora CAT 336");
            cat336.setAccesorios(accesoriosDeExcavadora());
            catalogoEquipos.add(jcb);
            catalogoEquipos.add(cat320);
            catalogoEquipos.add(cat336);
        }
        return catalogoEquipos;
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
            motivos.add("potencia de " + RequerimientosTecnicos.formatoNumero(equipo.getPotencia()) + " HP; se piden "
                    + RequerimientosTecnicos.formatoNumero(potencia) + " HP como mínimo");
        }
        Double profundidad = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.PROFUNDIDAD);
        if (profundidad != null && equipo.getProfundidadExcavacion() != null && equipo.getProfundidadExcavacion() < profundidad) {
            motivos.add("excava hasta " + RequerimientosTecnicos.formatoNumero(equipo.getProfundidadExcavacion())
                    + " m; se piden " + RequerimientosTecnicos.formatoNumero(profundidad) + " m");
        }
        Double peso = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.PESO);
        if (peso != null && equipo.getPeso() != null && equipo.getPeso() > peso) {
            motivos.add("pesa " + RequerimientosTecnicos.formatoNumero(equipo.getPeso()) + " t; el máximo es "
                    + RequerimientosTecnicos.formatoNumero(peso) + " t");
        }
        Double ancho = RequerimientosTecnicos.valor(requerimientos, RequerimientosTecnicos.ANCHO);
        if (ancho != null && equipo.getAnchoOperacion() != null && equipo.getAnchoOperacion() > ancho) {
            motivos.add("mide " + RequerimientosTecnicos.formatoNumero(equipo.getAnchoOperacion()) + " m de ancho; el acceso es de "
                    + RequerimientosTecnicos.formatoNumero(ancho) + " m");
        }
        return motivos.isEmpty() ? null : String.join(", ", motivos);
    }

    private EquipoDTO equipo(Long id, String marca, String modelo) {
        EquipoDTO equipo = new EquipoDTO();
        equipo.setIdEquipo(id);
        equipo.setMarca(marca);
        equipo.setModelo(modelo);
        equipo.setAccesorios(new ArrayList<>());
        return equipo;
    }

    private List<AccesorioDTO> accesoriosDeExcavadora() {
        List<AccesorioDTO> accesorios = new ArrayList<>();
        accesorios.add(accesorio(1L, "Kit de señalización", 300.0, true));
        accesorios.add(accesorio(2L, "Cucharón adicional 1.20 m", 200.0, false));
        return accesorios;
    }

    private AccesorioDTO accesorio(Long id, String nombre, Double costo, boolean obligatorio) {
        AccesorioDTO accesorio = new AccesorioDTO();
        accesorio.setIdAccesorio(id);
        accesorio.setNombre(nombre);
        accesorio.setCostoRenta(costo);
        accesorio.setEsObligatorio(obligatorio);
        return accesorio;
    }

    // Unidades físicas mientras no haya persistencia: solo las del escenario, que son de la
    // CAT 320 (M-001 disponible, M-002 en mantenimiento y M-003 ocupada del 13 al 15 de
    // septiembre). De los otros equipos el escenario no da unidades.
    private List<UnidadFisicaDTO> unidadesDe(EquipoDTO equipo) {
        if (unidades == null) {
            unidades = new ArrayList<>();
            unidades.add(unidad(1L, "M-001", "Disponible"));
            unidades.add(unidad(2L, "M-002", "En mantenimiento"));
            UnidadFisicaDTO m003 = unidad(3L, "M-003", "Disponible");
            OcupacionUnidadDTO ocupacion = new OcupacionUnidadDTO();
            ocupacion.setFechaInicio(LocalDate.of(2026, 9, 13));
            ocupacion.setFechaFin(LocalDate.of(2026, 9, 15));
            m003.getOcupaciones().add(ocupacion);
            unidades.add(m003);
        }
        return equipo != null && Long.valueOf(2L).equals(equipo.getIdEquipo()) ? unidades : new ArrayList<>();
    }

    private UnidadFisicaDTO unidad(Long id, String numeroEconomico, String estado) {
        UnidadFisicaDTO unidad = new UnidadFisicaDTO();
        unidad.setIdUnidad(id);
        unidad.setNumeroEconomico(numeroEconomico);
        unidad.setEstadoUnidad(estado);
        unidad.setOcupaciones(new ArrayList<>());
        return unidad;
    }

    // Estado de cada unidad del equipo recomendado durante el periodo de la solicitud. Va
    // aquí mientras Negocio no tenga evaluarDisponibilidad.
    private Map<UnidadFisicaDTO, String> evaluarDisponibilidad() {
        Map<UnidadFisicaDTO, String> estados = new LinkedHashMap<>();
        for (UnidadFisicaDTO unidad : unidadesDe(equipoRecomendado())) {
            estados.put(unidad, estadoEnElPeriodo(unidad));
        }
        return estados;
    }

    // "Disponible", "En mantenimiento" u "Ocupada del ... al ..." si alguna de sus
    // ocupaciones se cruza con el periodo de la solicitud
    private String estadoEnElPeriodo(UnidadFisicaDTO unidad) {
        if (!"Disponible".equals(unidad.getEstadoUnidad())) {
            return unidad.getEstadoUnidad();
        }
        PeriodoSolicitadoDTO periodo = solicitudActual.getPeriodo();
        if (periodo == null || periodo.getFechaInicio() == null || periodo.getFechaFin() == null) {
            return "Disponible";
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (OcupacionUnidadDTO ocupacion : unidad.getOcupaciones()) {
            if (!ocupacion.getFechaInicio().isAfter(periodo.getFechaFin())
                    && !ocupacion.getFechaFin().isBefore(periodo.getFechaInicio())) {
                return "Ocupada del " + ocupacion.getFechaInicio().format(formato) + " al "
                        + ocupacion.getFechaFin().format(formato);
            }
        }
        return "Disponible";
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
