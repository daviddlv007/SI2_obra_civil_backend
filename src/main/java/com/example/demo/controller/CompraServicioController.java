package com.example.demo.controller;

import com.example.demo.entity.CompraEquipo;
import com.example.demo.entity.CompraServicio;
import com.example.demo.service.CompraServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra-servicio")
public class CompraServicioController {

    private final CompraServicioService compraServicioService;

    @Autowired
    public CompraServicioController(CompraServicioService compraServicioService) {
        this.compraServicioService = compraServicioService;
    }

    @GetMapping
    public ResponseEntity<List<CompraServicio>> obtenerTodos() {
        return new ResponseEntity<>(compraServicioService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraServicio> obtenerPorId(@PathVariable Long id) {
        CompraServicio compraServicio = compraServicioService.obtenerPorId(id);
        if (compraServicio != null) {
            return new ResponseEntity<>(compraServicio, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CompraServicio> crear(@RequestBody CompraServicio compraServicio) {
        return new ResponseEntity<>(compraServicioService.crear(compraServicio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraServicio> actualizar(@PathVariable Long id, @RequestBody CompraServicio compraServicio) {
        CompraServicio actualizado = compraServicioService.actualizar(id, compraServicio);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compraServicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<List<CompraServicio>> obtenerComprasPorId(@PathVariable Long id) {
        return new ResponseEntity<>(compraServicioService.obtenerComprasPorId(id), HttpStatus.OK);
    }

}
