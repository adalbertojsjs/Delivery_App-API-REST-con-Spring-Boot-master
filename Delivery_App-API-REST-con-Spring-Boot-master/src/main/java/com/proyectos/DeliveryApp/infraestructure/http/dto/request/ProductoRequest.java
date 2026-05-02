package com.proyectos.DeliveryApp.infraestructure.http.dto.request;

import com.proyectos.DeliveryApp.domain.enums.Disponible;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoRequest {

    private String nombre;
    private BigDecimal precio;
    private Disponible disponible;
    private Long restauranteId;
}
