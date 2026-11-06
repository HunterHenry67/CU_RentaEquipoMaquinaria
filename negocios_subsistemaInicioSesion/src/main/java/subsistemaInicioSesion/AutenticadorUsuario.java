package subsistemaInicioSesion;

import objetosdto.UsuarioDTO;

/**
 * Permite iniciar sesion con cualquier correo y contrasenia validos.
 * No hay persistencia real por ahora, acepta cualquier cuenta.
 */
class AutenticadorUsuario {

    public UsuarioDTO validarCredenciales(String correo, String contrasenia) {
        if (correo == null || correo.trim().isEmpty()
                || contrasenia == null || contrasenia.trim().isEmpty()) {
            return null;
        }
        UsuarioDTO autenticado = new UsuarioDTO();
        autenticado.setCorreo(correo.trim());
        return autenticado;
    }
}
