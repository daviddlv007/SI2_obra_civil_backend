package com.example.demo.controller;

import com.example.demo.entity.EmpleadoTarea;
import com.example.demo.service.EmpleadoTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/empleados-tareas")
public class EmpleadoTareaController {

    private final EmpleadoTareaService empleadoTareaService;

    @Autowired
    public EmpleadoTareaController(EmpleadoTareaService empleadoTareaService) {
        this.empleadoTareaService = empleadoTareaService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoTarea>> obtenerTodos() {
        return new ResponseEntity<>(empleadoTareaService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoTarea> obtenerPorId(@PathVariable Long id) {
        EmpleadoTarea empleadoTarea = empleadoTareaService.obtenerPorId(id);
        if (empleadoTarea != null) {
            return new ResponseEntity<>(empleadoTarea, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmpleadoTarea> crear(@RequestBody EmpleadoTarea empleadoTarea) {
        return new ResponseEntity<>(empleadoTareaService.crear(empleadoTarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoTarea> actualizar(@PathVariable Long id, @RequestBody EmpleadoTarea empleadoTarea) {
        EmpleadoTarea actualizado = empleadoTareaService.actualizar(id, empleadoTarea);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoTareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}