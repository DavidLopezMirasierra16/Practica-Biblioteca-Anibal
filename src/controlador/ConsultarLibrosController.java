// ConsultarLibrosController.java
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.LibrosModelo;
import vista.ConsultarLibros;

public class ConsultarLibrosController implements ActionListener {
    
    private ConsultarLibros consultalibros_vista;

    public ConsultarLibrosController() {
        this.consultalibros_vista = new ConsultarLibros();
        this.consultalibros_vista.getBtnBuscar().addActionListener(this);
        this.consultalibros_vista.getBtnReestablecer().addActionListener(this); // Escuchar evento reestablecer
        this.consultalibros_vista.setVisible(true);
        LibrosModelo librosModelo = new LibrosModelo();
        librosModelo.consultarLibros(consultalibros_vista.getTablaLibros()); // Cargar los libros completos al inicio
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultalibros_vista.getBtnBuscar()) {
            String filtro = consultalibros_vista.getCbFiltro().getSelectedItem().toString();
            String busqueda = consultalibros_vista.getTxtBusqueda().getText();
            LibrosModelo librosModelo = new LibrosModelo();
            librosModelo.filtrarLibros(consultalibros_vista); // Filtrar según la búsqueda
        } else if (e.getSource() == consultalibros_vista.getBtnReestablecer()) {
            LibrosModelo librosModelo = new LibrosModelo();
            librosModelo.consultarLibros(consultalibros_vista.getTablaLibros()); // Reestablecer la tabla con todos los libros
        }
    }
}
