package com.proyectos.DeliveryApp.infraestructure.adapter;

import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;
import com.proyectos.DeliveryApp.infraestructure.mapper.RestauranteMapper;
import com.proyectos.DeliveryApp.infraestructure.repository.RestauranteRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class RepositoryRestauranteAdapterJpa implements RestauranteRepositoryOutPorts {

     private  final RestauranteRepositoryJpa repository;
     private  final RestauranteMapper mapper;


    @Override
    public Restaurante save(Restaurante restaurante) {
        RestauranteEntity entity = mapper.toEntity(restaurante);
        RestauranteEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Restaurante> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Restaurante> findAll() {
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
