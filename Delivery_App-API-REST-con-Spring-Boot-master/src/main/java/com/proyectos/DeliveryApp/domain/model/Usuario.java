package com.proyectos.DeliveryApp.domain.model;


import com.proyectos.DeliveryApp.domain.enums.Rol;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    private Rol rol;
}
