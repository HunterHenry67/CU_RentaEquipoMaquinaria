package subsistemaContratacionRenta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.OrdenEntregaDTO;
import objetosdto.ResultadoContratacionDTO;
import objetosdto.SolicitudRentaDTO;
import objetosnegocio.bo.ContratacionRentaBO;
import objetosnegocio.bo.IContratacionRentaBO;

/**
 * La decisión del cliente, la contratación que resulta de ella y su orden de entrega.
 */
class GestorContratacion {

    private static final String HORARIO_ENTREGA = "antes de las 8:00 AM";

    private final IContratacionRentaBO contratacionRentaBO = ContratacionRentaBO.getInstance();

    // Con ACEPTA entran todos, con ACEPTA PARCIAL solo los indispensables y en otro caso ninguno
    public List<AccesorioDTO> accesoriosAceptados(String tipoDecision, List<AccesorioDTO> propuestos) {
        List<AccesorioDTO> aceptados = new ArrayList<>();
        if (propuestos != null && ("ACEPTA".equals(tipoDecision) || "ACEPTA PARCIAL".equals(tipoDecision))) {
            for (AccesorioDTO accesorio : propuestos) {
                if ("ACEPTA".equals(tipoDecision) || Boolean.TRUE.equals(accesorio.getEsObligatorio())) {
                    aceptados.add(accesorio);
                }
            }
        }
        return aceptados;
    }

    // Sin el accesorio indispensable la renta queda pendiente; si acepta, faltan por verificar las condiciones
    public String estadoContratacion(String tipoDecision) {
        if ("RECHAZA".equals(tipoDecision)) {
            return "RECHAZADA";
        }
        return "NO ACEPTA INDISPENSABLE".equals(tipoDecision) ? "PENDIENTE POR ACCESORIO" : "POR VERIFICAR";
    }

    public String horarioEntrega() {
        return HORARIO_ENTREGA;
    }

    public ResultadoContratacionDTO crearContratacion(ContratacionRentaDTO contratacion) {
        ContratacionRentaDTO registrada = contratacionRentaBO.guardarContratacion(contratacion);
        boolean formalizada = "FORMALIZADA".equals(registrada.getEstado());
        return new ResultadoContratacionDTO(formalizada, formalizada ? null : "La contratación quedó como " + registrada.getEstado() + ".", registrada);
    }

    public ContratacionRentaDTO obtenerPorFolio(String folio) {
        return contratacionRentaBO.consultarContratacion(folio);
    }

    public OrdenEntregaDTO construirOrden(SolicitudRentaDTO solicitud) {
        OrdenEntregaDTO orden = new OrdenEntregaDTO();
        orden.setFechaGeneracion(LocalDate.now());
        orden.setFechaEntrega(solicitud.getPeriodo().getFechaInicio());
        orden.setDireccionEntrega(solicitud.getDireccionObra());
        orden.setHorario(horarioEntrega());
        orden.setEstadoOrden("Finalizada");
        return contratacionRentaBO.guardarOrdenEntrega(orden);
    }
}
