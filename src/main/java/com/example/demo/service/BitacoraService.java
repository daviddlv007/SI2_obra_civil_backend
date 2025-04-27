package com.example.demo.service;

import com.example.demo.entity.Bitacora;
import com.example.demo.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    @Autowired
    public BitacoraService(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }

    // Obtener todas las entradas de bitácora
    public List<Bitacora> obtenerTodasLasBitacoras() {
        return bitacoraRepository.findAll();
    }

    // Obtener una entrada específica por ID
    public Bitacora obtenerBitacoraPorId(Long id) {
        return bitacoraRepository.findById(id).orElse(null);
    }
}
