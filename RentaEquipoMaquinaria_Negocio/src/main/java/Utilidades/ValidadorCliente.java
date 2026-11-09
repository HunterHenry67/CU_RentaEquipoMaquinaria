/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.util.regex.Pattern;
import objetosdto.ClienteDTO;
import objetosdto.ClienteEmpresaDTO;

/**
 *
 * @author Home
 */
public class ValidadorCliente {
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
    
    public static boolean rfcValido(String rfc) {
        return rfc != null && rfc.trim().toUpperCase().matches(RFC);
    }
    
     public static boolean telefonoValido(String telefono) {
        return telefono != null && telefono.trim().matches("^\\d{10}$");
    }
     
    public static boolean correoValido(String correo) {
        return correo != null && correo.trim().matches(CORREO);
    }
    
    public static String faltantesCliente(ClienteDTO cliente) {
        return camposVacios("Nombre", cliente.getNombre(), "Correo electrónico", cliente.getCorreo(),
                "Teléfono", cliente.getTelefono(), "RFC", cliente.getRfc());
    }

    public static String faltantesEncargado(ClienteEmpresaDTO empresa) {
        return camposVacios("Nombre del encargado", empresa.getNombreEncargadoEmpresa(),
                "Teléfono del encargado", empresa.getTelefonoEncargadoEmpresa(), "Puesto del encargado", empresa.getPuestoEncargadoEmpresa());
    }
    
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
     
    public static boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
