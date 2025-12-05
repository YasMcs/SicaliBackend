package org.sicali.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.sicali.models.Usuario;
import org.sicali.repositories.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(Connection connection) {
        this.usuarioRepository = new UsuarioRepository(connection);
    }

    public Usuario crearUsuario(Usuario usuario) throws SQLException {
        validarUsuario(usuario);
        return usuarioRepository.crear(usuario);
    }

    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        Usuario usuario = usuarioRepository.obtenerPorId(id);
        if (usuario == null) {
            throw new SQLException("Usuario no encontrado con id: " + id);
        }
        return usuario;
    }

    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        return usuarioRepository.obtenerTodos();
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        validarUsuario(usuario);
        usuarioRepository.actualizar(usuario);
    }

    public void eliminarUsuario(int id) throws SQLException {
        usuarioRepository.eliminar(id);
    }

    public List<Usuario> obtenerUsuariosPorRol(String rol) throws SQLException {
        List<Usuario> usuarios = usuarioRepository.obtenerTodos();
        usuarios.removeIf(u -> u.getRol() == null || !u.getRol().name().equalsIgnoreCase(rol));
        return usuarios;
    }

    private void validarUsuario(Usuario usuario) throws SQLException {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new SQLException("El nombre del usuario es requerido");
        }
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            throw new SQLException("El nombre de usuario es requerido");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new SQLException("La contraseña es requerida");
        }
        if (usuario.getRol() == null) {
            throw new SQLException("El rol es requerido");
        }
    }
}