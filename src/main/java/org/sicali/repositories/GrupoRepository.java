package org.sicali.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sicali.models.Grupo;
import org.sicali.models.Periodo;
import org.sicali.models.Usuario;

public class GrupoRepository {
    private Connection connection;
    private PeriodoRepository periodoRepository;
    private UsuarioRepository usuarioRepository;

    public GrupoRepository(Connection connection) {
        this.connection = connection;
        this.periodoRepository = new PeriodoRepository(connection);
        this.usuarioRepository = new UsuarioRepository(connection);
    }

    public Grupo crear(Grupo grupo) throws SQLException {
        String sql = "INSERT INTO GRUPO (nombre, grado, id_periodo, id_docente) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, grupo.getNombre());
            stmt.setInt(2, grupo.getGrado());
            stmt.setInt(3, grupo.getIdPeriodo().getIdPeriodo());
            stmt.setInt(4, grupo.getIdDocente().getId_usuario());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                grupo.setIdGrupo(rs.getInt(1));
            }
        }
        return grupo;
    }

    public Grupo obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM GRUPO WHERE id_grupo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Periodo periodo = periodoRepository.obtenerPorId(rs.getInt("id_periodo"));
                Usuario docente = usuarioRepository.obtenerPorId(rs.getInt("id_docente"));
                return new Grupo(
                    rs.getInt("id_grupo"),
                    rs.getString("nombre"),
                    rs.getInt("grado"),
                    periodo,
                    docente
                );
            }
        }
        return null;
    }

    public List<Grupo> obtenerTodos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT * FROM GRUPO";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Periodo periodo = periodoRepository.obtenerPorId(rs.getInt("id_periodo"));
                Usuario docente = usuarioRepository.obtenerPorId(rs.getInt("id_docente"));
                grupos.add(new Grupo(
                    rs.getInt("id_grupo"),
                    rs.getString("nombre"),
                    rs.getInt("grado"),
                    periodo,
                    docente
                ));
            }
        }
        return grupos;
    }

    public void actualizar(Grupo grupo) throws SQLException {
        String sql = "UPDATE GRUPO SET nombre = ?, grado = ?, id_periodo = ?, id_docente = ? WHERE id_grupo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, grupo.getNombre());
            stmt.setInt(2, grupo.getGrado());
            stmt.setInt(3, grupo.getIdPeriodo().getIdPeriodo());
            stmt.setInt(4, grupo.getIdDocente().getId_usuario());
            stmt.setInt(5, grupo.getIdGrupo());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM GRUPO WHERE id_grupo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}