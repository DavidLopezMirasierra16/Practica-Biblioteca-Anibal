/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.BaseDatosController;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarSanciones;

/**
 *
 * @author pablo
 */
public class SancionesModelo {
    
    private BaseDatosController bd_controller;
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;
    
    public SancionesModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }
    
    public void consultarSanciones(JTable table) {
        String sql = "SELECT sanciones.ID_Sancion, CONCAT(socios.Nombre, ' ', socios.Apellidos) AS Nombre_Completo_Socio, sanciones.ID_Prestamo_FK, sanciones.Tipo_Sancion FROM sanciones JOIN socios ON sanciones.ID_Socio_FK = socios.DNI_Socio;";
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
                    rs.getInt("ID_Sancion"),
                    rs.getString("ID_Socio_FK"),
                    rs.getString("ID_Prestamo_FK"),
                    rs.getString("Tipo_Sancion")
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
    
        public void ingresarSancionBD(Sanciones sancion){

        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.socios (ID_Socio_FK, ID_Prestamo_FK, Tipo_Sancion)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setInt(1, sancion.getID_Socio_FK());
            prepare.setInt(2, sancion.getID_Prestamo_FK());
            prepare.setString(3, sancion.getTipo_Sancion());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Sanción agregada correctamente a la BD");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        
            // Método para resolver (eliminar) la sanción
    public void resolverSancion(ConsultarSanciones consultar) {
        // Obtener la fila seleccionada en la tabla
        int row = consultar.tablaSanciones.getSelectedRow();

        if (row != -1) { // Si hay una fila seleccionada
            // Obtener el ID de la sanción (asumimos que está en la primera columna)
            int idSancion = (int) consultar.tablaSanciones.getValueAt(row, 0);

            // Confirmar la acción de eliminación
            int confirm = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas eliminar esta sanción?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminar la sanción de la base de datos
                eliminarSancionDeBD(idSancion);

                // Eliminar la fila de la tabla
                DefaultTableModel model = (DefaultTableModel) consultar.tablaSanciones.getModel();
                model.removeRow(row);

                // Mostrar un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Sanción eliminada correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una sanción.");
        }
    }

    // Método para eliminar la sanción de la base de datos
    private void eliminarSancionDeBD(int idSancion) {
        String sql = "DELETE FROM sanciones WHERE ID_Sancion = ?";

        try (Connection conexion = bd_controller.conectar(); 
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Establecer el valor del ID de la sanción
            stmt.setInt(1, idSancion);

            // Ejecutar la consulta de eliminación
            int filasAfectadas = stmt.executeUpdate();

            // Si no se eliminó ninguna fila, mostrar un mensaje de error
            if (filasAfectadas == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la sanción. Por favor, intente nuevamente.");
            }

        } catch (SQLException e) {
            // Si ocurre un error, mostrar el mensaje de error
            JOptionPane.showMessageDialog(null, "Error al eliminar la sanción: " + e.getMessage());
        }
    }
}