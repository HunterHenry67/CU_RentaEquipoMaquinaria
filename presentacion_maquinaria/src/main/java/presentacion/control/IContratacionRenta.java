/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package presentacion.control;

import java.util.List;
import objetosdto.ClienteDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DecisionClienteDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;
import objetosdto.UsuarioDTO;

/**
 *
 * @author Home
 */
public interface IContratacionRenta {

    public abstract void abrirIniciarSesion();

    public abstract void abrirMenuPrincipal();

    public abstract void abrirIdentificacionCliente();

    public abstract void abrirBusquedaEquipo();

    public abstract void abrirRegistrarCliente();

    public abstract void abrirSolicitudRenta();

    public abstract void abrirAnalisisRequerimiento();

    public abstract void abrirSolicitudDatosFaltantes();

    public abstract void abrirEquiposCompatibles();

    public abstract void abrirSinEquipoCompatible();

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

    public abstract void pasarDatosDecisionCliente(DecisionClienteDTO decision);

    public abstract void pasarDatosCancelacion(SolicitudRentaDTO solicitud);

    public abstract void usarEquipoAlternativo();

    public abstract void pasarDatosOrdenEntrega(ContratacionRentaDTO contratacion);

    public abstract void abrirMotivoRechazo();


    public abstract void pasarDatosUsuario(UsuarioDTO dto);

    public abstract void pasarDatosSolicitudRenta(ClienteDTO dto);

    public abstract void pasarDatosRegistroCliente(ClienteDTO dto);

    public abstract void pasarDatosAnalisisRequerimientos(SolicitudRentaDTO dto);

    public abstract void pasarDatosRequisitosRenta(CotizacionDTO dto);

    public abstract void pasarDatosPropuestaRenta(List<RequisitoRentaDTO> requisitos);

    public abstract void pasarDatosEquipoSolicitado(EquipoSolicitadoDTO equipo);

    public abstract void pasarDatosSolicitudDatosFaltantes(SolicitudRentaDTO dto);

    public abstract void pasarDatosEquiposCompatibles(SolicitudRentaDTO dto);

    public abstract void pasarDatosSinEquipoCompatible(SolicitudRentaDTO dto);

    public abstract void pasarDatosSeleccionAccesorios(SolicitudRentaDTO dto);

    public abstract void pasarDatosEvaluacionTecnica(SolicitudRentaDTO dto);

    public abstract void pasarDatosVerificacionInventario(SolicitudRentaDTO dto);

    void pasarDatosUnidadSeleccionada(SolicitudRentaDTO dto);

    void abrirUnidadMantenimiento(UnidadFisicaDTO unidad);

    void abrirUnidadOcupada(UnidadFisicaDTO unidad);

    void pasarDatosCambioPeriodo(SolicitudRentaDTO dto);

    public abstract void ocultarTodo();

}
