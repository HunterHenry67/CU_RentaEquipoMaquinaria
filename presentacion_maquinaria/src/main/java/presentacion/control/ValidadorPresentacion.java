package presentacion.control;

import java.time.LocalDate;

/**
 * Validaciones propias de la presentación: que lo capturado tenga un formato correcto.
 * Las usa el Control de Aplicación; no decide nada del negocio (si un cliente puede rentar,
 * si una unidad está libre, etc.).
 */
public class ValidadorPresentacion {

    private static final String CORREO = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$";
    private static final String RFC = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$";
    private static final String NUMERO = "^\\d+(\\.\\d+|,\\d{1,2})?$";

    private ValidadorPresentacion() {
    }

    public static boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
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
}
