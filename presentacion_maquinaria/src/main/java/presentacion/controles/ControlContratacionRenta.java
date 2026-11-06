package presentacion.controles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DecisionClienteDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import objetosdto.UsuarioDTO;
import subsistemaContratacionRenta.ISubsistemaContratacionRenta;
import subsistemaInicioSesion.ISubsistemaInicioSesion;

/**
 * @author Home
 */
public class ControlContratacionRenta implements IContratacionRenta {

    // Las condiciones que el asesor revisa antes de generar la orden (las casillas de la pantalla)
    private static final List<String> CONDICIONES = Arrays.asList("Cliente", "Equipo y Unidad", "Accesorios aceptados", "Periodo", "Tarifa");

    private final ISubsistemaContratacionRenta subsistema;
    private final ISubsistemaInicioSesion subsistemaSesion;
    private ICoordinadorAplicacion coordinador;
    private ClienteDTO clienteActual;
    private SolicitudRentaDTO solicitudActual;
    private CotizacionDTO cotizacionActual;
    private ContratacionRentaDTO contratacionActual;
    private CotizacionDTO cotizacionContratacion;
    private Map<EquipoDTO, String> equiposEvaluados = new LinkedHashMap<>();

    public ControlContratacionRenta(ISubsistemaContratacionRenta subsistema, ISubsistemaInicioSesion subsistemaSesion) {
        this.subsistema = subsistema;
        this.subsistemaSesion = subsistemaSesion;
    }

    public void setCoordinador(ICoordinadorAplicacion coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public void abrirIniciarSesion() {
        coordinador.mostrarInicioSesion();
    }

    @Override
    public void abrirMenuPrincipal() {
        soltarOperacion();
        coordinador.mostrarMenuPrincipal();
    }

    @Override
    public void abrirIdentificacionCliente() {
        coordinador.mostrarIdentificacionCliente(subsistema.obtenerClientes());
    }

    @Override
    public void abrirBusquedaEquipo() {
        coordinador.mostrarBusquedaEquipo(subsistema.obtenerCatalogoEquipo());
    }

    @Override
    public void abrirSolicitudRenta() {
        coordinador.mostrarSolicitudRenta();
    }

    @Override
    public void abrirAnalisisRequerimiento() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarAnalisisRequerimiento(solicitudActual);
    }

