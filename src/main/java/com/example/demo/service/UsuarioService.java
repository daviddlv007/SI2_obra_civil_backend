package com.example.demo.service;

import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);
        if (usuarioExistente != null) {
            usuario.setId(usuarioExistente.getId());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    // Obtener todos los usuarios ordenados por ID de manera descendente
    public List<Usuario> getAllUsuariosDesc() {
        return usuarioRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    // Obtener todos los usuarios ordenados por ID de manera ascendente
    public List<Usuario> getAllUsuariosAsc() {
        return usuarioRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    // Obtener usuarios ordenados según el parámetro Sort
    public List<Usuario> getUsuariosOrdenados(Sort sort) {
        return usuarioRepository.findAll(sort);
    }

    // Obtener usuarios por nombre del rol
    public List<Usuario> getUsuariosByRolNombre(String nombreRol) {
        // Buscar el rol por su nombre
        Rol rol = rolRepository.findByNombre(nombreRol);

        if (rol != null) {
            // Si el rol existe, obtener los usuarios con ese rol
            return usuarioRepository.findByRol(rol);
        } else {
            // Si el rol no existe, retornar una lista vacía o lanzar una excepción
            return List.of();  // Lista vacía o una excepción personalizada
        }
    }

}