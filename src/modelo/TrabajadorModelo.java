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

            contraseña = hashedPassword;
            
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

            String sentencia_slq = "INSERT INTO bd_biblioteca.trabajadores (ID_Permisos_FK, Nombre, Apellidos, DNI, Nacimiento, Correo, Cuenta_bancaria, SeguridadSocial, Localidad, Contraseña)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setInt(1, trabajador.getId_permiso());
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
    
    /**
     * Nos ingresa la contraseña en la bd de contraseñas
     * @param trabajador 
     */
    public void ingresarContraseña(Trabajador trabajador){
        try {
            

            String sentencia_slq = "INSERT INTO bd_biblioteca.mbappe (Contrasenia, ID_Trabajador_FK)" + "VALUES (?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setString(1, trabajador.getContraseña());
            prepare.setInt(2, trabajador.getId());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Trabajador " + trabajador.getNombre() + " agregado correctamente a la BD");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int comporbarRolFuncion(String nombre, String contraseña){
        
        int id_trabajador = 0;
        
        try {
            //this.bd_controller.conectarBd();
            
            String comprobar_rol = "{? = call obtener_id_trabajador(?, ?)}";
            
            consultas_funciones = conexion.prepareCall(comprobar_rol);
            
            consultas_funciones.registerOutParameter(1, Integer.BYTES);
            consultas_funciones.setString(2, nombre);
            
            try {
                // Obtener una instancia del algoritmo SHA-256
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                // Convertir la contraseña a un arreglo de bytes
                byte[] hashBytes = digest.digest(contraseña.getBytes());

                // Codificar el hash en Base64 para hacerlo legible
                String hashedPassword = Base64.getEncoder().encodeToString(hashBytes);

                contraseña = hashedPassword;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            
            consultas_funciones.setString(3, contraseña);

            consultas_funciones.execute();
            
            id_trabajador = consultas_funciones.getInt(1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id_trabajador;
        
    }
    
    /**
     * Funcion que nos recoje el id del rol que se seleccion
     */ 
    public int añadirPermiso(String rol){
        int id = 0;
        
        try {
            
            String comprobar_id_biblioteca = "SELECT ID_Pernisos from bd_biblioteca.permisos WHERE Permiso = ?;";
            
            prepare=conexion.prepareStatement(comprobar_id_biblioteca);
            
            prepare.setString(1, rol);
            
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                id=resultado.getInt("ID_Pernisos");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
        
    }
    
    public void cambiarContraseña(String usuario, String nueva_contraseña) throws NoSuchAlgorithmException {
        try{

            // Obtener una instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Convertir la contraseña a un arreglo de bytes
            byte[] hashBytes = digest.digest(nueva_contraseña.getBytes());

            // Codificar el hash en Base64 para hacerlo legible
            String hashedPassword = Base64.getEncoder().encodeToString(hashBytes);

            nueva_contraseña = hashedPassword;
            
            System.out.println("Contraseña hasheada: " + hashedPassword);
            
            String sql = "UPDATE trabajadores SET Contraseña = ? WHERE Nombre = ?";
            
            prepare = conexion.prepareStatement(sql);
            
            prepare.setString(1, nueva_contraseña);
            prepare.setString(2, usuario);

            int filasActualizadas = prepare.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Contraseña actualizada exitosamente.");
            } else {
                System.out.println("No se encontró el usuario o no se pudo actualizar la contraseña.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la contraseña: " + e.getMessage());
        }
    }
    
}
