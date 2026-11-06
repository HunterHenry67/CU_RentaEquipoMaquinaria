package presentacion;

import almacentemporal.AlmacenTemporal;
import almacentemporal.IAlmacenTemporal;
import com.github.lgooddatepicker.components.DatePicker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.ResultadoContratacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import presentacion.controles.ControlContratacionRenta;
import presentacion.controles.IContratacionRenta;
import presentacion.controles.ICoordinadorAplicacion;
import presentacion.controles.Mensaje;
import presentacion.vistas.*;
import subsistemaContratacionRenta.ISubsistemaContratacionRenta;
import subsistemaContratacionRenta.SubsistemaContratacionRenta;
import subsistemaInicioSesion.ISubsistemaInicioSesion;
import subsistemaInicioSesion.SubsistemaInicioSesion;

/**
 * Plan de pruebas de la capa de presentación (casos P01 a P16 de la rúbrica y P17 con las reglas del subsistema). Abre las pantallas
 * reales, escribe en sus campos y oprime sus botones; los avisos se contestan con el botón que se ve
 * en pantalla. Se corre aparte (Run File en NetBeans) y deja en la carpeta que se le indique el
 * resultado de cada caso y las capturas:
 *
 *   PruebasPresentacion [carpeta de evidencias]
 *
 * Abre ventanas, por eso no se llama *Test: así no corre con cada Clean and Build.
 */
public class PruebasPresentacion {

    private static class Caso {
        String id, criterio, nombre, datos, esperado;
        List<String> revisiones = new ArrayList<>();
        List<String> capturas = new ArrayList<>();
        int mal = 0;
    }

    private record Aviso(String titulo, String mensaje, int tipo, List<String> botones) { }

    private static final Map<String, Caso> casos = new LinkedHashMap<>();
    private static final List<Aviso> avisos = Collections.synchronizedList(new ArrayList<>());
    private static final List<Throwable> excepciones = Collections.synchronizedList(new ArrayList<>());
    private static final Deque<String> respuestas = new ArrayDeque<>();
    private static final Set<String> pantallasVistas = new java.util.HashSet<>();
    private static File carpeta;
    private static Caso actual;
    private static String capturaDeAviso;
    private static int totalBien, totalMal;

    private static IContratacionRenta control;
    private static Object coordinador;
    private static IAlmacenTemporal almacen;

    public static void main(String[] args) throws Exception {
        carpeta = new File(args.length > 0 ? args[0] : "target/evidencias");
        new File(carpeta, "capturas").mkdirs();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> excepciones.add(e));
        iniciarVigiaDeAvisos();

        p01Inicio();
        p02Avance();
        p03Regresar();
        p04Cancelar();
        p05Duplicados();
        p06CapturaEnDto();
        p07SinAcoplar();
        p08Registrar();
        p09Recuperar();
        p10NoEncontrado();
        p11Obligatorios();
        p12Formato();
        p13Correccion();
        p14Alternativos();
        p15Mensajes();
        p16FlujoCompleto();
        p17Subsistema();

