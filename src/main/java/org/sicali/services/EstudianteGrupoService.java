package org.sicali.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.sicali.models.EstudianteGrupo;
import org.sicali.repositories.EstudianteGrupoRepository;

public class EstudianteGrupoService {
    private EstudianteGrupoRepository estudianteGrupoRepository;

    public EstudianteGrupoService(Connection connection) {
        this.estudianteGrupoRepository = new EstudianteGrupoRepository(connection);
    }

    public EstudianteGrupo crearEstudianteGrupo(EstudianteGrupo estudianteGrupo) throws SQLException {
        validarEstudianteGrupo(estudianteGrupo);
        return estudianteGrupoRepository.crear(estudianteGrupo);
    }

    public EstudianteGrupo obtenerEstudianteGrupoPorIds(int idGrupoAsignatura, int idEstudiante) throws SQLException {
        EstudianteGrupo estudianteGrupo = estudianteGrupoRepository.obtenerPorIds(idGrupoAsignatura, idEstudiante);
        if (estudianteGrupo == null) {
            throw new SQLException("Estudiante-Grupo no encontrado");
        }
        return estudianteGrupo;
    }

    public List<EstudianteGrupo> obtenerTodosEstudiantesGrupos() throws SQLException {
        return estudianteGrupoRepository.obtenerTodos();
    }

    public void eliminarEstudianteGrupo(int idGrupo, int idEstudiante) throws SQLException {
        estudianteGrupoRepository.eliminar(idGrupo, idEstudiante);
    }

    public List<EstudianteGrupo> obtenerEstudiantesPorGrupo(int idGrupo) throws SQLException {
        List<EstudianteGrupo> estudiantesGrupos = estudianteGrupoRepository.obtenerTodos();
        estudiantesGrupos.removeIf(eg -> eg.getGrupo() == null || eg.getGrupo().getIdGrupo() != idGrupo);
        return estudiantesGrupos;
    }

    public List<EstudianteGrupo> obtenerGruposPorEstudiante(int idEstudiante) throws SQLException {
        List<EstudianteGrupo> estudiantesGrupos = estudianteGrupoRepository.obtenerTodos();
        estudiantesGrupos.removeIf(eg -> eg.getEstudiante() == null || eg.getEstudiante().getId_usuario() != idEstudiante);
        return estudiantesGrupos;
    }

    public double calcularPromedioEstudiante(int idEstudiante) throws SQLException {
        // El cálculo de calificaciones ahora debe realizarse a partir de la tabla CALIFICACION.
        // Esta implementación devuelve 0.0 y debe ser reemplazada por un servicio que lea CALIFICACION.
        return 0.0;
    }

    // Compatibilidad temporal: mantiene el método que algunos controladores aún llaman.
    // Indica al cliente que las calificaciones ya no se actualizan desde EstudianteGrupo.
    public void actualizarCalificacion(int idGrupoAsignatura, int idEstudiante, int nuevaCalificacion) throws SQLException {
        throw new SQLException("Las calificaciones ahora se manejan en la entidad CALIFICACION. Use el endpoint de Calificacion para crear/actualizar calificaciones.");
    }

    private void validarEstudianteGrupo(EstudianteGrupo estudianteGrupo) throws SQLException {
        if (estudianteGrupo.getGrupo() == null) {
            throw new SQLException("El grupo es requerido");
        }
        if (estudianteGrupo.getEstudiante() == null) {
            throw new SQLException("El estudiante es requerido");
        }
    }
}