package com.PROGRA2.BACKEND.security;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/** Escribe respuestas JSON simples desde los filtros/handlers de seguridad (sin depender de Jackson). */
final class RespuestaJson {

    private RespuestaJson() {
    }

    static void escribir(HttpServletResponse res, int estado, Map<String, ?> datos) throws IOException {
        res.setStatus(estado);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(aJson(datos));
    }

    static String aJson(Map<String, ?> datos) {
        StringBuilder sb = new StringBuilder("{");
        boolean primero = true;
        for (Map.Entry<String, ?> e : datos.entrySet()) {
            if (!primero) sb.append(',');
            primero = false;
            sb.append('"').append(escapar(e.getKey())).append("\":");
            Object v = e.getValue();
            if (v == null) sb.append("null");
            else if (v instanceof Number || v instanceof Boolean) sb.append(v);
            else sb.append('"').append(escapar(String.valueOf(v))).append('"');
        }
        return sb.append('}').toString();
    }

    static String escapar(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }
}
