package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.Repartidor;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO implements Crud<Repartidor> {

    @Override
    public boolean guardar(Repartidor repartidor) {

        String sql = """
                INSERT INTO repartidores
                (cedula, nombres, apellidos, telefono, correo, vehiculo, placa, disponible)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getCedula());
            ps.setString(2, repartidor.getNombres());
            ps.setString(3, repartidor.getApellidos());
            ps.setString(4, repartidor.getTelefono());
            ps.setString(5, repartidor.getCorreo());
            ps.setString(6, repartidor.getVehiculo());
            ps.setString(7, repartidor.getPlaca());
            ps.setBoolean(8, repartidor.isDisponible());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar el repartidor.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Repartidor repartidor) {

        String sql = """
                UPDATE repartidores
                SET cedula=?, nombres=?, apellidos=?, telefono=?, correo=?,
                    vehiculo=?, placa=?, disponible=?
                WHERE id_repartidor=?
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getCedula());
            ps.setString(2, repartidor.getNombres());
            ps.setString(3, repartidor.getApellidos());
            ps.setString(4, repartidor.getTelefono());
            ps.setString(5, repartidor.getCorreo());
            ps.setString(6, repartidor.getVehiculo());
            ps.setString(7, repartidor.getPlaca());
            ps.setBoolean(8, repartidor.isDisponible());
            ps.setInt(9, repartidor.getIdRepartidor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el repartidor.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM repartidores WHERE id_repartidor=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el repartidor.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Repartidor buscarPorId(int id) {

        String sql = "SELECT * FROM repartidores WHERE id_repartidor=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Repartidor repartidor = new Repartidor();

                repartidor.setIdRepartidor(rs.getInt("id_repartidor"));
                repartidor.setCedula(rs.getString("cedula"));
                repartidor.setNombres(rs.getString("nombres"));
                repartidor.setApellidos(rs.getString("apellidos"));
                repartidor.setTelefono(rs.getString("telefono"));
                repartidor.setCorreo(rs.getString("correo"));
                repartidor.setVehiculo(rs.getString("vehiculo"));
                repartidor.setPlaca(rs.getString("placa"));
                repartidor.setDisponible(rs.getBoolean("disponible"));

                return repartidor;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el repartidor.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Repartidor> listar() {

        List<Repartidor> lista = new ArrayList<>();

        String sql = "SELECT * FROM repartidores ORDER BY id_repartidor";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Repartidor repartidor = new Repartidor();

                repartidor.setIdRepartidor(rs.getInt("id_repartidor"));
                repartidor.setCedula(rs.getString("cedula"));
                repartidor.setNombres(rs.getString("nombres"));
                repartidor.setApellidos(rs.getString("apellidos"));
                repartidor.setTelefono(rs.getString("telefono"));
                repartidor.setCorreo(rs.getString("correo"));
                repartidor.setVehiculo(rs.getString("vehiculo"));
                repartidor.setPlaca(rs.getString("placa"));
                repartidor.setDisponible(rs.getBoolean("disponible"));

                lista.add(repartidor);

            }

        } catch (SQLException e) {
            System.out.println("Error al listar los repartidores.");
            e.printStackTrace();
        }

        return lista;
    }
}