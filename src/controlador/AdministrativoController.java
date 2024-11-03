/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.LibrosModelo;
import modelo.SocioModelo;
import vista.MenuAdministrativo;

/**
 *
 * @author 34662
 */
public class AdministrativoController implements ActionListener{

    private MenuAdministrativo menu_administrativo_vista;
    private LibrosModelo libros_modelo;
    private SocioModelo socio_modelo;

    public AdministrativoController() {
        //Clases
        this.menu_administrativo_vista = new MenuAdministrativo();
        this.libros_modelo = new LibrosModelo();
        this.socio_modelo = new SocioModelo();
        //Botones
        this.menu_administrativo_vista.getBtnLibros().addActionListener(this);
        this.menu_administrativo_vista.getBtnConsulta().addActionListener(this);
        //------------------------------------------------
        this.menu_administrativo_vista.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();
        
        if (button == this.menu_administrativo_vista.getBtnLibros()) {
            //Abre el controlador de libros
            
        }else if (button == this.menu_administrativo_vista.getBtnConsulta()){
            //Abre el controlador de consultas
            
        }
        
    }
    
}
