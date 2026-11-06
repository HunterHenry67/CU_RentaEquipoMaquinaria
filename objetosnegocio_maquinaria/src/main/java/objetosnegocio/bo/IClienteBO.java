package objetosnegocio.bo;

import java.util.List;
import objetosdto.ClienteDTO;

/**
 * Operaciones de los clientes que necesita el negocio.
 */
public interface IClienteBO {

    public abstract List<ClienteDTO> consultarClientes();

    // filtro: "Nombre", "Teléfono" o "RFC"
    public abstract List<ClienteDTO> buscarClientes(String filtro, String texto);

    public abstract ClienteDTO consultarPorRfc(String rfc);

    public abstract ClienteDTO guardarCliente(ClienteDTO cliente);

    public abstract void actualizarCliente(ClienteDTO cliente);
}
