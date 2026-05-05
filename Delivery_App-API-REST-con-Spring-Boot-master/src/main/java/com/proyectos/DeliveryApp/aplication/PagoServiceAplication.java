package com.proyectos.DeliveryApp.aplication;

import com.proyectos.DeliveryApp.domain.Exception.PedidoNoEncontradoException;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pago;
import com.proyectos.DeliveryApp.domain.ports.in.PagoService;
import com.proyectos.DeliveryApp.domain.ports.out.PagoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.PedidoRepositoryOutPorts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@RequiredArgsConstructor
@Service
public class PagoServiceAplication implements PagoService {

    private final PedidoRepositoryOutPorts repository;

    private final PagoRepositoryOutPorts pagoRepository;


    @Override
    public Pago Pagar(Long pedidoId, Pago pago){

        if (pedidoId == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }

        var pedido = repository.findById(pedidoId).orElseThrow(() ->
                new PedidoNoEncontradoException(pedidoId));


        if (pedido.getEstadoPedido() == EstadoPedido.PAGADO){
            throw  new IllegalArgumentException("EL pedido ya esta pago");
        }

        Pago pago2;

        if (pedido.getEstadoPedido() == EstadoPedido.EN_CAMINO) {
            var numeroT = numCard(pago);

            pago2 = Pago.builder()
                    .pedidoId(pedido.getId())
                    .nombreComprador(pago.getNombreComprador())
                    .numeroTarjeta(numeroT)
                    .fecha(LocalDate.now())
                    .costoFinal(pedido.getTotal())
                    .build();

            pedido.setEstadoPedido(EstadoPedido.PAGADO);
        }
        else {
            pago2 = Pago.builder()
                    .pedidoId(pedido.getId())
                    .nombreComprador(pago.getNombreComprador())
                    .fecha(LocalDate.now())
                    .costoFinal(BigDecimal.valueOf(0.0))
                    .build();

            pedido.setEstadoPedido(EstadoPedido.PAGO_RECHAZADO);
        }
        repository.save(pedido);

        pagoRepository.save(pago2);
        return pago2;

    }

    private String numCard(Pago payment){
        var tarjeta = payment.getNumeroTarjeta();

        String mascarTarjeta = "****" + tarjeta.substring(tarjeta.length() - 4);

        return  mascarTarjeta;
    }


    @Override
    public Pago buscarPagoId(Long id){

        if (id == null){
            throw  new IllegalArgumentException("EL id no puede ser nulo");
        }

        var pago = pagoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("El pago no fue encontrado"));

        return pago;
    }
}
