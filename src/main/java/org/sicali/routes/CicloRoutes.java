package org.sicali.routes;

import org.sicali.controllers.CicloController;

import io.javalin.Javalin;

public class CicloRoutes {

    public static void register(Javalin app) {
        app.get("/api/ciclos", CicloController::obtenerTodos);
        // Registrar la ruta literal "/activo" antes de la ruta dinámica "{id}" para evitar conflicto
        app.get("/api/ciclos/activo", CicloController::obtenerActivo);
        app.get("/api/ciclos/{id}", CicloController::obtenerPorId);
        app.post("/api/ciclos", CicloController::crear);
        app.put("/api/ciclos/{id}", CicloController::actualizar);
        app.delete("/api/ciclos/{id}", CicloController::eliminar);
    }
}