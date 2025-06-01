package com.example.demo.repository;

import com.example.demo.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Buscar por NIT o CI (campo único)
    Proveedor findByNitCi(String nitCi);
}
