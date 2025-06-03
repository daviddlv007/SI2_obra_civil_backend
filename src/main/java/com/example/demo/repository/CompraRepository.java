package com.example.demo.repository;

import com.example.demo.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    Compra findByFecha(LocalDate fecha);
}
