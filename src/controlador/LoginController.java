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
import javax.swing.JOptionPane;
import modelo.TrabajadorModelo;
import vista.Login;
import vista.MenuAdministrador;
import vista.MenuAdministrativo;

/**
 *
 * @author 34662
 */
public class LoginController implements ActionListener {
    private Login login_vista;
    private TrabajadorModelo trabajador_modelo;

    public LoginController() throws SQLException {
        // Clases
        this.login_vista = new Login();
        this.trabajador_modelo = new TrabajadorModelo();
        // Botones
        this.login_vista.getBtnIniciar().addActionListener(this);
        this.login_vista.getBtnRecuperar1().addActionListener(this);
        //----------------------------------
        this.login_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();

        if (button == this.login_vista.getBtnIniciar()) {
            // Controller iniciar sesión
            int id = this.trabajador_modelo.comprobarRolFuncion(
                    Integer.parseInt(this.login_vista.getTxt_usuario().getText()),
                    this.login_vista.getTxt_contraseña().getText()
            );
            if (id > 0) {
                try {
                    mostrarVentana(id);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(login_vista, "Ese usuario no está registrado", "Error de comprobación", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (button == this.login_vista.getBtnRecuperar1()) {
            // Controller recuperar cuenta
        }
    }

    public void mostrarVentana(int id) throws SQLException {
        // Obtenemos el ID de la biblioteca y permisos
        int idPermiso = trabajador_modelo.getIdPermiso();
        int idBiblioteca = trabajador_modelo.getIdBiblioteca();

        if (idPermiso == 1) {
            new AdministradorController(new MenuAdministrador());
            this.login_vista.dispose();
        } else {
            new AdministrativoController(new MenuAdministrativo());
            this.login_vista.dispose();
        }
    }
}
