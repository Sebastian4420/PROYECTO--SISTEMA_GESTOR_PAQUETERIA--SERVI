package com.paqueteria.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstado {

    private int idHistorial;

    private Paquete paquete;

    private EstadoPaquete estado;

    private LocalDateTime fecha;

    private String observacion;

}
