/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.SancionesModelo;
import modelo.Sanciones;
import vista.RegistroSanciones;

/**
 * Controlador para el registro de sanciones en la biblioteca.
 * Gestiona la interacción entre la vista RegistroSanciones y el modelo SancionesModelo.
 * Implementa ActionListener para capturar eventos del formulario.
 * 
 * @author pablo
 */
public class RegistroSancionesController implements ActionListener {
    
    private SancionesModelo sancion_modelo;
    private RegistroSanciones registro_sancion_vista;

    public RegistroSancionesController(RegistroSanciones registro_sancion_vista) throws SQLException {
        this.sancion_modelo = new SancionesModelo();
        this.registro_sancion_vista = registro_sancion_vista;
        this.registro_sancion_vista.getBtn_Aceptar().addActionListener(this);
        this.registro_sancion_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarSancion();
    }

    /**
     * Método para agregar una sanción en la base de datos.
     * Valida los datos ingresados y utiliza el modelo para registrar la sanción.
     */
    public void agregarSancion() {
        
        if (validarDatos()) {
            try {
                Sanciones sancion = new Sanciones(
                        this.registro_sancion_vista.getTxt_Socio().getText(),
                        Integer.parseInt(this.registro_sancion_vista.getTxt_Prestamo().getText()),
                        this.registro_sancion_vista.getTxt_Sancion().getText()
                );
                this.sancion_modelo.ingresarSancionBD(sancion);
                JOptionPane.showMessageDialog(registro_sancion_vista, "Sanción registrada correctamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(registro_sancion_vista, "ID de Préstamo debe ser numérico.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Valida los datos ingresados en el formulario de sanciones.
     * Verifica que los campos requeridos no estén vacíos y muestra un mensaje en caso de errores.
     * 
     * @return true si los datos son válidos; false de lo contrario.
     */
    public boolean validarDatos() {
        boolean resultado = true;
        StringBuilder mensaje = new StringBuilder();

        if (this.registro_sancion_vista.getTxt_Socio().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un ID de Socio.\n");
            resultado = false;
        }

        if (this.registro_sancion_vista.getTxt_Prestamo().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un ID de Préstamo.\n");
            resultado = false;
        }

        if (this.registro_sancion_vista.getTxt_Sancion().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir el tipo de sanción.\n");
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registro_sancion_vista, mensaje.toString(), "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
