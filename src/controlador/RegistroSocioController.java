/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.RegistroSocios;

/**
 *
 * @author pablo
 */
public class RegistroSocioController implements ActionListener{
    private RegistroSocios registrosocio_vista;
    public RegistroSocioController(){
        this.registrosocio_vista = new RegistroSocios();
        this.registrosocio_vista.getBtn_guardar().addActionListener(this);
        this.registrosocio_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registrosocio_vista.getBtn_guardar()){
            //Metodo que registra autor
        }
    }
}