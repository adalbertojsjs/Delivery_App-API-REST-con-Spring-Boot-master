package com.proyectos.DeliveryApp.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
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

    // CLIENTE -> 1 a N
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private UsuarioEntity cliente;

    // REPARTIDOR -> 1 a N
    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private UsuarioEntity repartidor;

    // RESTAURANTE -> 1 a N
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @JsonBackReference(value = "restaurante-pedidos")
    private RestauranteEntity restaurante;



    public PedidoEntity() {

    }

    public PedidoEntity(Long id, LocalDateTime fecha, BigDecimal total,
                        EstadoPedido estadoPedido, UsuarioEntity cliente, UsuarioEntity repartidor, RestauranteEntity restaurante) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estadoPedido = estadoPedido;
        this.cliente = cliente;
        this.repartidor = repartidor;
        this.restaurante = restaurante;
    }
}