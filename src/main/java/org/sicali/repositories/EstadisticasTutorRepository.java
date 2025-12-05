package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.EstadisticasTutor;

public class EstadisticasTutorRepository {
    private Connection connection;

    public EstadisticasTutorRepository(Connection connection) {
        this.connection = connection;
    }

    public List<EstadisticasTutor> obtenerEstadisticasPorTutor(int idTutor) throws SQLException {
        List<EstadisticasTutor> estadisticas = new ArrayList<>();

        String sql = "SELECT " +
                "te.id_tutor, " +
                "te.id_estudiante, " +
                "u.nombre AS nombre_estudiante, " +
                "u.ape_p AS apellido_paterno, " +
                "u.ape_m AS apellido_materno, " +
                "g.id_grupo, " +
                "g.nombre AS nombre_grupo, " +
                "g.grado, " +
                "p.id_periodo, " +
                "CONCAT('Periodo ', p.numero_periodo) AS nombre_periodo, " +
                "a.nombre AS nombre_asignatura, " +
                "COALESCE(MAX(c.valor), 0) AS calificacion, " +
                "ROUND((SUM(CASE WHEN ast.estado = 'Asistencia' THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(ast.id_asistencia), 0)), 2) AS porcentaje_asistencia " +
                "FROM `TUTOR_ESTUDIANTE` te " +
                "INNER JOIN `USUARIO` u ON te.id_estudiante = u.id_usuario " +
                "INNER JOIN `ESTUDIANTE_GRUPO` eg ON te.id_estudiante = eg.id_estudiante " +
                "INNER JOIN `GRUPO_ASIGNATURA` ga ON ga.id_grupo = eg.id_grupo " +
                "INNER JOIN `GRUPO` g ON ga.id_grupo = g.id_grupo " +
                "INNER JOIN `PERIODO` p ON g.id_periodo = p.id_periodo " +
                "INNER JOIN `ASIGNATURA` a ON ga.id_asignatura = a.id_asignatura " +
                "LEFT JOIN `CALIFICACION` c ON c.id_estudiante = eg.id_estudiante " +
                "AND c.id_grupo_asignatura = ga.id_grupo_asignatura " +
                "AND c.id_periodo = p.id_periodo " +
                "LEFT JOIN `ASISTENCIA` ast ON te.id_estudiante = ast.id_estudiante " +
                "AND g.id_grupo = ast.id_grupo " +
                "WHERE te.id_tutor = ? " +
                "GROUP BY te.id_tutor, te.id_estudiante, u.nombre, u.ape_p, u.ape_m, " +
                "g.id_grupo, g.nombre, g.grado, p.id_periodo, a.nombre";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTutor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EstadisticasTutor estadistica = new EstadisticasTutor(
                            rs.getInt("id_tutor"),
                            rs.getInt("id_estudiante"),
                            rs.getString("nombre_estudiante"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getInt("id_grupo"),
                            rs.getString("nombre_grupo"),
                            rs.getInt("grado"),
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