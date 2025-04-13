package com.example.demo.controller;

import com.example.demo.entity.Persona;
import com.example.demo.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // Obtener todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodasLasPersonas() {
        return new ResponseEntity<>(personaService.obtenerTodasLasPersonas(), HttpStatus.OK);
    }

    // Obtener persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long id) {
        Persona persona = personaService.obtenerPersonaPorId(id);
        if (persona != null) {
            return new ResponseEntity<>(persona, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear persona
    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        return new ResponseEntity<>(personaService.crearPersona(persona), HttpStatus.CREATED);
    }

    // Actualizar persona
    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        Persona personaActualizada = personaService.actualizarPersona(id, persona);
        if (personaActualizada != null) {
            return new ResponseEntity<>(personaActualizada, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar persona por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}
