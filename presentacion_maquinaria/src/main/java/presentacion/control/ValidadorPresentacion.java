package presentacion.control;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;
import objetosdto.PeriodoSolicitadoDTO;
import objetosdto.RequerimientoTecnicoDTO;
import objetosdto.SolicitudRentaDTO;
import objetosdto.UsuarioDTO;

/**
 * Validaciones propias de la presentación: que lo capturado tenga un formato correcto.
 * Las usa el Control de Aplicación; no decide nada del negocio (si un cliente puede rentar,
 * si una unidad está libre, etc.).
 */
public class ValidadorPresentacion {

    private static final String CORREO = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$";
    private static final String RFC = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$";
    private static final String NUMERO = "^\\d+(\\.\\d+|,\\d{1,2})?$";
    public static final String PROFUNDIDAD = "Profundidad de trabajo";
    public static final String POTENCIA = "Potencia mínima";
    public static final String ALIMENTACION = "Tipo de alimentación";
    public static final String PESO = "Peso máximo permitido";
    public static final String TERRENO = "Tipo de terreno";
    public static final String ANCHO = "Ancho de acceso";
    public static final String MINIMO = "Mínimo";
    public static final String MAXIMO = "Máximo";
    private static final Pattern NUMEROPATRON = Pattern.compile("\\d+(?:[.,]\\d+)?");
    
    private ValidadorPresentacion() {
    }

    public static boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    // Unidad de cada característica con número
    public static String unidad(String caracteristica) {
        switch (caracteristica) {
            case PROFUNDIDAD:
            case ANCHO:
                return "m";
            case POTENCIA:
                return "HP";
            case PESO:
                return "t";
            default:
                return null;
        }
    }

    // Recibe pares de nombre y valor y regresa los nombres de los que vienen vacíos ("Nombre, RFC")
    public static String camposVacios(Object... nombreYValor) {
        StringBuilder faltan = new StringBuilder();
        for (int i = 0; i < nombreYValor.length; i += 2) {
            Object valor = nombreYValor[i + 1];
            if (valor == null || (valor instanceof String texto && textoVacio(texto))) {
                faltan.append(faltan.length() > 0 ? ", " : "").append(nombreYValor[i]);
            }
        }
        return faltan.toString();
    }

    public static String faltantesUsuario(UsuarioDTO usuario) {
        return camposVacios("Correo", usuario.getCorreo(), "Contraseña", usuario.getContrasenia());
    }

    public static String faltantesCliente(ClienteDTO cliente) {
        return camposVacios("Nombre", cliente.getNombre(), "Correo electrónico", cliente.getCorreo(),
                "Teléfono", cliente.getTelefono(), "RFC", cliente.getRfc());
    }

    public static String faltantesEncargado(ClienteEmpresaDTO empresa) {
        return camposVacios("Nombre del encargado", empresa.getNombreEncargadoEmpresa(),
                "Teléfono del encargado", empresa.getTelefonoEncargadoEmpresa(), "Puesto del encargado", empresa.getPuestoEncargadoEmpresa());
    }

    public static String faltantesSolicitud(SolicitudRentaDTO solicitud) {
        PeriodoSolicitadoDTO periodo = solicitud.getPeriodo();
        return camposVacios("Fecha de inicio", periodo == null ? null : periodo.getFechaInicio(),
                "Fecha final", periodo == null ? null : periodo.getFechaFin(),
                "Nombre de la obra", solicitud.getNombreObra(), "Dirección", solicitud.getDireccionObra(),
                "Actividad que va a realizar el cliente", solicitud.getDescripcionActividad());
    }

    public static boolean correoValido(String correo) {
        return correo != null && correo.trim().matches(CORREO);
    }

    public static boolean tieneLongitudMinima(String texto, int minimo) {
        return texto != null && texto.length() >= minimo;
    }

    public static boolean numeroEntero(String texto) {
        return texto != null && texto.trim().matches("^\\d+$");
    }

    public static boolean numeroValido(String texto) {
        return texto != null && texto.trim().matches(NUMERO);
    }

    public static boolean esNumeroConUnidad(String texto, String unidad) {
        if (texto == null) {
            return false;
        }
        String limpio = texto.trim();
        if (unidad != null && limpio.toLowerCase().endsWith(unidad.toLowerCase())) {
            limpio = limpio.substring(0, limpio.length() - unidad.length()).trim();
        }
        return numeroValido(limpio);
    }

    public static boolean telefonoValido(String telefono) {
        return telefono != null && telefono.trim().matches("^\\d{10}$");
    }

    public static boolean rfcValido(String rfc) {
        return rfc != null && rfc.trim().toUpperCase().matches(RFC);
    }

    public static boolean fechaValida(LocalDate fecha) {
        return fecha != null;
    }

    // Las dos fechas capturadas y la final no antes de la inicial
    public static boolean esPeriodoValido(LocalDate inicio, LocalDate fin) {
        return fechaValida(inicio) && fechaValida(fin) && !fin.isBefore(inicio);
    }

    public static boolean seleccionValida(Object seleccion) {
        return seleccion != null;
    }
    
    private static String limite(String caracteristica) {
        return POTENCIA.equals(caracteristica) || PROFUNDIDAD.equals(caracteristica) ? MINIMO : MAXIMO;
    }

    private static boolean esDeTexto(String caracteristica) {
        return unidad(caracteristica) == null;
    }

