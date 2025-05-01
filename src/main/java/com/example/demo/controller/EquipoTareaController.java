package com.example.demo.controller;

import com.example.demo.entity.EquipoTarea;
import com.example.demo.service.EquipoTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/equipos-tareas")
public class EquipoTareaController {

    private final EquipoTareaService equipoTareaService;

    @Autowired
    public EquipoTareaController(EquipoTareaService equipoTareaService) {
        this.equipoTareaService = equipoTareaService;
    }

    @GetMapping
    public ResponseEntity<List<EquipoTarea>> obtenerTodos() {
        return new ResponseEntity<>(equipoTareaService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoTarea> obtenerPorId(@PathVariable Long id) {
        EquipoTarea equipoTarea = equipoTareaService.obtenerPorId(id);
        if (equipoTarea != null) {
            return new ResponseEntity<>(equipoTarea, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EquipoTarea> crear(@RequestBody EquipoTarea equipoTarea) {
        return new ResponseEntity<>(equipoTareaService.crear(equipoTarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoTarea> actualizar(@PathVariable Long id, @RequestBody EquipoTarea equipoTarea) {
        EquipoTarea actualizado = equipoTareaService.actualizar(id, equipoTarea);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        equipoTareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}