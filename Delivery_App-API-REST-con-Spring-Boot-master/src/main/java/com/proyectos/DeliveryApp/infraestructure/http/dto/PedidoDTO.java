package com.proyectos.DeliveryApp.infraestructure.http.dto;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private EstadoPedido estado;

    private UsuarioDTO cliente;
    private UsuarioDTO repartidor;
    private RestauranteDTO restaurante;

}