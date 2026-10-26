/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author BALAMRUSH
 */
public class UsuarioDTO {
    
    private String correo;
    private String contrasenia;
    
    public UsuarioDTO(){
        
    }
    
    public UsuarioDTO(String correo, String contrasenia){
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public String getContrasenia(){
        return contrasenia;
    }
    
    public void setCorreo(String correo){
        this.correo = correo;
    }
    
    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }
}
