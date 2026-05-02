package com.proyectos.DeliveryApp.infraestructure.mapper;

import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.infraestructure.entity.PedidoEntity;
import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;
import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import com.proyectos.DeliveryApp.infraestructure.http.dto.request.PedidoRequest;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.PedidoResponse;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido toDomain(PedidoEntity entity) {
        if (entity == null) return null;

        return Pedido.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .total(entity.getTotal())
                .estadoPedido(entity.getEstadoPedido())
                .clienteId(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .repartidorId(entity.getRepartidor() != null ? entity.getRepartidor().getId() : null)
                .restauranteId(entity.getRestaurante() != null ? entity.getRestaurante().getId() : null)
                .build();
    }

    public PedidoEntity toEntity(Pedido domain) {
        if (domain == null) return null;

        PedidoEntity entity = new PedidoEntity();

        entity.setId(domain.getId());
        entity.setFecha(domain.getFecha());
        entity.setTotal(domain.getTotal());
        entity.setEstadoPedido(domain.getEstadoPedido());

        if (domain.getClienteId() != null) {
            UsuarioEntity cliente = new UsuarioEntity();
            cliente.setId(domain.getClienteId());
            entity.setCliente(cliente);
        }

        if (domain.getRepartidorId() != null) {
            UsuarioEntity repartidor = new UsuarioEntity();
            repartidor.setId(domain.getRepartidorId());
            entity.setRepartidor(repartidor);
        }

        if (domain.getRestauranteId() != null) {
            RestauranteEntity restaurante = new RestauranteEntity();
            restaurante.setId(domain.getRestauranteId());
            entity.setRestaurante(restaurante);
        }

        return entity;
    }

    public Pedido requestToDomain(PedidoRequest request){

        var pedido = Pedido.
                builder().
                total(request.getTotal()).
                clienteId(request.getClienteId()).
                repartidorId(request.getRepartidorId()).
                restauranteId(request.getRestauranteId()).
                build();

        return  pedido;
    }


    public PedidoResponse domainToResponse(Pedido pedido){

        var response = PedidoResponse.
                builder().
                id(pedido.getId()).
                fecha(pedido.getFecha()).
                total(pedido.getTotal()).
                estado(pedido.getEstadoPedido()).
                restauranteId(pedido.getRestauranteId()).
                build();

        if (pedido.getClienteId() != null){
            response.setClienteId(pedido.getClienteId());
        }
        if (pedido.getRepartidorId() != null){
            response.setRepartidorId(pedido.getRepartidorId());
        }

        return response;
    }
}