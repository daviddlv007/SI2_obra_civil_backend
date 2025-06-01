package com.example.demo.controller;

import com.example.demo.entity.Proveedor;
import com.example.demo.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedores() {
        return new ResponseEntity<>(proveedorService.obtenerTodosLosProveedores(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(id);
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nitci/{nitCi}")
    public ResponseEntity<Proveedor> obtenerProveedorPorNitCi(@PathVariable String nitCi) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorNitCi(nitCi);
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
public ResponseEntity<?> crearProveedor(@RequestBody Proveedor proveedor) {
    try {
        Proveedor nuevoProveedor = proveedorService.crearProveedor(proveedor);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
    } catch (org.springframework.dao.DataIntegrityViolationException ex) {
        return new ResponseEntity<>("Ya existe un proveedor con ese NIT/CI.", HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
        return new ResponseEntity<>("Error interno al guardar proveedor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Proveedor proveedorActualizado = proveedorService.actualizarProveedor(id, proveedor);
        if (proveedorActualizado != null) {
            return new ResponseEntity<>(proveedorActualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
