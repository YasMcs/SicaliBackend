package org.sicali.config;

import org.sicali.routes.AsignaturaRoutes;
import org.sicali.routes.AsistenciaRoutes;
import org.sicali.routes.CicloRoutes;
import org.sicali.routes.EstadisticasDocenteRoutes;
import org.sicali.routes.EstadisticasPorEstudianteRoutes;
import org.sicali.routes.EstadisticasPorGrupoRoutes;
import org.sicali.routes.EstadisticasTutorRoutes;
import org.sicali.routes.EstudianteGrupoRoutes;
import org.sicali.routes.GrupoAsignaturaRoutes;
import org.sicali.routes.GrupoRoutes;
import org.sicali.routes.PeriodoRoutes;
import org.sicali.routes.TutorEstudianteRoutes;
import org.sicali.routes.UsuarioRoutes;

public class configModule {

    public static AsignaturaRoutes initAsignaturaRoutes() {
        return new AsignaturaRoutes();
    }

    public static AsistenciaRoutes initAsistenciaRoutes() {
        return new AsistenciaRoutes();
    }

    public static UsuarioRoutes initUsuarioRoutes() {
        return new UsuarioRoutes();
    }

    public static CicloRoutes initCicloRoutes() {
        return new CicloRoutes();
    }

    public static GrupoRoutes initGrupoRoutes() {
        return new GrupoRoutes();
    }

    public static EstudianteGrupoRoutes initEstudianteGrupoRoutes() {
        return new EstudianteGrupoRoutes();
    }

    public static GrupoAsignaturaRoutes initGrupoAsignaturaRoutes() {
        return new GrupoAsignaturaRoutes();
    }

    public static PeriodoRoutes initPeriodoRoutes() {
        return new PeriodoRoutes();
    }

    public static TutorEstudianteRoutes initTutorEstudianteRoutes() {
        return new TutorEstudianteRoutes();
    }

    // --- Módulos de Estadísticas ---
    public static EstadisticasDocenteRoutes initEstadisticasDocenteRoutes() {
        return new EstadisticasDocenteRoutes();
    }

    public static EstadisticasPorEstudianteRoutes initEstadisticasPorEstudianteRoutes() {
        return new EstadisticasPorEstudianteRoutes();
    }

    public static EstadisticasPorGrupoRoutes initEstadisticasPorGrupoRoutes() {
        return new EstadisticasPorGrupoRoutes();
    }

    public static EstadisticasTutorRoutes initEstadisticasTutorRoutes() {
        return new EstadisticasTutorRoutes();
    }
}
