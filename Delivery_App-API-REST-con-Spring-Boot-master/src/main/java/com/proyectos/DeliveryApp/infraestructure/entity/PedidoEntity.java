package com.proyectos.DeliveryApp.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "pedidos")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private UsuarioEntity cliente;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private UsuarioEntity repartidor;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @JsonBackReference(value = "restaurante-pedidos")
    private RestauranteEntity restaurante;



    public PedidoEntity() {

    }

}