package com.example.demo.repository;

import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    // Buscar todos los usuarios y ordenarlos por id de manera descendente
    List<Usuario> findAll(Sort sort);

    // Buscar usuarios por rol (con rol como parámetro)
    List<Usuario> findByRol(Rol rol);

}