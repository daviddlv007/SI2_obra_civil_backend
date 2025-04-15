package com.example.demo.service;

import com.example.demo.entity.Rol;
import com.example.demo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Obtener todos los roles
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    // Obtener rol por ID
    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    // Crear rol
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // Actualizar rol
    public Rol actualizarRol(Long id, Rol rol) {
        if (rolRepository.existsById(id)) {
            rol.setId(id);
            return rolRepository.save(rol);
        }
        return null;
    }

    // Eliminar rol
    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }
}
