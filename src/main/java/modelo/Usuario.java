package modelo;

// Herencia donde Usuario "es una" Persona, y además tiene id, usuario y clave
public class Usuario extends Persona {

    private int id;
    private String usuario;
    private String clave;

    public Usuario(int id, String usuario, String nombre, String apellido,
                   String telefono, String correo, String clave) {
        super(nombre, apellido, telefono, correo);
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String getDescripcion() {
        return getNombreCompleto() + " (@" + usuario + ")";
    }
}