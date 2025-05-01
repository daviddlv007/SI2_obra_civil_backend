package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "documento_identidad", unique = true, nullable = false, length = 20)
    private String documentoIdentidad;

    @Column(name = "puesto", length = 100)
    private String puesto;

    @Column(name = "tipo_contrato", nullable = false, length = 20)
    private String tipoContrato;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "salario", nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "fecha_termino_contrato")
    private LocalDate fechaTerminoContrato;
}