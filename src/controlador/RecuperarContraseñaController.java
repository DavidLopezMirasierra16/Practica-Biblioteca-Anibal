/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.SocioModelo;
import vista.RecuperarContraseña;

/**
 *
 * @author pablo
 */
public class RecuperarContraseñaController implements ActionListener{
    private RecuperarContraseña recuperar_vista;
    private SocioModelo modelo;
    public RecuperarContraseñaController(){
        this.modelo = new SocioModelo();
        this.recuperar_vista = new RecuperarContraseña();
        this.recuperar_vista.getBtnRecuperar().addActionListener(this);
        this.recuperar_vista.getBtnVolver().addActionListener(this);
        this.recuperar_vista.setVisible(true);
        this.recuperar_vista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == recuperar_vista.getBtnRecuperar()){
            //Metodo que recoge el usuario y la nueva contraseña y actualiza su contraseña
            modelo.cambiarContraseña(recuperar_vista);
        }
        
        if(e.getSource() == recuperar_vista.getBtnVolver()){
            recuperar_vista.dispose();
        }
    }
}
