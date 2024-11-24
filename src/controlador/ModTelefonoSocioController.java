/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import modelo.SocioModelo;
import vista.ModCorreoSocio;
import vista.ModTelefonoSocio;

/**
 * Controlador para la funcionalidad de modificar el correo de un socio.
 */
public class ModTelefonoSocioController implements ActionListener {
    private final ModTelefonoSocio modificarVista; // Vista para modificar el correo
    private final SocioModelo socioModelo;      // Modelo para las operaciones de socio

    /**
     * Constructor del controlador.
     * Inicializa la vista, el modelo y configura los eventos.
     *
     * @throws SQLException si ocurre algún error al conectar con la base de datos
     */
    public ModTelefonoSocioController() throws SQLException {
        this.modificarVista = new ModTelefonoSocio();
        this.socioModelo = new SocioModelo();

        // Vincular el botón "Aceptar" con este controlador
        modificarVista.getBtnAceptar().addActionListener(this);

        // Configurar y mostrar la ventana
        modificarVista.setVisible(true);
        modificarVista.setResizable(false);
    }

    /**
     * Maneja los eventos de los elementos interactivos de la vista.
     *
     * @param e evento generado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Detectar si el evento proviene del botón "Aceptar"
        if (e.getSource() == modificarVista.getBtnAceptar()) {
            // Llamar al método para modificar el correo en el modelo
            socioModelo.modificarTelefono(modificarVista);
        }
    }
}
