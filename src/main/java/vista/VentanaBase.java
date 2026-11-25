package vista;

import javax.swing.JFrame;

// Clase abstracta donde todas las ventanas del programa heredan de ella.
public abstract class VentanaBase extends JFrame {

    public VentanaBase(String titulo, int ancho, int alto) {
        super(titulo);
        setSize(ancho, alto);
        setLocationRelativeTo(null);                 // centra la ventana en la pantalla
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Estilo.FONDO);
    }

    // Aqui cada ventana hija arma su propia interfaz.
    protected abstract void construirInterfaz();

    public void mostrar() {
        construirInterfaz();
        setVisible(true);
    }
}