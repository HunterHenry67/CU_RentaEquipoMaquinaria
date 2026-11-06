package subsistemaContratacionRenta;

import java.util.List;
import java.util.Objects;
import objetosdto.ClienteDTO;
import objetosdto.RequisitoRentaDTO;
import objetosnegocio.bo.ClienteBO;
import objetosnegocio.bo.IClienteBO;

/**
 * Los clientes y si cumplen con los requisitos de la renta.
 */
class GestorClientes {

    private final IClienteBO clienteBO = ClienteBO.getInstance();

    public List<ClienteDTO> listar() {
        return clienteBO.consultarClientes();
    }

    public List<ClienteDTO> buscarPorFiltro(String filtro, String texto) {
        return clienteBO.buscarClientes(filtro, texto);
    }

    public ClienteDTO buscarPorRfc(String rfc) {
        return clienteBO.consultarPorRfc(rfc);
    }

    public ClienteDTO registrar(ClienteDTO cliente) {
        if (clienteBO.consultarPorRfc(cliente.getRfc()) != null) {
            throw new IllegalArgumentException("Ya hay un cliente registrado con el RFC " + cliente.getRfc() + ".");
        }
        return clienteBO.guardarCliente(cliente);
    }

    public void actualizar(ClienteDTO cliente) {
        clienteBO.actualizarCliente(cliente);
    }

    public boolean rfcEnUso(ClienteDTO cliente, ClienteDTO registrado) {
        return registrado != null && !Objects.equals(registrado.getIdCliente(), cliente.getIdCliente());
    }

    // Con un requisito obligatorio pendiente la renta no puede continuar
    public boolean cumpleRequisitos(List<RequisitoRentaDTO> requisitos) {
        if (requisitos != null) {
            for (RequisitoRentaDTO requisito : requisitos) {
                if (Boolean.TRUE.equals(requisito.getEsObligatorio()) && "Pendiente".equals(requisito.getEstadoRequisito())) {
                    return false;
                }
            }
        }
        return true;
    }
}
