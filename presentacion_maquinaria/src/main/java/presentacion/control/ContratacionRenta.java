/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.control;

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
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import objetosdto.UsuarioDTO;
import presentacion.simulacion.INegocioSimulado;

/**
 * Control de Aplicación del caso de uso. Atiende lo que piden las pantallas: revisa lo capturado
 * con ValidadorPresentacion, arma y guarda los DTO, registra y consulta en el Almacén Temporal,
 * pide al negocio simulado lo que depende de reglas de negocio y le dice al Coordinador de
 * Aplicación qué pantalla mostrar. No tiene ventanas ni componentes gráficos.
 *
 * @author Home
 */
public class ContratacionRenta implements IContratacionRenta {

    // Las condiciones que el asesor revisa antes de generar la orden (las casillas de la pantalla)
    private static final List<String> CONDICIONES = Arrays.asList("Cliente", "Equipo y Unidad", "Accesorios aceptados", "Periodo", "Tarifa");

    private final IAlmacenTemporal almacen;
    private final INegocioSimulado negocio;
    private ICoordinadorAplicacion coordinador;

    private UsuarioDTO usuario;
    private ClienteDTO clienteActual;
    private SolicitudRentaDTO solicitudActual;
    private CotizacionDTO cotizacionActual;
    private ContratacionRentaDTO contratacionActual;
    private CotizacionDTO cotizacionContratacion;
    // Lo que respondió el negocio simulado al comparar el catálogo con los requerimientos
    private Map<EquipoDTO, String> equiposEvaluados = new LinkedHashMap<>();

    public ContratacionRenta(IAlmacenTemporal almacen, INegocioSimulado negocio) {
        this.almacen = almacen;
        this.negocio = negocio;
    }

    public void setCoordinador(ICoordinadorAplicacion coordinador) {
        this.coordinador = coordinador;
    }

