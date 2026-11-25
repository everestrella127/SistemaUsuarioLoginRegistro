import vista.VentanaFactory;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Swing debe iniciarse en su propio hilo
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaFactory.crearLogin().mostrar();
            }
        });
    }
}