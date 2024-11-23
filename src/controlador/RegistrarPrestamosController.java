package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Prestamos;
import modelo.PrestamosModelo;
import modelo.TrabajadorModelo;
import vista.RegistroPrestamo;

/**
 * Controlador para el registro de préstamos en la biblioteca.
 * Gestiona la interacción entre la vista RegistroPrestamo y el modelo PrestamosModelo.
 * Implementa ActionListener para capturar eventos del formulario.
 * 
 * @author pablo
 */
public class RegistrarPrestamosController implements ActionListener {

    private PrestamosModelo prestamosModelo;
    private RegistroPrestamo registroPrestamoVista;
    private TrabajadorModelo trabajador;

    public RegistrarPrestamosController(RegistroPrestamo registroPrestamoVista) throws SQLException {
        // Inicializa las clases
        this.prestamosModelo = new PrestamosModelo();
        this.trabajador = new TrabajadorModelo();
        this.registroPrestamoVista = registroPrestamoVista;
        // Asigna el evento al botón
        this.registroPrestamoVista.getBtnAceptar().addActionListener(this);
        this.registroPrestamoVista.getTxt_prestamo().setText(this.prestamosModelo.fecha());
        // Muestra la vista de registro de préstamos
        this.registroPrestamoVista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        registrarPrestamo();
    }

    /**
     * Método para registrar un préstamo en la base de datos.
     * Valida los datos ingresados y utiliza el modelo para registrar el préstamo.
     */
    public void registrarPrestamo() {
        if (validarDatos()) {
            try {
                // Obtener los datos de la vista
                String idSocio = this.registroPrestamoVista.getTxtDNI().getText().trim();
                int idLibro = Integer.parseInt(this.registroPrestamoVista.getTxtISBN().getText().trim());
                int biblioteca = trabajador.getIdBiblioteca();
                
                // Verificar si el socio puede realizar más préstamos
                if (!prestamosModelo.comprobarLimitePrestamos(idSocio)) {
                    JOptionPane.showMessageDialog(
                        registroPrestamoVista, 
                        "El socio ha alcanzado el límite máximo de préstamos permitidos.", 
                        "Límite de préstamos alcanzado", 
                        JOptionPane.WARNING_MESSAGE
                    );
                    return; // Salimos del método si no puede realizar más préstamos
                }

                // Obtener la fecha de préstamo actual
                Date fechaPrestamo = new Date();

                // Crear el objeto Prestamos con los datos
                Prestamos prestamo = new Prestamos(idLibro, idSocio, biblioteca, fechaPrestamo);

                // Intentar registrar el préstamo
                Prestamos prestamoRegistrado = this.prestamosModelo.registrarPrestamo(idLibro, idSocio, biblioteca, fechaPrestamo);

                if (prestamoRegistrado != null) {
                    JOptionPane.showMessageDialog(
                        registroPrestamoVista, 
                        "Préstamo registrado correctamente.", 
                        "Registro exitoso", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        registroPrestamoVista, 
                        "No se pudo registrar el préstamo. Verifique la disponibilidad del libro o el límite de préstamos del socio.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    registroPrestamoVista, 
                    "ISBN debe ser valores numéricos.", 
                    "Error de formato", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                // Agregar un mensaje de registro detallado para rastrear errores
                JOptionPane.showMessageDialog(
                    registroPrestamoVista, 
                    "Error al registrar el préstamo: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }

    /**
     * Valida los datos ingresados en el formulario de préstamos.
     * Verifica que los campos requeridos no estén vacíos y muestra un mensaje en caso de errores.
     * 
     * @return true si los datos son válidos; false en caso contrario.
     */
    public boolean validarDatos() {
        boolean resultado = true;
        StringBuilder mensaje = new StringBuilder();

        if (this.registroPrestamoVista.getTxtDNI().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un DNI del socio.\n");
            resultado = false;
        }

        if (this.registroPrestamoVista.getTxtISBN().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un ISBN del libro.\n");
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registroPrestamoVista, mensaje.toString(), "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
