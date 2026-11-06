package objetosnegocio.bo;

import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.EquipoDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Operaciones de los equipos, sus accesorios y sus unidades que necesita el negocio.
 */
public interface IEquipoBO {

    public abstract List<EquipoDTO> consultarCatalogo();

    public abstract List<EquipoDTO> buscarEquipos(String texto);

    public abstract EquipoDTO consultarEquipo(Long idEquipo);

    public abstract List<AccesorioDTO> consultarAccesorios(Long idEquipo);

    // null si el equipo no tiene tarifa registrada
    public abstract Double consultarTarifa(Long idEquipo);

    public abstract List<UnidadFisicaDTO> consultarUnidades(Long idEquipo);

    public abstract UnidadFisicaDTO consultarUnidad(Long idUnidad);

    public abstract void actualizarUnidad(UnidadFisicaDTO unidad);
}
