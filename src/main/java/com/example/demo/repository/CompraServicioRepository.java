package com.example.demo.repository;

import com.example.demo.entity.CompraServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraServicioRepository extends JpaRepository<CompraServicio, Long> {
    List<CompraServicio> findByCompraId(Long compraId);
}
