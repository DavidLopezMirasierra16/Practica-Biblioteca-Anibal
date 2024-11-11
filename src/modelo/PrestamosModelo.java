/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import controlador.BaseDatosController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarPrestamos;

/**
 *
 * @author pablo
 */
public class PrestamosModelo {
    // Método para consultar todos los libros y mostrar en la tabla
    public void consultarPrestamos(JTable table) {
        String sql = "SELECT * FROM prestamos";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Crear un modelo para la tabla y limpiar las filas actuales
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Limpiar las filas actuales antes de agregar nuevas

            // Agregar las filas a la tabla
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ID_Prestamo"),
                    rs.getString("ID_Libro_FK"),
                    rs.getString("ID_Socio_FK"),
                    rs.getString("Fecha_Prestamo"),
                    rs.getString("Fecha_Limite")
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
    public void filtrarLibros(ConsultarPrestamos consultarPrestamos) {
        String filtro = (String) consultarPrestamos.getCbFiltro().getSelectedItem();
        String busqueda = consultarPrestamos.getTxtBusqueda().getText().trim();

        if ("Seleccione una opción".equals(filtro) || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna = "";
        switch (filtro) {
            case "ID_Prestamo":
                columna = "ID_Prestamo";
                break;
            case "ID_Libro":
                columna = "ID_Libro";
                break;
            case "ID_Socio":
                columna = "ID_Socio";
                break;
        }

        String sql = "SELECT * FROM prestamos WHERE " + columna + " LIKE ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + busqueda + "%");  // Usamos % para buscar coincidencias parciales

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) consultarPrestamos.getTablaPrestamos().getModel();
                model.setRowCount(0); // Limpiar las filas actuales antes de filtrar

                // Agregar las filas filtradas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("ID_Prestamo"),
                        rs.getString("ID_Libro_FK"),
                        rs.getString("ID_Socio_FK"),
                        rs.getString("Fecha_Prestamo"),
                        rs.getString("Fecha_Limite")
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                consultarPrestamos.getTablaPrestamos().setModel(model);

                // No permitir redimensionar las columnas
                for (int i = 0; i < consultarPrestamos.getTablaPrestamos().getColumnCount(); i++) {
                    consultarPrestamos.getTablaPrestamos().getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void devolver(JTable tabla) {
        // Verifica si hay una fila seleccionada
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) { // -1 significa que no hay selección
            // Obtiene el modelo de la tabla
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

            // Obtener el ID del préstamo de la fila seleccionada
            int idPrestamo = (int) modelo.getValueAt(filaSeleccionada, 0);  // Asume que el ID está en la primera columna

            // Actualizar la base de datos para marcar el préstamo como devuelto
            String sql = "DELETE FROM prestamos WHERE ID_Prestamo = ?";
            BaseDatosController baseDatosController = new BaseDatosController();

            try (Connection conn = baseDatosController.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Configurar el ID del préstamo en la consulta
                stmt.setInt(1, idPrestamo);

                // Ejecutar la eliminación en la base de datos
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    // Si la eliminación fue exitosa, eliminar la fila del modelo de la tabla
                    modelo.removeRow(filaSeleccionada);
                    System.out.println("Préstamo devuelto exitosamente.");
                } else {
                    System.out.println("Error: No se pudo devolver el préstamo.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay ninguna fila seleccionada.");
        }
    }
}
