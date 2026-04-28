package com.proyectos.DeliveryApp.infraestructure.adapter;

import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.domain.ports.out.UsuarioRepositoryOutPorts;
import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import com.proyectos.DeliveryApp.infraestructure.mapper.UsuarioMapper;
import com.proyectos.DeliveryApp.infraestructure.repository.UsuarioRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class RepositoryuUsuarioAdapterJpa implements UsuarioRepositoryOutPorts {


    private final UsuarioRepositoryJpa repository;
    private final UsuarioMapper mapper;

    @Override
    public List<Usuario> findByRol(Rol rol) {
        return repository.findByRol(rol)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }


}
