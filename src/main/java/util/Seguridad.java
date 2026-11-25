package util;

import java.security.MessageDigest;

public class Seguridad {

    // Convierte la contraseña en un código SHA-256 para no guardarla en texto plano y mantener la privacidad
    public static String encriptar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes("UTF-8"));
            StringBuilder resultado = new StringBuilder();
            for (byte b : bytes) {
                resultado.append(String.format("%02x", b));
            }
            return resultado.toString();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo encriptar la contraseña", e);
        }
    }
}