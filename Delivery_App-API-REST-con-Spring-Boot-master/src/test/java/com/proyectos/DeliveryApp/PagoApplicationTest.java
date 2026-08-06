package com.proyectos.DeliveryApp;

import com.proyectos.DeliveryApp.aplication.PagoServiceApplication;
import com.proyectos.DeliveryApp.domain.Exception.PedidoNoEncontradoException;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pago;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.domain.ports.out.PagoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.PedidoRepositoryOutPorts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PagoApplicationTest {


    @Mock
    PagoRepositoryOutPorts repository;

    @Mock
    PedidoRepositoryOutPorts pedidoRepository;

    @InjectMocks
    PagoServiceApplication aplication;


    //Create Payment
    @Test
    void shouldPayOrderSuccessfully(){

       var pedido = Pedido.
                builder().
                id(1L).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.EN_CAMINO).
                clienteId(1L).
                repartidorId(1L).
                restauranteId(1L).
                build();

       var pago = Pago.
               builder().
               nombreComprador("random999").
               numeroTarjeta("484348374").
               build();

        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));
        when(repository.save(any())).thenReturn(pago);
        when(pedidoRepository.save(any())).thenReturn(pedido);

        aplication.Pagar(pedido.getId(),pago);

        ArgumentCaptor<Pedido> captorPedido = ArgumentCaptor.forClass(Pedido.class);
        ArgumentCaptor<Pago> captorPago = ArgumentCaptor.forClass(Pago.class);

        verify(repository).save(captorPago.capture());
        verify(pedidoRepository).save(captorPedido.capture());

        Pedido pedido1 = captorPedido.getValue();
        Pago pago1 = captorPago.getValue();

        assertNotNull(pago1);
        assertNotNull(pedido1);
        assertEquals(EstadoPedido.PAGADO,pedido1.getEstadoPedido());
        assertEquals("random999", pago1.getNombreComprador());
        assertEquals("****8374",pago1.getNumeroTarjeta());

        verify(repository).save(pago1);
        verify(pedidoRepository).save(pedido1);
        verify(pedidoRepository).findById(pedido.getId());
    }

    @Test
    void shouldCreatePaymentWithRejectedStatus(){
        var pedido = Pedido.
                builder().
                id(1L).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.CREADO).
                clienteId(1L).
                repartidorId(1L).
                restauranteId(1L).
                build();

        var pago = Pago.
                builder().
                nombreComprador("random999").
                numeroTarjeta("484348374").
                build();

        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));

        aplication.Pagar(pedido.getId(),pago);

        ArgumentCaptor<Pedido> captorPedido = ArgumentCaptor.forClass(Pedido.class);
        ArgumentCaptor<Pago> captorPago = ArgumentCaptor.forClass(Pago.class);

        verify(repository).save(captorPago.capture());
        verify(pedidoRepository).save(captorPedido.capture());

        Pedido pedido1 = captorPedido.getValue();
        Pago pago1 = captorPago.getValue();

        assertEquals(EstadoPedido.PAGO_RECHAZADO,pedido1.getEstadoPedido());
        assertEquals("random999", pago1.getNombreComprador());

        verify(pedidoRepository).findById(pedido.getId());
        verify(repository).save(any());
        verify(pedidoRepository).save(any());
    }


    @Test
    void shouldThrowExceptionWhenStatusIsPaid(){

        var pedido = Pedido.
                builder().
                id(1L).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.PAGADO).
                clienteId(1L).
                repartidorId(1L).
                restauranteId(1L).
                build();

        var pago = Pago.
                builder().
                nombreComprador("random999").
                numeroTarjeta("484348374").
                build();

        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));

      var exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.Pagar(pedido.getId(),pago));

      log.info(exception.getMessage());

      assertEquals("EL pedido ya esta pago",exception.getMessage());

        verify(pedidoRepository).findById(pedido.getId());
        verify(repository,never()).save(any());
        verify(pedidoRepository, never()).save(any());

    }

    @Test
    void shouldThrowOrderNotFoundException(){

        Long id = 13L;

        var pago = Pago.
                builder().
                nombreComprador("random999").
                numeroTarjeta("484348374").
                build();

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(PedidoNoEncontradoException.class,
                ()-> aplication.Pagar(id,pago));

        log.info(exception.getMessage());

        assertEquals("Pedido con id " + id + " no fue encontrado",exception.getMessage());

        verify(pedidoRepository, times(1)).findById(id);
        verify(repository,never()).save(any());
        verify(pedidoRepository, never()).save(any());
    }
    //Get Payment y ID

    @Test
    void shouldGetPaymentByIdSuccessfully(){
        var pedido = Pedido.
                builder().
                id(1L).
                fecha(LocalDateTime.of(2026,1,1,10,0)).
                total(BigDecimal.valueOf(7000)).
                estadoPedido(EstadoPedido.PAGADO).
                clienteId(1L).
                repartidorId(1L).
                restauranteId(1L).
                build();

        var pago = Pago.
                builder().
                id(1L).
                pedidoId(pedido.getId()).
                costoFinal(pedido.getTotal()).
                nombreComprador("random999").
                numeroTarjeta("484344744").
                build();

        when(repository.findById(pago.getId())).thenReturn(Optional.of(pago));

        var result = aplication.buscarPagoId(pago.getId());

        assertEquals("random999",result.getNombreComprador());
        assertEquals("484344744",result.getNumeroTarjeta());

        verify(repository).findById(pago.getId());
    }



    @Test
    void shouldThrowPaymentNotFoundException(){

        Long id = 4L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class,
                () -> aplication.buscarPagoId(id));

        log.info(exception.getMessage());

        assertEquals("El pago no fue encontrado", exception.getMessage());

        verify(repository).findById(id);

    }

}
