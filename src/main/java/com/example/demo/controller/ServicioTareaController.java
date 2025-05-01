package com.example.demo.controller;

import com.example.demo.entity.ServicioTarea;
import com.example.demo.service.ServicioTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/servicios-tareas")
public class ServicioTareaController {

    private final ServicioTareaService servicioTareaService;

    @Autowired
    public ServicioTareaController(ServicioTareaService servicioTareaService) {
        this.servicioTareaService = servicioTareaService;
    }

    @GetMapping
    public ResponseEntity<List<ServicioTarea>> obtenerTodos() {
        return new ResponseEntity<>(servicioTareaService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioTarea> obtenerPorId(@PathVariable Long id) {
        ServicioTarea servicioTarea = servicioTareaService.obtenerPorId(id);
        if (servicioTarea != null) {
            return new ResponseEntity<>(servicioTarea, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ServicioTarea> crear(@RequestBody ServicioTarea servicioTarea) {
        return new ResponseEntity<>(servicioTareaService.crear(servicioTarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioTarea> actualizar(@PathVariable Long id, @RequestBody ServicioTarea servicioTarea) {
        ServicioTarea actualizado = servicioTareaService.actualizar(id, servicioTarea);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioTareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}