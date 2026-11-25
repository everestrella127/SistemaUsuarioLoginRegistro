package util;

public class Validador {

    // El teléfono debe tener exactamente 10 números
    public static boolean esTelefonoValido(String telefono) {
        if (telefono.length() != 10) {
            return false;
        }
        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // El correo debe tener algo antes del @ y un punto después del @
    public static boolean esCorreoValido(String correo) {
        int arroba = correo.indexOf("@");
        int punto = correo.lastIndexOf(".");
        return arroba > 0
                && punto > arroba + 1
                && punto < correo.length() - 1
                && !correo.contains(" ");
    }
}