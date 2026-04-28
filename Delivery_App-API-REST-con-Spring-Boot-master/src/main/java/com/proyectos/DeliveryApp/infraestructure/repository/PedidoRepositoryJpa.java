package com.proyectos.DeliveryApp.infraestructure.repository;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.infraestructure.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepositoryJpa extends JpaRepository<PedidoEntity, Long> {


    List<PedidoEntity> findByClienteId(Long clienteId);

    List<PedidoEntity> findByRestauranteId(Long restauranteId);

    List<PedidoEntity> findByEstadoPedido(EstadoPedido estadoPedido);
}
