package com.paqueteria.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paquete {

    private int idPaquete;

    private String codigoSeguimiento;

    private Cliente cliente;

    private Repartidor repartidor;

    private TipoEnvio tipoEnvio;

    private EstadoPaquete estado;

    private Usuario usuario;

    private String nombreDestinatario;

    private String telefonoDestinatario;

    private String origen;

    private String destino;

    private BigDecimal peso;

    private BigDecimal precio;

    private LocalDateTime fechaRegistro;

}