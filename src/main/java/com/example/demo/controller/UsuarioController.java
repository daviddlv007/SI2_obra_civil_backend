package com.example.demo.controller;

import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        if (usuarioActualizado != null) {
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-correo/{correo}")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // Listar todos los usuarios ordenados por ID de manera descendente
    @GetMapping("/descendente")
    public ResponseEntity<List<Usuario>> getUsuariosDesc() {
        List<Usuario> usuarios = usuarioService.getAllUsuariosDesc();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Listar todos los usuarios ordenados por ID de manera ascendente
    @GetMapping("/ascendente")
    public ResponseEntity<List<Usuario>> getUsuariosAsc() {
        List<Usuario> usuarios = usuarioService.getAllUsuariosAsc();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Listar todos los usuarios ordenados por un campo especificado en la URL
    //GET /api/usuarios/ordenado?campo=id&orden=desc: Ordena por id en orden descendente
    //GET /api/usuarios/ordenado?campo=nombre&orden=asc: Ordena por nombre en orden ascendente
    @GetMapping("/ordenado")
    public ResponseEntity<List<Usuario>> getUsuariosOrdenados(
            @RequestParam(value = "campo", defaultValue = "id") String campo,
            @RequestParam(value = "orden", defaultValue = "desc") String orden) {

        Sort sort = Sort.by(orden.equalsIgnoreCase("asc") ? Sort.Order.asc(campo) : Sort.Order.desc(campo));
        List<Usuario> usuarios = usuarioService.getUsuariosOrdenados(sort);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Listar usuarios por nombre de rol
    @GetMapping("/por-rol/{nombreRol}")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable String nombreRol) {
        // Obtener los usuarios con el rol especificado por nombre
        List<Usuario> usuarios = usuarioService.getUsuariosByRolNombre(nombreRol);

        // Si no se encuentran usuarios con ese rol, respondemos con un 404
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Devolver los usuarios encontrados
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}