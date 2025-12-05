package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.Usuario;

public class UsuarioRepository {
    private Connection connection;

    public UsuarioRepository(Connection connection) {
        this.connection = connection;
    }

    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO USUARIO (nombre, ape_p, ape_m, curp, rfc, sexo, usuario, password, rol, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApe_p());
            stmt.setString(3, usuario.getApe_m());
            stmt.setString(4, usuario.getCurp());
            stmt.setString(5, usuario.getRfc());
            stmt.setString(6, usuario.getSexo() != null ? usuario.getSexo().name() : null);
            stmt.setString(7, usuario.getUsuario());
            stmt.setString(8, usuario.getPassword());
            stmt.setString(9, usuario.getRol() != null ? usuario.getRol().name() : null);
            stmt.setString(10, usuario.getEstado() != null ? usuario.getEstado().name().replace('_', ' ') : null);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId_usuario(rs.getInt(1));
            }
        }
        return usuario;
    }

    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM USUARIO WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearUsuario(rs);
            }
        }
        return null;
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE USUARIO SET nombre = ?, ape_p = ?, ape_m = ?, curp = ?, rfc = ?, sexo = ?, usuario = ?, password = ?, rol = ?, estado = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApe_p());
            stmt.setString(3, usuario.getApe_m());
            stmt.setString(4, usuario.getCurp());
            stmt.setString(5, usuario.getRfc());
            stmt.setString(6, usuario.getSexo() != null ? usuario.getSexo().name() : null);
            stmt.setString(7, usuario.getUsuario());
            stmt.setString(8, usuario.getPassword());
            stmt.setString(9, usuario.getRol() != null ? usuario.getRol().name() : null);
            stmt.setString(10, usuario.getEstado() != null ? usuario.getEstado().name().replace('_', ' ') : null);
            stmt.setInt(11, usuario.getId_usuario());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        // Leer enums y timestamps, normalizando nombres cuando sea necesario
        String sexoStr = rs.getString("sexo");
        org.sicali.models.Sexo sexo = sexoStr != null ? org.sicali.models.Sexo.valueOf(sexoStr) : null;

        String rolStr = rs.getString("rol");
        org.sicali.models.Rol rol = rolStr != null ? org.sicali.models.Rol.valueOf(rolStr) : null;

        String estadoStr = rs.getString("estado");
        org.sicali.models.EstadoUsuario estado = null;
        if (estadoStr != null) {
            // DB puede tener espacios en el valor (p.ej. 'Dado de baja'); mapear a nombre de enum con '_'
            String normalized = estadoStr.replace(' ', '_');
            estado = org.sicali.models.EstadoUsuario.valueOf(normalized);
        }

        java.sql.Timestamp tsCreated = rs.getTimestamp("created_at");
        java.time.LocalDateTime createdAt = tsCreated != null ? tsCreated.toLocalDateTime() : null;
        java.sql.Timestamp tsUpdated = rs.getTimestamp("updated_at");
        java.time.LocalDateTime updatedAt = tsUpdated != null ? tsUpdated.toLocalDateTime() : null;

        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("ape_p"),
                rs.getString("ape_m"),
                rs.getString("curp"),
                rs.getString("rfc"),
                sexo,
                rs.getString("usuario"),
                rs.getString("password"),
                rol,
                estado,
                createdAt,
                updatedAt
        );
    }
}