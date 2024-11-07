package modelo;

import controlador.BaseDatosController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import vista.ConsultarLibros;

public class LibrosModelo {

    // Método para consultar todos los libros y mostrar en la tabla
    public void consultarLibros(JTable table) {
        String sql = "SELECT ID_Libros, Titulo, Genero, Year, Editorial, ID_AUTOR FROM libros";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Crear un modelo para la tabla
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // Hacer que las celdas no sean editables

            // Agregar las filas a la tabla
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ID_Libros"),
                    rs.getString("Titulo"),
                    rs.getString("Genero"),
                    rs.getString("Year"),
                    rs.getString("Editorial"),
                    rs.getInt("ID_AUTOR")
                };
                model.addRow(row);
            }

            // Establecer el modelo en la tabla
            table.setModel(model);

            // No permitir redimensionar las columnas
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
            }

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

        String sql = "SELECT ID_Libros, Titulo, Genero, Year, Editorial, ID_AUTOR FROM libros WHERE " + columna + " LIKE ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + busqueda + "%");  // Usamos % para buscar coincidencias parciales

            try (ResultSet rs = stmt.executeQuery()) {
                String[] columnNames = {"ID", "Título", "Género", "Año", "Editorial", "Autor"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    // Hacer que las celdas no sean editables
                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;  // Ninguna celda es editable
                    }
                };

                // Agregar las filas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("ID_Libros"),
                        rs.getString("Titulo"),
                        rs.getString("Genero"),
                        rs.getString("Year"),
                        rs.getString("Editorial"),
                        rs.getInt("ID_AUTOR")
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                consultarLibros.getTablaLibros().setModel(model);

                // No permitir redimensionar las columnas
                for (int i = 0; i < consultarLibros.getTablaLibros().getColumnCount(); i++) {
                    consultarLibros.getTablaLibros().getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
