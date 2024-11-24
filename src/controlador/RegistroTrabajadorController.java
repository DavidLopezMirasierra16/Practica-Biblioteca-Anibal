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
        this.trabajadorModelo = new TrabajadorModelo();
        this.registroTrabajadoresVista = registroTrabajadoresVista;
        this.registroTrabajadoresVista.getBtn_guardar().addActionListener(this);
        this.registroTrabajadoresVista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }          

     /* Método que agrega los datos del trabajador a la base de datos.
     */
    public void agregarDatos() {
        if (validarDatos()) {           
            int id = this.trabajadorModelo.añadirPermiso(this.registroTrabajadoresVista.getCombo_roles().getSelectedItem().toString());
            int id_biblioteca = this.trabajadorModelo.seleccionarBiblioteca(this.registroTrabajadoresVista.getCombo_bibliotecas().getSelectedItem().toString());            
            if (this.trabajadorModelo.crearTrabajador(id, 
                    this.registroTrabajadoresVista.getTxt_nombre().getText(), 
                    this.registroTrabajadoresVista.getTxt_apellidos().getText(), 
                    this.registroTrabajadoresVista.getTxt_dni().getText(), 
                    this.registroTrabajadoresVista.getTxt_nacimiento().getText(), 
                    this.registroTrabajadoresVista.getTxt_correo().getText(), 
                    this.registroTrabajadoresVista.getTxt_cuenta().getText(), 
                    this.registroTrabajadoresVista.getTxt_seguridad_social().getText(), 
                    this.registroTrabajadoresVista.getTxt_contraseña().getText(),
                    Integer.toString(id_biblioteca))!=null) {
                JOptionPane.showMessageDialog(registroTrabajadoresVista, "Trabajador con dni " + this.registroTrabajadoresVista.getTxt_dni().getText() + " registrado correctamente", "Trabajador dado de alta", JOptionPane.INFORMATION_MESSAGE);
                this.registroTrabajadoresVista.dispose();
            }else{
                JOptionPane.showMessageDialog(registroTrabajadoresVista, "Usuario con dni " + this.registroTrabajadoresVista.getTxt_dni().getText() + " ya registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);
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

        if (this.registroTrabajadoresVista.getTxt_nombre().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.registroTrabajadoresVista.getTxt_apellidos().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir unos apellidos. \n";
            resultado = false;
        }

        if (this.registroTrabajadoresVista.getTxt_nacimiento().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una fecha de nacimiento. \n";
            resultado = false;
        }
        
        if (this.registroTrabajadoresVista.getTxt_correo().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un correo. \n";
            resultado = false;
        }
        
        if (this.registroTrabajadoresVista.getTxt_cuenta().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una cuenta bancaria. \n";
            resultado = false;
        }
        
        if (this.registroTrabajadoresVista.getTxt_seguridad_social().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un numero de la seguridad social. \n";
            resultado = false;
        }
        
        
        if (this.registroTrabajadoresVista.getTxt_localidad().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una fecha de alta. \n";
            resultado = false;
        }
        
        if (this.registroTrabajadoresVista.getTxt_dni().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un DNI. \n";
            resultado = false;
        }
        
        
        if (this.registroTrabajadoresVista.getTxt_contraseña().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una contraseña. \n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registroTrabajadoresVista, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
