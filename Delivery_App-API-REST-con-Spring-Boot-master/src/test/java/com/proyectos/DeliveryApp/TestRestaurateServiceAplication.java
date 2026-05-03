package com.proyectos.DeliveryApp;


import com.proyectos.DeliveryApp.aplication.RestauranteServiceAplication;
import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestRestaurateServiceAplication {

    @Mock
    RestauranteRepositoryOutPorts repository;

    @InjectMocks
    RestauranteServiceAplication aplication;


    //FinAll Restaurant
    @Test
    void shouldGetAllRestaurantsSuccessfully(){

        List<Restaurante> lista =
                List.of(Restaurante.
                        builder().
                        id(1L).
                        nombre("La buena mesa").
                        direccion("random222").
                        estado(EstadoRestaurante.CERRADO).
                        build(),
                        Restaurante.
                        builder().
                        id(2L).
                        nombre("Random777").
                        direccion("random88").
                        estado(EstadoRestaurante.ABIERTO).
                        build());

        when(repository.findAll()).thenReturn(lista);

        var result = aplication.listar();
        System.out.println(result.getLast().getNombre());
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(EstadoRestaurante.ABIERTO,result.getLast().getEstado());
        assertEquals("Random777",result.getLast().getNombre());
        assertEquals("La buena mesa",result.getFirst().getNombre());

        verify(repository).findAll();
    }


    //Create Restaurant
    @Test
    void createRestaurantSuccessfully() {

        var restaurante = Restaurante.
                builder().
                id(1L).
                nombre("La buena mesa").
                direccion("random222").
                estado(EstadoRestaurante.CERRADO).
                build();

        when(repository.save(restaurante)).thenReturn(restaurante);

        var result = aplication.crear(restaurante);

        assertNotNull(result);
        assertEquals("La buena mesa", result.getNombre());
        assertEquals("random222", result.getDireccion());
        assertEquals(EstadoRestaurante.CERRADO, result.getEstado());


        verify(repository).save(restaurante);
    }

    //Update Restaurant
    @Test
    void shouldUpdateRestaurantSuccessfully(){

        var restaurante = Restaurante.
                builder().
                id(1L).
                nombre("La buena mesa").
                direccion("random222").
                estado(EstadoRestaurante.CERRADO).
                build();

        var restaurante3 = Restaurante.
                builder().
                nombre("La buena mesa 42").
                direccion("random444").
                estado(EstadoRestaurante.ABIERTO).
                build();

        when(repository.save(restaurante)).thenReturn(restaurante);
        when(repository.findById(restaurante.getId())).thenReturn(Optional.of(restaurante));

        var result = aplication.actualizar(restaurante.getId(),restaurante3);

        assertNotNull(result);
        assertEquals("La buena mesa 42",result.getNombre());
        assertEquals("random444",result.getDireccion());

        verify(repository).save(restaurante);
        verify(repository).findById(restaurante.getId());
    }


    //Delete Restaurant

    @Test
    void shouldDeleteRestaurantSuccessfully(){
        var restaurante = Restaurante.
                builder().
                id(1L).
                nombre("La buena mesa").
                direccion("random222").
                estado(EstadoRestaurante.CERRADO).
                build();


          aplication.eliminar(restaurante.getId());

          verify(repository).deleteById(restaurante.getId());

    }



}
