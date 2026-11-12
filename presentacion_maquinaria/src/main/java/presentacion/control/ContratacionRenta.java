/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.control;

import Excepciones.NegocioException;
import Subsistema.IAdminContratacionRenta;
import almacentemporal.IAlmacenTemporal;
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
import presentacion.mensajes.Mensaje;
import presentacion.simulacion.INegocioSimulado;

/**
 * @author Home
 */
public class ContratacionRenta implements IContratacionRenta {

    // Las condiciones que el asesor revisa antes de generar la orden (las casillas de la pantalla)
    private static final List<String> CONDICIONES = Arrays.asList("Cliente", "Equipo y Unidad", "Accesorios aceptados", "Periodo", "Tarifa");

    private final IAdminContratacionRenta subsistemaAdminCon;
    private final IAlmacenTemporal almacen;
    private final INegocioSimulado negocio;
    private ICoordinadorAplicacion coordinador;
    private UsuarioDTO usuario;
    private ClienteDTO clienteActual;
    private SolicitudRentaDTO solicitudActual;
    private CotizacionDTO cotizacionActual;
    private ContratacionRentaDTO contratacionActual;
    private CotizacionDTO cotizacionContratacion;
    private Map<EquipoDTO, String> equiposEvaluados = new LinkedHashMap<>();

    public ContratacionRenta(IAlmacenTemporal almacen, INegocioSimulado negocio, IAdminContratacionRenta subsistemaAdminCon) {
        this.almacen = almacen;
        this.negocio = negocio;
        this.subsistemaAdminCon = subsistemaAdminCon;
    }

