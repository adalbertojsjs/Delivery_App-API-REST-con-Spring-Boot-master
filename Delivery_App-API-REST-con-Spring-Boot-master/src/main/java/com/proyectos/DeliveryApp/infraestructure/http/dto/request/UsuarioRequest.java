package com.proyectos.DeliveryApp.infraestructure.http.dto.request;


import com.proyectos.DeliveryApp.domain.enums.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {


    private String nombre;
    private String email;
    private String contrasena;
    private Rol rol;
}
