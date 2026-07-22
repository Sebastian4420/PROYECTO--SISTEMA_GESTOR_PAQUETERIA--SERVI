package com.paqueteria.model.service;

import com.paqueteria.model.dao.EstadoPaqueteDAO;
import com.paqueteria.model.entities.EstadoPaquete;

import java.util.List;

public class EstadoPaqueteService {

    private final EstadoPaqueteDAO estadoDAO;

    public EstadoPaqueteService() {
        estadoDAO = new EstadoPaqueteDAO();
    }

    public boolean guardarEstado(EstadoPaquete estado) {

        if (!validarEstado(estado)) {
            return false;
        }

        if (estadoDAO.existeNombre(estado.getNombre())) {
            System.out.println("Ya existe un estado con ese nombre.");
            return false;
        }

        return estadoDAO.guardar(estado);
    }

    public boolean actualizarEstado(EstadoPaquete estado) {

        if (!validarEstado(estado)) {
            return false;
        }

        return estadoDAO.actualizar(estado);
    }

    public boolean eliminarEstado(int id) {
        return estadoDAO.eliminar(id);
    }

    public EstadoPaquete buscarEstado(int id) {
        return estadoDAO.buscarPorId(id);
    }

    public List<EstadoPaquete> listarEstados() {
        return estadoDAO.listar();
    }

    private boolean validarEstado(EstadoPaquete estado) {

        if (estado.getNombre() == null || estado.getNombre().isBlank()) {
            System.out.println("El nombre del estado es obligatorio.");
            return false;
        }

        if (estado.getDescripcion() == null || estado.getDescripcion().isBlank()) {
            System.out.println("La descripción es obligatoria.");
            return false;
        }

        return true;
    }

}