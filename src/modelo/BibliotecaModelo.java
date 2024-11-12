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

/**
 *
 * @author 34662
 */
public class BibliotecaModelo {
    
    private BaseDatosController bd_controller;
    
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public BibliotecaModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }
    
    /**
     * Funcion que nos devuelve el id de ua biblioteca que nosotros le pasamos.
     * @param nombre_biblioteca
     * @return 
     */
    public int idBiblioteca(String nombre_biblioteca){
        int id = 0; 
        
        try {
            
            String comprobar_id_biblioteca = "SELECT ID_Biblioteca from bd_biblioteca.biblioteca WHERE Nombre_Biblioteca = ?;";
            
            prepare=conexion.prepareStatement(comprobar_id_biblioteca);
            
            prepare.setString(1, nombre_biblioteca);
            
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                id=resultado.getInt("ID_Biblioteca");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
        
    }
    
}
