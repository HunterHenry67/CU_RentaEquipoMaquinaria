package subsistemaContratacionRenta;

import java.util.List;
import java.util.Map;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.ResultadoCancelacionDTO;
import objetosdto.ResultadoContratacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Fachada del subsistema: recibe lo que pide el Control de Aplicación y se lo pasa a la clase interna
 * que le corresponde. No guarda información del negocio ni decide nada.
 */
public class SubsistemaContratacionRenta implements ISubsistemaContratacionRenta {

    private final GestorClientes gestorClientes = new GestorClientes();
    private final GestorSolicitud gestorSolicitud = new GestorSolicitud();
    private final GestorDisponibilidad gestorDisponibilidad = new GestorDisponibilidad();
    private final GestorCotizacion gestorCotizacion = new GestorCotizacion();
    private final GestorContratacion gestorContratacion = new GestorContratacion();

    @Override
    public List<ClienteDTO> obtenerClientes() {
        return gestorClientes.listar();
    }

    @Override
    public List<ClienteDTO> buscarClientes(String filtro, String texto) {
        return gestorClientes.buscarPorFiltro(filtro, texto);
    }

    @Override
    public ClienteDTO buscarClientePorRfc(String rfc) {
        return gestorClientes.buscarPorRfc(rfc);
    }

    @Override
    public ClienteDTO registrarClienteNuevo(ClienteDTO cliente) {
        return gestorClientes.registrar(cliente);
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) {
        gestorClientes.actualizar(cliente);
    }

    @Override
    public boolean rfcEnUso(ClienteDTO cliente, ClienteDTO registrado) {
        return gestorClientes.rfcEnUso(cliente, registrado);
    }

    @Override
    public boolean verificarRequisitosRenta(List<RequisitoRentaDTO> requisitos) {
        return gestorClientes.cumpleRequisitos(requisitos);
    }

    @Override
    public String siguienteFolio() {
        return gestorSolicitud.generarFolio();
    }

    @Override
    public SolicitudRentaDTO registrarSolicitud(SolicitudRentaDTO solicitud) {
        return gestorSolicitud.crear(solicitud);
    }

    @Override
    public SolicitudRentaDTO consultarSolicitud(String folio) {
        return gestorSolicitud.obtenerPorFolio(folio);
    }

    @Override
    public void actualizarSolicitud(SolicitudRentaDTO solicitud) {
        gestorSolicitud.actualizar(solicitud);
    }

    @Override
    public ResultadoCancelacionDTO cancelarSolicitudRenta(SolicitudRentaDTO solicitud) {
        return gestorSolicitud.cancelar(solicitud);
    }

    @Override
    public List<RequisitoRentaDTO> obtenerRequisitosRenta() {
        return gestorSolicitud.listarRequisitos();
    }

    @Override
    public int diasRenta(PeriodoSolicitadoDTO periodo) {
        return gestorSolicitud.diasRenta(periodo);
    }

    @Override
    public List<EquipoDTO> obtenerCatalogoEquipo() {
        return gestorDisponibilidad.listarCatalogo();
    }

    @Override
    public List<EquipoDTO> buscarEquipos(String texto) {
        return gestorDisponibilidad.buscarPorNombre(texto);
    }

    @Override
    public EquipoDTO buscarEquipoPorId(Long idEquipo) {
        return gestorDisponibilidad.buscarPorId(idEquipo);
    }

    @Override
    public Map<EquipoDTO, String> buscarEquiposCompatibles(List<RequerimientoTecnicoDTO> requerimientos) {
        return gestorDisponibilidad.buscarCompatibles(requerimientos);
    }

    @Override
    public List<AccesorioDTO> obtenerAccesoriosPropuestos(EquipoDTO equipo, List<AccesorioDTO> elegidos) {
        return gestorDisponibilidad.accesoriosPropuestos(equipo, elegidos);
    }

    @Override
    public UnidadFisicaDTO buscarUnidadPorId(Long idUnidad) {
        return gestorDisponibilidad.buscarUnidad(idUnidad);
    }

    @Override
    public List<UnidadFisicaDTO> verificarDisponibilidadUnidades(EquipoDTO equipo, PeriodoSolicitadoDTO periodo) {
        return gestorDisponibilidad.unidadesLibres(equipo, periodo);
    }

    @Override
    public boolean estaDisponible(UnidadFisicaDTO unidad) {
        return gestorDisponibilidad.estaDisponible(unidad);
    }

    @Override
    public UnidadFisicaDTO ocuparUnidad(UnidadFisicaDTO unidad, PeriodoSolicitadoDTO periodo) {
        return gestorDisponibilidad.ocupar(unidad, periodo);
    }

    @Override
    public double tarifaDiaria(EquipoDTO equipo) {
        return gestorCotizacion.tarifaDiaria(equipo);
    }

    @Override
    public CotizacionDTO generarCotizacion(String folio, EquipoDTO equipo, int dias, List<AccesorioDTO> accesorios) {
        return gestorCotizacion.calcularTotal(folio, equipo, dias, accesorios);
    }

    @Override
    public Double depositoGarantia(List<RequisitoRentaDTO> requisitos) {
        return gestorCotizacion.depositoGarantia(requisitos);
    }

    @Override
    public CotizacionDTO registrarCotizacion(CotizacionDTO cotizacion) {
        return gestorCotizacion.guardar(cotizacion);
    }

    @Override
    public CotizacionDTO consultarCotizacion(String folio) {
        return gestorCotizacion.obtenerPorFolio(folio);
    }

    @Override
    public void actualizarCotizacion(CotizacionDTO cotizacion) {
        gestorCotizacion.actualizar(cotizacion);
    }

    @Override
    public List<AccesorioDTO> accesoriosAceptados(String tipoDecision, List<AccesorioDTO> propuestos) {
        return gestorContratacion.accesoriosAceptados(tipoDecision, propuestos);
    }

    @Override
    public String estadoContratacion(String tipoDecision) {
        return gestorContratacion.estadoContratacion(tipoDecision);
    }

    @Override
    public String horarioEntrega() {
        return gestorContratacion.horarioEntrega();
    }

    @Override
    public ResultadoContratacionDTO registrarContratacion(ContratacionRentaDTO contratacion) {
        return gestorContratacion.crearContratacion(contratacion);
    }

    @Override
    public ContratacionRentaDTO consultarContratacion(String folio) {
        return gestorContratacion.obtenerPorFolio(folio);
    }

    @Override
    public OrdenEntregaDTO generarOrdenEntrega(SolicitudRentaDTO solicitud) {
        return gestorContratacion.construirOrden(solicitud);
    }
}
