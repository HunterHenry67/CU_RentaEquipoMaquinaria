package subsistemaInicioSesion;

import objetosdto.UsuarioDTO;

/**
 * Fachada del subsistema de inicio de sesión: le pasa la revisión a su clase interna.
 */
public class SubsistemaInicioSesion implements ISubsistemaInicioSesion {

    private final AutenticadorUsuario autenticador = new AutenticadorUsuario();

    @Override
    public UsuarioDTO autenticarUsuario(String correo, String contrasenia) {
        return autenticador.validarCredenciales(correo, contrasenia);
    }
}
