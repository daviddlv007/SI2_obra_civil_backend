package com.example.demo.service;

import com.example.demo.entity.Tarea;
import com.example.demo.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    @Autowired
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }

    public Tarea obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id).orElse(null);
    }

    public Tarea crearTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(Long id, Tarea tarea) {
        Tarea tareaExistente = obtenerTareaPorId(id);
        if (tareaExistente != null) {
            tarea.setId(tareaExistente.getId());
            return tareaRepository.save(tarea);
        }
        return null;
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
