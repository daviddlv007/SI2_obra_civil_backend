package com.example.demo.util;

public class BitacoraContext {

    private static final ThreadLocal<Long> entidadCreadaId = new ThreadLocal<>();

    public static void setEntidadCreadaId(Long id) {
        entidadCreadaId.set(id);
    }

    public static Long getEntidadCreadaId() {
        return entidadCreadaId.get();
    }

    public static void clear() {
        entidadCreadaId.remove();
    }
}
