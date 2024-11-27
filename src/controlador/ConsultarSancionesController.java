package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
        SancionesModelo sancionesModelo = new SancionesModelo();
        sancionesModelo.consultarSanciones(consultarSancionesVista.getTablaSanciones());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SancionesModelo sancionesModelo = new SancionesModelo();
            if (e.getSource() == consultarSancionesVista.getBtnBuscar()) {
                String filtro = consultarSancionesVista.getCbFiltro().getSelectedItem().toString();
                String busqueda = consultarSancionesVista.getTxtBusqueda().getText();
                sancionesModelo.filtrarSanciones(consultarSancionesVista, consultarSancionesVista.getTablaSanciones());
            }            
            else if (e.getSource() == consultarSancionesVista.getBtnReestablecer()) {
                sancionesModelo.consultarSanciones(consultarSancionesVista.getTablaSanciones());
            }            
            else if (e.getSource() == consultarSancionesVista.getBtnResolver()) {
                int confirm = JOptionPane.showConfirmDialog(null,"¿Estás seguro de que deseas resolver la sancion indicada?","Confirmar",JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    sancionesModelo.resolverSancion(consultarSancionesVista);
                    JOptionPane.showMessageDialog(null, "Sanción solucionada correctamente");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarSancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
