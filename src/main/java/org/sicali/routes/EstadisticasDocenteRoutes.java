package org.sicali.routes;

import org.sicali.controllers.EstadisticasDocenteController;

import io.javalin.Javalin;

public class EstadisticasDocenteRoutes {

    public static void register(Javalin app) {
        app.get("/api/estadisticas/docente/{idDocente}", EstadisticasDocenteController::obtenerPorDocente);
    }
}
