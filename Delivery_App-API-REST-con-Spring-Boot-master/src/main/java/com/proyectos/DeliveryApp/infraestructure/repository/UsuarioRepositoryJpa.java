package com.proyectos.DeliveryApp.infraestructure.repository;

import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepositoryJpa extends JpaRepository<UsuarioEntity, Long> {

    List<UsuarioEntity> findByRol(Rol rol);
}
