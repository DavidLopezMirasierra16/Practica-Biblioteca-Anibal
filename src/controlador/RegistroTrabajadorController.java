/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.TrabajadorModelo;
import vista.RegistroTrabajadores;

/**
 *
 * @author pablo
 */
public class RegistroTrabajadorController implements ActionListener{
    private TrabajadorModelo trabajadores_modelo;
    private RegistroTrabajadores registrotrabajadores_vista;
    
    public RegistroTrabajadorController(RegistroTrabajadores registrotrabajadores_vista) throws SQLException{
        //Clases
        this.trabajadores_modelo = new TrabajadorModelo();
        this.registrotrabajadores_vista = registrotrabajadores_vista;
        //Botones
        this.registrotrabajadores_vista.getBtn_guardar().addActionListener(this);
        //-------------------------------------------------
        this.registrotrabajadores_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }
    
    /**
     * Funcion que agrega todos los datos que escribimos.
     */
    public void agregarDatos(){
        if (validarDatos()) {
            
            int id = this.trabajadores_modelo.añadirPermiso(this.registrotrabajadores_vista.getCombo_roles().getSelectedItem().toString());
            
            if (this.trabajadores_modelo.crearTrabajador(id, 
                    this.registrotrabajadores_vista.getTxt_nombre().getText(), 
                    this.registrotrabajadores_vista.getTxt_apellidos().getText(), 
                    this.registrotrabajadores_vista.getTxt_dni().getText(), 
                    this.registrotrabajadores_vista.getTxt_nacimiento().getText(), 
                    this.registrotrabajadores_vista.getTxt_correo().getText(), 
                    this.registrotrabajadores_vista.getTxt_cuenta().getText(), 
                    this.registrotrabajadores_vista.getTxt_seguridad_social().getText(), 
                    this.registrotrabajadores_vista.getTxt_localidad().getText(), 
                    this.registrotrabajadores_vista.getTxt_contraseña().getText())!=null) {
                JOptionPane.showMessageDialog(registrotrabajadores_vista, "Trabajador con dni " + this.registrotrabajadores_vista.getTxt_dni().getText() + " registrado correctamente", "Trabajador dado de alta", JOptionPane.INFORMATION_MESSAGE);
                this.registrotrabajadores_vista.dispose();
            }else{
                JOptionPane.showMessageDialog(registrotrabajadores_vista, "Trabajador con dni " + this.registrotrabajadores_vista.getTxt_dni().getText() + " ya registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);                
            }
            
        }
    }
    
    /**
     * Funcion que nos valida si los campos estan correctamente rellenados.
     * @return 
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
            JOptionPane.showMessageDialog(registrotrabajadores_vista, mensaje, "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
    
}
