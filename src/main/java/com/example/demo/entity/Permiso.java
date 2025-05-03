package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permiso")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;

    public Permiso(){
        //Constructor sin parametros
    }

    public Permiso(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

}
