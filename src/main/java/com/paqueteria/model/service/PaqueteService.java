package com.paqueteria.model.service;

import com.paqueteria.model.dao.PaqueteDAO;
import com.paqueteria.model.entities.Paquete;

import java.math.BigDecimal;
import java.util.List;

public class PaqueteService {

    private final PaqueteDAO paqueteDAO;

    public PaqueteService() {
        paqueteDAO = new PaqueteDAO();
    }

    //=========================
    // CRUD
    //=========================

    public boolean guardarPaquete(Paquete paquete) {

        if (!validarPaquete(paquete)) {
            return false;
        }

        if (paqueteDAO.buscarPorCodigoSeguimiento(
                paquete.getCodigoSeguimiento()) != null) {

            System.out.println("Ya existe un paquete con ese código.");

            return false;
        }

        return paqueteDAO.guardar(paquete);

    }

    public boolean actualizarPaquete(Paquete paquete) {

        if (!validarPaquete(paquete)) {
            return false;
        }

        return paqueteDAO.actualizar(paquete);

    }

    public boolean eliminarPaquete(int id) {

        return paqueteDAO.eliminar(id);

    }

    public Paquete buscarPaquete(int id) {

        return paqueteDAO.buscarPorId(id);

    }

    public Paquete buscarPorCodigo(String codigo) {

        return paqueteDAO.buscarPorCodigoSeguimiento(codigo);

    }

    public List<Paquete> listarPaquetes() {

        return paqueteDAO.listar();

    }

    //=========================
    // VALIDACIONES
    //=========================

    private boolean validarPaquete(Paquete paquete) {

        if (paquete.getCodigoSeguimiento() == null
                || paquete.getCodigoSeguimiento().isBlank()) {

            System.out.println("Debe ingresar un código.");

            return false;

        }

        if (paquete.getCliente() == null) {

            System.out.println("Debe seleccionar un cliente.");

            return false;

        }

        if (paquete.getRepartidor() == null) {

            System.out.println("Debe seleccionar un repartidor.");

            return false;

        }

        if (paquete.getTipoEnvio() == null) {

            System.out.println("Debe seleccionar un tipo de envío.");

            return false;

        }

        if (paquete.getEstado() == null) {

            System.out.println("Debe seleccionar un estado.");

            return false;

        }

        if (paquete.getUsuario() == null) {

            System.out.println("Debe existir un usuario que registre el paquete.");

            return false;

        }

        if (paquete.getNombreDestinatario() == null
                || paquete.getNombreDestinatario().isBlank()) {

            System.out.println("Debe ingresar el destinatario.");

            return false;

        }

        if (paquete.getTelefonoDestinatario() == null
                || paquete.getTelefonoDestinatario().isBlank()) {

            System.out.println("Debe ingresar el teléfono del destinatario.");

            return false;

        }

        if (paquete.getOrigen() == null
                || paquete.getOrigen().isBlank()) {

            System.out.println("Debe ingresar el origen.");

            return false;

        }

        if (paquete.getDestino() == null
                || paquete.getDestino().isBlank()) {

            System.out.println("Debe ingresar el destino.");

            return false;

        }

        if (paquete.getPeso() == null
                || paquete.getPeso().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("El peso debe ser mayor que cero.");

            return false;

        }

        if (paquete.getPrecio() == null
                || paquete.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("El precio debe ser mayor que cero.");

            return false;

        }

        return true;

    }


}