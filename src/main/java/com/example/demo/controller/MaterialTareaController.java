package com.example.demo.controller;

import com.example.demo.entity.MaterialTarea;
import com.example.demo.service.MaterialTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/materiales-tareas")
public class MaterialTareaController {

    private final MaterialTareaService materialTareaService;

    @Autowired
    public MaterialTareaController(MaterialTareaService materialTareaService) {
        this.materialTareaService = materialTareaService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialTarea>> obtenerTodos() {
        return new ResponseEntity<>(materialTareaService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialTarea> obtenerPorId(@PathVariable Long id) {
        MaterialTarea materialTarea = materialTareaService.obtenerPorId(id);
        if (materialTarea != null) {
            return new ResponseEntity<>(materialTarea, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MaterialTarea> crear(@RequestBody MaterialTarea materialTarea) {
        return new ResponseEntity<>(materialTareaService.crear(materialTarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialTarea> actualizar(@PathVariable Long id, @RequestBody MaterialTarea materialTarea) {
        MaterialTarea actualizado = materialTareaService.actualizar(id, materialTarea);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        materialTareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}