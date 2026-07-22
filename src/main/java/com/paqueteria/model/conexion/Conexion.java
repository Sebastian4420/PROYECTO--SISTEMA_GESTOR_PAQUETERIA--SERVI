package com.paqueteria.model.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3307/sistema_paqueteria";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConexion() {

        try {

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {

            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();

            return null;
        }
    }

}