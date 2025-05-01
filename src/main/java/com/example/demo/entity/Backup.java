package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "backup")
public class Backup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ruta", nullable = false, length = 255)
    private String ruta;

    @Column(name = "fechahora", nullable = false)
    private LocalDateTime fechahora;

}
