/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

/**
 *
 * @author BALAMRUSH
 */
public class ResultadoCancelacionDTO {
    private boolean exitoso;
    private String estado;
    private String mensaje;

    public ResultadoCancelacionDTO(boolean exitoso, String estado, String mensaje) {
        this.exitoso = exitoso;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
