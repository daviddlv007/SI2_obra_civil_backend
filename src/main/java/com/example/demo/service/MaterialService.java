package com.example.demo.service;

import com.example.demo.entity.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> obtenerTodosLosMateriales() {
        return materialRepository.findAll();
    }

    public Material obtenerMaterialPorId(Long id) {
        return materialRepository.findById(id).orElse(null);
    }

    public Material obtenerMaterialPorCodigo(String codigo) {
        return materialRepository.findByCodigoInventario(codigo);
    }

    public Material crearMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material actualizarMaterial(Long id, Material material) {
        Material materialExistente = obtenerMaterialPorId(id);
        if (materialExistente != null) {
            material.setId(materialExistente.getId());
            return materialRepository.save(material);
        }
        return null;
    }

    public void eliminarMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}