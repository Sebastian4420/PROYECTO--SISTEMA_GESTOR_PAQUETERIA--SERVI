package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.EstadoPaquete;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoPaqueteDAO implements Crud<EstadoPaquete> {

    @Override
    public boolean guardar(EstadoPaquete estado) {

        String sql = """
                INSERT INTO estados_paquete
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado.getNombre());
            ps.setString(2, estado.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar el estado.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(EstadoPaquete estado) {

        String sql = """
                UPDATE estados_paquete
                SET nombre=?, descripcion=?
                WHERE id_estado=?
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado.getNombre());
            ps.setString(2, estado.getDescripcion());
            ps.setInt(3, estado.getIdEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM estados_paquete WHERE id_estado=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el estado.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EstadoPaquete buscarPorId(int id) {

        String sql = "SELECT * FROM estados_paquete WHERE id_estado=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                EstadoPaquete estado = new EstadoPaquete();

                estado.setIdEstado(rs.getInt("id_estado"));
                estado.setNombre(rs.getString("nombre"));
                estado.setDescripcion(rs.getString("descripcion"));

                return estado;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el estado.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<EstadoPaquete> listar() {

        List<EstadoPaquete> lista = new ArrayList<>();

        String sql = "SELECT * FROM estados_paquete ORDER BY id_estado";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                EstadoPaquete estado = new EstadoPaquete();

                estado.setIdEstado(rs.getInt("id_estado"));
                estado.setNombre(rs.getString("nombre"));
                estado.setDescripcion(rs.getString("descripcion"));

                lista.add(estado);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los estados.");
            e.printStackTrace();
        }

        return lista;
    }
    public boolean existeNombre(String nombre) {

        String sql = """
            SELECT COUNT(*)
            FROM estados_paquete
            WHERE LOWER(nombre) = LOWER(?)
            """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre.trim());

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {

            System.out.println("Error al verificar el nombre del estado.");
            e.printStackTrace();
            return false;
        }
    }

}