package objetosnegocio.bo;

import almacentemporal.AlmacenTemporal;
import java.util.List;
import objetosdto.ClienteDTO;

/**
 * Los clientes. Por ahora se guardan en el almacén temporal, mientras no haya persistencia real.
 */
public class ClienteBO implements IClienteBO {

    private static ClienteBO instance;

    private ClienteBO() {
    }

    public static ClienteBO getInstance() {
        if (instance == null) {
            instance = new ClienteBO();
        }
        return instance;
    }

    @Override
    public List<ClienteDTO> consultarClientes() {
        return AlmacenTemporal.getInstance().obtenerClientes();
    }

    @Override
    public List<ClienteDTO> buscarClientes(String filtro, String texto) {
        return AlmacenTemporal.getInstance().buscarClientes(filtro, texto);
    }

    @Override
    public ClienteDTO consultarPorRfc(String rfc) {
        return AlmacenTemporal.getInstance().buscarClientePorRfc(rfc);
    }

    @Override
    public ClienteDTO guardarCliente(ClienteDTO cliente) {
        return AlmacenTemporal.getInstance().registrarCliente(cliente);
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) {
        AlmacenTemporal.getInstance().actualizarCliente(cliente);
    }
}
