package org.sicali.routes;

import org.sicali.controllers.EstudianteGrupoController;

import io.javalin.Javalin;

public class EstudianteGrupoRoutes {

    public static void register(Javalin app) {
        // Rutas registradas con las rutas estáticas/ específicas primero
        app.get("/api/estudiante-grupos", EstudianteGrupoController::obtenerTodos);
        // rutas estáticas primero para evitar conflictos con la ruta dinámica
        app.get("/api/estudiante-grupos/grupo-asignatura/{idGrupoAsignatura}", EstudianteGrupoController::obtenerEstudiantesPorGrupoAsignatura);
        app.get("/api/estudiante-grupos/estudiante/{idEstudiante}", EstudianteGrupoController::obtenerGruposPorEstudiante);
        app.get("/api/estudiante-grupos/estudiante/{idEstudiante}/promedio", EstudianteGrupoController::calcularPromedioEstudiante);
        // ruta dinámica que captura dos segmentos (debe ir después de las estáticas)
        app.get("/api/estudiante-grupos/{idGrupoAsignatura}/{idEstudiante}", EstudianteGrupoController::obtenerPorIds);
        app.post("/api/estudiante-grupos", EstudianteGrupoController::crear);
        app.put("/api/estudiante-grupos/{idGrupoAsignatura}/{idEstudiante}/calificacion", EstudianteGrupoController::actualizarCalificacion);
        app.delete("/api/estudiante-grupos/{idGrupoAsignatura}/{idEstudiante}", EstudianteGrupoController::eliminar);
    }
}
