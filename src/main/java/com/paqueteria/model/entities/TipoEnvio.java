package com.paqueteria.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEnvio {

    private int idTipoEnvio;
    private String nombre;
    private String descripcion;

    @Override
    public String toString() {
        return nombre;
    }
}