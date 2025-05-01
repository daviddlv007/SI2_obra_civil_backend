package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "empleado_tarea")
public class EmpleadoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(name = "horas_trabajadas", precision = 5, scale = 2)
    private BigDecimal horasTrabajadas;

    @Column(name = "rol_en_tarea", nullable = false, length = 50)
    private String rolEnTarea;

    @Column(name = "fecha_asignacion", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate fechaAsignacion = LocalDate.now();
}