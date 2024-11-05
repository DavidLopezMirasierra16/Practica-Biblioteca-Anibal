/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ModLocalidadSocio;

/**
 *
 * @author pablo
 */
public class ModLocalidadSocioController implements ActionListener{
    private ModLocalidadSocio modificar_vista;
    public ModLocalidadSocioController(){
        this.modificar_vista = new ModLocalidadSocio();
        modificar_vista.getBtnAceptar().addActionListener(this);
        modificar_vista.setVisible(true);
        modificar_vista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == modificar_vista.getBtnAceptar()){
            //Metodo que recoge y modifica el usuario con la nueva localidad
        }
    }
    
}
