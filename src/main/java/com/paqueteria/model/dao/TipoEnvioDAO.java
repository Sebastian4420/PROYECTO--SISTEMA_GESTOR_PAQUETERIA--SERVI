package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.TipoEnvio;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class TipoEnvioDAO implements Crud<TipoEnvio> {

    @Override
    public boolean guardar(TipoEnvio tipo) {

        String sql = """
                INSERT INTO tipos_envio
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, tipo.getNombre());
            ps.setString(2, tipo.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar el tipo de envío.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(TipoEnvio tipo) {

        String sql = """
                UPDATE tipos_envio
                SET nombre=?, descripcion=?
                WHERE id_tipo_envio=?
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, tipo.getNombre());
            ps.setString(2, tipo.getDescripcion());
            ps.setInt(3, tipo.getIdTipoEnvio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el tipo de envío.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM tipos_envio WHERE id_tipo_envio=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el tipo de envío.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TipoEnvio buscarPorId(int id) {

        String sql = "SELECT * FROM tipos_envio WHERE id_tipo_envio=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                TipoEnvio tipo = new TipoEnvio();

                tipo.setIdTipoEnvio(rs.getInt("id_tipo_envio"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setDescripcion(rs.getString("descripcion"));

                return tipo;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el tipo de envío.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TipoEnvio> listar() {

        List<TipoEnvio> lista = new ArrayList<>();

        String sql = "SELECT * FROM tipos_envio ORDER BY id_tipo_envio";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                TipoEnvio tipo = new TipoEnvio();

                tipo.setIdTipoEnvio(rs.getInt("id_tipo_envio"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setDescripcion(rs.getString("descripcion"));

                lista.add(tipo);

            }

        } catch (SQLException e) {
            System.out.println("Error al listar los tipos de envío.");
            e.printStackTrace();
        }

        return lista;
    }

    public boolean existeNombre(String nombre) {

        String sql = "SELECT COUNT(*) FROM tipos_envio WHERE nombre=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}