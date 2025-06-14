package com.example.demo.service;

import com.example.demo.entity.Proveedor;
import com.example.demo.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public Proveedor obtenerProveedorPorNitCi(String nitCi) {
        return proveedorRepository.findByNitCi(nitCi);
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizarProveedor(Long id, Proveedor proveedor) {
        Proveedor proveedorExistente = obtenerProveedorPorId(id);
        if (proveedorExistente != null) {
            proveedor.setId(proveedorExistente.getId());
            return proveedorRepository.save(proveedor);
        }
        return null;
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    // Nuevo método para filtrar por tipoProveedor
    public List<Proveedor> obtenerProveedoresPorTipo(Proveedor.TipoProveedor tipoProveedor) {
        return proveedorRepository.findByTipoProveedor(tipoProveedor);
    }
}
