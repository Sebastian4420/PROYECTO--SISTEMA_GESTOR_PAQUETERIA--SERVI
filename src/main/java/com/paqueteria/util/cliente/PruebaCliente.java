package com.paqueteria.util.cliente;

import com.paqueteria.model.entities.Cliente;
import com.paqueteria.model.service.ClienteService;

public class PruebaCliente {

    public static void main(String[] args) {

        ClienteService service = new ClienteService();

        Cliente cliente = new Cliente();

        cliente.setCedula("1723456789");
        cliente.setNombres("Mateo");
        cliente.setApellidos("Garcia");
        cliente.setTelefono("0999999999");
        cliente.setCorreo("mateo@gmail.com");
        cliente.setDireccion("Quito - Ecuador");

        boolean guardado = service.guardarCliente(cliente);

        if (guardado) {
            System.out.println("✅ Cliente registrado correctamente.");
        } else {
            System.out.println("❌ No se pudo registrar el cliente.");
        }

    }
}