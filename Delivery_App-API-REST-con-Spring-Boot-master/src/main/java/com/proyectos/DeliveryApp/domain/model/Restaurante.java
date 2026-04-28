package com.proyectos.DeliveryApp.domain.model;

import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

    private Long id;
    private String nombre;
    private String direccion;
    private EstadoRestaurante estado;
}
