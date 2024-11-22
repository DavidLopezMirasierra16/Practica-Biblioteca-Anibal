/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.LibrosModelo;
import modelo.SocioModelo;
import vista.ConsultarSocios;
import vista.ControlLibros;
import vista.MenuAdministrativo;
import vista.RegistroSocios;

/**
 *
 * @author 34662
 */
public class AdministrativoController implements ActionListener{

    private MenuAdministrativo menu_administrativo_vista;
    private LibrosModelo libros_modelo;
    private SocioModelo socio_modelo;

    public AdministrativoController(MenuAdministrativo menu_administrativo_vista) throws SQLException {
        //Clases
        this.menu_administrativo_vista = menu_administrativo_vista;
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
            new LibrosController(new ControlLibros());
            this.menu_administrativo_vista.dispose();
        }else if (button == this.menu_administrativo_vista.getBtnConsulta()){
            try {
                //Abre el controlador de consultas
                new ConsultarSociosController(new ConsultarSocios());
            } catch (SQLException ex) {
                Logger.getLogger(AdministrativoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
}