    private static RequerimientoTecnicoDTO buscar(List<RequerimientoTecnicoDTO> lista, String caracteristica) {
        if (lista == null) {
            return null;
        }
        for (RequerimientoTecnicoDTO requerimiento : lista) {
            String nombre = requerimiento.getCaracteristica();
            if (nombre != null && (nombre.equals(caracteristica) || nombre.startsWith(caracteristica + ": "))) {
                return requerimiento;
            }
        }
        return null;
    }

   
    public static String texto(List<RequerimientoTecnicoDTO> lista, String caracteristica) {
        RequerimientoTecnicoDTO requerimiento = buscar(lista, caracteristica);
        if (requerimiento == null) {
            return "";
        }
        if (esDeTexto(caracteristica)) {
            String nombre = requerimiento.getCaracteristica();
            return nombre.length() > caracteristica.length() + 2 ? nombre.substring(caracteristica.length() + 2) : "";
        }
        return requerimiento.getValorRequerido() == null ? ""
                : formatoNumero(requerimiento.getValorRequerido()) + " " + requerimiento.getUnidadMedida();
    }

    /** El valor con número de esa característica, o null si no se ha capturado. */
    public static Double valor(List<RequerimientoTecnicoDTO> lista, String caracteristica) {
        RequerimientoTecnicoDTO requerimiento = buscar(lista, caracteristica);
        return requerimiento == null ? null : requerimiento.getValorRequerido();
    }

    /**
     * Lo que escribió el asesor en el campo de esa característica, tal cual ("Potencia mínima: 120").
     * Lo usan las pantallas; el control lo revisa y lo convierte con normalizar().
     */
    public static void capturar(List<RequerimientoTecnicoDTO> lista, String caracteristica, String textoCampo) {
        RequerimientoTecnicoDTO requerimiento = buscar(lista, caracteristica);
        String texto = textoCampo == null ? "" : textoCampo.trim();
        if (texto.isEmpty()) {
            if (requerimiento != null) {
                lista.remove(requerimiento);
            }
            return;
        }
        if (requerimiento == null) {
            requerimiento = new RequerimientoTecnicoDTO();
            lista.add(requerimiento);
        }
        requerimiento.setCaracteristica(caracteristica + ": " + texto);
        requerimiento.setValorRequerido(null);
        requerimiento.setUnidadMedida(null);
        requerimiento.setTipoLimite(null);
    }

    // Otra lista con los mismos datos: la pantalla captura en ella sin tocar la que tiene el control
    public static List<RequerimientoTecnicoDTO> copia(List<RequerimientoTecnicoDTO> lista) {
        List<RequerimientoTecnicoDTO> copia = new java.util.ArrayList<>();
        if (lista != null) {
            for (RequerimientoTecnicoDTO r : lista) {
                copia.add(new RequerimientoTecnicoDTO(r.getCaracteristica(), r.getValorRequerido(), r.getUnidadMedida(), r.getTipoLimite()));
            }
        }
        return copia;
    }

    // Deja cada característica con su valor y regresa la primera que no trae un número (null si todas están bien)
    public static String normalizar(List<RequerimientoTecnicoDTO> lista) {
        String[] conNumero = {PROFUNDIDAD, POTENCIA, PESO, ANCHO};
        for (String caracteristica : conNumero) {
            String texto = capturado(lista, caracteristica);
            if (texto != null && !ValidadorPresentacion.esNumeroConUnidad(texto, unidad(caracteristica))) {
                return caracteristica;
            }
        }
        for (String caracteristica : conNumero) {
            String texto = capturado(lista, caracteristica);
            if (texto != null) {
                guardar(lista, caracteristica, texto);
            }
        }
        return null;
    }

    private static String capturado(List<RequerimientoTecnicoDTO> lista, String caracteristica) {
        RequerimientoTecnicoDTO requerimiento = buscar(lista, caracteristica);
        if (requerimiento == null || requerimiento.getValorRequerido() != null) {
            return null;
        }
        String nombre = requerimiento.getCaracteristica();
        return nombre.length() > caracteristica.length() + 2 ? nombre.substring(caracteristica.length() + 2) : "";
    }

    // Lo deja ya convertido dentro de la lista: lo agrega, lo cambia o lo quita si el campo quedó vacío
    private static void guardar(List<RequerimientoTecnicoDTO> lista, String caracteristica, String textoCampo) {
        RequerimientoTecnicoDTO requerimiento = buscar(lista, caracteristica);
        String texto = textoCampo == null ? "" : textoCampo.trim();
        if (texto.isEmpty()) {
            if (requerimiento != null) {
                lista.remove(requerimiento);
            }
            return;
        }
        if (requerimiento == null) {
            requerimiento = new RequerimientoTecnicoDTO();
            lista.add(requerimiento);
        }
        if (esDeTexto(caracteristica)) {
            requerimiento.setCaracteristica(caracteristica + ": " + texto);
            requerimiento.setValorRequerido(null);
            requerimiento.setUnidadMedida(null);
            requerimiento.setTipoLimite(null);
        } else {
            Matcher numero = NUMEROPATRON.matcher(texto);
            numero.find();
            requerimiento.setCaracteristica(caracteristica);
            requerimiento.setValorRequerido(Double.valueOf(numero.group().replace(',', '.')));
            requerimiento.setUnidadMedida(unidad(caracteristica));
            requerimiento.setTipoLimite(limite(caracteristica));
        }
    }

    public static String formatoNumero(Double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf(valor.longValue());
        }
        BigDecimal numero = BigDecimal.valueOf(valor).stripTrailingZeros();
        return numero.scale() < 2 ? numero.setScale(2).toPlainString() : numero.toPlainString();
    }
}
