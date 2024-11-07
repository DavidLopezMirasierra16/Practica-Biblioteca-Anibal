package modelo;

import controlador.BaseDatosController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vista.ConsultarLibros;

public class LibrosModelo {

    public LibrosModelo() {}

    public void consultarLibros(JTable table) {
        String sql = "SELECT ID_Libros, Titulo, ISBN, Genero, Year, Editorial, ID_AUTOR FROM libros";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            List<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                data.add(row);
            }

            String[] columnNamesArray = columnNames.toArray(new String[0]);
            Object[][] dataArray = data.toArray(new Object[0][]);

            DefaultTableModel model = new DefaultTableModel(dataArray, columnNamesArray);
            table.setModel(model);
            table.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
public void filtrarLibros(ConsultarLibros consultarLibros) {  // Cambio: Se añade ConsultarLibros como parámetro
        String filtro = (String) consultarLibros.getCbFiltro().getSelectedItem();
        String busqueda = consultarLibros.getTxtBusqueda().getText().trim();

        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna;
        switch (filtro) {
            case "ISBN":
                columna = "ISBN";
                break;
            case "Nombre":
                columna = "Titulo";
                break;
            case "Editorial":
                columna = "Editorial";
                break;
            case "Autor":
                columna = "ID_AUTOR";
                break;
            default:
                System.out.println("Filtro no válido.");
                return;
        }

        String sql = "SELECT ID_Libros, Titulo, ISBN, Genero, Year, Editorial, ID_AUTOR FROM libros WHERE " + columna + " = ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, busqueda);

            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                List<String> columnNames = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                List<Object[]> data = new ArrayList<>();
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        row[i] = rs.getObject(i + 1);
                    }
                    data.add(row);
                }

                String[] columnNamesArray = columnNames.toArray(new String[0]);
                Object[][] dataArray = data.toArray(new Object[0][]);

                DefaultTableModel model = new DefaultTableModel(dataArray, columnNamesArray);
                consultarLibros.getTablaLibros().setModel(model);
                consultarLibros.getTablaLibros().repaint();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
