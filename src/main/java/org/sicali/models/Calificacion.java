package org.sicali.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Calificacion {
    private int idCalificacion;
    private Usuario estudiante;
    private GrupoAsignatura grupoAsignatura;
    private Periodo periodo;
    private BigDecimal valor;
    private String estado; // Pendiente,Capturada,Publicada,Modificada,Definitiva
    private Usuario docente;
    private LocalDateTime fecha_registro;
    private LocalDateTime fecha_modificacion;

    public Calificacion() {}

    public Calificacion(int idCalificacion, Usuario estudiante, GrupoAsignatura grupoAsignatura, Periodo periodo, BigDecimal valor, String estado, Usuario docente, LocalDateTime fecha_registro, LocalDateTime fecha_modificacion) {
        this.idCalificacion = idCalificacion;
        this.estudiante = estudiante;
        this.grupoAsignatura = grupoAsignatura;
        this.periodo = periodo;
        this.valor = valor;
        this.estado = estado;
        this.docente = docente;
        this.fecha_registro = fecha_registro;
        this.fecha_modificacion = fecha_modificacion;
    }

    public int getIdCalificacion() { return idCalificacion; }
    public void setIdCalificacion(int idCalificacion) { this.idCalificacion = idCalificacion; }

    public Usuario getEstudiante() { return estudiante; }
    public void setEstudiante(Usuario estudiante) { this.estudiante = estudiante; }

    public GrupoAsignatura getGrupoAsignatura() { return grupoAsignatura; }
    public void setGrupoAsignatura(GrupoAsignatura grupoAsignatura) { this.grupoAsignatura = grupoAsignatura; }

    public Periodo getPeriodo() { return periodo; }
    public void setPeriodo(Periodo periodo) { this.periodo = periodo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuario getDocente() { return docente; }
    public void setDocente(Usuario docente) { this.docente = docente; }

    public LocalDateTime getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(LocalDateTime fecha_registro) { this.fecha_registro = fecha_registro; }

    public LocalDateTime getFecha_modificacion() { return fecha_modificacion; }
    public void setFecha_modificacion(LocalDateTime fecha_modificacion) { this.fecha_modificacion = fecha_modificacion; }
}
