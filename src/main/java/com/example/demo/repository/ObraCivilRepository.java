package com.example.demo.repository;

import com.example.demo.entity.ObraCivil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraCivilRepository extends JpaRepository<ObraCivil, Long> {

    // Listar todas las obras civiles y ordenarlos por id de manera descendente o ascendente
    List<ObraCivil> findAll(Sort sort);
}
