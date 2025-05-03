package com.example.demo.controller;

import com.example.demo.entity.Bitacora;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.BitacoraRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;
    private final BitacoraRepository bitacoraRepository;

    // Leer la propiedad de habilitación de la bitácora
    @Value("${bitacora.interceptor.enabled:false}")
    private boolean interceptorEnabled;

    public AuthController(JwtUtils jwtUtils,
                          UsuarioRepository usuarioRepository,
                          BitacoraRepository bitacoraRepository) {
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraRepository = bitacoraRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> credenciales, HttpServletRequest request) {
        String correo = (String) credenciales.get("correo");
        String contrasena = (String) credenciales.get("contrasena");

        if (correo == null || contrasena == null) {
            return ResponseEntity.badRequest().body("Debe proporcionar correo y contraseña");
        }

        return usuarioRepository.findByCorreo(correo)
                .map(usuario -> {
                    if (usuario.getContrasena().equals(contrasena)) {
                        String token = jwtUtils.generateToken(correo, usuario.getId());

                        // Solo guardar la bitácora si está habilitada
                        if (interceptorEnabled) {
                            Bitacora bitacora = Bitacora.builder()
                                    .usuarioId(usuario.getId())
                                    .tipoAccion("inicio de sesion")
                                    .entidad("sesion")
                                    .entidadId(usuario.getId())
                                    .ipOrigen(request.getRemoteAddr())
                                    .build();
                            bitacoraRepository.save(bitacora);
                        }

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
    public ResponseEntity<Map<String, String>> logout(
            @RequestHeader("Authorization") String token,
            HttpServletRequest request) {

        String cleanToken = token.replace("Bearer ", "");
        Long userId = jwtUtils.getUserIdFromToken(cleanToken);

        // Solo guardar la bitácora si está habilitada
        if (interceptorEnabled) {
            Bitacora bitacora = Bitacora.builder()
                    .usuarioId(userId)
                    .tipoAccion("cierre de sesion")
                    .entidad("sesion")
                    .entidadId(userId)
                    .ipOrigen(request.getRemoteAddr())
                    .build();
            bitacoraRepository.save(bitacora);
        }

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Logout exitoso"
        ));
    }
}
