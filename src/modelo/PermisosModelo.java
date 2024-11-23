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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author 34662
 */
public class PermisosModelo {
    
    private BaseDatosController bd_controller;
    
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public PermisosModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }
    
    /**
     * Funcion que nos devuelve el id de un permiso que nosotros le pasamos.
     * @param descripcion
     * @return 
     */
    public int obtenerIdPermiso(String descripcion){
        int id = 0;
        
        try {
            
            String comprobar_id_biblioteca = "SELECT ID_Permisos from bd_biblioteca.permisos WHERE Permiso = ?;";
            
            prepare=conexion.prepareStatement(comprobar_id_biblioteca);
            
            prepare.setString(1, descripcion);
            
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                id=resultado.getInt("ID_Permisos");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
        
    }
    
    /**
     * Fucion que nos genera la fecha del dia de hoy
     * @return 
     */
    public String fecha(){
        LocalDateTime dia = LocalDateTime.now();
        
        DateTimeFormatter formatear = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String formateada = dia.format(formatear);
        return formateada;
    }
    
}
