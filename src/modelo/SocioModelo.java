package modelo;

import controlador.BaseDatosController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarSocios;
import vista.RecuperarContraseña;

/**
 * Modelo para realizar operaciones de base de datos sobre los socios.
 */
public class SocioModelo {

    public void consultarSocios(JTable table) {
        String sql = "SELECT ID_Socios, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria FROM socios";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Crear un modelo para la tabla y limpiar las filas anteriores
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Limpiar las filas actuales

            // Agregar las filas a la tabla
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ID_Socios"),
                    rs.getString("Nombre"),
                    rs.getString("Apellidos"),
                    rs.getString("Direccion"),
                    rs.getString("Telefono"),
                    rs.getString("Correo_Socio"),
                    rs.getString("Fecha_Alta"),
                    rs.getString("Cuenta_Bancaria"),
                };
                model.addRow(row);
            }

            // Establecer el modelo en la tabla
            table.setModel(model);

            // No permitir redimensionar las columnas
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para filtrar socios según el filtro y la búsqueda
    public void filtrarSocios(ConsultarSocios consultarSocios, JTable table) {
        String filtro = (String) consultarSocios.getCbFiltro().getSelectedItem();
        String busqueda = consultarSocios.getTxtBusqueda().getText().trim();

        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna = "";
        switch (filtro) {
            case "Nombre":
                columna = "Nombre";
                break;
            case "Apellidos":
                columna = "Apellidos";
                break;
            case "Correo":
                columna = "Correo_Socio";
                break;
            case "ID":
                columna = "ID_Socios";
                break;
        }

        String sql = "SELECT ID_Socios, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria FROM socios WHERE " + columna + " LIKE ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + busqueda + "%"); // Usamos % para buscar coincidencias parciales

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Limpiar las filas actuales antes de filtrar

                // Agregar las filas filtradas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("ID_Socios"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Direccion"),
                        rs.getString("Telefono"),
                        rs.getString("Correo_Socio"),
                        rs.getString("Fecha_Alta"),
                        rs.getString("Cuenta_Bancaria"),
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                consultarSocios.getTablaSocios().setModel(model);

                // No permitir redimensionar las columnas
                for (int i = 0; i < consultarSocios.getTablaSocios().getColumnCount(); i++) {
                    consultarSocios.getTablaSocios().getColumnModel().getColumn(i).setResizable(false);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cambiarContraseña(RecuperarContraseña recuperarContraseña) {
        String usuario = recuperarContraseña.getTxt_usuario().getText().trim();
        String nuevaContraseña = recuperarContraseña.getTxt_contraseña().getText().trim();
        
        if (usuario.isEmpty() || nuevaContraseña.isEmpty()) {
            System.out.println("Usuario o contraseña nueva no deben estar vacíos.");
            return;
        }

        String sql = "UPDATE mbappe SET Contrasenia = ? WHERE ID_Trabajador_FK = ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevaContraseña);
            stmt.setString(2, usuario);

            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Contraseña actualizada exitosamente.");
            } else {
                System.out.println("No se encontró el usuario o no se pudo actualizar la contraseña.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la contraseña: " + e.getMessage());
        }
    }
}
