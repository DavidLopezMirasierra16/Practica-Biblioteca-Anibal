package controlador;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.SocioModelo;
import vista.ModCorreoSocio;
import vista.ModCuentaBancariaSocio;

/**
 * Controlador para modificar el correo de un socio.
 */
public class ModCorreoSocioController implements ActionListener {
    private ModCorreoSocio modificarVista;
    private SocioModelo socioModelo;

    public ModCorreoSocioController() throws SQLException {
        this.modificarVista = new ModCorreoSocio();
        this.socioModelo = new SocioModelo();
        modificarVista.getBtnAceptar().addActionListener(this);
        modificarVista.setVisible(true);
        modificarVista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modificarVista.getBtnAceptar()) {
            // Llama al método para modificar el correo del socio
            socioModelo.modificarCorreo(new ModCuentaBancariaSocio());
        }
    }
}
