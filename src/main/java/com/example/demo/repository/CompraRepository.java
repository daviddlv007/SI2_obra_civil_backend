package com.example.demo.repository;

import com.example.demo.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>, JpaSpecificationExecutor<Compra> {
    Compra findByFecha(LocalDate fecha);

    @Query("SELECT MAX(c.numeroCompra) FROM Compra c")
    Optional<Long> findMaxNumeroCompra();
}
