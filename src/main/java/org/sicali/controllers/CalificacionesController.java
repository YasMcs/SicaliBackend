package org.sicali.controllers;

import org.sicali.config.DatabaseConfig;
import org.sicali.models.Calificacion;
import org.sicali.models.Ciclo;
import org.sicali.services.CalificacionesService;
import org.sicali.services.CicloService;

import javax.naming.Context;
import java.sql.Connection;
import java.util.List;

public class CalificacionesController {
    public static void ObtenerTodos (Context ctx){
        try (Connection conn = DatabaseConfig.getConnection()) {
            CalificacionesService service = new CalificacionesService(conn);
            List<Ciclo> ciclos = service.obtenerTodosCiclos();
            ctx.json(ciclos);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }
}
