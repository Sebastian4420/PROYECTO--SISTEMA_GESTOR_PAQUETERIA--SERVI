package com.paqueteria.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repartidor {

    private int idRepartidor;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String vehiculo;
    private String placa;
    private boolean disponible;

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}