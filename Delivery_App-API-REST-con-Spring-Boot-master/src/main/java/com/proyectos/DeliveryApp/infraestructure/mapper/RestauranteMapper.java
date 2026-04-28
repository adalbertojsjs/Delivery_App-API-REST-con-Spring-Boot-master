package com.proyectos.DeliveryApp.infraestructure.mapper;

import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    // ENTITY ➜ DOMAIN
    public Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) return null;

        return Restaurante.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .direccion(entity.getDireccion())
                .estado(entity.getEstado())
                .build();
    }

    // DOMAIN ➜ ENTITY
    public RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) return null;

        RestauranteEntity entity = new RestauranteEntity();

        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDireccion(domain.getDireccion());
        entity.setEstado(domain.getEstado());

        return entity;
    }
}