    private void abrirSolicitudDatosFaltantes() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarSolicitudDatosFaltantes(solicitudActual);
    }

    @Override
    public void abrirEquiposCompatibles() {
        if (!haySolicitud()) {
            return;
        }
        equiposEvaluados = subsistema.buscarEquiposCompatibles(solicitudActual.getRequerimientos());
        coordinador.mostrarEquiposCompatibles(solicitudActual, equiposAMostrar());
    }

    private void abrirSinEquipoCompatible() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarSinEquipoCompatible(solicitudActual);
    }

    @Override
    public void abrirSeleccionAccesorios() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarSeleccionAccesorios(solicitudActual, accesoriosDelRecomendado());
    }

    @Override
    public void abrirEvaluacionTecnica() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarEvaluacionTecnica(solicitudActual);
    }

    @Override
    public void abrirCambiarPeriodo() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarCambiarPeriodo(solicitudActual);
    }

    @Override
    public void abrirVerificacionInventario() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarVerificacionInventario(solicitudActual, unidadesDelRecomendado());
    }

    @Override
    public void abrirCalendarioUnidad() {
        if (!haySolicitud()) {
            return;
        }
        coordinador.mostrarCalendarioUnidad(solicitudActual);
    }

    @Override
    public void abrirCondicionesContratacion() {
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        coordinador.mostrarCondicionesContratacion(solicitudActual, cotizacionActual);
    }

    @Override
    public void abrirCotizacion() {
        if (!haySolicitud()) {
            return;
        }
        cotizacionActual = null;
        if (!hayCotizacion()) {
            return;
        }
        coordinador.mostrarCotizacion(solicitudActual, cotizacionActual);
    }

    @Override
    public void abrirRequisitosRenta() {
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        coordinador.mostrarRequisitosRenta(solicitudActual);
    }

    @Override
    public void abrirPropuestaRenta() {
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        coordinador.mostrarPropuestaRenta(solicitudActual, cotizacionActual);
    }

    @Override
    public void abrirPropuestaPendiente() {
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        coordinador.mostrarPropuestaPendiente(solicitudActual);
    }

    @Override
    public void abrirMotivoRechazo() {
        coordinador.mostrarMotivoRechazo();
    }

    // La pantalla de solicitud pide pasar a la cancelación; se confirma antes
    @Override
    public void abrirCancelacionSolicitud() {
        if (coordinador.confirmar(Mensaje.CONFIRMAR_CANCELAR_SOLICITUD)) {
            coordinador.mostrarCancelacionSolicitud();
        }
    }

    // La X de las pantallas que dejan la operación: se confirma antes de volver al menú
    @Override
    public void dejarOperacion() {
        if (coordinador.confirmar(Mensaje.CONFIRMAR_DEJAR_OPERACION)) {
            abrirMenuPrincipal();
        }
    }

    // Regresar de la solicitud: se deja lo que no se registró y se vuelve a elegir al cliente
    @Override
    public void regresarAIdentificacionCliente() {
        if (coordinador.confirmar(Mensaje.CONFIRMAR_REGRESAR_SOLICITUD)) {
            soltarOperacion();
            abrirIdentificacionCliente();
        }
    }

    // ================================================================ inicio de sesión y clientes
    @Override
    public void pasarDatosUsuario(UsuarioDTO usuario) {
        if (faltanDatos(Mensaje.FALTAN_DATOS_SESION, ValidadorPresentacion.faltantesUsuario(usuario))) {
            return;
        }
        if (!ValidadorPresentacion.correoValido(usuario.getCorreo())) {
            coordinador.mostrarAviso(Mensaje.CORREO_SESION_INVALIDO);
            return;
        }
        if (!ValidadorPresentacion.tieneLongitudMinima(usuario.getContrasenia(), 8)) {
            coordinador.mostrarAviso(Mensaje.CONTRASENIA_CORTA);
            return;
        }
        if (subsistemaSesion.autenticarUsuario(usuario.getCorreo().trim(), usuario.getContrasenia()) == null) {
            coordinador.mostrarAviso(Mensaje.CREDENCIALES_INCORRECTAS);
            return;
        }
        coordinador.mostrarMensaje(Mensaje.SESION_INICIADA);
        abrirMenuPrincipal();
    }

    @Override
    public void buscarClientes(String filtro, String texto) {
        if (ValidadorPresentacion.textoVacio(texto)) {
            coordinador.mostrarClientesEncontrados(subsistema.obtenerClientes());
            return;
        }
        if ("Teléfono".equals(filtro) && !ValidadorPresentacion.numeroEntero(texto.trim())) {
            coordinador.mostrarAviso(Mensaje.TELEFONO_BUSQUEDA_INVALIDO);
            return;
        }
        List<ClienteDTO> encontrados = subsistema.buscarClientes(filtro, texto);
        coordinador.mostrarClientesEncontrados(encontrados);
        if (encontrados.isEmpty()) {
            coordinador.mostrarMensaje(Mensaje.CLIENTE_NO_ENCONTRADO);
        }
    }

    @Override
    public void pasarDatosSolicitudRenta(ClienteDTO cliente) {
        if (!ValidadorPresentacion.seleccionValida(cliente)) {
            coordinador.mostrarAviso(Mensaje.CLIENTE_SIN_SELECCIONAR);
            return;
        }
        this.clienteActual = cliente;
        coordinador.mostrarSolicitudNueva(cliente, subsistema.siguienteFolio());
    }

    // Registrar cliente nuevo (null) o modificar uno que ya existe
    @Override
    public void pasarDatosRegistroCliente(ClienteDTO cliente) {
        this.clienteActual = cliente;
        coordinador.mostrarRegistroCliente(cliente);
    }

    @Override
    public void modificarCliente(ClienteDTO cliente) {
        if (!ValidadorPresentacion.seleccionValida(cliente)) {
            coordinador.mostrarAviso(Mensaje.CLIENTE_A_MODIFICAR_SIN_SELECCIONAR);
            return;
        }
        pasarDatosRegistroCliente(cliente);
    }

    @Override
    public void registrarCliente(ClienteDTO cliente) {
        if (faltanDatos(Mensaje.FALTAN_DATOS_CLIENTE, ValidadorPresentacion.faltantesCliente(cliente))) {
            return;
        }
        ClienteEmpresaDTO empresa = cliente instanceof ClienteEmpresaDTO e ? e : null;
        if (empresa != null && faltanDatos(Mensaje.FALTAN_DATOS_CLIENTE, ValidadorPresentacion.faltantesEncargado(empresa))) {
            return;
        }
        if (empresa != null && ValidadorPresentacion.textoVacio(empresa.getArchivoIdentificacionEncargado())) {
            coordinador.mostrarAviso(Mensaje.FALTA_IDENTIFICACION_ENCARGADO);
            return;
        }
        if (!ValidadorPresentacion.rfcValido(cliente.getRfc())) {
            coordinador.mostrarAviso(Mensaje.RFC_INVALIDO);
            return;
        }
        if (!ValidadorPresentacion.telefonoValido(cliente.getTelefono())) {
            coordinador.mostrarAviso(Mensaje.TELEFONO_INVALIDO);
            return;
        }
        if (!ValidadorPresentacion.correoValido(cliente.getCorreo())) {
            coordinador.mostrarAviso(Mensaje.CORREO_CLIENTE_INVALIDO);
            return;
        }
        if (empresa != null && !ValidadorPresentacion.telefonoValido(empresa.getTelefonoEncargadoEmpresa())) {
            coordinador.mostrarAviso(Mensaje.TELEFONO_ENCARGADO_INVALIDO);
            return;
        }
        if (subsistema.rfcEnUso(cliente, subsistema.buscarClientePorRfc(cliente.getRfc()))) {
            coordinador.mostrarAviso(Mensaje.RFC_REPETIDO);
            return;
        }
        cliente.setRfc(cliente.getRfc().trim().toUpperCase());
        Mensaje mensaje;
        if (cliente.getIdCliente() == null) {
            cliente = subsistema.registrarClienteNuevo(cliente);
            mensaje = empresa != null ? Mensaje.EMPRESA_REGISTRADA : Mensaje.CLIENTE_REGISTRADO;
        } else {
            subsistema.actualizarCliente(cliente);
            mensaje = empresa != null ? Mensaje.EMPRESA_ACTUALIZADA : Mensaje.CLIENTE_ACTUALIZADO;
        }
        coordinador.mostrarMensaje(mensaje);
        pasarDatosSolicitudRenta(cliente);
    }

    @Override
    public void buscarEquipos(String texto) {
        List<EquipoDTO> encontrados = ValidadorPresentacion.textoVacio(texto) ? subsistema.obtenerCatalogoEquipo() : subsistema.buscarEquipos(texto);
        coordinador.mostrarEquiposEncontrados(encontrados);
        if (encontrados.isEmpty()) {
            coordinador.mostrarMensaje(Mensaje.EQUIPO_NO_ENCONTRADO);
        }
    }

    @Override
    public void pasarDatosEquipoSolicitado(EquipoSolicitadoDTO equipo) {
        if (!ValidadorPresentacion.seleccionValida(equipo)) {
            coordinador.mostrarAviso(Mensaje.EQUIPO_SIN_SELECCIONAR);
            return;
        }
        if (this.solicitudActual == null) {
            this.solicitudActual = new SolicitudRentaDTO();
        }
        boolean yaEsta = false;
        for (EquipoSolicitadoDTO ya : this.solicitudActual.getEquiposSolicitados()) {
            yaEsta = yaEsta || Objects.equals(ya.getDescripcionEquipo(), equipo.getDescripcionEquipo());
        }
        if (!yaEsta) {
            this.solicitudActual.getEquiposSolicitados().add(equipo);
        }
        coordinador.mostrarEquiposSolicitados(this.solicitudActual.getEquiposSolicitados());
        coordinador.mostrarSolicitudRenta();
    }

    @Override
    public void quitarEquipoSolicitado(EquipoSolicitadoDTO equipo) {
        if (!ValidadorPresentacion.seleccionValida(equipo)) {
            coordinador.mostrarAviso(Mensaje.EQUIPO_SIN_SELECCIONAR);
            return;
        }
        if (solicitudActual == null) {
            return;
        }
        solicitudActual.getEquiposSolicitados().removeIf(ya -> Objects.equals(ya.getDescripcionEquipo(), equipo.getDescripcionEquipo()));
        coordinador.mostrarEquiposSolicitados(solicitudActual.getEquiposSolicitados());
    }

    @Override
    public void registrarSolicitud(SolicitudRentaDTO solicitud) {
        PeriodoSolicitadoDTO periodo = solicitud.getPeriodo();
        if (faltanDatos(Mensaje.FALTAN_DATOS_SOLICITUD, ValidadorPresentacion.faltantesSolicitud(solicitud))) {
            return;
        }
        if (!ValidadorPresentacion.esPeriodoValido(periodo.getFechaInicio(), periodo.getFechaFin())) {
            coordinador.mostrarAviso(Mensaje.FECHAS_SOLICITUD_INVALIDAS);
            return;
        }
        if (solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty()) {
            coordinador.mostrarAviso(Mensaje.FALTA_EQUIPO_SOLICITUD);
            return;
        }
        periodo.setDiasRenta(subsistema.diasRenta(periodo));
        ValidadorPresentacion.normalizar(solicitud.getRequerimientos());
        if (solicitud.getCliente() == null) {
            solicitud.setCliente(clienteActual);
        }
        solicitud.setFechaSolicitud(LocalDate.now());
        solicitud.setEstadoSolicitud("Pendiente");
        conservarLoCapturado(solicitud);
        // con el mismo folio se actualiza: un segundo clic no crea otra solicitud
        solicitudActual = solicitud;
        cotizacionActual = null;
        guardarSolicitud();
        int equipos = solicitud.getEquiposSolicitados().size();
        if (equipos > 1) {
            coordinador.mostrarMensaje(Mensaje.SOLICITUD_REGISTRADA_VARIOS_EQUIPOS, equipos);
        } else {
            coordinador.mostrarMensaje(Mensaje.SOLICITUD_REGISTRADA);
        }
        abrirAnalisisRequerimiento();
    }

    @Override
    public void pasarDatosCancelacion(SolicitudRentaDTO solicitud) {
        if (ValidadorPresentacion.textoVacio(solicitud.getMotivoCancelacion())) {
            coordinador.mostrarAviso(Mensaje.FALTA_RAZON_CANCELACION);
            return;
        }
        if (!coordinador.confirmar(Mensaje.CONFIRMAR_REGISTRAR_CANCELACION)) {
            return;
        }
        // se cancela la solicitud que se estaba capturando, con el folio y el cliente de la pantalla
        if (solicitudActual == null) {
            solicitudActual = solicitud;
        }
        if (solicitudActual.getCliente() == null) {
            solicitudActual.setCliente(clienteActual);
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio(solicitud.getFolio());
        }
        solicitudActual.setMotivoCancelacion(solicitud.getMotivoCancelacion().trim());
        ResultadoCancelacionDTO resultado = subsistema.cancelarSolicitudRenta(solicitudActual);
        if (!resultado.getCancelada()) {
            coordinador.mostrarAviso(Mensaje.CANCELACION_NO_PERMITIDA, resultado.getMotivo());
            return;
        }
        coordinador.mostrarMensaje(Mensaje.CANCELACION_REGISTRADA);
        abrirMenuPrincipal();
    }

    @Override
    public void pasarDatosAnalisisRequerimientos(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_DATOS_FALTANTES)) {
            return;
        }
        // si sigue faltando lo mismo que antes, el cliente todavía no respondió nada
        String faltan = ValidadorPresentacion.faltantesRequerimiento(requerimientos);
        if (faltan.equals(ValidadorPresentacion.faltantesRequerimiento(solicitudActual.getRequerimientos()))) {
            coordinador.mostrarAviso(Mensaje.FALTA_RESPUESTA_CLIENTE);
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
        solicitudActual.setEstadoSolicitud(faltan.isEmpty() ? "En evaluación técnica" : "En espera de información");
        guardarSolicitud();
        abrirAnalisisRequerimiento();
    }

    @Override
    public void pasarDatosSolicitudDatosFaltantes(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_ANALISIS)) {
            return;
        }
        if (ValidadorPresentacion.faltantesRequerimiento(requerimientos).isEmpty()) {
            coordinador.mostrarAviso(Mensaje.SIN_DATOS_FALTANTES);
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
        solicitudActual.setEstadoSolicitud("En espera de información");
        guardarSolicitud();
        abrirSolicitudDatosFaltantes();
    }

    // Regresar del análisis: lo que se escribió se queda en la solicitud; un número mal escrito se avisa para corregirlo
    @Override
    public void regresarDeAnalisis(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_ANALISIS)) {
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
        guardarSolicitud();
        coordinador.mostrarTerrenoSolicitud(ValidadorPresentacion.texto(requerimientos, ValidadorPresentacion.TERRENO));
        abrirSolicitudRenta();
    }

    @Override
    public void pasarDatosEquiposCompatibles(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_ANALISIS)) {
            return;
        }
        if (faltanDatos(Mensaje.FALTAN_DATOS_ANALISIS, ValidadorPresentacion.faltantesRequerimiento(requerimientos))) {
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
        solicitudActual.setEstadoSolicitud("En evaluación técnica");
        guardarSolicitud();
        abrirEquiposCompatibles();
    }

    @Override
    public void ningunEquipoCumple() {
        if (!haySolicitud()) {
            return;
        }
        if (equiposEvaluados.containsValue(null) && !coordinador.confirmar(Mensaje.CONFIRMAR_NINGUNO_CUMPLE)) {
            return;
        }
        EvaluacionTecnicaDTO evaluacion = evaluacionParaGuardar();
        evaluacion.setResultado("Sin equipo compatible");
        evaluacion.setRecomendado(null);
        evaluacion.setAlternativo(null);
        evaluacion.setFechaEvaluacion(LocalDate.now());
        evaluacion.setMotivoIncumplimiento(resumenIncumplimientos());
        abrirSinEquipoCompatible();
    }

    @Override
    public void cerrarEvaluacion(EvaluacionTecnicaDTO capturada) {
        if (!haySolicitud()) {
            return;
        }
        if (ValidadorPresentacion.textoVacio(capturada.getMotivoIncumplimiento())) {
            coordinador.mostrarAviso(Mensaje.FALTA_MOTIVO_EVALUACION);
            return;
        }
        evaluacionParaGuardar().setMotivoIncumplimiento(capturada.getMotivoIncumplimiento());
        solicitudActual.setEstadoSolicitud("Sin solución técnica");
        guardarSolicitud();
        coordinador.mostrarMensaje(Mensaje.EVALUACION_CERRADA, solicitudActual.getFolio());
        abrirMenuPrincipal();
    }

    @Override
    public void pasarDatosSeleccionAccesorios(EvaluacionTecnicaDTO seleccion) {
        if (!haySolicitud()) {
            return;
        }
        if (!ValidadorPresentacion.seleccionValida(seleccion.getRecomendado())) {
            coordinador.mostrarAviso(equiposEvaluados.containsValue(null) ? Mensaje.FALTA_ELEGIR_RECOMENDADO : Mensaje.SIN_EQUIPO_COMPATIBLE);
            return;
        }
        if (seleccion.getAlternativo() != null
                && Objects.equals(seleccion.getAlternativo().getIdEquipo(), seleccion.getRecomendado().getIdEquipo())) {
            coordinador.mostrarAviso(Mensaje.EQUIPOS_IGUALES);
            return;
        }
        EvaluacionTecnicaDTO evaluacion = evaluacionParaGuardar();
        evaluacion.setResultado("Cumple");
        evaluacion.setMotivoIncumplimiento(motivoDelPedido());
        evaluacion.setFechaEvaluacion(LocalDate.now());
        evaluacion.setRecomendado(conservar(evaluacion.getRecomendado(), seleccion.getRecomendado()));
        evaluacion.setAlternativo(seleccion.getAlternativo() == null ? null : conservar(evaluacion.getAlternativo(), seleccion.getAlternativo()));
        guardarSolicitud();
        abrirSeleccionAccesorios();
    }

    @Override
    public void pasarDatosEvaluacionTecnica(List<AccesorioDTO> opcionalesElegidos) {
        if (!haySolicitud()) {
            return;
        }
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitudActual);
        if (evaluacion == null || !ValidadorPresentacion.seleccionValida(evaluacion.getRecomendado())) {
            coordinador.mostrarAviso(Mensaje.FALTA_RECOMENDADO);
            abrirEquiposCompatibles();
            return;
        }
        evaluacion.getRecomendado().setAccesorios(subsistema.obtenerAccesoriosPropuestos(evaluacion.getRecomendado(), opcionalesElegidos));
        // con otro equipo u otros accesorios la cotización se vuelve a armar
        cotizacionActual = null;
        guardarSolicitud();
        abrirEvaluacionTecnica();
    }

    @Override
    public void pasarDatosVerificacionInventario(SolicitudRentaDTO solicitud) {
        solicitud.setEstadoSolicitud("En verificación de disponibilidad");
        this.solicitudActual = solicitud;
        guardarSolicitud();
        coordinador.mostrarMensaje(Mensaje.EVALUACION_REGISTRADA);
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosUnidadSeleccionada(UnidadFisicaDTO unidad) {
        if (!ValidadorPresentacion.seleccionValida(unidad)) {
            coordinador.mostrarAviso(Mensaje.UNIDAD_SIN_SELECCIONAR);
            return;
        }
        if (!subsistema.estaDisponible(unidad)) {
            coordinador.mostrarAviso(Mensaje.UNIDAD_NO_DISPONIBLE);
            return;
        }
        if (!haySolicitud()) {
            return;
        }
        solicitudActual.setUnidadSeleccionada(unidad);
        solicitudActual.setEstadoSolicitud("Unidad seleccionada");
        guardarSolicitud();
        // antes de las condiciones se ven los días del periodo de la unidad elegida
        abrirCalendarioUnidad();
    }

    @Override
    public void verMotivoUnidad(UnidadFisicaDTO unidad) {
        if (!ValidadorPresentacion.seleccionValida(unidad)) {
            coordinador.mostrarAviso(Mensaje.UNIDAD_SIN_SELECCIONAR);
            return;
        }
        if (!haySolicitud()) {
            return;
        }
        String estado = unidad.getEstadoUnidad() == null ? "" : unidad.getEstadoUnidad();
        if (estado.startsWith("En mantenimiento")) {
            coordinador.mostrarUnidadMantenimiento(solicitudActual, unidad);
        } else if (estado.startsWith("Ocupada")) {
            coordinador.mostrarUnidadOcupada(solicitudActual, unidad);
        } else {
            coordinador.mostrarMensaje(Mensaje.UNIDAD_DISPONIBLE);
        }
    }

    // Unidad en mantenimiento u ocupada: se toma el equipo alternativo de la evaluación y se buscan sus unidades
    @Override
    public void usarEquipoAlternativo() {
        if (!haySolicitud()) {
            return;
        }
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitudActual);
        if (evaluacion == null || evaluacion.getAlternativo() == null) {
            coordinador.mostrarMensaje(Mensaje.SIN_ALTERNATIVA);
            abrirEquiposCompatibles();
            return;
        }
        EquipoDTO recomendado = evaluacion.getRecomendado();
        EquipoDTO alternativo = evaluacion.getAlternativo();
        // al alternativo se le proponen los mismos accesorios que se eligieron para el recomendado
        alternativo.setAccesorios(subsistema.obtenerAccesoriosPropuestos(alternativo, recomendado == null ? null : recomendado.getAccesorios()));
        evaluacion.setRecomendado(alternativo);
        evaluacion.setAlternativo(recomendado);
        solicitudActual.setUnidadSeleccionada(null);
        cotizacionActual = null;
        guardarSolicitud();
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosCambioPeriodo(PeriodoSolicitadoDTO periodo) {
        if (periodo == null || !ValidadorPresentacion.fechaValida(periodo.getFechaInicio()) || !ValidadorPresentacion.fechaValida(periodo.getFechaFin())) {
            coordinador.mostrarAviso(Mensaje.FALTAN_FECHAS_PERIODO);
            return;
        }
        if (!ValidadorPresentacion.esPeriodoValido(periodo.getFechaInicio(), periodo.getFechaFin())) {
            coordinador.mostrarAviso(Mensaje.FECHAS_PERIODO_INVALIDAS);
            return;
        }
        if (!haySolicitud()) {
            return;
        }
        periodo.setDiasRenta(subsistema.diasRenta(periodo));
        solicitudActual.setPeriodo(periodo);
        coordinador.mostrarPeriodoSolicitud(periodo);
        solicitudActual.setUnidadSeleccionada(null);
        cotizacionActual = null;
        guardarSolicitud();
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosRequisitosRenta(CotizacionDTO cotizacion) {
        this.cotizacionActual = cotizacion;
        abrirRequisitosRenta();
    }

    @Override
    public void pasarDatosPropuestaRenta(List<RequisitoRentaDTO> requisitos) {
        if (!haySolicitud()) {
            return;
        }
        solicitudActual.setRequisitos(requisitos);
        if (!subsistema.verificarRequisitosRenta(requisitos)) {
            coordinador.mostrarAviso(Mensaje.REQUISITOS_PENDIENTES);
            coordinador.mostrarRequisitosPendientes();
            return;
        }
        guardarSolicitud();
        coordinador.mostrarMensaje(Mensaje.REQUISITOS_VALIDADOS);
        abrirPropuestaRenta();
    }

    @Override
    public void pasarDatosDecisionCliente(DecisionClienteDTO decision) {
        if ("RECHAZA".equals(decision.getTipoDecision()) && ValidadorPresentacion.textoVacio(decision.getObservaciones())) {
            coordinador.mostrarAviso(Mensaje.FALTA_MOTIVO_RECHAZO);
            return;
        }
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        decision.setFechaDecision(LocalDate.now());
        cotizacionActual.setDecision(decision);
        subsistema.actualizarCotizacion(cotizacionActual);
        contratacionActual = armarContratacion(decision);
        cotizacionContratacion = cotizacionDe(contratacionActual);
        String estado = contratacionActual.getEstado();
        if ("RECHAZADA".equals(estado)) {
            solicitudActual.setEstadoSolicitud("Rechazada");
            guardarSolicitud();
            // lo que se muestra es la propuesta que rechazó el cliente, con su total
            coordinador.mostrarOrdenRechazada(solicitudActual, contratacionActual, cotizacionActual, decision.getObservaciones().trim());
        } else if ("PENDIENTE POR ACCESORIO".equals(estado)) {
            // sin el accesorio indispensable no hay condiciones que revisar: la solicitud queda pendiente
            solicitudActual.setEstadoSolicitud("Pendiente por accesorio");
            guardarSolicitud();
            contratacionActual = subsistema.registrarContratacion(contratacionActual).getContratacion();
            coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
        } else if (!unidadSigueLibre()) {
            coordinador.mostrarAviso(Mensaje.UNIDAD_YA_NO_LIBRE);
            abrirVerificacionInventario();
        } else {
            coordinador.mostrarVerificacionCondicion(solicitudActual, contratacionActual, cotizacionContratacion, subsistema.horarioEntrega());
        }
    }

    // Generar orden: el asesor marcó las condiciones que revisó con el cliente
    @Override
    public void pasarDatosOrdenEntrega(ContratacionRentaDTO revisada) {
        List<String> revisadas = revisada.getCondicionesAcordadas() == null ? new ArrayList<>()
                : Arrays.asList(revisada.getCondicionesAcordadas().split(", "));
        for (String condicion : CONDICIONES) {
            if (!revisadas.contains(condicion)) {
                coordinador.mostrarAviso(Mensaje.FALTA_CONDICION, condicion);
                return;
            }
        }
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        if (!unidadSigueLibre()) {
            coordinador.mostrarAviso(Mensaje.UNIDAD_YA_NO_LIBRE);
            abrirVerificacionInventario();
            return;
        }
        ContratacionRentaDTO contratacion = contratacionActual;
        contratacion.setCondicionesAcordadas(revisada.getCondicionesAcordadas());
        contratacion.setEstado("FORMALIZADA");
        contratacion.setFechaFormalizacion(LocalDate.now());
        contratacion.setOrdenEntrega(subsistema.generarOrdenEntrega(solicitudActual));
        // la orden que se muestra es la que quedó registrada
        contratacionActual = subsistema.registrarContratacion(contratacion).getContratacion();
        // la unidad queda apartada para el periodo de la renta
        UnidadFisicaDTO unidad = subsistema.buscarUnidadPorId(solicitudActual.getUnidadSeleccionada().getIdUnidad());
        if (unidad != null) {
            subsistema.ocuparUnidad(unidad, solicitudActual.getPeriodo());
        }
        solicitudActual.setEstadoSolicitud("Formalizada");
        guardarSolicitud();
        cotizacionContratacion = cotizacionDe(contratacionActual);
        coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
        coordinador.mostrarMensaje(Mensaje.ORDEN_REGISTRADA, solicitudActual.getFolio());
    }

    // No generar orden: la solicitud queda pendiente y la unidad no se aparta
    @Override
    public void pasarDatosSolicitudPendiente(ContratacionRentaDTO contratacion) {
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        contratacion.setEstado("PENDIENTE");
        contratacionActual = subsistema.registrarContratacion(contratacion).getContratacion();
        solicitudActual.setEstadoSolicitud("Pendiente por condiciones");
        guardarSolicitud();
        cotizacionContratacion = cotizacionDe(contratacionActual);
        coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
    }

    // El asesor regresó a modificar la solicitud: se queda lo que ya había capturado después de registrarla
    private void conservarLoCapturado(SolicitudRentaDTO nueva) {
        if (solicitudActual == null || !Objects.equals(solicitudActual.getFolio(), nueva.getFolio())) {
            return;
        }
        nueva.setIdSolicitud(solicitudActual.getIdSolicitud());
        List<RequerimientoTecnicoDTO> requerimientos = ValidadorPresentacion.copia(solicitudActual.getRequerimientos());
        // la captura ya muestra el terreno del análisis: lo que diga al registrar es lo que queda
        ValidadorPresentacion.capturar(requerimientos, ValidadorPresentacion.TERRENO,
                ValidadorPresentacion.texto(nueva.getRequerimientos(), ValidadorPresentacion.TERRENO));
        nueva.setRequerimientos(requerimientos);
        nueva.setRequisitos(solicitudActual.getRequisitos());
        nueva.setUnidadSeleccionada(solicitudActual.getUnidadSeleccionada());
        for (EquipoSolicitadoDTO equipo : nueva.getEquiposSolicitados()) {
            for (EquipoSolicitadoDTO anterior : solicitudActual.getEquiposSolicitados()) {
                if (Objects.equals(equipo.getDescripcionEquipo(), anterior.getDescripcionEquipo())) {
                    equipo.setEvaluacion(anterior.getEvaluacion());
                }
            }
        }
    }

    // Consulta desde el menú lo que quedó registrado con ese folio
    @Override
    public void consultarOrdenPorFolio(String folio) {
        if (ValidadorPresentacion.textoVacio(folio)) {
            coordinador.mostrarAviso(Mensaje.FALTA_FOLIO);
            return;
        }
        String buscado = folio.trim().toUpperCase();
        SolicitudRentaDTO solicitud;
        try {
            solicitud = subsistema.consultarSolicitud(buscado);
        } catch (IllegalArgumentException ex) {
            coordinador.mostrarMensaje(Mensaje.FOLIO_NO_ENCONTRADO, buscado);
            return;
        }
        ContratacionRentaDTO contratacion = subsistema.consultarContratacion(buscado);
        if (contratacion == null) {
            coordinador.mostrarMensaje(Mensaje.SOLICITUD_SIN_ORDEN, buscado, solicitud.getEstadoSolicitud());
            return;
        }
        solicitudActual = solicitud;
        contratacionActual = contratacion;
        cotizacionContratacion = cotizacionDe(contratacion);
        coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
    }

    // ================================================================ DTO que arma el control
    // La contratación con lo que decidió el cliente; qué accesorios entran y el estado los responde el subsistema
    private ContratacionRentaDTO armarContratacion(DecisionClienteDTO decision) {
        ContratacionRentaDTO contratacion = new ContratacionRentaDTO();
        contratacion.setFolioSolicitud(solicitudActual.getFolio());
        contratacion.setCliente(solicitudActual.getCliente());
        contratacion.setFechaInicio(solicitudActual.getPeriodo().getFechaInicio());
        contratacion.setFechaFin(solicitudActual.getPeriodo().getFechaFin());
        contratacion.setUnidad(solicitudActual.getUnidadSeleccionada());
        contratacion.setEstado(subsistema.estadoContratacion(decision.getTipoDecision()));
        EquipoDTO equipo = equipoRecomendado();
        contratacion.setTarifaAcordada(subsistema.tarifaDiaria(equipo));
        contratacion.setAccesorios(subsistema.accesoriosAceptados(decision.getTipoDecision(), equipo.getAccesorios()));
        contratacion.setDepositoGarantia(subsistema.depositoGarantia(solicitudActual.getRequisitos()));
        return contratacion;
    }

    // Los importes de la contratación (solo con los accesorios que aceptó el cliente)
    private CotizacionDTO cotizacionDe(ContratacionRentaDTO contratacion) {
        CotizacionDTO cotizacion = subsistema.generarCotizacion(solicitudActual.getFolio(), equipoRecomendado(), solicitudActual.getPeriodo().getDiasRenta(), contratacion.getAccesorios());
        cotizacion.setDepositoGarantia(contratacion.getDepositoGarantia());
        return cotizacion;
    }

    // La cotización con el equipo y los accesorios propuestos; queda registrada con el folio de la solicitud
    // (si se rehace porque el asesor regresó, se actualiza la que ya estaba)
    private boolean hayCotizacion() {
        if (solicitudActual.getRequisitos() == null || solicitudActual.getRequisitos().isEmpty()) {
            solicitudActual.setRequisitos(subsistema.obtenerRequisitosRenta());
        }
        if (cotizacionActual != null) {
            return true;
        }
        EquipoDTO equipo = equipoRecomendado();
        if (equipo == null || solicitudActual.getPeriodo() == null || solicitudActual.getPeriodo().getDiasRenta() == null) {
            coordinador.mostrarAviso(Mensaje.FALTA_EQUIPO_Y_PERIODO);
            abrirEquiposCompatibles();
            return false;
        }
        CotizacionDTO cotizacion;
        try {
            cotizacion = subsistema.generarCotizacion(solicitudActual.getFolio(), equipo, solicitudActual.getPeriodo().getDiasRenta(), equipo.getAccesorios());
        } catch (IllegalStateException ex) {
            coordinador.mostrarAviso(Mensaje.EQUIPO_SIN_TARIFA, equipo.getModelo());
            return false;
        }
        cotizacion.setDepositoGarantia(subsistema.depositoGarantia(solicitudActual.getRequisitos()));
        CotizacionDTO anterior = subsistema.consultarCotizacion(solicitudActual.getFolio());
        if (anterior == null) {
            cotizacionActual = subsistema.registrarCotizacion(cotizacion);
        } else {
            cotizacion.setIdCotizacion(anterior.getIdCotizacion());
            subsistema.actualizarCotizacion(cotizacion);
            cotizacionActual = cotizacion;
        }
        return true;
    }

    // Las pantallas del flujo necesitan la solicitud que se registró en Solicitud de renta
    private boolean haySolicitud() {
        if (solicitudActual == null) {
            coordinador.mostrarAviso(Mensaje.FALTA_SOLICITUD);
            abrirMenuPrincipal();
            return false;
        }
        if (solicitudActual.getRequerimientos() == null) {
            solicitudActual.setRequerimientos(new ArrayList<>());
        }
        return true;
    }

    // Antes de formalizar se vuelve a preguntar si la unidad elegida sigue libre
    private boolean unidadSigueLibre() {
        UnidadFisicaDTO elegida = solicitudActual.getUnidadSeleccionada();
        EquipoDTO equipo = equipoRecomendado();
        if (elegida == null || equipo == null) {
            return false;
        }
        for (UnidadFisicaDTO unidad : subsistema.verificarDisponibilidadUnidades(equipo, solicitudActual.getPeriodo())) {
            if (Objects.equals(unidad.getIdUnidad(), elegida.getIdUnidad())) {
                return subsistema.estaDisponible(unidad);
            }
        }
        return false;
    }

    // ---------------------------------------------------------------- evaluación técnica
    // En la tabla van los equipos que cumplen y los que pidió el cliente, aunque no cumplan
    private Map<EquipoDTO, String> equiposAMostrar() {
        List<EquipoDTO> pedidos = equiposPedidos();
        Map<EquipoDTO, String> mostrar = new LinkedHashMap<>();
        for (Map.Entry<EquipoDTO, String> evaluado : equiposEvaluados.entrySet()) {
            if (evaluado.getValue() == null || esPedido(evaluado.getKey(), pedidos)) {
                mostrar.put(evaluado.getKey(), evaluado.getValue());
            }
        }
        return mostrar;
    }

    private boolean esPedido(EquipoDTO equipo, List<EquipoDTO> pedidos) {
        for (EquipoDTO pedido : pedidos) {
            if (Objects.equals(pedido.getIdEquipo(), equipo.getIdEquipo())) {
                return true;
            }
        }
        return false;
    }

    // Los equipos del catálogo que pidió el cliente
    private List<EquipoDTO> equiposPedidos() {
        List<EquipoDTO> pedidos = new ArrayList<>();
        for (EquipoSolicitadoDTO pedido : solicitudActual.getEquiposSolicitados()) {
            if (!ValidadorPresentacion.textoVacio(pedido.getDescripcionEquipo())) {
                List<EquipoDTO> encontrados = subsistema.buscarEquipos(pedido.getDescripcionEquipo());
                if (!encontrados.isEmpty()) {
                    pedidos.add(encontrados.get(0));
                }
            }
        }
        return pedidos;
    }

    // La evaluación técnica es una para todos los equipos que pidió el cliente; si todavía no hay, se crea
    private EvaluacionTecnicaDTO evaluacionParaGuardar() {
        if (solicitudActual.getEquiposSolicitados().isEmpty()) {
            EquipoSolicitadoDTO pedido = new EquipoSolicitadoDTO();
            pedido.setCantidad(1);
            solicitudActual.getEquiposSolicitados().add(pedido);
        }
        EvaluacionTecnicaDTO evaluacion = solicitudActual.getEquiposSolicitados().get(0).getEvaluacion();
        if (evaluacion == null) {
            evaluacion = new EvaluacionTecnicaDTO();
        }
        for (EquipoSolicitadoDTO pedido : solicitudActual.getEquiposSolicitados()) {
            pedido.setEvaluacion(evaluacion);
        }
        return evaluacion;
    }

    // Si se vuelve a elegir el mismo equipo se queda el que ya estaba, con los accesorios que ya
    // se habían propuesto; si es otro, se toma del catálogo
    private EquipoDTO conservar(EquipoDTO anterior, EquipoDTO elegido) {
        if (anterior != null && Objects.equals(anterior.getIdEquipo(), elegido.getIdEquipo())) {
            return anterior;
        }
        EquipoDTO delCatalogo = subsistema.buscarEquipoPorId(elegido.getIdEquipo());
        return delCatalogo == null ? elegido : delCatalogo;
    }

    // Por qué no se quedan los equipos que pidió el cliente (null si todos cumplen)
    private String motivoDelPedido() {
        StringBuilder motivos = new StringBuilder();
        for (EquipoDTO pedido : equiposPedidos()) {
            String motivo = motivoDe(pedido);
            if (motivo != null) {
                motivos.append(motivos.length() > 0 ? "\n" : "").append(pedido.getModelo()).append(": ").append(motivo).append(".");
            }
        }
        return motivos.length() == 0 ? null : motivos.toString();
    }

    private String motivoDe(EquipoDTO equipo) {
        for (Map.Entry<EquipoDTO, String> evaluado : equiposEvaluados.entrySet()) {
            if (Objects.equals(evaluado.getKey().getIdEquipo(), equipo.getIdEquipo())) {
                return evaluado.getValue();
            }
        }
        return null;
    }

    private String resumenIncumplimientos() {
        StringBuilder resumen = new StringBuilder();
        for (Map.Entry<EquipoDTO, String> evaluado : equiposEvaluados.entrySet()) {
            if (evaluado.getValue() != null) {
                resumen.append(resumen.length() > 0 ? "\n" : "").append(evaluado.getKey().getModelo()).append(": ").append(evaluado.getValue()).append(".");
            }
        }
        return resumen.toString();
    }

    // ================================================================ apoyo
    // Deja la operación que estaba en curso: lo que siga empieza de cero
    private void soltarOperacion() {
        solicitudActual = null;
        cotizacionActual = null;
        contratacionActual = null;
        cotizacionContratacion = null;
        clienteActual = null;
        equiposEvaluados = new LinkedHashMap<>();
    }

    // Si falta algún dato pide al coordinador que avise cuáles son
    private boolean faltanDatos(Mensaje aviso, String faltan) {
        if (faltan.isEmpty()) {
            return false;
        }
        coordinador.mostrarAviso(aviso, faltan);
        return true;
    }

    private boolean requerimientosValidos(List<RequerimientoTecnicoDTO> requerimientos, Mensaje avisoNumero) {
        String sinNumero = ValidadorPresentacion.normalizar(requerimientos);
        if (sinNumero != null) {
            coordinador.mostrarAviso(avisoNumero, sinNumero, ValidadorPresentacion.unidad(sinNumero));
            return false;
        }
        return true;
    }

    // La primera vez se registra y el subsistema le pone su id; después se actualiza
    private void guardarSolicitud() {
        if (solicitudActual == null) {
            return;
        }
        if (solicitudActual.getIdSolicitud() == null) {
            solicitudActual = subsistema.registrarSolicitud(solicitudActual);
        } else {
            subsistema.actualizarSolicitud(solicitudActual);
        }
    }

    private EvaluacionTecnicaDTO evaluacionDe(SolicitudRentaDTO solicitud) {
        List<EquipoSolicitadoDTO> pedidos = solicitud == null ? null : solicitud.getEquiposSolicitados();
        return pedidos == null || pedidos.isEmpty() ? null : pedidos.get(0).getEvaluacion();
    }

    private EquipoDTO equipoRecomendado() {
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitudActual);
        return evaluacion == null ? null : evaluacion.getRecomendado();
    }

    private List<AccesorioDTO> accesoriosDelRecomendado() {
        EquipoDTO recomendado = equipoRecomendado();
        EquipoDTO delCatalogo = recomendado == null ? null : subsistema.buscarEquipoPorId(recomendado.getIdEquipo());
        return delCatalogo == null ? new ArrayList<>() : delCatalogo.getAccesorios();
    }

    private List<UnidadFisicaDTO> unidadesDelRecomendado() {
        EquipoDTO recomendado = equipoRecomendado();
        if (recomendado == null) {
            return new ArrayList<>();
        }
        try {
            return subsistema.verificarDisponibilidadUnidades(recomendado, solicitudActual.getPeriodo());
        } catch (IllegalArgumentException ex) {
            coordinador.mostrarAviso(Mensaje.EQUIPO_SIN_UNIDADES, recomendado.getModelo());
            return new ArrayList<>();
        }
    }

    @Override
    public void cambiarEstadoRequisitos(String estado) {
        if (!haySolicitud()) {
            return;
        }
        if (!"Pendiente".equals(estado) && !"Cumplida".equals(estado)) {
            return;
        }
        List<RequisitoRentaDTO> requisitos = solicitudActual.getRequisitos();

        if (requisitos == null) {
            return;
        }
        for (RequisitoRentaDTO requisito : requisitos) {
            if ("Identificación oficial".equals(requisito.getDescripcion())) {
                requisito.setEstadoRequisito(estado);
            }
        }
        guardarSolicitud();
    }

}
