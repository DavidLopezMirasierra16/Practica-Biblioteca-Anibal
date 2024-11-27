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
    
    /**
     * Funcion que nos muestra en una tabla todas las sanciones
     * @param table 
     */
    public void consultarSanciones(JTable table) {
        String consultaSanciones = "SELECT * FROM Sanciones;";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(consultaSanciones);
             ResultSet result = parametro.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (result.next()) {
                Object[] row = {
                    result.getInt("ID_Sancion"),
                    result.getString("ID_Socio_FK"),
                    result.getString("ID_Prestamo_FK"),
                    result.getString("Tipo_Sancion")
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
    
    public void filtrarSanciones(ConsultarSanciones consultarSanciones, JTable table) {
        String filtro = (String) consultarSanciones.getCbFiltro().getSelectedItem();
        String busqueda = consultarSanciones.getTxtBusqueda().getText().trim();
        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Debe ingresar un valor de búsqueda válido y seleccionar un filtro");
            return;
        }
        String columna = "";
        switch (filtro) {
            case "Socio":
                columna = "ID_Socio_FK";
                break;
            case "Tipo de sanción":
                columna = "Tipo_Sancion";
                break;
        }
        String consultaSanciones = "SELECT * FROM Sanciones WHERE " + columna + " LIKE ?";
        try (Connection conexion = new BaseDatosController().conectar();
             PreparedStatement parametro = conexion.prepareStatement(consultaSanciones)) {
            parametro.setString(1, "%" + busqueda + "%");
            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                while (result.next()) {
                    model.addRow(new Object[]{
                            result.getInt("ID_Sancion"),
                            result.getString("ID_Socio_FK"),
                            result.getString("ID_Prestamo_FK"),
                            result.getString("Tipo_Sancion")
                    });
                }
                table.setModel(model);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setResizable(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    /**
     * Funcion que nos ingresa la sancion en BD
     * @param sancion 
     */
    public void ingresarSancionBD(Sanciones sancion){

        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.sanciones (ID_Socio_FK, ID_Prestamo_FK, Tipo_Sancion)" + "VALUES (?, ?, ?);";
            prepare = conexion.prepareStatement(sentencia_slq);          
            prepare.setString(1, sancion.getID_Socio_FK());
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
        
    /**
     * Método para resolver (eliminar) la sanción
     * @param consultar 
     */
    public void resolverSancion(ConsultarSanciones consultar) {
        int row = consultar.tablaSanciones.getSelectedRow();
        if (row != -1) {
            int idSancion = (int) consultar.tablaSanciones.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(null,"¿Estás seguro de que deseas resolver esta sanción?","Confirmar",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                eliminarSancionDeBD(idSancion);
                DefaultTableModel model = (DefaultTableModel) consultar.tablaSanciones.getModel();
                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "Sanción resuelta correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una sanción.");
        }
    }

    /**
     * Método para eliminar la sanción de la base de datos
     * @param idSancion 
     */
    private void eliminarSancionDeBD(int idSancion) {
        String resuelve_Sancion = "DELETE FROM sanciones WHERE ID_Sancion = ?";

        try (Connection conexion = bd_controller.conectar(); 
             PreparedStatement parametro = conexion.prepareStatement(resuelve_Sancion)) {
            parametro.setInt(1, idSancion);
            int filasAfectadas = parametro.executeUpdate();
            if (filasAfectadas == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la sanción. Por favor, intente nuevamente");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la sanción: " + e.getMessage());
        }
    }
}