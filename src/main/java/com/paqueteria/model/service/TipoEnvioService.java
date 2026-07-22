package com.paqueteria.model.service;

import com.paqueteria.model.dao.TipoEnvioDAO;
import com.paqueteria.model.entities.TipoEnvio;

import java.util.List;

public class TipoEnvioService {

    private final TipoEnvioDAO tipoDAO;

    public TipoEnvioService() {
        tipoDAO = new TipoEnvioDAO();
    }

    public boolean guardarTipo(TipoEnvio tipo) {

        if (!validarTipo(tipo)) {
            return false;
        }

        if (tipoDAO.existeNombre(tipo.getNombre())) {
            System.out.println("Ya existe un tipo de envío con ese nombre.");
            return false;
        }

        return tipoDAO.guardar(tipo);
    }

    public boolean actualizarTipo(TipoEnvio tipo) {

        if (!validarTipo(tipo)) {
            return false;
        }

        return tipoDAO.actualizar(tipo);
    }

    public boolean eliminarTipo(int id) {
        return tipoDAO.eliminar(id);
    }

    public TipoEnvio buscarTipo(int id) {
        return tipoDAO.buscarPorId(id);
    }

    public List<TipoEnvio> listarTipos() {
        return tipoDAO.listar();
    }

    private boolean validarTipo(TipoEnvio tipo) {

        if (tipo.getNombre() == null || tipo.getNombre().isBlank()) {
            System.out.println("El nombre es obligatorio.");
            return false;
        }

        if (tipo.getDescripcion() == null || tipo.getDescripcion().isBlank()) {
            System.out.println("La descripción es obligatoria.");
            return false;
        }

        return true;
    }
}