package dao;

import basedatos.ConexionBD;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// DAO es la única clase que conecta con la tabla "usuarios" de MySQL
public class UsuarioDAO {

    private Connection obtenerConexion() throws SQLException {
        return ConexionBD.getInstancia().getConexion();
    }

    public void registrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, nombre, apellido, telefono, correo, clave) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getClave());
            ps.executeUpdate();
        }
    }

    // Devuelve el usuario si los datos son correctos, o null si no lo son
    public Usuario iniciarSesion(String usuario, String claveEncriptada) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        try (PreparedStatement ps = obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, claveEncriptada);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearUsuario(rs);
                }
            }
        }
        return null;
    }

    public ArrayList<Usuario> listar() throws SQLException {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nombre";
        try (PreparedStatement ps = obtenerConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearUsuario(rs));
            }
        }
        return lista;
    }

    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET usuario = ?, nombre = ?, apellido = ?, "
                + "telefono = ?, correo = ?, clave = ? WHERE id = ?";
        try (PreparedStatement ps = obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getClave());
            ps.setInt(7, u.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Revisa si el nombre de usuario ya existe (ignorando al usuario con ese id)
    public boolean existeUsuario(String usuario, int idIgnorar) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE usuario = ? AND id <> ?";
        try (PreparedStatement ps = obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setInt(2, idIgnorar);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("clave"));
    }
}