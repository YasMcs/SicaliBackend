package org.sicali.controllers;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.sicali.config.DatabaseConfig;
import org.sicali.models.EstudianteGrupo;
import org.sicali.services.EstudianteGrupoService;

import io.javalin.http.Context;

public class EstudianteGrupoController {

    public static void obtenerTodos(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            List<EstudianteGrupo> estudiantesGrupos = service.obtenerTodosEstudiantesGrupos();
            ctx.json(estudiantesGrupos);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void obtenerPorIds(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idGrupoAsignatura = Integer.parseInt(ctx.pathParam("idGrupoAsignatura"));
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            EstudianteGrupo estudianteGrupo = service.obtenerEstudianteGrupoPorIds(idGrupoAsignatura, idEstudiante);
            ctx.json(estudianteGrupo);
        } catch (Exception e) {
            ctx.status(404).json("Error: " + e.getMessage());
        }
    }

    public static void crear(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            EstudianteGrupo estudianteGrupo = ctx.bodyAsClass(EstudianteGrupo.class);
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            EstudianteGrupo nuevoEstudianteGrupo = service.crearEstudianteGrupo(estudianteGrupo);
            ctx.status(201).json(nuevoEstudianteGrupo);
        } catch (Exception e) {
            ctx.status(400).json("Error: " + e.getMessage());
        }
    }

    public static void actualizarCalificacion(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idGrupoAsignatura = Integer.parseInt(ctx.pathParam("idGrupoAsignatura"));
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            Map<String, Object> body = ctx.bodyAsClass(Map.class);
            int nuevaCalificacion = ((Number) body.get("calificacion")).intValue();
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            service.actualizarCalificacion(idGrupoAsignatura, idEstudiante, nuevaCalificacion);
            ctx.json("Calificación actualizada");
        } catch (Exception e) {
            ctx.status(400).json("Error: " + e.getMessage());
        }
    }

    public static void eliminar(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idGrupoAsignatura = Integer.parseInt(ctx.pathParam("idGrupoAsignatura"));
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            service.eliminarEstudianteGrupo(idGrupoAsignatura, idEstudiante);
            ctx.json("Estudiante-Grupo eliminado");
        } catch (Exception e) {
            ctx.status(400).json("Error: " + e.getMessage());
        }
    }

    public static void obtenerEstudiantesPorGrupoAsignatura(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idGrupoAsignatura = Integer.parseInt(ctx.pathParam("idGrupoAsignatura"));
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            // La implementación actual usa 'grupo' (id_grupo) en lugar de grupo-asignatura.
            List<EstudianteGrupo> estudiantesGrupos = service.obtenerEstudiantesPorGrupo(idGrupoAsignatura);
            ctx.json(estudiantesGrupos);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void obtenerGruposPorEstudiante(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            List<EstudianteGrupo> estudiantesGrupos = service.obtenerGruposPorEstudiante(idEstudiante);
            ctx.json(estudiantesGrupos);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void calcularPromedioEstudiante(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            EstudianteGrupoService service = new EstudianteGrupoService(conn);
            double promedio = service.calcularPromedioEstudiante(idEstudiante);
            ctx.json(Map.of("promedio", promedio));
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }
}