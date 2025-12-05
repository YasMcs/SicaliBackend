package org.sicali.routes;

import org.sicali.controllers.UsuarioController;

import io.javalin.Javalin;

public class UsuarioRoutes {
    public static void registerRoutes(Javalin app) {

        app.get("/api/usuarios", UsuarioController::obtenerTodos);
        app.get("/api/usuarios/{id}", UsuarioController::obtenerPorId);
        app.post("/api/usuarios", UsuarioController::crear);
        app.put("/api/usuarios/{id}", UsuarioController::actualizar);
        app.delete("/api/usuarios/{id}", UsuarioController::eliminar);
        app.get("/api/usuarios/rol/{rol}", UsuarioController::obtenerPorRol);
    }

    public static void register(Javalin app) {
        registerRoutes(app);
    }
}