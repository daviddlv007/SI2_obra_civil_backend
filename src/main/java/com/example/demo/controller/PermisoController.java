package com.example.demo.controller;

import com.example.demo.entity.Permiso;
import com.example.demo.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    private final PermisoService permisoService;

    @Autowired
    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    // Obtener todos los permisos
    @GetMapping
    public ResponseEntity<List<Permiso>> obtenerTodosLosPermisos() {
        return new ResponseEntity<>(permisoService.obtenerTodosLosPermisos(), HttpStatus.OK);
    }

    // Obtener permiso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Long id) {
        Permiso permiso = permisoService.obtenerPermisoPorId(id);
        if (permiso != null) {
            return new ResponseEntity<>(permiso, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear permiso
    @PostMapping
    public ResponseEntity<Permiso> crearPermiso(@RequestBody Permiso permiso) {
        return new ResponseEntity<>(permisoService.crearPermiso(permiso), HttpStatus.CREATED);
    }

    // Actualizar permiso
    @PutMapping("/{id}")
    public ResponseEntity<Permiso> actualizarPermiso(@PathVariable Long id, @RequestBody Permiso permiso) {
        Permiso permisoActualizada = permisoService.actualizarPermiso(id, permiso);
        if (permisoActualizada != null) {
            return new ResponseEntity<>(permisoActualizada, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar permiso por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarpermiso(@PathVariable Long id) {
        permisoService.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }

}
