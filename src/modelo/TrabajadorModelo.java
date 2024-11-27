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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarSocios;
import vista.ConsultarTrabajadores;
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
    private static int idPermiso;
    private static int idBiblioteca;

    public TrabajadorModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.conexion = this.bd_controller.conectar();
    }

    /**
     * Funcion que nos crea un trabajado en funcion de los datos que nosotros
     * introducimos
     *
     * @param id_permiso
     * @param nombre
     * @param apellido
     * @param dni
     * @param fecha_nacimiento
     * @param correo
     * @param cuenta_banco
     * @param seguridad_social
     * @param contraseña
     * @param biblioteca
     * @return
     */
    public Trabajador crearTrabajador(int id_permiso, String nombre, String apellido, String dni, String fecha_nacimiento, String correo, String cuenta_banco, String seguridad_social, String contraseña, String biblioteca) {
        //contraseña = "hola";
        hashearContraseña(contraseña);
        System.out.println("Contraseña hasheada: " + contraseña);

        Trabajador trabajador = new Trabajador(id_permiso, nombre, apellido, dni, fecha_nacimiento, correo, cuenta_banco, seguridad_social, biblioteca);

        if (!comprobarDNI(dni)) {
            ingresarUsuarioBd(trabajador);
            return trabajador;
        }

        return null;
    }

    /**
     * Funcion que nos comprueba que un dni ya está registrado
     *
     * @param dni
     * @return
     */
    public boolean comprobarDNI(String dni) {
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

    /**
     * Funcion que ingresa un usuario en la BD
     *
     * @param trabajador
     */
    public void ingresarUsuarioBd(Trabajador trabajador) {
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
     *
     * @param trabajador
     */
    public void ingresarContraseña(Trabajador trabajador) {
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

    /**
     * Funcion que nos comprueba que rol tiene un trabajador
     *
     * @param usuario
     * @param contraseña
     * @return
     */
    public int comprobarRolFuncion(int usuario, String contraseña) {
        int id_trabajador = 0;
        try {
            // Hashear la contraseña antes de enviarla al procedimiento almacenado
            String contrasenaHasheada = hashearContraseña(contraseña);
            String comprobar_rol = "{CALL obtener_id_trabajadores(?, ?, ?, ?)}";
            CallableStatement consultas_funciones = conexion.prepareCall(comprobar_rol);
            consultas_funciones.setInt(1, usuario); 
            consultas_funciones.setString(2, contrasenaHasheada);
            consultas_funciones.registerOutParameter(3, java.sql.Types.INTEGER);
            consultas_funciones.registerOutParameter(4, java.sql.Types.INTEGER);
            consultas_funciones.execute();
            idPermiso = consultas_funciones.getInt(3);
            idBiblioteca = consultas_funciones.getInt(4);

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

    /**
     * Método para hashear la contraseña con SHA-256 y codificarla en Base64
     *
     * @param contraseña
     * @return
     */
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

    /**
     * Métodos para acceder a los valores de permiso y biblioteca
     *
     * @return
     */
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
    public int añadirPermiso(String rol) {
        int id = 0;

        try {

            String comprobar_id_biblioteca = "SELECT ID_Pernisos from bd_biblioteca.permisos WHERE Permiso = ?;";

            prepare = conexion.prepareStatement(comprobar_id_biblioteca);

            prepare.setString(1, rol);

            resultado = prepare.executeQuery();

            if (resultado.next()) {
                id = resultado.getInt("ID_Pernisos");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;

    }

    /**
     * Funcion para cambiar la contraseña de un tabajador
     * @param usuario
     * @param nuevaContraseña 
     */
    public void cambiarContraseña(String usuario, String nuevaContraseña) {
        String contrasenaHasheada = hashearContraseña(nuevaContraseña);
        String sql = "UPDATE mbappe SET Contrasenia = ? WHERE ID_Trabajador_FK = ?";
        try (Connection conn = bd_controller.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contrasenaHasheada);
            stmt.setString(2, usuario);

            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Contraseña actualizada exitosamente");
            } else {
                System.out.println("No se encontró el usuario o no se pudo actualizar la contraseña");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la contraseña: " + e.getMessage());
        }
    }

    /**
     * Funcion que busca el id de una biblioteca
     * @param biblioteca
     * @return 
     */
    public int seleccionarBiblioteca(String biblioteca) {
        int id = 0;

        try {

            String comprobar_id_biblioteca = "SELECT ID_Biblioteca from bd_biblioteca.biblioteca WHERE Nombre_Biblioteca = ?;";

            prepare = conexion.prepareStatement(comprobar_id_biblioteca);

            prepare.setString(1, biblioteca);

            resultado = prepare.executeQuery();

            if (resultado.next()) {
                id = resultado.getInt("ID_Biblioteca");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    
    public void cambiarEstadoHabilitado(ConsultarTrabajadores consultar) {
        int row = consultar.getTablaTrabajadores().getSelectedRow();
        if (row != -1) {
            int idTrabajador = (int) consultar.getTablaTrabajadores().getValueAt(row, 0);
            String estadoActual = (String) consultar.getTablaTrabajadores().getValueAt(row, 7);
            String nuevoEstado;
            if ("Habilitado".equalsIgnoreCase(estadoActual)) {
                nuevoEstado = "Deshabilitado";
            } else {
                nuevoEstado = "Habilitado";
            }
            int confirm = JOptionPane.showConfirmDialog(null,"¿Estás seguro de que deseas cambiar el estado de Activo del trabajador seleccionado?","Confirmar",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                actualizarEstadoHabilitadoEnBD(idTrabajador, nuevoEstado);
                consultar.getTablaTrabajadores().setValueAt(nuevoEstado, row, 7);
                JOptionPane.showMessageDialog(null, "Estado Activo actualizado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un trabajador");
        }
    }

    // Método para actualizar el estado en la base de datos
    private void actualizarEstadoHabilitadoEnBD(int idTrabajador, String nuevoEstado) {
        String actualiza_Activo = "UPDATE trabajadores SET Activo = ? WHERE ID_Trabajador = ?";
        try (Connection conexion = bd_controller.conectar();
            PreparedStatement parametro = conexion.prepareStatement(actualiza_Activo)) {
            parametro.setString(1, nuevoEstado);
            parametro.setInt(2, idTrabajador);
            int filasAfectadas = parametro.executeUpdate();
            if (filasAfectadas == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado. Por favor, intente de nuevo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + e.getMessage());
        }
    }

    
    public void consultarTrabajadores(JTable table) {
        String consulta_Trabajadores = "CALL obtenerDatosTrabajadores()";
        BaseDatosController baseDatosController = new BaseDatosController();
        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(consulta_Trabajadores);
             ResultSet result = parametro.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (result.next()) {
                Object[] row = {
                    result.getInt("ID_Trabajador"),
                    result.getString("Nombre"),
                    result.getString("Apellidos"),
                    result.getString("DNI"),
                    result.getString("Correo"),
                    result.getString("Permiso"),
                    result.getString("Localidad"),
                    result.getString("Activo")
                };
                model.addRow(row);
            }

            table.setModel(model);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void filtrarTrabajadores(String busqueda, JTable table) {
        if (busqueda.isEmpty()) {
            System.out.println("Debe ingresar un valor de búsqueda");
            return;
        }
        String consulta_TrabajadorFiltro = "{CALL obtenerDatosTrabajadoresFiltrados(?)}";
        BaseDatosController baseDatosController = new BaseDatosController();
        try (Connection conexion = baseDatosController.conectar();
             PreparedStatement parametro = conexion.prepareStatement(consulta_TrabajadorFiltro)) {
            parametro.setString(1, busqueda);
            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                while (result.next()) {
                    Object[] row = {
                        result.getInt("ID_Trabajador"),
                        result.getString("Nombre"),
                        result.getString("Apellidos"),
                        result.getString("DNI"),
                        result.getString("Correo"),
                        result.getString("Permiso"),
                        result.getString("Localidad"),
                        result.getString("Activo")
                    };
                    model.addRow(row);
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
    * Método que comprueba si el trabajador está logado y habilitado.
    * @param idTrabajador El ID del trabajador que se desea comprobar.
    * @return Un String indicando si el trabajador está "Logado" y "Habilitado", o si está "Deshabilitado".
    */
   public String comprobarEstadoTrabajador(int idTrabajador) {
       String estado = "Deshabilitado"; // Valor por defecto
       String consultaEstado = "SELECT Activo FROM trabajadores WHERE ID_Trabajador = ?";

       try (PreparedStatement stmt = conexion.prepareStatement(consultaEstado)) {
           stmt.setInt(1, idTrabajador);

           try (ResultSet resultSet = stmt.executeQuery()) {
               if (resultSet.next()) {
                   estado = resultSet.getString("Activo");
               } else {
                   System.out.println("No se encontró el trabajador con ID: " + idTrabajador);
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }

       return estado;
   }
}
