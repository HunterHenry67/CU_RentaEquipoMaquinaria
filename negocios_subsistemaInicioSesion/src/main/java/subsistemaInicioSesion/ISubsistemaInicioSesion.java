package subsistemaInicioSesion;

import objetosdto.UsuarioDTO;

/**
 * Contrato del subsistema de inicio de sesión.
 */
public interface ISubsistemaInicioSesion {

    // El usuario con ese correo y esa contraseña, o null si no corresponden a ninguno
    public abstract UsuarioDTO autenticarUsuario(String correo, String contrasenia);
}
