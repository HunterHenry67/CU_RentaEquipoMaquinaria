package objetosdto;

/**
 *
 * @author BALAMRUSH
 */
public class UsuarioDTO {
    
    private String correo;
    private String contrasenia;
    
    public UsuarioDTO(){
        
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
