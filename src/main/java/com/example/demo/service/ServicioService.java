package com.example.demo.service;

import com.example.demo.entity.Servicio;
import com.example.demo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    @Autowired
    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }

    public Servicio obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    public Servicio obtenerServicioPorCodigo(String codigo) {
        return servicioRepository.findByCodigoServicio(codigo);
    }

    public Servicio crearServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio actualizarServicio(Long id, Servicio servicio) {
        Servicio servicioExistente = obtenerServicioPorId(id);
        if (servicioExistente != null) {
            servicio.setId(servicioExistente.getId());
            return servicioRepository.save(servicio);
        }
        return null;
    }

    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }
}