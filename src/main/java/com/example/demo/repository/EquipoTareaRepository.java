package com.example.demo.repository;

import com.example.demo.entity.EquipoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoTareaRepository extends JpaRepository<EquipoTarea, Long> {
}