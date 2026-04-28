package com.proyectos.DeliveryApp.domain.ports.out;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pedido;

import java.util.List;

public interface PedidoRepositoryOutPorts extends BaseCrudRepositoryOutPort<Pedido,Long> {


    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByRestauranteId(Long restauranteId);

    List<Pedido> findByEstadoPedido(EstadoPedido estadoPedido);
}
