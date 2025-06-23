package com.example.demo.service;

import com.example.demo.entity.CompraEquipo;
import com.example.demo.repository.CompraEquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraEquipoService {

    private final CompraEquipoRepository compraEquipoRepository;

    @Autowired
    public CompraEquipoService(CompraEquipoRepository compraEquipoRepository) {
        this.compraEquipoRepository = compraEquipoRepository;
    }

    public List<CompraEquipo> obtenerTodos() {
        return compraEquipoRepository.findAll();
    }

    public CompraEquipo obtenerPorId(Long id) {
        return compraEquipoRepository.findById(id).orElse(null);
    }

    public CompraEquipo crear(CompraEquipo compraEquipo) {
        return compraEquipoRepository.save(compraEquipo);
    }

    public CompraEquipo actualizar(Long id, CompraEquipo compraEquipo) {
        CompraEquipo existente = obtenerPorId(id);
        if (existente != null) {
            compraEquipo.setId(existente.getId());
            return compraEquipoRepository.save(compraEquipo);
        }
        return null;
    }

    public void eliminar(Long id) {
        compraEquipoRepository.deleteById(id);
    }

    public List<CompraEquipo> obtenerComprasPorId(Long compraId) {
        return compraEquipoRepository.findByCompraId(compraId);
    }
}
