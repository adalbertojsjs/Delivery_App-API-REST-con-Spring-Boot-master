package com.proyectos.DeliveryApp.aplication;

import com.proyectos.DeliveryApp.domain.Exception.ProductoNoEncontrado;
import com.proyectos.DeliveryApp.domain.Exception.RestauranteNoEncontradoException;
import com.proyectos.DeliveryApp.domain.enums.Disponible;
import com.proyectos.DeliveryApp.domain.model.Producto;
import com.proyectos.DeliveryApp.domain.ports.in.ProductoService;
import com.proyectos.DeliveryApp.domain.ports.out.ProductoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    private  final ProductoRepositoryOutPorts productoRepository;
    private  final RestauranteRepositoryOutPorts restauranteRepository;


    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }


    @Override
    public Producto crear(Producto producto) {
        if (producto == null){
            throw  new IllegalArgumentException("No puede estar vacio el parametro");
        }
        if (producto.getId() != null){
            throw  new IllegalArgumentException("El ID no debe ser ingresado");
        }
        if (producto.getDisponible() == Disponible.AGOTADO){
            throw new IllegalArgumentException("No puedes crear un producto y que este agotado");
        }

        if (producto.getRestauranteId() == null) {
            throw new IllegalArgumentException("El restaurante es obligatorio");
        }


        return productoRepository.save(producto);

    }

    @Override
    public Producto cambiarDisponibilidad(Long id, Disponible disponible) {
        if (id == null){
            throw new IllegalArgumentException("El Id es obligatorio");
        }

        if (disponible == null) {
            throw new IllegalArgumentException("El estado de disponibilidad es obligatorio");
        }

        Producto producto =  productoRepository.findById(id).
                orElseThrow(() -> new ProductoNoEncontrado(id));

        producto.setDisponible(disponible);
       return productoRepository.save(producto);


    }

    @Override
    public List<Producto> listarPorRestaurante(Long restauranteId) {
        if (restauranteId == null){
            throw new IllegalArgumentException("El Id del restaurante es obligatorio");
        }

        if (!restauranteRepository.existsById(restauranteId)){
            throw new RestauranteNoEncontradoException(restauranteId);
        }

       return productoRepository.findByRestauranteId(restauranteId);

    }

    @Override
    public Producto buscarPorId(Long id) {
        if (id == null){
            throw new IllegalArgumentException("El Id es obligatorio");
        }

       return productoRepository.findById(id).orElseThrow
                (() -> new ProductoNoEncontrado(id));


    }
}
