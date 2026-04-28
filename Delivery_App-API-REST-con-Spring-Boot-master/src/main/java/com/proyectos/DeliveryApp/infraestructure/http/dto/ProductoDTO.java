package com.proyectos.DeliveryApp.infraestructure.http.dto;

import com.proyectos.DeliveryApp.domain.enums.Disponible;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class ProductoDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Disponible disponible;

    private RestauranteDTO restauranteDTO;


}
