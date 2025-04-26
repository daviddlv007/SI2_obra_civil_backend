package com.example.demo.controller;

import com.example.demo.entity.Bitacora;
import com.example.demo.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bitacoras")
public class BitacoraController {

    private final BitacoraService bitacoraService;

    @Autowired
    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    // Obtener todas las entradas de bitácora
    @GetMapping
    public ResponseEntity<List<Bitacora>> obtenerTodasLasBitacoras() {
        return ResponseEntity.ok(bitacoraService.obtenerTodasLasBitacoras());
    }

    // Obtener una entrada específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Bitacora> obtenerBitacoraPorId(@PathVariable Long id) {
        Bitacora bitacora = bitacoraService.obtenerBitacoraPorId(id);
        if (bitacora != null) {
            return ResponseEntity.ok(bitacora);
        }
        return ResponseEntity.notFound().build();
    }
}
