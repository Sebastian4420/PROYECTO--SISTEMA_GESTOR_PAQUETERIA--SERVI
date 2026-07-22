package com.paqueteria.model.dao;

import com.paqueteria.model.conexion.Conexion;
import com.paqueteria.model.entities.EstadoPaquete;
import com.paqueteria.model.entities.HistorialEstado;
import com.paqueteria.model.entities.Paquete;
import com.paqueteria.model.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialEstadoDAO implements Crud<HistorialEstado> {

    @Override
    public boolean guardar(HistorialEstado historial) {

        String sql = """
                INSERT INTO historial_estados
                (id_paquete, id_estado, fecha, observacion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, historial.getPaquete().getIdPaquete());
            ps.setInt(2, historial.getEstado().getIdEstado());

            ps.setTimestamp(3, Timestamp.valueOf(historial.getFecha()));

            ps.setString(4, historial.getObservacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al guardar el historial.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean actualizar(HistorialEstado historial) {

        String sql = """
                UPDATE historial_estados
                SET id_paquete=?,
                    id_estado=?,
                    fecha=?,
                    observacion=?
                WHERE id_historial=?
                """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, historial.getPaquete().getIdPaquete());
            ps.setInt(2, historial.getEstado().getIdEstado());

            ps.setTimestamp(3, Timestamp.valueOf(historial.getFecha()));

            ps.setString(4, historial.getObservacion());

            ps.setInt(5, historial.getIdHistorial());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar el historial.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM historial_estados WHERE id_historial=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar el historial.");
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public HistorialEstado buscarPorId(int id) {

        String sql = "SELECT * FROM historial_estados WHERE id_historial=?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirHistorial(rs);
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar historial.");
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public List<HistorialEstado> listar() {

        List<HistorialEstado> lista = new ArrayList<>();

        String sql = "SELECT * FROM historial_estados ORDER BY id_historial";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(construirHistorial(rs));
            }

        } catch (SQLException e) {

            System.out.println("Error al listar historial.");
            e.printStackTrace();

        }

        return lista;
    }
    public List<HistorialEstado> listarPorPaquete(int idPaquete) {

        List<HistorialEstado> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM historial_estados
            WHERE id_paquete=?
            ORDER BY fecha ASC
            """;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPaquete);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(construirHistorial(rs));
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar el historial del paquete."
            );

            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // Método privado
    // ==========================

    private HistorialEstado construirHistorial(ResultSet rs) throws SQLException {

        PaqueteDAO paqueteDAO = new PaqueteDAO();
        Paquete paquete = paqueteDAO.buscarPorId(
                rs.getInt("id_paquete")
        );

        EstadoPaqueteDAO estadoDAO = new EstadoPaqueteDAO();
        EstadoPaquete estado = estadoDAO.buscarPorId(
                rs.getInt("id_estado")
        );

        HistorialEstado historial = new HistorialEstado();

        historial.setIdHistorial(
                rs.getInt("id_historial")
        );

        historial.setPaquete(paquete);
        historial.setEstado(estado);

        Timestamp timestamp = rs.getTimestamp("fecha");

        if (timestamp != null) {
            historial.setFecha(
                    timestamp.toLocalDateTime()
            );
        }

        historial.setObservacion(
                rs.getString("observacion")
        );

        return historial;
    }

}