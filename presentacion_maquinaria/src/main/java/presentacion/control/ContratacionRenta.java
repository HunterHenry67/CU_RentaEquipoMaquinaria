/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.control;

import almacentemporal.IAlmacenTemporal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        prepararDatosSolicitud();
        coordinador.mostrarAnalisisRequerimiento(solicitudActual);
    }

    @Override
    public void abrirSolicitudDatosFaltantes() {
        prepararDatosSolicitud();
        coordinador.mostrarSolicitudDatosFaltantes(solicitudActual);
    }

    @Override
    public void abrirEquiposCompatibles() {
        prepararDatosSolicitud();
        coordinador.mostrarEquiposCompatibles(solicitudActual,
                negocio.compararEquipos(almacen.obtenerCatalogoEquipo(), solicitudActual.getRequerimientos()));
    }

    @Override
    public void abrirSinEquipoCompatible() {
        prepararDatosSolicitud();
        coordinador.mostrarSinEquipoCompatible(solicitudActual);
    }

    @Override
    public void abrirSeleccionAccesorios() {
        prepararDatosSolicitud();
        coordinador.mostrarSeleccionAccesorios(solicitudActual, accesoriosDelRecomendado());
    }

    @Override
    public void abrirEvaluacionTecnica() {
        prepararDatosSolicitud();
        coordinador.mostrarEvaluacionTecnica(solicitudActual);
    }

    @Override
    public void abrirCambiarPeriodo() {
        prepararDatosSolicitud();
        coordinador.mostrarCambiarPeriodo(solicitudActual);
    }

    @Override
    public void abrirVerificacionInventario() {
        prepararDatosSolicitud();
        coordinador.mostrarVerificacionInventario(solicitudActual, unidadesDelRecomendado());
    }

    @Override
    public void abrirCalendarioUnidad() {
        prepararDatosSolicitud();
        coordinador.mostrarCalendarioUnidad(solicitudActual);
    }

    @Override
    public void abrirCondicionesContratacion() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        coordinador.mostrarCondicionesContratacion(solicitudActual, cotizacionActual);
    }

    @Override
    public void abrirCotizacion() {
        prepararDatosCotizacion();
        // se arma de nuevo con lo elegido, por si el asesor regresó y cambió equipo, accesorios o periodo
        cotizacionActual = armarCotizacion();
        coordinador.mostrarCotizacion(solicitudActual, cotizacionActual);
    }

    @Override
    public void abrirRequisitosRenta() {
        prepararDatosCotizacion();
        coordinador.mostrarRequisitosRenta(solicitudActual);
    }

    @Override
    public void abrirPropuestaRenta() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        coordinador.mostrarPropuestaRenta(solicitudActual, cotizacionActual);
    }

    @Override
    public void abrirPropuestaPendiente() {
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        coordinador.mostrarPropuestaPendiente(solicitudActual);
    }

    @Override
    public void abrirMotivoRechazo() {
        coordinador.mostrarMotivoRechazo();
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
        coordinador.mostrarSolicitudRenta(cliente, almacen.siguienteFolio());
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
        ClienteDTO mismoRfc = almacen.buscarClientePorRfc(cliente.getRfc());
        if (mismoRfc != null && !Objects.equals(mismoRfc.getIdCliente(), cliente.getIdCliente())) {
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
        periodo.setDiasRenta(diasDe(periodo));
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
            solicitudActual.setCliente(clienteActual);
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
    public void pasarDatosSinEquipoCompatible(SolicitudRentaDTO solicitud) {
        this.solicitudActual = solicitud;
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
    public void pasarDatosSeleccionAccesorios(SolicitudRentaDTO solicitud) {
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitud);
        if (evaluacion == null || !ValidadorPresentacion.esSeleccionValida(evaluacion.getRecomendado())) {
            coordinador.mostrarAviso("Equipos Compatibles", "Ningún equipo cumple con lo requerido. Usa «Ninguno cumple».");
            return;
        }
        if (evaluacion.getAlternativo() != null
                && Objects.equals(evaluacion.getAlternativo().getIdEquipo(), evaluacion.getRecomendado().getIdEquipo())) {
            coordinador.mostrarAviso("Equipos Compatibles", "El equipo recomendado y la alternativa tienen que ser distintos.");
            return;
        }
        this.solicitudActual = solicitud;
        guardarSolicitud();
        abrirSeleccionAccesorios();
    }

    @Override
    public void pasarDatosEvaluacionTecnica(SolicitudRentaDTO solicitud) {
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitud);
        if (evaluacion == null || !ValidadorPresentacion.esSeleccionValida(evaluacion.getRecomendado())) {
            coordinador.mostrarAviso("Selección de Accesorios", "Primero hay que elegir el equipo recomendado.");
            abrirEquiposCompatibles();
            return;
        }
        this.solicitudActual = solicitud;
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
        prepararDatosSolicitud();
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
        prepararDatosSolicitud();
        EvaluacionTecnicaDTO evaluacion = evaluacionDe(solicitudActual);
        if (evaluacion == null || evaluacion.getAlternativo() == null) {
            coordinador.mostrarMensaje("Usar la alternativa", "No se eligió un equipo alternativo; se abre Equipos compatibles para elegir otro.");
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
        prepararDatosSolicitud();
        periodo.setDiasRenta(diasDe(periodo));
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
        if (cotizacion.getIdCotizacion() == null) {
            almacen.registrarCotizacion(cotizacion);
        }
        abrirRequisitosRenta();
    }

    @Override
    public void pasarDatosPropuestaRenta(List<RequisitoRentaDTO> requisitos) {
        prepararDatosSolicitud();
        solicitudActual.setRequisitos(requisitos);
        if (!negocio.requisitosCumplidos(requisitos)) {
            coordinador.mostrarAviso("Requisitos de la renta", "Hay requisitos obligatorios pendientes: la renta no puede continuar.");
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
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        cotizacionActual.setDecision(decision);
        contratacionActual = armarContratacion(decision);
        cotizacionContratacion = cotizacionDe(contratacionActual);
        if ("RECHAZA".equals(decision.getTipoDecision())) {
            solicitudActual.setEstadoSolicitud("Rechazada");
            contratacionActual.setEstado("RECHAZADA");
            guardarSolicitud();
            coordinador.mostrarOrdenRechazada(solicitudActual, contratacionActual, cotizacionContratacion, decision.getObservaciones().trim());
        } else if ("NO ACEPTA INDISPENSABLE".equals(decision.getTipoDecision())) {
            // sin el accesorio indispensable no hay condiciones que revisar: la solicitud queda pendiente
            contratacionActual.setEstado("PENDIENTE POR ACCESORIO");
            coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
        } else {
            coordinador.mostrarVerificacionCondicion(solicitudActual, contratacionActual, cotizacionContratacion);
        }
    }

    @Override
    public void pasarDatosOrdenEntrega(ContratacionRentaDTO contratacion) {
        if ("FORMALIZADA".equals(contratacion.getEstado())) {
            List<String> revisadas = contratacion.getCondicionesAcordadas() == null ? new ArrayList<>()
                    : Arrays.asList(contratacion.getCondicionesAcordadas().split(", "));
            for (String condicion : CONDICIONES) {
                if (!revisadas.contains(condicion)) {
                    contratacion.setEstado("PENDIENTE");
                    coordinador.mostrarAviso("Verificación de condiciones", "Falta revisar: " + condicion + ". Marca todas las condiciones para generar la orden.");
                    return;
                }
            }
        }
        prepararDatosSolicitud();
        prepararDatosCotizacion();
        contratacionActual = contratacion;
        if ("FORMALIZADA".equals(contratacion.getEstado()) && contratacion.getOrdenEntrega() == null) {
            contratacion.setFechaFormalizacion(LocalDate.now());
            contratacion.setOrdenEntrega(negocio.generarOrdenEntrega(solicitudActual));
            almacen.registrarContratacion(contratacion);
            solicitudActual.setEstadoSolicitud("Formalizada");
            guardarSolicitud();
        }
        cotizacionContratacion = cotizacionDe(contratacion);
        coordinador.mostrarOrdenEntrega(solicitudActual, contratacionActual, cotizacionContratacion);
    }

    // ================================================================ DTO que arma el control
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
            contratacion.setTarifaAcordada(negocio.tarifaDiaria(equipo));
            if (equipo.getAccesorios() != null && !"NO ACEPTA INDISPENSABLE".equals(tipo)) {
                for (AccesorioDTO accesorio : equipo.getAccesorios()) {
                    if (Boolean.TRUE.equals(accesorio.getEsObligatorio()) || !"ACEPTA PARCIAL".equals(tipo)) {
                        accesorios.add(accesorio);
                    }
                }
            }
        }
        contratacion.setAccesorios(accesorios);
        contratacion.setDepositoGarantia(negocio.depositoGarantia(solicitudActual.getRequisitos()));
        return contratacion;
    }

    // Los importes de la contratación (solo con los accesorios que aceptó el cliente)
    private CotizacionDTO cotizacionDe(ContratacionRentaDTO contratacion) {
        EquipoDTO equipo = equipoRecomendado();
        if (equipo == null || solicitudActual.getPeriodo().getDiasRenta() == null) {
            return cotizacionActual;
        }
        return negocio.cotizar(equipo, solicitudActual.getPeriodo().getDiasRenta(), contratacion.getAccesorios());
    }

    private CotizacionDTO armarCotizacion() {
        EquipoDTO equipo = equipoRecomendado();
        Integer dias = solicitudActual.getPeriodo().getDiasRenta();
        if (equipo == null || dias == null) {
            // sin evaluación técnica todavía, se usa la cotización del escenario
            return new CotizacionDTO(null, LocalDate.now(), LocalDate.now().plusDays(15),
                    9500.0, 1520.0, 11020.0, almacen.obtenerDetallesCotizacionEjemplo(), null);
        }
        return negocio.cotizar(equipo, dias, equipo.getAccesorios());
    }

    private void prepararDatosCotizacion() {
        prepararDatosSolicitud();
        if (solicitudActual.getRequisitos() == null || solicitudActual.getRequisitos().isEmpty()) {
            solicitudActual.setRequisitos(almacen.obtenerRequisitosRenta());
        }
        if (cotizacionActual == null) {
            cotizacionActual = armarCotizacion();
        }
    }

    // Lo que falta cuando una pantalla se abre sin venir de las anteriores se completa con el escenario
    private void prepararDatosSolicitud() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudRentaDTO();
        }
        if (solicitudActual.getCliente() == null) {
            solicitudActual.setCliente(clienteActual != null ? clienteActual : almacen.buscarClientePorId(1L));
        }
        if (solicitudActual.getFolio() == null) {
            solicitudActual.setFolio(almacen.siguienteFolio());
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
            solicitudActual.setPeriodo(new PeriodoSolicitadoDTO(LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 15), 6));
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

    private int diasDe(PeriodoSolicitadoDTO periodo) {
        return (int) ChronoUnit.DAYS.between(periodo.getFechaInicio(), periodo.getFechaFin()) + 1;
    }

    // Si la solicitud ya se registró, el almacén se queda con sus cambios
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
