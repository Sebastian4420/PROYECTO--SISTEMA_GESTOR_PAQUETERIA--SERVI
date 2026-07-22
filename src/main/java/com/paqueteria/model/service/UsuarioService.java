package com.paqueteria.model.service;

import com.paqueteria.model.dao.UsuarioDAO;
import com.paqueteria.model.entities.Usuario;

import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean guardarUsuario(Usuario usuario) {

        if (!validarUsuario(usuario)) {
            return false;
        }

        if (usuarioDAO.existeUsername(usuario.getUsername())) {
            System.out.println("Ya existe un usuario con ese username.");
            return false;
        }

        return usuarioDAO.guardar(usuario);
    }

    public boolean actualizarUsuario(Usuario usuario) {

        if (!validarUsuario(usuario)) {
            return false;
        }

        return usuarioDAO.actualizar(usuario);
    }

    public boolean eliminarUsuario(int id) {
        return usuarioDAO.eliminar(id);
    }

    public Usuario buscarUsuario(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioDAO.buscarPorUsername(username);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }

    // ==========================
    // VALIDACIONES
    // ==========================

    private boolean validarUsuario(Usuario usuario) {

        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            System.out.println("El username es obligatorio.");
            return false;
        }

        if (usuario.getUsername().length() < 4) {
            System.out.println("El username debe tener al menos 4 caracteres.");
            return false;
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            System.out.println("La contraseña es obligatoria.");
            return false;
        }

        if (usuario.getPassword().length() < 6) {
            System.out.println("La contraseña debe tener al menos 6 caracteres.");
            return false;
        }

        if (usuario.getIdRol() < 1 || usuario.getIdRol() > 4) {
            System.out.println("Rol no válido.");
            return false;
        }

        return true;
    }

    public Usuario iniciarSesion(String usuario, String password) {
        return usuarioDAO.iniciarSesion(usuario, password);
    }

}