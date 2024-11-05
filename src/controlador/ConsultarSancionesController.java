/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ConsultarSanciones;

/**
 *
 * @author pablo
 */
public class ConsultarSancionesController implements ActionListener{
    private ConsultarSanciones consultasanciones_vista;
    private ConsultarSancionesController(){
        this.consultasanciones_vista.getBtnBuscar().addActionListener(this);
        this.consultasanciones_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == consultasanciones_vista.getBtnBuscar()){
            //Metodo para consultar sanciones
        }
    }
}
