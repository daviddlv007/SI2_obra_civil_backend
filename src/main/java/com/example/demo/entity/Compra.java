package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_compra", unique = true, nullable = false, updatable = false)
    private Long numeroCompra;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(name = "estado_compra", length = 50)
    @Enumerated(EnumType.STRING)
    private Compra.EstadoCompra estadoCompra;

    public enum EstadoCompra {
        PENDIENTE, APROBADO, CANCELADA
    }

}
