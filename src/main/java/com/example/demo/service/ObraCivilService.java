package com.example.demo.service;

import com.example.demo.entity.ObraCivil;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ObraCivilRepository;
import com.example.demo.repository.ObraCivilUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraCivilService {

    private final ObraCivilRepository obraCivilRepository;

    @Autowired
    private ObraCivilUsuarioRepository obraCivilUsuarioRepository;

    @Autowired
    public ObraCivilService(ObraCivilRepository obraCivilRepository) {
        this.obraCivilRepository = obraCivilRepository;
    }

    // Obtener todas las obras civiles
    public List<ObraCivil> obtenerTodasLasObras() {
        return obraCivilRepository.findAll();
    }

    // Obtener obra civil por ID
    public ObraCivil obtenerObraPorId(Long id) {
        return obraCivilRepository.findById(id).orElse(null);
    }

    // Crear obra civil
    public ObraCivil crearObra(ObraCivil obraCivil) {
        return obraCivilRepository.save(obraCivil);
    }

    // Actualizar obra civil
    public ObraCivil actualizarObra(Long id, ObraCivil obraCivil) {
        ObraCivil obraExistente = obtenerObraPorId(id);
        if (obraExistente != null) {
            obraCivil.setId(obraExistente.getId());
            return obraCivilRepository.save(obraCivil);
        }
        return null;
    }

    // Eliminar obra civil
    public void eliminarObra(Long id) {
        obraCivilRepository.deleteById(id);
    }

    // Obtener todas las obras civiles con los usuarios asignados que tienen los roles "empleado" y "cliente"
    public List<ObraCivil> getObrasCivilesConUsuariosPorRoles() {
        return obraCivilUsuarioRepository.findObrasCivilesConUsuariosPorRoles();
    }

    // Obtener todos los usuarios ordenados por ID de manera ascendente
    public List<ObraCivil> getAllObrasCivilesAsc() {
        return obraCivilRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }
}
