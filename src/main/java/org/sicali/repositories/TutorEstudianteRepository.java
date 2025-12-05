package org.sicali.repositories;

import org.sicali.models.TutorEstudiante;
import org.sicali.models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorEstudianteRepository {
    private Connection connection;
    private UsuarioRepository usuarioRepository; // ¡Reincorporamos la dependencia!

    public TutorEstudianteRepository(Connection connection) {
        this.connection = connection;
        this.usuarioRepository = new UsuarioRepository(connection); // ¡Instanciamos el repositorio de Usuario!
    }

    // --- Método crear (Corregido para obtener el ID del objeto Usuario) ---
    // Clase: org.sicali.repositories.TutorEstudianteRepository

    public TutorEstudiante crear(TutorEstudiante tutorEstudiante) throws SQLException {
        // Obtener los IDs antes de la inserción
        int tutorId = tutorEstudiante.getIdTutor().getId_usuario();
        int estudianteId = tutorEstudiante.getIdEstudiante().getId_usuario();

        // 1. Insertar en la DB (tu código actual)
        String sql = "INSERT INTO TUTOR_ESTUDIANTE (id_tutor, id_estudiante) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tutorId);
            stmt.setInt(2, estudianteId);
            stmt.executeUpdate();
        }

        // 2. Después de la inserción, RECUPERAR los objetos Usuario completos
        Usuario tutorCompleto = usuarioRepository.obtenerPorId(tutorId);
        Usuario estudianteCompleto = usuarioRepository.obtenerPorId(estudianteId);

        // 3. Devolver un nuevo objeto TutorEstudiante con los datos completos
        if (tutorCompleto != null && estudianteCompleto != null) {
            return new TutorEstudiante(tutorCompleto, estudianteCompleto);
        }

        // Si la recuperación falla, devolvemos null o lanzamos una excepción
        return null;
    }

    // --- Método obtenerPorIds (Corregido para devolver objetos Usuario) ---
    public TutorEstudiante obtenerPorIds(int idTutor, int idEstudiante) throws SQLException {
        // Solo necesitamos la consulta de la tabla de relación
        String sql = "SELECT id_tutor, id_estudiante FROM TUTOR_ESTUDIANTE WHERE id_tutor = ? AND id_estudiante = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTutor);
            stmt.setInt(2, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 1. Obtener los IDs de la tabla de relación (que deben coincidir con los parámetros)
                int tutorId = rs.getInt("id_tutor");
                int estudianteId = rs.getInt("id_estudiante");

                // 2. Usar el UsuarioRepository para obtener los objetos Usuario completos
                Usuario tutor = usuarioRepository.obtenerPorId(tutorId);
                Usuario estudiante = usuarioRepository.obtenerPorId(estudianteId);

                // 3. Verificar que ambos usuarios existan antes de construir el objeto
                if (tutor != null && estudiante != null) {
                    // 4. Devolver el nuevo objeto TutorEstudiante con los objetos Usuario
                    return new TutorEstudiante(tutor, estudiante);
                }
            }
        }
        // Devuelve null si no se encuentra la relación o si faltan los usuarios
        return null;
    }

    // --- Método obtenerTodos (Corregido para devolver objetos Usuario) ---
    public List<TutorEstudiante> obtenerTodos() throws SQLException {
        List<TutorEstudiante> tutorEstudiantes = new ArrayList<>();
        String sql = "SELECT id_tutor, id_estudiante FROM TUTOR_ESTUDIANTE";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // 1. Obtener los IDs de la DB
                int tutorId = rs.getInt("id_tutor");
                int estudianteId = rs.getInt("id_estudiante");

                // 2. Usar el UsuarioRepository para obtener los objetos Usuario
                Usuario tutor = usuarioRepository.obtenerPorId(tutorId);
                Usuario estudiante = usuarioRepository.obtenerPorId(estudianteId);

                // 3. Si ambos usuarios existen, se añade la relación a la lista
                if (tutor != null && estudiante != null) {
                    tutorEstudiantes.add(new TutorEstudiante(tutor, estudiante));
                }
            }
        }
        return tutorEstudiantes;
    }

    // --- Método eliminar (Correcto) ---
    public void eliminar(int idTutor, int idEstudiante) throws SQLException {
        String sql = "DELETE FROM TUTOR_ESTUDIANTE WHERE id_tutor = ? AND id_estudiante = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTutor);
            stmt.setInt(2, idEstudiante);
            stmt.executeUpdate();
        }
    }
}