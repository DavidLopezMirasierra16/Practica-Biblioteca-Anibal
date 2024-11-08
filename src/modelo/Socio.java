/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author 34662
 */
public class Socio {
    
    private int ID_Socios;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String correo;
    private String fecha_alta;
    private String cuenta_banco;
    private String ID_Biblioteca;

    public Socio(int ID_Socios, String nombre, String apellidos, String direccion, String telefono, String correo, String fecha_alta, String cuenta_banco, String ID_Biblioteca) {
        this.ID_Socios = ID_Socios;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha_alta = fecha_alta;
        this.cuenta_banco = cuenta_banco;
        this.ID_Biblioteca = ID_Biblioteca;
    }

    public int getID_Socios() {
        return ID_Socios;
    }

    public void setID_Socios(int ID_Socios) {
        this.ID_Socios = ID_Socios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getCuenta_banco() {
        return cuenta_banco;
    }

    public void setCuenta_banco(String cuenta_banco) {
        this.cuenta_banco = cuenta_banco;
    }

    public String getID_Biblioteca() {
        return ID_Biblioteca;
    }

    public void setID_Biblioteca(String ID_Biblioteca) {
        this.ID_Biblioteca = ID_Biblioteca;
    }
}
