package com.proyectos.DeliveryApp.domain.Exception;

public class PedidoNoEncontradoException extends RuntimeException{

    public PedidoNoEncontradoException (Long id){
        super("Pedido con id " + id + " no fue encontrado");
    }
}
