package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "nit_ci", nullable = false, length = 20, unique = true)
    private String nitCi;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "ciudad", length = 50)
    private String ciudad;

    @Column(name = "pais", length = 50)
    private String pais;

    @Column(name = "empresa", length = 100)
    private String empresa;

    @Column(name = "tipo_proveedor", length = 50)
    @Enumerated(EnumType.STRING)
    private Proveedor.TipoProveedor tipoProveedor;

    @Column(name = "estado", length = 20)
    private String estado; // "Activo" o "Inactivo"

    public enum TipoProveedor {
        MATERIAL, EQUIPO, SERVICIO, OTROS
    }

    /*@OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Compra> compras = new ArrayList<>();*/
}
