package com.paqueteria.util;

import com.paqueteria.model.dao.RolDAO;
import com.paqueteria.model.entities.Rol;

public class PruebaRol {

    public static void main(String[] args) {

        RolDAO dao = new RolDAO();

        System.out.println("=== LISTA DE ROLES ===");

        for (Rol rol : dao.listar()) {

            System.out.println(
                    rol.getIdRol() + " - " +
                            rol.getNombre() + " - " +
                            rol.getDescripcion()
            );

        }

    }

}