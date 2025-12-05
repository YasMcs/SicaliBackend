package org.sicali.routes;

import io.javalin.Javalin;
import org.sicali.controllers.CalificacionesController;

public class CalificacionesRoutes {
    public static void Register (Javalin app) {
        app.get("api/calificaciones", CalificacionesController::ObtenerTodos);
    }
}
