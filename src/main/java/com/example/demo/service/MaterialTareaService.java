package com.example.demo.service;

import com.example.demo.entity.MaterialTarea;
import com.example.demo.repository.MaterialTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialTareaService {

    private final MaterialTareaRepository materialTareaRepository;

    @Autowired
    public MaterialTareaService(MaterialTareaRepository materialTareaRepository) {
        this.materialTareaRepository = materialTareaRepository;
    }

    public List<MaterialTarea> obtenerTodos() {
        return materialTareaRepository.findAll();
    }

    public MaterialTarea obtenerPorId(Long id) {
        return materialTareaRepository.findById(id).orElse(null);
    }

    public MaterialTarea crear(MaterialTarea materialTarea) {
        return materialTareaRepository.save(materialTarea);
    }

    public MaterialTarea actualizar(Long id, MaterialTarea materialTarea) {
        MaterialTarea existente = obtenerPorId(id);
        if (existente != null) {
            materialTarea.setId(existente.getId());
            return materialTareaRepository.save(materialTarea);
        }
        return null;
    }

    public void eliminar(Long id) {
        materialTareaRepository.deleteById(id);
    }
}