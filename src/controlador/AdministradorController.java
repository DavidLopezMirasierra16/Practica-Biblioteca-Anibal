/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.SocioModelo;
import vista.MenuAdministrador;

/**
 *
 * @author 34662
 */
public class AdministradorController implements ActionListener{
    
    private MenuAdministrador menu_administrador_vista;
    private SocioModelo socio_modelo;

    public AdministradorController(MenuAdministrador menu_administrador_vista, SocioModelo socio_modelo) {
        //Clases
        this.menu_administrador_vista = new MenuAdministrador();
        this.socio_modelo = new SocioModelo();
        //Botones
        this.menu_administrador_vista.getBtnConsulta().addActionListener(this);
        this.menu_administrador_vista.getBtnModificar().addActionListener(this);
        this.menu_administrador_vista.getBtnRegistrar().addActionListener(this);
        //-----------------------------------------------
        this.menu_administrador_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object button = e.getSource();
        
        if (button == this.menu_administrador_vista.getBtnRegistrar()) {
            //COntrolador de registrar usuario
            
        }else if (button == this.menu_administrador_vista.getBtnConsulta()){
            //Controller consultar socios
            
        }else if (button == this.menu_administrador_vista.getBtnModificar()){
            //Controller editar socio
            
        }
        
    }
    
    
    
}
