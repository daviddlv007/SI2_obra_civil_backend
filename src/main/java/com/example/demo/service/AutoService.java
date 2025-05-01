package com.example.demo.service;

import com.example.demo.entity.Auto;
import com.example.demo.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    // Obtener todos los autos
    public List<Auto> obtenerTodosLosAutos() {
        return autoRepository.findAll();
    }

    // Obtener auto por ID
    public Auto obtenerAutoPorId(Long id) {
        return autoRepository.findById(id).orElse(null);
    }

    // Crear auto
    public Auto crearAuto(Auto auto) {
        return autoRepository.save(auto);
    }

    // Actualizar auto
    public Auto actualizarAuto(Long id, Auto auto) {
        Auto autoExistente = obtenerAutoPorId(id);
        if (autoExistente != null) {
            auto.setId(autoExistente.getId());
            return autoRepository.save(auto);
        }
        return null;
    }

    // Eliminar auto
    public void eliminarAuto(Long id) {
        autoRepository.deleteById(id);
    }
}
