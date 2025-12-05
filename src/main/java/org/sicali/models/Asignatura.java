package org.sicali.models;

public class Asignatura {
    private int idAsignatura;
    private String nombre;
    private String codigo;
    private String descripcion;

    public Asignatura(){}

    public Asignatura(int idAsignatura, String nombre, String codigo, String descripcion) {
        this.idAsignatura = idAsignatura;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}