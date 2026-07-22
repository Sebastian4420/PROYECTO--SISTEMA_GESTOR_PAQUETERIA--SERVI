
package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.Cliente;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements Crud<Cliente> {

    @Override
    public boolean guardar(Cliente cliente) {

        String sql = """
                INSERT INTO clientes
                (cedula, nombres, apellidos, telefono, correo, direccion)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getDireccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al guardar el cliente.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {

        String sql = """
                UPDATE clientes
                SET cedula = ?, nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ?
                WHERE id_cliente = ?
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getDireccion());
            ps.setInt(7, cliente.getIdCliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar el cliente.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("No se puede eliminar el cliente porque tiene paquetes asociados.");
            return false;

        } catch (SQLException e) {

            System.out.println("Error al eliminar el cliente.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return construirCliente(rs);

            }

        } catch (SQLException e) {

            System.out.println("Error al buscar el cliente.");
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public List<Cliente> listar() {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY id_cliente";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(construirCliente(rs));

            }

        } catch (SQLException e) {

            System.out.println("Error al listar los clientes.");
            e.printStackTrace();

        }

        return lista;
    }

    //=================================================
    // MÉTODO AUXILIAR
    //=================================================

    private Cliente construirCliente(ResultSet rs) throws SQLException {

        Cliente cliente = new Cliente();

        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setCedula(rs.getString("cedula"));
        cliente.setNombres(rs.getString("nombres"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setDireccion(rs.getString("direccion"));

        Timestamp fecha = rs.getTimestamp("fecha_registro");

        if (fecha != null) {
            cliente.setFechaRegistro(fecha.toLocalDateTime());
        }

        return cliente;
    }

    public boolean existeCedula(String cedula) {

        String sql = "SELECT COUNT(*) FROM clientes WHERE cedula=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar la cédula.");
            e.printStackTrace();
            return false;
        }
    }
    public boolean existeTelefono(String telefono) {

        String sql = "SELECT COUNT(*) FROM clientes WHERE telefono=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, telefono);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar el teléfono.");
            e.printStackTrace();
            return false;
        }
    }
    public boolean existeCedulaEnOtroCliente(String cedula, int idCliente) {

        String sql = """
            SELECT COUNT(*)
            FROM clientes
            WHERE cedula=? AND id_cliente<>?
            """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setInt(2, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar la cédula.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeTelefonoEnOtroCliente(
            String telefono,
            int idCliente) {

        String sql = """
            SELECT COUNT(*)
            FROM clientes
            WHERE telefono=? AND id_cliente<>?
            """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, telefono);
            ps.setInt(2, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar el teléfono.");
            e.printStackTrace();
            return false;
        }
    }

}