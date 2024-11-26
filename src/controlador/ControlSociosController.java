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
import vista.ConsultarSocios;
import vista.ControlSocios;
import vista.MenuAdministrador;
import vista.ModTelefonoSocio;
import vista.RegistroSocios;

/**
 *
 * @author pablo
 */
public class ControlSociosController implements ActionListener{
    private ControlSocios controlsocios_vista;
    
    public ControlSociosController(ControlSocios controlSocios_vista){
        this.controlsocios_vista = new ControlSocios();
        this.controlsocios_vista.getBtnConsulta().addActionListener(this);
        this.controlsocios_vista.getBtnModificar().addActionListener(this);
        this.controlsocios_vista.getBtnModificarCB().addActionListener(this);
        this.controlsocios_vista.getBtnModificarCorreo().addActionListener(this);
        this.controlsocios_vista.getBtnModificarTLF().addActionListener(this);
        this.controlsocios_vista.getBtnRegistrar().addActionListener(this);
        this.controlsocios_vista.getBtnVolver().addActionListener(this);
        this.controlsocios_vista.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.controlsocios_vista.getBtnConsulta()){
            try {
                new ConsultarSociosController(new ConsultarSocios());
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.controlsocios_vista.getBtnModificar()){
            try {
                new ModLocalidadSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.controlsocios_vista.getBtnModificarCB()){
            try {
                new ModCuentaBancariaSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.controlsocios_vista.getBtnModificarCorreo()){
            try {
                new ModCorreoSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.controlsocios_vista.getBtnModificarTLF()){
            try {
                new ModTelefonoSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.controlsocios_vista.getBtnRegistrar()){
            try {
                new RegistroSocioController(new RegistroSocios());
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.controlsocios_vista.getBtnVolver()){
            this.controlsocios_vista.dispose();
            try {
                new AdministradorController(new MenuAdministrador());
            } catch (SQLException ex) {
                Logger.getLogger(ControlSociosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
