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
                // Obtener el ID del trabajador y la contraseña desde la vista
                int usuarioId = Integer.parseInt(this.login_vista.getTxt_usuario().getText().trim());
                String contraseña = this.login_vista.getTxt_contraseña().getText().trim();

                // Comprobar el ID del trabajador y la contraseña
                int idTrabajador = this.trabajador_modelo.comprobarRolFuncion(usuarioId, contraseña);
                if (idTrabajador > 0) {
                    // Comprobar el estado del trabajador (habilitado o deshabilitado)
                    String estado = trabajador_modelo.comprobarEstadoTrabajador(idTrabajador);

                    if ("Habilitado".equals(estado)) {
                        // Si el trabajador está habilitado, mostramos la ventana correspondiente
                        int idPermiso = trabajador_modelo.getIdPermiso();
                        int idBiblioteca = trabajador_modelo.getIdBiblioteca();
                        System.out.println("ID Trabajador: " + idTrabajador);
                        System.out.println("ID Permiso: " + idPermiso);
                        System.out.println("ID Biblioteca: " + idBiblioteca);
                        mostrarVentana(idPermiso);
                    } else {
                        // Si el trabajador está deshabilitado, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(login_vista, "Tu cuenta está deshabilitada. No puedes iniciar sesión.", 
                                                      "Error de acceso", JOptionPane.ERROR_MESSAGE);
                    }
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
                this.login_vista.dispose();
                new RecuperarContrasenaController(new RecuperarContrasena());
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * En función del id que le pasemos, busca una ventana u otra
     * @param id El ID de permiso del trabajador
     * @throws SQLException Si ocurre un error en la base de datos
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
