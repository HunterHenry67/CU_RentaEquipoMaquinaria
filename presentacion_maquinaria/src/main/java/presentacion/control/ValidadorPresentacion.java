package presentacion.control;

import java.time.LocalDate;

/**
 * Validaciones propias de la presentación: que lo capturado tenga un formato correcto.
 * Las usa el Control de Aplicación; no decide nada del negocio (si un cliente puede rentar,
 * si una unidad está libre, etc.).
 */
public class ValidadorPresentacion {

    private static final String CORREO = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$";
    // RFC: 3 letras (empresa) o 4 (persona), la fecha en 6 dígitos y la homoclave de 3
    private static final String RFC = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$";
    private static final String NUMERO = "^\\d+([.,]\\d+)?$";

    private ValidadorPresentacion() {
    }

    public static boolean esTextoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.trim().matches(CORREO);
    }

    public static boolean tieneLongitudMinima(String texto, int minimo) {
        return texto != null && texto.length() >= minimo;
    }

    public static boolean esNumeroEntero(String texto) {
        return texto != null && texto.trim().matches("^\\d+$");
    }

    // Un número con punto o coma decimal; si trae la unidad ("3.50 m") se toma el número
    public static boolean esNumeroValido(String texto) {
        if (texto == null) {
            return false;
        }
        String limpio = texto.trim().replaceAll("\\s*[a-zA-Z]+$", "");
        return limpio.matches(NUMERO);
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.trim().matches("^\\d{10}$");
    }

    public static boolean esRfcValido(String rfc) {
        return rfc != null && rfc.trim().toUpperCase().matches(RFC);
    }

    public static boolean esFechaValida(LocalDate fecha) {
        return fecha != null;
    }

    // Las dos fechas capturadas y la final no antes de la inicial
    public static boolean esPeriodoValido(LocalDate inicio, LocalDate fin) {
        return esFechaValida(inicio) && esFechaValida(fin) && !fin.isBefore(inicio);
    }

    public static boolean esSeleccionValida(Object seleccion) {
        return seleccion != null;
    }
}
