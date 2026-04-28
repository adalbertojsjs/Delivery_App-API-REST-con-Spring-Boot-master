package com.proyectos.DeliveryApp.domain.model;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private EstadoPedido estadoPedido;

    private Long clienteId;
    private Long repartidorId;
    private Long restauranteId;
}
