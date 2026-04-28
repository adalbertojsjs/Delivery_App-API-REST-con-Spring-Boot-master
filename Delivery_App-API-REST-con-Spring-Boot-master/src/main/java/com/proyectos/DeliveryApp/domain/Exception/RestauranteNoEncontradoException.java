package com.proyectos.DeliveryApp.domain.Exception;

public class RestauranteNoEncontradoException extends RuntimeException{

    public RestauranteNoEncontradoException(Long id ){

        super("restaurante con id " + id + " no fue encontrado");
    }
}
