package org.sicali.routes;

import org.sicali.controllers.EstadisticasPorEstudianteController;

import io.javalin.Javalin;

public class EstadisticasPorEstudianteRoutes {

    public static void register(Javalin app) {
        app.get("/api/estadisticas/estudiante/{idEstudiante}", EstadisticasPorEstudianteController::obtenerPorEstudiante);
    }
}
