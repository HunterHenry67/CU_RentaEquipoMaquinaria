/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.control;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import objetosdto.RequerimientoTecnicoDTO;

/**
 * Los requerimientos técnicos de la solicitud (una lista de RequerimientoTecnicoDTO): las
 * pantallas los muestran y capturan lo escrito; el Control de Aplicación lo revisa y lo convierte.
 *
 * Las características con número guardan el valor, la unidad y si es un mínimo o un
 * máximo. Las de texto (tipo de alimentación y tipo de terreno) no tienen número, así
 * que su valor va en la misma característica: "Tipo de terreno: Arcilloso".
 *
 * @author alejandromontijo
 */
public class RequerimientosTecnicos {

    public static final String PROFUNDIDAD = "Profundidad de trabajo";
    public static final String POTENCIA = "Potencia mínima";
    public static final String ALIMENTACION = "Tipo de alimentación";
    public static final String PESO = "Peso máximo permitido";
    public static final String TERRENO = "Tipo de terreno";
    public static final String ANCHO = "Ancho de acceso";

    public static final String MINIMO = "Mínimo";
    public static final String MAXIMO = "Máximo";

    private static final Pattern NUMERO = Pattern.compile("\\d+(?:[.,]\\d+)?");

    private RequerimientosTecnicos() {
    }

    // Unidad de cada característica con número
    private static String unidad(String caracteristica) {
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

    /** Lo que se muestra en el campo de esa característica ("3.50 m", "Arcilloso" o vacío). */
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

    /**
     * Para el Control de Aplicación: revisa que las características con número traigan un número
     * y las deja con su valor, su unidad y su límite. Regresa el primer error o null si todo está bien.
     */
    public static String normalizar(List<RequerimientoTecnicoDTO> lista) {
        String[] conNumero = {PROFUNDIDAD, POTENCIA, PESO, ANCHO};
        for (String caracteristica : conNumero) {
            String texto = capturado(lista, caracteristica);
            if (texto != null && !ValidadorPresentacion.esNumeroConUnidad(texto, unidad(caracteristica))) {
                return "Escribe un número en «" + caracteristica + "» (en " + unidad(caracteristica) + ").";
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

    // El texto que se capturó para una característica con número y que todavía no se convierte
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
            Matcher numero = NUMERO.matcher(texto);
            numero.find();
            requerimiento.setCaracteristica(caracteristica);
            requerimiento.setValorRequerido(Double.valueOf(numero.group().replace(',', '.')));
            requerimiento.setUnidadMedida(unidad(caracteristica));
            requerimiento.setTipoLimite(limite(caracteristica));
        }
    }

    // 120 se queda como "120", 3.5 sale como "3.50" y 3.125 como "3.125" (no se redondea
    // lo capturado, porque el campo se vuelve a leer al salir de la pantalla)
    public static String formatoNumero(Double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf(valor.longValue());
        }
        BigDecimal numero = BigDecimal.valueOf(valor).stripTrailingZeros();
        return numero.scale() < 2 ? numero.setScale(2).toPlainString() : numero.toPlainString();
    }
}
