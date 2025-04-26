package com.example.demo.interceptor;

import com.example.demo.entity.Bitacora;
import com.example.demo.repository.BitacoraRepository;
import com.example.demo.security.JwtUtils;
import com.example.demo.util.BitacoraContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BitacoraInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final BitacoraRepository bitacoraRepository;

    private static final Map<String, String> ACTION_MAP = new ConcurrentHashMap<>();
    static {
        ACTION_MAP.put("POST", "creacion");
        ACTION_MAP.put("PUT", "actualizacion");
        ACTION_MAP.put("DELETE", "eliminacion");
    }

    @Autowired
    public BitacoraInterceptor(JwtUtils jwtUtils, BitacoraRepository bitacoraRepository) {
        this.jwtUtils = jwtUtils;
        this.bitacoraRepository = bitacoraRepository;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) throws Exception {
        if (request.getRequestURI().startsWith("/auth")) {
            return;
        }

        try {
            String method = request.getMethod();
            String actionType = ACTION_MAP.get(method);

            // Solo registrar para POST, PUT, DELETE
            if (actionType != null) {
                String token = jwtUtils.resolveToken(request);
                if (token != null && jwtUtils.isTokenValid(token)) {
                    Long userId = jwtUtils.getUserIdFromToken(token);
                    String entityName = extractEntityName(request.getRequestURI());
                    Long entityId = "POST".equals(method)
                                    ? BitacoraContext.getEntidadCreadaId()
                                    : extractEntityId(request.getRequestURI(), method);
                    String ipAddress = request.getRemoteAddr();

                    if (entityId != null) {
                        Bitacora bitacora = Bitacora.builder()
                            .usuarioId(userId)
                            .tipoAccion(actionType)
                            .entidad(entityName)
                            .entidadId(entityId)
                            .ipOrigen(ipAddress)
                            .build();
                        bitacoraRepository.save(bitacora);
                    }
                }
            }
        } finally {
            // Limpieza del ThreadLocal
            BitacoraContext.clear();
        }
    }

    private String extractEntityName(String uri) {
        // Ejemplo: "/personas/1" -> "personas"
        String[] parts = uri.split("/");
        return parts.length > 1 ? parts[1] : "desconocido";
    }

    private Long extractEntityId(String uri, String method) {
        if ("POST".equals(method)) {
            return BitacoraContext.getEntidadCreadaId();
        }
        try {
            String[] parts = uri.split("/");
            if (parts.length > 2) {
                return Long.parseLong(parts[2]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }
}
