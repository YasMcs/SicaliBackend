package org.sicali.services;

import org.sicali.models.TutorEstudiante;
import org.sicali.models.Usuario;
import org.sicali.repositories.TutorEstudianteRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TutorEstudianteService {
    private TutorEstudianteRepository tutorEstudianteRepository;

    public TutorEstudianteService(Connection connection) {
        this.tutorEstudianteRepository = new TutorEstudianteRepository(connection);
    }

    public TutorEstudiante crearTutorEstudiante(TutorEstudiante tutorEstudiante) throws SQLException {
        validarTutorEstudiante(tutorEstudiante);
        return tutorEstudianteRepository.crear(tutorEstudiante);
    }

    public TutorEstudiante obtenerTutorEstudiantePorIds(int idTutor, int idEstudiante) throws SQLException {
        TutorEstudiante tutorEstudiante = tutorEstudianteRepository.obtenerPorIds(idTutor, idEstudiante);
        if (tutorEstudiante == null) {
            throw new SQLException("Relación Tutor-Estudiante no encontrada");
        }
        return tutorEstudiante;
    }

    public List<TutorEstudiante> obtenerTodosTutorEstudiantes() throws SQLException {
        return tutorEstudianteRepository.obtenerTodos();
    }

    public void eliminarTutorEstudiante(int idTutor, int idEstudiante) throws SQLException {
        tutorEstudianteRepository.eliminar(idTutor, idEstudiante);
    }

    public List<TutorEstudiante> obtenerEstudiantesPorTutor(int idTutor) throws SQLException {
        List<TutorEstudiante> tutorEstudiantes = tutorEstudianteRepository.obtenerTodos();
        tutorEstudiantes.removeIf(te -> te.getIdTutor().getId_usuario() != idTutor);
        return tutorEstudiantes;
    }

    public List<TutorEstudiante> obtenerTutoresPorEstudiante(int idEstudiante) throws SQLException {
        List<TutorEstudiante> tutorEstudiantes = tutorEstudianteRepository.obtenerTodos();
        tutorEstudiantes.removeIf(te -> te.getIdEstudiante().getId_usuario() != idEstudiante);
        return tutorEstudiantes;
    }

    private void validarTutorEstudiante(TutorEstudiante tutorEstudiante) throws SQLException {
        if (tutorEstudiante.getIdTutor() == null) {
            throw new SQLException("El tutor es requerido");
        }
        if (tutorEstudiante.getIdEstudiante() == null) {
            throw new SQLException("El estudiante es requerido");
        }
        if (tutorEstudiante.getIdTutor() == tutorEstudiante.getIdEstudiante()) {
            throw new SQLException("Un usuario no puede ser tutor de sí mismo");
        }
    }
}