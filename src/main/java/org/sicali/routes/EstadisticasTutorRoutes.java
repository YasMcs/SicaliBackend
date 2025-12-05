package org.sicali.routes;

import org.sicali.controllers.EstadisticasTutorController;

import io.javalin.Javalin;

public class EstadisticasTutorRoutes {

    public static void register(Javalin app) {
        app.get("/api/estadisticas/tutor/{idTutor}", EstadisticasTutorController::obtenerPorTutor);
    }
}
