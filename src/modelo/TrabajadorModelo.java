/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.BaseDatosController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

/**
 *
 * @author 34662
 */
public class TrabajadorModelo {

    private BaseDatosController bd_controller;
    
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public TrabajadorModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }
    
    public Trabajador crearTrabajador(int id_permiso, String nombre, String apellido, String dni, String fecha_nacimiento, String correo, String cuenta_banco, String seguridad_social, String localidad, String contraseña){
        //contraseña = "hola";
        try {
            // Obtener una instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Convertir la contraseña a un arreglo de bytes
            byte[] hashBytes = digest.digest(contraseña.getBytes());

            // Codificar el hash en Base64 para hacerlo legible
            String hashedPassword = Base64.getEncoder().encodeToString(hashBytes);

            System.out.println("Contraseña hasheada: " + hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Trabajador trabajador = new Trabajador(id_permiso, nombre, apellido, dni, fecha_nacimiento, correo, cuenta_banco, seguridad_social, localidad, contraseña);
        
        if (!comprobarDNI(dni)) {
            ingresarUsuarioBd(trabajador);
            return trabajador;
        }
        
        return null;
    } 
    
    /**
     * Funcion que nos comprueba que un dni ya está registrado
     * @param dni
     * @return 
     */
    public boolean comprobarDNI(String dni){
        try {
            //this.bd_controller.conectarBd();
            
            String buscar_email = "SELECT DNI FROM bd_biblioteca.trabajadores WHERE dni=?;";
            
            prepare = conexion.prepareStatement(buscar_email);
            
            prepare.setString(1, dni);
            
            resultado = prepare.executeQuery();
            
            //Si me devueve algo será un true
            return resultado.next();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    
    public void ingresarUsuarioBd(Trabajador trabajador){
        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.socios (ID_Permisos_FK, Nombre, Apellidos, DNI, Nacimiento, Correo, Cuenta_bancaria, SeguridadSocial, Localidad, Contraseña)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setString(1, Integer.toString(trabajador.getId_permiso()));
            prepare.setString(2, trabajador.getNombre());
            prepare.setString(3, trabajador.getApellido());
            prepare.setString(4, trabajador.getDni());
            prepare.setString(5, trabajador.getFecha_nacimiento());
            prepare.setString(6, trabajador.getCorreo());
            prepare.setString(7, trabajador.getCuenta_banco());
            prepare.setString(8, trabajador.getSeguridad_social());
            prepare.setString(9, trabajador.getLocalidad());
            prepare.setString(10, trabajador.getContraseña());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Trabajador " + trabajador.getNombre() + " agregado correctamente a la BD");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
