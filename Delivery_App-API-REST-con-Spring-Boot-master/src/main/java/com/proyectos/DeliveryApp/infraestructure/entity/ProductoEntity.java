package com.proyectos.DeliveryApp.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyectos.DeliveryApp.domain.enums.Disponible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class ProductoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    private Disponible disponible;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @JsonBackReference(value = "restaurante-productos")
    private RestauranteEntity restaurante;



    public ProductoEntity(){

    }

    public ProductoEntity(Long id, String nombre, BigDecimal precio, Disponible disponible, RestauranteEntity restaurante) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
        this.restaurante = restaurante;
    }
}
