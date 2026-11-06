package objetosnegocio.bo;

import almacentemporal.AlmacenTemporal;
import java.util.ArrayList;
import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.EquipoDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Los equipos del catálogo, sus accesorios y sus unidades. Por ahora se guardan en el almacén
 * temporal, mientras no haya persistencia real.
 */
public class EquipoBO implements IEquipoBO {

    private static EquipoBO instance;

    private EquipoBO() {
    }

    public static EquipoBO getInstance() {
        if (instance == null) {
            instance = new EquipoBO();
        }
        return instance;
    }

    @Override
    public List<EquipoDTO> consultarCatalogo() {
        return AlmacenTemporal.getInstance().obtenerCatalogoEquipo();
    }

    @Override
    public List<EquipoDTO> buscarEquipos(String texto) {
        return AlmacenTemporal.getInstance().buscarEquipos(texto);
    }

    @Override
    public EquipoDTO consultarEquipo(Long idEquipo) {
        return AlmacenTemporal.getInstance().buscarEquipoPorId(idEquipo);
    }

    @Override
    public List<AccesorioDTO> consultarAccesorios(Long idEquipo) {
        EquipoDTO equipo = consultarEquipo(idEquipo);
        return equipo == null ? new ArrayList<>() : equipo.getAccesorios();
    }

    @Override
    public Double consultarTarifa(Long idEquipo) {
        EquipoDTO equipo = consultarEquipo(idEquipo);
        return equipo == null ? null : equipo.getCostoDiario();
    }

    @Override
    public List<UnidadFisicaDTO> consultarUnidades(Long idEquipo) {
        return AlmacenTemporal.getInstance().obtenerUnidadesDeEquipo(idEquipo);
    }

    @Override
    public UnidadFisicaDTO consultarUnidad(Long idUnidad) {
        return AlmacenTemporal.getInstance().buscarUnidadPorId(idUnidad);
    }

    @Override
    public void actualizarUnidad(UnidadFisicaDTO unidad) {
        AlmacenTemporal.getInstance().actualizarUnidad(unidad);
    }
}
