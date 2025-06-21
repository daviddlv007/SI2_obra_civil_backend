package com.example.demo.controller;

import com.example.demo.dto.EstimacionRequest;
import com.example.demo.dto.EstimacionResponse;
import com.example.demo.service.EstimacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para el endpoint de estimación de costos de obra.
 */
@RestController
@RequestMapping("/estimaciones")
public class EstimacionController {

    private final EstimacionService estimacionService;

    @Autowired
    public EstimacionController(EstimacionService estimacionService) {
        this.estimacionService = estimacionService;
    }

    /**
     * POST /estimaciones
     * Body: EstimacionRequest con superficieM2 y mapa de parámetros.
     * Retorna EstimacionResponse con costoPorM2 y costoTotal.
     */
    @PostMapping
    public ResponseEntity<EstimacionResponse> estimarObra(@RequestBody EstimacionRequest request) {
        try {
            EstimacionResponse resp = estimacionService.calcularEstimacion(request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException ex) {
            // Por ejemplo: superficie inválida o parámetros vacíos
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {
            // Log y error genérico
            ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
