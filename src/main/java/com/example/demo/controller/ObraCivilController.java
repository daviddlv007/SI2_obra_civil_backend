package com.example.demo.controller;

import com.example.demo.entity.ObraCivil;
import com.example.demo.service.ObraCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraCivilController {

    private final ObraCivilService obraCivilService;

    @Autowired
    public ObraCivilController(ObraCivilService obraCivilService) {
        this.obraCivilService = obraCivilService;
    }

    // Obtener todas las obras civiles
    @GetMapping
    public ResponseEntity<List<ObraCivil>> obtenerTodasLasObras() {
        return new ResponseEntity<>(obraCivilService.obtenerTodasLasObras(), HttpStatus.OK);
    }

    // Obtener obra civil por ID
    @GetMapping("/{id}")
    public ResponseEntity<ObraCivil> obtenerObraPorId(@PathVariable Long id) {
        ObraCivil obraCivil = obraCivilService.obtenerObraPorId(id);
        if (obraCivil != null) {
            return new ResponseEntity<>(obraCivil, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear obra civil
    @PostMapping
    public ResponseEntity<ObraCivil> crearObra(@RequestBody ObraCivil obraCivil) {
        return new ResponseEntity<>(obraCivilService.crearObra(obraCivil), HttpStatus.CREATED);
    }

    // Actualizar obra civil
    @PutMapping("/{id}")
    public ResponseEntity<ObraCivil> actualizarObra(@PathVariable Long id, @RequestBody ObraCivil obraCivil) {
        ObraCivil obraActualizada = obraCivilService.actualizarObra(id, obraCivil);
        if (obraActualizada != null) {
            return new ResponseEntity<>(obraActualizada, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar obra civil por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarObra(@PathVariable Long id) {
        obraCivilService.eliminarObra(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todas las obras civiles con los usuarios (empleado y cliente) asignados
    @GetMapping("/con-usuarios")
    public ResponseEntity<List<ObraCivil>> getObrasCivilesConUsuarios() {
        List<ObraCivil> obrasCiviles = obraCivilService.getObrasCivilesConUsuariosPorRoles();

        if (obrasCiviles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(obrasCiviles, HttpStatus.OK);
    }
}