    public void setCoordinador(ICoordinadorAplicacion coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public void abrirIniciarSesion() {
        usuario = null;
        coordinador.mostrarInicioSesion();
    }

    @Override
    public void abrirMenuPrincipal() {
        solicitudActual = null;
        cotizacionActual = null;
        contratacionActual = null;
        cotizacionContratacion = null;
        clienteActual = null;
        equiposEvaluados = new LinkedHashMap<>();
        coordinador.mostrarMenuPrincipal();
    }

    @Override
    public void abrirIdentificacionCliente() {
        coordinador.mostrarIdentificacionCliente(almacen.obtenerClientes());
    }

    @Override
    public void abrirBusquedaEquipo() {
        coordinador.mostrarBusquedaEquipo(almacen.obtenerCatalogoEquipo());
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
        equiposEvaluados = negocio.compararEquipos(almacen.obtenerCatalogoEquipo(), solicitudActual.getRequerimientos());
        coordinador.mostrarEquiposCompatibles(solicitudActual, equiposEvaluados, equipoPedido());
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

    private void abrirCalendarioUnidad() {
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
        // la revisión de la cuenta es del negocio; por ahora se acepta cualquier usuario con datos válidos
        this.usuario = usuario;
        coordinador.mostrarMensaje(Mensaje.SESION_INICIADA);
        abrirMenuPrincipal();
    }

    @Override
    public void buscarClientes(String filtro, String texto) {
        if (ValidadorPresentacion.textoVacio(texto)) {
            coordinador.mostrarClientesEncontrados(almacen.obtenerClientes());
            return;
        }
        if ("Teléfono".equals(filtro) && !ValidadorPresentacion.numeroEntero(texto.trim())) {
            coordinador.mostrarAviso(Mensaje.TELEFONO_BUSQUEDA_INVALIDO);
            return;
        }
        String valor = ValidadorPresentacion.textoVacio(texto) ? null : texto.trim();
        String nombre = null;
        String rfc = null;
        String tipo = null;
        String telefono = null;
        
        if(valor != null){
            switch(filtro){
                case "RFC":
                    rfc = valor;
                    break;
                case "Nombre":
                    nombre = valor;
                    break;
                case "Tipo":
                    tipo = valor;
                    break;
                case "Teléfono":
                    telefono = valor;
                    break;
                default:
                    return;
            }
                                    
        }
        List<ClienteDTO> encontrados = subsistemaAdminCon.buscarCliente(rfc, nombre, tipo, telefono);
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
        coordinador.mostrarSolicitudNueva(cliente, almacen.siguienteFolio());
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
        if (negocio.rfcEnUso(cliente, almacen.buscarClientePorRfc(cliente.getRfc()))) {
            coordinador.mostrarAviso(Mensaje.RFC_REPETIDO);
            return;
        }
        cliente.setRfc(cliente.getRfc().trim().toUpperCase());
        Mensaje mensaje;
        if (cliente.getIdCliente() == null) {
            try{
                cliente = subsistemaAdminCon.registrarClienteNuevo(cliente);
                mensaje = empresa != null ? Mensaje.EMPRESA_REGISTRADA : Mensaje.CLIENTE_REGISTRADO;
            }catch(NegocioException ex){
                coordinador.mostrarAviso(Mensaje.RFC_REPETIDO);
                return;
            }
                
        } else {
            // Tdoavia esta pendinete el checar este método, aún no lo he implementado dentro del subsistema
            almacen.actualizarCliente(cliente);
            mensaje = empresa != null ? Mensaje.EMPRESA_ACTUALIZADA : Mensaje.CLIENTE_ACTUALIZADO;
        }
        coordinador.mostrarMensaje(mensaje);
        pasarDatosSolicitudRenta(cliente);
    }

    @Override
    public void buscarEquipos(String texto) {
        List<EquipoDTO> encontrados = ValidadorPresentacion.textoVacio(texto) ? almacen.obtenerCatalogoEquipo() : almacen.buscarEquipos(texto);
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
        periodo.setDiasRenta(negocio.diasRenta(periodo));
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
        coordinador.mostrarMensaje(Mensaje.SOLICITUD_REGISTRADA);
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
        if (solicitudActual == null) {
            solicitudActual = solicitud;
        }
        if (solicitudActual.getCliente() == null) {
            solicitudActual.setCliente(clienteActual);
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio(solicitud.getFolio());
        }
        String folio = solicitudActual.getFolio();
        String motivo = solicitudActual.getMotivoCancelacion();
        
        if(ValidadorPresentacion.textoVacio(folio)){
            coordinador.mostrarAviso(Mensaje.FALTA_FOLIO);
        }
        ResultadoCancelacionDTO resultado = subsistemaAdminCon.cancelarSolicitudRenta(folio, motivo);
        
        if(resultado == null || !resultado.isExitoso()){
            coordinador.mostrarMensaje(Mensaje.FOLIO_NO_ENCONTRADO, folio);
        }
        solicitudActual.setEstadoSolicitud(resultado.getEstado());
        solicitudActual.setMotivoCancelacion(motivo);
        coordinador.mostrarMensaje(Mensaje.CANCELACION_REGISTRADA);
        abrirMenuPrincipal();
    }

    @Override
    public void pasarDatosAnalisisRequerimientos(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_DATOS_FALTANTES)) {
            return;
        }
        if (ValidadorPresentacion.textoVacio(ValidadorPresentacion.texto(requerimientos, ValidadorPresentacion.ANCHO))
                && ValidadorPresentacion.textoVacio(ValidadorPresentacion.texto(requerimientos, ValidadorPresentacion.TERRENO))) {
            coordinador.mostrarAviso(Mensaje.FALTA_RESPUESTA_CLIENTE);
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
        solicitudActual.setEstadoSolicitud("En evaluación técnica");
        guardarSolicitud();
        abrirAnalisisRequerimiento();
    }

    @Override
    public void pasarDatosSolicitudDatosFaltantes(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_ANALISIS)) {
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
        solicitudActual.setEstadoSolicitud("En espera de información");
        guardarSolicitud();
        abrirSolicitudDatosFaltantes();
    }

    @Override
    public void pasarDatosEquiposCompatibles(List<RequerimientoTecnicoDTO> requerimientos) {
        if (!haySolicitud() || !requerimientosValidos(requerimientos, Mensaje.NUMERO_INVALIDO_ANALISIS)) {
            return;
        }
        if (!hayRequerimientosConNumero(requerimientos)) {
            coordinador.mostrarAviso(Mensaje.FALTA_CARACTERISTICA);
            return;
        }
        solicitudActual.setRequerimientos(requerimientos);
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
        abrirMenuPrincipal();
    }

