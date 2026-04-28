package com.proyectos.DeliveryApp.domain.ports.out;

import java.util.List;
import java.util.Optional;

public interface BaseCrudRepositoryOutPort<T, ID>{

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);
}
