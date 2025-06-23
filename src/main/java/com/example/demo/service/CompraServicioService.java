package com.example.demo.service;

import com.example.demo.entity.CompraServicio;
import com.example.demo.repository.CompraServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServicioService {

    private final CompraServicioRepository compraServicioRepository;

    @Autowired
    public CompraServicioService(CompraServicioRepository compraServicioRepository) {
        this.compraServicioRepository = compraServicioRepository;
    }

    public List<CompraServicio> obtenerTodos() {
        return compraServicioRepository.findAll();
    }

    public CompraServicio obtenerPorId(Long id) {
        return compraServicioRepository.findById(id).orElse(null);
    }

    public CompraServicio crear(CompraServicio compraServicio) {
        return compraServicioRepository.save(compraServicio);
    }

    public CompraServicio actualizar(Long id, CompraServicio compraServicio) {
        CompraServicio existente = obtenerPorId(id);
        if (existente != null) {
            compraServicio.setId(existente.getId());
            return compraServicioRepository.save(compraServicio);
        }
        return null;
    }

    public void eliminar(Long id) {
        compraServicioRepository.deleteById(id);
    }

    public List<CompraServicio> obtenerComprasPorId(Long compraId) {
        return compraServicioRepository.findByCompraId(compraId);
    }

}
