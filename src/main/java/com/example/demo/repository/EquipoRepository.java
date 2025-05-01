package com.example.demo.repository;

import com.example.demo.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Equipo findByCodigoActivo(String codigoActivo);
}