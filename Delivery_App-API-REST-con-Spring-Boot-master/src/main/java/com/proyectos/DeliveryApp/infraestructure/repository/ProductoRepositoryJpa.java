package com.proyectos.DeliveryApp.infraestructure.repository;

import com.proyectos.DeliveryApp.infraestructure.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositoryJpa extends JpaRepository<ProductoEntity, Long> {

    List<ProductoEntity> findByRestauranteId(Long restauranteId);
}
