/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.SocioModelo;
import modelo.TrabajadorModelo;
import vista.RecuperarContraseña;

/**
 *
 * @author pablo
 */
public class RecuperarContraseñaController implements ActionListener{
    private RecuperarContraseña recuperar_vista;
    private TrabajadorModelo modelo;
    
    public RecuperarContraseñaController(RecuperarContraseña recuperar_vista) throws SQLException{
        //Clases
        this.modelo = new TrabajadorModelo();
        this.recuperar_vista = recuperar_vista;
        //Botones
        this.recuperar_vista.getBtnRecuperar().addActionListener(this);
        this.recuperar_vista.getBtnVolver().addActionListener(this);
        //--------------------------------------
        this.recuperar_vista.setVisible(true);
        this.recuperar_vista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Object button = e.getSource();
        
        if(button == recuperar_vista.getBtnRecuperar()){
            try {
                cambiarContraseña();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RecuperarContraseñaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getSource() == recuperar_vista.getBtnVolver()){
            recuperar_vista.dispose();
        }
    }
    
    /**
     * Funcion que nos cambia la contraseña de un usuario
     */
    public void cambiarContraseña() throws NoSuchAlgorithmException{
        
        if (validarDatos()) {
            
            this.modelo.cambiarContraseña(new RecuperarContraseña());
        }
        
    }
    
    /**
     * Funcion que nos valida los campos
     * @return 
     */
    public boolean validarDatos(){
        boolean resultado = true;
        String mensaje = " ";

        if (this.recuperar_vista.getTxt_usuario().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.recuperar_vista.getTxt_contraseña().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una contraseña nueva. \n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(recuperar_vista, mensaje, "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
    
}
