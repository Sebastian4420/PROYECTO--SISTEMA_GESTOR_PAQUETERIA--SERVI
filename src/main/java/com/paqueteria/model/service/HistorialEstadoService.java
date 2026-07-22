package com.paqueteria.model.service;

import com.paqueteria.model.dao.HistorialEstadoDAO;
import com.paqueteria.model.entities.HistorialEstado;

import java.util.List;

public class HistorialEstadoService {

    private final HistorialEstadoDAO dao;

    public HistorialEstadoService() {
        dao = new HistorialEstadoDAO();
    }

    public boolean guardarHistorial(HistorialEstado historial) {
        return dao.guardar(historial);
    }

    public boolean actualizarHistorial(HistorialEstado historial) {
        return dao.actualizar(historial);
    }

    public boolean eliminarHistorial(int id) {
        return dao.eliminar(id);
    }

    public HistorialEstado buscarHistorial(int id) {
        return dao.buscarPorId(id);
    }

    public List<HistorialEstado> listarHistorial() {
        return dao.listar();
    }

    public List<HistorialEstado> listarPorPaquete(int idPaquete) {
        return dao.listarPorPaquete(idPaquete);
    }

}
