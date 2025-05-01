package com.example.demo.controller;

import com.example.demo.entity.Material;
import com.example.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<Material>> obtenerTodosLosMateriales() {
        return new ResponseEntity<>(materialService.obtenerTodosLosMateriales(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> obtenerMaterialPorId(@PathVariable Long id) {
        Material material = materialService.obtenerMaterialPorId(id);
        if (material != null) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Material> obtenerMaterialPorCodigo(@PathVariable String codigo) {
        Material material = materialService.obtenerMaterialPorCodigo(codigo);
        if (material != null) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Material> crearMaterial(@RequestBody Material material) {
        return new ResponseEntity<>(materialService.crearMaterial(material), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> actualizarMaterial(@PathVariable Long id, @RequestBody Material material) {
        Material materialActualizado = materialService.actualizarMaterial(id, material);
        if (materialActualizado != null) {
            return new ResponseEntity<>(materialActualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMaterial(@PathVariable Long id) {
        materialService.eliminarMaterial(id);
        return ResponseEntity.noContent().build();
    }
}