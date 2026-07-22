package com.paqueteria.model.service;

import com.paqueteria.model.dao.RepartidorDAO;
import com.paqueteria.model.entities.Repartidor;

import java.util.List;
import java.util.regex.Pattern;

public class RepartidorService {

    private final RepartidorDAO repartidorDAO;

    public RepartidorService() {
        repartidorDAO = new RepartidorDAO();
    }

    public boolean guardarRepartidor(Repartidor repartidor) {

        if (!validarRepartidor(repartidor)) {
            return false;
        }

        return repartidorDAO.guardar(repartidor);
    }

    public boolean actualizarRepartidor(Repartidor repartidor) {

        if (!validarRepartidor(repartidor)) {
            return false;
        }

        return repartidorDAO.actualizar(repartidor);
    }

    public boolean eliminarRepartidor(int id) {
        return repartidorDAO.eliminar(id);
    }

    public Repartidor buscarRepartidor(int id) {
        return repartidorDAO.buscarPorId(id);
    }

    public List<Repartidor> listarRepartidores() {
        return repartidorDAO.listar();
    }

    //=========================
    // VALIDACIONES
    //=========================

    private boolean validarRepartidor(Repartidor repartidor) {

        if (repartidor.getCedula() == null || repartidor.getCedula().isBlank()) {
            System.out.println("La cédula es obligatoria.");
            return false;
        }

        if (repartidor.getNombres() == null || repartidor.getNombres().isBlank()) {
            System.out.println("Los nombres son obligatorios.");
            return false;
        }

        if (repartidor.getApellidos() == null || repartidor.getApellidos().isBlank()) {
            System.out.println("Los apellidos son obligatorios.");
            return false;
        }

        if (!validarCorreo(repartidor.getCorreo())) {
            System.out.println("Correo inválido.");
            return false;
        }

        if (!validarTelefono(repartidor.getTelefono())) {
            System.out.println("Teléfono inválido.");
            return false;
        }

        if (repartidor.getVehiculo() == null || repartidor.getVehiculo().isBlank()) {
            System.out.println("Debe indicar el vehículo.");
            return false;
        }

        if (repartidor.getPlaca() == null || repartidor.getPlaca().isBlank()) {
            System.out.println("Debe indicar la placa.");
            return false;
        }

        return true;
    }

    private boolean validarCorreo(String correo) {

        if (correo == null) {
            return false;
        }

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        return Pattern.matches(regex, correo);
    }

    private boolean validarTelefono(String telefono) {

        if (telefono == null) {
            return false;
        }

        return telefono.matches("\\d{10}");
    }

}

