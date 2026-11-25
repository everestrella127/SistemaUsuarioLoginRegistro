package modelo;

// Clase abstracta donde representa a cualquier persona, pero no se puede crear directamente
public abstract class Persona {

    // Aqui usamos el Encapsulamiento para crear nuestros atributos privados
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    public Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // Un Método abstracto: cada clase hija lo define a su manera (polimorfismo)
    public abstract String getDescripcion();
}