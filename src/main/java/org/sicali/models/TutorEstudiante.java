package org.sicali.models;

public class TutorEstudiante {
    private Usuario idTutor;
    private Usuario idEstudiante;

    public TutorEstudiante(){}
    public TutorEstudiante(Usuario idTutor, Usuario idEstudiante) {
        this.idTutor = idTutor;
        this.idEstudiante = idEstudiante;
    }

    public Usuario getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Usuario idTutor) {
        this.idTutor = idTutor;
    }

    public Usuario getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Usuario idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}