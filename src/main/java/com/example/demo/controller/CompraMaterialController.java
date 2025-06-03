package com.example.demo.controller;

import com.example.demo.entity.CompraMaterial;
import com.example.demo.service.CompraMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra-material")
public class CompraMaterialController {

    private final CompraMaterialService compraMaterialService;

    @Autowired
    public CompraMaterialController(CompraMaterialService compraMaterialService) {
        this.compraMaterialService = compraMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<CompraMaterial>> obtenerTodos() {
        return new ResponseEntity<>(compraMaterialService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraMaterial> obtenerPorId(@PathVariable Long id) {
        CompraMaterial compraMaterial = compraMaterialService.obtenerPorId(id);
        if (compraMaterial != null) {
            return new ResponseEntity<>(compraMaterial, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CompraMaterial> crear(@RequestBody CompraMaterial compraMaterial) {
        return new ResponseEntity<>(compraMaterialService.crear(compraMaterial), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraMaterial> actualizar(@PathVariable Long id, @RequestBody CompraMaterial compraMaterial) {
        CompraMaterial actualizado = compraMaterialService.actualizar(id, compraMaterial);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compraMaterialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
