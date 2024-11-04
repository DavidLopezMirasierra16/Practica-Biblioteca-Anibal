/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.RegistrarAutor;

/**
 *
 * @author pablo
 */
public class RegistroAutorController implements ActionListener{
    private RegistrarAutor registroautor_vista;
    public RegistroAutorController(){
        this.registroautor_vista = new RegistrarAutor();
        this.registroautor_vista.getBtn_agregar().addActionListener(this);
        this.registroautor_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registroautor_vista.getBtn_agregar()){
            //Metodo que registra autor
        }
    }
}
