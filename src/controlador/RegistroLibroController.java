package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Libros;
import modelo.LibrosModelo;
import vista.RegistrarLibro;

/**
 * Controlador para el registro de libros.
 */
public class RegistroLibroController implements ActionListener {

    private LibrosModelo librosModelo;
    private RegistrarLibro registroLibroVista;

    public RegistroLibroController(RegistrarLibro registroLibroVista) {
        try {
            // Inicializar el modelo y la vista
            this.librosModelo = new LibrosModelo();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        this.registroLibroVista = registroLibroVista;

        // Agregar listener al botón "Agregar"
        this.registroLibroVista.getBtn_agregar().addActionListener(this);

        // Mostrar la vista de registro de libros
        this.registroLibroVista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }

    /**
     * Método para agregar datos del libro a la base de datos.
     */
    public void agregarDatos() {
        if (validarDatos()) {
            try {
                // Obtener datos desde la vista
                int isbn = Integer.parseInt(registroLibroVista.getTxt_isbn().getText());
                String titulo = registroLibroVista.getTxt_nombre().getText();
                String genero = registroLibroVista.getTxt_genero().getText();
                String año = registroLibroVista.getTxt_año().getText();
                String editorial = registroLibroVista.getTxt_editorial().getText();
                int idAutor = Integer.parseInt(registroLibroVista.getTxt_autor().getText());

                // Registrar el libro en el modelo
                Libros nuevoLibro = librosModelo.registrarLibro(isbn, titulo, genero, año, editorial, idAutor);
                
                if (nuevoLibro != null) {
                    JOptionPane.showMessageDialog(registroLibroVista, "Libro '" + titulo + "' registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    registroLibroVista.dispose();
                } else {
                    JOptionPane.showMessageDialog(registroLibroVista, "El libro ya existe en la base de datos.", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(registroLibroVista, "Error en el formato de los datos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Valida que los campos necesarios estén completados.
     * @return true si los datos son válidos, false en caso contrario.
     */
    public boolean validarDatos() {
        boolean resultado = true;
        StringBuilder mensaje = new StringBuilder(" ");

        if (registroLibroVista.getTxt_nombre().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un nombre.\n");
            resultado = false;
        }

        if (registroLibroVista.getTxt_isbn().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un ISBN.\n");
            resultado = false;
        }

        if (registroLibroVista.getTxt_genero().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un género.\n");
            resultado = false;
        }

        if (registroLibroVista.getTxt_año().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un año.\n");
            resultado = false;
        }

        if (registroLibroVista.getTxt_editorial().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir una editorial.\n");
            resultado = false;
        }

        if (registroLibroVista.getTxt_autor().getText().trim().isEmpty()) {
            mensaje.append("Debe introducir un autor.\n");
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registroLibroVista, mensaje.toString(), "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
}
