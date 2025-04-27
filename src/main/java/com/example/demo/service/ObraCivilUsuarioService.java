package com.example.demo.service;

import com.example.demo.entity.ObraCivilUsuario;
import com.example.demo.repository.ObraCivilUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraCivilUsuarioService {

    private final ObraCivilUsuarioRepository obraCivilUsuarioRepository;

    @Autowired
    public ObraCivilUsuarioService(ObraCivilUsuarioRepository obraCivilUsuarioRepository) {
        this.obraCivilUsuarioRepository = obraCivilUsuarioRepository;
    }

    // Obtener todas las relaciones
    public List<ObraCivilUsuario> obtenerTodasLasRelaciones() {
        return obraCivilUsuarioRepository.findAll();
    }

    // Obtener relación por ID
    public ObraCivilUsuario obtenerRelacionPorId(Long id) {
        return obraCivilUsuarioRepository.findById(id).orElse(null); // Devuelve null si no se encuentra
    }

    // Crear relación
    public ObraCivilUsuario crearRelacion(ObraCivilUsuario obraCivilUsuario) {
        return obraCivilUsuarioRepository.save(obraCivilUsuario);
    }

    // Actualizar relación
    public ObraCivilUsuario actualizarRelacion(Long id, ObraCivilUsuario obraCivilUsuario) {
        ObraCivilUsuario relacionExistente = obtenerRelacionPorId(id);
        if (relacionExistente != null) {
            // Aquí se actualizan los campos que el usuario modifica
            relacionExistente.setObraCivil(obraCivilUsuario.getObraCivil());
            relacionExistente.setUsuario(obraCivilUsuario.getUsuario());
            return obraCivilUsuarioRepository.save(relacionExistente);
        }
        return null; // Retorna null si no se encuentra la relación
    }

    // Eliminar relación
    public void eliminarRelacion(Long id) {
        obraCivilUsuarioRepository.deleteById(id);
    }
}
