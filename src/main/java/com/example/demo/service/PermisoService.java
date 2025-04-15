package com.example.demo.service;

import com.example.demo.entity.Permiso;
import com.example.demo.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoService {
    private final PermisoRepository permisoRepository;

    @Autowired
    public PermisoService(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    // Obtener todas las personas
    public List<Permiso> obtenerTodosLosPermisos() {
        return permisoRepository.findAll();
    }

    // Obtener permiso por ID
    public Permiso obtenerPermisoPorId(Long id) {
        return permisoRepository.findById(id).orElse(null); // Devuelve null si no se encuentra
    }

    // Crear permiso
    public Permiso crearPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    // Actualizar permiso
    public Permiso actualizarPermiso(Long id, Permiso permiso) {
        Permiso permisoExistente = obtenerPermisoPorId(id);
        if (permisoExistente != null) {
            permiso.setId(permisoExistente.getId());
            return permisoRepository.save(permiso);
        }
        return null; // Retorna null si no se encuentra la permiso
    }

    // Eliminar permiso
    public void eliminarPermiso(Long id) {
        permisoRepository.deleteById(id);
    }

}
