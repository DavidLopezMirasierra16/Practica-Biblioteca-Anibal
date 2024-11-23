package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.SancionesModelo;
import vista.ConsultarSanciones;

/**
 *
 * @author pablo
 */
public class ConsultarSancionesController implements ActionListener {
    private ConsultarSanciones consultasanciones_vista;

    public ConsultarSancionesController() throws SQLException {
        this.consultasanciones_vista = new ConsultarSanciones();
        this.consultasanciones_vista.getBtnResolver().addActionListener(this); // Escuchar evento para resolver sanción
        this.consultasanciones_vista.setVisible(true);

        // Cargar todas las sanciones al iniciar la vista
        SancionesModelo sancionesModelo = new SancionesModelo();
        sancionesModelo.consultarSanciones(consultasanciones_vista.getTablaSanciones());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultasanciones_vista.getBtnResolver()) {
            try {
                // Resolver la sanción seleccionada
                SancionesModelo sancionesModelo = new SancionesModelo();
                sancionesModelo.resolverSancion(consultasanciones_vista); // Manejar resolución desde el modelo
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarSancionesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
