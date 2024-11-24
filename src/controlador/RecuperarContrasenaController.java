package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.TrabajadorModelo;
import vista.RecuperarContrasena;

public class RecuperarContrasenaController implements ActionListener {
    
    private RecuperarContrasena recuperar_vista;
    private TrabajadorModelo modelo;

    public RecuperarContrasenaController(RecuperarContrasena recuperar_vista) throws SQLException {
        this.modelo = new TrabajadorModelo();
        this.recuperar_vista = recuperar_vista;
        this.recuperar_vista.getBtnRecuperar().addActionListener(this);
        this.recuperar_vista.getBtnVolver().addActionListener(this);
        this.recuperar_vista.setVisible(true);
        this.recuperar_vista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();       
        if (button == recuperar_vista.getBtnRecuperar()) {
            try {
                cambiarContraseña();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RecuperarContrasenaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        if (e.getSource() == recuperar_vista.getBtnVolver()) {
            recuperar_vista.dispose();
            try {
                new LoginController();
            } catch (SQLException ex) {
                Logger.getLogger(RecuperarContrasenaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Función que nos cambia la contraseña de un usuario
     */
    public void cambiarContraseña() throws NoSuchAlgorithmException {
        if (validarDatos()) {
            String usuario = recuperar_vista.getTxt_usuario().getText().trim();
            String nuevaContraseña = recuperar_vista.getTxt_contraseña().getText().trim();
            this.modelo.cambiarContraseña(usuario, nuevaContraseña);
        }
    }

    /**
     * Función que nos valida los campos
     * @return 
     */
    public boolean validarDatos() {
        boolean resultado = true;
        String mensaje = " ";

        if (this.recuperar_vista.getTxt_usuario().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.recuperar_vista.getTxt_contraseña().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una contraseña nueva. \n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(recuperar_vista, mensaje, "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
