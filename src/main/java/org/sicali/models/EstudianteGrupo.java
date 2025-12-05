package org.sicali.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EstudianteGrupo {
    private Usuario estudiante;
    private Grupo grupo;
    private LocalDate fechaInscripcion;
    private EstadoInscripcion estado;
    private LocalDateTime created_at;

    public EstudianteGrupo() {}

    public EstudianteGrupo(Usuario estudiante, Grupo grupo, LocalDate fechaInscripcion, EstadoInscripcion estado, LocalDateTime created_at) {
        this.estudiante = estudiante;
        this.grupo = grupo;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.created_at = created_at;
    }

    public Usuario getEstudiante() { return estudiante; }
    public void setEstudiante(Usuario estudiante) { this.estudiante = estudiante; }

    public Grupo getGrupo() { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }

    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public EstadoInscripcion getEstado() { return estado; }
    public void setEstado(EstadoInscripcion estado) { this.estado = estado; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}