        escribirResultados();
        System.out.println();
        System.out.println(totalBien + " revisiones bien, " + totalMal + " mal");
        System.exit(totalMal == 0 ? 0 : 1);
    }

    // ================================================================ casos
    private static void p01Inicio() throws Exception {
        caso("P01", "C10", "Inicio del sistema", "Crear todas las ventanas como Main y abrir la inicial.",
                "La aplicación inicia sin errores que bloqueen el uso.");
        nuevaApp();
        revisa("abre el inicio de sesión", visible().equals("InicioSesionView"));
        revisa("están las 25 pantallas", cuantasVentanas() == 25);
        foto("P01_inicio");
        sinExcepciones();
    }

    private static void p02Avance() throws Exception {
        caso("P02", "C3", "Avance de pantalla", "Botones del flujo principal, desde el inicio de sesión hasta la orden.",
                "Se muestra la vista prevista sin abrir pantallas incorrectas.");
        nuevaApp();
        entrar();
        revisa("Ingresar abre el menú", visible().equals("MenuPrincipalView"));
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        revisa("Contratación abre Identificación de cliente", visible().equals("IdentificacionClienteView"));
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        revisa("Registrar nueva solicitud abre Solicitud de renta", visible().equals("SolicitudRentaView"));
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        revisa("Agregar equipo abre Búsqueda de equipo", visible().equals("BusquedaEquipoView"));
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        revisa("Registrar equipo regresa a la solicitud", visible().equals("SolicitudRentaView"));
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("Registrar abre Análisis de requerimiento", visible().equals("AnalisisRequerimientoView"));
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        revisa("Buscar equipos abre Equipos compatibles", visible().equals("EquiposCompatiblesView"));
        continuarAAccesorios();
        revisa("Continuar abre Selección de accesorios", visible().equals("SeleccionAccesoriosView"));
        marcarOpcional(true);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        revisa("Continuar abre Evaluación técnica", visible().equals("EvaluacionTecnicaView"));
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        revisa("Enviar a verificación abre Inventario", visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        revisa("Seleccionar unidad abre el calendario", visible().equals("CalendarioUnidadView"));
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        revisa("del calendario pasa a Condiciones", visible().equals("CondicionesContratacionView"));
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        revisa("Enviar a validación abre la Cotización", visible().equals("CotizacionView"));
        clic(CotizacionView.class, "btnContinuar");
        revisa("Continuar abre Requisitos", visible().equals("RequisitosRentaView"));
        clic(RequisitosRentaView.class, "btnValidar");
        revisa("Validar abre la Propuesta", visible().equals("PropuestaRentaView"));
        clic(PropuestaRentaView.class, "btnAceptar");
        revisa("Aceptar abre Verificación de condiciones", visible().equals("VerificacionCondicionView"));
        marcarCondiciones();
        clic(VerificacionCondicionView.class, "btnGenerar");
        revisa("Generar abre la Orden de entrega", visible().equals("OrdenEntregaView"));
        sinExcepciones();
    }

    private static void p03Regresar() throws Exception {
        caso("P03", "C3", "Regresar", "Avanzar y regresar en cada pantalla que tiene Regresar; cambiar el periodo, el equipo y los accesorios desde la propuesta.",
                "Regresa a la pantalla esperada y conserva datos necesarios.");
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        revisa("Registrar cliente abre su pantalla", visible().equals("RegistroClienteView"));
        clic(RegistroClienteView.class, "btnCancelar");
        revisa("Cancelar del registro regresa a Identificación", visible().equals("IdentificacionClienteView"));
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        clic(BusquedaEquipoView.class, "btnRegresar");
        revisa("Regresar de la búsqueda vuelve a la solicitud", visible().equals("SolicitudRentaView"));
        revisa("la solicitud conserva lo capturado", texto(SolicitudRentaView.class, "txtFieldNombreObra").equals("Remodelación Plaza Goya"));
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        clic(SolicitudRentaView.class, "btnRegistrar");
        escribir(AnalisisRequerimientoView.class, "txtFieldProfundidad", "3.50");
        escribir(AnalisisRequerimientoView.class, "txtFieldPotencia", "120");
        clic(AnalisisRequerimientoView.class, "btnSolicitarDatos");
        revisa("Solicitar datos abre Datos faltantes", visible().equals("SolicitudDatosFaltantesView"));
        revisa("Datos faltantes solo pide lo que falta", !filaVisible("panelFaltaProfundidad") && !filaVisible("panelFaltaPotencia")
                && filaVisible("panelFaltaPesoMaximo") && filaVisible("panelFaltaAnchoAcceso") && filaVisible("panelFaltaTerreno"));
        foto("P03_datos_faltantes");
        clic(SolicitudDatosFaltantesView.class, "btnRegresar");
        revisa("Regresar vuelve al Análisis", visible().equals("AnalisisRequerimientoView"));
        revisa("el Análisis conserva la profundidad y la potencia: " + texto(AnalisisRequerimientoView.class, "txtFieldProfundidad"),
                texto(AnalisisRequerimientoView.class, "txtFieldProfundidad").startsWith("3.5") && texto(AnalisisRequerimientoView.class, "txtFieldPotencia").startsWith("120"));
        clic(AnalisisRequerimientoView.class, "btnSolicitarDatos");
        escribir(SolicitudDatosFaltantesView.class, "txtFieldAnchoAcceso", "4.20");
        clic(SolicitudDatosFaltantesView.class, "btnRegistrarRespuesta");
        revisa("la respuesta del cliente regresa al Análisis y se suma a lo de antes", visible().equals("AnalisisRequerimientoView")
                && texto(AnalisisRequerimientoView.class, "txtFieldAnchoAcceso").startsWith("4.2") && texto(AnalisisRequerimientoView.class, "txtFieldProfundidad").startsWith("3.5"));
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        revisa("si sigue faltando un dato no deja buscar: " + ultimoAviso().mensaje(), visible().equals("AnalisisRequerimientoView") && ultimoAviso().mensaje().contains("Falta capturar")
                && ultimoAviso().mensaje().contains("Peso") && !ultimoAviso().mensaje().contains("Ancho"));
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        continuarAAccesorios();
        clic(SeleccionAccesoriosView.class, "btnRegresar");
        revisa("Regresar de accesorios vuelve a Equipos compatibles", visible().equals("EquiposCompatiblesView"));
        continuarAAccesorios();
        marcarOpcional(true);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        clic(VerificacionInventarioView.class, "btnRegresar");
        revisa("Regresar del inventario vuelve a Evaluación técnica", visible().equals("EvaluacionTecnicaView"));
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnRegresar");
        revisa("Regresar del calendario vuelve al inventario", visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnRegresar");
        revisa("Regresar de condiciones vuelve al calendario con la misma unidad", visible().equals("CalendarioUnidadView") && etiqueta(CalendarioUnidadView.class, "lblUnidad").contains("M-001"));
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnRegresar");
        revisa("Regresar de la cotización vuelve a condiciones", visible().equals("CondicionesContratacionView"));
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        revisa("Requisitos tiene Regresar a la vista sin ningún requisito pendiente", ((JButton) campo(ventana(RequisitosRentaView.class), "btnRegresar")).isShowing());
        foto("P03_requisitos_con_regresar");
        clic(RequisitosRentaView.class, "btnRegresar");
        revisa("Regresar de Requisitos vuelve a la cotización", visible().equals("CotizacionView"));
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnRechazar");
        foto("P03_motivo_del_rechazo");
        clic(MotivoRechazoView.class, "btnRegresar");
        revisa("Regresar del rechazo vuelve a la propuesta", visible().equals("PropuestaRentaView"));
        clic(PropuestaRentaView.class, "btnCambiarPeriodo");
        foto("P03_cambiar_periodo");
        clic(CambiarPeriodoView.class, "btnRegresar");
        revisa("Regresar de cambiar periodo vuelve a la propuesta", visible().equals("PropuestaRentaView"));
        revisa("la propuesta sigue con el cliente y la unidad: " + etiqueta(PropuestaRentaView.class, "lblEquipo"),
                etiqueta(PropuestaRentaView.class, "lblCliente").contains("Julián") && etiqueta(PropuestaRentaView.class, "lblEquipo").contains("M-001"));
        // cambiar el periodo y seguir avanzando sin perder lo demás
        clic(PropuestaRentaView.class, "btnCambiarPeriodo");
        fecha(CambiarPeriodoView.class, "selectorFechaInicio", LocalDate.of(2026, 9, 20));
        fecha(CambiarPeriodoView.class, "selectorFechaFin", LocalDate.of(2026, 9, 23));
        clic(CambiarPeriodoView.class, "btnConsultarInventario");
        revisa("con otro periodo vuelve al inventario", visible().equals("VerificacionInventarioView"));
        revisa("la captura de la solicitud ya muestra el periodo nuevo", LocalDate.of(2026, 9, 20).equals(selector(SolicitudRentaView.class, "datePickerInicio").getDate())
                && LocalDate.of(2026, 9, 23).equals(selector(SolicitudRentaView.class, "datePickerFinal").getDate()));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        revisa("la cotización se rehace con 4 días y sigue con el cliente", etiquetas(CotizacionView.class).contains("Julián") && etiquetas(CotizacionView.class).contains("4 días"));
        foto("P03_cambio_de_periodo");
        // cambiar el equipo desde la propuesta y volver a avanzar sin perder lo demás
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnCambiarEquipo");
        revisa("Cambiar equipo abre Equipos compatibles", visible().equals("EquiposCompatiblesView"));
        marcarEquipos("Excavadora CAT 336", "Excavadora CAT 320");
        continuarAAccesorios();
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        revisa("con el otro equipo salen sus unidades", tablaTexto(VerificacionInventarioView.class, "tableUnidades").contains("N-001"));
        revisa("del 20 al 23 ya no cruzan el mantenimiento ni la otra renta: " + tablaTexto(VerificacionInventarioView.class, "tableUnidades"),
                !tablaTexto(VerificacionInventarioView.class, "tableUnidades").contains("mantenimiento") && !tablaTexto(VerificacionInventarioView.class, "tableUnidades").contains("Ocupada"));
        fila(VerificacionInventarioView.class, "tableUnidades", "N-002");
        clic(VerificacionInventarioView.class, "btnVerMotivo");
        revisa("Ver motivo de una unidad libre lo dice y no cambia de pantalla", ultimoAviso().mensaje().contains("disponible") && visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "N-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnMarcarPendiente");
        clic(RequisitosRentaView.class, "btnValidar");
        revisa("con un requisito pendiente también se ve Regresar", ((JButton) campo(ventana(RequisitosRentaView.class), "btnRegresar")).isShowing());
        clic(RequisitosRentaView.class, "btnRegresar");
        revisa("Regresar con requisito pendiente vuelve a la cotización", visible().equals("CotizacionView"));
        clic(CotizacionView.class, "btnContinuar");
        revisa("el requisito pendiente se conserva al regresar", tablaTexto(RequisitosRentaView.class, "tableRequisitos").contains("Pendiente"));
        clic(RequisitosRentaView.class, "btnValidar");
        clic(RequisitosRentaView.class, "btnReintentar");
        revisa("la propuesta ya es de la CAT 336 con el mismo periodo: " + etiqueta(PropuestaRentaView.class, "lblEquipo"),
                etiqueta(PropuestaRentaView.class, "lblEquipo").contains("CAT 336 · unidad N-001") && etiqueta(PropuestaRentaView.class, "lblPeriodo").startsWith("20/09/2026"));

        // las pantallas que no tenían Regresar
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        String folioDeLaCaptura = etiqueta(SolicitudRentaView.class, "lblFolio");
        foto("P03_solicitud_con_regresar");
        contestar("No");
        clic(SolicitudRentaView.class, "btnRegresar");
        revisa("Regresar de la solicitud pregunta; con No sigue con lo capturado", ultimoAviso().botones().equals(List.of("Sí", "No"))
                && visible().equals("SolicitudRentaView") && texto(SolicitudRentaView.class, "txtFieldNombreObra").equals("Remodelación Plaza Goya"));
        contestar("Sí");
        clic(SolicitudRentaView.class, "btnRegresar");
        revisa("con Sí vuelve a Identificación sin registrar nada", visible().equals("IdentificacionClienteView") && almacen.obtenerSolicitudes().isEmpty());
        fila(IdentificacionClienteView.class, "tableCliente", "LOMF920410H12");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        revisa("la solicitud del otro cliente empieza en blanco y con el mismo folio", visible().equals("SolicitudRentaView") && texto(SolicitudRentaView.class, "txtFieldNombreObra").isEmpty()
                && tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 0 && etiqueta(SolicitudRentaView.class, "lblFolio").equals(folioDeLaCaptura));
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Excavadora CAT 320");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        revisa("el equipo de la solicitud anterior no regresa", tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 1
                && tablaTexto(SolicitudRentaView.class, "tableEquipoSolicitado").contains("CAT 320"));

        nuevaApp();
        entrar();
        hastaLosCompatibles();
        clic(EquiposCompatiblesView.class, "btnRegresar");
        escribir(AnalisisRequerimientoView.class, "txtFieldPesoMaximo", "abc");
        clic(AnalisisRequerimientoView.class, "btnRegresar");
        revisa("Regresar con un número mal escrito avisa y deja corregirlo: " + ultimoAviso().mensaje(), visible().equals("AnalisisRequerimientoView") && ultimoAviso().mensaje().contains("Peso"));
        escribir(AnalisisRequerimientoView.class, "txtFieldPesoMaximo", "25");
        revisa("Regresar de Equipos compatibles vuelve al Análisis con lo capturado", visible().equals("AnalisisRequerimientoView") && texto(AnalisisRequerimientoView.class, "txtFieldPotencia").startsWith("120"));
        clic(AnalisisRequerimientoView.class, "btnRegresar");
        revisa("Regresar del Análisis vuelve a la solicitud con lo capturado", visible().equals("SolicitudRentaView") && texto(SolicitudRentaView.class, "txtFieldNombreObra").equals("Remodelación Plaza Goya"));
        revisa("la solicitud muestra el terreno capturado en el Análisis: " + combo(SolicitudRentaView.class, "comboTipoTerreno"), combo(SolicitudRentaView.class, "comboTipoTerreno").equals("Terracería compactada"));
        escribir(SolicitudRentaView.class, "txtFieldNombreObra", "Plaza Goya, segunda etapa");
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("modificar la solicitud no crea otra y conserva los requerimientos", visible().equals("AnalisisRequerimientoView") && almacen.obtenerSolicitudes().size() == 1
                && almacen.obtenerSolicitudes().get(0).getNombreObra().equals("Plaza Goya, segunda etapa") && texto(AnalisisRequerimientoView.class, "txtFieldPotencia").startsWith("120"));
        revisa("lo que se escribió en el Análisis antes de regresar sigue ahí: " + texto(AnalisisRequerimientoView.class, "txtFieldPesoMaximo"), texto(AnalisisRequerimientoView.class, "txtFieldPesoMaximo").startsWith("25"));
        seleccionarCombo(AnalisisRequerimientoView.class, "comboTipoTerreno", "Firme");
        clic(AnalisisRequerimientoView.class, "btnRegresar");
        revisa("cambiar el terreno en el Análisis también se ve en la solicitud", combo(SolicitudRentaView.class, "comboTipoTerreno").equals("Firme"));
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("registrar otra vez no regresa el terreno anterior", combo(AnalisisRequerimientoView.class, "comboTipoTerreno").equals("Firme"));
        seleccionarCombo(AnalisisRequerimientoView.class, "comboTipoTerreno", "Sin especificar");
        clic(AnalisisRequerimientoView.class, "btnRegresar");
        revisa("sin terreno en el Análisis, la solicitud dice Sin especificar", combo(SolicitudRentaView.class, "comboTipoTerreno").equals("Sin especificar"));
        seleccionarCombo(SolicitudRentaView.class, "comboTipoTerreno", "Arenoso");
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("el terreno que se cambia en la solicitud llega al Análisis", combo(AnalisisRequerimientoView.class, "comboTipoTerreno").equals("Arenoso"));
        clic(AnalisisRequerimientoView.class, "btnRegresar");
        seleccionarCombo(SolicitudRentaView.class, "comboTipoTerreno", "Sin especificar");
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("quitar el terreno en la solicitud lo quita del Análisis", combo(AnalisisRequerimientoView.class, "comboTipoTerreno").equals("Sin especificar"));
        seleccionarCombo(AnalisisRequerimientoView.class, "comboTipoTerreno", "Terracería compactada");
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        continuarAAccesorios();
        marcarOpcional(true);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnRegresar");
        revisa("Regresar de Evaluación técnica vuelve a accesorios con el opcional marcado", visible().equals("SeleccionAccesoriosView")
                && Boolean.TRUE.equals(tabla(SeleccionAccesoriosView.class, "tableAccesorios").getValueAt(1, 2)));
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnRegresar");
        revisa("Regresar de la propuesta vuelve a Requisitos", visible().equals("RequisitosRentaView"));
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnAceptarParcial");
        clic(PropuestaPendienteView.class, "btnRegresar");
        revisa("Regresar del accesorio indispensable vuelve a la propuesta", visible().equals("PropuestaRentaView"));
        clic(PropuestaRentaView.class, "btnAceptar");
        clic(VerificacionCondicionView.class, "btnRegresar");
        revisa("Regresar de Verificación vuelve a la propuesta sin contratación registrada", visible().equals("PropuestaRentaView") && almacen.obtenerContrataciones().isEmpty());
        // modificar la propuesta: sin el opcional cambian las condiciones, la cotización y la propuesta
        clic(PropuestaRentaView.class, "btnCambiarEquipo");
        continuarAAccesorios();
        marcarOpcional(false);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        Object cotizacion = campo(control, "cotizacionActual");
        revisa("en Condiciones ya no queda la cotización anterior", Double.valueOf(10788.0).equals(cotizacion.getClass().getMethod("getTotal").invoke(cotizacion)));
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        revisa("sin el opcional la propuesta da $10,788", etiquetas(PropuestaRentaView.class).contains("10,788"));
        foto("P03_propuesta_modificada");
        sinExcepciones();
    }

    private static void p04Cancelar() throws Exception {
        caso("P04", "C3/C7", "Cancelar operación", "Capturar la solicitud y usar Cancelar: primero No, luego Sí, razón vacía, Regresar y razón \"El cliente ya no requiere el equipo\", contestando primero No y luego Sí al registrar.",
                "Se cancela el flujo de forma controlada; no hay registros involuntarios.");
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        capturarSolicitud();
        String folio = almacen.siguienteFolio();

        contestar("No");
        capturaDeAviso = "P04_confirmacion";
        clic(SolicitudRentaView.class, "btnCancelarSolicitud");
        revisa("pregunta antes de cancelar y sus botones son Sí y No", ultimoAviso().botones().equals(List.of("Sí", "No")));
        revisa("con No sigue en la solicitud con lo capturado", visible().equals("SolicitudRentaView") && tarjetaCaptura() && texto(SolicitudRentaView.class, "txtFieldNombreObra").equals("Remodelación Plaza Goya"));

        contestar("Sí");
        clic(SolicitudRentaView.class, "btnCancelarSolicitud");
        revisa("con Sí pide la razón", !tarjetaCaptura());
        revisa("la pantalla muestra el folio: " + etiqueta(SolicitudRentaView.class, "lblFolioCancelacion"), etiqueta(SolicitudRentaView.class, "lblFolioCancelacion").equals("Folio: " + folio));
        foto("P04_razon_de_cancelacion");

        escribir(SolicitudRentaView.class, "txtAreaRazonCancelacion", "    ");
        capturaDeAviso = "P04_razon_vacia";
        clic(SolicitudRentaView.class, "btnRegistrarCancelacion");
        revisa("sin razón avisa y no sale: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("razón") && visible().equals("SolicitudRentaView"));
        revisa("sin razón no registra nada", almacen.obtenerSolicitudes().isEmpty());

        clic(SolicitudRentaView.class, "btnRegresarCancelacion");
        revisa("Regresar vuelve a la captura con los datos", tarjetaCaptura() && texto(SolicitudRentaView.class, "txtFieldNombreObra").equals("Remodelación Plaza Goya"));

        contestar("Sí");
        clic(SolicitudRentaView.class, "btnCancelarSolicitud");
        escribir(SolicitudRentaView.class, "txtAreaRazonCancelacion", "El cliente ya no requiere el equipo");
        contestar("No");
        clic(SolicitudRentaView.class, "btnRegistrarCancelacion");
        revisa("pregunta antes de registrar; con No sigue en la razón y no registra", ultimoAviso().botones().equals(List.of("Sí", "No"))
                && !tarjetaCaptura() && visible().equals("SolicitudRentaView") && almacen.obtenerSolicitudes().isEmpty());
        contestar("Sí");
        capturaDeAviso = "P04_cancelacion_registrada";
        clic(SolicitudRentaView.class, "btnRegistrarCancelacion");
        revisa("confirma la cancelación: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("cancelación"));
        revisa("regresa al menú", visible().equals("MenuPrincipalView"));
        SolicitudRentaDTO cancelada = almacen.buscarSolicitudPorFolio(folio);
        revisa("queda una sola solicitud, cancelada y con su razón", almacen.obtenerSolicitudes().size() == 1 && cancelada != null
                && "Cancelada".equals(cancelada.getEstadoSolicitud()) && "El cliente ya no requiere el equipo".equals(cancelada.getMotivoCancelacion()));
        revisa("no se registró ninguna contratación", almacen.obtenerContrataciones().isEmpty());

        // cancelar otra vez, con la siguiente solicitud
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        revisa("la siguiente solicitud empieza en blanco", tarjetaCaptura() && texto(SolicitudRentaView.class, "txtFieldNombreObra").isEmpty() && texto(SolicitudRentaView.class, "txtAreaRazonCancelacion").isEmpty());
        contestar("Sí");
        clic(SolicitudRentaView.class, "btnCancelarSolicitud");
        escribir(SolicitudRentaView.class, "txtAreaRazonCancelacion", "Cambió de obra");
        clic(SolicitudRentaView.class, "btnRegistrarCancelacion");
        revisa("una segunda cancelación seguida también queda registrada", almacen.obtenerSolicitudes().size() == 2 && visible().equals("MenuPrincipalView"));
        // la X de una pantalla sin Regresar pregunta antes de salir
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        contestar("No");
        cerrarConX(SolicitudRentaView.class);
        revisa("con la X y No se queda en la pantalla", visible().equals("SolicitudRentaView"));
        contestar("Sí");
        cerrarConX(SolicitudRentaView.class);
        revisa("con la X y Sí regresa al menú", visible().equals("MenuPrincipalView"));
        sinExcepciones();
    }

    private static void p05Duplicados() throws Exception {
        caso("P05", "C3", "Evitar duplicados", "Oprimir dos veces seguidas los botones que abren una pantalla y Registrar de la solicitud.",
                "No se crean ventanas o flujos duplicados inesperadamente.");
        nuevaApp();
        int ventanas = cuantasVentanas();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        segundoClic(MenuPrincipalView.class, "btnContratacionEquipo");
        revisa("dos clics en Contratación: una sola pantalla visible", visible().equals("IdentificacionClienteView"));
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        segundoClic(SolicitudRentaView.class, "btnAgregarEquipo");
        revisa("dos clics en Agregar equipo: una sola pantalla visible", visible().equals("BusquedaEquipoView"));
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        revisa("agregar otra vez el mismo equipo no lo repite", tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 1);
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnRegistrar");
        segundoClic(SolicitudRentaView.class, "btnRegistrar");
        revisa("dos clics en Registrar: una sola solicitud en el almacén", almacen.obtenerSolicitudes().size() == 1);
        revisa("no se crearon ventanas nuevas", cuantasVentanas() == ventanas);

        // repetir la contratación: otra solicitud con otra unidad no se mezcla con la primera
        nuevaApp();
        entrar();
        String primero = contratacionHastaLaOrden();
        cerrarConX(OrdenEntregaView.class);
        revisa("la X de la orden regresa al menú", visible().equals("MenuPrincipalView"));
        hastaElInventario();
        revisa("la segunda solicitud tiene otro folio", !almacen.siguienteFolio().equals(primero));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        revisa("la M-001 ya apartada no se puede volver a elegir: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("disponible"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnVerMotivo");
        clic(UnidadOcupadaView.class, "btnUsarAlternativa");
        fila(VerificacionInventarioView.class, "tableUnidades", "N-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnAceptar");
        marcarCondiciones();
        clic(VerificacionCondicionView.class, "btnGenerar");
        revisa("la segunda orden es de la CAT 336 con la N-001", etiqueta(OrdenEntregaView.class, "lblEquipo").contains("CAT 336") && etiqueta(OrdenEntregaView.class, "lblUnidad").contains("N-001"));
        clic(OrdenEntregaView.class, "btnTerminar");
        escribir(MenuPrincipalView.class, "txtFieldFolio", primero);
        clic(MenuPrincipalView.class, "btnConsultarFolio");
        revisa("la primera orden sigue con la CAT 320 y la M-001", etiqueta(OrdenEntregaView.class, "lblEquipo").contains("CAT 320") && etiqueta(OrdenEntregaView.class, "lblUnidad").contains("M-001"));
        sinExcepciones();
    }

    private static void p06CapturaEnDto() throws Exception {
        caso("P06", "C4", "Captura en DTO", "Registro de cliente con datos válidos y solicitud de renta con los del escenario.",
                "Los valores se integran correctamente al DTO correspondiente.");
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        capturarCliente("Ana Ruiz Soto", "ruas900101ab1", "6441112233", "ana.ruiz@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        ClienteDTO cliente = almacen.buscarClientePorRfc("RUAS900101AB1");
        revisa("el ClienteDTO trae lo capturado", cliente != null && "Ana Ruiz Soto".equals(cliente.getNombre()) && "6441112233".equals(cliente.getTelefono())
                && "ana.ruiz@gmail.com".equals(cliente.getCorreo()) && "Particular".equals(cliente.getTipoCliente()));
        revisa("pasa a la solicitud con ese cliente", visible().equals("SolicitudRentaView") && etiquetas(SolicitudRentaView.class).contains("Ana Ruiz Soto"));
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        String folio = almacen.siguienteFolio();
        clic(SolicitudRentaView.class, "btnRegistrar");
        SolicitudRentaDTO solicitud = almacen.buscarSolicitudPorFolio(folio);
        revisa("el SolicitudRentaDTO trae obra, dirección, actividad, periodo y equipo", solicitud != null
                && "Remodelación Plaza Goya".equals(solicitud.getNombreObra())
                && solicitud.getDireccionObra().startsWith("Av. Guerrero")
                && solicitud.getDescripcionActividad().contains("cepas")
                && LocalDate.of(2026, 9, 10).equals(solicitud.getPeriodo().getFechaInicio())
                && LocalDate.of(2026, 9, 15).equals(solicitud.getPeriodo().getFechaFin())
                && Integer.valueOf(6).equals(solicitud.getPeriodo().getDiasRenta())
                && solicitud.getEquiposSolicitados().size() == 1
                && solicitud.getEquiposSolicitados().get(0).getDescripcionEquipo().contains("Retroexcavadora JCB"));

        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        clic(RegistroClienteView.class, "radioBtnEmpresa");
        capturarCliente("Constructora Sur", "CSU101010AB1", "6441112233", "ventas@csur.com");
        escribir(RegistroClienteView.class, "txtFieldNombreEncargado", "Rosa Díaz");
        escribir(RegistroClienteView.class, "txtFieldTelefonoEncargado", "6449990011");
        escribir(RegistroClienteView.class, "txtFieldPuestoEncargado", "Gerente");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("empresa sin identificación: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("identificación del encargado"));
        Field archivo = RegistroClienteView.class.getDeclaredField("archivoIdentificacion");
        archivo.setAccessible(true);
        archivo.set(ventana(RegistroClienteView.class), "identificacion_rosa.pdf");
        clic(RegistroClienteView.class, "btnRegistrar");
        ClienteDTO empresa = almacen.buscarClientePorRfc("CSU101010AB1");
        revisa("el ClienteEmpresaDTO trae al encargado", empresa instanceof ClienteEmpresaDTO e && "Rosa Díaz".equals(e.getNombreEncargadoEmpresa())
                && "Gerente".equals(e.getPuestoEncargadoEmpresa()) && "6449990011".equals(e.getTelefonoEncargadoEmpresa()) && "identificacion_rosa.pdf".equals(e.getArchivoIdentificacionEncargado()));
        sinExcepciones();
    }

    private static void p07SinAcoplar() throws Exception {
        caso("P07", "C2/C4", "Intercambio de datos sin acoplar vistas", "Revisar los atributos y constructores de las 25 pantallas, del Control y del Coordinador.",
                "Las vistas no dependen directamente unas de otras ni transportan el estado del proceso mediante constructores.");
        nuevaApp();
        boolean soloControl = true, sinOtrasVistas = true, sinAlmacen = true;
        for (JFrame v : ventanas()) {
            for (Constructor<?> k : v.getClass().getDeclaredConstructors()) {
                soloControl &= k.getParameterCount() == 1 && k.getParameterTypes()[0] == IContratacionRenta.class;
            }
            for (Field f : v.getClass().getDeclaredFields()) {
                sinOtrasVistas &= !JFrame.class.isAssignableFrom(f.getType());
                sinAlmacen &= !IAlmacenTemporal.class.isAssignableFrom(f.getType())
                        && !f.getType().getName().equals("presentacion.controles.ICoordinadorAplicacion");
            }
        }
        revisa("cada pantalla recibe en su constructor solo la interfaz del Control", soloControl);
        revisa("ninguna pantalla guarda otra pantalla", sinOtrasVistas);
        revisa("ninguna pantalla guarda el almacén ni el coordinador", sinAlmacen);
        boolean interfazSinCoordinador = true;
        for (Method m : IContratacionRenta.class.getMethods()) {
            interfazSinCoordinador &= m.getReturnType() != ICoordinadorAplicacion.class;
        }
        revisa("la interfaz del Control no le entrega el coordinador a las pantallas", interfazSinCoordinador);
        boolean controlSinVentanas = true;
        for (Field f : ControlContratacionRenta.class.getDeclaredFields()) {
            controlSinVentanas &= !Component.class.isAssignableFrom(f.getType());
        }
        revisa("el Control no tiene ventanas", controlSinVentanas);
        Set<Class<?>> delControl = new java.util.HashSet<>();
        for (Field f : ControlContratacionRenta.class.getDeclaredFields()) {
            delControl.add(f.getType());
        }
        revisa("el Control conoce a los subsistemas solo por su interfaz", delControl.contains(ISubsistemaContratacionRenta.class) && delControl.contains(ISubsistemaInicioSesion.class)
                && !delControl.contains(SubsistemaContratacionRenta.class) && !delControl.contains(SubsistemaInicioSesion.class));
        revisa("el Control no usa el almacén", !delControl.contains(IAlmacenTemporal.class) && !delControl.contains(AlmacenTemporal.class));
        revisa("ya no hay negocio simulado", !existeClase("presentacion.simulacion.NegocioSimulado") && !existeClase("presentacion.simulacion.INegocioSimulado"));
        boolean fachadaSinEstado = true;
        for (Field f : SubsistemaContratacionRenta.class.getDeclaredFields()) {
            fachadaSinEstado &= f.getType().getSimpleName().startsWith("Gestor");
        }
        revisa("la fachada solo guarda sus clases internas", fachadaSinEstado);
        boolean soloFachadaPublica = Modifier.isPublic(SubsistemaContratacionRenta.class.getModifiers()) && Modifier.isPublic(SubsistemaInicioSesion.class.getModifiers());
        for (String interna : new String[]{"subsistemaContratacionRenta.GestorClientes", "subsistemaContratacionRenta.GestorSolicitud", "subsistemaContratacionRenta.GestorDisponibilidad",
            "subsistemaContratacionRenta.GestorCotizacion", "subsistemaContratacionRenta.GestorContratacion", "subsistemaInicioSesion.AutenticadorUsuario"}) {
            soloFachadaPublica &= !Modifier.isPublic(Class.forName(interna).getModifiers());
        }
        revisa("de cada subsistema solo la interfaz y la fachada son públicas", soloFachadaPublica);
        boolean gestoresPorInterfaz = true;
        for (Field f : Class.forName("subsistemaContratacionRenta.GestorCotizacion").getDeclaredFields()) {
            gestoresPorInterfaz &= !f.getType().getSimpleName().endsWith("BO") || f.getType().isInterface();
        }
        for (Field f : Class.forName("subsistemaContratacionRenta.GestorDisponibilidad").getDeclaredFields()) {
            gestoresPorInterfaz &= !f.getType().getSimpleName().endsWith("BO") || f.getType().isInterface();
        }
        revisa("los gestores guardan sus BO por su interfaz", gestoresPorInterfaz);
        boolean coordinadorSinAlmacen = true;
        for (Field f : coordinador.getClass().getDeclaredFields()) {
            coordinadorSinAlmacen &= !IAlmacenTemporal.class.isAssignableFrom(f.getType()) && !IContratacionRenta.class.isAssignableFrom(f.getType());
        }
        revisa("el Coordinador no usa el almacén ni el Control", coordinadorSinAlmacen);
        boolean sinTexto = true;
        for (Method m : ICoordinadorAplicacion.class.getMethods()) {
            if (m.getName().equals("mostrarAviso") || m.getName().equals("mostrarMensaje")) {
                sinTexto &= m.getParameterTypes()[0] == Mensaje.class;
            }
        }
        revisa("el Control no puede mandar texto: pide los avisos con un Mensaje", sinTexto);
        boolean controlSinMensajes = true;
        File fuente = new File("src/main/java/presentacion/controles/ControlContratacionRenta.java");
        if (fuente.exists()) {
            String codigo = Files.readString(fuente.toPath());
            controlSinMensajes = !codigo.matches("(?s).*(mostrarAviso|mostrarMensaje|confirmar)\\(\\s*\"\\s*.*") && !codigo.contains("JOptionPane");
        }
        revisa("el código del Control no trae mensajes escritos", controlSinMensajes);
        boolean vistasSinCambiar = true;
        File carpetaVistas = new File("src/main/java/presentacion/vistas");
        if (carpetaVistas.exists()) {
            for (File f : carpetaVistas.listFiles((d, n) -> n.endsWith("View.java"))) {
                String codigo = Files.readString(f.toPath());
                if (codigo.matches("(?s).*\\b(solicitud|contratacion|cotizacion|evaluacion|requisito|requisitos|unidad)\\.(set[A-Z]|getRequerimientos\\(\\)\\.).*")
                        || codigo.contains("capturar(solicitud.")) {
                    vistasSinCambiar = false;
                    revisa(f.getName() + " cambia un DTO que le dio el Control", false);
                }
            }
        }
        revisa("las pantallas no cambian los DTO que les da el Control", vistasSinCambiar);
        sinExcepciones();
    }

    private static void p08Registrar() throws Exception {
        caso("P08", "C5", "Registrar temporalmente", "Registrar un cliente y una solicitud desde las pantallas.",
                "El dato queda guardado en memoria y puede consultarse.");
        nuevaApp();
        int antes = almacen.obtenerClientes().size();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        capturarCliente("Luis Peña Mora", "PEML850612XY3", "6443334455", "luis.pena@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("el almacén tiene un cliente más", almacen.obtenerClientes().size() == antes + 1);
        revisa("se encuentra por RFC y tiene id", almacen.buscarClientePorRfc("PEML850612XY3") != null && almacen.buscarClientePorRfc("PEML850612XY3").getIdCliente() != null);
        sinExcepciones();
    }

    private static void p09Recuperar() throws Exception {
        caso("P09", "C3/C5", "Recuperar información", "Registrar cliente, volver al menú y buscarlo; completar una contratación y consultarla por folio.",
                "Se recupera la misma información mientras la aplicación siga abierta.");
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        capturarCliente("Luis Peña Mora", "PEML850612XY3", "6443334455", "luis.pena@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        contestar("Sí");
        cerrarConX(SolicitudRentaView.class);
        revisa("volvió al menú", visible().equals("MenuPrincipalView"));
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        escribir(IdentificacionClienteView.class, "txtFieldBusquedaCliente", "PEML850612XY3");
        seleccionarCombo(IdentificacionClienteView.class, "comboFiltroBusquedaCliente", "RFC");
        clic(IdentificacionClienteView.class, "btnBuscar");
        JTable clientes = tabla(IdentificacionClienteView.class, "tableCliente");
        revisa("el cliente registrado se encuentra navegando: " + filaTexto(clientes, 0), clientes.getRowCount() == 1 && filaTexto(clientes, 0).contains("Luis Peña Mora") && filaTexto(clientes, 0).contains("6443334455"));
        foto("P09_cliente_recuperado");

        clic(IdentificacionClienteView.class, "btnCancelar");
        String folio = contratacionHastaLaOrden();
        String total = etiqueta(OrdenEntregaView.class, "lblTotal");
        String unidad = etiqueta(OrdenEntregaView.class, "lblUnidad");
        clic(OrdenEntregaView.class, "btnTerminar");
        revisa("Terminar regresa al menú", visible().equals("MenuPrincipalView"));
        escribir(MenuPrincipalView.class, "txtFieldFolio", folio.toLowerCase());
        clic(MenuPrincipalView.class, "btnConsultarFolio");
        revisa("el folio abre la orden registrada", visible().equals("OrdenEntregaView"));
        revisa("trae el mismo total y la misma unidad: " + total + " / " + unidad, etiqueta(OrdenEntregaView.class, "lblTotal").equals(total) && etiqueta(OrdenEntregaView.class, "lblUnidad").equals(unidad));
        foto("P09_orden_recuperada_por_folio");
        clic(OrdenEntregaView.class, "btnTerminar");
        revisa("el menú queda con el campo vacío", texto(MenuPrincipalView.class, "txtFieldFolio").isEmpty());
        sinExcepciones();
    }

    private static void p10NoEncontrado() throws Exception {
        caso("P10", "C5/C7", "Dato no encontrado", "Buscar cliente \"ZZZ\", equipo \"XYZ\" y folio \"FOL-1\"; consultar una solicitud sin orden.",
                "Se informa sin bloquear ni provocar excepciones.");
        nuevaApp();
        entrar();
        escribir(MenuPrincipalView.class, "txtFieldFolio", "FOL-1");
        capturaDeAviso = "P10_folio_no_encontrado";
        clic(MenuPrincipalView.class, "btnConsultarFolio");
        revisa("folio inexistente: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("No se encontró") && visible().equals("MenuPrincipalView"));
        escribir(MenuPrincipalView.class, "txtFieldFolio", "   ");
        clic(MenuPrincipalView.class, "btnConsultarFolio");
        revisa("folio vacío: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Falta capturar: Folio") && visible().equals("MenuPrincipalView"));
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        escribir(IdentificacionClienteView.class, "txtFieldBusquedaCliente", "ZZZ");
        capturaDeAviso = "P10_cliente_no_encontrado";
        clic(IdentificacionClienteView.class, "btnBuscar");
        revisa("cliente inexistente: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("No se encontró") && tabla(IdentificacionClienteView.class, "tableCliente").getRowCount() == 0);
        revisa("sigue en Identificación", visible().equals("IdentificacionClienteView"));
        avisos.clear();
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        revisa("sin cliente seleccionado no avanza: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("seleccionar un cliente") && visible().equals("IdentificacionClienteView"));
        escribir(IdentificacionClienteView.class, "txtFieldBusquedaCliente", "");
        clic(IdentificacionClienteView.class, "btnBuscar");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        escribir(BusquedaEquipoView.class, "txtFieldBusqueda", "XYZ");
        capturaDeAviso = "P10_equipo_no_encontrado";
        clic(BusquedaEquipoView.class, "btnBuscar");
        revisa("equipo inexistente: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("No hay equipos") && tabla(BusquedaEquipoView.class, "tableEquipoCatalogo").getRowCount() == 0);
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        revisa("sin equipo seleccionado avisa: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("seleccione un equipo") && visible().equals("BusquedaEquipoView"));
        clic(BusquedaEquipoView.class, "btnRegresar");
        contestar("Sí");
        clic(SolicitudRentaView.class, "btnCancelarSolicitud");
        String folio = etiqueta(SolicitudRentaView.class, "lblFolioCancelacion").replace("Folio: ", "");
        escribir(SolicitudRentaView.class, "txtAreaRazonCancelacion", "Prueba de consulta");
        clic(SolicitudRentaView.class, "btnRegistrarCancelacion");
        escribir(MenuPrincipalView.class, "txtFieldFolio", folio);
        capturaDeAviso = "P10_solicitud_sin_orden";
        clic(MenuPrincipalView.class, "btnConsultarFolio");
        revisa("solicitud sin orden: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("no tiene orden") && ultimoAviso().mensaje().contains("Cancelada") && visible().equals("MenuPrincipalView"));
        sinExcepciones();
    }

    private static void p11Obligatorios() throws Exception {
        caso("P11", "C6", "Campos obligatorios vacíos", "Inicio de sesión, registro de cliente y de empresa, solicitud, cancelación, datos faltantes y motivo de rechazo, vacíos y con solo espacios.",
                "La operación se detiene y se informa qué dato falta.");
        nuevaApp();
        capturaDeAviso = "P11_inicio_sesion_vacio";
        clic(InicioSesionView.class, "btnIngresar");
        revisa("inicio de sesión: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().equals("Falta capturar: Correo, Contraseña.") && visible().equals("InicioSesionView"));
        escribir(InicioSesionView.class, "txtFieldCorreo", "   ");
        clic(InicioSesionView.class, "btnIngresar");
        revisa("con solo espacios tampoco entra", ultimoAviso().mensaje().contains("Correo") && visible().equals("InicioSesionView"));
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        capturaDeAviso = "P11_cliente_vacio";
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("cliente: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().equals("Falta capturar: Nombre, Correo electrónico, Teléfono, RFC.") && visible().equals("RegistroClienteView"));
        capturarCliente("Ana Ruiz Soto", "", "6441112233", "ana.ruiz@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("dice solo el que falta: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().equals("Falta capturar: RFC."));
        clic(RegistroClienteView.class, "radioBtnEmpresa");
        capturarCliente("Constructora Sur", "CSU101010AB1", "6441112233", "ventas@csur.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("empresa: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().equals("Falta capturar: Nombre del encargado, Teléfono del encargado, Puesto del encargado."));
        clic(RegistroClienteView.class, "btnCancelar");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        capturaDeAviso = "P11_solicitud_vacia";
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("solicitud: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().equals("Falta capturar: Fecha de inicio, Fecha final, Nombre de la obra, Dirección.") && visible().equals("SolicitudRentaView"));
        seleccionarCombo(SolicitudRentaView.class, "comboTipoTrabajo", "Sin especificar");
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("sin tipo de trabajo también pide la actividad: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().endsWith("Actividad que va a realizar el cliente."));
        seleccionarCombo(SolicitudRentaView.class, "comboTipoTrabajo", "Excavación de cepas");
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("solicitud sin equipo: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("agregar el equipo") && visible().equals("SolicitudRentaView"));
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        clic(SolicitudRentaView.class, "btnRegistrar");
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        revisa("análisis sin características: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Falta capturar") && ultimoAviso().mensaje().contains("Tipo de terreno") && visible().equals("AnalisisRequerimientoView"));
        clic(AnalisisRequerimientoView.class, "btnSolicitarDatos");
        clic(SolicitudDatosFaltantesView.class, "btnRegistrarRespuesta");
        revisa("datos faltantes sin respuesta: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("por lo menos un dato") && visible().equals("SolicitudDatosFaltantesView"));
        clic(SolicitudDatosFaltantesView.class, "btnRegresar");
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnSolicitarDatos");
        revisa("con todo capturado no hay datos que pedir: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Ya están todos") && visible().equals("AnalisisRequerimientoView"));
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        clic(EquiposCompatiblesView.class, "btnContinuarAccesorios");
        revisa("equipos sin marcar: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Marca en la tabla") && visible().equals("EquiposCompatiblesView"));
        continuarAAccesorios();
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        revisa("inventario sin unidad: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Selecciona una unidad") && visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnRechazar");
        escribir(MotivoRechazoView.class, "txtMotivo", "   ");
        clic(MotivoRechazoView.class, "btnEnviar");
        revisa("rechazo sin motivo: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("motivo del rechazo") && visible().equals("MotivoRechazoView"));
        clic(MotivoRechazoView.class, "btnRegresar");
        clic(PropuestaRentaView.class, "btnAceptar");
        clic(VerificacionCondicionView.class, "btnGenerar");
        revisa("condiciones sin marcar: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Falta revisar") && visible().equals("VerificacionCondicionView"));
        sinExcepciones();
    }

    private static void p12Formato() throws Exception {
        caso("P12", "C6", "Formato incorrecto", "Correo \"asesor@\", contraseña \"123\", RFC \"ABC\", teléfono \"644abc\", profundidad \"abc\" y fecha final antes de la inicial.",
                "Se muestra un error comprensible y no se procesa el dato inválido.");
        nuevaApp();
        escribir(InicioSesionView.class, "txtFieldCorreo", "asesor@");
        escribir(InicioSesionView.class, "psswFieldContrasenia", "12345678");
        capturaDeAviso = "P12_correo_invalido";
        clic(InicioSesionView.class, "btnIngresar");
        revisa("correo: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("formato válido") && visible().equals("InicioSesionView"));
        escribir(InicioSesionView.class, "txtFieldCorreo", "asesor@maquinaria.com");
        escribir(InicioSesionView.class, "psswFieldContrasenia", "123");
        clic(InicioSesionView.class, "btnIngresar");
        revisa("contraseña: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("8 caracteres") && visible().equals("InicioSesionView"));
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        int clientes = almacen.obtenerClientes().size();
        capturarCliente("Ana Ruiz Soto", "ABC", "6441112233", "ana.ruiz@gmail.com");
        capturaDeAviso = "P12_rfc_invalido";
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("RFC: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("RFC") && ultimoAviso().mensaje().contains("MARJ8503158A1"));
        capturarCliente("Ana Ruiz Soto", "RUAS900101AB1", "644abc", "ana.ruiz@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("teléfono: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("10 dígitos"));
        capturarCliente("Ana Ruiz Soto", "RUAS900101AB1", "6441112233", "ana.ruiz");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("correo del cliente: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("correo electrónico"));
        capturarCliente("Ana Ruiz Soto", "MARJ8503158A1", "6441112233", "ana.ruiz@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("RFC repetido: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Ya hay un cliente"));
        revisa("ningún dato inválido quedó registrado", almacen.obtenerClientes().size() == clientes && visible().equals("RegistroClienteView"));
        clic(RegistroClienteView.class, "btnCancelar");
        seleccionarCombo(IdentificacionClienteView.class, "comboFiltroBusquedaCliente", "Teléfono");
        escribir(IdentificacionClienteView.class, "txtFieldBusquedaCliente", "abc");
        clic(IdentificacionClienteView.class, "btnBuscar");
        revisa("búsqueda por teléfono: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("solo números"));
        escribir(IdentificacionClienteView.class, "txtFieldBusquedaCliente", "");
        clic(IdentificacionClienteView.class, "btnBuscar");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        clic(SolicitudRentaView.class, "btnRegistrar");
        escribir(AnalisisRequerimientoView.class, "txtFieldProfundidad", "abc");
        capturaDeAviso = "P12_numero_invalido";
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        revisa("profundidad: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().toLowerCase().contains("profundidad") && visible().equals("AnalisisRequerimientoView"));
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        continuarAAccesorios();
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnCambiarPeriodo");
        fecha(CambiarPeriodoView.class, "selectorFechaInicio", null);
        fecha(CambiarPeriodoView.class, "selectorFechaFin", null);
        clic(CambiarPeriodoView.class, "btnConsultarInventario");
        revisa("periodo sin fechas: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("fecha") && visible().equals("CambiarPeriodoView"));
        // primero la final y después una inicial posterior: el calendario ya no lo evita
        fecha(CambiarPeriodoView.class, "selectorFechaFin", LocalDate.of(2026, 9, 10));
        fecha(CambiarPeriodoView.class, "selectorFechaInicio", LocalDate.of(2026, 9, 15));
        avisos.clear();
        clic(CambiarPeriodoView.class, "btnConsultarInventario");
        revisa("periodo con la final antes de la inicial no continúa: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("fecha") && visible().equals("CambiarPeriodoView"));
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        fecha(SolicitudRentaView.class, "datePickerInicio", null);
        fecha(SolicitudRentaView.class, "datePickerFinal", LocalDate.of(2026, 9, 10));
        fecha(SolicitudRentaView.class, "datePickerInicio", LocalDate.of(2026, 9, 15));
        avisos.clear();
        capturaDeAviso = "P12_fecha_invalida";
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("solicitud con la final antes de la inicial no continúa: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Fecha final") || ultimoAviso().mensaje().contains("fecha final"));
        revisa("sigue en la solicitud y no registró nada", visible().equals("SolicitudRentaView") && almacen.obtenerSolicitudes().isEmpty());
        sinExcepciones();
    }

    private static void p13Correccion() throws Exception {
        caso("P13", "C6", "Corrección tras error", "RFC inválido en el registro y obra vacía en la solicitud; corregir solo ese campo y continuar.",
                "Permite corregir y continuar, sin perder datos válidos innecesariamente.");
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        capturarCliente("Ana Ruiz Soto", "ABC", "6441112233", "ana.ruiz@gmail.com");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("después del error sigue en el registro", visible().equals("RegistroClienteView"));
        revisa("los demás campos siguen capturados", texto(RegistroClienteView.class, "txtFieldNombre").equals("Ana Ruiz Soto")
                && texto(RegistroClienteView.class, "txtFieldTelefono").equals("6441112233") && texto(RegistroClienteView.class, "txtFieldCorreoElectronico").equals("ana.ruiz@gmail.com"));
        foto("P13_cliente_tras_error");
        escribir(RegistroClienteView.class, "txtFieldRFC", "RUAS900101AB1");
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("corregido el RFC, se registra y continúa", visible().equals("SolicitudRentaView") && almacen.buscarClientePorRfc("RUAS900101AB1") != null);
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        escribir(SolicitudRentaView.class, "txtFieldNombreObra", "");
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("sin obra avisa cuál falta: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().equals("Falta capturar: Nombre de la obra."));
        revisa("conserva dirección, actividad, fechas y equipo", texto(SolicitudRentaView.class, "txtFieldDireccion").startsWith("Av. Guerrero")
                && texto(SolicitudRentaView.class, "txtAreaActividadRealizar").contains("cepas")
                && LocalDate.of(2026, 9, 10).equals(selector(SolicitudRentaView.class, "datePickerInicio").getDate())
                && tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 1);
        foto("P13_solicitud_tras_error");
        escribir(SolicitudRentaView.class, "txtFieldNombreObra", "Remodelación Plaza Goya");
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("corregida la obra, continúa al análisis", visible().equals("AnalisisRequerimientoView"));
        escribir(AnalisisRequerimientoView.class, "txtFieldProfundidad", "abc");
        escribir(AnalisisRequerimientoView.class, "txtFieldPotencia", "120");
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        revisa("con un número inválido sigue en el análisis y conserva la potencia", visible().equals("AnalisisRequerimientoView") && texto(AnalisisRequerimientoView.class, "txtFieldPotencia").startsWith("120"));
        SolicitudRentaDTO enControl = (SolicitudRentaDTO) campo(control, "solicitudActual");
        boolean sinTextoMalo = true;
        for (RequerimientoTecnicoDTO r : enControl.getRequerimientos()) {
            sinTextoMalo &= !String.valueOf(r.getCaracteristica()).contains("abc");
        }
        revisa("el dato incorrecto no llega a la solicitud del Control", sinTextoMalo);
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        revisa("corregido el número, continúa", visible().equals("EquiposCompatiblesView"));
        continuarAAccesorios();
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnRegresar");
        clic(CalendarioUnidadView.class, "btnRegresar");
        edt(() -> tabla(VerificacionInventarioView.class, "tableUnidades").clearSelection());
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        revisa("seleccionar sin fila avisa y no borra la unidad ya elegida", ultimoAviso().mensaje().contains("Selecciona una unidad")
                && ((SolicitudRentaDTO) campo(control, "solicitudActual")).getUnidadSeleccionada() != null);
        sinExcepciones();
    }

    private static void p14Alternativos() throws Exception {
        caso("P14", "C7", "Flujo alternativo", "Unidad en mantenimiento y ocupada, usar la alternativa, ninguno cumple, requisito pendiente, aceptación parcial, sin indispensable, no generar orden y rechazo.",
                "Se sigue el comportamiento alternativo según el diseño.");
        // unidad no disponible y equipo alternativo
        nuevaApp();
        entrar();
        hastaElInventario();
        fila(VerificacionInventarioView.class, "tableUnidades", "M-002");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        revisa("una unidad en mantenimiento no se puede elegir: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("disponible") && visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-002");
        clic(VerificacionInventarioView.class, "btnVerMotivo");
        revisa("Ver motivo abre Unidad en mantenimiento", visible().equals("UnidadMantenimientoView"));
        foto("P14_unidad_en_mantenimiento");
        clic(UnidadMantenimientoView.class, "btnRegresar");
        revisa("Regresar de mantenimiento vuelve al inventario", visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-002");
        clic(VerificacionInventarioView.class, "btnVerMotivo");
        clic(UnidadMantenimientoView.class, "btnOtraUnidad");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-003");
        clic(VerificacionInventarioView.class, "btnVerMotivo");
        revisa("Ver motivo abre Unidad ocupada", visible().equals("UnidadOcupadaView"));
        foto("P14_unidad_ocupada");
        clic(UnidadOcupadaView.class, "btnRegresar");
        revisa("Regresar de ocupada vuelve al inventario", visible().equals("VerificacionInventarioView"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-003");
        clic(VerificacionInventarioView.class, "btnVerMotivo");
        clic(UnidadOcupadaView.class, "btnUsarAlternativa");
        revisa("Usar la alternativa cambia el equipo y vuelve al inventario", visible().equals("VerificacionInventarioView") && etiquetas(VerificacionInventarioView.class).contains("CAT 336") && tablaTexto(VerificacionInventarioView.class, "tableUnidades").contains("N-001"));
        foto("P14_inventario_con_la_alternativa");

        // ninguno cumple
        nuevaApp();
        entrar();
        hastaLosCompatibles();
        contestar("Sí");
        clic(EquiposCompatiblesView.class, "btnNingunoCumple");
        revisa("Ninguno cumple abre Sin equipo compatible", visible().equals("SinEquipoCompatibleView"));
        foto("P14_sin_equipo_compatible");
        clic(SinEquipoCompatibleView.class, "btnAjustarRequerimiento");
        revisa("Ajustar regresa al análisis", visible().equals("AnalisisRequerimientoView"));
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        contestar("Sí");
        clic(EquiposCompatiblesView.class, "btnNingunoCumple");
        escribir(SinEquipoCompatibleView.class, "txtAreaMotivo", "Ningún equipo alcanza la profundidad");
        capturaDeAviso = "P14_evaluacion_cerrada";
        clic(SinEquipoCompatibleView.class, "btnCerrarEvaluacion");
        revisa("Cerrar evaluación avisa cómo queda la solicitud: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("sin solución técnica")
                && ultimoAviso().mensaje().contains(almacen.obtenerSolicitudes().get(0).getFolio()));
        revisa("Cerrar evaluación regresa al menú y deja el estado", visible().equals("MenuPrincipalView") && "Sin solución técnica".equals(almacen.obtenerSolicitudes().get(0).getEstadoSolicitud()));

        // requisito pendiente
        nuevaApp();
        entrar();
        hastaLosRequisitos();
        clic(RequisitosRentaView.class, "btnMarcarPendiente");
        revisa("Marcar pendiente lo cambia en la tabla (el Control hace el cambio)", tablaTexto(RequisitosRentaView.class, "tableRequisitos").contains("Pendiente") && visible().equals("RequisitosRentaView"));
        foto("P14_requisito_marcado_pendiente");
        clic(RequisitosRentaView.class, "btnValidar");
        revisa("con un requisito pendiente no continúa: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("pendientes") && visible().equals("RequisitosRentaView"));
        foto("P14_requisito_pendiente");
        clic(RequisitosRentaView.class, "btnReintentar");
        revisa("Reintentar lo deja cumplido y continúa a la propuesta", visible().equals("PropuestaRentaView"));

        // aceptación parcial, con el indispensable
        clic(PropuestaRentaView.class, "btnAceptarParcial");
        revisa("Aceptar parcial abre la propuesta pendiente", visible().equals("PropuestaPendienteView"));
        foto("P14_propuesta_pendiente");
        clic(PropuestaPendienteView.class, "btnAceptar");
        revisa("sigue a Verificación de condiciones", visible().equals("VerificacionCondicionView"));
        clic(VerificacionCondicionView.class, "btnNoGenerar");
        revisa("No generar deja la solicitud pendiente y no aparta la unidad", visible().equals("OrdenEntregaView") && etiqueta(OrdenEntregaView.class, "lblEstado").equals("Pendiente")
                && etiqueta(OrdenEntregaView.class, "lblMotivo").contains("no se aparta") && "Disponible".equals(almacen.obtenerUnidadesDeEquipo(2L).get(0).getEstadoUnidad()));
        foto("P14_solicitud_pendiente_por_condicion");
        String pendiente = almacen.obtenerSolicitudes().get(0).getFolio();
        clic(OrdenEntregaView.class, "btnTerminar");
        escribir(MenuPrincipalView.class, "txtFieldFolio", pendiente);
        clic(MenuPrincipalView.class, "btnConsultarFolio");
        revisa("por folio se recupera como pendiente", visible().equals("OrdenEntregaView") && etiqueta(OrdenEntregaView.class, "lblEstado").equals("Pendiente"));

        // sin el indispensable
        nuevaApp();
        entrar();
        hastaLaPropuesta();
        clic(PropuestaRentaView.class, "btnAceptarParcial");
        clic(PropuestaPendienteView.class, "btnNoContinuar");
        revisa("sin el indispensable queda pendiente, sin accesorios", visible().equals("OrdenEntregaView") && etiqueta(OrdenEntregaView.class, "lblMotivo").contains("indispensable"));
        foto("P14_solicitud_pendiente_sin_indispensable");

        // rechazo
        nuevaApp();
        entrar();
        hastaLaPropuesta();
        clic(PropuestaRentaView.class, "btnRechazar");
        escribir(MotivoRechazoView.class, "txtMotivo", "El cliente consiguió otra máquina");
        clic(MotivoRechazoView.class, "btnEnviar");
        revisa("el rechazo muestra el motivo: " + etiqueta(OrdenEntregaView.class, "lblMotivo"), visible().equals("OrdenEntregaView") && etiqueta(OrdenEntregaView.class, "lblMotivo").contains("otra máquina"));
        foto("P14_rechazo");
        revisa("la solicitud queda Rechazada", "Rechazada".equals(almacen.obtenerSolicitudes().get(0).getEstadoSolicitud()));
        revisa("el rechazo muestra la propuesta que se rechazó: " + etiqueta(OrdenEntregaView.class, "lblAccesorios") + " / " + etiqueta(OrdenEntregaView.class, "lblPrecioAccesorios") + " / " + etiqueta(OrdenEntregaView.class, "lblTotal"),
                etiqueta(OrdenEntregaView.class, "lblAccesorios").contains("Kit de señalización") && etiqueta(OrdenEntregaView.class, "lblAccesorios").contains("Cucharón")
                && etiqueta(OrdenEntregaView.class, "lblPrecioAccesorios").equals("$500") && etiqueta(OrdenEntregaView.class, "lblTotal").contains("11,020"));
        revisa("el rechazo no registra contratación ni aparta la unidad", almacen.obtenerContrataciones().isEmpty() && "Disponible".equals(almacen.obtenerUnidadesDeEquipo(2L).get(0).getEstadoUnidad()));

        // varios equipos en la solicitud: se quita el que se agregó por error y los demás se evalúan
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        for (String equipo : new String[]{"Excavadora CAT 336", "Retroexcavadora JCB", "Excavadora CAT 320"}) {
            clic(SolicitudRentaView.class, "btnAgregarEquipo");
            fila(BusquedaEquipoView.class, "tableEquipoCatalogo", equipo);
            clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        }
        revisa("no hay tope de equipos: la solicitud trae los tres", tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 3);
        clic(SolicitudRentaView.class, "btnQuitarEquipo");
        revisa("Quitar sin elegir un renglón avisa y no quita nada: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("seleccione")
                && tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 3);
        fila(SolicitudRentaView.class, "tableEquipoSolicitado", "CAT 320");
        clic(SolicitudRentaView.class, "btnQuitarEquipo");
        revisa("Quitar deja los otros dos", tabla(SolicitudRentaView.class, "tableEquipoSolicitado").getRowCount() == 2
                && !tablaTexto(SolicitudRentaView.class, "tableEquipoSolicitado").contains("CAT 320"));
        capturarSolicitud();
        foto("P14_varios_equipos");
        capturaDeAviso = "P14_varios_equipos_registrados";
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("al registrar dice que son 2 equipos y que se revisan todos: " + ultimoAviso().mensaje().replace("\n", " "), ultimoAviso().mensaje().contains("2 equipos")
                && ultimoAviso().mensaje().contains("revisan todos"));
        revisa("quedan registrados los dos", almacen.obtenerSolicitudes().get(0).getEquiposSolicitados().size() == 2);
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        String pedidos = etiqueta(EquiposCompatiblesView.class, "lblEquipoPedido");
        revisa("Equipos compatibles dice los dos que pidió: " + pedidos, pedidos.contains("Retroexcavadora JCB") && pedidos.contains("Excavadora CAT 336") && !pedidos.contains("CAT 320"));
        foto("P14_varios_equipos_compatibles");
        continuarAAccesorios();
        boolean todosEvaluados = true;
        String motivoRegistrado = "";
        for (EquipoSolicitadoDTO pedido : almacen.obtenerSolicitudes().get(0).getEquiposSolicitados()) {
            todosEvaluados &= pedido.getEvaluacion() != null && pedido.getEvaluacion().getRecomendado() != null;
            motivoRegistrado = pedido.getEvaluacion() == null ? "" : pedido.getEvaluacion().getMotivoIncumplimiento();
        }
        revisa("los dos quedan evaluados; el motivo es el del que no cumple: " + motivoRegistrado, todosEvaluados && motivoRegistrado.contains("Retroexcavadora JCB") && !motivoRegistrado.contains("CAT 336"));
        marcarOpcional(true);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        clic(PropuestaRentaView.class, "btnAceptar");
        marcarCondiciones();
        clic(VerificacionCondicionView.class, "btnGenerar");
        revisa("la orden sale del equipo recomendado y la solicitud conserva los dos que pidió el cliente", visible().equals("OrdenEntregaView") && etiqueta(OrdenEntregaView.class, "lblEquipo").contains("CAT 320")
                && almacen.obtenerSolicitudes().get(0).getEquiposSolicitados().size() == 2);
        // la lista solo trae lo que cumple y lo que pidió el cliente
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Excavadora CAT 320");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnRegistrar");
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        String sinPedir = tablaTexto(EquiposCompatiblesView.class, "tableEquipos");
        revisa("si no la pidió y no cumple, la JCB no sale en la lista: " + sinPedir, !sinPedir.contains("JCB") && sinPedir.contains("CAT 320") && sinPedir.contains("CAT 336"));
        sinExcepciones();
    }

    private static void p15Mensajes() throws Exception {
        caso("P15", "C8", "Mensaje de resultado", "Acciones válidas: entrar, registrar cliente, registrar solicitud, generar la orden.",
                "Se muestra confirmación, resultado o advertencia adecuada.");
        nuevaApp();
        capturaDeAviso = "P15_inicio_de_sesion";
        entrar();
        revisa("inicio de sesión: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Exitoso") && ultimoAviso().tipo() == JOptionPane.INFORMATION_MESSAGE);
        revisa("el botón de los avisos dice Aceptar", ultimoAviso().botones().equals(List.of("Aceptar")));
        revisa("el selector de archivos y el calendario están en español", "Abrir".equals(javax.swing.UIManager.getString("FileChooser.openButtonText", java.util.Locale.getDefault()))
                && "es".equals(selector(SolicitudRentaView.class, "datePickerInicio").getSettings().getLocale().getLanguage()));
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevoCliente");
        capturarCliente("Ana Ruiz Soto", "RUAS900101AB1", "6441112233", "ana.ruiz@gmail.com");
        avisos.clear();
        clic(RegistroClienteView.class, "btnRegistrar");
        revisa("cliente: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("registrado correctamente"));
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        capturaDeAviso = "P15_solicitud_registrada";
        clic(SolicitudRentaView.class, "btnRegistrar");
        revisa("solicitud: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("Registrada Correctamente"));
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        continuarAAccesorios();
        marcarOpcional(true);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        revisa("evaluación: " + ultimoAviso().mensaje().replace("\n", " "), ultimoAviso().mensaje().contains("registrada con éxito"));
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
        clic(RequisitosRentaView.class, "btnValidar");
        revisa("requisitos: " + ultimoAviso().mensaje().replace("\n", " "), ultimoAviso().mensaje().contains("validados"));
        clic(PropuestaRentaView.class, "btnAceptar");
        marcarCondiciones();
        capturaDeAviso = "P15_orden_registrada";
        clic(VerificacionCondicionView.class, "btnGenerar");
        revisa("orden: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("orden de entrega") && ultimoAviso().mensaje().contains("registrada"));
        sinExcepciones();
    }

    private static void p16FlujoCompleto() throws Exception {
        caso("P16", "C7/C10", "Flujo completo", "Contratación del escenario de principio a fin: Julián Martínez, CAT 320, M-001, del 10 al 15 de septiembre. Después, otra con otra clienta, otro equipo y otro periodo.",
                "El recorrido concluye sin fallas y respeta el orden establecido.");
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        foto("P16_01_identificacion");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        foto("P16_02_busqueda_equipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        foto("P16_03_solicitud");
        String folio = etiqueta(SolicitudRentaView.class, "lblFolio").replace("Folio: ", "");
        revisa("el folio es el del escenario: " + folio, folio.equals("FOL-89241"));
        clic(SolicitudRentaView.class, "btnRegistrar");
        capturarRequerimientos();
        foto("P16_04_analisis");
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        foto("P16_05_equipos_compatibles");
        revisa("la JCB no cumple y marca por qué", tablaTexto(EquiposCompatiblesView.class, "tableEquipos").contains("Potencia de 109 HP"));
        revisa("la JCB no se puede marcar y las CAT sí", !tabla(EquiposCompatiblesView.class, "tableEquipos").isCellEditable(0, 3) && tabla(EquiposCompatiblesView.class, "tableEquipos").isCellEditable(1, 3));
        continuarAAccesorios();
        marcarOpcional(true);
        foto("P16_06_accesorios");
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        foto("P16_07_evaluacion_tecnica");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        foto("P16_08_inventario");
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        foto("P16_09_calendario");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        foto("P16_10_condiciones");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        foto("P16_11_cotizacion");
        revisa("la cotización da el total del escenario: $11,020", etiquetas(CotizacionView.class).contains("11,020"));
        clic(CotizacionView.class, "btnContinuar");
        foto("P16_12_requisitos");
        clic(RequisitosRentaView.class, "btnValidar");
        foto("P16_13_propuesta");
        revisa("la propuesta dice equipo y unidad: " + etiqueta(PropuestaRentaView.class, "lblEquipo"), etiqueta(PropuestaRentaView.class, "lblEquipo").equals("Excavadora CAT 320 · unidad M-001"));
        revisa("la propuesta trae los dos accesorios en su fila: " + etiqueta(PropuestaRentaView.class, "lblAccesorioObligatorio") + " / " + etiqueta(PropuestaRentaView.class, "lblAccesorioOpcional"),
                etiqueta(PropuestaRentaView.class, "lblAccesorioObligatorio").contains("Kit de señalización") && etiqueta(PropuestaRentaView.class, "lblAccesorioOpcional").contains("Cucharón"));
        clic(PropuestaRentaView.class, "btnAceptar");
        foto("P16_14_verificacion");
        revisa("Verificación dice la fecha, la hora y el lugar de la entrega: " + etiqueta(VerificacionCondicionView.class, "lblEntrega"),
                etiqueta(VerificacionCondicionView.class, "lblEntrega").contains("10/09/2026 antes de las 8:00 AM, en Av. Guerrero #1105"));
        marcarCondiciones();
        clic(VerificacionCondicionView.class, "btnGenerar");
        foto("P16_15_orden_de_entrega");
        revisa("la orden dice la dirección: " + etiqueta(OrdenEntregaView.class, "lblDireccion"), etiqueta(OrdenEntregaView.class, "lblDireccion").contains("Av. Guerrero #1105"));
        revisa("la orden dice la fecha y la hora de entrega, las mismas de Verificación: " + etiqueta(OrdenEntregaView.class, "lblFechaEntrega"), etiqueta(OrdenEntregaView.class, "lblFechaEntrega").equals("10/09/2026, antes de las 8:00 AM"));
        revisa("la orden dice el total: " + etiqueta(OrdenEntregaView.class, "lblTotal"), etiqueta(OrdenEntregaView.class, "lblTotal").contains("11,020"));
        ContratacionRentaDTO contratacion = almacen.buscarContratacionPorFolio(folio);
        revisa("la contratación queda FORMALIZADA con su orden", contratacion != null && "FORMALIZADA".equals(contratacion.getEstado()) && contratacion.getOrdenEntrega() != null);
        revisa("la M-001 queda apartada", "Ocupada".equals(almacen.obtenerUnidadesDeEquipo(2L).get(0).getEstadoUnidad()));
        revisa("la solicitud queda Formalizada", "Formalizada".equals(almacen.buscarSolicitudPorFolio(folio).getEstadoSolicitud()));
        clic(OrdenEntregaView.class, "btnTerminar");
        revisa("al terminar vuelve al menú", visible().equals("MenuPrincipalView"));
        clic(MenuPrincipalView.class, "btnCerrarSesion");
        revisa("Cerrar sesión vuelve al inicio sin dejar escritos el correo ni la contraseña", visible().equals("InicioSesionView")
                && texto(InicioSesionView.class, "txtFieldCorreo").isEmpty() && texto(InicioSesionView.class, "psswFieldContrasenia").isEmpty());

        // con otro cliente, otra obra y otro periodo no debe quedar ningún dato fijo del escenario
        nuevaApp();
        entrar();
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "LOMF920410H12");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "CAT 336");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        escribir(SolicitudRentaView.class, "txtFieldNombreObra", "Bodega Norte");
        escribir(SolicitudRentaView.class, "txtFieldDireccion", "Calle 200 #45, Navojoa");
        escribir(SolicitudRentaView.class, "txtAreaActividadRealizar", "Nivelar el terreno");
        fecha(SolicitudRentaView.class, "datePickerInicio", LocalDate.of(2026, 12, 1));
        fecha(SolicitudRentaView.class, "datePickerFinal", LocalDate.of(2026, 12, 3));
        sinDatosFijos(SolicitudRentaView.class);
        clic(SolicitudRentaView.class, "btnRegistrar");
        capturarRequerimientos();
        sinDatosFijos(AnalisisRequerimientoView.class);
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
        marcarEquipos("Excavadora CAT 336", null);
        continuarAAccesorios();
        sinDatosFijos(SeleccionAccesoriosView.class);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        sinDatosFijos(EvaluacionTecnicaView.class);
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        sinDatosFijos(VerificacionInventarioView.class);
        fila(VerificacionInventarioView.class, "tableUnidades", "N-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        sinDatosFijos(CalendarioUnidadView.class);
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        sinDatosFijos(CondicionesContratacionView.class);
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        sinDatosFijos(CotizacionView.class);
        clic(CotizacionView.class, "btnContinuar");
        sinDatosFijos(RequisitosRentaView.class);
        clic(RequisitosRentaView.class, "btnValidar");
        sinDatosFijos(PropuestaRentaView.class);
        clic(PropuestaRentaView.class, "btnAceptarParcial");
        sinDatosFijos(PropuestaPendienteView.class);
        clic(PropuestaPendienteView.class, "btnAceptar");
        sinDatosFijos(VerificacionCondicionView.class);
        marcarCondiciones();
        clic(VerificacionCondicionView.class, "btnGenerar");
        sinDatosFijos(OrdenEntregaView.class);
        foto("P16_16_otro_cliente");
        revisa("la orden es de la otra clienta y dice sus datos", etiqueta(OrdenEntregaView.class, "lblCliente").contains("María Fernanda") && etiqueta(OrdenEntregaView.class, "lblDireccion").contains("Calle 200"));
        revisa("durante todas las pruebas se abrieron las 25 pantallas: " + pantallasVistas.size(), pantallasVistas.size() == 25);
        sinExcepciones();
    }

    private static void p17Subsistema() throws Exception {
        caso("P17", "C5", "Reglas del subsistema", "Pedirle al subsistema, sin pantallas, la comparación de equipos, la disponibilidad por periodo, la cotización, la cancelación y el inicio de sesión; luego contraseña incorrecta y equipo sin tarifa desde las pantallas.",
                "Las respuestas salen de los datos registrados y lo que no se puede hacer se avisa sin errores.");
        nuevaApp();
        ISubsistemaContratacionRenta subsistema = new SubsistemaContratacionRenta();

        // comparación de equipos: las cuatro características, con lo que tenga registrado cada equipo
        List<RequerimientoTecnicoDTO> requerimientos = List.of(new RequerimientoTecnicoDTO("Profundidad de trabajo", 3.5, "m", "Mínimo"),
                new RequerimientoTecnicoDTO("Potencia mínima", 120.0, "HP", "Mínimo"), new RequerimientoTecnicoDTO("Peso máximo permitido", 22.0, "t", "Máximo"),
                new RequerimientoTecnicoDTO("Ancho de acceso", 4.2, "m", "Máximo"), new RequerimientoTecnicoDTO("Tipo de terreno: Arenoso", null, null, null));
        revisa("la JCB no cumple por los 109 HP: " + motivoDe(subsistema, requerimientos, "Retroexcavadora JCB"),
                "potencia de 109 HP; se piden 120 HP como mínimo".equals(motivoDe(subsistema, requerimientos, "Retroexcavadora JCB")));
        revisa("sin datos registrados la CAT 320 no se descarta", motivoDe(subsistema, requerimientos, "Excavadora CAT 320") == null);
        EquipoDTO cat320 = null;
        for (EquipoDTO equipo : (List<EquipoDTO>) campo(almacen, "catalogoEquipos")) {
            cat320 = equipo.getModelo().equals("Excavadora CAT 320") ? equipo : cat320;
        }
        cat320.setProfundidadExcavacion(3.5);
        cat320.setPotencia(120.0);
        cat320.setPeso(22.0);
        cat320.setAnchoOperacion(4.2);
        revisa("justo en cada límite cumple", motivoDe(subsistema, requerimientos, "Excavadora CAT 320") == null);
        cat320.setProfundidadExcavacion(3.0);
        revisa("le falta profundidad: " + motivoDe(subsistema, requerimientos, "Excavadora CAT 320"), "profundidad de 3 m; se piden 3.50 m como mínimo".equals(motivoDe(subsistema, requerimientos, "Excavadora CAT 320")));
        cat320.setProfundidadExcavacion(3.5);
        cat320.setPotencia(119.5);
        revisa("le falta potencia: " + motivoDe(subsistema, requerimientos, "Excavadora CAT 320"), "potencia de 119.50 HP; se piden 120 HP como mínimo".equals(motivoDe(subsistema, requerimientos, "Excavadora CAT 320")));
        cat320.setPotencia(120.0);
        cat320.setPeso(30.0);
        revisa("pesa de más: " + motivoDe(subsistema, requerimientos, "Excavadora CAT 320"), "peso de 30 t; se piden 22 t como máximo".equals(motivoDe(subsistema, requerimientos, "Excavadora CAT 320")));
        cat320.setPeso(22.0);
        cat320.setAnchoOperacion(5.5);
        revisa("no cabe por el acceso: " + motivoDe(subsistema, requerimientos, "Excavadora CAT 320"), "ancho de 5.50 m; se piden 4.20 m como máximo".equals(motivoDe(subsistema, requerimientos, "Excavadora CAT 320")));
        cat320.setPeso(30.0);
        revisa("con dos fallas dice las dos: " + motivoDe(subsistema, requerimientos, "Excavadora CAT 320"), "peso de 30 t; se piden 22 t como máximo · ancho de 5.50 m; se piden 4.20 m como máximo".equals(motivoDe(subsistema, requerimientos, "Excavadora CAT 320")));
        revisa("sin requerimientos ninguno se descarta", motivoDe(subsistema, null, "Excavadora CAT 320") == null && motivoDe(subsistema, null, "Retroexcavadora JCB") == null);

        // disponibilidad: sale de las ocupaciones que caen en el periodo
        nuevaApp();
        subsistema = new SubsistemaContratacionRenta();
        EquipoDTO equipo = subsistema.buscarEquipoPorId(2L);
        revisa("del 10 al 15, como en el escenario", "M-001 Disponible, M-002 En mantenimiento, M-003 Ocupada".equals(estados(subsistema, equipo, 10, 15)));
        revisa("del 20 al 25 las tres están libres", "M-001 Disponible, M-002 Disponible, M-003 Disponible".equals(estados(subsistema, equipo, 20, 25)));
        revisa("el último día de la ocupación todavía cuenta", "M-001 Disponible, M-002 En mantenimiento, M-003 Ocupada".equals(estados(subsistema, equipo, 15, 20)));
        revisa("el día siguiente ya no", "M-001 Disponible, M-002 Disponible, M-003 Disponible".equals(estados(subsistema, equipo, 16, 20)));
        revisa("el primer día de la ocupación ya cuenta", "M-001 Disponible, M-002 En mantenimiento, M-003 Ocupada".equals(estados(subsistema, equipo, 13, 13)));
        revisa("el día anterior todavía no", "M-001 Disponible, M-002 En mantenimiento, M-003 Disponible".equals(estados(subsistema, equipo, 1, 12)));
        revisa("antes del mantenimiento también está libre la M-002", "M-001 Disponible, M-002 Disponible, M-003 Disponible".equals(estados(subsistema, equipo, 1, 9)));
        PeriodoSolicitadoDTO escenario = new PeriodoSolicitadoDTO(LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 15), 6);
        PeriodoSolicitadoDTO otro = new PeriodoSolicitadoDTO(LocalDate.of(2026, 9, 20), LocalDate.of(2026, 9, 25), 6);
        revisa("solo trae las ocupaciones que caen en el periodo", subsistema.verificarDisponibilidadUnidades(equipo, escenario).get(2).getOcupaciones().size() == 1
                && subsistema.verificarDisponibilidadUnidades(equipo, otro).get(2).getOcupaciones().isEmpty());
        revisa("preguntar no cambia lo registrado", "Ocupada".equals(almacen.obtenerUnidadesDeEquipo(2L).get(2).getEstadoUnidad()) && almacen.obtenerUnidadesDeEquipo(2L).get(2).getOcupaciones().size() == 1);
        UnidadFisicaDTO m001 = subsistema.ocuparUnidad(subsistema.buscarUnidadPorId(almacen.obtenerUnidadesDeEquipo(2L).get(0).getIdUnidad()), otro);
        revisa("apartar la unidad la deja registrada", "Ocupada".equals(m001.getEstadoUnidad()) && "Ocupada".equals(almacen.obtenerUnidadesDeEquipo(2L).get(0).getEstadoUnidad())
                && almacen.obtenerUnidadesDeEquipo(2L).get(0).getOcupaciones().size() == 1);
        revisa("apartada del 20 al 25 sale ocupada en esas fechas y libre en otras", estados(subsistema, equipo, 20, 25).startsWith("M-001 Ocupada") && estados(subsistema, equipo, 10, 15).startsWith("M-001 Disponible"));
        revisa("un equipo sin unidades se avisa con una excepción", lanza(IllegalArgumentException.class, sub -> {
            EquipoDTO sinUnidades = new EquipoDTO();
            sinUnidades.setIdEquipo(99L);
            sinUnidades.setModelo("Sin unidades");
            sub.verificarDisponibilidadUnidades(sinUnidades, escenario);
        }));

        // cotización
        CotizacionDTO cotizacion = subsistema.generarCotizacion("FOL-X", equipo, 6, equipo.getAccesorios());
        revisa("la cuenta del escenario: $9,500 + IVA = $11,020", cotizacion.getSubtotal() == 9500.0 && cotizacion.getIva() == 1520.0 && cotizacion.getTotal() == 11020.0 && "FOL-X".equals(cotizacion.getFolioSolicitud()));
        revisa("con la tarifa registrada", subsistema.tarifaDiaria(equipo) == 1500.0);
        revisa("el depósito es el del requisito", subsistema.depositoGarantia(subsistema.obtenerRequisitosRenta()) == 3200.0);
        ponerTarifa("Excavadora CAT 336", null);
        EquipoDTO cat336 = subsistema.buscarEquipoPorId(3L);
        revisa("sin tarifa no se cotiza", lanza(IllegalStateException.class, sub -> sub.generarCotizacion("FOL-X", cat336, 6, cat336.getAccesorios())));
        revisa("sin tarifa no hay tarifa diaria", lanza(IllegalStateException.class, sub -> sub.tarifaDiaria(cat336)));

        // clientes y solicitudes
        int clientes = almacen.obtenerClientes().size();
        ClienteDTO repetido = new ClienteDTO(null, "Otro", "MARJ8503158A1", "6440000000", "otro@correo.com", "Particular");
        revisa("un RFC repetido no se registra", lanza(IllegalArgumentException.class, sub -> sub.registrarClienteNuevo(repetido)) && almacen.obtenerClientes().size() == clientes);
        revisa("un folio que no existe se avisa con una excepción", lanza(IllegalArgumentException.class, sub -> sub.consultarSolicitud("FOL-1")));
        revisa("sin cotización ni contratación regresa null", subsistema.consultarCotizacion("FOL-1") == null && subsistema.consultarContratacion("FOL-1") == null);
        SolicitudRentaDTO nueva = new SolicitudRentaDTO();
        nueva.setFolio(subsistema.siguienteFolio());
        ResultadoCancelacionDTO cancelacion = subsistema.cancelarSolicitudRenta(nueva);
        revisa("una solicitud sin registrar se cancela y queda registrada", cancelacion.getCancelada() && "Cancelada".equals(cancelacion.getEstadoSolicitud())
                && "Cancelada".equals(subsistema.consultarSolicitud(nueva.getFolio()).getEstadoSolicitud()));
        SolicitudRentaDTO formalizada = new SolicitudRentaDTO();
        formalizada.setFolio(subsistema.siguienteFolio());
        formalizada.setEstadoSolicitud("Formalizada");
        formalizada = subsistema.registrarSolicitud(formalizada);
        ResultadoCancelacionDTO negada = subsistema.cancelarSolicitudRenta(formalizada);
        revisa("con contratación formalizada no se cancela y dice por qué: " + negada.getMotivo(), !negada.getCancelada() && negada.getMotivo() != null
                && "Formalizada".equals(subsistema.consultarSolicitud(formalizada.getFolio()).getEstadoSolicitud()));

        // contratación
        ContratacionRentaDTO pendiente = new ContratacionRentaDTO();
        pendiente.setFolioSolicitud("FOL-P");
        pendiente.setEstado("PENDIENTE");
        ResultadoContratacionDTO sinFormalizar = subsistema.registrarContratacion(pendiente);
        revisa("pendiente: no queda formalizada y dice por qué", !sinFormalizar.getFormalizada() && sinFormalizar.getMotivo() != null && sinFormalizar.getContratacion().getIdContratacion() != null);
        SolicitudRentaDTO conPeriodo = new SolicitudRentaDTO();
        conPeriodo.setPeriodo(escenario);
        conPeriodo.setDireccionObra("Av. Guerrero #1105");
        OrdenEntregaDTO orden = subsistema.generarOrdenEntrega(conPeriodo);
        revisa("la orden queda registrada, con la fecha, la dirección y el horario", orden.getIdOrdenEntrega() != null && orden.getFechaEntrega().equals(escenario.getFechaInicio())
                && "Av. Guerrero #1105".equals(orden.getDireccionEntrega()) && orden.getHorario().equals(subsistema.horarioEntrega()));
        ContratacionRentaDTO formal = new ContratacionRentaDTO();
        formal.setFolioSolicitud("FOL-F");
        formal.setEstado("FORMALIZADA");
        formal.setOrdenEntrega(orden);
        ResultadoContratacionDTO conOrden = subsistema.registrarContratacion(formal);
        revisa("formalizada: sin motivo y conserva su orden", conOrden.getFormalizada() && conOrden.getMotivo() == null
                && orden.getIdOrdenEntrega().equals(subsistema.consultarContratacion("FOL-F").getOrdenEntrega().getIdOrdenEntrega()));

        // inicio de sesión
        ISubsistemaInicioSesion sesion = new SubsistemaInicioSesion();
        revisa("la cuenta registrada entra, sin regresar la contraseña", sesion.autenticarUsuario("asesor@maquinaria.com", "maquinaria2026") != null
                && sesion.autenticarUsuario("ASESOR@maquinaria.com", "maquinaria2026").getContrasenia() == null);
        revisa("otra contraseña u otro correo no entran", sesion.autenticarUsuario("asesor@maquinaria.com", "otraclave2026") == null && sesion.autenticarUsuario("otro@maquinaria.com", "maquinaria2026") == null);

        // desde las pantallas
        nuevaApp();
        escribir(InicioSesionView.class, "txtFieldCorreo", "asesor@maquinaria.com");
        escribir(InicioSesionView.class, "psswFieldContrasenia", "otraclave2026");
        clic(InicioSesionView.class, "btnIngresar");
        revisa("con otra contraseña avisa y no entra: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("no corresponden") && visible().equals("InicioSesionView"));
        entrar();
        revisa("con la correcta entra", visible().equals("MenuPrincipalView"));
        ponerTarifa("Excavadora CAT 336", null);
        hastaLosCompatibles();
        marcarEquipos("Excavadora CAT 336", null);
        continuarAAccesorios();
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
        fila(VerificacionInventarioView.class, "tableUnidades", "N-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        revisa("un equipo sin tarifa no se cotiza: avisa y se queda: " + ultimoAviso().mensaje(), ultimoAviso().mensaje().contains("CAT 336") && ultimoAviso().mensaje().contains("tarifa")
                && visible().equals("CalendarioUnidadView") && almacen.buscarCotizacionPorFolio(almacen.obtenerSolicitudes().get(0).getFolio()) == null);
        foto("P17_equipo_sin_tarifa");
        sinExcepciones();
    }

    private interface Peticion {

        void pedir(ISubsistemaContratacionRenta subsistema) throws Exception;
    }

    private static boolean lanza(Class<? extends Exception> tipo, Peticion peticion) {
        try {
            peticion.pedir(new SubsistemaContratacionRenta());
            return false;
        } catch (Exception ex) {
            return tipo.isInstance(ex);
        }
    }

    private static String motivoDe(ISubsistemaContratacionRenta subsistema, List<RequerimientoTecnicoDTO> requerimientos, String modelo) {
        for (Map.Entry<EquipoDTO, String> evaluado : subsistema.buscarEquiposCompatibles(requerimientos).entrySet()) {
            if (evaluado.getKey().getModelo().equals(modelo)) {
                return evaluado.getValue();
            }
        }
        return "(no está en el catálogo)";
    }

    private static String estados(ISubsistemaContratacionRenta subsistema, EquipoDTO equipo, int inicio, int fin) {
        List<String> estados = new ArrayList<>();
        PeriodoSolicitadoDTO periodo = new PeriodoSolicitadoDTO(LocalDate.of(2026, 9, inicio), LocalDate.of(2026, 9, fin), fin - inicio + 1);
        for (UnidadFisicaDTO unidad : subsistema.verificarDisponibilidadUnidades(equipo, periodo)) {
            estados.add(unidad.getNumeroEconomico() + " " + unidad.getEstadoUnidad());
        }
        return String.join(", ", estados);
    }

    private static boolean existeClase(String nombre) {
        try {
            Class.forName(nombre);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    // ================================================================ recorridos de apoyo
    private static void entrar() throws Exception {
        escribir(InicioSesionView.class, "txtFieldCorreo", "asesor@maquinaria.com");
        escribir(InicioSesionView.class, "psswFieldContrasenia", "maquinaria2026");
        clic(InicioSesionView.class, "btnIngresar");
    }

    private static void capturarCliente(String nombre, String rfc, String telefono, String correo) throws Exception {
        escribir(RegistroClienteView.class, "txtFieldNombre", nombre);
        escribir(RegistroClienteView.class, "txtFieldRFC", rfc);
        escribir(RegistroClienteView.class, "txtFieldTelefono", telefono);
        escribir(RegistroClienteView.class, "txtFieldCorreoElectronico", correo);
    }

    private static void capturarSolicitud() throws Exception {
        escribir(SolicitudRentaView.class, "txtFieldNombreObra", "Remodelación Plaza Goya");
        escribir(SolicitudRentaView.class, "txtFieldDireccion", "Av. Guerrero #1105, Ciudad Obregón, Sonora");
        escribir(SolicitudRentaView.class, "txtAreaActividadRealizar", "Excavar cepas para la cimentación y retirar el material, 3.50 m de profundidad");
        fecha(SolicitudRentaView.class, "datePickerInicio", LocalDate.of(2026, 9, 10));
        fecha(SolicitudRentaView.class, "datePickerFinal", LocalDate.of(2026, 9, 15));
    }

    private static void capturarRequerimientos() throws Exception {
        escribir(AnalisisRequerimientoView.class, "txtFieldProfundidad", "3.50");
        escribir(AnalisisRequerimientoView.class, "txtFieldPotencia", "120");
        escribir(AnalisisRequerimientoView.class, "txtFieldPesoMaximo", "22");
        escribir(AnalisisRequerimientoView.class, "txtFieldAnchoAcceso", "4.20");
        seleccionarCombo(AnalisisRequerimientoView.class, "comboTipoTerreno", "Terracería compactada");
    }

    private static void hastaLosCompatibles() throws Exception {
        clic(MenuPrincipalView.class, "btnContratacionEquipo");
        fila(IdentificacionClienteView.class, "tableCliente", "MARJ8503158A1");
        clic(IdentificacionClienteView.class, "btnRegistrarNuevaSolicitud");
        clic(SolicitudRentaView.class, "btnAgregarEquipo");
        fila(BusquedaEquipoView.class, "tableEquipoCatalogo", "Retroexcavadora JCB");
        clic(BusquedaEquipoView.class, "btnRegistrarEquipo");
        capturarSolicitud();
        clic(SolicitudRentaView.class, "btnRegistrar");
        capturarRequerimientos();
        clic(AnalisisRequerimientoView.class, "btnBuscarEquipos");
    }

    private static void hastaElInventario() throws Exception {
        hastaLosCompatibles();
        continuarAAccesorios();
        marcarOpcional(true);
        clic(SeleccionAccesoriosView.class, "btnContinuar");
        clic(EvaluacionTecnicaView.class, "btnEnviarVerificacion");
    }

    private static void hastaLosRequisitos() throws Exception {
        hastaElInventario();
        fila(VerificacionInventarioView.class, "tableUnidades", "M-001");
        clic(VerificacionInventarioView.class, "btnSeleccionar");
        clic(CalendarioUnidadView.class, "btnSeleccionarUnidad");
        clic(CondicionesContratacionView.class, "btnEnviarValidacion");
        clic(CotizacionView.class, "btnContinuar");
    }

    private static void hastaLaPropuesta() throws Exception {
        hastaLosRequisitos();
        clic(RequisitosRentaView.class, "btnValidar");
    }

    // Termina en la orden de entrega y regresa el folio
    private static String contratacionHastaLaOrden() throws Exception {
        hastaLaPropuesta();
        String folio = almacen.obtenerSolicitudes().get(almacen.obtenerSolicitudes().size() - 1).getFolio();
        clic(PropuestaRentaView.class, "btnAceptar");
        marcarCondiciones();
        clic(VerificacionCondicionView.class, "btnGenerar");
        return folio;
    }

    private static void marcarOpcional(boolean marcar) throws Exception {
        edt(() -> {
            JTable accesorios = (JTable) campo(ventana(SeleccionAccesoriosView.class), "tableAccesorios");
            for (int i = 0; i < accesorios.getRowCount(); i++) {
                if (accesorios.isCellEditable(i, 2)) {
                    accesorios.setValueAt(marcar, i, 2);
                }
            }
        });
    }

    // Marca en la tabla el recomendado y la alternativa; sin alternativa, null
    private static void marcarEquipos(String recomendado, String alternativa) throws Exception {
        edt(() -> {
            JTable equipos = (JTable) campo(ventana(EquiposCompatiblesView.class), "tableEquipos");
            for (int i = 0; i < equipos.getRowCount(); i++) {
                if (equipos.getValueAt(i, 0).equals(recomendado)) {
                    equipos.setValueAt(true, i, 3);
                }
            }
            for (int i = 0; i < equipos.getRowCount(); i++) {
                equipos.setValueAt(equipos.getValueAt(i, 0).equals(alternativa), i, 4);
            }
        });
    }

    // Como en el escenario: si todavía no se marca el recomendado, la CAT 320 y de alternativa la CAT 336
    private static void continuarAAccesorios() throws Exception {
        JTable equipos = tabla(EquiposCompatiblesView.class, "tableEquipos");
        boolean hayRecomendado = false;
        for (int i = 0; i < equipos.getRowCount(); i++) {
            hayRecomendado |= Boolean.TRUE.equals(equipos.getValueAt(i, 3));
        }
        if (!hayRecomendado) {
            marcarEquipos("Excavadora CAT 320", "Excavadora CAT 336");
        }
        clic(EquiposCompatiblesView.class, "btnContinuarAccesorios");
    }

    private static boolean filaVisible(String fila) throws Exception {
        return ((java.awt.Component) campo(ventana(SolicitudDatosFaltantesView.class), fila)).isVisible();
    }

    private static void marcarCondiciones() throws Exception {
        edt(() -> {
            JFrame verificacion = ventana(VerificacionCondicionView.class);
            for (String casilla : new String[]{"chkCliente", "chkEquipo", "chkAccesorios", "chkPeriodo", "chkTarifa"}) {
                ((JCheckBox) campo(verificacion, casilla)).setSelected(true);
            }
        });
    }

    // ================================================================ manejo de las pantallas
    private static void nuevaApp() throws Exception {
        edt(() -> {
            for (Window w : Window.getWindows()) {
                w.dispose();
            }
            Main.prepararAspecto();
            // cada prueba empieza con el almacén como al abrir la aplicación
            Field instancia = AlmacenTemporal.class.getDeclaredField("instance");
            instancia.setAccessible(true);
            instancia.set(null, null);
            almacen = AlmacenTemporal.getInstance();
            control = Main.iniciar();
            coordinador = campo(control, "coordinador");
            control.abrirIniciarSesion();
        });
        avisos.clear();
    }

    // Para probar qué pasa con un equipo sin tarifa
    private static void ponerTarifa(String modelo, Double tarifa) throws Exception {
        for (EquipoDTO equipo : (List<EquipoDTO>) campo(almacen, "catalogoEquipos")) {
            if (equipo.getModelo().equals(modelo)) {
                equipo.setCostoDiario(tarifa);
            }
        }
    }

    private static List<JFrame> ventanas() throws Exception {
        return List.of((JFrame[]) campo(coordinador, "ventanas"));
    }

    private static int cuantasVentanas() throws Exception {
        return ventanas().size();
    }

    private static JFrame ventana(Class<? extends JFrame> tipo) throws Exception {
        for (JFrame v : ventanas()) {
            if (tipo.isInstance(v)) {
                return v;
            }
        }
        throw new IllegalArgumentException(tipo.getName());
    }

    // El nombre de la ventana a la vista; si hay más de una o ninguna, se nota en la comparación
    private static String visible() throws Exception {
        List<String> abiertas = new ArrayList<>();
        edt(() -> {
            for (JFrame v : ventanas()) {
                if (v.isShowing()) {
                    abiertas.add(v.getClass().getSimpleName());
                }
            }
        });
        return String.join(",", abiertas);
    }

    private static void clic(Class<? extends JFrame> pantalla, String boton) throws Exception {
        AbstractButton b = (AbstractButton) campo(ventana(pantalla), boton);
        if (!b.isShowing()) {
            revisa(boton + " de " + pantalla.getSimpleName() + " tiene que estar a la vista para oprimirlo", false);
        }
        segundoClic(pantalla, boton);
    }

    // El segundo clic de un doble clic: llega cuando la pantalla ya pudo haberse ido
    private static void segundoClic(Class<? extends JFrame> pantalla, String boton) throws Exception {
        AbstractButton b = (AbstractButton) campo(ventana(pantalla), boton);
        ejecutar(b::doClick, pantalla.getSimpleName() + "." + boton);
        pantallasVistas.add(visible());
    }

    // Lo que se lee en la pantalla no debe traer datos fijos de otra contratación
    private static void sinDatosFijos(Class<? extends JFrame> pantalla) throws Exception {
        String texto = etiquetas(pantalla);
        for (String fijo : new String[]{"Julián", "MARJ8503158A1", "Plaza Goya", "Guerrero", "M-001", "CAT 320", "11,020", "10/09/2026", "15/09/2026"}) {
            if (texto.contains(fijo)) {
                revisa(pantalla.getSimpleName() + " muestra un dato fijo: " + fijo, false);
                return;
            }
        }
        revisa(pantalla.getSimpleName() + " solo trae los datos de esta contratación", true);
    }

    // Una etiqueta de una línea que no cabe en su lugar se ve cortada con "..."
    private static void revisarTextosCortados(Container c, String pantalla) {
        for (Component x : c.getComponents()) {
            if (x instanceof JLabel l && l.isShowing() && l.getText() != null && !l.getText().startsWith("<html>")
                    && l.getFontMetrics(l.getFont()).stringWidth(l.getText()) > l.getWidth() && l.getWidth() > 0) {
                revisa(pantalla + ": texto cortado: " + l.getText(), false);
            }
            if (x instanceof Container k) {
                revisarTextosCortados(k, pantalla);
            }
        }
    }

    private static void cerrarConX(Class<? extends JFrame> pantalla) throws Exception {
        JFrame v = ventana(pantalla);
        ejecutar(() -> v.dispatchEvent(new WindowEvent(v, WindowEvent.WINDOW_CLOSING)), pantalla.getSimpleName() + " X");
    }

    private static void ejecutar(Runnable accion, String que) throws Exception {
        try {
            SwingUtilities.invokeAndWait(accion);
        } catch (java.lang.reflect.InvocationTargetException e) {
            excepciones.add(e.getCause());
            revisa("sin excepción en " + que + ": " + e.getCause(), false);
        }
        SwingUtilities.invokeAndWait(() -> { });
    }

    private static void escribir(Class<? extends JFrame> pantalla, String campo, String valor) throws Exception {
        JTextComponent t = (JTextComponent) campo(ventana(pantalla), campo);
        edt(() -> t.setText(valor));
    }

    private static String texto(Class<? extends JFrame> pantalla, String campo) throws Exception {
        return ((JTextComponent) campo(ventana(pantalla), campo)).getText();
    }

    private static String etiqueta(Class<? extends JFrame> pantalla, String nombre) throws Exception {
        return String.valueOf(((JLabel) campo(ventana(pantalla), nombre)).getText()).replaceAll("<[^>]+>", "");
    }

    // Todo el texto que se lee en las etiquetas visibles de una pantalla
    private static String etiquetas(Class<? extends JFrame> pantalla) throws Exception {
        StringBuilder sb = new StringBuilder();
        juntarEtiquetas(ventana(pantalla).getContentPane(), sb);
        return sb.toString();
    }

    private static void juntarEtiquetas(Container c, StringBuilder sb) {
        for (Component x : c.getComponents()) {
            if (x instanceof JLabel l && l.isVisible()) {
                sb.append(l.getText()).append(" | ");
            }
            if (x instanceof Container k) {
                juntarEtiquetas(k, sb);
            }
        }
    }

    private static JTable tabla(Class<? extends JFrame> pantalla, String nombre) throws Exception {
        return (JTable) campo(ventana(pantalla), nombre);
    }

    private static String tablaTexto(Class<? extends JFrame> pantalla, String nombre) throws Exception {
        JTable t = tabla(pantalla, nombre);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t.getRowCount(); i++) {
            sb.append(filaTexto(t, i)).append(" / ");
        }
        return sb.toString();
    }

    private static String filaTexto(JTable t, int fila) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < t.getColumnCount(); j++) {
            sb.append(t.getValueAt(fila, j)).append(" ");
        }
        return sb.toString().trim();
    }

    // Selecciona el renglón que contiene el texto
    private static void fila(Class<? extends JFrame> pantalla, String nombre, String contiene) throws Exception {
        JTable t = tabla(pantalla, nombre);
        edt(() -> {
            for (int i = 0; i < t.getRowCount(); i++) {
                if (filaTexto(t, i).contains(contiene)) {
                    t.setRowSelectionInterval(i, i);
                    return;
                }
            }
            revisa("la tabla " + nombre + " tiene " + contiene, false);
        });
    }

    private static void seleccionarCombo(Class<? extends JFrame> pantalla, String nombre, String opcion) throws Exception {
        JComboBox<?> c = (JComboBox<?>) campo(ventana(pantalla), nombre);
        edt(() -> c.setSelectedItem(opcion));
    }

    private static String combo(Class<? extends JFrame> pantalla, String nombre) throws Exception {
        return String.valueOf(((JComboBox<?>) campo(ventana(pantalla), nombre)).getSelectedItem());
    }

    private static DatePicker selector(Class<? extends JFrame> pantalla, String nombre) throws Exception {
        return (DatePicker) campo(ventana(pantalla), nombre);
    }

    private static void fecha(Class<? extends JFrame> pantalla, String nombre, LocalDate valor) throws Exception {
        DatePicker d = selector(pantalla, nombre);
        edt(() -> d.setDate(valor));
    }

    // true si la solicitud muestra la captura; false si muestra la razón de cancelación
    private static boolean tarjetaCaptura() throws Exception {
        return ((Component) campo(ventana(SolicitudRentaView.class), "txtFieldNombreObra")).isShowing();
    }

    private static Object campo(Object o, String nombre) throws Exception {
        Field f = o.getClass().getDeclaredField(nombre);
        f.setAccessible(true);
        return f.get(o);
    }

    private interface Paso {
        void correr() throws Exception;
    }

    private static void edt(Paso paso) throws Exception {
        Exception[] error = new Exception[1];
        SwingUtilities.invokeAndWait(() -> {
            try {
                paso.correr();
            } catch (Exception e) {
                error[0] = e;
            }
        });
        if (error[0] != null) {
            throw error[0];
        }
    }

    // ================================================================ avisos
    private static void contestar(String boton) {
        synchronized (respuestas) {
            respuestas.add(boton);
        }
    }

    private static Aviso ultimoAviso() {
        synchronized (avisos) {
            return avisos.isEmpty() ? new Aviso("", "(no salió ningún aviso)", -1, List.of()) : avisos.get(avisos.size() - 1);
        }
    }

    // Lee cada aviso que aparece, lo guarda y oprime el botón que se le indicó (o el primero)
    private static void iniciarVigiaDeAvisos() {
        Thread vigia = new Thread(() -> {
            Set<Window> vistos = Collections.newSetFromMap(new IdentityHashMap<>());
            while (true) {
                try {
                    Thread.sleep(20);
                    for (Window w : Window.getWindows()) {
                        if (w instanceof JDialog d && d.isShowing() && vistos.add(d)) {
                            atender(d);
                        }
                    }
                } catch (Exception e) {
                    excepciones.add(e);
                }
            }
        });
        vigia.setDaemon(true);
        vigia.start();
    }

    private static void atender(JDialog d) throws Exception {
        JOptionPane op = buscar(d.getContentPane(), JOptionPane.class);
        if (op == null) {
            return;
        }
        List<JButton> botones = new ArrayList<>();
        juntarBotones(d.getContentPane(), botones);
        List<String> nombres = new ArrayList<>();
        for (JButton b : botones) {
            nombres.add(b.getText());
        }
        avisos.add(new Aviso(d.getTitle(), String.valueOf(op.getMessage()), op.getMessageType(), nombres));
        // las preguntas de confirmación solo se capturan si su nombre lo pide
        if (capturaDeAviso != null && (op.getMessageType() != JOptionPane.QUESTION_MESSAGE || capturaDeAviso.contains("confirmacion"))) {
            String nombre = capturaDeAviso;
            capturaDeAviso = null;
            SwingUtilities.invokeAndWait(() -> guardarCaptura(nombre, d));
        }
        String respuesta;
        synchronized (respuestas) {
            respuesta = botones.size() > 1 ? respuestas.poll() : null;
        }
        JButton elegido = botones.get(0);
        for (JButton b : botones) {
            if (b.getText().equals(respuesta)) {
                elegido = b;
            }
        }
        SwingUtilities.invokeLater(elegido::doClick);
    }

    private static void juntarBotones(Container c, List<JButton> botones) {
        for (Component x : c.getComponents()) {
            if (x instanceof JButton b) {
                botones.add(b);
            } else if (x instanceof Container k) {
                juntarBotones(k, botones);
            }
        }
    }

    private static <T> T buscar(Container c, Class<T> tipo) {
        for (Component x : c.getComponents()) {
            if (tipo.isInstance(x)) {
                return tipo.cast(x);
            }
            if (x instanceof Container k) {
                T r = buscar(k, tipo);
                if (r != null) {
                    return r;
                }
            }
        }
        return null;
    }

    // ================================================================ registro
    private static void caso(String id, String criterio, String nombre, String datos, String esperado) {
        actual = new Caso();
        actual.id = id;
        actual.criterio = criterio;
        actual.nombre = nombre;
        actual.datos = datos;
        actual.esperado = esperado;
        casos.put(id, actual);
        excepciones.clear();
        synchronized (respuestas) {
            respuestas.clear();
        }
        System.out.println();
        System.out.println(id + " " + nombre);
    }

    private static void revisa(String que, boolean bien) {
        actual.revisiones.add((bien ? "bien  " : "MAL   ") + que.replace("\n", " "));
        if (bien) {
            totalBien++;
        } else {
            totalMal++;
            actual.mal++;
        }
        System.out.println((bien ? "  bien  " : "  MAL   ") + que.replace("\n", " "));
    }

    private static void sinExcepciones() {
        revisa("sin excepciones durante el caso" + (excepciones.isEmpty() ? "" : ": " + excepciones), excepciones.isEmpty());
    }

    private static void foto(String nombre) throws Exception {
        edt(() -> {
            for (JFrame v : ventanas()) {
                if (v.isShowing()) {
                    guardarCaptura(nombre, null);
                    return;
                }
            }
        });
    }

    // La pantalla a la vista, con el aviso encima si lo hay
    private static void guardarCaptura(String nombre, JDialog aviso) {
        try {
            JFrame vista = null;
            for (JFrame v : ventanas()) {
                if (v.isShowing()) {
                    vista = v;
                }
            }
            Container contenido = vista.getContentPane();
            revisarTextosCortados(contenido, vista.getClass().getSimpleName());
            int barra = 26;
            BufferedImage img = new BufferedImage(contenido.getWidth(), contenido.getHeight() + barra, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            barraDeTitulo(g, vista.getTitle(), 0, 0, img.getWidth(), barra);
            g.translate(0, barra);
            contenido.paint(g);
            g.translate(0, -barra);
            if (aviso != null) {
                Container c = aviso.getContentPane();
                int x = (img.getWidth() - c.getWidth()) / 2;
                int y = (img.getHeight() - c.getHeight()) / 2;
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x - 1, y - barra - 1, c.getWidth() + 1, c.getHeight() + barra + 1);
                barraDeTitulo(g, aviso.getTitle(), x, y - barra, c.getWidth(), barra);
                g.translate(x, y);
                c.paint(g);
                g.translate(-x, -y);
            }
            g.dispose();
            ImageIO.write(img, "png", new File(new File(carpeta, "capturas"), nombre + ".png"));
            if (actual != null) {
                actual.capturas.add(nombre + ".png");
            }
        } catch (Exception e) {
            excepciones.add(e);
        }
    }

    private static void barraDeTitulo(Graphics2D g, String titulo, int x, int y, int ancho, int alto) {
        g.setColor(new Color(236, 236, 236));
        g.fillRect(x, y, ancho, alto);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Dialog", Font.BOLD, 13));
        g.drawString(titulo == null ? "" : titulo, x + 10, y + 18);
    }

    private static void escribirResultados() throws Exception {
        try (PrintWriter w = new PrintWriter(new File(carpeta, "resultados.txt"), "UTF-8")) {
            for (Caso c : casos.values()) {
                w.println(c.id + " | " + c.criterio + " | " + c.nombre + " | " + (c.mal == 0 ? "Aprobado" : "Falló"));
                w.println("  Datos: " + c.datos);
                w.println("  Esperado: " + c.esperado);
                for (String r : c.revisiones) {
                    w.println("  " + r);
                }
                if (!c.capturas.isEmpty()) {
                    w.println("  Capturas: " + String.join(", ", c.capturas));
                }
                w.println();
            }
            w.println(totalBien + " revisiones bien, " + totalMal + " mal");
        }
    }
}
