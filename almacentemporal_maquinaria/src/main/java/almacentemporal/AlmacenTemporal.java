/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package almacentemporal;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import objetosdto.AccesorioDTO;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import objetosdto.ContratacionRentaDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.OcupacionUnidadDTO;
import objetosdto.RequisitoRentaDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UnidadFisicaDTO;

/**
 * Los datos se cargan una vez con la información de prueba del escenario y se quedan mientras
 * la aplicación está abierta. Lo que se regresa son copias: quien quiera cambiar algo lo tiene
 * que registrar o actualizar aquí.
 *
 * @author BALAMRUSH
 */
public class AlmacenTemporal implements IAlmacenTemporal {

    private final List<ClienteDTO> clientes = new ArrayList<>();
    private final List<EquipoDTO> catalogoEquipos = new ArrayList<>();
    private final List<UnidadFisicaDTO> unidades = new ArrayList<>();
    private final List<Long> equipoDeUnidad = new ArrayList<>();
    private final List<RequisitoRentaDTO> requisitos = new ArrayList<>();
    private final List<SolicitudRentaDTO> solicitudes = new ArrayList<>();
    private final List<CotizacionDTO> cotizaciones = new ArrayList<>();
    private final List<ContratacionRentaDTO> contrataciones = new ArrayList<>();
    private int siguienteNumeroFolio = 89241;

    public AlmacenTemporal() {
        cargarClientes();
        cargarCatalogo();
        cargarUnidades();
        requisitos.add(new RequisitoRentaDTO("Identificación oficial", true, "Cumplida", null));
        requisitos.add(new RequisitoRentaDTO("Depósito de garantía", true, "Cubierto", 3200.0));
    }

    // ---------------------------------------------------------------- datos de prueba
    private void cargarClientes() {
        clientes.add(new ClienteDTO(1L, "Julián Martínez García", "MARJ8503158A1", "6449876543", "julianmar12@gmail.com", "Particular"));
        ClienteEmpresaDTO empresa = new ClienteEmpresaDTO();
        empresa.setIdCliente(4L);
        empresa.setNombre("Constructora del Norte S.A. de C.V.");
        empresa.setRfc("CNO081015H99");
        empresa.setTelefono("6445551234");
        empresa.setCorreo("contacto@constructoranorte.com");
        empresa.setTipoCliente("Empresa");
        empresa.setNombreEncargadoEmpresa("Ing. Carlos Alberto Sánchez");
        empresa.setPuestoEncargadoEmpresa("Gerente de Operaciones");
        empresa.setTelefonoEncargadoEmpresa("6445559876");
        clientes.add(empresa);
        clientes.add(new ClienteDTO(3L, "María Fernanda López", "LOMF920410H12", "6447654321", "mafe@correo.com", "Particular"));
    }

    private void cargarCatalogo() {
        EquipoDTO jcb = equipo(1L, "JCB", "Retroexcavadora JCB");
        jcb.setPotencia(109.0);
        EquipoDTO cat320 = equipo(2L, "CAT", "Excavadora CAT 320");
        cat320.setCostoDiario(1500.0);
        cat320.setAccesorios(accesoriosExcavadora());
        EquipoDTO cat336 = equipo(3L, "CAT", "Excavadora CAT 336");
        cat336.setAccesorios(accesoriosExcavadora());
        catalogoEquipos.add(jcb);
        catalogoEquipos.add(cat320);
        catalogoEquipos.add(cat336);
    }

