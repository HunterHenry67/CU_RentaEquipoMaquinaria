/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frames;

import DTOs.AccesorioDTO;
import DTOs.ClienteDTO;
import DTOs.CotizacionDTO;
import DTOs.DecisionClienteDTO;
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
import DTOs.UsuarioDTO;
import Temp.AlmacenTemporal;
import Temp.IAlmacenTemporal;
import java.time.LocalDate;
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

    private static final double TARIFA_DIARIA_SIMULADA = 1500.0;
    private static final double IVA_SIMULADO = 0.16;

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
    private CambiarPeriodo cambiarPeriodo;
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
    private List<EquipoDTO> catalogoEquipos;
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
        this.iniciarSesionFrame = new InicioSesionFrame(this);
        this.menuPrincipalFrame = new MenuPrincipalFrame(this);
        this.identificacionClienteFrame = new IdentificacionClienteFrame(this, this.almacenTemporal);
        this.registrarClienteFrame = new RegistroClienteFrame(this);
        this.busquedaEquipoForm = new BusquedaEquipoFrame(this, this.almacenTemporal);
        this.solicitudRentaForm = new SolicitudRentaFrame(this);
        this.analisisRequerimientoFrame = new AnalisisRequerimientoFrame(this);
        this.solicitudDatosFaltantesFrame = new SolicitudDatosFaltantesFrame(this);
        this.equiposCompatiblesFrame = new EquiposCompatiblesFrame(this);
        this.sinEquipoCompatibleFrame = new SinEquipoCompatibleFrame(this);
        this.seleccionAccesoriosFrame = new SeleccionAccesoriosFrame(this);
        this.evaluacionTecnicaFrame = new EvaluacionTecnicaFrame(this);
        this.cambiarPeriodo = new CambiarPeriodo(this);
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

        this.abrirInciarSesión();
    }

    
    @Override
    public void abrirInciarSesión() {
        ocultarTodo();
        iniciarSesionFrame.setVisible(true);
    }

    @Override
    public void abrirMenuPrincipal() {
        
        solicitudActual = null;
        cotizacionActual = null;
        clienteActual = null;
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
        ocultarTodo();
        cambiarPeriodo.setVisible(true);
    }

    @Override
    public void abrirVerificacionInventario() {
        prepararDatosSolicitud();
        ocultarTodo();
        verificacionInventarioFrame.cargarSolicitud(solicitudActual, unidadesSegunModelo());   // ⬅ aquí se usa
        verificacionInventarioFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadMantenimiento() {
        ocultarTodo();
        unidadMantenimientoFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadMantenimiento(UnidadFisicaDTO unidad) {
        prepararDatosSolicitud();
        ocultarTodo();
        unidadMantenimientoFrame.cargarMantenimiento(solicitudActual, unidad);
        unidadMantenimientoFrame.setVisible(true);
    }

    @Override
    public void abrirUnidadOcupada() {
        ocultarTodo();
        unidadOcupadaFrame.setVisible(true);
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
        ocultarTodo();
        calendarioUnidadFrame.setVisible(true);
    }

    @Override
    public void abrirCondicionesContratacion() {
        prepararDatosSolicitud();
        ocultarTodo();
        condicionesContratacionFrame.cargarCondiciones(solicitudActual);
        condicionesContratacionFrame.setVisible(true);
    }

    @Override
    public void abrirPropuestaRenta() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        ocultarTodo();
        propuestaRentaFrame.cargarDatos(solicitudActual);
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
    public void abrirVerificacionCondicion(boolean conCucharon) {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        ocultarTodo();
        verificacionCondicionFrame.cargarDatos(solicitudActual, conCucharon);
        verificacionCondicionFrame.setVisible(true);
    }

    @Override
    public void abrirOrdenEntrega(boolean conCucharon, boolean finalizada) {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        ocultarTodo();
        ordenEntregaFrame.cargarDatos(solicitudActual, conCucharon, finalizada);
        ordenEntregaFrame.setVisible(true);
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
    public void abrirOrdenEntregaRechazada(String motivo) {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
       
        cotizacionActual.setDecision(new DecisionClienteDTO(LocalDate.now(), "RECHAZA", motivo));
        solicitudActual.setEstadoSolicitud("Rechazada");
        ocultarTodo();
        ordenEntregaFrame.cargarDatosRechazada(solicitudActual, motivo);
        ordenEntregaFrame.setVisible(true);
    }

    // Estos métodos no servirán para sobreecargar los datos en las pantallas
    @Override
    public void pasarDatosUsuario(UsuarioDTO dto) {
        this.usuarioDTO = dto;
        abrirMenuPrincipal();
    }

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
    public void pasarDatosUnidadSeleccionada(SolicitudRentaDTO dto) {
        this.solicitudActual = dto;
        abrirCondicionesContratacion();
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
            solicitudActual.setFolio("FOL-89241");
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
    private CotizacionDTO armarCotizacion() {
        EquipoDTO equipo = equipoRecomendado();
        Integer dias = solicitudActual.getPeriodo().getDiasRenta();
        if (equipo == null || dias == null) {
            // sin evaluación técnica todavía, se usa el escenario del almacén
            return new CotizacionDTO(1L, LocalDate.now(), LocalDate.now().plusDays(15),
                    9500.0, 1520.0, 11020.0, almacenTemporal.getListaDetalleCotizacionTemp(), null);
        }
        double tarifa = equipo.getCostoDiario() != null ? equipo.getCostoDiario() : TARIFA_DIARIA_SIMULADA;
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
            solicitudActual.setFolio("FOL-89241");
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

        // La ocupación cubre desde el día siguiente al inicio hasta el fin del periodo
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

    // Esta clase la señalo porque no se si se reemplzará por la que está en almacenamiento.
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
        if (cambiarPeriodo != null) {
            cambiarPeriodo.setVisible(false);
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
