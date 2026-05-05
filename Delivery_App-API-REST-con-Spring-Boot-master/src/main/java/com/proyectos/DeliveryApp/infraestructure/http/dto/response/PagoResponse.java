package com.proyectos.DeliveryApp.infraestructure.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;



@AllArgsConstructor
@Getter
@Setter
@Builder
public class PagoResponse {

    private Long id;

    private Long pedidoId;

    private String nombreComprador;

    private String numeroTarjeta;

    private LocalDate fecha;

    private BigDecimal costoFinal;
}