    @Override
    public void pasarDatosSeleccionAccesorios(EvaluacionTecnicaDTO seleccion) {
        if (!haySolicitud()) {
            return;
        }
        if (!ValidadorPresentacion.seleccionValida(seleccion.getRecomendado())) {
            coordinador.mostrarAviso(Mensaje.SIN_EQUIPO_COMPATIBLE);
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
        evaluacion.getRecomendado().setAccesorios(negocio.accesoriosPropuestos(accesoriosDelRecomendado(), opcionalesElegidos));
        try{
            boolean registrado = subsistemaAdminCon.registrarSeleccionAccesorios(solicitudActual, evaluacion.getRecomendado().getAccesorios());
            if(!registrado){
                return;
            }
            cotizacionActual = null;
            abrirEvaluacionTecnica();
        }catch(NegocioException ex){
            coordinador.mostrarAviso(Mensaje.ERROR_REGISTRAR_ACCESORIOS);
            return;
        }    
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
        if (!negocio.estaDisponible(unidad)) {
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
        EquipoDTO delCatalogo = almacen.buscarEquipoPorId(alternativo.getIdEquipo());
        alternativo.setAccesorios(negocio.accesoriosPropuestos(delCatalogo == null ? null : delCatalogo.getAccesorios(),
                recomendado == null ? null : recomendado.getAccesorios()));
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
        periodo.setDiasRenta(negocio.diasRenta(periodo));
        solicitudActual.setPeriodo(periodo);
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
        if (requisitos != null && !subsistemaAdminCon.verificarRequisitosRenta(requisitos)) {
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
        almacen.actualizarCotizacion(cotizacionActual);
        contratacionActual = armarContratacion(decision);
        cotizacionContratacion = cotizacionDe(contratacionActual);
        String estado = contratacionActual.getEstado();
        if ("RECHAZADA".equals(estado)) {
            solicitudActual.setEstadoSolicitud("Rechazada");
            guardarSolicitud();
            coordinador.mostrarOrdenRechazada(solicitudActual, contratacionActual, cotizacionContratacion, decision.getObservaciones().trim());
        } else if ("PENDIENTE POR ACCESORIO".equals(estado)) {
            solicitudActual.setEstadoSolicitud("Pendiente por accesorio");
            guardarSolicitud();
            contratacionActual = subsistemaAdminCon.registrarContratacion(contratacionActual);
            coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
        } else if (!unidadSigueLibre()) {
            coordinador.mostrarAviso(Mensaje.UNIDAD_YA_NO_LIBRE);
            abrirVerificacionInventario();
        } else {
            coordinador.mostrarVerificacionCondicion(solicitudActual, contratacionActual, cotizacionContratacion);
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
        contratacion.setOrdenEntrega(negocio.generarOrdenEntrega(solicitudActual));
        subsistemaAdminCon.registrarContratacion(contratacion);
        UnidadFisicaDTO unidad = almacen.buscarUnidadPorId(solicitudActual.getUnidadSeleccionada().getIdUnidad());
        if (unidad != null) {
            almacen.actualizarUnidad(negocio.ocuparUnidad(unidad, solicitudActual.getPeriodo()));
        }
        solicitudActual.setEstadoSolicitud("Formalizada");
        guardarSolicitud();
        // la orden que se muestra es la que quedó registrada
        contratacionActual = almacen.buscarContratacionPorFolio(solicitudActual.getFolio());
        cotizacionContratacion = cotizacionDe(contratacionActual);
        coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
        coordinador.mostrarMensaje(Mensaje.ORDEN_REGISTRADA, solicitudActual.getFolio());
    }

    // No generar orden: la solicitud queda pendiente y la unidad no se aparta
    @Override
    public void pasarDatosSolicitudPendiente(ContratacionRentaDTO contratacion){
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        contratacion.setEstado("PENDIENTE");
        contratacionActual = subsistemaAdminCon.registrarContratacion(contratacion);
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
        List<RequerimientoTecnicoDTO> requerimientos = ValidadorPresentacion.copia(solicitudActual.getRequerimientos());
        String terreno = ValidadorPresentacion.texto(nueva.getRequerimientos(), ValidadorPresentacion.TERRENO);
        if (!ValidadorPresentacion.textoVacio(terreno)) {
            ValidadorPresentacion.capturar(requerimientos, ValidadorPresentacion.TERRENO, terreno);
        }
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
        try{
            solicitud = subsistemaAdminCon.validarSolicitudRenta(buscado);
        }catch(NegocioException ex){
            coordinador.mostrarMensaje(Mensaje.RFC_INVALIDO, buscado);
            return;
        }
        if (solicitud == null) {
            coordinador.mostrarMensaje(Mensaje.FOLIO_NO_ENCONTRADO, buscado);
            return;
        }
        ContratacionRentaDTO contratacion = almacen.buscarContratacionPorFolio(buscado);
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
    // La contratación con lo que decidió el cliente; qué accesorios entran y el estado los responde el negocio simulado
    private ContratacionRentaDTO armarContratacion(DecisionClienteDTO decision) {
        ContratacionRentaDTO contratacion = new ContratacionRentaDTO();
        contratacion.setFolioSolicitud(solicitudActual.getFolio());
        contratacion.setCliente(solicitudActual.getCliente());
        contratacion.setFechaInicio(solicitudActual.getPeriodo().getFechaInicio());
        contratacion.setFechaFin(solicitudActual.getPeriodo().getFechaFin());
        contratacion.setUnidad(solicitudActual.getUnidadSeleccionada());
        contratacion.setEstado(negocio.estadoContratacion(decision.getTipoDecision()));
        EquipoDTO equipo = equipoRecomendado();
        contratacion.setTarifaAcordada(negocio.tarifaDiaria(equipo));
        contratacion.setAccesorios(negocio.accesoriosAceptados(decision.getTipoDecision(), equipo.getAccesorios()));
        contratacion.setDepositoGarantia(negocio.depositoGarantia(solicitudActual.getRequisitos()));
        return contratacion;
    }

    // Los importes de la contratación (solo con los accesorios que aceptó el cliente)
    private CotizacionDTO cotizacionDe(ContratacionRentaDTO contratacion) {
        CotizacionDTO cotizacion = negocio.cotizar(equipoRecomendado(), solicitudActual.getPeriodo().getDiasRenta(), contratacion.getAccesorios());
        cotizacion.setFolioSolicitud(solicitudActual.getFolio());
        cotizacion.setDepositoGarantia(contratacion.getDepositoGarantia());
        return cotizacion;
    }

    // La cotización con el equipo y los accesorios propuestos; queda registrada con el folio de la solicitud
    // (si se rehace porque el asesor regresó, se actualiza la que ya estaba)
    private boolean hayCotizacion() {
        if (solicitudActual.getRequisitos() == null || solicitudActual.getRequisitos().isEmpty()) {
            solicitudActual.setRequisitos(almacen.obtenerRequisitosRenta());
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
        CotizacionDTO cotizacion = negocio.cotizar(equipo, solicitudActual.getPeriodo().getDiasRenta(), equipo.getAccesorios());
        cotizacion.setFolioSolicitud(solicitudActual.getFolio());
        cotizacion.setDepositoGarantia(negocio.depositoGarantia(solicitudActual.getRequisitos()));
        CotizacionDTO anterior = almacen.buscarCotizacionPorFolio(solicitudActual.getFolio());
        if (anterior == null) {
            cotizacionActual = almacen.registrarCotizacion(cotizacion);
        } else {
            cotizacion.setIdCotizacion(anterior.getIdCotizacion());
            almacen.actualizarCotizacion(cotizacion);
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
        if (elegida == null) {
            return false;
        }
        List<UnidadFisicaDTO> registrada = new ArrayList<>();
        UnidadFisicaDTO enAlmacen = almacen.buscarUnidadPorId(elegida.getIdUnidad());
        registrada.add(enAlmacen == null ? elegida : enAlmacen);
        return negocio.estaDisponible(negocio.disponibilidad(registrada, solicitudActual.getPeriodo()).get(0));
    }

    // ---------------------------------------------------------------- evaluación técnica
    // El equipo del catálogo que pidió el cliente (el primero de la solicitud)
    private EquipoDTO equipoPedido() {
        List<EquipoSolicitadoDTO> pedidos = solicitudActual.getEquiposSolicitados();
        if (pedidos == null || pedidos.isEmpty() || ValidadorPresentacion.textoVacio(pedidos.get(0).getDescripcionEquipo())) {
            return null;
        }
        List<EquipoDTO> encontrados = almacen.buscarEquipos(pedidos.get(0).getDescripcionEquipo());
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }

    // La evaluación técnica va con el equipo que pidió el cliente; si todavía no hay, se crea
    private EvaluacionTecnicaDTO evaluacionParaGuardar() {
        if (solicitudActual.getEquiposSolicitados().isEmpty()) {
            EquipoSolicitadoDTO pedido = new EquipoSolicitadoDTO();
            pedido.setCantidad(1);
            solicitudActual.getEquiposSolicitados().add(pedido);
        }
        EquipoSolicitadoDTO pedido = solicitudActual.getEquiposSolicitados().get(0);
        if (pedido.getEvaluacion() == null) {
            pedido.setEvaluacion(new EvaluacionTecnicaDTO());
        }
        return pedido.getEvaluacion();
    }

    // Si se vuelve a elegir el mismo equipo se queda el que ya estaba, con los accesorios que ya
    // se habían propuesto; si es otro, se toma del catálogo del almacén
    private EquipoDTO conservar(EquipoDTO anterior, EquipoDTO elegido) {
        if (anterior != null && Objects.equals(anterior.getIdEquipo(), elegido.getIdEquipo())) {
            return anterior;
        }
        EquipoDTO delCatalogo = almacen.buscarEquipoPorId(elegido.getIdEquipo());
        return delCatalogo == null ? elegido : delCatalogo;
    }

    // Por qué no se queda el equipo que pidió el cliente (null si sí cumple)
    private String motivoDelPedido() {
        EquipoDTO pedido = equipoPedido();
        String motivo = pedido == null ? null : motivoDe(pedido);
        return motivo == null ? null : pedido.getModelo() + ": " + motivo + ".";
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

    private boolean hayRequerimientosConNumero(List<RequerimientoTecnicoDTO> requerimientos) {
        return ValidadorPresentacion.valor(requerimientos, ValidadorPresentacion.PROFUNDIDAD) != null
                || ValidadorPresentacion.valor(requerimientos, ValidadorPresentacion.POTENCIA) != null
                || ValidadorPresentacion.valor(requerimientos, ValidadorPresentacion.PESO) != null
                || ValidadorPresentacion.valor(requerimientos, ValidadorPresentacion.ANCHO) != null;
    }

    // La solicitud queda en el almacén temporal: se registra la primera vez y después se actualiza
    private void guardarSolicitud() {
        if (solicitudActual == null) {
            return;
        }
        if (almacen.buscarSolicitudPorFolio(solicitudActual.getFolio()) != null) {
            almacen.actualizarSolicitud(solicitudActual);
        } else {
            solicitudActual = almacen.registrarSolicitud(solicitudActual);
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
        EquipoDTO delCatalogo = recomendado == null ? null : almacen.buscarEquipoPorId(recomendado.getIdEquipo());
        return delCatalogo == null ? new ArrayList<>() : delCatalogo.getAccesorios();
    }

    private List<UnidadFisicaDTO> unidadesDelRecomendado() {
        EquipoDTO recomendado = equipoRecomendado();
        if (recomendado == null) {
            return new ArrayList<>();
        }
        return subsistemaAdminCon.verificarDisponibilidadUnidades(recomendado, solicitudActual.getPeriodo());
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
