package com.proyectos.DeliveryApp.infraestructure.adapter;

import com.proyectos.DeliveryApp.domain.model.Producto;
import com.proyectos.DeliveryApp.domain.ports.out.ProductoRepositoryOutPorts;
import com.proyectos.DeliveryApp.infraestructure.entity.ProductoEntity;
import com.proyectos.DeliveryApp.infraestructure.mapper.ProductoMapper;
import com.proyectos.DeliveryApp.infraestructure.repository.ProductoRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class RepositoryProductoAdapterJpa implements ProductoRepositoryOutPorts {

    private final ProductoRepositoryJpa repository;
    private final ProductoMapper mapper;

    @Override
    public List<Producto> findByRestauranteId(Long restauranteId) {
        return repository.findByRestauranteId(restauranteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Producto save(Producto producto) {
        ProductoEntity entity = mapper.toEntity(producto);
        ProductoEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Producto> findAll() {
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
