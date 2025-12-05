package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.EstadisticasPorEstudiante;

public class EstadisticasPorEstudianteRepository {
    private Connection connection;

    public EstadisticasPorEstudianteRepository(Connection connection) {
        this.connection = connection;
    }

    public List<EstadisticasPorEstudiante> obtenerEstadisticasPorEstudiante(int idEstudiante) throws SQLException {
        List<EstadisticasPorEstudiante> estadisticas = new ArrayList<>();

        String sql = "SELECT " +
                "u.id_usuario AS id_estudiante, " +
                "u.nombre AS nombre_estudiante, " +
                "u.ape_p AS apellido_paterno, " +
                "u.ape_m AS apellido_materno, " +
                "p.id_periodo, " +
                "CONCAT('Periodo ', p.numero_periodo) AS nombre_periodo, " +
                "a.nombre AS nombre_asignatura, " +
                "COALESCE(MAX(c.valor), 0) AS calificacion, " +
                "ROUND((SUM(CASE WHEN ast.estado = 'Asistencia' THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(ast.id_asistencia), 0)), 2) AS porcentaje_asistencia " +
                "FROM `USUARIO` u " +
                "INNER JOIN `ESTUDIANTE_GRUPO` eg ON u.id_usuario = eg.id_estudiante " +
                "INNER JOIN `GRUPO_ASIGNATURA` ga ON ga.id_grupo = eg.id_grupo " +
                "INNER JOIN `ASIGNATURA` a ON ga.id_asignatura = a.id_asignatura " +
                "INNER JOIN `GRUPO` g ON ga.id_grupo = g.id_grupo " +
                "INNER JOIN `PERIODO` p ON g.id_periodo = p.id_periodo " +
                "LEFT JOIN `CALIFICACION` c ON c.id_estudiante = eg.id_estudiante " +
                "AND c.id_grupo_asignatura = ga.id_grupo_asignatura " +
                "AND c.id_periodo = p.id_periodo " +
                "LEFT JOIN `ASISTENCIA` ast ON u.id_usuario = ast.id_estudiante " +
                "AND g.id_grupo = ast.id_grupo " +
                "WHERE u.id_usuario = ? " +
                "GROUP BY u.id_usuario, u.nombre, u.ape_p, u.ape_m, p.id_periodo, a.nombre";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEstudiante);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EstadisticasPorEstudiante estadistica = new EstadisticasPorEstudiante(
                            rs.getInt("id_estudiante"),
                            rs.getString("nombre_estudiante"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getInt("id_periodo"),
                            rs.getString("nombre_periodo"),
                            rs.getString("nombre_asignatura"),
                            rs.getInt("calificacion"),
                            rs.getDouble("porcentaje_asistencia")
                    );
                    estadisticas.add(estadistica);
                }
            }
        }

        return estadisticas;
    }
}