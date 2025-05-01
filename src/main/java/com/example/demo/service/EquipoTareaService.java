package com.example.demo.service;

import com.example.demo.entity.EquipoTarea;
import com.example.demo.repository.EquipoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipoTareaService {

    private final EquipoTareaRepository equipoTareaRepository;

    @Autowired
    public EquipoTareaService(EquipoTareaRepository equipoTareaRepository) {
        this.equipoTareaRepository = equipoTareaRepository;
    }

    public List<EquipoTarea> obtenerTodos() {
        return equipoTareaRepository.findAll();
    }

    public EquipoTarea obtenerPorId(Long id) {
        return equipoTareaRepository.findById(id).orElse(null);
    }

    public EquipoTarea crear(EquipoTarea equipoTarea) {
        return equipoTareaRepository.save(equipoTarea);
    }

    public EquipoTarea actualizar(Long id, EquipoTarea equipoTarea) {
        EquipoTarea existente = obtenerPorId(id);
        if (existente != null) {
            equipoTarea.setId(existente.getId());
            return equipoTareaRepository.save(equipoTarea);
        }
        return null;
    }

    public void eliminar(Long id) {
        equipoTareaRepository.deleteById(id);
    }
}