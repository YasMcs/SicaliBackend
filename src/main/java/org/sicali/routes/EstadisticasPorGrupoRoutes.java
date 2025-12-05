package org.sicali.routes;

import org.sicali.controllers.EstadisticasPorGrupoController;

import io.javalin.Javalin;

public class EstadisticasPorGrupoRoutes {

    public static void register(Javalin app) {
        app.get("/api/estadisticas/grupo/{idGrupo}", EstadisticasPorGrupoController::obtenerPorGrupo);
    }
}
