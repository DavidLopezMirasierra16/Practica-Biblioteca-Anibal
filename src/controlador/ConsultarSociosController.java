/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.SocioModelo;
import vista.ConsultarSocios;

/**
 * Controlador para la vista de ConsultarSocios.
 */
public class ConsultarSociosController implements ActionListener {
    private ConsultarSocios consultasocios_vista;

    public ConsultarSociosController() {
        this.consultasocios_vista = new ConsultarSocios();
        this.consultasocios_vista.getBtnBuscar().addActionListener(this);
        this.consultasocios_vista.getBtnReestablecer().addActionListener(this);
        this.consultasocios_vista.setVisible(true);
        
        SocioModelo sociosModelo = new SocioModelo();
        sociosModelo.consultarSocios(consultasocios_vista.getTablaSocios());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SocioModelo sociosModelo = new SocioModelo();
        if (e.getSource() == consultasocios_vista.getBtnBuscar()) {
            String filtro = consultasocios_vista.getCbFiltro().getSelectedItem().toString();
            String busqueda = consultasocios_vista.getTxtBusqueda().getText();
            sociosModelo.filtrarSocios(consultasocios_vista, consultasocios_vista.getTablaSocios());
        } else if (e.getSource() == consultasocios_vista.getBtnReestablecer()) {
            sociosModelo.consultarSocios(consultasocios_vista.getTablaSocios());
        }
    }
}
