package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.Asistencia;
import org.sicali.models.Grupo;
import org.sicali.models.Usuario;

public class AsistenciaRepository {
    private Connection connection;
    private UsuarioRepository usuarioRepository;
    private GrupoRepository grupoRepository;

    public AsistenciaRepository(Connection connection) {
        this.connection = connection;
        this.usuarioRepository = new UsuarioRepository(connection);
        this.grupoRepository = new GrupoRepository(connection);
    }

    public Asistencia crear(Asistencia asistencia) throws SQLException {
        String sql = "INSERT INTO ASISTENCIA (id_estudiante, fecha, id_grupo, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, asistencia.getIdEstudiante().getId_usuario());
            stmt.setDate(2, java.sql.Date.valueOf(asistencia.getFecha()));
            stmt.setInt(3, asistencia.getIdGrupo().getIdGrupo());
            stmt.setString(4, asistencia.getEstado() != null ? asistencia.getEstado().name() : null);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                asistencia.setIdAsistencia(rs.getInt(1));
            }
        }
        return asistencia;
    }

    public Asistencia obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ASISTENCIA WHERE id_asistencia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario estudiante = usuarioRepository.obtenerPorId(rs.getInt("id_estudiante"));
                Grupo grupo = grupoRepository.obtenerPorId(rs.getInt("id_grupo"));
                // Map SQL types to LocalDate and EstadoAsistencia
                java.sql.Date sqlDate = rs.getDate("fecha");
                java.time.LocalDate fecha = sqlDate != null ? sqlDate.toLocalDate() : null;
                String estadoStr = rs.getString("estado");
                org.sicali.models.EstadoAsistencia estado = estadoStr != null ? org.sicali.models.EstadoAsistencia.valueOf(estadoStr) : null;
                java.sql.Timestamp ts = rs.getTimestamp("fecha_registro");
                java.time.LocalDateTime fechaRegistro = ts != null ? ts.toLocalDateTime() : null;

                return new Asistencia(
                    rs.getInt("id_asistencia"),
                    estudiante,
                    fecha,
                    grupo,
                    estado,
                    fechaRegistro
                );
            }
        }
        return null;
    }

    public List<Asistencia> obtenerTodos() throws SQLException {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM ASISTENCIA";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario estudiante = usuarioRepository.obtenerPorId(rs.getInt("id_estudiante"));
                Grupo grupo = grupoRepository.obtenerPorId(rs.getInt("id_grupo"));
                java.sql.Date sqlDate = rs.getDate("fecha");
                java.time.LocalDate fecha = sqlDate != null ? sqlDate.toLocalDate() : null;
                String estadoStr = rs.getString("estado");
                org.sicali.models.EstadoAsistencia estado = estadoStr != null ? org.sicali.models.EstadoAsistencia.valueOf(estadoStr) : null;
                java.sql.Timestamp ts = rs.getTimestamp("fecha_registro");
                java.time.LocalDateTime fechaRegistro = ts != null ? ts.toLocalDateTime() : null;

                asistencias.add(new Asistencia(
                    rs.getInt("id_asistencia"),
                    estudiante,
                    fecha,
                    grupo,
                    estado,
                    fechaRegistro
                ));
            }
        }
        return asistencias;
    }

    public void actualizar(Asistencia asistencia) throws SQLException {
        String sql = "UPDATE ASISTENCIA SET id_estudiante = ?, fecha = ?, id_grupo = ?, estado = ? WHERE id_asistencia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, asistencia.getIdEstudiante().getId_usuario());
            stmt.setDate(2, java.sql.Date.valueOf(asistencia.getFecha()));
            stmt.setInt(3, asistencia.getIdGrupo().getIdGrupo());
            stmt.setString(4, asistencia.getEstado() != null ? asistencia.getEstado().name() : null);
            stmt.setInt(5, asistencia.getIdAsistencia());
            stmt.executeUpdate();
        }
    }

}