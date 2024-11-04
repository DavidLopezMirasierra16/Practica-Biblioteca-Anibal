/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.RegistroTrabajadores;

/**
 *
 * @author pablo
 */
public class RegistroTrabajadorController implements ActionListener{
    private RegistroTrabajadores registrotrabajadores_vista;
    public RegistroTrabajadorController(){
        this.registrotrabajadores_vista = new RegistroTrabajadores();
        this.registrotrabajadores_vista.getBtn_guardar().addActionListener(this);
        this.registrotrabajadores_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registrotrabajadores_vista.getBtn_guardar()){
            //Metodo que registra autor
        }
    }
}
