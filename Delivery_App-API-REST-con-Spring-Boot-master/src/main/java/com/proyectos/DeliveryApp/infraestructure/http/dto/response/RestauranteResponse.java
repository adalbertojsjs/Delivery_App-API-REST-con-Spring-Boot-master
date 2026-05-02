package com.proyectos.DeliveryApp.infraestructure.http.dto.response;

import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder

public class RestauranteResponse {

    private Long id;
    private  String nombre;
    private String direccion;
    private EstadoRestaurante estado;


}
