package subsistemaInicioSesion;

import java.util.ArrayList;
import java.util.List;
import objetosdto.UsuarioDTO;

/**
 * Revisa si el correo y la contraseña corresponden a un usuario registrado. Por ahora los usuarios
 * se guardan aquí, mientras no haya persistencia real.
 */
class AutenticadorUsuario {

    private final List<UsuarioDTO> usuarios = new ArrayList<>();

    AutenticadorUsuario() {
        UsuarioDTO asesor = new UsuarioDTO();
        asesor.setCorreo("asesor@maquinaria.com");
        asesor.setContrasenia("maquinaria2026");
        usuarios.add(asesor);
    }

    public UsuarioDTO validarCredenciales(String correo, String contrasenia) {
        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getCorreo().equalsIgnoreCase(correo) && usuario.getContrasenia().equals(contrasenia)) {
                UsuarioDTO autenticado = new UsuarioDTO();
                autenticado.setCorreo(usuario.getCorreo());
                return autenticado;
            }
        }
        return null;
    }
}
