package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import modelo.Libros;
import modelo.LibrosModelo;
import vista.RegistrarLibro;

/**
 * Controlador para el registro de libros.
 */
public class RegistroLibroController implements ActionListener {
    private LibrosModelo modelo_libros;
    private RegistrarLibro registro_libro_vista;

    public RegistroLibroController(RegistrarLibro registro_libro_vista) throws SQLException{
        //Clases
        this.modelo_libros = new LibrosModelo();
        this.registro_libro_vista = registro_libro_vista;
        //Botones
        this.registro_libro_vista.getBtn_agregar().addActionListener(this);
        //-------------------------------------------
        JOptionPane.showMessageDialog(registro_libro_vista, "Cuando vaya a INSERTAR el autor recuerde escribir la primera letra del NOMBRE y del APELLIDO/S en MAYÚSCULA", "Recordatorio", JOptionPane.INFORMATION_MESSAGE);
        this.registro_libro_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }
    
    public void agregarDatos(){
        
        if (validarDatos()) {
            int id = this.modelo_libros.seleccionarAutor(this.registro_libro_vista.getTxt_autor().getText());

            if (id==0) {
                JOptionPane.showMessageDialog(registro_libro_vista, "Autor no registrado", "Error autor", JOptionPane.ERROR_MESSAGE);
            }else{

                if (this.modelo_libros.crearLibro(Integer.parseInt(this.registro_libro_vista.getTxt_isbn().getText()), 
                        this.registro_libro_vista.getTxt_nombre().getText(), 
                        this.registro_libro_vista.getTxt_genero().getText(), 
                        this.registro_libro_vista.getTxt_año().getText(), 
                        this.registro_libro_vista.getTxt_editorial().getText(), id)!=null) {
                    JOptionPane.showMessageDialog(registro_libro_vista, "Libro con ISBN " + this.registro_libro_vista.getTxt_isbn().getText() + " registrado correctamente", "Libro dado de alta", JOptionPane.INFORMATION_MESSAGE);
                    this.registro_libro_vista.dispose();
                }else{
                    JOptionPane.showMessageDialog(registro_libro_vista, "Autor " + this.registro_libro_vista.getTxt_autor().getText() + " no registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);                
                }
            } 
        }   
        
    }
    
    /**
     * Funcion que nos valida si los campos estan correctamente rellenados.
     * @return 
     */
    public boolean validarDatos() {
        boolean resultado = true;
        String mensaje = " ";

        if (this.registro_libro_vista.getTxt_nombre().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.registro_libro_vista.getTxt_isbn().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir uno ISBN. \n";
            resultado = false;
        }

        if (this.registro_libro_vista.getTxt_genero().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un género. \n";
            resultado = false;
        }
        
        if (this.registro_libro_vista.getTxt_año().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un año. \n";
            resultado = false;
        }
        
        if (this.registro_libro_vista.getTxt_editorial().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir una editorial. \n";
            resultado = false;
        }
        
        if (this.registro_libro_vista.getTxt_autor().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un autor. \n";
        }
        
        return resultado;
    }
}