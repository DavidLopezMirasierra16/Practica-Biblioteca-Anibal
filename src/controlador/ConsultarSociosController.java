/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ConsultarSocios;

/**
 *
 * @author pablo
 */
public class ConsultarSociosController implements ActionListener{
    private ConsultarSocios consultasocios_vista;
    private ConsultarSociosController(){
        this.consultasocios_vista.getBtnBuscar().addActionListener(this);
        this.consultasocios_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == consultasocios_vista.getBtnBuscar()){
            //Metodo para consultar socios
        }
    }
}
