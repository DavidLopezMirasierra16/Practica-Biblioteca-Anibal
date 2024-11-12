/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.AutorModelo;
import vista.RegistrarAutor;

/**
 *
 * @author pablo
 */
public class RegistroAutorController implements ActionListener{
    
    private AutorModelo autor_modelo;
    private RegistrarAutor registro_autor_vista;

    public RegistroAutorController(RegistrarAutor registro_autor_vista) throws SQLException {
        //Clases
        this.autor_modelo = new AutorModelo();
        this.registro_autor_vista = registro_autor_vista;
        //------------------------------------------------------------------
        this.registro_autor_vista.getBtn_agregar().addActionListener(this);
        //-------------------------------------------
        this.registro_autor_vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        agregarDatos();
    }
    
    public void agregarDatos(){
        
        if (validarDatos()) {
            
            if (this.autor_modelo.crearAutor(this.registro_autor_vista.getTxt_nombre().getText(), 
                    this.registro_autor_vista.getTxt_nacimiento().getText(), 
                    this.registro_autor_vista.getTxt_correo().getText()) != null) {
                JOptionPane.showMessageDialog(registro_autor_vista, "Autor " + this.registro_autor_vista.getTxt_nombre().getText() + " registrado correctamente", "Autor dado de alta", JOptionPane.INFORMATION_MESSAGE);
                this.registro_autor_vista.dispose();
            }else{
                JOptionPane.showMessageDialog(registro_autor_vista, "Autor " + this.registro_autor_vista.getTxt_nombre().getText() + " ya registrado", "Error de registro", JOptionPane.ERROR_MESSAGE);                

            }
            
        }
        
    }
    
    public boolean validarDatos() {
        boolean resultado = true;
        String mensaje = " ";

        if (this.registro_autor_vista.getTxt_nombre().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir un nombre. \n";
            resultado = false;
        }

        if (this.registro_autor_vista.getTxt_nacimiento().getText().trim().length() == 0) {
            mensaje = mensaje + "Debe introducir uno nacimiento. \n";
            resultado = false;
        }

        if (!resultado) {
            JOptionPane.showMessageDialog(registro_autor_vista, mensaje, "Faltan datos", JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
    
}
