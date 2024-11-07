package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatosController {

    private static final String URL = "jdbc:mysql://localhost:3306/bd_biblioteca"; // Cambia por tu URL
    private static final String USER = "root"; // Cambia por tu usuario de BD
    private static final String PASSWORD = "Admin1234"; // Cambia por tu contraseña

    // Método para obtener la conexión
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}