/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ConsultarPrestamos;

/**
 *
 * @author pablo
 */
public class ConsultarPrestamosController implements ActionListener{
    private ConsultarPrestamos consultaprestamos_vista;
    private ConsultarPrestamosController(){
        this.consultaprestamos_vista.getBtnBuscar().addActionListener(this);
        this.consultaprestamos_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == consultaprestamos_vista.getBtnBuscar()){
            //Metodo para consultar prestamos
        }
    }
}
