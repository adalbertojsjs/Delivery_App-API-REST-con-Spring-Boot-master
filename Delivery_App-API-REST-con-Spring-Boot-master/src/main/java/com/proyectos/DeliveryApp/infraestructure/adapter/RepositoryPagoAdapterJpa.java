package com.proyectos.DeliveryApp.infraestructure.adapter;

import com.proyectos.DeliveryApp.domain.model.Pago;
import com.proyectos.DeliveryApp.domain.ports.out.PagoRepositoryOutPorts;
import com.proyectos.DeliveryApp.infraestructure.mapper.PagoMapper;
import com.proyectos.DeliveryApp.infraestructure.repository.PagoRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RepositoryPagoAdapterJpa implements PagoRepositoryOutPorts {

    private  final PagoRepositoryJpa repositoryJpa;

    private  final PagoMapper mapper;

    @Override
    public Pago save(Pago model) {
        var entity = mapper.toEntity(model);
        var savedEntity = repositoryJpa.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Pago> findById(Long id) {
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Pago> findAll() {
        return repositoryJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    @Override
    public void deleteById(Long id) {

        repositoryJpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repositoryJpa.existsById(id);
    }
}
