package com.proyectos.DeliveryApp.domain.ports.out;

import com.proyectos.DeliveryApp.domain.model.Producto;

import java.util.List;

public interface ProductoRepositoryOutPorts extends BaseCrudRepositoryOutPort<Producto,Long> {

    List<Producto> findByRestauranteId(Long restauranteId);

}
