package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.Seguridad;
import util.Validador;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

// Sirve para registrar usuarios nuevos y también para actualizar usuarios existentes
public class VentanaRegistro extends VentanaBase implements ActionListener {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtUsuario;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JPasswordField txtClave;
    private JPasswordField txtConfirmar;
    private JButton btnGuardar;
    private JButton btnVolver;

    private VentanaPrincipal principal;   // null si se abrió desde el login
    private Usuario usuarioEditar;        // null si es un usuario nuevo
    private UsuarioDAO dao = new UsuarioDAO();

    public VentanaRegistro(VentanaPrincipal principal, Usuario usuarioEditar) {
        super(usuarioEditar == null ? "Crear cuenta" : "Actualizar usuario", 560, 640);
        this.principal = principal;
        this.usuarioEditar = usuarioEditar;

        // Al cerrar con la X no se cierra todo el programa, solo esta ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrar();
            }
        });
    }

    @Override
    protected void construirInterfaz() {
        setLayout(new GridBagLayout());

        JPanel tarjeta = Estilo.crearTarjeta(500, 500);

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtUsuario = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtClave = new JPasswordField();
        txtConfirmar = new JPasswordField();

        String titulo = "Crear cuenta";
        String subtitulo = "Todos los campos son obligatorios";
        String textoBoton = "Registrar";

        if (usuarioEditar != null) {
            titulo = "Actualizar usuario";
            subtitulo = "Deje las contraseñas vacías para conservar la actual";
            textoBoton = "Guardar cambios";
            txtNombre.setText(usuarioEditar.getNombre());
            txtApellido.setText(usuarioEditar.getApellido());
            txtUsuario.setText(usuarioEditar.getUsuario());
            txtTelefono.setText(usuarioEditar.getTelefono());
            txtCorreo.setText(usuarioEditar.getCorreo());
        }

        btnGuardar = Estilo.crearBoton(textoBoton, Estilo.ACENTO, Estilo.TEXTO);
        btnVolver = Estilo.crearBoton("Volver", Estilo.SECUNDARIO, Color.WHITE);
        btnGuardar.addActionListener(this);
        btnVolver.addActionListener(this);

        tarjeta.add(Estilo.crearTitulo(titulo));
        tarjeta.add(Estilo.crearSubtitulo(subtitulo));
        tarjeta.add(Box.createVerticalStrut(18));
        tarjeta.add(Estilo.crearFilaDoble(
                Estilo.crearCampo("Nombre", txtNombre),
                Estilo.crearCampo("Apellido", txtApellido)));
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(Estilo.crearFilaDoble(
                Estilo.crearCampo("Nombre de usuario", txtUsuario),
                Estilo.crearCampo("Teléfono", txtTelefono)));
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(Estilo.crearCampo("Correo electrónico", txtCorreo));
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(Estilo.crearFilaDoble(
                Estilo.crearCampo("Contraseña", txtClave),
                Estilo.crearCampo("Confirmar contraseña", txtConfirmar)));
        tarjeta.add(Box.createVerticalStrut(22));
        tarjeta.add(btnGuardar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnVolver);

        add(tarjeta);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            guardar();
        } else if (e.getSource() == btnVolver) {
            cerrar();
        }
    }

    private void cerrar() {
        if (principal == null) {
            VentanaFactory.crearLogin().mostrar();   // viene del login donde se vuelve a mostrar
        }
        dispose();
    }

    private boolean estaVacio(JTextField campo) {
        return campo.getText().trim().isEmpty();
    }

    private void mostrarError(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    private boolean validarCampos() {
        // Los campos obligatorios donde se muestran todos los que falten
        ArrayList<String> faltantes = new ArrayList<>();
        if (estaVacio(txtNombre)) {
            faltantes.add("Nombre");
        }
        if (estaVacio(txtApellido)) {
            faltantes.add("Apellido");
        }
        if (estaVacio(txtUsuario)) {
            faltantes.add("Nombre de usuario");
        }
        if (estaVacio(txtTelefono)) {
            faltantes.add("Teléfono");
        }
        if (estaVacio(txtCorreo)) {
            faltantes.add("Correo electrónico");
        }
        if (usuarioEditar == null) {   // en un registro nuevo las contraseñas son obligatorias
            if (txtClave.getPassword().length == 0) {
                faltantes.add("Contraseña");
            }
            if (txtConfirmar.getPassword().length == 0) {
                faltantes.add("Confirmar contraseña");
            }
        }
        if (!faltantes.isEmpty()) {
            mostrarError("Debe completar los siguientes campos: " + String.join(", ", faltantes),
                    "Campos obligatorios");
            return false;
        }

        // El Formato del teléfono y del correo
        if (!Validador.esTelefonoValido(txtTelefono.getText().trim())) {
            mostrarError("El teléfono debe tener 10 números, sin guiones ni espacios",
                    "Teléfono inválido");
            return false;
        }
        if (!Validador.esCorreoValido(txtCorreo.getText().trim())) {
            mostrarError("Escriba un correo válido, por ejemplo: nombre@correo.com",
                    "Correo inválido");
            return false;
        }

        // 3. Las dos contraseñas deben ser iguales
        String clave = new String(txtClave.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());
        if (!clave.equals(confirmar)) {
            mostrarError("La contraseña y la confirmación no coinciden", "Contraseñas distintas");
            txtClave.setText("");
            txtConfirmar.setText("");
            return false;
        }
        return true;
    }

    private void guardar() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String clave = new String(txtClave.getPassword());

        try {
            int idActual = 0;
            if (usuarioEditar != null) {
                idActual = usuarioEditar.getId();
            }
            if (dao.existeUsuario(usuario, idActual)) {
                mostrarError("Ese nombre de usuario ya está en uso, elija otro", "Usuario repetido");
                return;
            }

            if (usuarioEditar == null) {
                Usuario nuevo = new Usuario(0, usuario, nombre, apellido, telefono, correo,
                        Seguridad.encriptar(clave));
                dao.registrar(nuevo);
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente",
                        "Listo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Si no escribió contraseña nueva, se conserva la que ya tenía
                String claveFinal = usuarioEditar.getClave();
                if (!clave.isEmpty()) {
                    claveFinal = Seguridad.encriptar(clave);
                }
                Usuario actualizado = new Usuario(idActual, usuario, nombre, apellido,
                        telefono, correo, claveFinal);
                dao.actualizar(actualizado);
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente",
                        "Listo", JOptionPane.INFORMATION_MESSAGE);
            }

            if (principal != null) {
                principal.cargarTabla();   // la tabla se refresca sola
            }
            cerrar();

        } catch (SQLException ex) {
            mostrarError("Error con la base de datos: " + ex.getMessage(), "Error de base de datos");
        }
    }
}