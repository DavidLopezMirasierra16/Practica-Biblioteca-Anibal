package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.SocioModelo;
import vista.ConsultarSocios;
import vista.ConsultarTrabajadores;
import vista.MenuAdministrador;
import vista.ModCorreoSocio;
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
        this.menu_administrador_vista = menu_administrador_vista;
        this.menu_administrador_vista.getBtnConsulta().addActionListener(this);
        this.menu_administrador_vista.getBtnModificar().addActionListener(this);
        this.menu_administrador_vista.getBtnModificarCorreo().addActionListener(this);
        this.menu_administrador_vista.getBtnModificarCB().addActionListener(this);
        this.menu_administrador_vista.getBtnModificarTLF().addActionListener(this);
        this.menu_administrador_vista.getBtnRegistrar().addActionListener(this);
        this.menu_administrador_vista.getBtn_registrar_trabajador().addActionListener(this);
        this.menu_administrador_vista.getBtn_consultar_trabajadores().addActionListener(this);
        this.menu_administrador_vista.getBtn_Cerrar().addActionListener(this);
        this.menu_administrador_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object button = e.getSource();
        
        if (button == this.menu_administrador_vista.getBtnRegistrar()) {
            try {
                new RegistroSocioController(new RegistroSocios());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (button == this.menu_administrador_vista.getBtnConsulta()){
            try {
                new ConsultarSociosController(new ConsultarSocios());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } else if (button == this.menu_administrador_vista.getBtnModificar()){
            try {
                new ModLocalidadSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (button == this.menu_administrador_vista.getBtn_registrar_trabajador()) {
            try {
                new RegistroTrabajadorController(new RegistroTrabajadores());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(button == this.menu_administrador_vista.getBtnModificarCorreo()){
            try {
                new ModCorreoSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(button == this.menu_administrador_vista.getBtn_consultar_trabajadores()){
            try {
                new ConsultarTrabajadoresController(new ConsultarTrabajadores());
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(button == this.menu_administrador_vista.getBtn_Cerrar()){
            this.menu_administrador_vista.dispose();
            try {
                new LoginController();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(button == this.menu_administrador_vista.getBtnModificarCB()){
            try {
                new ModCuentaBancariaSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(button == this.menu_administrador_vista.getBtnModificarTLF()){
            try {
                new ModTelefonoSocioController();
            } catch (SQLException ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
