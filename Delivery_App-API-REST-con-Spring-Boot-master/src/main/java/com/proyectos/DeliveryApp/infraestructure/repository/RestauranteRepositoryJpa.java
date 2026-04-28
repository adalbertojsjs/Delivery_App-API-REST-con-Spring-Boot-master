package com.proyectos.DeliveryApp.infraestructure.repository;

import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepositoryJpa extends JpaRepository<RestauranteEntity, Long> {


}
