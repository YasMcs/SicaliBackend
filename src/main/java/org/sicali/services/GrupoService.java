package org.sicali.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.sicali.models.Grupo;
import org.sicali.repositories.GrupoRepository;
import org.sicali.repositories.PeriodoRepository;
import org.sicali.repositories.UsuarioRepository;

public class GrupoService {
    private GrupoRepository grupoRepository;
    private Connection connection;

    public GrupoService(Connection connection) {
        this.connection = connection;
        this.grupoRepository = new GrupoRepository(connection);
    }

    public Grupo crearGrupo(Grupo grupo) throws SQLException {
        validarGrupo(grupo);
        return grupoRepository.crear(grupo);
    }

    public Grupo obtenerGrupoPorId(int id) throws SQLException {
        Grupo grupo = grupoRepository.obtenerPorId(id);
        if (grupo == null) {
            throw new SQLException("Grupo no encontrado con id: " + id);
        }
        return grupo;
    }

    public List<Grupo> obtenerTodosGrupos() throws SQLException {
        return grupoRepository.obtenerTodos();
    }

    public void actualizarGrupo(Grupo grupo) throws SQLException {
        validarGrupo(grupo);
        grupoRepository.actualizar(grupo);
    }

    public void eliminarGrupo(int id) throws SQLException {
        grupoRepository.eliminar(id);
    }

    public List<Grupo> obtenerGruposPorDocente(int idDocente) throws SQLException {
        List<Grupo> grupos = grupoRepository.obtenerTodos();
        grupos.removeIf(g -> g.getIdDocente().getId_usuario() != idDocente);
        return grupos;
    }

    public List<Grupo> obtenerGruposPorPeriodo(int idPeriodo) throws SQLException {
        List<Grupo> grupos = grupoRepository.obtenerTodos();
        grupos.removeIf(g -> g.getIdPeriodo().getIdPeriodo() != idPeriodo);
        return grupos;
    }

    private void validarGrupo(Grupo grupo) throws SQLException {
        if (grupo.getNombre() == null || grupo.getNombre().trim().isEmpty()) {
            throw new SQLException("El nombre del grupo es requerido");
        }
        if (grupo.getGrado() < 1 || grupo.getGrado() > 6) {
            throw new SQLException("El grado debe estar entre 1 y 6");
        }
        if (grupo.getIdPeriodo() == null) {
            throw new SQLException("El periodo es requerido");
        }
        // Verify periodo exists
        PeriodoRepository periodoRepo = new PeriodoRepository(this.connection);
        if (periodoRepo.obtenerPorId(grupo.getIdPeriodo().getIdPeriodo()) == null) {
            throw new SQLException("Periodo con id " + grupo.getIdPeriodo().getIdPeriodo() + " no existe. Crea el periodo primero.");
        }
        if (grupo.getIdDocente() == null) {
            throw new SQLException("El docente es requerido");
        }
        // Optionally validate docente exists
        UsuarioRepository usuarioRepo = new UsuarioRepository(this.connection);
        if (usuarioRepo.obtenerPorId(grupo.getIdDocente().getId_usuario()) == null) {
            throw new SQLException("Docente con id " + grupo.getIdDocente().getId_usuario() + " no existe.");
        }
    }
}