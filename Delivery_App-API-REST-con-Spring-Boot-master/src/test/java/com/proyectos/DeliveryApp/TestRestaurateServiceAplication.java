package com.proyectos.DeliveryApp;


import com.proyectos.DeliveryApp.aplication.RestauranteServiceAplication;
import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
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

    @Test
    void shouldThrowExceptionRestaurantIdIsNull(){
        var restaurante = Restaurante.
                builder().
                id(2L).
                nombre("La buena mesa").
                direccion("random222").
                estado(EstadoRestaurante.CERRADO).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> aplication.crear(restaurante));

        log.info(exception.getMessage());

        assertEquals("El id no debe enviarse",exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionStatusRestaurantsNull(){
        var restaurante = Restaurante.
                builder().
                nombre("La buena mesa").
                direccion("random222").
                estado(null).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> aplication.crear(restaurante));

        log.info(exception.getMessage());

        assertEquals("No se puede crear un restaurante con estado nulo",exception.getMessage());

        verify(repository,never()).save(any());
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

    @Test
    void shouldThrowExceptionRestaurantIdIsNull_Update(){
        var restaurante = Restaurante.
                builder().
                nombre("La buena mesa").
                direccion("random222").
                estado(EstadoRestaurante.CERRADO).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> aplication.actualizar(null,restaurante));

        log.info(exception.getMessage());

        assertEquals("El id no puede ser nulo",exception.getMessage());

        verify(repository, never()).findById(any());
        verify(repository,never()).save(any());
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
