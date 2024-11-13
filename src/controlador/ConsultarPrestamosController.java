/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.PrestamosModelo;
import vista.ConsultarPrestamos;

/**
 *
 * @author pablo
 */
public class ConsultarPrestamosController implements ActionListener{
    private ConsultarPrestamos consultaprestamos_vista;

    public ConsultarPrestamosController() throws SQLException {
        this.consultaprestamos_vista = new ConsultarPrestamos();
        this.consultaprestamos_vista.getBtnBuscar().addActionListener(this);
        this.consultaprestamos_vista.getBtnReestablecer().addActionListener(this); // Escuchar evento reestablecer
        this.consultaprestamos_vista.setVisible(true);
        PrestamosModelo prestamosModelo = new PrestamosModelo();
        prestamosModelo.consultarPrestamos(consultaprestamos_vista.getTablaPrestamos()); // Cargar los libros completos al inicio
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultaprestamos_vista.getBtnBuscar()) {
            try {
                String filtro = consultaprestamos_vista.getCbFiltro().getSelectedItem().toString();
                String busqueda = consultaprestamos_vista.getTxtBusqueda().getText();
                PrestamosModelo prestamosModelo = new PrestamosModelo();
                prestamosModelo.filtrarLibros(consultaprestamos_vista); // Filtrar según la búsqueda
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarPrestamosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == consultaprestamos_vista.getBtnReestablecer()) {
            try {
                PrestamosModelo prestamosModelo = new PrestamosModelo();
                prestamosModelo.consultarPrestamos(consultaprestamos_vista.getTablaPrestamos()); // Reestablecer la tabla con todos los libros
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarPrestamosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(e.getSource() == consultaprestamos_vista.getBtnDevilver()){
            try {
                PrestamosModelo prestamosModelo = new PrestamosModelo();
                prestamosModelo.devolver(consultaprestamos_vista.getTablaPrestamos());
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarPrestamosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
