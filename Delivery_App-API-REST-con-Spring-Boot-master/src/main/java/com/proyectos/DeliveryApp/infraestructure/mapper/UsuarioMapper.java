package com.proyectos.DeliveryApp.infraestructure.mapper;

import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.UsuarioResponse;
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

        public Usuario responseToDomain(UsuarioResponse response){

            var domain = Usuario.
                    builder().
                    id(response.getId()).
                    nombre(response.getNombre()).
                    email(response.getEmail()).
                    rol(response.getRol()).
                    build();

            return domain;
        }

        public UsuarioResponse domainToResponse(Usuario usuario){

            var response = UsuarioResponse.
                    builder().
                    id(usuario.getId()).
                    nombre(usuario.getNombre()).
                    email(usuario.getEmail()).
                    rol(usuario.getRol()).
                    build();

            return response;
        }
}

