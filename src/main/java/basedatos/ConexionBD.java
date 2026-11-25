package basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Usamos el Patrón Singleton para que solo exista UNA conexión a la base de datos en todo el programa
public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_usuarios";
    private static final String USUARIO_BD = "root";
    private static final String CLAVE_BD = "Tu_clave_de_mysql";

    // Constructor privado donde nadie puede hacer "new ConexionBD()"
    private ConexionBD() {
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO_BD, CLAVE_BD);
        }
        return conexion;
    }
}