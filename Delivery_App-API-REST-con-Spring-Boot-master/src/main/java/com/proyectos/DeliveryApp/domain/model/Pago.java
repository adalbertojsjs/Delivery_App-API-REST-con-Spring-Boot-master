package com.proyectos.DeliveryApp.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    private Long id;

    private Long pedidoId;
    
    private String nombreComprador;

    private String numeroTarjeta;
    
    private LocalDate fecha;

    private BigDecimal costoFinal;
}
