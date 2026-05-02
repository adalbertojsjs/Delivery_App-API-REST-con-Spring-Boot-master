package com.proyectos.DeliveryApp.infraestructure.mapper;

import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;
import com.proyectos.DeliveryApp.infraestructure.http.dto.request.RestauranteRequest;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.RestauranteResponse;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    public Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) return null;

        return Restaurante.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .direccion(entity.getDireccion())
                .estado(entity.getEstado())
                .build();
    }

    public RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) return null;

        RestauranteEntity entity = new RestauranteEntity();

        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDireccion(domain.getDireccion());
        entity.setEstado(domain.getEstado());

        return entity;
    }

    public Restaurante requestToDomain(RestauranteRequest request){

        var domain = Restaurante.
                builder().
                nombre(request.getNombre()).
                direccion(request.getDireccion()).
                estado(request.getEstado()).
                build();

        return domain;
    }

    public RestauranteResponse domainToResponse(Restaurante restaurante){

        var response = RestauranteResponse.
                builder().
                id(restaurante.getId()).
                nombre(restaurante.getNombre()).
                direccion(restaurante.getDireccion()).
                estado(restaurante.getEstado()).
                build();
        return response;
    }
}
