package com.paqueteria.util.cliente;

import com.paqueteria.model.entities.Cliente;
import com.paqueteria.model.service.ClienteService;

import java.util.List;

public class PruebaListarClientes {

    public static void main(String[] args) {

        ClienteService service = new ClienteService();

        List<Cliente> clientes = service.listarClientes();

        System.out.println("========= LISTA DE CLIENTES =========");

        if (clientes.isEmpty()) {
            System.out.println("No existen clientes registrados.");
        } else {

            for (Cliente cliente : clientes) {

                System.out.println("--------------------------------");

                System.out.println("ID: " + cliente.getIdCliente());
                System.out.println("Cédula: " + cliente.getCedula());
                System.out.println("Nombres: " + cliente.getNombres());
                System.out.println("Apellidos: " + cliente.getApellidos());
                System.out.println("Teléfono: " + cliente.getTelefono());
                System.out.println("Correo: " + cliente.getCorreo());
                System.out.println("Dirección: " + cliente.getDireccion());
                System.out.println("Fecha Registro: " + cliente.getFechaRegistro());

            }
        }

    }

}