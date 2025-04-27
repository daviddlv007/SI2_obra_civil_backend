package com.example.demo.controller;

import com.example.demo.entity.ObraCivilUsuario;
import com.example.demo.service.ObraCivilUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obra-civil-usuario")
public class ObraCivilUsuarioController {

    private final ObraCivilUsuarioService obraCivilUsuarioService;

    @Autowired
    public ObraCivilUsuarioController(ObraCivilUsuarioService obraCivilUsuarioService) {
        this.obraCivilUsuarioService = obraCivilUsuarioService;
    }

    // Obtener todas las relaciones entre obra civil y usuario
    @GetMapping
    public ResponseEntity<List<ObraCivilUsuario>> obtenerTodasLasRelaciones() {
        List<ObraCivilUsuario> relaciones = obraCivilUsuarioService.obtenerTodasLasRelaciones();
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    // Obtener relación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ObraCivilUsuario> obtenerRelacionPorId(@PathVariable Long id) {
        ObraCivilUsuario relacion = obraCivilUsuarioService.obtenerRelacionPorId(id);
        if (relacion != null) {
            return new ResponseEntity<>(relacion, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear relación entre obra civil y usuario
    @PostMapping
    public ResponseEntity<ObraCivilUsuario> crearRelacion(@RequestBody ObraCivilUsuario obraCivilUsuario) {
        ObraCivilUsuario nuevaRelacion = obraCivilUsuarioService.crearRelacion(obraCivilUsuario);
        return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
    }

    // Actualizar relación entre obra civil y usuario
    @PutMapping("/{id}")
    public ResponseEntity<ObraCivilUsuario> actualizarRelacion(@PathVariable Long id, @RequestBody ObraCivilUsuario obraCivilUsuario) {
        ObraCivilUsuario relacionActualizada = obraCivilUsuarioService.actualizarRelacion(id, obraCivilUsuario);
        if (relacionActualizada != null) {
            return new ResponseEntity<>(relacionActualizada, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar relación entre obra civil y usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRelacion(@PathVariable Long id) {
        obraCivilUsuarioService.eliminarRelacion(id);
        return ResponseEntity.noContent().build();
    }
}
