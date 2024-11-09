
package controlador;

import java.sql.SQLException;

public class main {
    
    public static void main(String[] args) throws SQLException {
        new BaseDatosController().conectar();
        new LoginController();
    }
}
