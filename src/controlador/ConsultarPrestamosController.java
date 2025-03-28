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
import javax.swing.JOptionPane;
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
        this.consultaprestamos_vista.getBtnReestablecer().addActionListener(this);
        this.consultaprestamos_vista.getBtnDevilver().addActionListener(this);
        this.consultaprestamos_vista.setVisible(true);
        PrestamosModelo prestamosModelo = new PrestamosModelo();
        prestamosModelo.consultarPrestamos(consultaprestamos_vista.getTablaPrestamos());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultaprestamos_vista.getBtnBuscar()) {
            try {
                String filtro = consultaprestamos_vista.getCbFiltro().getSelectedItem().toString();
                String busqueda = consultaprestamos_vista.getTxtBusqueda().getText();
                PrestamosModelo prestamosModelo = new PrestamosModelo();
                prestamosModelo.filtrarLibros(consultaprestamos_vista);
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarPrestamosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == consultaprestamos_vista.getBtnReestablecer()) {
            try {
                PrestamosModelo prestamosModelo = new PrestamosModelo();
                prestamosModelo.consultarPrestamos(consultaprestamos_vista.getTablaPrestamos());
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarPrestamosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(e.getSource() == consultaprestamos_vista.getBtnDevilver()){
            try {
                int confirm = JOptionPane.showConfirmDialog(null,"¿Estás seguro de que deseas devolver el préstamo indicado?","Confirmar",JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    PrestamosModelo prestamosModelo = new PrestamosModelo();
                    prestamosModelo.devolver(consultaprestamos_vista.getTablaPrestamos());
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarPrestamosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
