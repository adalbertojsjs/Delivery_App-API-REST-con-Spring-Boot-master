package com.proyectos.DeliveryApp.infraestructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "pedidoId")
    private Long pedidoId;

    @NotNull
    private String nombreComprador;

    @NotNull
    private String numeroTarjeta;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private BigDecimal costoFinal;

}
