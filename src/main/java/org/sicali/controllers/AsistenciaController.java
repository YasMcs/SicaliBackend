package org.sicali.controllers;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.sicali.config.DatabaseConfig;
import org.sicali.models.Asistencia;
import org.sicali.services.AsistenciaService;

import io.javalin.http.Context;

public class AsistenciaController {

    public static void obtenerTodos(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            AsistenciaService service = new AsistenciaService(conn);
            List<Asistencia> asistencias = service.obtenerTodasAsistencias();
            ctx.json(asistencias);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void obtenerPorId(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            AsistenciaService service = new AsistenciaService(conn);
            Asistencia asistencia = service.obtenerAsistenciaPorId(id);
            ctx.json(asistencia);
        } catch (Exception e) {
            ctx.status(404).json("Error: " + e.getMessage());
        }
    }

    public static void crear(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            Asistencia asistencia = ctx.bodyAsClass(Asistencia.class);
            AsistenciaService service = new AsistenciaService(conn);
            Asistencia nuevaAsistencia = service.crearAsistencia(asistencia);
            ctx.status(201).json(nuevaAsistencia);
        } catch (Exception e) {
            ctx.status(400).json("Error: " + e.getMessage());
        }
    }

    public static void actualizar(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Asistencia asistencia = ctx.bodyAsClass(Asistencia.class);
            asistencia.setIdAsistencia(id);
            AsistenciaService service = new AsistenciaService(conn);
            service.actualizarAsistencia(asistencia);
            ctx.json("Asistencia actualizada");
        } catch (Exception e) {
            ctx.status(400).json("Error: " + e.getMessage());
        }
    }


    public static void obtenerPorEstudiante(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            AsistenciaService service = new AsistenciaService(conn);
            List<Asistencia> asistencias = service.obtenerAsistenciasPorEstudiante(idEstudiante);
            ctx.json(asistencias);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void obtenerPorGrupo(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));
            AsistenciaService service = new AsistenciaService(conn);
            List<Asistencia> asistencias = service.obtenerAsistenciasPorGrupo(idGrupo);
            ctx.json(asistencias);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void obtenerPorFecha(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String fechaStr = ctx.pathParam("fecha");
            // Esperamos formato ISO date: yyyy-MM-dd
            LocalDate fecha = LocalDate.parse(fechaStr);
            AsistenciaService service = new AsistenciaService(conn);
            List<Asistencia> asistencias = service.obtenerAsistenciasPorFecha(fecha);
            ctx.json(asistencias);
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }

    public static void calcularPorcentajeAsistencia(Context ctx) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            int idEstudiante = Integer.parseInt(ctx.pathParam("idEstudiante"));
            int idGrupo = Integer.parseInt(ctx.pathParam("idGrupo"));
            AsistenciaService service = new AsistenciaService(conn);
            double porcentaje = service.calcularPorcentajeAsistencia(idEstudiante, idGrupo);
            ctx.json(Map.of("porcentajeAsistencia", porcentaje));
        } catch (Exception e) {
            ctx.status(500).json("Error: " + e.getMessage());
        }
    }
}