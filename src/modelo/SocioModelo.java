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
import vista.ModLocalidadSocio;
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
    
    public void consultarSocios(JTable table) {
        int idBiblioteca = trabajador.getIdBiblioteca();
        String sql = "SELECT DNI_Socio, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria FROM socios WHERE ID_Biblioteca_FK = ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer el valor para el parámetro de la consulta
            stmt.setInt(1, idBiblioteca);

            try (ResultSet rs = stmt.executeQuery()) {

                // Crear un modelo para la tabla y limpiar las filas anteriores
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Limpiar las filas actuales

                // Agregar las filas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("DNI_Socio"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Direccion"),
                        rs.getString("Telefono"),
                        rs.getString("Correo_Socio"),
                        rs.getString("Fecha_Alta"),
                        rs.getString("Cuenta_Bancaria"),
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                table.setModel(model);

                // No permitir redimensionar las columnas
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setResizable(false);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Método para filtrar socios según el filtro y la búsqueda
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

        String sql = "SELECT DNI_Socio, Nombre, Apellidos, Direccion, Telefono, Correo_Socio, Fecha_Alta, Cuenta_Bancaria FROM socios WHERE " + columna + " LIKE ? AND ID_Biblioteca_FK = ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + busqueda + "%"); // Usamos % para buscar coincidencias parciales
            stmt.setInt(2, idBiblioteca);

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Limpiar las filas actuales antes de filtrar

                // Agregar las filas filtradas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("DNI_Socio"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Direccion"),
                        rs.getString("Telefono"),
                        rs.getString("Correo_Socio"),
                        rs.getString("Fecha_Alta"),
                        rs.getString("Cuenta_Bancaria"),
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                consultarSocios.getTablaSocios().setModel(model);

                // No permitir redimensionar las columnas
                for (int i = 0; i < consultarSocios.getTablaSocios().getColumnCount(); i++) {
                    consultarSocios.getTablaSocios().getColumnModel().getColumn(i).setResizable(false);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para modificar el ID_Biblioteca_FK de un socio usando nombre, apellidos y localidad
    public void modificarIdBiblioteca(ModLocalidadSocio modificar) {
        String nombre = modificar.getTxtSocio().getText().trim();  // Nombre del socio
        String apellidos = modificar.getTxtApellido().getText().trim();  // Apellidos del socio
        String localidad = modificar.getTxtLocalidad().getText().trim();  // Localidad de la biblioteca

        // Consulta SQL para llamar al procedimiento almacenado
        String sql = "CALL ModificarIdBibliotecaPorNombreYApellidos(?, ?, ?)";

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Establecer los valores para el PreparedStatement
            stmt.setString(1, nombre);  // Establece el nombre del socio
            stmt.setString(2, apellidos);  // Establece los apellidos del socio
            stmt.setString(3, localidad);  // Establece la localidad de la biblioteca

            // Ejecutar la llamada al procedimiento almacenado
            boolean resultado = stmt.execute();

            // Si la llamada es exitosa, el procedimiento interno se encargará de la actualización
            if (resultado) {
                System.out.println("La modificación se ha realizado correctamente.");
            } else {
                System.out.println("No se encontró un socio o una biblioteca correspondiente.");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar el ID_Biblioteca_FK: " + e.getMessage());
        }
    }
    
    public void cambiarEstadoPago(ConsultarSocios consultar) {
        // Obtener la fila seleccionada en la tabla
        int row = consultar.getTablaSocios().getSelectedRow();

        if (row != -1) { // Si hay una fila seleccionada
            // Obtener el ID del socio (asumimos que está en la primera columna)
            int idSocio = (int) consultar.getTablaSocios().getValueAt(row, 0);

            // Obtener el estado actual del campo "Pagado" (asumimos que está en la novena columna)
            String estadoActual = (String) consultar.getTablaSocios().getValueAt(row, 8);

            // Determinar el nuevo estado
            String nuevoEstado;
            if ("Pagado".equalsIgnoreCase(estadoActual)) {
                nuevoEstado = "No Pagado";
            } else {
                nuevoEstado = "Pagado";
            }

            // Confirmar la acción de cambio de estado
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas cambiar el estado de 'Pagado' del socio seleccionado?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Actualizar el estado en la base de datos
                actualizarEstadoPagoEnBD(idSocio, nuevoEstado);

                // Actualizar el estado en la tabla
                consultar.getTablaSocios().setValueAt(nuevoEstado, row, 8);

                // Mostrar un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Estado de 'Pagado' actualizado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un socio.");
        }
    }

    // Método para actualizar el estado en la base de datos
    private void actualizarEstadoPagoEnBD(int idSocio, String nuevoEstado) {
        String sql = "UPDATE socios SET Pagado = ? WHERE ID_Socios = ?";

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Establecer los valores de los parámetros
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idSocio);

            // Ejecutar la consulta de actualización
            int filasAfectadas = stmt.executeUpdate();

            // Si no se actualizó ninguna fila, mostrar un mensaje de error
            if (filasAfectadas == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado. Por favor, intente nuevamente.");
            }
        } catch (SQLException e) {
            // Si ocurre un error, mostrar el mensaje de error
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + e.getMessage());
        }
    }

    public void cambiarEstadoHabilitado(ConsultarSocios consultar) {
        // Obtener la fila seleccionada en la tabla
        int row = consultar.getTablaSocios().getSelectedRow();

        if (row != -1) { // Si hay una fila seleccionada
            // Obtener el ID del socio (asumimos que está en la primera columna)
            int idSocio = (int) consultar.getTablaSocios().getValueAt(row, 0);

            // Obtener el estado actual del campo "Habilitado" (asumimos que está en la columna correspondiente)
            String estadoActual = (String) consultar.getTablaSocios().getValueAt(row, 9); // Ajusta el índice de la columna

            // Determinar el nuevo estado
            String nuevoEstado;
            if ("Habilitado".equalsIgnoreCase(estadoActual)) {
                nuevoEstado = "Deshabilitado";
            } else {
                nuevoEstado = "Habilitado";
            }

            // Confirmar la acción de cambio de estado
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas cambiar el estado de 'Habilitado' del socio seleccionado?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Actualizar el estado en la base de datos
                actualizarEstadoHabilitadoEnBD(idSocio, nuevoEstado);

                // Actualizar el estado en la tabla
                consultar.getTablaSocios().setValueAt(nuevoEstado, row, 9); // Ajusta el índice de la columna

                // Mostrar un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Estado de 'Habilitado' actualizado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un socio.");
        }
    }

    // Método para actualizar el estado de Habilitado en la base de datos
    private void actualizarEstadoHabilitadoEnBD(int idSocio, String nuevoEstado) {
        String sql = "UPDATE socios SET Habilitado = ? WHERE ID_Socios = ?"; // Cambia Habilitado

        try (Connection conexion = bd_controller.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Establecer los valores de los parámetros
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idSocio);

            // Ejecutar la consulta de actualización
            int filasAfectadas = stmt.executeUpdate();

            // Si no se actualizó ninguna fila, mostrar un mensaje de error
            if (filasAfectadas == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado. Por favor, intente nuevamente.");
            }
        } catch (SQLException e) {
            // Si ocurre un error, mostrar el mensaje de error
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + e.getMessage());
        }
    }

}