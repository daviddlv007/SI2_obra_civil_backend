package com.example.demo.service;

import com.example.demo.dto.EstimacionRequest;
import com.example.demo.dto.EstimacionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.*;

/**
 * Servicio actualizado que simula la tabla parametro_costo_construccion con valores estáticos en USD
 * y calcula la estimación de costo, generando un detalle con claves legibles en el Map<String, BigDecimal>.
 */
@Service
public class EstimacionService {

    private static class ParametroCosto {
        String parametro;
        String valor;
        String unidad;
        BigDecimal costo;

        ParametroCosto(String parametro, String valor, String unidad, BigDecimal costo) {
            this.parametro = parametro;
            this.valor = valor;
            this.unidad = unidad;
            this.costo = costo;
        }
    }

    private static final List<ParametroCosto> PARAMETROS_STATIC;

    static {
        List<ParametroCosto> list = new ArrayList<>();
        // NOTA: usar siempre sin acentos en 'parametro' y 'valor'
        // Terreno / suelo (preparación por m2)
        list.add(new ParametroCosto("tipo_suelo", "arenoso", "USD/m2", new BigDecimal("15.00")));
        list.add(new ParametroCosto("tipo_suelo", "rocoso", "USD/m2", new BigDecimal("30.00")));
        list.add(new ParametroCosto("tipo_suelo", "arcilloso", "USD/m2", new BigDecimal("20.00")));
        // Cimentación (por m2 de plataforma)
        list.add(new ParametroCosto("tipo_cimentacion", "radier", "USD/m2", new BigDecimal("50.00")));
        list.add(new ParametroCosto("tipo_cimentacion", "zapatas", "USD/m2", new BigDecimal("80.00")));
        list.add(new ParametroCosto("tipo_cimentacion", "pilotes", "USD/m2", new BigDecimal("120.00")));
        // Estructura (por m2 construida)
        list.add(new ParametroCosto("tipo_estructura", "hormigon", "USD/m2", new BigDecimal("120.00")));
        list.add(new ParametroCosto("tipo_estructura", "metalica", "USD/m2", new BigDecimal("150.00")));
        list.add(new ParametroCosto("tipo_estructura", "mixta", "USD/m2", new BigDecimal("140.00")));
        // Acabado / terminaciones (multiplicador sobre base)
        list.add(new ParametroCosto("calidad_acabado", "basica", "%", new BigDecimal("-10.00"))); // -10%
        list.add(new ParametroCosto("calidad_acabado", "media", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("calidad_acabado", "alta", "%", new BigDecimal("20.00")));   // +20%
        // Logística / acceso
        list.add(new ParametroCosto("acceso_obra", "facil", "USD/m2", new BigDecimal("0.00")));
        list.add(new ParametroCosto("acceso_obra", "moderado", "USD/m2", new BigDecimal("5.00")));
        list.add(new ParametroCosto("acceso_obra", "dificil", "USD/m2", new BigDecimal("10.00")));
        // Zona / ubicación (multiplicador)
        list.add(new ParametroCosto("zona", "urbana", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("zona", "periurbana", "%", new BigDecimal("5.00")));
        list.add(new ParametroCosto("zona", "rural", "%", new BigDecimal("10.00")));
        // Complejidad de diseño (multiplicador)
        list.add(new ParametroCosto("complejidad_diseno", "baja", "%", new BigDecimal("-5.00")));
        list.add(new ParametroCosto("complejidad_diseno", "media", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("complejidad_diseno", "alta", "%", new BigDecimal("20.00")));
        // Número de pisos
        list.add(new ParametroCosto("numero_pisos", "1", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("numero_pisos", "2", "%", new BigDecimal("10.00")));
        list.add(new ParametroCosto("numero_pisos", "3-5", "%", new BigDecimal("20.00")));
        list.add(new ParametroCosto("numero_pisos", "6+", "%", new BigDecimal("30.00")));
        // Uso / tipología
        list.add(new ParametroCosto("uso", "residencial", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("uso", "comercial", "%", new BigDecimal("15.00")));
        list.add(new ParametroCosto("uso", "industrial", "%", new BigDecimal("20.00")));
        // Instalaciones interiores
        list.add(new ParametroCosto("instalaciones", "basicas", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("instalaciones", "medias", "%", new BigDecimal("10.00")));
        list.add(new ParametroCosto("instalaciones", "completas", "%", new BigDecimal("20.00")));
        // Estudio geotécnico (costo fijo prorrateado)
        list.add(new ParametroCosto("estudio_geotecnico", "basico", "USD", new BigDecimal("5000.00")));
        list.add(new ParametroCosto("estudio_geotecnico", "avanzado", "USD", new BigDecimal("10000.00")));
        // Diseño sostenible
        list.add(new ParametroCosto("diseno_sostenible", "no", "%", new BigDecimal("0.00")));
        list.add(new ParametroCosto("diseno_sostenible", "si", "%", new BigDecimal("5.00")));
        // Honorarios profesionales
        list.add(new ParametroCosto("honorarios_profesionales", "base", "%", new BigDecimal("10.00")));
        // Permisos y licencias
        list.add(new ParametroCosto("permisos", "no", "USD", new BigDecimal("0.00")));
        list.add(new ParametroCosto("permisos", "si", "USD", new BigDecimal("3000.00")));
        // Otros
        list.add(new ParametroCosto("flete_km", "base", "USD/km", new BigDecimal("1.50")));
        list.add(new ParametroCosto("indirectos", "base", "%", new BigDecimal("8.00")));
        list.add(new ParametroCosto("margen_utilidad", "base", "%", new BigDecimal("12.00")));
        PARAMETROS_STATIC = Collections.unmodifiableList(list);
    }

    /**
     * Normaliza una cadena: quita tildes y pone en minúsculas.
     */
    private String normalize(String input) {
        if (input == null) return null;
        String norm = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return norm.toLowerCase(Locale.ROOT).trim();
    }

    /**
     * Convierte una clave técnica (snake_case) o valor en una etiqueta legible.
     * e.g. "tipo_suelo" → "Tipo Suelo", "arcilloso" → "Arcilloso".
     */
    private String humanize(String texto) {
        if (texto == null) return "";
        String s = texto.replace('_', ' ').toLowerCase(Locale.ROOT);
        String[] parts = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            sb.append(Character.toUpperCase(p.charAt(0)))
              .append(p.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }

    public EstimacionResponse calcularEstimacion(EstimacionRequest request) {
        Double superficie = request.getSuperficieM2();
        if (superficie == null || superficie <= 0) {
            throw new IllegalArgumentException("Superficie debe ser un valor positivo.");
        }
        BigDecimal superficieM2 = BigDecimal.valueOf(superficie);

        Map<String, String> seleccion = request.getParametros();
        if (seleccion == null || seleccion.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionarse al menos un parámetro para la estimación.");
        }

        // Filtrar y normalizar coincidencias
        List<ParametroCosto> encontrados = new ArrayList<>();
        for (Map.Entry<String, String> entry : seleccion.entrySet()) {
            String claveRaw = entry.getKey();
            String valorRaw = entry.getValue();
            String claveNorm = normalize(claveRaw);
            String valorNorm = normalize(valorRaw);

            Optional<ParametroCosto> match = PARAMETROS_STATIC.stream()
                    .filter(pc -> normalize(pc.parametro).equals(claveNorm)
                            && normalize(pc.valor).equals(valorNorm))
                    .findFirst();
            if (match.isPresent()) {
                encontrados.add(match.get());
            } else {
                System.out.println("Advertencia: no se halló costo para parámetro=" + claveRaw
                        + ", valor=" + valorRaw);
            }
        }

        BigDecimal baseUsdPorM2 = BigDecimal.ZERO;
        BigDecimal multiplicadorAcumulado = BigDecimal.ONE;
        BigDecimal sumaFijosProrrateados = BigDecimal.ZERO;

        // Generar detalle con claves legibles
        Map<String, BigDecimal> detalleLegible = new LinkedHashMap<>();

        // Procesar cada parámetro encontrado
        for (ParametroCosto pc : encontrados) {
            String clave = pc.parametro;
            String valorRaw = pc.valor;
            String etiquetaParam = humanize(clave);
            String etiquetaValor = humanize(valorRaw);
            String unidad = pc.unidad.trim();
            BigDecimal costo = pc.costo;

            if ("USD/m2".equals(unidad)) {
                // Aporte directo
                baseUsdPorM2 = baseUsdPorM2.add(costo);
                String key = String.format("Costo %s (%s) [USD/m²]", etiquetaParam, etiquetaValor);
                detalleLegible.put(key, costo.setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            else if ("%".equals(unidad)) {
                // Multiplicador
                BigDecimal factor = BigDecimal.ONE.add(
                        costo.divide(BigDecimal.valueOf(100), 6, BigDecimal.ROUND_HALF_UP)
                );
                multiplicadorAcumulado = multiplicadorAcumulado.multiply(factor);
                String key = String.format("Multiplicador %s (%s)", etiquetaParam, etiquetaValor);
                detalleLegible.put(key, factor.setScale(4, BigDecimal.ROUND_HALF_UP));
            }
            else if ("USD".equals(unidad)) {
                // Costo fijo prorrateado
                sumaFijosProrrateados = sumaFijosProrrateados.add(costo);
                BigDecimal prorrateo = costo.divide(superficieM2, 6, BigDecimal.ROUND_HALF_UP)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);
                String key = String.format("Prorrateo %s (%s) [USD/m²]", etiquetaParam, etiquetaValor);
                detalleLegible.put(key, prorrateo);
            }
            else {
                // Unidades no procesadas en este MVP mejorado
                String key = String.format("%s (%s) - unidad no procesada: %s",
                        etiquetaParam, etiquetaValor, unidad);
                detalleLegible.put(key, costo.setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }

        // Prorratear fijos a la base
        if (sumaFijosProrrateados.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal prorrateoTotal = sumaFijosProrrateados.divide(superficieM2, 6, BigDecimal.ROUND_HALF_UP);
            baseUsdPorM2 = baseUsdPorM2.add(prorrateoTotal);
        }

        // Totales intermedios y finales
        BigDecimal costoBasePorM2 = baseUsdPorM2.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal multiplicadorTotal = multiplicadorAcumulado.setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal costoPorM2 = baseUsdPorM2.multiply(multiplicadorAcumulado).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal costoTotal = costoPorM2.multiply(superficieM2).setScale(2, BigDecimal.ROUND_HALF_UP);

        detalleLegible.put("Costo base por m² [USD/m²]", costoBasePorM2);
        detalleLegible.put("Multiplicador total", multiplicadorTotal);
        if (sumaFijosProrrateados.compareTo(BigDecimal.ZERO) > 0) {
            detalleLegible.put("Suma fijos prorrateados [USD]", sumaFijosProrrateados.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        detalleLegible.put("Costo final por m² [USD/m²]", costoPorM2);
        detalleLegible.put("Costo total [USD]", costoTotal);

        // Construir y devolver la respuesta
        EstimacionResponse response = new EstimacionResponse();
        response.setCostoPorM2(costoPorM2);
        response.setCostoTotal(costoTotal);
        response.setDetalle(detalleLegible);
        return response;
    }
}
