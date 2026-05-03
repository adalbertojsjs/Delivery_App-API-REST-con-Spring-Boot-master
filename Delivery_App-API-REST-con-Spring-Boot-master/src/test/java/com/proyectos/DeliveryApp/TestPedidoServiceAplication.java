package com.proyectos.DeliveryApp;


import com.proyectos.DeliveryApp.aplication.PedidoServiceAplication;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestPedidoServiceAplication {


    @Mock
    PedidoRepositoryOutPorts repository;

    @Mock
    UsuarioRepositoryOutPorts repositoryOutPorts;

    @InjectMocks
    PedidoServiceAplication aplication;


    //FindAll Orders

    @Test
    void shouldGetAllOrdersSuccessfully() {

        List<Pedido> lista = List.of(Pedido.
                builder().
                id(1L).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.CREADO).
                clienteId(1L).
                repartidorId(1L).
                restauranteId(1L).
                build(), Pedido.
                builder().
                id(2L).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.CANCELADO).
                clienteId(2L).
                repartidorId(2L).
                restauranteId(2L).
                build());

        when(repository.findAll()).thenReturn(lista);

        var result = aplication.listar();

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

        var result = aplication.crear(order);

        assertNotNull(result);
        assertEquals(2L, result.getRepartidorId());
        assertEquals(1L, result.getClienteId());
        assertEquals(3L, result.getRestauranteId());

        verify(repository).save(order);
        verify(repositoryOutPorts).findById(user.getId());
    }

    @Test
    void shouldThrowExceptionWhenOrderIsNull() {
        Pedido pedido = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.crear(pedido));

        log.info(exception.getMessage());
        assertEquals("El pedido no puede ser nulo", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenClientIdIsNull() {
        var order = Pedido.
                builder().
                total(BigDecimal.valueOf(7000)).
                clienteId(null).
                repartidorId(2L).
                restauranteId(3L).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.crear(order));

        log.info(exception.getMessage());
        assertEquals("El id del cliente no puede ser nulo", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIdIsNull() {
        var order = Pedido.
                builder().
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(null).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.crear(order));

        log.info(exception.getMessage());
        assertEquals("El id del restaurante no puede ser nulo", exception.getMessage());

        verify(repository, never()).save(any());
    }

    //Canceled Orders

    @Test
    void shouldCancelOrderSuccessfully() {

        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        aplication.cancelar(order.getId());

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

    @Test
    void shouldThrowExceptionWhenOrderIdIsNull() {
        var order = Pedido.
                builder().
                id(null).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> aplication.cancelar(order.getId()));

        log.info(exception.getMessage());
        assertEquals("El ID es obligatorio", exception.getMessage());

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    //Update Status Order

    @Test
    void shouldUpdateOrderStatus() {
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        aplication.cambiarEstado(order.getId(), EstadoPedido.EN_CAMINO);

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

    @Test
    void shouldThrowExceptionWhenChangingStatusWithNullId() {
        var order = Pedido.
                builder().
                id(null).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> aplication.cambiarEstado(order.getId(), EstadoPedido.EN_CAMINO));

        log.info(exception.getMessage());

        assertEquals("El ID es obligatorio", exception.getMessage());

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenOrderIsDeliveredOrCancelled() {
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CANCELADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> aplication.cambiarEstado(order.getId(), EstadoPedido.EN_CAMINO));

        log.info(exception.getMessage());

        assertEquals("El estado no se puede modificar una vez este finalizado", exception.getMessage());

        verify(repository).findById(order.getId());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenChangingStatusOfFinalizedOrder() {
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.ENTREGADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> aplication.cambiarEstado(order.getId(), EstadoPedido.EN_CAMINO));

        log.info(exception.getMessage());

        assertEquals("El estado no se puede modificar una vez este finalizado", exception.getMessage());

        verify(repository).findById(order.getId());
        verify(repository, never()).save(any());
    }

    //Asignar Repartidor

    @Test
    void shouldAssignDriverToOrder() {
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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));
        when(repositoryOutPorts.findById(userRpartidor.getId())).thenReturn(Optional.of(userRpartidor));

        aplication.asignarRepartidor(order.getId(), userRpartidor.getId());

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);

        verify(repository).save(captor.capture());

        Pedido result = captor.getValue();

        System.out.println(result.getRepartidorId());
        assertNotNull(result);
        assertEquals(1L, result.getRepartidorId());

        verify(repository).findById(order.getId());
        verify(repositoryOutPorts).findById(userRpartidor.getId());
        verify(repository).save(order);
    }

    @Test
    void shouldThrowExceptionWhenOderIdIsNullForAssign() {
        Long idRepartidor = 1L;
        var order = Pedido.
                builder().
                id(null).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.asignarRepartidor(order.getId(), idRepartidor));

        log.info(exception.getMessage());

        assertEquals("El ID del pedido es obligatorio", exception.getMessage());

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
        verify(repositoryOutPorts, never()).findById(any());
    }

    @Test
    void shouldThrowExceptionWhenDealerIdIsNullForAssign() {//dealer según el traductor es repartidor
        var order = Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.asignarRepartidor(order.getId(), null));

        log.info(exception.getMessage());

        assertEquals("El ID del repartidor es obligatorio", exception.getMessage());

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
        verify(repositoryOutPorts, never()).findById(any());
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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(2L).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.asignarRepartidor(order.getId(), userRpartidor.getId()));

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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));
        when(repositoryOutPorts.findById(userRpartidor.getId())).thenReturn(Optional.of(userRpartidor));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.asignarRepartidor(order.getId(), userRpartidor.getId()));

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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.asignarRepartidor(order.getId(), userRpartidor.getId()));

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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.asignarRepartidor(order.getId(), userRpartidor.getId()));

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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(userClient.getId()).
                repartidorId(null).
                restauranteId(3L).
                build(), Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(userClient.getId()).
                repartidorId(null).
                restauranteId(4L).
                build());

        when(repository.findByClienteId(userClient.getId())).thenReturn(lista);

        var result = aplication.obtenerPedidosPorCliente(userClient.getId());

        System.out.println(result.size());
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(EstadoPedido.CANCELADO, result.getFirst().getEstadoPedido());
        assertEquals(EstadoPedido.CREADO, result.getLast().getEstadoPedido());

        verify(repository).findByClienteId(userClient.getId());
    }

    @Test
    void shouldThrowExceptionWhenClientIdIsNull_GetOrdersByClientId() {
        var userClient = Usuario.
                builder().
                id(null).
                nombre("random44455").
                email("random888").
                contrasena("random66").
                rol(Rol.CLIENTE).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.obtenerPedidosPorCliente(userClient.getId()));

        log.info(exception.getMessage());
        assertEquals("El ID del cliente es obligatorio", exception.getMessage());

        verify(repository, never()).findByClienteId(any());

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
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(restaurant.getId()).
                build(), Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(restaurant.getId()).
                build());

        when(repository.findByRestauranteId(restaurant.getId())).thenReturn(lista);

        var result = aplication.obtenerPedidosPorRestaurante(restaurant.getId());

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals(EstadoPedido.CREADO, result.getLast().getEstadoPedido());
        assertEquals(2, result.size());

        verify(repository).findByRestauranteId(restaurant.getId());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIdIsNull_GetByRestaurantId(){
        var restaurant = Restaurante.
                builder().
                id(null).
                nombre("random000").
                direccion("random44").
                estado(EstadoRestaurante.ABIERTO).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.obtenerPedidosPorRestaurante(restaurant.getId()));

        log.info(exception.getMessage());
        assertEquals("El ID del restaurante es obligatorio",exception.getMessage());

        verify(repository, never()).findByRestauranteId(any());
    }

    //Get By Status
    @Test
    void shouldGetAllOrdersByStatusSuccessfully(){
        EstadoPedido estadoPedido = EstadoPedido.CREADO;
        List<Pedido> lista = List.of(Pedido.
                builder().
                id(1L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(1L).
                build(), Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(3L).
                build());

        when(repository.findByEstadoPedido(estadoPedido)).thenReturn(lista);

        var result = aplication.obtenerPorEstado(estadoPedido);

        assertNotNull(result);
        assertEquals(2L,result.size());
        assertEquals(EstadoPedido.CREADO,result.getFirst().getEstadoPedido());

        verify(repository).findByEstadoPedido(estadoPedido);
    }

    @Test
    void shouldThrowExceptionStatusIsNull_GetByStatus(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> aplication.obtenerPorEstado(null));

        log.info(exception.getMessage());
        assertEquals("El ESTADO del pedido es obligatorio",exception.getMessage());

        verify(repository, never()).findByEstadoPedido(any());

    }

    //Get By IdOrders

    @Test
    void shouldGetOrderByIdSuccessfully(){

        var order = Pedido.
                builder().
                id(2L).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(2L).
                build();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));

        var result = aplication.buscarPorId(order.getId());

        assertNotNull(result);
        assertEquals(EstadoPedido.CREADO,result.getEstadoPedido());
        assertEquals(2L,result.getId());
        assertEquals(1L,result.getClienteId());
        assertEquals(2L,result.getRestauranteId());

        verify(repository).findById(order.getId());
    }

    @Test
    void shouldThrowExceptionOrderIdIsNull_GetById(){
        var order = Pedido.
                builder().
                id(null).
                estadoPedido(EstadoPedido.CREADO).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(8990)).
                clienteId(1L).
                repartidorId(null).
                restauranteId(2L).
                build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> aplication.buscarPorId(order.getId()));

        log.info(exception.getMessage());
        assertEquals("El ID del pedido es obligatorio",exception.getMessage());

        verify(repository,never()).findById(any());
    }
}
