package com.proyectos.DeliveryApp.infraestructure.http.dto.request;

import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {

    private String nombre;
    private String direccion;
    private EstadoRestaurante estado;
}
