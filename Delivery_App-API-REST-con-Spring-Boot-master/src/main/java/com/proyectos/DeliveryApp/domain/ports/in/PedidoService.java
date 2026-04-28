package com.proyectos.DeliveryApp.domain.ports.in;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.infraestructure.entity.PedidoEntity;

import java.util.List;

public interface PedidoService {

    Pedido crear(Pedido pedido);

    List<Pedido> listar();

    Pedido cancelar(Long id);

    Pedido cambiarEstado(Long id, EstadoPedido estado);

    Pedido asignarRepartidor(Long pedidoId, Long repartidorId);

    List<Pedido> obtenerPedidosPorCliente(Long clienteId);

    List<Pedido> obtenerPedidosPorRestaurante(Long restauranteId);

    List<Pedido> obtenerPorEstado(EstadoPedido estado);

    Pedido buscarPorId(Long id);


}
