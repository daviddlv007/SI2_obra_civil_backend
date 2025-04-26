package com.example.demo.interceptor;

import com.example.demo.util.BitacoraContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class BitacoraResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            Long id = extractIdFromBody(body);
            if (id != null) {
                BitacoraContext.setEntidadCreadaId(id);
            }
        }
        return body;
    }

    private Long extractIdFromBody(Object body) {
        try {
            var method = body.getClass().getMethod("getId");
            Object id = method.invoke(body);
            if (id instanceof Long) {
                return (Long) id;
            }
        } catch (Exception ignored) {}
        return null;
    }
}
