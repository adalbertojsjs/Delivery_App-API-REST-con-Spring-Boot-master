package com.proyectos.DeliveryApp.infraestructure.http.dto.response;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoResponse {

    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private EstadoPedido estado;
    private Long clienteId;
    private Long repartidorId;
    private Long restauranteId;
}