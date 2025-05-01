package com.example.demo.repository;

import com.example.demo.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Empleado findByDocumentoIdentidad(String documentoIdentidad);
    Empleado findByEmail(String email);
    List<Empleado> findByActivo(Boolean activo);
}