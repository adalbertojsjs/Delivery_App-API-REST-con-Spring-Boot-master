package com.proyectos.DeliveryApp.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyectos.DeliveryApp.domain.enums.EstadoRestaurante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "restaurantes")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Enumerated(EnumType.STRING)
    private EstadoRestaurante estado;

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "restaurante-pedidos")
    private List<PedidoEntity> pedidos;

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "restaurante-productos")
    private List<ProductoEntity> productos;


    public RestauranteEntity() {
    }

    public RestauranteEntity(Long id, String nombre, String direccion, EstadoRestaurante estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.estado = estado;
    }
}
