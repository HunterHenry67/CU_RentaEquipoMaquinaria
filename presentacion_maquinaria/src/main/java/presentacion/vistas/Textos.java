package presentacion.vistas;

import java.text.Normalizer;

/**
 * Comparación de textos para las búsquedas de las pantallas: sin acentos y en minúsculas.
 */
public class Textos {

    private Textos() {
    }

    public static String sinAcentos(String texto) {
        if (texto == null) {
            return "";
        }
        String sinMarcas = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinMarcas.toLowerCase();
    }
}
