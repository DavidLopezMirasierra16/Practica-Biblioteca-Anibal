/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.TrabajadorModelo;
import vista.Login;
import vista.MenuAdministrador;
import vista.MenuAdministrativo;
import vista.RecuperarContrasena;

/**
 *
 * @author 34662
 */
public class LoginController implements ActionListener {
    private Login login_vista;
    private TrabajadorModelo trabajador_modelo;

    public LoginController() throws SQLException {
        this.login_vista = new Login();
        this.trabajador_modelo = new TrabajadorModelo();
        this.login_vista.getBtnIniciar().addActionListener(this);
        this.login_vista.getBtnRecuperar1().addActionListener(this);
        this.login_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();

        if (button == this.login_vista.getBtnIniciar()) {
            try {
                int usuarioId = Integer.parseInt(this.login_vista.getTxt_usuario().getText().trim());
                String contraseña = this.login_vista.getTxt_contraseña().getText().trim();
                int idTrabajador = this.trabajador_modelo.comprobarRolFuncion(usuarioId, contraseña);
                if (idTrabajador > 0) {
                    int idPermiso = trabajador_modelo.getIdPermiso();
                    int idBiblioteca = trabajador_modelo.getIdBiblioteca();
                    System.out.println("ID Trabajador: " + idTrabajador);
                    System.out.println("ID Permiso: " + idPermiso);
                    System.out.println("ID Biblioteca: " + idBiblioteca);
                    mostrarVentana(idPermiso);
                } else {
                    JOptionPane.showMessageDialog(login_vista, "Ese usuario no está registrado", "Error de comprobación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(login_vista, "Por favor, ingrese un usuario válido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (button == this.login_vista.getBtnRecuperar1()) {
            try {
                new RecuperarContrasenaController(new RecuperarContrasena());
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * En funcion del id que le pasemos nos busca una ventana u otra
     * @param id
     * @throws SQLException 
     */
    public void mostrarVentana(int id) throws SQLException {
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
