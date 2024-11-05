/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ConsultarLibros;

/**
 *
 * @author pablo
 */
public class ConsultarLibrosController implements ActionListener{
    private ConsultarLibros consultalibros_vista;
    private ConsultarLibrosController(){
        this.consultalibros_vista.getBtnBuscar().addActionListener(this);
        this.consultalibros_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == consultalibros_vista.getBtnBuscar()){
            //Metodo para consultar libros
        }
    }
}
