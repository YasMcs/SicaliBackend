package org.sicali.models;

import java.time.LocalDate;

public class Ciclo {
    private int idCiclo;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Constructor por defecto requerido por Jackson
    public Ciclo() {}


    // Allow deserialization from a numeric id (e.g. "idCiclo": 1)
    public Ciclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }
    public Ciclo(int idCiclo, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idCiclo = idCiclo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdCiclo() { return idCiclo; }
    public void setIdCiclo(int idCiclo) { this.idCiclo = idCiclo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}