package org.sicali.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Asistencia {
    private int idAsistencia;
    private Usuario idEstudiante;
    private LocalDate fecha;
    private Grupo idGrupo;
    private EstadoAsistencia estado;
    private LocalDateTime fecha_registro;


    public Asistencia(){}

    public Asistencia(int idAsistencia, Usuario idEstudiante, LocalDate fecha, Grupo idGrupo, EstadoAsistencia estado, LocalDateTime fecha_registro) {
        this.idAsistencia = idAsistencia;
        this.idEstudiante = idEstudiante;
        this.fecha = fecha;
        this.idGrupo = idGrupo;
        this.estado = estado;
        this.fecha_registro = fecha_registro;
    }

    public int getIdAsistencia() { return idAsistencia; }
    public void setIdAsistencia(int idAsistencia) { this.idAsistencia = idAsistencia; }

    public Usuario getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Usuario idEstudiante) { this.idEstudiante = idEstudiante; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Grupo getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Grupo idGrupo) { this.idGrupo = idGrupo; }

    public EstadoAsistencia getEstado() { return estado; }
    public void setEstado(EstadoAsistencia estado) { this.estado = estado; }

    public LocalDateTime getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(LocalDateTime fecha_registro) { this.fecha_registro = fecha_registro; }
}