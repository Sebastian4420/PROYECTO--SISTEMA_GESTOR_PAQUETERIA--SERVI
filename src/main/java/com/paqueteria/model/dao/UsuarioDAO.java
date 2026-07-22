package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.Usuario;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Crud<Usuario> {

    @Override
    public boolean guardar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (username, password, id_rol, estado)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setInt(3, usuario.getIdRol());
            ps.setBoolean(4, usuario.isEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar el usuario.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET username = ?, password = ?, id_rol = ?, estado = ?
                WHERE id_usuario = ?
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setInt(3, usuario.getIdRol());
            ps.setBoolean(4, usuario.isEstado());
            ps.setInt(5, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setEstado(rs.getBoolean("estado"));

                return usuario;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios ORDER BY id_usuario";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setEstado(rs.getBoolean("estado"));

                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los usuarios.");
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // MÉTODOS ADICIONALES
    // ==========================

    public Usuario buscarPorUsername(String username) {

        String sql = "SELECT * FROM usuarios WHERE username = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setEstado(rs.getBoolean("estado"));

                return usuario;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario por username.");
            e.printStackTrace();
        }

        return null;
    }

    public boolean existeUsername(String username) {

        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar el username.");
            e.printStackTrace();
        }

        return false;
    }

    public Usuario iniciarSesion(String username, String password) {

        String sql = """
            SELECT *
            FROM usuarios
            WHERE username = ?
            AND password = ?
            """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirUsuario(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    private Usuario construirUsuario(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setIdRol(rs.getInt("id_rol"));
        usuario.setEstado(rs.getBoolean("estado"));

        return usuario;
    }

}