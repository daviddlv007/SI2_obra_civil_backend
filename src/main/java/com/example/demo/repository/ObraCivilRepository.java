package com.example.demo.repository;

import com.example.demo.entity.ObraCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraCivilRepository extends JpaRepository<ObraCivil, Long> {
}
