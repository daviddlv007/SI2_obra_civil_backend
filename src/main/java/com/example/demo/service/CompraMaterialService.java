package com.example.demo.service;

import com.example.demo.entity.CompraMaterial;
import com.example.demo.repository.CompraMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraMaterialService {

    private final CompraMaterialRepository compraMaterialRepository;

    @Autowired
    public CompraMaterialService(CompraMaterialRepository compraMaterialRepository) {
        this.compraMaterialRepository = compraMaterialRepository;
    }

    public List<CompraMaterial> obtenerTodos() {
        return compraMaterialRepository.findAll();
    }

    public CompraMaterial obtenerPorId(Long id) {
        return compraMaterialRepository.findById(id).orElse(null);
    }

    public CompraMaterial crear(CompraMaterial compraMaterial) {
        return compraMaterialRepository.save(compraMaterial);
    }

    public CompraMaterial actualizar(Long id, CompraMaterial compraMaterial) {
        CompraMaterial existente = obtenerPorId(id);
        if (existente != null) {
            compraMaterial.setId(existente.getId());
            return compraMaterialRepository.save(compraMaterial);
        }
        return null;
    }

    public void eliminar(Long id) {
        compraMaterialRepository.deleteById(id);
    }

    public List<CompraMaterial> obtenerComprasPorId(Long compraId) {
        return compraMaterialRepository.findByCompraId(compraId);
    }
}
