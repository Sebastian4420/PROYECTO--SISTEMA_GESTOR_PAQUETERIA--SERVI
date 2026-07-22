package com.paqueteria.util;

import com.paqueteria.model.conexion.Conexion;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        Connection conexion = Conexion.getConexion();

        if (conexion != null) {
            System.out.println("Conexión exitosa.");
        } else {
            System.out.println("Error al conectar.");
        }

    }

}
