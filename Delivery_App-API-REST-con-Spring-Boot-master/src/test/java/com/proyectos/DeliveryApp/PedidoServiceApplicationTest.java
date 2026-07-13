package com.proyectos.DeliveryApp;


import com.proyectos.DeliveryApp.aplication.PedidoServiceApplication;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.domain.ports.out.PedidoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.UsuarioRepositoryOutPorts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PedidoServiceApplicationTest {


    @Mock
    PedidoRepositoryOutPorts repository;

    @Mock
    UsuarioRepositoryOutPorts repositoryOutPorts;

    @InjectMocks
    PedidoServiceApplication application;


    //FindAll Orders

    @Test
    void shouldGetAllOrdersSuccessfully() {

        List<Pedido> lista = List.of(Pedido.
                builder().
                id(1L).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.CREADO).
                clienteId(1L).
                repartidorId(1L).
                restauranteId(1L).
                build(), Pedido.
                builder().
                id(2L).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.CANCELADO).
                clienteId(2L).
                repartidorId(2L).
                restauranteId(2L).
                build());

        when(repository.findAll()).thenReturn(lista);

        var result = application.listar();

        log.info("Cantidad de resultados: {}", result.size());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(EstadoPedido.CREADO, result.getFirst().getEstadoPedido());
        assertEquals(EstadoPedido.CANCELADO, result.getLast().getEstadoPedido());

        verify(repository).findAll();
    }


    //Create Orders

    @Test
    void createProductSuccessfully() {

        var user = Usuario
                .builder()
                .id(1L)
                .nombre("random333")
                .build();
        var order = Pedido.
                builder().
                total(BigDecimal.valueOf(7000)).
                clienteId(user.getId()).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.save(order)).thenReturn(order);
        when(repositoryOutPorts.findById(order.getClienteId())).thenReturn(Optional.of(user));

        var result = application.crear(order);

        assertNotNull(result);
        assertEquals(2L, result.getRepartidorId());
        assertEquals(1L, result.getClienteId());
        assertEquals(3L, result.getRestauranteId());

        verify(repository).save(order);
        verify(repositoryOutPorts).findById(user.getId());
    }


    @ParameterizedTest
    @MethodSource("invalidOrder")
    void shouldThrowException(Pedido pedido, String expectedMessage){

        var exception = assertThrows(IllegalArgumentException.class, () -> application.crear(pedido));

        assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> invalidOrder(){

        Pedido orderIsNull = null;
        var orderClienteIdIsNull = Pedido.
                builder().
                total(BigDecimal.valueOf(7000)).
                clienteId(null).
                repartidorId(2L).
                restauranteId(3L).
                build();

        var orderRestauranteIdIsNull = Pedido.
                builder().
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(null).
                build();

        return Stream.of(
                Arguments.of(orderIsNull, "El pedido no puede ser nulo"),
                Arguments.of(orderClienteIdIsNull, "El id del cliente no puede ser nulo"),
                Arguments.of(orderRestauranteIdIsNull, "El id del restaurante no puede ser nulo"));
    }

    //Canceled Orders

    @Test
    void shouldCancelOrderSuccessfully() {

        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        application.cancelar(order.getId());

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);

        verify(repository).save(captor.capture());

        Pedido result = captor.getValue();

        System.out.println(result.getEstadoPedido());
        assertNotNull(result);
        assertEquals(EstadoPedido.CANCELADO, result.getEstadoPedido());
        assertEquals(1L, result.getId());
        assertEquals(2L, result.getRepartidorId());
        assertEquals(1L, result.getClienteId());
        assertEquals(3L, result.getRestauranteId());

        verify(repository).findById(order.getId());
        verify(repository).save(order);
    }


    //Update Status Order

    @Test
    void shouldUpdateOrderStatus() {
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        application.cambiarEstado(order.getId(), EstadoPedido.EN_CAMINO);

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);

        verify(repository).save(captor.capture());

        Pedido result = captor.getValue();

        System.out.println(result.getEstadoPedido());
        assertNotNull(result);
        assertEquals(EstadoPedido.EN_CAMINO, result.getEstadoPedido());
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(7000), result.getTotal());

        verify(repository).findById(order.getId());
        verify(repository).save(order);
    }


    @ParameterizedTest
    @MethodSource("finishedOrders")
    void shouldThrowExceptionInUpdate(Pedido pedido,String expectedMessage){

        when(repository.findById(pedido.getId())).thenReturn(Optional.of(pedido));

        var exception = assertThrows(IllegalStateException.class,
                ()-> application.cambiarEstado(pedido.getId(), EstadoPedido.CREADO));

        assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> finishedOrders(){

        var orderIsEntregada = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.ENTREGADO).
                build();

        var orderIsCancell = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CANCELADO).
                build();

        return Stream.of(
                Arguments.of(orderIsEntregada,"El estado no se puede modificar una vez este finalizado"),
                Arguments.of(orderIsCancell,"El estado no se puede modificar una vez este finalizado")
        );
    }

    //Asignar Repartidor

    @Test
    void shouldAssignDriverToOrder() {
        var userRepartidor = Usuario.
                builder().
                id(1L).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.REPARTIDOR).
                build();
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));
        when(repositoryOutPorts.findById(userRepartidor.getId())).thenReturn(Optional.of(userRepartidor));

        application.asignarRepartidor(order.getId(), userRepartidor.getId());

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);

        verify(repository).save(captor.capture());

        Pedido result = captor.getValue();

        System.out.println(result.getRepartidorId());
        assertNotNull(result);
        assertEquals(1L, result.getRepartidorId());

        verify(repository).findById(order.getId());
        verify(repositoryOutPorts).findById(userRepartidor.getId());
        verify(repository).save(order);
    }


    @Test
    void shouldThrowExceptionWhenOrderAlreadyHasAssignedDriver() {
        var userRpartidor = Usuario.
                builder().
                id(1L).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.REPARTIDOR).
                build();
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> application.asignarRepartidor(order.getId(), userRpartidor.getId()));

        log.info(exception.getMessage());

        assertEquals("El pedido ya tiene repartidor asignado", exception.getMessage());

        verify(repository).findById(order.getId());
        verify(repository, never()).save(any());
        verify(repositoryOutPorts, never()).findById(any());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotDeliveryDriver() {
        var userRpartidor = Usuario.
                builder().
                id(1L).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.CLIENTE).
                build();
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));
        when(repositoryOutPorts.findById(userRpartidor.getId())).thenReturn(Optional.of(userRpartidor));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> application.asignarRepartidor(order.getId(), userRpartidor.getId()));

        log.info(exception.getMessage());

        assertEquals("El usuario no es repartidor", exception.getMessage());

        verify(repository).findById(order.getId());
        verify(repository, never()).save(any());
        verify(repositoryOutPorts).findById(userRpartidor.getId());
    }

    @Test
    void shouldThrowExceptionWhenAssigningDriverToFinalizedOrder() {//ENTREGADO
        var userRpartidor = Usuario.
                builder().
                id(1L).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.REPARTIDOR).
                build();
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.ENTREGADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> application.asignarRepartidor(order.getId(), userRpartidor.getId()));

        log.info(exception.getMessage());

        assertEquals("No se puede asignar repartidor a un pedido finalizado", exception.getMessage());

        verify(repository).findById(order.getId());
        verify(repository, never()).save(any());
        verify(repositoryOutPorts, never()).findById(any());

    }

    @Test
    void shouldThrowExceptionWhenAssigningDriverToCancelOrder() {//CANCELADO
        var userRpartidor = Usuario.
                builder().
                id(1L).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.REPARTIDOR).
                build();
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CANCELADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> application.asignarRepartidor(order.getId(), userRpartidor.getId()));

        log.info(exception.getMessage());

        assertEquals("No se puede asignar repartidor a un pedido finalizado", exception.getMessage());

        verify(repository).findById(order.getId());
        verify(repository, never()).save(any());
        verify(repositoryOutPorts, never()).findById(any());
    }

    //Get Orders By Users(Client)
    @Test
    void shouldGetAllOrdersByClientSuccessfully() {
        var userClient = Usuario.
                builder().
                id(1L).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.CLIENTE).
                build();

        List<Pedido> lista = List.of(Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CANCELADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(userClient.getId()).
                repartidorId(null).
                restauranteId(3L).
                build(), Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(userClient.getId()).
                repartidorId(null).
                restauranteId(4L).
                build());

        when(repository.findByClienteId(userClient.getId())).thenReturn(lista);

        var result = application.obtenerPedidosPorCliente(userClient.getId());

        System.out.println(result.size());
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(EstadoPedido.CANCELADO, result.getFirst().getEstadoPedido());
        assertEquals(EstadoPedido.CREADO, result.getLast().getEstadoPedido());

        verify(repository).findByClienteId(userClient.getId());
    }


    //Get Ordes By restaurant

    @Test
    void shouldGetAllOrdersByRestaurantSuccessfully() {

        var restaurant = Restaurante.
                builder().
                id(1L).
                nombre("random000").
                direccion("random44").
                estado(EstadoRestaurante.ABIERTO).
                build();

        List<Pedido> lista = List.of(Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CANCELADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(restaurant.getId()).
                build(), Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(restaurant.getId()).
                build());

        when(repository.findByRestauranteId(restaurant.getId())).thenReturn(lista);

        var result = application.obtenerPedidosPorRestaurante(restaurant.getId());

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals(EstadoPedido.CREADO, result.getLast().getEstadoPedido());
        assertEquals(2, result.size());

        verify(repository).findByRestauranteId(restaurant.getId());
    }


    //Get By Status
    @Test
    void shouldGetAllOrdersByStatusSuccessfully() {
        EstadoPedido estadoPedido = EstadoPedido.CREADO;
        List<Pedido> lista = List.of(Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(1L).
                build(), Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build());

        when(repository.findByEstadoPedido(estadoPedido)).thenReturn(lista);

        var result = application.obtenerPorEstado(estadoPedido);

        assertNotNull(result);
        assertEquals(2L, result.size());
        assertEquals(EstadoPedido.CREADO, result.getFirst().getEstadoPedido());

        verify(repository).findByEstadoPedido(estadoPedido);
    }

    //Get By IdOrders

    @Test
    void shouldGetOrderByIdSuccessfully() {

        var order = Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026, 1, 1, 10, 0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(2L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        var result = application.buscarPorId(order.getId());

        assertNotNull(result);
        assertEquals(EstadoPedido.CREADO, result.getEstadoPedido());
        assertEquals(2L, result.getId());
        assertEquals(1L, result.getClienteId());
        assertEquals(2L, result.getRestauranteId());

        verify(repository).findById(order.getId());
    }

}
