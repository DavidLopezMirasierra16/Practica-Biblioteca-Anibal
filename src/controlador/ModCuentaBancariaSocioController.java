package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.SocioModelo;
import vista.ModCorreoSocio;
import vista.ModCuentaBancariaSocio;

/**
 * Controlador para modificar la cuenta bancaria de un socio.
 */
public class ModCuentaBancariaSocioController implements ActionListener {
    private ModCuentaBancariaSocio modificarVista;
    private SocioModelo socioModelo;

    public ModCuentaBancariaSocioController() throws SQLException {
        this.modificarVista = new ModCuentaBancariaSocio();
        this.socioModelo = new SocioModelo();
        modificarVista.getBtnAceptar().addActionListener(this);
        modificarVista.setVisible(true);
        modificarVista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modificarVista.getBtnAceptar()) {
            // Llama al método para modificar la cuenta bancaria del socio
            socioModelo.modificarCuentaBancaria(new ModCorreoSocio());
        }
    }
}
