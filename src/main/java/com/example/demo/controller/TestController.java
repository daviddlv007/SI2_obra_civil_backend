package com.example.demo.controller;

import com.example.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final JwtUtils jwtUtils;

    public TestController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfoFromToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Eliminar "Bearer "
            String username = jwtUtils.getClaimsFromToken(token).getSubject();
            Long userId = jwtUtils.getUserIdFromToken(token);
            
            return ResponseEntity.ok(Map.of(
                "username", username,
                "userId", userId,
                "message", "Información extraída del token JWT"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token inválido o no proporcionado");
        }
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok(Map.of(
            "message", "Esta es una ruta protegida",
            "status", "success"
        ));
    }
}