package com.example.demo.controller;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;

    public AuthController(JwtUtils jwtUtils, 
                         UsuarioRepository usuarioRepository) {
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> credenciales) {
        String correo = (String) credenciales.get("correo");
        String contrasena = (String) credenciales.get("contrasena");

        if (correo == null || contrasena == null) {
            return ResponseEntity.badRequest().body("Debe proporcionar correo y contraseña");
        }

        return usuarioRepository.findByCorreo(correo)
                .map(usuario -> {
                    if (usuario.getContrasena().equals(contrasena)) {
                        String token = jwtUtils.generateToken(correo);
                        return ResponseEntity.ok(Map.of(
                            "token", token,
                            "id", usuario.getId(),
                            "nombre", usuario.getNombre(),
                            "correo", usuario.getCorreo()
                        ));
                    }
                    return ResponseEntity.badRequest().body("Contraseña incorrecta");
                })
                .orElse(ResponseEntity.badRequest().body("Usuario no encontrado"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Logout exitoso"
        ));
    }
}