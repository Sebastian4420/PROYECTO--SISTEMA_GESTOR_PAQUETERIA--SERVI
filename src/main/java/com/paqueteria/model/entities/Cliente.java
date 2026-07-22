package com.paqueteria.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private int idCliente;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private LocalDateTime fechaRegistro;

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}