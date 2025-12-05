package org.sicali.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Grupo {
    private int idGrupo;
    private String nombre;
    private int grado;
    private Periodo idPeriodo;
    private Usuario idDocente;
    private Integer capacidad;

    public Grupo(){}

    public Grupo (int id) {
        this.idGrupo = id;
    }

    public Grupo(int idGrupo, String nombre, int grado, Periodo idPeriodo, Usuario idDocente) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.grado = grado;
        this.idPeriodo = idPeriodo;
        this.idDocente = idDocente;
    }

    

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getGrado() { return grado; }
    public void setGrado(int grado) { this.grado = grado; }

    public Periodo getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(Periodo idPeriodo) { this.idPeriodo = idPeriodo; }

    // Accept integer id for JSON forms that provide only the id
    public void setIdPeriodo(int idPeriodo) {
        Periodo p = new Periodo();
        p.setIdPeriodo(idPeriodo);
        this.idPeriodo = p;
    }

    public Usuario getIdDocente() { return idDocente; }
    public void setIdDocente(Usuario idDocente) { this.idDocente = idDocente; }

    // Accept integer id for JSON forms that provide only the docente id
    public void setIdDocente(int idDocente) {
        Usuario u = new Usuario();
        u.setId_usuario(idDocente);
        this.idDocente = u;
    }
}
