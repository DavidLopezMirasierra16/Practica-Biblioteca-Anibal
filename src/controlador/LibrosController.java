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
import javax.swing.JFrame;
import modelo.UbicacionLibroModelo;
import vista.ControlLibros;
import vista.MenuAdministrativo;
import vista.RegistrarAutor;
import vista.RegistrarLibro;
import vista.RegistroUbicacionLibro;

/**
 *
 * @author 34662
 */
public class LibrosController implements ActionListener{
    
    private ControlLibros menu_libros_vista;

    public LibrosController(ControlLibros menu_libros_vista) {
        //Clases
        this.menu_libros_vista = menu_libros_vista;
        //Botones
        this.menu_libros_vista.getBtn_registrar_libros().addActionListener(this);
        this.menu_libros_vista.getBtn_consulta_libros().addActionListener(this);
        this.menu_libros_vista.getBtn_consulta_sanciones().addActionListener(this);
        this.menu_libros_vista.getBtn_registrar_autor().addActionListener(this);
        this.menu_libros_vista.getBtn_consultar_prestamos().addActionListener(this);
        this.menu_libros_vista.getBtn_registrar_librosbiblio().addActionListener(this);
        this.menu_libros_vista.getBtnVolver().addActionListener(this);
        //----------------------------------------
        this.menu_libros_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object button = e.getSource();
        
        if (button == this.menu_libros_vista.getBtn_registrar_libros()) {
            try {
                new RegistroLibroController(new RegistrarLibro());
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (button == this.menu_libros_vista.getBtn_consulta_libros()) {
            try {
                new ConsultarLibrosController();
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (button == this.menu_libros_vista.getBtn_consulta_sanciones()) {
            try {
                new ConsultarSancionesController();
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (button == this.menu_libros_vista.getBtn_registrar_autor()) {
            try {
                new RegistroAutorController(new RegistrarAutor());
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (button == this.menu_libros_vista.getBtn_consultar_prestamos()) {
            try {
                new ConsultarPrestamosController();
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(button == this.menu_libros_vista.getBtn_registrar_librosbiblio()){
            try {
                new RegistroUbicacionLibroController(new RegistroUbicacionLibro());
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(button == this.menu_libros_vista.getBtnVolver()){
            this.menu_libros_vista.dispose();
            try {
                new AdministrativoController(new MenuAdministrativo());
            } catch (SQLException ex) {
                Logger.getLogger(LibrosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
