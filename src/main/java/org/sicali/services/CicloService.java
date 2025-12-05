package org.sicali.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.sicali.models.Ciclo;
import org.sicali.repositories.CicloRepository;

public class CicloService {
    private CicloRepository cicloRepository;

    public CicloService(Connection connection) {
        this.cicloRepository = new CicloRepository(connection);
    }

    public Ciclo crearCiclo(Ciclo ciclo) throws SQLException {
        validarCiclo(ciclo);
        return cicloRepository.crear(ciclo);
    }

    public Ciclo obtenerCicloPorId(int id) throws SQLException {
        Ciclo ciclo = cicloRepository.obtenerPorId(id);
        if (ciclo == null) {
            throw new SQLException("Ciclo no encontrado con id: " + id);
        }
        return ciclo;
    }

    public List<Ciclo> obtenerTodosCiclos() throws SQLException {
        return cicloRepository.obtenerTodos();
    }

    public void actualizarCiclo(Ciclo ciclo) throws SQLException {
        validarCiclo(ciclo);
        cicloRepository.actualizar(ciclo);
    }

    public void eliminarCiclo(int id) throws SQLException {
        cicloRepository.eliminar(id);
    }

    public Ciclo obtenerCicloActivo() throws SQLException {
        LocalDate hoy = LocalDate.now();
        List<Ciclo> ciclos = cicloRepository.obtenerTodos();
        for (Ciclo ciclo : ciclos) {
            if (ciclo.getFechaInicio() != null && ciclo.getFechaFin() != null &&
                    ( !hoy.isBefore(ciclo.getFechaInicio()) ) && ( !hoy.isAfter(ciclo.getFechaFin()) )) {
                return ciclo;
            }
        }
        return null;
    }

    private void validarCiclo(Ciclo ciclo) throws SQLException {
        if (ciclo.getNombre() == null || ciclo.getNombre().trim().isEmpty()) {
            throw new SQLException("El nombre del ciclo es requerido");
        }
        if (ciclo.getFechaInicio() == null) {
            throw new SQLException("La fecha de inicio es requerida");
        }
        if (ciclo.getFechaFin() == null) {
            throw new SQLException("La fecha de fin es requerida");
        }
        if (ciclo.getFechaInicio().isAfter(ciclo.getFechaFin())) {
            throw new SQLException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
    }
}