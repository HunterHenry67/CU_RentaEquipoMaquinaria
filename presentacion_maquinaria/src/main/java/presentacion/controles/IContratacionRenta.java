package presentacion.controles;

import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DecisionClienteDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.EvaluacionTecnicaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import objetosdto.UsuarioDTO;

/**
 * Lo que las pantallas le piden al Control de Aplicación. Las pantallas solo conocen esta
 * interfaz: no se abren entre sí ni tocan el almacén temporal.
 *
 * @author Home
 */
public interface IContratacionRenta {

    // Cambios de pantalla sin datos nuevos (regresar, cancelar, continuar)
    public abstract void abrirIniciarSesion();

    public abstract void abrirMenuPrincipal();

    public abstract void abrirIdentificacionCliente();

    public abstract void abrirBusquedaEquipo();

    public abstract void abrirSolicitudRenta();

    public abstract void abrirAnalisisRequerimiento();

    public abstract void abrirEquiposCompatibles();

    public abstract void abrirSeleccionAccesorios();

    public abstract void abrirEvaluacionTecnica();

    public abstract void abrirCambiarPeriodo();

    public abstract void abrirVerificacionInventario();

    public abstract void abrirCalendarioUnidad();

    public abstract void abrirCondicionesContratacion();

    public abstract void abrirCotizacion();

    public abstract void abrirRequisitosRenta();

    public abstract void abrirPropuestaRenta();

    public abstract void abrirPropuestaPendiente();

    public abstract void abrirMotivoRechazo();

    // Confirmaciones: la pantalla pide la acción y el control pregunta antes de seguir
    public abstract void abrirCancelacionSolicitud();

    public abstract void dejarOperacion();

    public abstract void regresarAIdentificacionCliente();

    // Inicio de sesión y clientes
    public abstract void pasarDatosUsuario(UsuarioDTO usuario);

    public abstract void buscarClientes(String filtro, String texto);

    public abstract void pasarDatosSolicitudRenta(ClienteDTO cliente);

    public abstract void pasarDatosRegistroCliente(ClienteDTO cliente);

    public abstract void modificarCliente(ClienteDTO cliente);

    public abstract void registrarCliente(ClienteDTO cliente);
    
    public abstract void cambiarEstadoRequisitos(String estado);

    // Solicitud
    public abstract void buscarEquipos(String texto);

    public abstract void pasarDatosEquipoSolicitado(EquipoSolicitadoDTO equipo);

    public abstract void quitarEquipoSolicitado(EquipoSolicitadoDTO equipo);

    public abstract void registrarSolicitud(SolicitudRentaDTO solicitud);

    public abstract void pasarDatosCancelacion(SolicitudRentaDTO solicitud);

    // Equipo adecuado
    public abstract void pasarDatosAnalisisRequerimientos(List<RequerimientoTecnicoDTO> requerimientos);

    public abstract void pasarDatosSolicitudDatosFaltantes(List<RequerimientoTecnicoDTO> requerimientos);

    public abstract void regresarDeAnalisis(List<RequerimientoTecnicoDTO> requerimientos);

    public abstract void pasarDatosEquiposCompatibles(List<RequerimientoTecnicoDTO> requerimientos);

    public abstract void ningunEquipoCumple();

    public abstract void cerrarEvaluacion(EvaluacionTecnicaDTO capturada);

    public abstract void pasarDatosSeleccionAccesorios(EvaluacionTecnicaDTO seleccion);

    public abstract void pasarDatosEvaluacionTecnica(List<AccesorioDTO> opcionalesElegidos);

    // Disponibilidad
    public abstract void pasarDatosVerificacionInventario(SolicitudRentaDTO solicitud);

    public abstract void pasarDatosUnidadSeleccionada(UnidadFisicaDTO unidad);

    public abstract void verMotivoUnidad(UnidadFisicaDTO unidad);

    public abstract void usarEquipoAlternativo();

    public abstract void pasarDatosCambioPeriodo(PeriodoSolicitadoDTO periodo);

    // Cotización, requisitos y decisión del cliente
    public abstract void pasarDatosRequisitosRenta(CotizacionDTO cotizacion);

    public abstract void pasarDatosPropuestaRenta(List<RequisitoRentaDTO> requisitos);

    public abstract void pasarDatosDecisionCliente(DecisionClienteDTO decision);

    public abstract void pasarDatosOrdenEntrega(ContratacionRentaDTO revisada);

    public abstract void pasarDatosSolicitudPendiente(ContratacionRentaDTO contratacion);

    public abstract void consultarOrdenPorFolio(String folio);
}
