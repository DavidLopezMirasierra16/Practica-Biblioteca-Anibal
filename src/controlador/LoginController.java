/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.Login;

/**
 *
 * @author 34662
 */
public class LoginController implements ActionListener{
 
    private Login login_vista;
    
    public LoginController() {
        //Clases
        this.login_vista = new Login();
        //Botones
        this.login_vista.getBtnIniciar().addActionListener(this);
        this.login_vista.getBtnRecuperar1().addActionListener(this);
        //----------------------------------
        this.login_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object button = e.getSource();
        
        if (button == this.login_vista.getBtnIniciar()) {
            //Controller inciar
            
        }else if (button == this.login_vista.getBtnRecuperar1()){
            //Controller recuperar cuenta
            
        }
        
    }
    
}
