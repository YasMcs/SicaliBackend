package org.sicali.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.sicali.models.Asistencia;
import org.sicali.repositories.AsistenciaRepository;

public class AsistenciaService {
    private AsistenciaRepository asistenciaRepository;

    public AsistenciaService(Connection connection) {
        this.asistenciaRepository = new AsistenciaRepository(connection);
    }

    public Asistencia crearAsistencia(Asistencia asistencia) throws SQLException {
        validarAsistencia(asistencia);
        return asistenciaRepository.crear(asistencia);
    }

    public Asistencia obtenerAsistenciaPorId(int id) throws SQLException {
        Asistencia asistencia = asistenciaRepository.obtenerPorId(id);
        if (asistencia == null) {
            throw new SQLException("Asistencia no encontrada con id: " + id);
        }
        return asistencia;
    }

    public List<Asistencia> obtenerTodasAsistencias() throws SQLException {
        return asistenciaRepository.obtenerTodos();
    }

    public void actualizarAsistencia(Asistencia asistencia) throws SQLException {
        validarAsistencia(asistencia);
        asistenciaRepository.actualizar(asistencia);
    }

    public List<Asistencia> obtenerAsistenciasPorEstudiante(int idEstudiante) throws SQLException {
        List<Asistencia> asistencias = asistenciaRepository.obtenerTodos();
        asistencias.removeIf(a -> a.getIdEstudiante().getId_usuario() != idEstudiante);
        return asistencias;
    }

    public List<Asistencia> obtenerAsistenciasPorGrupo(int idGrupo) throws SQLException {
        List<Asistencia> asistencias = asistenciaRepository.obtenerTodos();
        asistencias.removeIf(a -> a.getIdGrupo().getIdGrupo() != idGrupo);
        return asistencias;
    }

    public List<Asistencia> obtenerAsistenciasPorFecha(LocalDate fecha) throws SQLException {
        List<Asistencia> asistencias = asistenciaRepository.obtenerTodos();
        asistencias.removeIf(a -> a.getFecha() == null || !a.getFecha().equals(fecha));
        return asistencias;
    }

    public double calcularPorcentajeAsistencia(int idEstudiante, int idGrupo) throws SQLException {
        List<Asistencia> asistencias = asistenciaRepository.obtenerTodos();
        asistencias.removeIf(a -> a.getIdEstudiante().getId_usuario() != idEstudiante
                || a.getIdGrupo().getIdGrupo() != idGrupo);

        if (asistencias.isEmpty()) {
            return 0.0;
        }

        long asistenciasTotales = asistencias.size();
        long asistenciasPresentes = asistencias.stream().filter(a -> a.getEstado() == org.sicali.models.EstadoAsistencia.Asistencia).count();

        return (asistenciasPresentes * 100.0) / asistenciasTotales;
    }

    private void validarAsistencia(Asistencia asistencia) throws SQLException {
        if (asistencia.getIdEstudiante() == null) {
            throw new SQLException("El estudiante es requerido");
        }
        if (asistencia.getIdGrupo() == null) {
            throw new SQLException("El grupo es requerido");
        }
        if (asistencia.getFecha() == null) {
            throw new SQLException("La fecha es requerida");
        }
    }
}