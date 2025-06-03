package com.example.demo.controller;

import com.example.demo.entity.Compra;
import com.example.demo.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerTodosLasCompras() {
        return new ResponseEntity<>(compraService.obtenerTodosLasCompras(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable Long id) {
        Compra compra = compraService.obtenerCompraPorId(id);
        if (compra != null) {
            return new ResponseEntity<>(compra, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nitci/{nitCi}")
    public ResponseEntity<Compra> obtenerProveedorPorFecha(@PathVariable LocalDate fecha) {
        Compra compra = compraService.obtenerProveedorPorFecha(fecha);
        if (compra != null) {
            return new ResponseEntity<>(compra, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearProveedor(@RequestBody Compra compra) {
        try {
            Compra nuevoCompra = compraService.crearCompra(compra);
            return new ResponseEntity<>(nuevoCompra, HttpStatus.CREATED);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Ya existe una compra con ese ID.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error interno al guardar compra.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
