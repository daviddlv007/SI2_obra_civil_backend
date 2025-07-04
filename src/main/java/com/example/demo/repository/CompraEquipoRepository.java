package com.example.demo.repository;

import com.example.demo.entity.CompraEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraEquipoRepository extends JpaRepository<CompraEquipo, Long> {
    List<CompraEquipo> findByCompraId(Long compraId);
}
