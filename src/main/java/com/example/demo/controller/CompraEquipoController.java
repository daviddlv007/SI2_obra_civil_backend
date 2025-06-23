package com.example.demo.controller;

import com.example.demo.entity.CompraEquipo;
import com.example.demo.entity.CompraMaterial;
import com.example.demo.service.CompraEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra-equipo")
public class CompraEquipoController {

    private final CompraEquipoService compraEquipoService;

    @Autowired
    public CompraEquipoController(CompraEquipoService compraEquipoService) {
        this.compraEquipoService = compraEquipoService;
    }

    @GetMapping
    public ResponseEntity<List<CompraEquipo>> obtenerTodos() {
        return new ResponseEntity<>(compraEquipoService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraEquipo> obtenerPorId(@PathVariable Long id) {
        CompraEquipo compraEquipo = compraEquipoService.obtenerPorId(id);
        if (compraEquipo != null) {
            return new ResponseEntity<>(compraEquipo, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CompraEquipo> crear(@RequestBody CompraEquipo compraEquipo) {
        return new ResponseEntity<>(compraEquipoService.crear(compraEquipo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraEquipo> actualizar(@PathVariable Long id, @RequestBody CompraEquipo compraEquipo) {
        CompraEquipo actualizado = compraEquipoService.actualizar(id, compraEquipo);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compraEquipoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<List<CompraEquipo>> obtenerComprasPorId(@PathVariable Long id) {
        return new ResponseEntity<>(compraEquipoService.obtenerComprasPorId(id), HttpStatus.OK);
    }

}