    // ================================================================ cambios de pantalla
    @Override
    public void abrirIniciarSesion() {
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

    private void abrirSeleccionAccesorios() {
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
        // se arma de nuevo con lo elegido, por si el asesor regresó y cambió equipo, accesorios o periodo
        cotizacionActual = null;
        if (!hayCotizacion()) {
            return;
        }
        coordinador.mostrarCotizacion(solicitudActual, cotizacionActual);
    }

    private void abrirRequisitosRenta() {
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
        if (coordinador.confirmar("Cancelación de Solicitud", "¿Confirma que desea cancelar el proceso de solicitud?")) {
            coordinador.mostrarCancelacionSolicitud();
        }
    }

    // La X de una pantalla sin botón para regresar: se confirma antes de dejar la operación
    @Override
    public void dejarOperacion() {
        if (coordinador.confirmar("Contratación de equipo", "¿Dejar la operación y volver al menú principal?")) {
            abrirMenuPrincipal();
        }
    }

    // ================================================================ inicio de sesión y clientes
    @Override
    public void pasarDatosUsuario(UsuarioDTO usuario) {
        String titulo = "Error de Inicio de Sesión";
        if (ValidadorPresentacion.esTextoVacio(usuario.getCorreo()) || ValidadorPresentacion.esTextoVacio(usuario.getContrasenia())) {
            coordinador.mostrarAviso(titulo, "Favor de ingresar el correo y la contraseña para iniciar sesión.");
            return;
        }
        if (!ValidadorPresentacion.esCorreoValido(usuario.getCorreo())) {
            coordinador.mostrarAviso(titulo, "Formato de Correo Inválido.");
            return;
        }
        if (!ValidadorPresentacion.tieneLongitudMinima(usuario.getContrasenia(), 8)) {
            coordinador.mostrarAviso(titulo, "La contraseña debe tener al menos 8 caracteres.");
            return;
        }
        // la revisión de la cuenta es del negocio; por ahora se acepta cualquier usuario con datos válidos
        this.usuario = usuario;
        coordinador.mostrarMensaje("Inicio de Sesión", "Inicio de Sesión Exitoso.");
        abrirMenuPrincipal();
    }

    @Override
    public void buscarClientes(String filtro, String texto) {
        if (ValidadorPresentacion.esTextoVacio(texto)) {
            coordinador.mostrarClientesEncontrados(almacen.obtenerClientes());
            return;
        }
        if ("Teléfono".equals(filtro) && !ValidadorPresentacion.esNumeroEntero(texto.trim())) {
            coordinador.mostrarAviso("Búsqueda de Cliente", "Para buscar por teléfono escribe solo números.");
            return;
        }
        List<ClienteDTO> encontrados = almacen.buscarClientes(filtro, texto);
        coordinador.mostrarClientesEncontrados(encontrados);
        if (encontrados.isEmpty()) {
            coordinador.mostrarMensaje("Búsqueda de Cliente", "No se encontró ningún cliente con ese dato. Si es nuevo, regístrelo.");
        }
    }

    @Override
    public void pasarDatosSolicitudRenta(ClienteDTO cliente) {
        if (!ValidadorPresentacion.esSeleccionValida(cliente)) {
            coordinador.mostrarAviso("Falla de Registro de Solicitud", "Favor de seleccionar un cliente para realizar el registro de solicitud.");
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
        if (!ValidadorPresentacion.esSeleccionValida(cliente)) {
            coordinador.mostrarAviso("Modificar Datos", "Favor de seleccionar un cliente para modificar.");
            return;
        }
        pasarDatosRegistroCliente(cliente);
    }

    @Override
    public void registrarCliente(ClienteDTO cliente) {
        String titulo = "Error de Registro";
        if (ValidadorPresentacion.esTextoVacio(cliente.getNombre()) || ValidadorPresentacion.esTextoVacio(cliente.getRfc())
                || ValidadorPresentacion.esTextoVacio(cliente.getTelefono()) || ValidadorPresentacion.esTextoVacio(cliente.getCorreo())) {
            coordinador.mostrarAviso(titulo, "Favor de llenar campos de cliente.");
            return;
        }
        ClienteEmpresaDTO empresa = cliente instanceof ClienteEmpresaDTO e ? e : null;
        if (empresa != null && (ValidadorPresentacion.esTextoVacio(empresa.getNombreEncargadoEmpresa())
                || ValidadorPresentacion.esTextoVacio(empresa.getPuestoEncargadoEmpresa())
                || ValidadorPresentacion.esTextoVacio(empresa.getTelefonoEncargadoEmpresa()))) {
            coordinador.mostrarAviso(titulo, "Favor de llenar campos de empresa.");
            return;
        }
        if (empresa != null && ValidadorPresentacion.esTextoVacio(empresa.getArchivoIdentificacionEncargado())) {
            coordinador.mostrarAviso(titulo, "Selecciona el archivo con la identificación del encargado.");
            return;
        }
        if (!ValidadorPresentacion.esRfcValido(cliente.getRfc())) {
            coordinador.mostrarAviso(titulo, "El RFC no tiene un formato válido (por ejemplo MARJ8503158A1).");
            return;
        }
        if (!ValidadorPresentacion.esTelefonoValido(cliente.getTelefono())) {
            coordinador.mostrarAviso(titulo, "El teléfono debe tener 10 dígitos.");
            return;
        }
        if (!ValidadorPresentacion.esCorreoValido(cliente.getCorreo())) {
            coordinador.mostrarAviso(titulo, "El correo electrónico no tiene un formato válido.");
            return;
        }
        if (empresa != null && !ValidadorPresentacion.esTelefonoValido(empresa.getTelefonoEncargadoEmpresa())) {
            coordinador.mostrarAviso(titulo, "El teléfono del encargado debe tener 10 dígitos.");
            return;
        }
        if (negocio.rfcEnUso(cliente, almacen.buscarClientePorRfc(cliente.getRfc()))) {
            coordinador.mostrarAviso(titulo, "Ya hay un cliente registrado con ese RFC.");
            return;
        }
        cliente.setRfc(cliente.getRfc().trim().toUpperCase());
        String mensaje;
        if (cliente.getIdCliente() == null) {
            cliente = almacen.registrarCliente(cliente);
            mensaje = empresa != null ? "La Empresa ha sido registrada correctamente." : "El Cliente ha sido registrado correctamente.";
        } else {
            almacen.actualizarCliente(cliente);
            mensaje = empresa != null ? "Los datos de la Empresa se actualizaron." : "Los datos del Cliente se actualizaron.";
        }
        coordinador.mostrarMensaje("Cliente Registrado", mensaje);
        pasarDatosSolicitudRenta(cliente);
    }

    // ================================================================ solicitud
    @Override
    public void buscarEquipos(String texto) {
        List<EquipoDTO> encontrados = ValidadorPresentacion.esTextoVacio(texto) ? almacen.obtenerCatalogoEquipo() : almacen.buscarEquipos(texto);
        coordinador.mostrarEquiposEncontrados(encontrados);
        if (encontrados.isEmpty()) {
            coordinador.mostrarMensaje("Búsqueda", "No hay equipos en el catálogo con ese nombre.");
        }
    }

    @Override
    public void pasarDatosEquipoSolicitado(EquipoSolicitadoDTO equipo) {
        if (!ValidadorPresentacion.esSeleccionValida(equipo)) {
            coordinador.mostrarAviso("Advertencia", "Por favor, seleccione un equipo de la tabla.");
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
        String titulo = "Error de Solicitud";
        PeriodoSolicitadoDTO periodo = solicitud.getPeriodo();
        if (ValidadorPresentacion.esTextoVacio(solicitud.getDireccionObra()) || ValidadorPresentacion.esTextoVacio(solicitud.getNombreObra())
                || ValidadorPresentacion.esTextoVacio(solicitud.getDescripcionActividad())
                || periodo == null || !ValidadorPresentacion.esFechaValida(periodo.getFechaInicio()) || !ValidadorPresentacion.esFechaValida(periodo.getFechaFin())) {
            coordinador.mostrarAviso(titulo, "Favor de llenar todos los campos obligatorios");
            return;
        }
        if (!ValidadorPresentacion.esPeriodoValido(periodo.getFechaInicio(), periodo.getFechaFin())) {
            coordinador.mostrarAviso(titulo, "La fecha final no puede ser anterior a la de inicio.");
            return;
        }
        if (solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty()) {
            coordinador.mostrarAviso(titulo, "Favor de agregar el equipo que solicita el cliente.");
            return;
        }
        periodo.setDiasRenta(negocio.diasRenta(periodo));
        RequerimientosTecnicos.normalizar(solicitud.getRequerimientos());
        if (solicitud.getCliente() == null) {
            solicitud.setCliente(clienteActual);
        }
        solicitud.setFechaSolicitud(LocalDate.now());
        solicitud.setEstadoSolicitud("Pendiente");
        solicitudActual = almacen.registrarSolicitud(solicitud);
        coordinador.mostrarMensaje("Solicitud Renta", "La Solicitud de Renta ha sido Registrada Correctamente.");
        abrirAnalisisRequerimiento();
    }

    @Override
    public void pasarDatosCancelacion(SolicitudRentaDTO solicitud) {
        if (ValidadorPresentacion.esTextoVacio(solicitud.getMotivoCancelacion())) {
            coordinador.mostrarAviso("Error en el Registro de Cancelación", "Favor de describir la razón de su cancelación.");
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
        solicitudActual.setEstadoSolicitud("Cancelada");
        solicitudActual.setMotivoCancelacion(solicitud.getMotivoCancelacion().trim());
        guardarSolicitud();
        coordinador.mostrarMensaje("Solicitud Renta", "La cancelación de la solicitud ha sido registrada.");
        abrirMenuPrincipal();
    }

    // ================================================================ equipo adecuado
    // Desde Datos faltantes: con la respuesta del cliente se regresa al análisis
    @Override
    public void pasarDatosAnalisisRequerimientos(SolicitudRentaDTO solicitud) {
        if (!requerimientosValidos(solicitud, "Solicitud de Datos Faltantes")) {
            return;
        }
        if (ValidadorPresentacion.esTextoVacio(RequerimientosTecnicos.texto(solicitud.getRequerimientos(), RequerimientosTecnicos.ANCHO))
                && ValidadorPresentacion.esTextoVacio(RequerimientosTecnicos.texto(solicitud.getRequerimientos(), RequerimientosTecnicos.TERRENO))) {
            coordinador.mostrarAviso("Solicitud de Datos Faltantes", "Captura por lo menos un dato de la respuesta del cliente.");
            return;
        }
        solicitud.setEstadoSolicitud("En evaluación técnica");
        this.solicitudActual = solicitud;
        guardarSolicitud();
        abrirAnalisisRequerimiento();
    }

    @Override
    public void pasarDatosSolicitudDatosFaltantes(SolicitudRentaDTO solicitud) {
        if (!requerimientosValidos(solicitud, "Análisis de Requerimiento Técnico")) {
            return;
        }
        solicitud.setEstadoSolicitud("En espera de información");
        this.solicitudActual = solicitud;
        guardarSolicitud();
        abrirSolicitudDatosFaltantes();
    }

    @Override
    public void pasarDatosEquiposCompatibles(SolicitudRentaDTO solicitud) {
        if (!requerimientosValidos(solicitud, "Análisis de Requerimiento Técnico")) {
            return;
        }
        if (!hayRequerimientosConNumero(solicitud)) {
            coordinador.mostrarAviso("Análisis de Requerimiento Técnico", "Captura por lo menos una característica con número para comparar los equipos.");
            return;
        }
        this.solicitudActual = solicitud;
        guardarSolicitud();
        abrirEquiposCompatibles();
    }

    @Override
    public void ningunEquipoCumple() {
        if (!haySolicitud()) {
            return;
        }
        if (equiposEvaluados.containsValue(null) && !coordinador.confirmar("Equipos Compatibles",
                "Con lo capturado, el sistema marcó equipos que sí cumplen.\n¿Registrar de todos modos que ninguno cumple?")) {
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
    public void cerrarEvaluacion(SolicitudRentaDTO solicitud) {
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitud);
        if (evaluacion == null || ValidadorPresentacion.esTextoVacio(evaluacion.getMotivoIncumplimiento())) {
            coordinador.mostrarAviso("Sin Equipo Compatible", "Escribe el motivo antes de cerrar la evaluación.");
            return;
        }
        solicitud.setEstadoSolicitud("Sin solución técnica");
        this.solicitudActual = solicitud;
        guardarSolicitud();
        abrirMenuPrincipal();
    }

    @Override
    public void pasarDatosSeleccionAccesorios(EvaluacionTecnicaDTO seleccion) {
        if (!haySolicitud()) {
            return;
        }
        if (!ValidadorPresentacion.esSeleccionValida(seleccion.getRecomendado())) {
            coordinador.mostrarAviso("Equipos Compatibles", "Ningún equipo cumple con lo requerido. Usa «Ninguno cumple».");
            return;
        }
        if (seleccion.getAlternativo() != null
                && Objects.equals(seleccion.getAlternativo().getIdEquipo(), seleccion.getRecomendado().getIdEquipo())) {
            coordinador.mostrarAviso("Equipos Compatibles", "El equipo recomendado y la alternativa tienen que ser distintos.");
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
        if (evaluacion == null || !ValidadorPresentacion.esSeleccionValida(evaluacion.getRecomendado())) {
            coordinador.mostrarAviso("Selección de Accesorios", "Primero hay que elegir el equipo recomendado.");
            abrirEquiposCompatibles();
            return;
        }
        evaluacion.getRecomendado().setAccesorios(negocio.accesoriosPropuestos(accesoriosDelRecomendado(), opcionalesElegidos));
        guardarSolicitud();
        abrirEvaluacionTecnica();
    }

    // ================================================================ disponibilidad
    @Override
    public void pasarDatosVerificacionInventario(SolicitudRentaDTO solicitud) {
        solicitud.setEstadoSolicitud("En verificación de disponibilidad");
        this.solicitudActual = solicitud;
        guardarSolicitud();
        coordinador.mostrarMensaje("Evaluación Técnica", "Evaluación técnica registrada con éxito.\nSolicitud enviada a verificación de inventario.");
        abrirVerificacionInventario();
    }

    @Override
    public void pasarDatosUnidadSeleccionada(SolicitudRentaDTO solicitud) {
        UnidadFisicaDTO unidad = solicitud.getUnidadSeleccionada();
        if (!ValidadorPresentacion.esSeleccionValida(unidad)) {
            coordinador.mostrarAviso("Verificación de Inventario", "Selecciona una unidad de la tabla.");
            return;
        }
        if (!negocio.estaDisponible(unidad)) {
            solicitud.setUnidadSeleccionada(null);
            coordinador.mostrarAviso("Verificación de Inventario", "Solo puede seleccionarse una unidad disponible durante todo el periodo.");
            return;
        }
        solicitud.setEstadoSolicitud("Unidad seleccionada");
        this.solicitudActual = solicitud;
        guardarSolicitud();
        // antes de las condiciones se ven los días del periodo de la unidad elegida
        abrirCalendarioUnidad();
    }

    @Override
    public void verMotivoUnidad(UnidadFisicaDTO unidad) {
        if (!ValidadorPresentacion.esSeleccionValida(unidad)) {
            coordinador.mostrarAviso("Verificación de Inventario", "Selecciona una unidad de la tabla.");
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
            coordinador.mostrarMensaje("Verificación de Inventario", "La unidad está disponible: se puede seleccionar.");
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
            coordinador.mostrarMensaje("Usar la alternativa", "No se eligió un equipo alternativo; se abre Equipos compatibles para elegir otro.");
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
        if (periodo == null || !ValidadorPresentacion.esFechaValida(periodo.getFechaInicio()) || !ValidadorPresentacion.esFechaValida(periodo.getFechaFin())) {
            coordinador.mostrarAviso("Cambiar periodo", "Selecciona la fecha de inicio y la de fin.");
            return;
        }
        if (!ValidadorPresentacion.esPeriodoValido(periodo.getFechaInicio(), periodo.getFechaFin())) {
            coordinador.mostrarAviso("Cambiar periodo", "La fecha de fin no puede ser anterior a la de inicio.");
            return;
        }
        if (!haySolicitud()) {
            return;
        }
        periodo.setDiasRenta(negocio.diasRenta(periodo));
        // con otras fechas hay que volver a revisar qué unidades están libres y rehacer la cotización
        solicitudActual.setPeriodo(periodo);
        solicitudActual.setUnidadSeleccionada(null);
        cotizacionActual = null;
        guardarSolicitud();
        abrirVerificacionInventario();
    }

    // ================================================================ cotización, requisitos y decisión
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
        if (!negocio.requisitosCumplidos(requisitos)) {
            coordinador.mostrarAviso("Requisitos de la renta", "Hay requisitos obligatorios pendientes: la renta no puede continuar.");
            coordinador.mostrarRequisitosPendientes();
            return;
        }
        guardarSolicitud();
        coordinador.mostrarMensaje("Requisitos de la renta", "Requisitos validados con éxito.\nLa renta puede continuar a su confirmación.");
        abrirPropuestaRenta();
    }

    @Override
    public void pasarDatosDecisionCliente(DecisionClienteDTO decision) {
        if ("RECHAZA".equals(decision.getTipoDecision()) && ValidadorPresentacion.esTextoVacio(decision.getObservaciones())) {
            coordinador.mostrarAviso("Motivo del rechazo", "Favor de escribir el motivo del rechazo.");
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
            // sin el accesorio indispensable no hay condiciones que revisar: la solicitud queda pendiente
            solicitudActual.setEstadoSolicitud("Pendiente por accesorio");
            guardarSolicitud();
            contratacionActual = almacen.registrarContratacion(contratacionActual);
            coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
        } else if (!unidadSigueLibre()) {
            coordinador.mostrarAviso("Verificación de condiciones", "La unidad elegida ya no está libre en el periodo: elige otra.");
            abrirVerificacionInventario();
        } else {
            coordinador.mostrarVerificacionCondicion(solicitudActual, contratacionActual, cotizacionContratacion);
        }
    }

    // Generar orden: el asesor marcó las condiciones que revisó con el cliente
    @Override
    public void pasarDatosOrdenEntrega(ContratacionRentaDTO contratacion) {
        List<String> revisadas = contratacion.getCondicionesAcordadas() == null ? new ArrayList<>()
                : Arrays.asList(contratacion.getCondicionesAcordadas().split(", "));
        for (String condicion : CONDICIONES) {
            if (!revisadas.contains(condicion)) {
                coordinador.mostrarAviso("Verificación de condiciones", "Falta revisar: " + condicion + ". Marca todas las condiciones para generar la orden.");
                return;
            }
        }
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        if (!unidadSigueLibre()) {
            coordinador.mostrarAviso("Verificación de condiciones", "La unidad elegida ya no está libre en el periodo: elige otra.");
            abrirVerificacionInventario();
            return;
        }
        contratacion.setEstado("FORMALIZADA");
        contratacion.setFechaFormalizacion(LocalDate.now());
        contratacion.setOrdenEntrega(negocio.generarOrdenEntrega(solicitudActual));
        almacen.registrarContratacion(contratacion);
        // la unidad queda apartada para el periodo de la renta
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
    }

    // No generar orden: la solicitud queda pendiente y la unidad no se aparta
    @Override
    public void pasarDatosSolicitudPendiente(ContratacionRentaDTO contratacion) {
        if (!haySolicitud() || !hayCotizacion()) {
            return;
        }
        contratacion.setEstado("PENDIENTE");
        contratacionActual = almacen.registrarContratacion(contratacion);
        solicitudActual.setEstadoSolicitud("Pendiente por condiciones");
        guardarSolicitud();
        cotizacionContratacion = cotizacionDe(contratacionActual);
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
            coordinador.mostrarAviso("Cotización", "Primero hay que elegir el equipo recomendado y el periodo.");
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
            coordinador.mostrarAviso("Contratación de equipo", "Primero hay que registrar la solicitud de renta.");
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
        if (pedidos == null || pedidos.isEmpty() || ValidadorPresentacion.esTextoVacio(pedidos.get(0).getDescripcionEquipo())) {
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
    private boolean requerimientosValidos(SolicitudRentaDTO solicitud, String titulo) {
        if (solicitud.getRequerimientos() == null) {
            solicitud.setRequerimientos(new ArrayList<>());
        }
        String error = RequerimientosTecnicos.normalizar(solicitud.getRequerimientos());
        if (error != null) {
            coordinador.mostrarAviso(titulo, error);
            return false;
        }
        return true;
    }

    private boolean hayRequerimientosConNumero(SolicitudRentaDTO solicitud) {
        return RequerimientosTecnicos.valor(solicitud.getRequerimientos(), RequerimientosTecnicos.PROFUNDIDAD) != null
                || RequerimientosTecnicos.valor(solicitud.getRequerimientos(), RequerimientosTecnicos.POTENCIA) != null
                || RequerimientosTecnicos.valor(solicitud.getRequerimientos(), RequerimientosTecnicos.PESO) != null
                || RequerimientosTecnicos.valor(solicitud.getRequerimientos(), RequerimientosTecnicos.ANCHO) != null;
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

    // El equipo que se recomendó en la evaluación técnica, con los accesorios que se eligieron
    private EquipoDTO equipoRecomendado() {
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitudActual);
        return evaluacion == null ? null : evaluacion.getRecomendado();
    }

    // Todos los accesorios que tiene en el catálogo el equipo recomendado
    private List<AccesorioDTO> accesoriosDelRecomendado() {
        EquipoDTO recomendado = equipoRecomendado();
        EquipoDTO delCatalogo = recomendado == null ? null : almacen.buscarEquipoPorId(recomendado.getIdEquipo());
        return delCatalogo == null ? new ArrayList<>() : delCatalogo.getAccesorios();
    }

    // Las unidades del equipo recomendado con su estado para el periodo (respuesta simulada)
    private List<UnidadFisicaDTO> unidadesDelRecomendado() {
        EquipoDTO recomendado = equipoRecomendado();
        if (recomendado == null) {
            return new ArrayList<>();
        }
        return negocio.disponibilidad(almacen.obtenerUnidadesDeEquipo(recomendado.getIdEquipo()), solicitudActual.getPeriodo());
    }
}
