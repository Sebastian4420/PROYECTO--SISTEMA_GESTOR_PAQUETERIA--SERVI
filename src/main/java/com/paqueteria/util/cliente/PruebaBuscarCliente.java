package com.paqueteria.util.cliente;

import com.paqueteria.model.entities.Cliente;
import com.paqueteria.model.service.ClienteService;

public class PruebaBuscarCliente {

    public static void main(String[] args) {

        ClienteService service = new ClienteService();

        Cliente cliente = service.buscarCliente(1);

        if (cliente != null) {

            System.out.println("Cliente encontrado");
            System.out.println(cliente);

        } else {

            System.out.println("Cliente no encontrado");

        }

    }

}