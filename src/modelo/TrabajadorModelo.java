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
import vista.RecuperarContrasena;

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
    private int idPermiso;
    private static int idBiblioteca;

    public TrabajadorModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }
    
    public Trabajador crearTrabajador(int id_permiso, String nombre, String apellido, String dni, String fecha_nacimiento, String correo, String cuenta_banco, String seguridad_social, String contraseña, String biblioteca){
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

        Trabajador trabajador = new Trabajador(id_permiso, nombre, apellido, dni, fecha_nacimiento, correo, cuenta_banco, seguridad_social, biblioteca);
        
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

            String sentencia_slq = "INSERT INTO bd_biblioteca.trabajadores (ID_Permisos_FK, Nombre, Apellidos, DNI, Nacimiento, Correo, Cuenta_bancaria, SeguridadSocial, ID_Biblioteca_FK)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setInt(1, trabajador.getId_permiso());
            prepare.setString(2, trabajador.getNombre());
            prepare.setString(3, trabajador.getApellido());
            prepare.setString(4, trabajador.getDni());
            prepare.setString(5, trabajador.getFecha_nacimiento());
            prepare.setString(6, trabajador.getCorreo());
            prepare.setString(7, trabajador.getCuenta_banco());
            prepare.setString(8, trabajador.getSeguridad_social());
            prepare.setString(9, trabajador.getBiblioteca());
            
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
            //this.bd_controller.conectarBd();

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
    
        public int comprobarRolFuncion(int usuario, String contraseña) {
        int id_trabajador = 0;

        try {
            // Hashear la contraseña antes de enviarla al procedimiento almacenado
            String contrasenaHasheada = hashearContraseña(contraseña);

            // Llamada al procedimiento almacenado para obtener el ID de permisos y el ID de biblioteca
            String comprobar_rol = "{CALL obtener_id_trabajadores(?, ?, ?, ?)}";
            CallableStatement consultas_funciones = conexion.prepareCall(comprobar_rol);

            // Establecer los parámetros de entrada
            consultas_funciones.setInt(1, usuario);  // ID del trabajador
            consultas_funciones.setString(2, contrasenaHasheada);  // Contraseña hasheada

            // Registrar los parámetros de salida
            consultas_funciones.registerOutParameter(3, java.sql.Types.INTEGER);  // ID de permisos
            consultas_funciones.registerOutParameter(4, java.sql.Types.INTEGER);  // ID de biblioteca

            consultas_funciones.execute();

            // Recuperar los valores de salida
            idPermiso = consultas_funciones.getInt(3);  // Obtener ID de permisos
            idBiblioteca = consultas_funciones.getInt(4);  // Obtener ID de biblioteca

            // Verificamos si se obtuvo correctamente el ID del trabajador
            if (idPermiso != 0 && idBiblioteca != 0) {
                id_trabajador = usuario;
            } else {
                System.out.println("No se encontró el trabajador con ID: " + usuario);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id_trabajador;
    }

    // Método para hashear la contraseña con SHA-256 y codificarla en Base64
    private String hashearContraseña(String contraseña) {
        String contraseñaHasheada = "";
        try {
            // Obtener una instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convertir la contraseña en un arreglo de bytes y obtener el hash
            byte[] hashBytes = digest.digest(contraseña.getBytes());

            // Codificar el hash en Base64 para hacerlo legible
            contraseñaHasheada = Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return contraseñaHasheada;
    }

    // Métodos para acceder a los valores de permiso y biblioteca
    public int getIdPermiso() {
        return idPermiso;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
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

        public void cambiarContraseña(String usuario, String nuevaContraseña) {
            // Hashear la nueva contraseña
            String contrasenaHasheada = hashearContraseña(nuevaContraseña);
            // Preparar el SQL para actualizar la contraseña en la base de datos
            String sql = "UPDATE mbappe SET Contrasenia = ? WHERE ID_Trabajador_FK = ?";
            // Conexión y ejecución de la consulta
            try (Connection conn = bd_controller.conectar();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, contrasenaHasheada); // Usar la contraseña hasheada
                stmt.setString(2, usuario); // Usuario (ID del trabajador)

                int filasActualizadas = stmt.executeUpdate();

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
    
    public int seleccionarBiblioteca(String biblioteca){
        int id = 0;
        
        try {
            
            String comprobar_id_biblioteca = "SELECT ID_Biblioteca from bd_biblioteca.biblioteca WHERE Nombre_Biblioteca = ?;";
            
            prepare=conexion.prepareStatement(comprobar_id_biblioteca);
            
            prepare.setString(1, biblioteca);
            
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