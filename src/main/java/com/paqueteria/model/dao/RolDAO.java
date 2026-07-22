package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.Rol;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO implements Crud<Rol> {

    @Override
    public boolean guardar(Rol rol) {

        String sql = "INSERT INTO roles(nombre, descripcion) VALUES (?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean actualizar(Rol rol) {

        String sql = "UPDATE roles SET nombre=?, descripcion=? WHERE id_rol=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setInt(3, rol.getIdRol());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM roles WHERE id_rol=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Rol buscarPorId(int id) {

        String sql = "SELECT * FROM roles WHERE id_rol=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Rol> listar() {

        List<Rol> lista = new ArrayList<>();

        String sql = "SELECT * FROM roles";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Rol rol = new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );

                lista.add(rol);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}