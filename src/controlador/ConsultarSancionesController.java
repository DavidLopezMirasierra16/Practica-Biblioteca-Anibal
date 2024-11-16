/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.SancionesModelo;
import vista.ConsultarSanciones;

/**
 *
 * @author pablo
 */
public class ConsultarSancionesController{
    private ConsultarSanciones consultasanciones_vista;
    public ConsultarSancionesController() throws SQLException {
        this.consultasanciones_vista = new ConsultarSanciones();
        this.consultasanciones_vista.setVisible(true);
        SancionesModelo sancionesModelo = new SancionesModelo();
        sancionesModelo.consultarSanciones(consultasanciones_vista.getTablaSanciones()); // Cargar los libros completos al inicio
    }
}
