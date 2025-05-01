package com.example.demo.repository;

import com.example.demo.entity.ServicioTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioTareaRepository extends JpaRepository<ServicioTarea, Long> {
}