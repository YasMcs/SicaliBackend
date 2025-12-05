package org.sicali;

import org.sicali.config.configModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import io.javalin.plugin.bundled.CorsPlugin;

public class Main {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Javalin app = Javalin.create(config -> {

            config.jsonMapper(new JavalinJackson(mapper, true));

            // ===== CORS UNIVERSAL =====
            config.registerPlugin(new CorsPlugin(cors -> {
                cors.addRule(rule -> {
                    rule.anyHost();
                    // O restringir: rule.allowHost("http://localhost:4200");
                });
            }));
            // ===========================

        }).start(7001);

        System.out.println("✓ Servidor SICALI iniciado en http://localhost:8000");

        configModule.initUsuarioRoutes().register(app);
        configModule.initCicloRoutes().register(app);
        configModule.initPeriodoRoutes().register(app);
        configModule.initAsignaturaRoutes().register(app);
        configModule.initGrupoRoutes().register(app);
        configModule.initGrupoAsignaturaRoutes().register(app);
        configModule.initEstudianteGrupoRoutes().register(app);
        configModule.initAsistenciaRoutes().register(app);
        configModule.initTutorEstudianteRoutes().register(app);

        configModule.initEstadisticasDocenteRoutes().register(app);
        configModule.initEstadisticasPorEstudianteRoutes().register(app);
        configModule.initEstadisticasPorGrupoRoutes().register(app);
        configModule.initEstadisticasTutorRoutes().register(app);
    }
}
