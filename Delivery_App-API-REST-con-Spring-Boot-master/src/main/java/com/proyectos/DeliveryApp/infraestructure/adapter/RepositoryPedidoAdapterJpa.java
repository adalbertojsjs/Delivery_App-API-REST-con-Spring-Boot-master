package com.proyectos.DeliveryApp.infraestructure.adapter;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.domain.ports.out.PedidoRepositoryOutPorts;
import com.proyectos.DeliveryApp.infraestructure.entity.PedidoEntity;
import com.proyectos.DeliveryApp.infraestructure.mapper.PedidoMapper;
import com.proyectos.DeliveryApp.infraestructure.repository.PedidoRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class RepositoryPedidoAdapterJpa implements PedidoRepositoryOutPorts {

    private final PedidoRepositoryJpa repository;

    private  final PedidoMapper mapper;


    @Override
    public List<Pedido> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByRestauranteId(Long restauranteId) {
        return repository.findByRestauranteId(restauranteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByEstadoPedido(EstadoPedido estadoPedido) {
        return repository.findByEstadoPedido(estadoPedido)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Pedido save(Pedido pedido) {
        PedidoEntity entity = mapper.toEntity(pedido);
        PedidoEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}

