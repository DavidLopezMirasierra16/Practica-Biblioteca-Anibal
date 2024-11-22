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
import modelo.SocioModelo;
import vista.ConsultarSocios;
import vista.MenuAdministrador;
import vista.RegistroSocios;
import vista.RegistroTrabajadores;

/**
 *
 * @author 34662
 */
public class AdministradorController implements ActionListener{
    
    private MenuAdministrador menu_administrador_vista;
    private SocioModelo socio_modelo;

    public AdministradorController(MenuAdministrador menu_administrador_vista/*, SocioModelo socio_modelo*/) throws SQLException {
        //Clases
        this.menu_administrador_vista = menu_administrador_vista;
        //this.socio_modelo = new SocioModelo();
        //Botones
        this.menu_administrador_vista.getBtnConsulta().addActionListener(this);
        this.menu_administrador_vista.getBtnModificar().addActionListener(this);
        this.menu_administrador_vista.getBtnRegistrar().addActionListener(this);
        this.menu_administrador_vista.getBtn_registrar_trabajador().addActionListener(this);
        //-----------------------------------------------
        this.menu_administrador_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object button = e.getSource();
        
        if (button == this.menu_administrador_vista.getBtnRegistrar()) {
            try {
                //COntrolador de registrar usuario
                new RegistroSocioController(new RegistroSocios());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (button == this.menu_administrador_vista.getBtnConsulta()){
            try {
                //Controller consultar socios
                new ConsultarSociosController(new ConsultarSocios());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else if (button == this.menu_administrador_vista.getBtnModificar()){
            try {
                //Controller editar socio
                new ModLocalidadSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (button == this.menu_administrador_vista.getBtn_registrar_trabajador()) {
            try {
                //Controller registrar trabajador
                new RegistroTrabajadorController(new RegistroTrabajadores());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    
}