    // Tres unidades por equipo, como en el escenario: la 1 libre, la 2 en mantenimiento y la 3 rentada del 13 al 15
    private void cargarUnidades() {
        String[][] numeros = {{"J-001", "J-002", "J-003"}, {"M-001", "M-002", "M-003"}, {"N-001", "N-002", "N-003"}};
        long id = 1;
        for (int e = 0; e < numeros.length; e++) {
            Long idEquipo = (long) (e + 1);
            agregarUnidad(id++, idEquipo, numeros[e][0], "Disponible", new ArrayList<>());
            List<OcupacionUnidadDTO> mantenimiento = new ArrayList<>();
            mantenimiento.add(new OcupacionUnidadDTO(LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 15), "Mantenimiento preventivo", "En curso"));
            agregarUnidad(id++, idEquipo, numeros[e][1], "En mantenimiento", mantenimiento);
            List<OcupacionUnidadDTO> renta = new ArrayList<>();
            renta.add(new OcupacionUnidadDTO(LocalDate.of(2026, 9, 13), LocalDate.of(2026, 9, 15), "Renta", "Confirmada"));
            agregarUnidad(id++, idEquipo, numeros[e][2], "Ocupada", renta);
        }
    }

    private void agregarUnidad(Long id, Long idEquipo, String numero, String estado, List<OcupacionUnidadDTO> ocupaciones) {
        unidades.add(new UnidadFisicaDTO(id, numero, estado, null, null, ocupaciones));
        equipoDeUnidad.add(idEquipo);
    }

    private List<AccesorioDTO> accesoriosExcavadora() {
        List<AccesorioDTO> accesorios = new ArrayList<>();
        accesorios.add(new AccesorioDTO(1L, "Kit de señalización", null, 300.0, true));
        accesorios.add(new AccesorioDTO(2L, "Cucharón adicional 1.20 m", null, 200.0, false));
        return accesorios;
    }

    private EquipoDTO equipo(Long id, String marca, String modelo) {
        EquipoDTO equipo = new EquipoDTO();
        equipo.setIdEquipo(id);
        equipo.setMarca(marca);
        equipo.setModelo(modelo);
        equipo.setAccesorios(new ArrayList<>());
        return equipo;
    }

    // ---------------------------------------------------------------- clientes
    @Override
    public List<ClienteDTO> obtenerClientes() {
        List<ClienteDTO> lista = new ArrayList<>();
        for (ClienteDTO c : clientes) {
            lista.add(copia(c));
        }
        return lista;
    }

    // filtro: "Nombre", "Teléfono" o "RFC" (como el combo de Identificación de cliente)
    @Override
    public List<ClienteDTO> buscarClientes(String filtro, String texto) {
        String buscado = sinAcentos(texto);
        List<ClienteDTO> encontrados = new ArrayList<>();
        for (ClienteDTO c : clientes) {
            String dato = "Nombre".equals(filtro) ? c.getNombre() : "Teléfono".equals(filtro) ? c.getTelefono() : c.getRfc();
            if (sinAcentos(dato).contains(buscado)) {
                encontrados.add(copia(c));
            }
        }
        return encontrados;
    }

    @Override
    public ClienteDTO buscarClientePorId(Long idCliente) {
        for (ClienteDTO c : clientes) {
            if (Objects.equals(c.getIdCliente(), idCliente)) {
                return copia(c);
            }
        }
        return null;
    }

    @Override
    public ClienteDTO buscarClientePorRfc(String rfc) {
        for (ClienteDTO c : clientes) {
            if (c.getRfc() != null && c.getRfc().equalsIgnoreCase(rfc == null ? "" : rfc.trim())) {
                return copia(c);
            }
        }
        return null;
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) {
        long mayor = 0;
        for (ClienteDTO c : clientes) {
            mayor = Math.max(mayor, c.getIdCliente() == null ? 0 : c.getIdCliente());
        }
        ClienteDTO nuevo = copia(cliente);
        nuevo.setIdCliente(mayor + 1);
        clientes.add(nuevo);
        return copia(nuevo);
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (Objects.equals(clientes.get(i).getIdCliente(), cliente.getIdCliente())) {
                clientes.set(i, copia(cliente));
                return;
            }
        }
    }

    // ---------------------------------------------------------------- equipos y unidades
    @Override
    public List<EquipoDTO> obtenerCatalogoEquipo() {
        List<EquipoDTO> lista = new ArrayList<>();
        for (EquipoDTO e : catalogoEquipos) {
            lista.add(copia(e));
        }
        return lista;
    }

    @Override
    public List<EquipoDTO> buscarEquipos(String texto) {
        String buscado = sinAcentos(texto);
        List<EquipoDTO> encontrados = new ArrayList<>();
        for (EquipoDTO e : catalogoEquipos) {
            if (sinAcentos(e.getMarca() + " " + e.getModelo()).contains(buscado)) {
                encontrados.add(copia(e));
            }
        }
        return encontrados;
    }

    @Override
    public EquipoDTO buscarEquipoPorId(Long idEquipo) {
        for (EquipoDTO e : catalogoEquipos) {
            if (Objects.equals(e.getIdEquipo(), idEquipo)) {
                return copia(e);
            }
        }
        return null;
    }

    @Override
    public List<UnidadFisicaDTO> obtenerUnidadesDeEquipo(Long idEquipo) {
        List<UnidadFisicaDTO> lista = new ArrayList<>();
        for (int i = 0; i < unidades.size(); i++) {
            if (Objects.equals(equipoDeUnidad.get(i), idEquipo)) {
                lista.add(copia(unidades.get(i)));
            }
        }
        return lista;
    }

    // ---------------------------------------------------------------- solicitudes
    @Override
    public String siguienteFolio() {
        return "FOL-" + siguienteNumeroFolio;
    }

    @Override
    public SolicitudRentaDTO registrarSolicitud(SolicitudRentaDTO solicitud) {
        if (solicitud.getFolio() == null || buscarSolicitudPorFolio(solicitud.getFolio()) != null) {
            solicitud.setFolio(siguienteFolio());
        }
        if (solicitud.getFolio().equals(siguienteFolio())) {
            siguienteNumeroFolio++;
        }
        solicitud.setIdSolicitud((long) solicitudes.size() + 1);
        solicitudes.add(solicitud);
        return solicitud;
    }

    @Override
    public void actualizarSolicitud(SolicitudRentaDTO solicitud) {
        for (int i = 0; i < solicitudes.size(); i++) {
            if (Objects.equals(solicitudes.get(i).getFolio(), solicitud.getFolio())) {
                solicitudes.set(i, solicitud);
                return;
            }
        }
    }

    @Override
    public SolicitudRentaDTO buscarSolicitudPorFolio(String folio) {
        for (SolicitudRentaDTO s : solicitudes) {
            if (Objects.equals(s.getFolio(), folio)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<SolicitudRentaDTO> obtenerSolicitudes() {
        return new ArrayList<>(solicitudes);
    }

    // ---------------------------------------------------------------- cotizaciones y contrataciones
    @Override
    public CotizacionDTO registrarCotizacion(CotizacionDTO cotizacion) {
        cotizacion.setIdCotizacion((long) cotizaciones.size() + 1);
        cotizaciones.add(cotizacion);
        return cotizacion;
    }

    @Override
    public List<CotizacionDTO> obtenerCotizaciones() {
        return new ArrayList<>(cotizaciones);
    }

    @Override
    public ContratacionRentaDTO registrarContratacion(ContratacionRentaDTO contratacion) {
        contratacion.setIdContratacion((long) contrataciones.size() + 1);
        if (contratacion.getOrdenEntrega() != null) {
            contratacion.getOrdenEntrega().setIdOrdenEntrega(contratacion.getIdContratacion());
        }
        contrataciones.add(contratacion);
        return contratacion;
    }

    @Override
    public List<ContratacionRentaDTO> obtenerContrataciones() {
        return new ArrayList<>(contrataciones);
    }

    // ---------------------------------------------------------------- requisitos y ejemplo
    @Override
    public List<RequisitoRentaDTO> obtenerRequisitosRenta() {
        List<RequisitoRentaDTO> lista = new ArrayList<>();
        for (RequisitoRentaDTO r : requisitos) {
            lista.add(new RequisitoRentaDTO(r.getDescripcion(), r.getEsObligatorio(), r.getEstadoRequisito(), r.getMonto()));
        }
        return lista;
    }

    @Override
    public List<DetalleCotizacionDTO> obtenerDetallesCotizacionEjemplo() {
        List<DetalleCotizacionDTO> detalles = new ArrayList<>();
        detalles.add(new DetalleCotizacionDTO("Equipo", "Excavadora CAT 320 (M-001)", 6, 1500.0, 9000.0, null, null));
        detalles.add(new DetalleCotizacionDTO("Accesorio", "kit", 1, 300.0, 300.0, null, null));
        detalles.add(new DetalleCotizacionDTO("Accesorio", "cucharón", 1, 200.0, 200.0, null, null));
        return detalles;
    }

    // ---------------------------------------------------------------- copias
    private ClienteDTO copia(ClienteDTO c) {
        ClienteDTO nuevo;
        if (c instanceof ClienteEmpresaDTO e) {
            ClienteEmpresaDTO empresa = new ClienteEmpresaDTO();
            empresa.setNombreEncargadoEmpresa(e.getNombreEncargadoEmpresa());
            empresa.setPuestoEncargadoEmpresa(e.getPuestoEncargadoEmpresa());
            empresa.setTelefonoEncargadoEmpresa(e.getTelefonoEncargadoEmpresa());
            empresa.setArchivoIdentificacionEncargado(e.getArchivoIdentificacionEncargado());
            nuevo = empresa;
        } else {
            nuevo = new ClienteDTO();
        }
        nuevo.setIdCliente(c.getIdCliente());
        nuevo.setNombre(c.getNombre());
        nuevo.setRfc(c.getRfc());
        nuevo.setTelefono(c.getTelefono());
        nuevo.setCorreo(c.getCorreo());
        nuevo.setTipoCliente(c.getTipoCliente());
        return nuevo;
    }

    private EquipoDTO copia(EquipoDTO e) {
        List<AccesorioDTO> accesorios = new ArrayList<>();
        if (e.getAccesorios() != null) {
            for (AccesorioDTO a : e.getAccesorios()) {
                accesorios.add(new AccesorioDTO(a.getIdAccesorio(), a.getNombre(), a.getTipo(), a.getCostoRenta(), a.getEsObligatorio()));
            }
        }
        return new EquipoDTO(e.getIdEquipo(), e.getMarca(), e.getModelo(), e.getPotencia(), e.getPeso(),
                e.getAnchoOperacion(), e.getProfundidadExcavacion(), e.getCostoDiario(), accesorios);
    }

    private UnidadFisicaDTO copia(UnidadFisicaDTO u) {
        List<OcupacionUnidadDTO> ocupaciones = new ArrayList<>();
        for (OcupacionUnidadDTO o : u.getOcupaciones()) {
            ocupaciones.add(new OcupacionUnidadDTO(o.getFechaInicio(), o.getFechaFin(), o.getMotivoOcupacion(), o.getEstadoOcupacion()));
        }
        return new UnidadFisicaDTO(u.getIdUnidad(), u.getNumeroEconomico(), u.getEstadoUnidad(), u.getUbicacion(), u.getHorasUso(), ocupaciones);
    }

    private String sinAcentos(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }
}
