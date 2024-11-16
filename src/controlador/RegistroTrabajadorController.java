package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.TrabajadorModelo;
import vista.RegistroTrabajadores;

public class RegistroTrabajadorController implements ActionListener {
    
    private TrabajadorModelo trabajadorModelo;
    private RegistroTrabajadores registroTrabajadoresVista;

    public RegistroTrabajadorController(RegistroTrabajadores registroTrabajadoresVista) throws SQLException {
        // Inicialización de la clase de modelo y vista
        this.trabajadorModelo = new TrabajadorModelo();
        this.registroTrabajadoresVista = registroTrabajadoresVista;
        
        // Configuración del botón de guardar
        this.registroTrabajadoresVista.getBtn_guardar().addActionListener(this);
        
        // Mostrar la vista
        this.registroTrabajadoresVista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Acción de guardar trabajador

        agregarDatos();
    }          

     /* Método que agrega los datos del trabajador a la base de datos.
     */
    public void agregarDatos() {
        
        // Validación de datos
        if (validarDatos()) {
            String nombre = this.registroTrabajadoresVista.getTxt_nombre().getText();
            String apellido = this.registroTrabajadoresVista.getTxt_apellidos().getText();
            String dni = this.registroTrabajadoresVista.getTxt_dni().getText();
            String fechaNacimiento = this.registroTrabajadoresVista.getTxt_nacimiento().getText();
            String correo = this.registroTrabajadoresVista.getTxt_correo().getText();
            String cuentaBanco = this.registroTrabajadoresVista.getTxt_cuenta().getText();
            String seguridadSocial = this.registroTrabajadoresVista.getTxt_seguridad_social().getText();
            String localidad = this.registroTrabajadoresVista.getTxt_localidad().getText();
            String contraseña = "contraseñaEjemplo"; // Cambiar por una fuente segura

            int idPermiso = obtenerIdPermiso();

            if (this.trabajadorModelo.crearTrabajador(idPermiso, nombre, apellido, dni, fechaNacimiento, correo, cuentaBanco, seguridadSocial, localidad, contraseña) != null) {
                JOptionPane.showMessageDialog(registroTrabajadoresVista, "Trabajador " + nombre + " registrado correctamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                this.registroTrabajadoresVista.dispose();
            } else {
                JOptionPane.showMessageDialog(registroTrabajadoresVista, "El DNI " + dni + " ya está registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    
    /**

     * Funcion que nos valida si los campos estan correctamente rellenados.
     * @return 
     * Método que valida los datos ingresados en la vista.
     * @return true si los datos son válidos, false si no lo son.
     */
    public boolean validarDatos() {
        boolean resultado = true;
        String mensaje = " ";

        if (this.registrotrabajadores_vista.getTxt_nombre().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.registrotrabajadores_vista.getTxt_apellidos().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir unos apellidos. \n";
            resultado = false;
        }

        if (this.registrotrabajadores_vista.getTxt_nacimiento().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una fecha de nacimiento. \n";
            resultado = false;
        }
        
        if (this.registrotrabajadores_vista.getTxt_correo().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un correo. \n";
            resultado = false;
        }
        
        if (this.registrotrabajadores_vista.getTxt_cuenta().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una cuenta bancaria. \n";
            resultado = false;
        }
        
        if (this.registrotrabajadores_vista.getTxt_seguridad_social().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un numero de la seguridad social. \n";
            resultado = false;
        }
        
        
        if (this.registrotrabajadores_vista.getTxt_localidad().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una fecha de alta. \n";
            resultado = false;
        }
        
        if (this.registrotrabajadores_vista.getTxt_dni().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un DNI. \n";
            resultado = false;
        }
        
        
        if (this.registrotrabajadores_vista.getTxt_contraseña().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una contraseña. \n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registroTrabajadoresVista, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
