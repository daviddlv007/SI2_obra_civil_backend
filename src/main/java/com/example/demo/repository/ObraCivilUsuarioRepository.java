package com.example.demo.repository;

import com.example.demo.entity.ObraCivilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraCivilUsuarioRepository extends JpaRepository<ObraCivilUsuario, Long> {

}
