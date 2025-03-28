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
import modelo.SocioModelo;
import vista.ConsultarSocios;

/**
 * Controlador para la vista de ConsultarSocios.
 */
public class ConsultarSociosController implements ActionListener {
    private ConsultarSocios consultasocios_vista;
    private SocioModelo sociosModelo;

    public ConsultarSociosController(ConsultarSocios consultasocios_vista) throws SQLException {
        this.consultasocios_vista = consultasocios_vista;
        this.sociosModelo = new SocioModelo();
        this.consultasocios_vista.getBtnBuscar().addActionListener(this);
        this.consultasocios_vista.getBtnReestablecer().addActionListener(this);
        this.consultasocios_vista.getBtnPago().addActionListener(this);
        sociosModelo.consultarSocios(consultasocios_vista.getTablaSocios());
        
        this.consultasocios_vista.setVisible(true);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultasocios_vista.getBtnBuscar()) {
            String filtro = consultasocios_vista.getCbFiltro().getSelectedItem().toString();
            String busqueda = consultasocios_vista.getTxtBusqueda().getText();
            sociosModelo.filtrarSocios(consultasocios_vista, consultasocios_vista.getTablaSocios());
            
        } else if (e.getSource() == consultasocios_vista.getBtnReestablecer()) {
            sociosModelo.consultarSocios(consultasocios_vista.getTablaSocios());
            
        } else if (e.getSource() == consultasocios_vista.getBtnPago()) {
            sociosModelo.cambiarEstadoPago(consultasocios_vista);
        }
    }
}
