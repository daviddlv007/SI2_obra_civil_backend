package com.example.demo.service;

import com.example.demo.entity.RolPermiso;
import com.example.demo.repository.RolPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;

    @Autowired
    public RolPermisoService(RolPermisoRepository rolPermisoRepository) {
        this.rolPermisoRepository = rolPermisoRepository;
    }

    public List<RolPermiso> obtenerTodos() {
        return rolPermisoRepository.findAll();
    }

    public RolPermiso obtenerPorId(Long id) {
        return rolPermisoRepository.findById(id).orElse(null);
    }

    public RolPermiso crear(RolPermiso rolPermiso) {
        return rolPermisoRepository.save(rolPermiso);
    }

    public RolPermiso actualizar(Long id, RolPermiso rolPermiso) {
        RolPermiso existente = obtenerPorId(id);
        if (existente != null) {
            rolPermiso.setId(id);
            return rolPermisoRepository.save(rolPermiso);
        }
        return null;
    }

    public void eliminar(Long id) {
        rolPermisoRepository.deleteById(id);
    }
}
