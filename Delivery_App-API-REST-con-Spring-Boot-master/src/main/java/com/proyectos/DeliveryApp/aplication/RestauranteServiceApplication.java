package com.proyectos.DeliveryApp.aplication;

import com.proyectos.DeliveryApp.domain.Exception.RestauranteNoEncontradoException;
import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.in.ServiceRestaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestauranteServiceApplication implements ServiceRestaurante {

    private final RestauranteRepositoryOutPorts repository;

    @Override
    public List<Restaurante> listar() {
        return repository.findAll();
    }

    @Override
    public Restaurante crear(Restaurante restaurante){
        if (restaurante.getId() != null){
            throw new IllegalArgumentException("El id no debe enviarse");
        }
        if (restaurante.getEstado() == null){
            throw new IllegalArgumentException("No se puede crear un restaurante con estado nulo");
        }
        return repository.save(restaurante);
    }

    @Override
    public Restaurante actualizar(Long id, Restaurante restaurante){

        Restaurante restauranteExistente = repository.findById(id)
                .orElseThrow(() -> new RestauranteNoEncontradoException(id));

        restauranteExistente.setNombre(restaurante.getNombre());
        restauranteExistente.setDireccion(restaurante.getDireccion());

        return repository.save(restauranteExistente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}

