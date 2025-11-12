package org.sicali;

import io.javalin.Javalin;
import org.sicali.config.configModule;
import org.sicali.routes.*;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create()
                .start(7000);

        System.out.println("✓ Servidor SICALI iniciado en http://localhost:7000");


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
