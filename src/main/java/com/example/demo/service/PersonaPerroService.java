package com.example.demo.service;

import com.example.demo.entity.PersonaPerro;
import com.example.demo.repository.PersonaPerroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaPerroService {

    private final PersonaPerroRepository personaPerroRepository;

    @Autowired
    public PersonaPerroService(PersonaPerroRepository personaPerroRepository) {
        this.personaPerroRepository = personaPerroRepository;
    }

    // Obtener todas las relaciones
    public List<PersonaPerro> obtenerTodasLasRelaciones() {
        return personaPerroRepository.findAll();
    }

    // Obtener relación por ID
    public PersonaPerro obtenerRelacionPorId(Long id) {
        return personaPerroRepository.findById(id).orElse(null); // Devuelve null si no se encuentra
    }

    // Crear relación
    public PersonaPerro crearRelacion(PersonaPerro personaPerro) {
        return personaPerroRepository.save(personaPerro);
    }

    // Actualizar relación
    public PersonaPerro actualizarRelacion(Long id, PersonaPerro personaPerro) {
        PersonaPerro relacionExistente = obtenerRelacionPorId(id);
        if (relacionExistente != null) {
            personaPerro.setId(relacionExistente.getId());
            return personaPerroRepository.save(personaPerro);
        }
        return null;
    }

    // Eliminar relación
    public void eliminarRelacion(Long id) {
        personaPerroRepository.deleteById(id);
    }
}
