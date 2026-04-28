package com.proyectos.DeliveryApp.infraestructure.http.dto;

import com.proyectos.DeliveryApp.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private Rol rol;


}
