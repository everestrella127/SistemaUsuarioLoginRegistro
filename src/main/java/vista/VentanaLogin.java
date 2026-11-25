package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.Seguridad;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VentanaLogin extends VentanaBase implements ActionListener {

    private JTextField txtUsuario;
    private JPasswordField txtClave;   // JPasswordField oculta lo que se escribe
    private JButton btnEntrar;
    private JButton btnRegistro;

    public VentanaLogin() {
        super("Acceso al sistema", 460, 540);
    }

    @Override
    protected void construirInterfaz() {
        setLayout(new GridBagLayout());   // deja la tarjeta centrada en la ventana

        JPanel tarjeta = Estilo.crearTarjeta(360, 360);

        txtUsuario = new JTextField();
        txtClave = new JPasswordField();

        btnEntrar = Estilo.crearBoton("Entrar", Estilo.ACENTO, Estilo.TEXTO);
        btnRegistro = Estilo.crearBoton("Crear cuenta nueva", Estilo.SECUNDARIO, Color.WHITE);
        btnEntrar.addActionListener(this);
        btnRegistro.addActionListener(this);
        getRootPane().setDefaultButton(btnEntrar);   // con la tecla Enter se presiona "Entrar"

        tarjeta.add(Estilo.crearTitulo("Bienvenido"));
        tarjeta.add(Estilo.crearSubtitulo("Inicia sesión para continuar"));
        tarjeta.add(Box.createVerticalStrut(22));
        tarjeta.add(Estilo.crearCampo("Nombre de usuario", txtUsuario));
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(Estilo.crearCampo("Contraseña", txtClave));
        tarjeta.add(Box.createVerticalStrut(22));
        tarjeta.add(btnEntrar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnRegistro);

        add(tarjeta);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEntrar) {
            iniciarSesion();
        } else if (e.getSource() == btnRegistro) {
            VentanaFactory.crearRegistro(null, null).mostrar();
            dispose();   // cierra el login
        }
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse",
                    "Campos incompletos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario encontrado = dao.iniciarSesion(usuario, Seguridad.encriptar(clave));

            if (encontrado == null) {
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos",
                        "Error de acceso", JOptionPane.ERROR_MESSAGE);
                txtClave.setText("");
            } else {
                VentanaFactory.crearPrincipal(encontrado).mostrar();
                dispose();   // cierra el login
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar con la base de datos: " + ex.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}