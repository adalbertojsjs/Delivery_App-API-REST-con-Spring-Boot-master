package com.proyectos.DeliveryApp.infraestructure.http.dto;

import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class RestauranteDTO {

    private Long id;
    private  String nombre;
    private String direccion;
    private EstadoRestaurante estado;


}
