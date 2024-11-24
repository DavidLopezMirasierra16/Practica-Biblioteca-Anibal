package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatosController {

    private static final String URL = "jdbc:mysql://localhost:3306/bd_biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}