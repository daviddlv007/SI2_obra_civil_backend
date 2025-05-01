package com.example.demo.repository;

import com.example.demo.entity.MaterialTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialTareaRepository extends JpaRepository<MaterialTarea, Long> {
}