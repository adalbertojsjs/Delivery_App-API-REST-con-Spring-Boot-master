package com.proyectos.DeliveryApp.infraestructure.http.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoRequest {

    private String nombreComprador;

    private String numeroTarjeta;
}
