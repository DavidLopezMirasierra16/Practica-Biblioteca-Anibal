/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.RegistrarLibro;

/**
 *
 * @author pablo
 */
public class RegistroLibroController implements ActionListener{
    private RegistrarLibro registrolibro_vista;
    public RegistroLibroController(){
        this.registrolibro_vista = new RegistrarLibro();
        this.registrolibro_vista.getBtn_agregar().addActionListener(this);
        this.registrolibro_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registrolibro_vista.getBtn_agregar()){
            //Metodo que registra autor
        }
    }
}
