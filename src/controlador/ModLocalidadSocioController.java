/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.SocioModelo;
import vista.ModLocalidadSocio;

/**
 *
 * @author pablo
 */
public class ModLocalidadSocioController implements ActionListener{
    private ModLocalidadSocio modificar_vista;
    private SocioModelo socio;
    public ModLocalidadSocioController() throws SQLException{
        this.modificar_vista = new ModLocalidadSocio();
        this.socio = new SocioModelo();
        modificar_vista.getBtnAceptar().addActionListener(this);
        modificar_vista.setVisible(true);
        modificar_vista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == modificar_vista.getBtnAceptar()){
            socio.modificarIdBiblioteca(modificar_vista);
        }
    }
    
}
