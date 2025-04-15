package com.example.demo.controller;

import com.example.demo.entity.Rol;
import com.example.demo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodosLosRoles() {
        return new ResponseEntity<>(rolService.obtenerTodosLosRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        Rol rol = rolService.obtenerRolPorId(id);
        if (rol != null) {
            return new ResponseEntity<>(rol, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        return new ResponseEntity<>(rolService.crearRol(rol), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @RequestBody Rol rol) {
        Rol rolActualizado = rolService.actualizarRol(id, rol);
        if (rolActualizado != null) {
            return new ResponseEntity<>(rolActualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
