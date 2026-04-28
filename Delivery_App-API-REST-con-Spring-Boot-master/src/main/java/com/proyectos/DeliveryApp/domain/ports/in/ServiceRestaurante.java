package com.proyectos.DeliveryApp.domain.ports.in;

import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.infraestructure.entity.RestauranteEntity;

import java.util.List;

public interface ServiceRestaurante {

        List<Restaurante> listar();
        Restaurante crear(Restaurante restaurante);
        Restaurante actualizar(Long id, Restaurante restaurante);
        void eliminar(Long id);


}
