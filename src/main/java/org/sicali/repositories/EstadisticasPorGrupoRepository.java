package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.EstadisticasPorGrupo;

public class EstadisticasPorGrupoRepository {
    private Connection connection;

    public EstadisticasPorGrupoRepository(Connection connection) {
        this.connection = connection;
    }

    public List<EstadisticasPorGrupo> obtenerEstadisticasPorGrupo(int idGrupo) throws SQLException {
        List<EstadisticasPorGrupo> estadisticas = new ArrayList<>();

        String sql = "SELECT " +
                "g.id_grupo, " +
                "g.nombre AS nombre_grupo, " +
                "g.grado, " +
                "p.id_periodo, " +
                "CONCAT('Periodo ', p.numero_periodo) AS nombre_periodo, " +
                "a.nombre AS nombre_asignatura, " +
                "ROUND(AVG(c.valor), 2) AS promedio_calificaciones, " +
                "COUNT(DISTINCT eg.id_estudiante) AS total_estudiantes, " +
                "ROUND((SUM(CASE WHEN ast.estado = 'Asistencia' THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(ast.id_asistencia), 0)), 2) AS porcentaje_asistencia " +
                "FROM `GRUPO` g " +
                "INNER JOIN `PERIODO` p ON g.id_periodo = p.id_periodo " +
                "INNER JOIN `GRUPO_ASIGNATURA` ga ON g.id_grupo = ga.id_grupo " +
                "INNER JOIN `ASIGNATURA` a ON ga.id_asignatura = a.id_asignatura " +
                "INNER JOIN `ESTUDIANTE_GRUPO` eg ON ga.id_grupo = eg.id_grupo " +
                "LEFT JOIN `CALIFICACION` c ON c.id_estudiante = eg.id_estudiante " +
                "AND c.id_grupo_asignatura = ga.id_grupo_asignatura " +
                "AND c.id_periodo = p.id_periodo " +
                "LEFT JOIN `ASISTENCIA` ast ON eg.id_estudiante = ast.id_estudiante " +
                "AND g.id_grupo = ast.id_grupo " +
                "WHERE g.id_grupo = ? " +
                "GROUP BY g.id_grupo, g.nombre, g.grado, p.id_periodo, a.nombre";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EstadisticasPorGrupo estadistica = new EstadisticasPorGrupo(
                            rs.getInt("id_grupo"),
                            rs.getString("nombre_grupo"),
                            rs.getString("grado"),
                            rs.getInt("id_periodo"),
                            rs.getString("nombre_periodo"),
                            rs.getString("nombre_asignatura"),
                            rs.getDouble("promedio_calificaciones"),
                            rs.getInt("total_estudiantes"),
                            rs.getDouble("porcentaje_asistencia")
                    );
                    estadisticas.add(estadistica);
                }
            }
        }

        return estadisticas;
    }
}