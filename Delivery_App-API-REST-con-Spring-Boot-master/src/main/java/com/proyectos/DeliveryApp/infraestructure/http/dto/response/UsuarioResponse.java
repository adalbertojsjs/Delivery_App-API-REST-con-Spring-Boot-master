package com.proyectos.DeliveryApp.infraestructure.http.dto.response;

import com.proyectos.DeliveryApp.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String email;
    private Rol rol;


}
