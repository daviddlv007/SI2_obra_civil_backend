package com.example.demo.service;

import com.example.demo.entity.ServicioTarea;
import com.example.demo.repository.ServicioTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioTareaService {

    private final ServicioTareaRepository servicioTareaRepository;

    @Autowired
    public ServicioTareaService(ServicioTareaRepository servicioTareaRepository) {
        this.servicioTareaRepository = servicioTareaRepository;
    }

    public List<ServicioTarea> obtenerTodos() {
        return servicioTareaRepository.findAll();
    }

    public ServicioTarea obtenerPorId(Long id) {
        return servicioTareaRepository.findById(id).orElse(null);
    }

    public ServicioTarea crear(ServicioTarea servicioTarea) {
        return servicioTareaRepository.save(servicioTarea);
    }

    public ServicioTarea actualizar(Long id, ServicioTarea servicioTarea) {
        ServicioTarea existente = obtenerPorId(id);
        if (existente != null) {
            servicioTarea.setId(existente.getId());
            return servicioTareaRepository.save(servicioTarea);
        }
        return null;
    }

    public void eliminar(Long id) {
        servicioTareaRepository.deleteById(id);
    }
}