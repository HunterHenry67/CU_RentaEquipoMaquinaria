/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

/**
 *
 * @author alejandromontijo
 */
public class ClienteDTO {

    private Long idCliente;
    private String nombre;
    private String rfc;
    private String telefono;
    private String correo;
    private String tipoCliente;

    public ClienteDTO() {
    }

    public ClienteDTO(Long idCliente, String nombre, String rfc, String telefono, String correo, String tipoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.rfc = rfc;
        this.telefono = telefono;
        this.correo = correo;
        this.tipoCliente = tipoCliente;
    }
    

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

}
