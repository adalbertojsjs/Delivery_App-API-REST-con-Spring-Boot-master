package com.proyectos.DeliveryApp.domain.Exception;

public class ProductoNoEncontrado extends RuntimeException{

    public ProductoNoEncontrado(Long id){
        super("Producto con id " + id + " no fue encontrado");
    }
}
