package com.example.demo.controller;

import com.example.demo.entity.RolPermiso;
import com.example.demo.service.RolPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol-permiso")
public class RolPermisoController {

    private final RolPermisoService rolPermisoService;

    @Autowired
    public RolPermisoController(RolPermisoService rolPermisoService) {
        this.rolPermisoService = rolPermisoService;
    }

    // Obtener todas las relaciones entre rol y permiso
    @GetMapping
    public ResponseEntity<List<RolPermiso>> obtenerTodasLasRelaciones() {
        List<RolPermiso> relaciones = rolPermisoService.obtenerTodos();
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    // Obtener relación por ID
    @GetMapping("/{id}")
    public ResponseEntity<RolPermiso> obtenerRelacionPorId(@PathVariable Long id) {
        RolPermiso relacion = rolPermisoService.obtenerPorId(id);
        if (relacion != null) {
            return new ResponseEntity<>(relacion, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear nueva relación entre rol y permiso
    @PostMapping
    public ResponseEntity<RolPermiso> crearRelacion(@RequestBody RolPermiso rolPermiso) {
        RolPermiso nuevaRelacion = rolPermisoService.crear(rolPermiso);
        return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
    }

    // Actualizar relación existente
    @PutMapping("/{id}")
    public ResponseEntity<RolPermiso> actualizarRelacion(@PathVariable Long id, @RequestBody RolPermiso rolPermiso) {
        RolPermiso actualizada = rolPermisoService.actualizar(id, rolPermiso);
        if (actualizada != null) {
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar relación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRelacion(@PathVariable Long id) {
        rolPermisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
