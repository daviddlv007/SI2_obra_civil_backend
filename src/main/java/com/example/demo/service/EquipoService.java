package com.example.demo.service;

import com.example.demo.entity.Equipo;
import com.example.demo.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepository.findAll();
    }

    public Equipo obtenerEquipoPorId(Long id) {
        return equipoRepository.findById(id).orElse(null);
    }

    public Equipo obtenerEquipoPorCodigo(String codigo) {
        return equipoRepository.findByCodigoActivo(codigo);
    }

    public Equipo crearEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo actualizarEquipo(Long id, Equipo equipo) {
        Equipo equipoExistente = obtenerEquipoPorId(id);
        if (equipoExistente != null) {
            equipo.setId(equipoExistente.getId());
            return equipoRepository.save(equipo);
        }
        return null;
    }

    public void eliminarEquipo(Long id) {
        equipoRepository.deleteById(id);
    }
}