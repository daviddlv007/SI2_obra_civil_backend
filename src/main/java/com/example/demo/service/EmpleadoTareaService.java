package com.example.demo.service;

import com.example.demo.entity.EmpleadoTarea;
import com.example.demo.repository.EmpleadoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoTareaService {

    private final EmpleadoTareaRepository empleadoTareaRepository;

    @Autowired
    public EmpleadoTareaService(EmpleadoTareaRepository empleadoTareaRepository) {
        this.empleadoTareaRepository = empleadoTareaRepository;
    }

    public List<EmpleadoTarea> obtenerTodos() {
        return empleadoTareaRepository.findAll();
    }

    public EmpleadoTarea obtenerPorId(Long id) {
        return empleadoTareaRepository.findById(id).orElse(null);
    }

    public EmpleadoTarea crear(EmpleadoTarea empleadoTarea) {
        return empleadoTareaRepository.save(empleadoTarea);
    }

    public EmpleadoTarea actualizar(Long id, EmpleadoTarea empleadoTarea) {
        EmpleadoTarea existente = obtenerPorId(id);
        if (existente != null) {
            empleadoTarea.setId(existente.getId());
            return empleadoTareaRepository.save(empleadoTarea);
        }
        return null;
    }

    public void eliminar(Long id) {
        empleadoTareaRepository.deleteById(id);
    }
}