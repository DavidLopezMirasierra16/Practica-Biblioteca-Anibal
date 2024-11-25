package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.SancionesModelo;
import vista.ConsultarSanciones;

/**
 * Controlador para la vista de ConsultarSanciones.
 */
public class ConsultarSancionesController implements ActionListener {
    private ConsultarSanciones consultarSancionesVista;

    public ConsultarSancionesController(ConsultarSanciones consultarSancionesVista) throws SQLException {
        this.consultarSancionesVista = consultarSancionesVista;
        this.consultarSancionesVista.getBtnBuscar().addActionListener(this);
        this.consultarSancionesVista.getBtnReestablecer().addActionListener(this);
        this.consultarSancionesVista.getBtnResolver().addActionListener(this);
        this.consultarSancionesVista.setVisible(true);

        // Inicializar la tabla con todas las sanciones
        SancionesModelo sancionesModelo = new SancionesModelo();
        sancionesModelo.consultarSanciones(consultarSancionesVista.getTablaSanciones());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SancionesModelo sancionesModelo = new SancionesModelo();

            // Acción de búsqueda
            if (e.getSource() == consultarSancionesVista.getBtnBuscar()) {
                String filtro = consultarSancionesVista.getCbFiltro().getSelectedItem().toString();
                String busqueda = consultarSancionesVista.getTxtBusqueda().getText();
                sancionesModelo.filtrarSanciones(consultarSancionesVista, consultarSancionesVista.getTablaSanciones());
            }
            
            // Acción de reestablecer, muestra todas las sanciones
            else if (e.getSource() == consultarSancionesVista.getBtnReestablecer()) {
                sancionesModelo.consultarSanciones(consultarSancionesVista.getTablaSanciones());
            }
            
            // Acción de resolver la sanción
            else if (e.getSource() == consultarSancionesVista.getBtnResolver()) {
                // Lógica para resolver una sanción, si es necesario
                // Por ejemplo, podríamos cambiar el estado de una sanción en la base de datos
                System.out.println("Resolver sanción");
                // Implementar la lógica según lo que necesites
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarSancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
