package com.example.demo.controller;

import com.example.demo.entity.Empleado;
import com.example.demo.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        return new ResponseEntity<>(empleadoService.obtenerTodosLosEmpleados(), HttpStatus.OK);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosActivos() {
        return new ResponseEntity<>(empleadoService.obtenerEmpleadosActivos(), HttpStatus.OK);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosInactivos() {
        return new ResponseEntity<>(empleadoService.obtenerEmpleadosInactivos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
        if (empleado != null) {
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorDocumento(@PathVariable String documento) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorDocumento(documento);
        if (empleado != null) {
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorEmail(@PathVariable String email) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorEmail(email);
        if (empleado != null) {
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        return new ResponseEntity<>(empleadoService.crearEmpleado(empleado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado empleadoActualizado = empleadoService.actualizarEmpleado(id, empleado);
        if (empleadoActualizado != null) {
            return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}