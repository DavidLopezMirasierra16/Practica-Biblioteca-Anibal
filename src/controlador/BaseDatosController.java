package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Socio;

public class BaseDatosController {

    private static final String URL = "jdbc:mysql://localhost:3306/bd_biblio"; // Cambia por tu URL
    private static final String USER = "root"; // Cambia por tu usuario de BD
    private static final String PASSWORD = "Admin1234"; // Cambia por tu contraseña

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void ingresarSocioBaseDatos(Socio socio) throws SQLException {
        String sql = "INSERT INTO socios (nombre, apellidos, direccion, telefono, correo, fecha_alta) VALUES (?, ?, ?, ?, ?, ?)";

        /*try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getApellidos());
            stmt.setString(3, socio.getDirección());
            stmt.setString(4, socio.getTelefono());
            stmt.setString(5, socio.getCorreo());
            stmt.setString(6, socio.getFecha_alta());
            stmt.executeUpdate();
        }*/
    }
}
