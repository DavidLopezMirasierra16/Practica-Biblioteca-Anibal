/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.BibliotecaModelo;
import modelo.SocioModelo;
import vista.RegistroSocios;

/**
 *
 * @author pablo
 */
public class RegistroSocioController implements ActionListener{
    
    private SocioModelo socio_modelo;
    private RegistroSocios registro_socio_vista;
    private BibliotecaModelo biblioteca_modelo;

    public RegistroSocioController(RegistroSocios registro_socio_vista) throws SQLException {
        //Clases
        this.socio_modelo = new SocioModelo();
        this.registro_socio_vista = registro_socio_vista;
        this.biblioteca_modelo = new BibliotecaModelo();
        //Botones
        this.registro_socio_vista.getBtn_guardar().addActionListener(this);
        this.registro_socio_vista.getTxt_alta().setText(this.socio_modelo.fecha());
        //------------------------------------------
        this.registro_socio_vista.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }
    
    /**
     * Funcion que nos agrega los datos del socio que escribimos
     */ 
    public void agregarDatos(){
        
        if (validarDatos()) {
            
            int id = this.biblioteca_modelo.idBiblioteca(this.registro_socio_vista.getCombo_biblioteca().getSelectedItem().toString());
            
            if (this.socio_modelo.crearSocio(this.registro_socio_vista.getTxt_dni().getText(), 
                    this.registro_socio_vista.getTxt_nombre().getText(),
                    this.registro_socio_vista.getTxt_apellidos().getText(), 
                    this.registro_socio_vista.getTxt_direccion().getText(), 
                    this.registro_socio_vista.getTxt_telefono().getText(), 
                    this.registro_socio_vista.getTxt_correo().getText(), 
                    this.registro_socio_vista.getTxt_alta().getText(), 
                    this.registro_socio_vista.getTxt_cuenta().getText(), 
                    Integer.toString(id)) != null) {
                JOptionPane.showMessageDialog(registro_socio_vista, "Socio con dni " + this.registro_socio_vista.getTxt_dni().getText() + " registrado correctamente", "Socio dado de alta", JOptionPane.INFORMATION_MESSAGE);
                this.registro_socio_vista.dispose();
            }else{
                JOptionPane.showMessageDialog(registro_socio_vista, "Usuario con dni " + this.registro_socio_vista.getTxt_dni().getText() + " ya registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);                
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

        if (this.registro_socio_vista.getTxt_dni().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un dni. \n";
            resultado = false;
        }

        if (this.registro_socio_vista.getTxt_nombre().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.registro_socio_vista.getTxt_apellidos().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir unos apellidos. \n";
            resultado = false;
        }

        if (this.registro_socio_vista.getTxt_direccion().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una direccion. \n";
            resultado = false;
        }
        
        if (this.registro_socio_vista.getTxt_telefono().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un telefono. \n";
            resultado = false;
        }
        
        if (this.registro_socio_vista.getTxt_correo().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un correo. \n";
            resultado = false;
        }
        
        if (this.registro_socio_vista.getTxt_alta().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una fecha de alta. \n";
            resultado = false;
        }
        
        if (this.registro_socio_vista.getTxt_cuenta().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una cuenta bancaria. \n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registro_socio_vista, mensaje, "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
    
}