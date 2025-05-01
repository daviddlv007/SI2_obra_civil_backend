package com.example.demo.controller;

import com.example.demo.entity.Equipo;
import com.example.demo.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<Equipo>> obtenerTodosLosEquipos() {
        return new ResponseEntity<>(equipoService.obtenerTodosLosEquipos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Long id) {
        Equipo equipo = equipoService.obtenerEquipoPorId(id);
        if (equipo != null) {
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Equipo> obtenerEquipoPorCodigo(@PathVariable String codigo) {
        Equipo equipo = equipoService.obtenerEquipoPorCodigo(codigo);
        if (equipo != null) {
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        return new ResponseEntity<>(equipoService.crearEquipo(equipo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        Equipo equipoActualizado = equipoService.actualizarEquipo(id, equipo);
        if (equipoActualizado != null) {
            return new ResponseEntity<>(equipoActualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}