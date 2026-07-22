package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.*;

        import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
        import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaqueteDAO implements Crud<Paquete> {

    @Override
    public boolean guardar(Paquete paquete) {

        String sql = """
        INSERT INTO paquetes
        (
        codigo_seguimiento,
        id_cliente,
        id_repartidor,
        nombre_destinatario,
        telefono_destinatario,
        id_tipo_envio,
        id_estado,
        id_usuario,
        origen,
        destino,
        peso,
        precio
        )
        VALUES
        (?,?,?,?,?,?,?,?,?,?,?,?)
        """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, paquete.getCodigoSeguimiento());
            ps.setInt(2, paquete.getCliente().getIdCliente());
            if (paquete.getRepartidor() != null) {
                ps.setInt(3, paquete.getRepartidor().getIdRepartidor());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, paquete.getNombreDestinatario());
            ps.setString(5, paquete.getTelefonoDestinatario());

            ps.setInt(6, paquete.getTipoEnvio().getIdTipoEnvio());
            ps.setInt(7, paquete.getEstado().getIdEstado());
            ps.setInt(8, paquete.getUsuario().getIdUsuario());

            ps.setString(9, paquete.getOrigen());
            ps.setString(10, paquete.getDestino());

            ps.setBigDecimal(11, paquete.getPeso());
            ps.setBigDecimal(12, paquete.getPrecio());


            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al guardar el paquete.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean actualizar(Paquete paquete) {

        String sql = """
            UPDATE paquetes
            SET codigo_seguimiento=?,
                id_cliente=?,
                id_repartidor=?,
                nombre_destinatario=?,
                telefono_destinatario=?,
                id_tipo_envio=?,
                id_estado=?,
                id_usuario=?,
                origen=?,
                destino=?,
                peso=?,
                precio=?
            WHERE id_paquete=?
            """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, paquete.getCodigoSeguimiento());
            ps.setInt(2, paquete.getCliente().getIdCliente());

            if (paquete.getRepartidor() != null) {
                ps.setInt(3, paquete.getRepartidor().getIdRepartidor());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, paquete.getNombreDestinatario());
            ps.setString(5, paquete.getTelefonoDestinatario());

            ps.setInt(6, paquete.getTipoEnvio().getIdTipoEnvio());
            ps.setInt(7, paquete.getEstado().getIdEstado());
            ps.setInt(8, paquete.getUsuario().getIdUsuario());

            ps.setString(9, paquete.getOrigen());
            ps.setString(10, paquete.getDestino());

            ps.setBigDecimal(11, paquete.getPeso());
            ps.setBigDecimal(12, paquete.getPrecio());

            ps.setInt(13, paquete.getIdPaquete());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar el paquete.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM paquetes WHERE id_paquete=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar el paquete.");
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public Paquete buscarPorId(int id) {

        String sql = "SELECT * FROM paquetes WHERE id_paquete=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return construirPaquete(rs);

            }

        } catch (SQLException e) {

            System.out.println("Error al buscar el paquete.");
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public List<Paquete> listar() {

        List<Paquete> lista = new ArrayList<>();

        String sql = "SELECT * FROM paquetes ORDER BY id_paquete";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(construirPaquete(rs));

            }

        } catch (SQLException e) {

            System.out.println("Error al listar paquetes.");
            e.printStackTrace();

        }

        return lista;
    }

    // ==========================
    // MÉTODO EXTRA
    // ==========================

    public Paquete buscarPorCodigoSeguimiento(String codigo) {

        String sql = "SELECT * FROM paquetes WHERE codigo_seguimiento=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return construirPaquete(rs);

            }

        } catch (SQLException e) {

            System.out.println("Error al buscar por código.");
            e.printStackTrace();

        }

        return null;
    }

    // ==========================
    // MÉTODO PRIVADO
    // ==========================

    private Paquete construirPaquete(ResultSet rs) throws SQLException {

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorId(rs.getInt("id_cliente"));

        RepartidorDAO repartidorDAO = new RepartidorDAO();
        Repartidor repartidor = null;

        int idRepartidor = rs.getInt("id_repartidor");
        if (!rs.wasNull()) {
            repartidor = repartidorDAO.buscarPorId(idRepartidor);
        }

        TipoEnvioDAO tipoEnvioDAO = new TipoEnvioDAO();
        TipoEnvio tipoEnvio =
                tipoEnvioDAO.buscarPorId(rs.getInt("id_tipo_envio"));

        EstadoPaqueteDAO estadoDAO = new EstadoPaqueteDAO();
        EstadoPaquete estado =
                estadoDAO.buscarPorId(rs.getInt("id_estado"));

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));

        Paquete paquete = new Paquete();

        paquete.setIdPaquete(rs.getInt("id_paquete"));
        paquete.setCodigoSeguimiento(rs.getString("codigo_seguimiento"));

        paquete.setCliente(cliente);
        paquete.setRepartidor(repartidor);
        paquete.setTipoEnvio(tipoEnvio);
        paquete.setEstado(estado);
        paquete.setUsuario(usuario);

        paquete.setNombreDestinatario(rs.getString("nombre_destinatario"));
        paquete.setTelefonoDestinatario(rs.getString("telefono_destinatario"));

        paquete.setOrigen(rs.getString("origen"));
        paquete.setDestino(rs.getString("destino"));

        paquete.setPeso(rs.getBigDecimal("peso"));
        paquete.setPrecio(rs.getBigDecimal("precio"));

        Timestamp timestamp = rs.getTimestamp("fecha_registro");
        if (timestamp != null) {
            paquete.setFechaRegistro(timestamp.toLocalDateTime());
        }

        return paquete;
    }

}
