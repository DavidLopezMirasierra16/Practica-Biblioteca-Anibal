/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.BaseDatosController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class SancionesModelo {
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
}
