package com.proyectos.DeliveryApp.infraestructure.repository;

import com.proyectos.DeliveryApp.infraestructure.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepositoryJpa extends JpaRepository<PagoEntity, Long> {
}
