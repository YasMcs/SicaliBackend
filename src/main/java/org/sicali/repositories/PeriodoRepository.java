package org.sicali.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.Ciclo;
import org.sicali.models.Periodo;

public class PeriodoRepository {
    private Connection connection;
    private CicloRepository cicloRepository;

    public PeriodoRepository(Connection connection) {
        this.connection = connection;
        this.cicloRepository = new CicloRepository(connection);
    }

    public Periodo crear(Periodo periodo) throws SQLException {
        String sql = "INSERT INTO PERIODO (id_ciclo, numero_periodo, nombre, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, periodo.getIdCiclo().getIdCiclo());
            stmt.setInt(2, periodo.getNumeroPeriodo());
            stmt.setString(3, periodo.getNombre());
            if (periodo.getFechaInicio() != null) stmt.setDate(4, Date.valueOf(periodo.getFechaInicio()));
            else stmt.setNull(4, Types.DATE);
            if (periodo.getFechaFin() != null) stmt.setDate(5, Date.valueOf(periodo.getFechaFin()));
            else stmt.setNull(5, Types.DATE);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                periodo.setIdPeriodo(rs.getInt(1));
            }
        }
        return periodo;
    }

    public Periodo obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM PERIODO WHERE id_periodo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idCiclo = rs.getInt("id_ciclo");
                Ciclo ciclo = cicloRepository.obtenerPorId(idCiclo);
                int numero = rs.getInt("numero_periodo");
                String nombre = rs.getString("nombre");
                java.sql.Date fi = rs.getDate("fecha_inicio");
                java.sql.Date ff = rs.getDate("fecha_fin");
                java.time.LocalDate fechaInicio = fi != null ? fi.toLocalDate() : null;
                java.time.LocalDate fechaFin = ff != null ? ff.toLocalDate() : null;
                return new Periodo(rs.getInt("id_periodo"), ciclo, numero, nombre, fechaInicio, fechaFin);
            }
        }
        return null;
    }

    public List<Periodo> obtenerTodos() throws SQLException {
        List<Periodo> periodos = new ArrayList<>();
        String sql = "SELECT * FROM PERIODO";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int idCiclo = rs.getInt("id_ciclo");
                Ciclo ciclo = cicloRepository.obtenerPorId(idCiclo);
                int numero = rs.getInt("numero_periodo");
                String nombre = rs.getString("nombre");
                java.sql.Date fi = rs.getDate("fecha_inicio");
                java.sql.Date ff = rs.getDate("fecha_fin");
                java.time.LocalDate fechaInicio = fi != null ? fi.toLocalDate() : null;
                java.time.LocalDate fechaFin = ff != null ? ff.toLocalDate() : null;
                periodos.add(new Periodo(rs.getInt("id_periodo"), ciclo, numero, nombre, fechaInicio, fechaFin));
            }
        }
        return periodos;
    }

    public void actualizar(Periodo periodo) throws SQLException {
        String sql = "UPDATE PERIODO SET id_ciclo = ?, numero_periodo = ?, nombre = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_periodo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, periodo.getIdCiclo().getIdCiclo());
            stmt.setInt(2, periodo.getNumeroPeriodo());
            stmt.setString(3, periodo.getNombre());
            if (periodo.getFechaInicio() != null) stmt.setDate(4, Date.valueOf(periodo.getFechaInicio()));
            else stmt.setNull(4, Types.DATE);
            if (periodo.getFechaFin() != null) stmt.setDate(5, Date.valueOf(periodo.getFechaFin()));
            else stmt.setNull(5, Types.DATE);
            stmt.setInt(6, periodo.getIdPeriodo());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM PERIODO WHERE id_periodo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}