package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class EstimacionResponse {
    /**
     * Costo estimado por m², luego de sumar y aplicar multiplicadores.
     */
    private BigDecimal costoPorM2;

    /**
     * Costo total de la obra = costoPorM2 × superficieM2.
     */
    private BigDecimal costoTotal;

    /**
     * (Opcional) detalle de componentes previos a multiplicar y sumas:
     * - "baseBsPorM2": suma de todos los Bs/m2 directos.
     * - "multiplicadorAcumulado": producto de factores % (ej. 1.25 × 1.05 ...).
     * Puedes incluirlo si quieres más trazabilidad.
     */
    private Map<String, BigDecimal> detalle;
}
