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
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarPrestamos;

/**
 *
 * @author pablo
 */
public class PrestamosModelo {
    
    private BaseDatosController bd_controller;
    private Connection conexion;
    private PreparedStatement prepare;
    private ResultSet resultado;

    public PrestamosModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }

    /**
     * Registra un préstamo en la base de datos.
     * @param ID_Libro_FK ID del libro
     * @param ID_Socio_FK ID del socio
     * @param Fecha_Prestamo Fecha del préstamo
     * @param Fecha_Devolucion Fecha de devolución prevista
     * @return el préstamo registrado o null si el libro ya está prestado o el socio tiene 3 préstamos
     */
    public Prestamos registrarPrestamo(int ID_Libro_FK, int ID_Socio_FK, Date Fecha_Prestamo, Date Fecha_Devolucion) {
        if (!comprobarDisponibilidadLibro(ID_Libro_FK) && comprobarLimitePrestamos(ID_Socio_FK)) {
            Prestamos prestamo = new Prestamos(0, ID_Libro_FK, ID_Socio_FK, Fecha_Prestamo, Fecha_Devolucion); // ID_Prestamo = 0 ya que será autogenerado
            ingresarPrestamoEnBd(prestamo);
            return prestamo;
        }
        
        return null; // Retorna null si el libro ya está prestado o el socio tiene 3 préstamos activos
    }

    /**
     * Verifica si un libro está disponible para préstamo.
     * @param ID_Libro_FK ID del libro a verificar
     * @return true si el libro está disponible, false si ya está prestado
     */
    public boolean comprobarDisponibilidadLibro(int ID_Libro_FK) {
        try {
            String query = "SELECT * FROM bd_biblioteca.prestamos WHERE ID_Libro_FK = ? AND (Fecha_Devolucion IS NULL OR Fecha_Devolucion > CURRENT_DATE);";
            prepare = conexion.prepareStatement(query);
            prepare.setInt(1, ID_Libro_FK);
            
            resultado = prepare.executeQuery();
            
            return !resultado.next(); // Retorna true si no encuentra un préstamo activo para el libro
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si un socio tiene menos de 3 préstamos activos.
     * @param ID_Socio_FK ID del socio a verificar
     * @return true si el socio tiene menos de 3 préstamos activos, false si ya tiene 3
     */
    public boolean comprobarLimitePrestamos(int ID_Socio_FK) {
        try {
            String query = "SELECT COUNT(*) AS total_prestamos FROM bd_biblioteca.prestamos WHERE ID_Socio_FK = ? AND (Fecha_Devolucion IS NULL OR Fecha_Devolucion > CURRENT_DATE);";
            prepare = conexion.prepareStatement(query);
            prepare.setInt(1, ID_Socio_FK);
            
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                int totalPrestamos = resultado.getInt("total_prestamos");
                return totalPrestamos < 3; // Retorna true si el socio tiene menos de 3 préstamos
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return false; // Retorna false en caso de error o si ya tiene 3 préstamos
    }

    /**
     * Inserta un préstamo en la base de datos.
     * @param prestamo el objeto Prestamos que queremos registrar en la base de datos
     */
    public void ingresarPrestamoEnBd(Prestamos prestamo) {
        try {
            String sql = "INSERT INTO bd_biblioteca.prestamos (ID_Libro_FK, ID_Socio_FK, Fecha_Prestamo, Fecha_Devolucion)" +
                         "VALUES (?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sql);
            prepare.setInt(1, prestamo.getID_Libro_FK());
            prepare.setInt(2, prestamo.getID_Socio_FK());
            prepare.setDate(3, new java.sql.Date(prestamo.getFecha_Prestamo().getTime()));
            prepare.setDate(4, new java.sql.Date(prestamo.getFecha_Devolucion().getTime()));
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Préstamo registrado correctamente para el libro con ID: " + prestamo.getID_Libro_FK());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
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
