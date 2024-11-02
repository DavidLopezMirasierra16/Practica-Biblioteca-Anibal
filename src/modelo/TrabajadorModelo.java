/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author pablo
 */
public class TrabajadorModelo {
    
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String correo;
    private String cuenta_banco;
    private String seguridad_social;
    private String localidad;

    public TrabajadorModelo(String nombre, String apellido, String fecha_nacimiento, String correo, String cuenta_banco, String seguridad_social, String localidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.correo = correo;
        this.cuenta_banco = cuenta_banco;
        this.seguridad_social = seguridad_social;
        this.localidad = localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCuenta_banco() {
        return cuenta_banco;
    }

    public void setCuenta_banco(String cuenta_banco) {
        this.cuenta_banco = cuenta_banco;
    }

    public String getSeguridad_social() {
        return seguridad_social;
    }

    public void setSeguridad_social(String seguridad_social) {
        this.seguridad_social = seguridad_social;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    
    
}
