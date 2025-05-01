package com.example.demo.repository;

import com.example.demo.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Servicio findByCodigoServicio(String codigoServicio);
}