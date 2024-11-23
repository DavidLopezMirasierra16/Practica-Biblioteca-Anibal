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
public class AutorModelo {

    private BaseDatosController bd_controller;
    
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public AutorModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }
 
    /**
     * Fnucion que nos crea un autor.
     * @param nombre
     * @param fecha_nacimiento
     * @param email
     * @return 
     */
    public Autor crearAutor(String nombre, String fecha_nacimiento, String email){
        Autor autor = new Autor(nombre, fecha_nacimiento, email);
        
        if (!comprobarNombre(nombre)) {
            ingresarUsuarioBd(autor);
            return autor;
        }
        
        return null;
    }
    
    /**
     * Funcion que comprueba que un autor no está registrado
     * @param nombre
     * @return 
     */
    public boolean comprobarNombre(String nombre){
        try {
            //this.bd_controller.conectarBd();
            
            String buscar_nombre = "SELECT nombre_autor FROM bd_biblioteca.autores WHERE nombre_autor=?;";
            
            prepare = conexion.prepareStatement(buscar_nombre);
            
            prepare.setString(1, nombre);
            
            resultado = prepare.executeQuery();
            
            //Si me devueve algo será un true
            return resultado.next();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Funcion que nos ingresa en la BD lo datos del autor
     * @param autor 
     */
    public void ingresarUsuarioBd(Autor autor){
        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.autores (nombre_autor, fecha_nacimiento, email)" + "VALUES (?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setString(1, autor.getNombre());
            prepare.setString(2, autor.getFecha_nacimiento());
            prepare.setString(3, autor.getEmail());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Usuario " + autor.getNombre() + " agregado correctamente a la BD");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
