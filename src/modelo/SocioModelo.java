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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarSocios;
import vista.ModCorreoSocio;
import vista.ModCuentaBancariaSocio;
import vista.ModLocalidadSocio;
import vista.ModTelefonoSocio;
import vista.RecuperarContrasena;

/**
 * Modelo para realizar operaciones de base de datos sobre los socios.
 */
public class SocioModelo {

    private BaseDatosController bd_controller;
    private TrabajadorModelo trabajador;
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public SocioModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.trabajador = new TrabajadorModelo();
        this.conexion = this.bd_controller.conectar();
    }
    
    //--------------------------------INSERTAR----------------------------------
    
    /**
     * Nos crea un socio en funcion de los parámetros que nosotros le pasamos.
     * @param dni
     * @param nombre
     * @param apellidos
     * @param direccion
     * @param telefono
     * @param correo
     * @param fecha_alta
     * @param cuenta_banco
     * @param ID_Biblioteca
     * @return 
     */
    public Socio crearSocio(String dni, String nombre, String apellidos, String direccion, String telefono, String correo, String fecha_alta, String cuenta_banco, String ID_Biblioteca){
        Socio socio = new Socio(dni, nombre, apellidos, direccion, telefono, correo, fecha_alta, cuenta_banco, ID_Biblioteca);
        
        if (!comprobarDNI(dni)) {
            ingresarUsuarioBd(socio);
            return socio;
        }
        
        return null;
    }
    
    /**
     * Comprueba que un DNI no esté ya registrado.
     * @param dni
     * @return 
     */
    public boolean comprobarDNI(String dni){
        try {
            //this.bd_controller.conectarBd();
            
                String buscar_email = "SELECT DNI_Socio FROM bd_biblioteca.socios WHERE DNI_Socio=?;";
            
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
     * Nos inserta un socio en la BD.
     * @param socio 
     */
    public void ingresarUsuarioBd(Socio socio){
        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.socios (DNI_Socio, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria, ID_Biblioteca_FK)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setString(1, socio.getDni());
            prepare.setString(2, socio.getNombre());
            prepare.setString(3, socio.getApellidos());
            prepare.setString(4, socio.getDireccion());
            prepare.setString(5, socio.getTelefono());
            prepare.setString(6, socio.getCorreo());
            prepare.setString(7, socio.getFecha_alta());
            prepare.setString(8, socio.getCuenta_banco());
            prepare.setString(9, socio.getID_Biblioteca());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Usuario " + socio.getNombre() + " agregado correctamente a la BD");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
    
    //--------------------------------CONSULTAR---------------------------------
    
    /**
     * Método para consultar los socios registrados en la bd
     * @param table 
     */
    public void consultarSocios(JTable table) {
        int idBiblioteca = trabajador.getIdBiblioteca();
        String consulta_Socio = "SELECT DNI_Socio, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria FROM socios WHERE ID_Biblioteca_FK = ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(consulta_Socio)) {
            parametro.setInt(1, idBiblioteca);
            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                while (result.next()) {
                    Object[] row = {
                        result.getString("DNI_Socio"),
                        result.getString("Nombre"),
                        result.getString("Apellidos"),
                        result.getString("Direccion"),
                        result.getString("Telefono"),
                        result.getString("Correo_Socio"),
                        result.getString("Fecha_Alta"),
                        result.getString("Cuenta_Bancaria"),
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
     * Método para consultar los Socios de la BD filtrando por varios campos
     * @param consultarSocios
     * @param table 
     */
    public void filtrarSocios(ConsultarSocios consultarSocios, JTable table) {
        String filtro = (String) consultarSocios.getCbFiltro().getSelectedItem();
        String busqueda = consultarSocios.getTxtBusqueda().getText().trim();
        int idBiblioteca = trabajador.getIdBiblioteca();
        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna = "";
        switch (filtro) {
            case "Nombre":
                columna = "Nombre";
                break;
            case "Apellidos":
                columna = "Apellidos";
                break;
            case "Correo":
                columna = "Correo_Socio";
                break;
            case "ID":
                columna = "DNI_Socio";
                break;
        }

        String consulta_SocioFiltro = "SELECT DNI_Socio, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria FROM socios WHERE " + columna + " LIKE ? AND ID_Biblioteca_FK = ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(consulta_SocioFiltro)) {

            parametro.setString(1, "%" + busqueda + "%");
            parametro.setInt(2, idBiblioteca);

            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                while (result.next()) {
                    Object[] row = {
                        result.getInt("DNI_Socio"),
                        result.getString("Nombre"),
                        result.getString("Apellidos"),
                        result.getString("Direccion"),
                        result.getString("Telefono"),
                        result.getString("Correo_Socio"),
                        result.getString("Fecha_Alta"),
                        result.getString("Cuenta_Bancaria"),
                    };
                    model.addRow(row);
                }
                consultarSocios.getTablaSocios().setModel(model);
                for (int i = 0; i < consultarSocios.getTablaSocios().getColumnCount(); i++) {
                    consultarSocios.getTablaSocios().getColumnModel().getColumn(i).setResizable(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método para modificar el ID_Biblioteca_FK de un socio usando nombre, apellidos y localidad
     * @param modificar 
     */

    public void modificarIdBiblioteca(ModLocalidadSocio modificar) {
        String nombre = modificar.getTxtSocio().getText().trim();
        String apellidos = modificar.getTxtApellido().getText().trim();
        String localidad = modificar.getTxtLocalidad().getText().trim();
        String direccion = modificar.getTxtLocalidad().getText().trim();
        String modifica_biblioteca = "CALL ModificarIdBibliotecaPorNombreYApellidos(?, ?, ?)";

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement parametro = conexion.prepareStatement(modifica_biblioteca)) {
            parametro.setString(1, nombre);
            parametro.setString(2, apellidos);
            parametro.setString(3, localidad);
            parametro.setString(4, direccion);
            boolean resultado = parametro.execute();

            if (resultado) {
                JOptionPane.showMessageDialog(null, "La modificación se ha realizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un socio o una biblioteca correspondiente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el ID_Biblioteca_FK: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarTelefono(ModTelefonoSocio modificar) {
        String nombre = modificar.getTxtSocio().getText().trim();
        String apellidos = modificar.getTxtApellido().getText().trim();
        String nuevoTelefono = modificar.getTxtTelefono().getText().trim();
        String modifica_telefono = "CALL ModificarTelefonoPorNombreYApellidos(?, ?, ?)";

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement parametro = conexion.prepareStatement(modifica_telefono)) {
            parametro.setString(1, nombre);
            parametro.setString(2, apellidos);
            parametro.setString(3, nuevoTelefono);
            boolean resultado = parametro.execute();

            if (resultado) {
                JOptionPane.showMessageDialog(null, "El teléfono ha sido actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un socio con el nombre y apellidos especificados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el teléfono del socio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarCorreo(ModCorreoSocio modificar) {
        String nombre = modificar.getTxtSocio().getText().trim();
        String apellidos = modificar.getTxtApellido().getText().trim();
        String nuevaCuenta = modificar.getTxtCorreo().getText().trim();
        String modifica_correo = "CALL ModificarCorreoPorNombreYApellidos(?, ?, ?)";

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement parametro = conexion.prepareStatement(modifica_correo)) {
            parametro.setString(1, nombre);
            parametro.setString(2, apellidos);
            parametro.setString(3, nuevaCuenta);
            boolean resultado = parametro.execute();

            if (resultado) {
                JOptionPane.showMessageDialog(null, "El correo ha sido actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un socio con el nombre y apellidos especificados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el correo del socio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarCuentaBancaria(ModCuentaBancariaSocio modificar) {
        String nombre = modificar.getTxtSocio().getText().trim();
        String apellidos = modificar.getTxtApellido().getText().trim();
        String nuevoCorreo = modificar.getTxtCuenta().getText().trim();
        String modifica_CB = "CALL ModificarCuentaBancariaPorNombreYApellidos(?, ?, ?)";

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement parametro = conexion.prepareStatement(modifica_CB)) {
            parametro.setString(1, nombre);
            parametro.setString(2, apellidos);
            parametro.setString(3, nuevoCorreo);
            boolean resultado = parametro.execute();

            if (resultado) {
                JOptionPane.showMessageDialog(null, "La cuenta bancaria ha sido actualizada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un socio con el nombre y apellidos especificados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la cuenta bancaria del socio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 
    public void cambiarEstadoPago(ConsultarSocios consultar) {
        int row = consultar.getTablaSocios().getSelectedRow();
        if (row != -1) {
            int idSocio = (int) consultar.getTablaSocios().getValueAt(row, 0);
            String estadoActual = (String) consultar.getTablaSocios().getValueAt(row, 8);
            String nuevoEstado;
            if ("Pagado".equalsIgnoreCase(estadoActual)) {
                nuevoEstado = "No Pagado";
            } else {
                nuevoEstado = "Pagado";
            }
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas cambiar el estado de 'Pagado' del socio seleccionado?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                actualizarEstadoPagoEnBD(idSocio, nuevoEstado);
                consultar.getTablaSocios().setValueAt(nuevoEstado, row, 8);
                JOptionPane.showMessageDialog(null, "Estado de 'Pagado' actualizado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un socio.");
        }
    }

    private void actualizarEstadoPagoEnBD(int idSocio, String nuevoEstado) {
        String actualiza_Pago = "UPDATE socios SET Pagado = ? WHERE ID_Socios = ?";
        try (Connection conexion = bd_controller.conectar();
             PreparedStatement parametro = conexion.prepareStatement(actualiza_Pago)) {
            parametro.setString(1, nuevoEstado);
            parametro.setInt(2, idSocio);
            int filasAfectadas = parametro.executeUpdate();
            if (filasAfectadas == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado. Por favor, intente nuevamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + e.getMessage());
        }
    }
}