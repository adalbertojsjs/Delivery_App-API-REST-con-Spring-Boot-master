package com.proyectos.DeliveryApp.domain.ports.in;

import com.proyectos.DeliveryApp.domain.enums.Disponible;
import com.proyectos.DeliveryApp.domain.model.Producto;
import com.proyectos.DeliveryApp.infraestructure.entity.ProductoEntity;

import java.util.List;

public interface ProductoService {

    List<Producto> listar();
    Producto crear(Producto producto);
    Producto cambiarDisponibilidad(Long id, Disponible disponible);
    List<Producto> listarPorRestaurante(Long restauranteId);
    Producto buscarPorId(Long id);

}
