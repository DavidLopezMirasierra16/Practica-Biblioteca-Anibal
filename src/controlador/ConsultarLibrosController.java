// ConsultarLibrosController.java
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.LibrosModelo;
import vista.ConsultarLibros;

public class ConsultarLibrosController implements ActionListener {
    
    private ConsultarLibros consultalibros_vista;

    public ConsultarLibrosController() throws SQLException {
        this.consultalibros_vista = new ConsultarLibros();
        this.consultalibros_vista.getBtnBuscar().addActionListener(this);
        this.consultalibros_vista.getBtnReestablecer().addActionListener(this);
        this.consultalibros_vista.setVisible(true);
        LibrosModelo librosModelo = new LibrosModelo();
        librosModelo.consultarLibros(consultalibros_vista.getTablaLibros());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultalibros_vista.getBtnBuscar()) {
            try {
                String filtro = consultalibros_vista.getCbFiltro().getSelectedItem().toString();
                String busqueda = consultalibros_vista.getTxtBusqueda().getText();
                LibrosModelo librosModelo = new LibrosModelo();
                librosModelo.filtrarLibros(consultalibros_vista);
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarLibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == consultalibros_vista.getBtnReestablecer()) {
            try {
                LibrosModelo librosModelo = new LibrosModelo();
                librosModelo.consultarLibros(consultalibros_vista.getTablaLibros());
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarLibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}