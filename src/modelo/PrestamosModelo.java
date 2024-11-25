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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarPrestamos;

/**
 *
 * @author pablo
 */
public class PrestamosModelo {
    private TrabajadorModelo trabajador;
    private BaseDatosController bd_controller;
    private Connection conexion;
    private PreparedStatement prepare;
    private ResultSet resultado;

    public PrestamosModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.trabajador = new TrabajadorModelo();
        this.conexion = this.bd_controller.conectar();
    }

    //---------------------------INSERTAR---------------------------
    
    /**
     * Registra un préstamo en la base de datos.
     * @param ID_Libro_FK ID del libro
     * @param ID_Socio_FK ID del socio
     * @param Fecha_Prestamo Fecha del préstamo
     * @param Fecha_Devolucion Fecha de devolución prevista
     * @return el préstamo registrado o null si el libro ya está prestado o el socio tiene 3 préstamos
     */
    public Prestamos registrarPrestamo(int ID_Libro_FK, String ID_Socio_FK, int Biblioteca, Date Fecha_Prestamo) {
        Prestamos prestamo = new Prestamos(ID_Libro_FK, ID_Socio_FK, Biblioteca, Fecha_Prestamo);
        ingresarPrestamoEnBd(prestamo);
        return prestamo;
    }

    /**
     * Obtiene el ID de la biblioteca según su nombre.
     * @param Nombre_Biblioteca Nombre de la biblioteca
     * @return el ID de la biblioteca o null si no se encuentra
     */
    private String obtenerIDBiblioteca(String Nombre_Biblioteca) {
        try {
            String obtener_ID = "SELECT ID_Biblioteca FROM biblioteca WHERE Nombre_Biblioteca = ?";
            prepare = conexion.prepareStatement(obtener_ID);
            prepare.setString(1, Nombre_Biblioteca);
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                return resultado.getString("ID_Biblioteca");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Verifica si un libro está disponible para préstamo.
     * @param ID_Libro_FK ID del libro a verificar
     * @return true si el libro está disponible, false si ya está prestado
     */
    public boolean comprobarDisponibilidadLibro(int ID_Libro_FK) {
        try {
            String comprueba_Libro = "SELECT * FROM prestamos WHERE ID_Libro_FK = ? AND (Fecha_Devolucion IS NULL OR Fecha_Devolucion > CURRENT_DATE);";
            prepare = conexion.prepareStatement(comprueba_Libro);
            prepare.setInt(1, ID_Libro_FK);
            resultado = prepare.executeQuery();
            
            return !resultado.next();
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
    public boolean comprobarLimitePrestamos(String ID_Socio_FK) {
        try {
            String comprueba_Prestamos = "SELECT COUNT(*) AS total_prestamos FROM prestamos WHERE ID_Socio_FK = ? AND (Fecha_Devolucion IS NULL OR Fecha_Devolucion > CURRENT_DATE);";
            prepare = conexion.prepareStatement(comprueba_Prestamos);
            prepare.setString(1, ID_Socio_FK);
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                int totalPrestamos = resultado.getInt("total_prestamos");
                return totalPrestamos < 3;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Función que recoge la fecha actual
     * @return 
     */
    public String fecha(){
        LocalDateTime dia = LocalDateTime.now();
        
        DateTimeFormatter formatear = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String formateada = dia.format(formatear);
        return formateada;
    }

    /**
     * Inserta un préstamo en la base de datos.
     * @param prestamo el objeto Prestamos que queremos registrar en la base de datos
     */

    private void ingresarPrestamoEnBd(Prestamos prestamo) {
        try {
            String verificarExistencias = "SELECT Cantidad FROM ubicacion WHERE ID_Libro = ? AND ID_Biblioteca = ?";
            prepare = conexion.prepareStatement(verificarExistencias);
            prepare.setInt(1, prestamo.getID_Libro_FK());
            prepare.setInt(2, prestamo.getID_Biblioteca_FK());
            ResultSet resultado = prepare.executeQuery();
            if (resultado.next()) {
                int existencias = resultado.getInt("Cantidad");

                if (existencias > 0) {
                    String ingresar_Prestamo = "{CALL registrarPrestamo(?, ?, ?, ?)}";

                    prepare = conexion.prepareStatement(ingresar_Prestamo);
                    prepare.setInt(1, prestamo.getID_Libro_FK());
                    prepare.setString(2, prestamo.getID_Socio_FK());
                    prepare.setInt(3, prestamo.getID_Biblioteca_FK());
                    prepare.setTimestamp(4, new java.sql.Timestamp(prestamo.getFecha_Prestamo().getTime()));

                    prepare.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay existencias suficientes del libro en esta ubicación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El libro no está registrado en esta ubicación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            if (ex.getMessage().contains("El socio no está pagado")) {
                JOptionPane.showMessageDialog(null, "El socio no está habilitado para realizar préstamos dado que no ha pagado.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al procesar el préstamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }



    //---------------------------CONSULTAS---------------------------

    /**
     * Método para consultar todos los libros y mostrar en la tabla
     * @param table 
     */
    public void consultarPrestamos(JTable table) {
        String consulta_Prestamos = "SELECT * FROM prestamos";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement stmt = conection.prepareStatement(consulta_Prestamos);
             ResultSet result = stmt.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (result.next()) {
                Object[] row = {
                    result.getInt("ID_Prestamo"),
                    result.getString("ID_Libro_FK"),
                    result.getString("ID_Socio_FK"),
                    result.getString("Fecha_Prestamo"),
                    result.getString("Fecha_Devolucion")
                };
                model.addRow(row);
            }

            table.setModel(model);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para filtrar libros según el filtro y la búsqueda
     * @param consultarPrestamos 
     */
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

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(sql)) {

            parametro.setString(1, "%" + busqueda + "%");

            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) consultarPrestamos.getTablaPrestamos().getModel();
                model.setRowCount(0);

                while (result.next()) {
                    Object[] row = {
                        result.getInt("ID_Prestamo"),
                        result.getString("ID_Libro_FK"),
                        result.getString("ID_Socio_FK"),
                        result.getString("Fecha_Prestamo"),
                        result.getString("Fecha_Devolucion")
                    };
                    model.addRow(row);
                }

                consultarPrestamos.getTablaPrestamos().setModel(model);
                for (int i = 0; i < consultarPrestamos.getTablaPrestamos().getColumnCount(); i++) {
                    consultarPrestamos.getTablaPrestamos().getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Funcion que empleada para devolver un libro
     * @param tabla 
     */
    public void devolver(JTable tabla) {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

            int idPrestamo = (int) modelo.getValueAt(filaSeleccionada, 0); // Asume que el ID está en la primera columna

            String devuelve_Prestamo = "DELETE FROM prestamos WHERE ID_Prestamo = ?";
            BaseDatosController baseDatosController = new BaseDatosController();

            try (Connection conection = baseDatosController.conectar();
                 PreparedStatement parametro = conection.prepareStatement(devuelve_Prestamo)) {

                parametro.setInt(1, idPrestamo);

                int filasAfectadas = parametro.executeUpdate();

                if (filasAfectadas > 0) {
                    modelo.removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(null, 
                        "Préstamo devuelto exitosamente.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Error: No se pudo devolver el préstamo.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Ocurrió un error al procesar la devolución: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "Por favor, seleccione una fila para devolver un préstamo.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
}