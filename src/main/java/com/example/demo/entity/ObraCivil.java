package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "obra_civil")
public class ObraCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "costo_estimado", columnDefinition = "DECIMAL(10, 2)")
    private Double costoEstimado;

    @Column(name = "costo", columnDefinition = "DECIMAL(10, 2)")
    private Double costo;

    @Column(name = "presupuesto", columnDefinition = "DECIMAL(10, 2)")
    private Double presupuesto;

    @Column(name = "presupuesto_disponible", columnDefinition = "DECIMAL(10, 2)")
    private Double presupuestoDisponible;

    @Column(name = "fecha_inicio")
    private java.sql.Date fechaInicio;

    @Column(name = "fecha_fin_estimada")
    private java.sql.Date fechaFinEstimada;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fecha_creacion", updatable = false)
    private java.sql.Timestamp fechaCreacion = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());

    @Column(name = "fecha_ultima_actualizacion")
    private java.sql.Timestamp fechaUltimaActualizacion = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());

    @Column(name = "tipo_obra", length = 50)
    @Enumerated(EnumType.STRING)
    private TipoObra tipoObra;

    @Column(name = "ubicacion", length = 255)
    private String ubicacion;

    @Column(name = "plano_url", length = 255)
    private String planoUrl;

    @Column(name = "fecha_terminacion_real")
    private java.sql.Date fechaTerminacionReal;
    
    public enum TipoObra {
        VIVIENDA, COMERCIAL, INDUSTRIAL, OTROS
    }

    public ObraCivil() {
        // Constructor sin parámetros
    }

    // Constructor con todos los parámetros (opcional)
    public ObraCivil(String nombre, String descripcion,
                     Double costoEstimado, Double costo,
                     Double presupuesto, Double presupuestoDisponible,
                     Date fechaInicio, Date fechaFin,
                     String estado, Timestamp fechaCreacion,
                     Timestamp fechaUltimaActualizacion,
                     TipoObra tipoObra,
                     String ubicacion, String plano, Date fechaTerminacionReal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoEstimado = costoEstimado;
        this.costo = costo;
        this.presupuesto = presupuesto;
        this.presupuestoDisponible = presupuestoDisponible;
        this.fechaInicio = fechaInicio;
        this.fechaFinEstimada = fechaFin;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
        this.tipoObra = tipoObra;
        this.ubicacion = ubicacion;
        this.planoUrl = plano;
        this.fechaTerminacionReal = fechaTerminacionReal;
     }
}
