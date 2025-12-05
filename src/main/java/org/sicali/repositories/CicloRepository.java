package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.Ciclo;

public class CicloRepository {
    private Connection connection;

    public CicloRepository(Connection connection) {
        this.connection = connection;
    }

    public Ciclo crear(Ciclo ciclo) throws SQLException {
        String sql = "INSERT INTO CICLO (nombre, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // nombre then LocalDate -> java.sql.Date
            stmt.setString(1, ciclo.getNombre());
            stmt.setDate(2, ciclo.getFechaInicio() != null ? java.sql.Date.valueOf(ciclo.getFechaInicio()) : null);
            stmt.setDate(3, ciclo.getFechaFin() != null ? java.sql.Date.valueOf(ciclo.getFechaFin()) : null);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                ciclo.setIdCiclo(rs.getInt(1));
            }
        }
        return ciclo;
    }

    public Ciclo obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM CICLO WHERE id_ciclo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                java.sql.Date sqlFechaInicio = rs.getDate("fecha_inicio");
                java.sql.Date sqlFechaFin = rs.getDate("fecha_fin");
                java.time.LocalDate fechaInicio = sqlFechaInicio != null ? sqlFechaInicio.toLocalDate() : null;
                java.time.LocalDate fechaFin = sqlFechaFin != null ? sqlFechaFin.toLocalDate() : null;
                return new Ciclo(
                    rs.getInt("id_ciclo"),
                    rs.getString("nombre"),
                    fechaInicio,
                    fechaFin
                );
            }
        }
        return null;
    }

    public List<Ciclo> obtenerTodos() throws SQLException {
        List<Ciclo> ciclos = new ArrayList<>();
        String sql = "SELECT * FROM CICLO";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                java.sql.Date sqlFechaInicio = rs.getDate("fecha_inicio");
                java.sql.Date sqlFechaFin = rs.getDate("fecha_fin");
                java.time.LocalDate fechaInicio = sqlFechaInicio != null ? sqlFechaInicio.toLocalDate() : null;
                java.time.LocalDate fechaFin = sqlFechaFin != null ? sqlFechaFin.toLocalDate() : null;
                ciclos.add(new Ciclo(
                    rs.getInt("id_ciclo"),
                    rs.getString("nombre"),
                    fechaInicio,
                    fechaFin
                ));
            }
        }
        return ciclos;
    }

    public void actualizar(Ciclo ciclo) throws SQLException {
        String sql = "UPDATE CICLO SET nombre = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_ciclo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ciclo.getNombre());
            stmt.setDate(2, ciclo.getFechaInicio() != null ? java.sql.Date.valueOf(ciclo.getFechaInicio()) : null);
            stmt.setDate(3, ciclo.getFechaFin() != null ? java.sql.Date.valueOf(ciclo.getFechaFin()) : null);
            stmt.setInt(4, ciclo.getIdCiclo());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM CICLO WHERE id_ciclo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}