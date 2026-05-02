package com.proyectos.DeliveryApp.infraestructure.http.dto.request;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoRequest {

    private BigDecimal total;
    private EstadoPedido estadoPedido;

    private Long clienteId;
    private Long repartidorId;
    private Long restauranteId;
}
