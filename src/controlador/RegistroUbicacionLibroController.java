package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.UbicacionLibroModelo;
import modelo.UbicacionLibro;
import vista.RegistroUbicacionLibro;

/**
 *
 * @author
 */
public class RegistroUbicacionLibroController implements ActionListener {

    private UbicacionLibroModelo ubicacionLibroModelo;
    private RegistroUbicacionLibro registroUbicacionLibroVista;

    public RegistroUbicacionLibroController(RegistroUbicacionLibro registroUbicacionLibroVista) throws SQLException {
        this.ubicacionLibroModelo = new UbicacionLibroModelo();
        this.registroUbicacionLibroVista = registroUbicacionLibroVista;
        
        // Asignar eventos a los botones
        this.registroUbicacionLibroVista.getBtnAceptar().addActionListener(this);
        
        // Mostrar la vista
        this.registroUbicacionLibroVista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }

    private void agregarDatos() {
        if (validarDatos()) {
            try {
                // Obtener datos de la vista
                int idBiblioteca = Integer.parseInt(this.registroUbicacionLibroVista.getTxt_idBiblioteca().getText().trim());
                int idLibro = Integer.parseInt(this.registroUbicacionLibroVista.getTxtLibro().getText().trim());
                String estanteria = this.registroUbicacionLibroVista.getTxtEstanteria().getText().trim();
                String seccion = this.registroUbicacionLibroVista.getTxtSeccion().getText().trim();
                String piso = this.registroUbicacionLibroVista.getTxtPiso().getText().trim();
                int cantidad = Integer.parseInt(this.registroUbicacionLibroVista.getTxtCantidad().getText().trim());
                
                // Crear objeto UbicacionLibro con los datos
                UbicacionLibro ubicacion = new UbicacionLibro(idBiblioteca, idLibro, estanteria, seccion, piso, cantidad);

                // Registrar ubicación en la base de datos
                if (ubicacionLibroModelo.registrarUbicacion(ubicacion)) {
                    JOptionPane.showMessageDialog(registroUbicacionLibroVista, "Ubicación registrada correctamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    this.registroUbicacionLibroVista.dispose();  // Cerrar la ventana después de registrar
                } else {
                    JOptionPane.showMessageDialog(registroUbicacionLibroVista, "Error al registrar la ubicación", "Error de registro", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(registroUbicacionLibroVista, "Error en el formato de los campos numéricos", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para validar que los datos de entrada en los campos son correctos.
     * @return true si los datos son válidos, false en caso contrario.
     */
    private boolean validarDatos() {
        boolean resultado = true;
        StringBuilder mensaje = new StringBuilder();
        
        if (this.registroUbicacionLibroVista.getTxtLibro().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir el ISBN del libro.\n");
            resultado = false;
        }

        if (this.registroUbicacionLibroVista.getTxtEstanteria().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir la estantería.\n");
            resultado = false;
        }

        if (this.registroUbicacionLibroVista.getTxtSeccion().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir la sección.\n");
            resultado = false;
        }

        if (this.registroUbicacionLibroVista.getTxtPiso().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir el piso.\n");
            resultado = false;
        }

        if (this.registroUbicacionLibroVista.getTxtCantidad().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir la cantidad.\n");
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registroUbicacionLibroVista, mensaje.toString(), "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
