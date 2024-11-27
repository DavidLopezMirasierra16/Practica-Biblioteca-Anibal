package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.TrabajadorModelo;
import vista.ConsultarTrabajadores; // Asegúrate de tener la vista de ConsultarTrabajadores

public class ConsultarTrabajadoresController implements ActionListener {
    private TrabajadorModelo trabajador_modelo;
    private ConsultarTrabajadores consultarTrabajadoresVista;

    public ConsultarTrabajadoresController(ConsultarTrabajadores consultarTrabajadores) throws SQLException {
        this.trabajador_modelo = new TrabajadorModelo();
        this.consultarTrabajadoresVista = consultarTrabajadores;
        this.consultarTrabajadoresVista.getBtnHabilitar().addActionListener(this);
        this.consultarTrabajadoresVista.getBtnBuscar().addActionListener(this);
        this.consultarTrabajadoresVista.getBtnReestablecer().addActionListener(this);
        this.consultarTrabajadoresVista.setVisible(true);
        this.consultarTrabajadoresVista.setResizable(false);

        // Cargar los trabajadores sin filtro inicialmente
        trabajador_modelo.consultarTrabajadores(consultarTrabajadoresVista.getTablaTrabajadores());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Filtrado cuando el usuario hace clic en el botón de búsqueda
        if (e.getSource() == consultarTrabajadoresVista.getBtnBuscar()) {
            String busqueda = consultarTrabajadoresVista.getTxtBusqueda().getText().trim();
            String filtro = (String) consultarTrabajadoresVista.getCbFiltro().getSelectedItem();
            if (!busqueda.isEmpty() && !filtro.equals("Seleccione una opción")) {
                // Obtener el ID de la biblioteca
                int idBiblioteca = trabajador_modelo.getIdBiblioteca(); // Asegúrate de tener este método en tu modelo
                // Llamar al método filtrarTrabajadores pasándole los parámetros correctos
                trabajador_modelo.filtrarTrabajadores(busqueda, consultarTrabajadoresVista.getTablaTrabajadores());
            } else {
                System.out.println("Debe ingresar un valor de búsqueda válido");
            }
        }
        // Reestablecer la tabla cuando se presiona el botón de reestablecer
        else if (e.getSource() == consultarTrabajadoresVista.getBtnReestablecer()) {
            trabajador_modelo.consultarTrabajadores(consultarTrabajadoresVista.getTablaTrabajadores());
        }
        // Habilitar/deshabilitar trabajador (esto sigue igual)
        else if (e.getSource() == consultarTrabajadoresVista.getBtnHabilitar()) {
            trabajador_modelo.cambiarEstadoHabilitado(consultarTrabajadoresVista);
        }
    }
}
