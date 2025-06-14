package com.example.demo.service;

import com.example.demo.entity.Compra;
import com.example.demo.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    @Autowired
    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<Compra> obtenerTodosLasCompras() {
        return compraRepository.findAll();
    }

    public Compra obtenerCompraPorId(Long id) {
        return compraRepository.findById(id).orElse(null);
    }

    public Compra obtenerProveedorPorFecha(LocalDate fecha) {
        return compraRepository.findByFecha(fecha);
    }

    public Compra crearCompra(Compra compra) {

        compra.setNumeroCompra(this.generarSiguienteNumeroCompra());
        return compraRepository.save(compra);
    }

    public Compra actualizarCompra(Long id, Compra compra) {
        Compra compraExistente = obtenerCompraPorId(id);
        if (compraExistente != null) {
            compra.setId(compraExistente.getId());
            return compraRepository.save(compra);
        }
        return null;
    }

    public void eliminarCompra(Long id) {
        compraRepository.deleteById(id);
    }

    public Long generarSiguienteNumeroCompra() {
        Long ultimoNumero = compraRepository.findMaxNumeroCompra().orElse(1000L);
        return ultimoNumero + 1;
    }

    public List<Compra> obtenerComprasOrdenadasDescPorId() {
        return compraRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
