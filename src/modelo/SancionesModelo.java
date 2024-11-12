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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
        String sql = "SELECT * FROM sanciones";
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
}
