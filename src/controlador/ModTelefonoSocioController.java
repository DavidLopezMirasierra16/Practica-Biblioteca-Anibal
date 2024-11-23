package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.SocioModelo;
import vista.ModTelefonoSocio;

/**
 * Controlador para modificar el teléfono de un socio.
 */
public class ModTelefonoSocioController implements ActionListener {
    private ModTelefonoSocio modificarVista;
    private SocioModelo socioModelo;

    public ModTelefonoSocioController() throws SQLException {
        this.modificarVista = new ModTelefonoSocio();
        this.socioModelo = new SocioModelo();
        modificarVista.getBtnAceptar().addActionListener(this);
        modificarVista.setVisible(true);
        modificarVista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modificarVista.getBtnAceptar()) {
            // Llama al método para modificar el teléfono del socio
            socioModelo.modificarTelefono(new ModTelefonoSocio());
        }
    }
}
