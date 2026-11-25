package vista;

import modelo.Usuario;

// El Patrón Fábrica aquí se crean todas las ventanas del programa
public class VentanaFactory {

    public static VentanaBase crearLogin() {
        return new VentanaLogin();
    }

    // principal = null y usuario = null -> registro nuevo desde el login
    // principal != null y usuario = null -> usuario nuevo desde la pantalla principal
    // principal != null y usuario != null -> actualizar ese usuario
    public static VentanaBase crearRegistro(VentanaPrincipal principal, Usuario usuario) {
        return new VentanaRegistro(principal, usuario);
    }

    public static VentanaBase crearPrincipal(Usuario usuarioActivo) {
        return new VentanaPrincipal(usuarioActivo);
    }
}