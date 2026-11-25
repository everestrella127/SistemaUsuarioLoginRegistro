package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPrincipal extends VentanaBase implements ActionListener {

    private Usuario usuarioActivo;
    private final UsuarioDAO dao = new UsuarioDAO();
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    private JTable tabla;
    private DefaultTableModel modelo;
    private JLabel lblSesion;
    private JButton btnNuevo;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnCerrarSesion;

    public VentanaPrincipal(Usuario usuarioActivo) {
        super("Panel de usuarios", 920, 560);
        this.usuarioActivo = usuarioActivo;
    }

    @Override
    protected void construirInterfaz() {
        setLayout(new BorderLayout());

        // Aqui va la parte de arriba, osea título y quién tiene la sesión iniciada
        JLabel titulo = Estilo.crearTitulo("Usuarios registrados");
        titulo.setForeground(Color.WHITE);
        lblSesion = Estilo.crearSubtitulo("");
        lblSesion.setForeground(Estilo.ACENTO);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(lblSesion);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Estilo.FONDO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(20, 24, 16, 24));
        encabezado.add(textos, BorderLayout.WEST);

        // El centro de la tabla
        String[] columnas = {"Nombre", "Apellido", "Teléfono", "Correo electrónico", "Usuario"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);   // las celdas no se pueden escribir
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(30);
        tabla.setFont(Estilo.NORMAL);
        tabla.setForeground(Estilo.TEXTO);
        tabla.setBackground(Estilo.TARJETA);
        tabla.setSelectionBackground(Estilo.ACENTO);
        tabla.setSelectionForeground(Estilo.TEXTO);
        tabla.setGridColor(new Color(215, 209, 196));
        tabla.getTableHeader().setFont(Estilo.NEGRITA);
        tabla.getTableHeader().setBackground(Estilo.SECUNDARIO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Estilo.TARJETA);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Estilo.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        centro.add(scroll, BorderLayout.CENTER);

        // La parte de abajo donde se encuentran los botones
        btnNuevo = Estilo.crearBoton("Nuevo usuario", Estilo.ACENTO, Estilo.TEXTO);
        btnActualizar = Estilo.crearBoton("Actualizar", Estilo.SECUNDARIO, Color.WHITE);
        btnEliminar = Estilo.crearBoton("Eliminar", Estilo.PELIGRO, Color.WHITE);
        btnCerrarSesion = Estilo.crearBoton("Cerrar sesión", new Color(70, 82, 110), Color.WHITE);
        btnNuevo.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnCerrarSesion.addActionListener(this);

        JPanel acciones = new JPanel(new GridLayout(1, 3, 10, 0));
        acciones.setOpaque(false);
        acciones.add(btnNuevo);
        acciones.add(btnActualizar);
        acciones.add(btnEliminar);

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(Estilo.FONDO);
        barra.setBorder(BorderFactory.createEmptyBorder(16, 24, 20, 24));
        barra.add(acciones, BorderLayout.WEST);
        barra.add(btnCerrarSesion, BorderLayout.EAST);

        add(encabezado, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(barra, BorderLayout.SOUTH);

        cargarTabla();
    }

    // Lee los usuarios de MySQL y los muestra en la tabla. Se llama cada vez que hay cambios
    public void cargarTabla() {
        try {
            listaUsuarios = dao.listar();
        } catch (SQLException ex) {
            mostrarErrorBD(ex);
            return;
        }

        modelo.setRowCount(0);   // vacía la tabla antes de llenarla
        for (Usuario u : listaUsuarios) {
            modelo.addRow(new Object[]{
                    u.getNombre(), u.getApellido(), u.getTelefono(), u.getCorreo(), u.getUsuario()
            });
            if (u.getId() == usuarioActivo.getId()) {
                usuarioActivo = u;   // por si el usuario actual se editó a sí mismo
            }
        }
        lblSesion.setText("Sesión iniciada como " + usuarioActivo.getDescripcion());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevo) {
            VentanaFactory.crearRegistro(this, null).mostrar();
        } else if (e.getSource() == btnActualizar) {
            actualizarUsuario();
        } else if (e.getSource() == btnEliminar) {
            eliminarUsuario();
        } else if (e.getSource() == btnCerrarSesion) {
            cerrarSesion();
        }
    }

    private Usuario obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista primero",
                    "Atención", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return listaUsuarios.get(fila);
    }

    private void actualizarUsuario() {
        Usuario elegido = obtenerSeleccionado();
        if (elegido != null) {
            VentanaFactory.crearRegistro(this, elegido).mostrar();
        }
    }

    private void eliminarUsuario() {
        Usuario elegido = obtenerSeleccionado();
        if (elegido == null) {
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar a " + elegido.getNombreCompleto() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.eliminar(elegido.getId());
        } catch (SQLException ex) {
            mostrarErrorBD(ex);
            return;
        }

        if (elegido.getId() == usuarioActivo.getId()) {
            JOptionPane.showMessageDialog(this, "Eliminó su propia cuenta, se cerrará la sesión");
            cerrarSesion();
        } else {
            cargarTabla();   // la tabla se actualiza automáticamente
        }
    }

    private void cerrarSesion() {
        VentanaFactory.crearLogin().mostrar();
        dispose();
    }

    private void mostrarErrorBD(SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error con la base de datos: " + ex.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
    }
}