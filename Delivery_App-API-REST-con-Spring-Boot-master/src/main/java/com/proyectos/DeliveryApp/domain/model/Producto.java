package com.proyectos.DeliveryApp.domain.model;

import com.proyectos.DeliveryApp.domain.enums.Disponible;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Disponible disponible;

    private Long restauranteId;
}
