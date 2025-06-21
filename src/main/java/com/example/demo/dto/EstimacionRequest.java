package com.example.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EstimacionRequest {
    /**
     * Superficie total de la obra en m².
     */
    private Double superficieM2;

    /**
     * Selecciones del administrador: clave = nombre del parámetro, valor = opción seleccionada.
     * Ejemplo: {"tipo_suelo":"arcilloso", "tipo_cimentacion":"zapatas", ...}
     */
    private Map<String, String> parametros;
}
