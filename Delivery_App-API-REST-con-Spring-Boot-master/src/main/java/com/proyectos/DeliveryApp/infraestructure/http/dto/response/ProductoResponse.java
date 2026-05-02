package com.proyectos.DeliveryApp.infraestructure.http.dto.response;

import com.proyectos.DeliveryApp.domain.enums.Disponible;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductoResponse {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Disponible disponible;
    private Long restauranteId;

}
