package org.sicali.models;

public class Periodo {
    private int idPeriodo;
    private Ciclo idCiclo;
    private int numeroPeriodo;
    private String nombre;
    private java.time.LocalDate fechaInicio;
    private java.time.LocalDate fechaFin;

    // No-arg constructor required by Jackson
    public Periodo() {}

    public Periodo(int idPeriodo, Ciclo idCiclo) {
        this.idPeriodo = idPeriodo;
        this.idCiclo = idCiclo;
    }

    public Periodo(int idPeriodo, Ciclo idCiclo, int numeroPeriodo, String nombre, java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin) {
        this.idPeriodo = idPeriodo;
        this.idCiclo = idCiclo;
        this.numeroPeriodo = numeroPeriodo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Ciclo getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(Ciclo idCiclo) {
        this.idCiclo = idCiclo;
    }

    public int getNumeroPeriodo() { return numeroPeriodo; }
    public void setNumeroPeriodo(int numeroPeriodo) { this.numeroPeriodo = numeroPeriodo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public java.time.LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(java.time.LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public java.time.LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(java.time.LocalDate fechaFin) { this.fechaFin = fechaFin; }

    // Convenience setter to accept numeric idCiclo in JSON
    public void setIdCiclo(int idCiclo) {
        if (this.idCiclo == null) this.idCiclo = new Ciclo();
        this.idCiclo.setIdCiclo(idCiclo);
    }
}