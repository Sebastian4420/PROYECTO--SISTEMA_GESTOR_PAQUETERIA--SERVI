package com.paqueteria.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    private int idTarifa;
    private TipoEnvio tipoEnvio;
    private BigDecimal precioBase;
    private BigDecimal costoPorKgExtra;
    private BigDecimal recargo;

}