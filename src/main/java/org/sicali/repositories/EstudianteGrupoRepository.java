package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.EstudianteGrupo;
import org.sicali.models.Grupo;
import org.sicali.models.Usuario;

public class EstudianteGrupoRepository {
    private Connection connection;
    private GrupoRepository grupoRepository;
    private UsuarioRepository usuarioRepository;

    public EstudianteGrupoRepository(Connection connection) {
        this.connection = connection;
        this.grupoRepository = new GrupoRepository(connection);
        this.usuarioRepository = new UsuarioRepository(connection);
    }

    public EstudianteGrupo crear(EstudianteGrupo estudianteGrupo) throws SQLException {
        String sql = "INSERT INTO ESTUDIANTE_GRUPO (id_estudiante, id_grupo, fecha_inscripcion, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estudianteGrupo.getEstudiante().getId_usuario());
            stmt.setInt(2, estudianteGrupo.getGrupo().getIdGrupo());
            // fecha_inscripcion LocalDate -> java.sql.Date
            if (estudianteGrupo.getFechaInscripcion() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(estudianteGrupo.getFechaInscripcion()));
            } else {
                stmt.setDate(3, null);
            }
            stmt.setString(4, estudianteGrupo.getEstado() != null ? estudianteGrupo.getEstado().name().replace('_', ' ') : null);
            stmt.executeUpdate();
        }
        return estudianteGrupo;
    }

    public EstudianteGrupo obtenerPorIds(int idGrupo, int idEstudiante) throws SQLException {
        String sql = "SELECT * FROM ESTUDIANTE_GRUPO WHERE id_grupo = ? AND id_estudiante = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idEstudiante);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Grupo grupo = grupoRepository.obtenerPorId(rs.getInt("id_grupo"));
                Usuario estudiante = usuarioRepository.obtenerPorId(rs.getInt("id_estudiante"));
                java.sql.Date sqlDate = rs.getDate("fecha_inscripcion");
                java.time.LocalDate fechaIns = sqlDate != null ? sqlDate.toLocalDate() : null;
                String estadoStr = rs.getString("estado");
                org.sicali.models.EstadoInscripcion estado = estadoStr != null ? org.sicali.models.EstadoInscripcion.valueOf(estadoStr.replace(' ', '_')) : null;
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                java.time.LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;
                return new EstudianteGrupo(
                        estudiante,
                        grupo,
                        fechaIns,
                        estado,
                        createdAt
                );
            }
        }
        return null;
    }

    public List<EstudianteGrupo> obtenerTodos() throws SQLException {
        List<EstudianteGrupo> estudianteGrupos = new ArrayList<>();
        String sql = "SELECT * FROM ESTUDIANTE_GRUPO";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grupo grupo = grupoRepository.obtenerPorId(rs.getInt("id_grupo"));
                Usuario estudiante = usuarioRepository.obtenerPorId(rs.getInt("id_estudiante"));
                java.sql.Date sqlDate = rs.getDate("fecha_inscripcion");
                java.time.LocalDate fechaIns = sqlDate != null ? sqlDate.toLocalDate() : null;
                String estadoStr = rs.getString("estado");
                org.sicali.models.EstadoInscripcion estado = estadoStr != null ? org.sicali.models.EstadoInscripcion.valueOf(estadoStr.replace(' ', '_')) : null;
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                java.time.LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;
                estudianteGrupos.add(new EstudianteGrupo(
                        estudiante,
                        grupo,
                        fechaIns,
                        estado,
                        createdAt
                ));
            }
        }
        return estudianteGrupos;
    }

    public void actualizar(EstudianteGrupo estudianteGrupo) throws SQLException {
        String sql = "UPDATE ESTUDIANTE_GRUPO SET fecha_inscripcion = ?, estado = ? WHERE id_grupo = ? AND id_estudiante = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (estudianteGrupo.getFechaInscripcion() != null) {
                stmt.setDate(1, java.sql.Date.valueOf(estudianteGrupo.getFechaInscripcion()));
            } else {
                stmt.setDate(1, null);
            }
            stmt.setString(2, estudianteGrupo.getEstado() != null ? estudianteGrupo.getEstado().name().replace('_', ' ') : null);
            stmt.setInt(3, estudianteGrupo.getGrupo().getIdGrupo());
            stmt.setInt(4, estudianteGrupo.getEstudiante().getId_usuario());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idGrupo, int idEstudiante) throws SQLException {
        String sql = "DELETE FROM ESTUDIANTE_GRUPO WHERE id_grupo = ? AND id_estudiante = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);
            stmt.setInt(2, idEstudiante);
            stmt.executeUpdate();
        }
    }
}