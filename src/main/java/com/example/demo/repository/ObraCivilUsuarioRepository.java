package com.example.demo.repository;

import com.example.demo.entity.ObraCivil;
import com.example.demo.entity.ObraCivilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraCivilUsuarioRepository extends JpaRepository<ObraCivilUsuario, Long> {

    // Obtener las obras civiles con los usuarios filtrados por rol (Empleado y Cliente)
    @Query("SELECT o FROM ObraCivilUsuario ocu " +
            "JOIN ocu.obraCivil o " +
            "JOIN ocu.usuario u " +
            "WHERE u.rol.nombre IN ('Empleado', 'Cliente')")
    List<ObraCivil> findObrasCivilesConUsuariosPorRoles();

    // Método para buscar por obra_civil_id y rol de usuario
    List<ObraCivilUsuario> findByObraCivilIdAndUsuarioRolId(Long obraCivilId, Long rolId);

    // Método para buscar por obra_civil_id y nombre del rol de usuario
    List<ObraCivilUsuario> findByObraCivilIdAndUsuarioRolNombre(Long obraCivilId, String rolNombre);
}
