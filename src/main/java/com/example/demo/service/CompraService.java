package com.example.demo.service;

import com.example.demo.entity.Compra;
import com.example.demo.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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

    public Compra cambiarEstadoCompra(Long id, Compra.EstadoCompra nuevoEstado) {
        Compra compra = obtenerCompraPorId(id);
        if (compra != null) {
            compra.setEstadoCompra(nuevoEstado);
            return compraRepository.save(compra);
        }
        return null;
    }

    public Long generarSiguienteNumeroCompra() {
        Long ultimoNumero = compraRepository.findMaxNumeroCompra().orElse(1000L);
        return ultimoNumero + 1;
    }

    public List<Compra> obtenerComprasOrdenadasDescPorId() {
        return compraRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // reportes
    public Page<Compra> filtrarCompras(
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String tipoProveedor,
            String estadoCompra,
            Pageable pageable) {

        Specification<Compra> spec = Specification.where(null);

        if (fechaInicio != null && fechaFin != null) {
            spec = spec.and(fechaEntre(fechaInicio, fechaFin));
        } else if (fechaInicio != null) {
            spec = spec.and(fechaMayorOIgualQue(fechaInicio));
        } else if (fechaFin != null) {
            spec = spec.and(fechaMenorOIgualQue(fechaFin));
        }

        if (tipoProveedor != null && !tipoProveedor.isEmpty()) {
            spec = spec.and(conTipoProveedor(tipoProveedor));
        }

        if (estadoCompra != null && !estadoCompra.isEmpty()) {
            spec = spec.and(conEstadoCompra(estadoCompra));
        }

        return compraRepository.findAll(spec, pageable);
    }

    // Métodos auxiliares para las Specifications
    private Specification<Compra> fechaEntre(LocalDate inicio, LocalDate fin) {
        return (root, query, cb) -> cb.between(root.get("fecha"), inicio, fin);
    }

    private Specification<Compra> fechaMayorOIgualQue(LocalDate fecha) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fecha"), fecha);
    }

    private Specification<Compra> fechaMenorOIgualQue(LocalDate fecha) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("fecha"), fecha);
    }

    private Specification<Compra> conTipoProveedor(String tipo) {
        return (root, query, cb) -> cb.equal(
                root.join("proveedor").get("tipoProveedor"),
                tipo
        );
    }

    private Specification<Compra> conEstadoCompra(String estado) {
        return (root, query, cb) -> cb.equal(
                root.get("estadoCompra"),
                Compra.EstadoCompra.valueOf(estado)
        );
    }
}
