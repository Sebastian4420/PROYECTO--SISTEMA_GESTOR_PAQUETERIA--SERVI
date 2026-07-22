package com.paqueteria.model.service;

import com.paqueteria.model.dao.ClienteDAO;
import com.paqueteria.model.entities.Cliente;

import java.util.List;
import java.util.regex.Pattern;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        clienteDAO = new ClienteDAO();
    }

    public boolean guardarCliente(Cliente cliente) {

        if (!validarCliente(cliente)) {
            return false;
        }

        return clienteDAO.guardar(cliente);
    }

    public boolean actualizarCliente(Cliente cliente) {

        if (!validarCliente(cliente)) {
            return false;
        }

        return clienteDAO.actualizar(cliente);
    }

    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminar(id);
    }

    public Cliente buscarCliente(int id) {
        return clienteDAO.buscarPorId(id);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listar();
    }

    //=========================
    // VALIDACIONES
    //=========================

    private boolean validarCliente(Cliente cliente) {

        if (cliente.getCedula() == null || cliente.getCedula().isBlank()) {
            System.out.println("La cédula es obligatoria.");
            return false;
        }

        if (cliente.getNombres() == null || cliente.getNombres().isBlank()) {
            System.out.println("Los nombres son obligatorios.");
            return false;
        }

        if (cliente.getApellidos() == null || cliente.getApellidos().isBlank()) {
            System.out.println("Los apellidos son obligatorios.");
            return false;
        }

        if (!validarCorreo(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }

        if (!validarTelefono(cliente.getTelefono())) {
            System.out.println("Teléfono inválido.");
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

    public boolean existeCedula(String cedula) {
        return clienteDAO.existeCedula(cedula);
    }

    public boolean existeTelefono(String telefono) {
        return clienteDAO.existeTelefono(telefono);
    }

    public boolean existeCedulaEnOtroCliente(String cedula, int idCliente) {
        return clienteDAO.existeCedulaEnOtroCliente(cedula, idCliente);
    }

    public boolean existeTelefonoEnOtroCliente(String telefono, int idCliente) {
        return clienteDAO.existeTelefonoEnOtroCliente(telefono, idCliente);
    }

}