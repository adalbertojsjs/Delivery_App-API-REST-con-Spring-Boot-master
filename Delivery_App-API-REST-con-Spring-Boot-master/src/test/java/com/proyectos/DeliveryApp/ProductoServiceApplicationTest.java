package com.proyectos.DeliveryApp;


import com.proyectos.DeliveryApp.aplication.ProductoServiceApplication;
import com.proyectos.DeliveryApp.domain.Exception.RestauranteNoEncontradoException;
import com.proyectos.DeliveryApp.domain.enums.Disponible;
import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import com.proyectos.DeliveryApp.domain.model.Producto;
import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.ports.out.ProductoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ProductoServiceApplicationTest {

    @Mock
    ProductoRepositoryOutPorts repository;
    @Mock
    RestauranteRepositoryOutPorts restauranteRepositoryOutPorts;

    @InjectMocks
    ProductoServiceApplication aplication;


    //Metodo listar
    @Test
    void shouldGetAllProductsSuccessfully(){

        List<Producto> lista = List.of(new Producto(), new Producto());

        when(repository.findAll()).thenReturn(lista);

        var result = aplication.listar();


        log.info("Cantidad de resultados: {}", result.size());
        assertNotNull(result);
        assertEquals(2,result.size());

        verify(repository).findAll();
    }

    //Metodo Crear
    @Test
    void createProductSuccessfully(){

        var producto = Producto.
                builder().
                nombre("Random333").
                precio(BigDecimal.valueOf(200)).
                disponible(Disponible.DISPONIBLE).
                restauranteId(1L).
                build();

        when(repository.save(producto)).thenReturn(producto);

        var result = aplication.crear(producto);

        log.info(result.getNombre());
        assertEquals("Random333",result.getNombre());
        assertEquals(Disponible.DISPONIBLE,result.getDisponible());

        verify(repository).save(any(Producto.class));
    }

    @Test
    void shouldThrowExceptionWhenIdIsProvided(){

        var producto = Producto.
                builder().
                id(1L).
                nombre("Random333").
                precio(BigDecimal.valueOf(200)).
                disponible(Disponible.DISPONIBLE).
                restauranteId(1L).
                build();

      IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> aplication.crear(producto));

        log.info(exception.getMessage());

        verify(repository, never()).save(any());

    }

    @Test
    void shouldThrowExceptionWhenStatusIsOutOfStock(){

        var producto = Producto.
                builder().
                nombre("Random333").
                precio(BigDecimal.valueOf(200)).
                disponible(Disponible.AGOTADO).
                restauranteId(1L).
                build();

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> aplication.crear(producto));

        log.info(exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductIsNull(){
         Producto producto = null;

        System.out.println(producto);
       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> aplication.crear(producto));

        log.info(exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRestauratIdIsNull(){

        var producto = Producto.
                builder().
                nombre("Random333").
                precio(BigDecimal.valueOf(200)).
                disponible(Disponible.DISPONIBLE).
                restauranteId(null).
                build();

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
               ()-> aplication.crear(producto));

        log.info(exception.getMessage());

        verify(repository, never()).save(any());
    }

    // Metodo cambiarDisponibilidad
    @Test
    void shouldChangeProductAvailabilitySuccessfully(){

        Long id = 1L;
        var producto = Producto.
                builder().
                id(id).
                nombre("Random333").
                precio(BigDecimal.valueOf(778)).
                disponible(Disponible.AGOTADO).
                restauranteId(1L).
                build();

        when(repository.save(producto)).thenReturn(producto);
        when(repository.findById(id)).thenReturn(Optional.of(producto));

        var result = aplication.cambiarDisponibilidad(id,Disponible.DISPONIBLE);


        log.info(result.getNombre());
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(778),result.getPrecio());
        assertEquals(1L,result.getRestauranteId());
        assertEquals("Random333",result.getNombre());
        assertEquals(Disponible.DISPONIBLE,result.getDisponible());

        verify(repository).save(any(Producto.class));
        verify(repository).findById(id);
    }


    @Test
    void shouldThrowExceptionWhenavailableIsNull(){

      Disponible disponible = null ;
        Long id = 1L;

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() ->
                aplication.cambiarDisponibilidad(id , disponible));

        log.info(exception.getMessage());
        verify(repository, never()).save(any());
    }

    //Metodo ListarRestaurante
    @Test
    void shouldGetProductsByRestaurantSuccessfully() {

        var restaurante = Restaurante.
                builder().
                id(1L).
                nombre("Restaurante la buena mesa").
                direccion("random99").
                estado(EstadoRestaurante.ABIERTO).
                build();

        List<Producto> lista = List.of(Producto.
                builder().
                id(1L).
                nombre("Random333").
                precio(BigDecimal.valueOf(778)).
                disponible(Disponible.DISPONIBLE).
                restauranteId(restaurante.getId()).
                build(), Producto.
                builder().
                id(1L).
                nombre("Random444").
                precio(BigDecimal.valueOf(778)).
                disponible(Disponible.DISPONIBLE).
                restauranteId(restaurante.getId()).
                build());

        when(repository.findByRestauranteId(restaurante.getId())).thenReturn(lista);
        when(restauranteRepositoryOutPorts.existsById(restaurante.getId())).thenReturn(true);

        var result = aplication.listarPorRestaurante(restaurante.getId());

        log.info(result.getLast().getNombre());
        log.info(result.getFirst().getNombre());
        assertNotNull(result);
        assertEquals("Random333",result.getFirst().getNombre());
        assertEquals("Random444",result.getLast().getNombre());
        assertEquals(2, result.size());

        verify(repository).findByRestauranteId(restaurante.getId());
        verify(restauranteRepositoryOutPorts).existsById(restaurante.getId());
    }


    @Test
    void shouldThrowExceptionWhenRestaurantIdDoesNotExist(){

        Long id = 4L;
        RestauranteNoEncontradoException exception =
                assertThrows(RestauranteNoEncontradoException.class,()-> aplication.listarPorRestaurante(id));

        log.info(exception.getMessage());
        verify(repository, never()).findByRestauranteId(any());
        verify(restauranteRepositoryOutPorts).existsById(id);


    }


    //Metodo BuscarId
    @Test
    void shouldGetProductByIdSuccessfully(){

        var producto = Producto.
                builder().
                id(1L).
                nombre("Random333").
                precio(BigDecimal.valueOf(778)).
                disponible(Disponible.DISPONIBLE).
                restauranteId(1L).
                build();


        when(repository.findById(producto.getId())).thenReturn(Optional.of(producto));

        var result = aplication.buscarPorId(producto.getId());

        log.info(result.getNombre());
        assertNotNull(result);
        assertEquals(1L,result.getRestauranteId());
        assertEquals("Random333",result.getNombre());
    }
}
