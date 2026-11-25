package vista;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

// Aquí se encuentra el diseño de la aplicación: colores, fuentes y componentes
public class Estilo {

    public static final Color FONDO = new Color(15, 27, 51);        // azul noche
    public static final Color TARJETA = new Color(245, 241, 232);   // crema
    public static final Color ACENTO = new Color(244, 162, 38);     // ámbar
    public static final Color SECUNDARIO = new Color(61, 90, 128);  // azul acero
    public static final Color PELIGRO = new Color(192, 57, 43);     // rojo
    public static final Color TEXTO = new Color(33, 41, 60);        // gris oscuro

    public static final Font TITULO = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font NEGRITA = new Font("Segoe UI", Font.BOLD, 14);

    // Panel vertical de color crema donde van los formularios
    public static JPanel crearTarjeta(int ancho, int alto) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(TARJETA);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        tarjeta.setPreferredSize(new Dimension(ancho, alto));
        return tarjeta;
    }

    public static JLabel crearTitulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(TITULO);
        titulo.setForeground(FONDO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titulo;
    }

    public static JLabel crearSubtitulo(String texto) {
        JLabel subtitulo = new JLabel(texto);
        subtitulo.setFont(NORMAL);
        subtitulo.setForeground(new Color(110, 115, 130));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return subtitulo;
    }

    // Etiqueta + campo de texto
    public static JPanel crearCampo(String etiqueta, JTextField campo) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(NEGRITA);
        lbl.setForeground(TEXTO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        campo.setFont(NORMAL);
        campo.setPreferredSize(new Dimension(100, 34));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(185, 180, 168)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        return panel;
    }

    // Dos campos lado a lado
    public static JPanel crearFilaDoble(JPanel izquierda, JPanel derecha) {
        JPanel fila = new JPanel(new GridLayout(1, 2, 14, 0));
        fila.setOpaque(false);
        fila.add(izquierda);
        fila.add(derecha);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, fila.getPreferredSize().height));
        return fila;
    }

    public static JButton crearBoton(String texto, Color fondo, Color letra) {
        JButton boton = new JButton(texto);
        boton.setFont(NEGRITA);
        boton.setBackground(fondo);
        boton.setForeground(letra);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return boton;
    }
}