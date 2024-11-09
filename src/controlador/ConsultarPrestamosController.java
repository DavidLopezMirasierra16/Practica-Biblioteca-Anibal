/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.PrestamosModelo;
import vista.ConsultarPrestamos;

/**
 *
 * @author pablo
 */
public class ConsultarPrestamosController implements ActionListener{
    private ConsultarPrestamos consultalibros_vista;

    public ConsultarPrestamosController() {
        this.consultalibros_vista = new ConsultarPrestamos();
        this.consultalibros_vista.getBtnBuscar().addActionListener(this);
        this.consultalibros_vista.getBtnReestablecer().addActionListener(this); // Escuchar evento reestablecer
        this.consultalibros_vista.setVisible(true);
        PrestamosModelo prestamosModelo = new PrestamosModelo();
        prestamosModelo.consultarPrestamos(consultalibros_vista.getTablaPrestamos()); // Cargar los libros completos al inicio
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultalibros_vista.getBtnBuscar()) {
            String filtro = consultalibros_vista.getCbFiltro().getSelectedItem().toString();
            String busqueda = consultalibros_vista.getTxtBusqueda().getText();
            PrestamosModelo prestamosModelo = new PrestamosModelo();
            prestamosModelo.filtrarLibros(consultalibros_vista); // Filtrar según la búsqueda
        } else if (e.getSource() == consultalibros_vista.getBtnReestablecer()) {
            PrestamosModelo prestamosModelo = new PrestamosModelo();
            prestamosModelo.consultarPrestamos(consultalibros_vista.getTablaPrestamos()); // Reestablecer la tabla con todos los libros
        }
    }
}
