package modelo;

import controlador.BaseDatosController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import vista.ConsultarLibros;

public class LibrosModelo {

    // Método para consultar todos los libros y mostrar en la tabla
    public void consultarLibros(JTable table) {
        String sql = "SELECT ID_Libros, Titulo, ISBN, Genero, Year, Editorial, ID_AUTOR FROM libros";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Definir las columnas de la tabla
            String[] columnNames = {"ID", "Título", "ISBN", "Género", "Año", "Editorial", "Autor"};
            // Crear un modelo para la tabla
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            // Agregar las filas a la tabla
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ID_Libros"),
                    rs.getString("Titulo"),
                    rs.getString("ISBN"),
                    rs.getString("Genero"),
                    rs.getString("Year"),
                    rs.getString("Editorial"),
                    rs.getInt("ID_AUTOR")
                };
                model.addRow(row);
            }

            // Establecer el modelo en la tabla
            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para filtrar libros según el filtro y la búsqueda
    public void filtrarLibros(ConsultarLibros consultarLibros) {
        String filtro = (String) consultarLibros.getCbFiltro().getSelectedItem();
        String busqueda = consultarLibros.getTxtBusqueda().getText().trim();

        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna = "";
        switch (filtro) {
            case "Nombre":
                columna = "Titulo";
                break;
            case "Editorial":
                columna = "Editorial";
                break;
            case "Autor":
                columna = "ID_AUTOR";
                break;
        }

        String sql = "SELECT ID_Libros, Titulo, ISBN, Genero, Year, Editorial, ID_AUTOR FROM libros WHERE " + columna + " LIKE ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + busqueda + "%");  // Usamos % para buscar coincidencias parciales

            try (ResultSet rs = stmt.executeQuery()) {
                String[] columnNames = {"ID", "Título", "ISBN", "Género", "Año", "Editorial", "Autor"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                // Agregar las filas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("ID_Libros"),
                        rs.getString("Titulo"),
                        rs.getString("ISBN"),
                        rs.getString("Genero"),
                        rs.getString("Year"),
                        rs.getString("Editorial"),
                        rs.getInt("ID_AUTOR")
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                consultarLibros.getTablaLibros().setModel(model);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
