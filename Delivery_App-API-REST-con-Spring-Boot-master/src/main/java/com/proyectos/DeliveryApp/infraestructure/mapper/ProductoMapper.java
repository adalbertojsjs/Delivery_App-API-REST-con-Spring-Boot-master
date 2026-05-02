package com.proyectos.DeliveryApp.infraestructure.mapper;

import com.proyectos.DeliveryApp.domain.model.Producto;
import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;
import com.proyectos.DeliveryApp.infraestructure.entity.ProductoEntity;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.ProductoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

     public Producto toDomain(ProductoEntity entity) {
            if (entity == null) return null;

            return Producto.builder()
                    .id(entity.getId())
                    .nombre(entity.getNombre())
                    .precio(entity.getPrecio())
                    .disponible(entity.getDisponible())
                    .restauranteId(
                            entity.getRestaurante() != null
                                    ? entity.getRestaurante().getId()
                                    : null
                    )
                    .build();
        }

        public ProductoEntity toEntity(Producto domain) {
            if (domain == null) return null;

            ProductoEntity entity = new ProductoEntity();

            entity.setId(domain.getId());
            entity.setNombre(domain.getNombre());
            entity.setPrecio(domain.getPrecio());
            entity.setDisponible(domain.getDisponible());

            if (domain.getRestauranteId() != null) {
                RestauranteEntity restaurante = new RestauranteEntity();
                restaurante.setId(domain.getRestauranteId());
                entity.setRestaurante(restaurante);
            }

            return entity;
        }

        public Producto respomseToDomain(ProductoResponse response){

         var domain = Producto.
                 builder().
                 id(response.getId()).
                 nombre(response.getNombre()).
                 precio(response.getPrecio()).
                 disponible(response.getDisponible()).
                 restauranteId(response.getRestauranteId()).
                 build();

         return domain;
        }

        public ProductoResponse domainToRespose(Producto producto){
            var response = ProductoResponse.
                    builder().
                    id(producto.getId()).
                    nombre(producto.getNombre()).
                    precio(producto.getPrecio()).
                    disponible(producto.getDisponible()).
                    restauranteId(producto.getRestauranteId()).
                    build();

            return response;
        }
    }

