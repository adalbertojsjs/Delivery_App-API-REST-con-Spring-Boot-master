package com.proyectos.DeliveryApp.infraestructure.mapper;

import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import org.springframework.stereotype.Component;


@Component
public class UsuarioMapper {

        public Usuario toDomain(UsuarioEntity entity) {
            if (entity == null) return null;

            return Usuario.builder()
                    .id(entity.getId())
                    .nombre(entity.getNombre())
                    .email(entity.getEmail())
                    .contrasena(entity.getContrasena())
                    .rol(entity.getRol())
                    .build();
        }

        public UsuarioEntity toEntity(Usuario domain) {
            if (domain == null) return null;

            UsuarioEntity entity = new UsuarioEntity();

            entity.setId(domain.getId());
            entity.setNombre(domain.getNombre());
            entity.setEmail(domain.getEmail());
            entity.setContrasena(domain.getContrasena());
            entity.setRol(domain.getRol());

            return entity;
        }
    }

