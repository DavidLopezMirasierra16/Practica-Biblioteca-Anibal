/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author pablo
 */
public class Trabajador {
    
    private int id_permiso;
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String fecha_nacimiento;
    private String correo;
    private String cuenta_banco;
    private String seguridad_social;
    private String localidad;
    private String contraseña;
    private String biblioteca;

    /**
     * Constructor para consultar
     * @param id_permiso
     * @param id
     * @param nombre
     * @param apellido
     * @param dni
     * @param fecha_nacimiento
     * @param correo
     * @param cuenta_banco
     * @param seguridad_social
     * @param localidad
     * @param contraseña
     * @param biblioteca 
     */
    public Trabajador(int id_permiso, int id, String nombre, String apellido, String dni, String fecha_nacimiento, String correo, String cuenta_banco, String seguridad_social, String localidad, String contraseña, String biblioteca) {
        this.id_permiso = id_permiso;
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.correo = correo;
        this.cuenta_banco = cuenta_banco;
        this.seguridad_social = seguridad_social;
        this.localidad = localidad;
        this.contraseña = contraseña;
        this.biblioteca = biblioteca;
    }

    /**
     * Constructor para agregar
     * @param id_permiso
     * @param nombre
     * @param apellido
     * @param dni
     * @param fecha_nacimiento
     * @param correo
     * @param cuenta_banco
     * @param seguridad_social
     * @param biblioteca 
     */
    public Trabajador(int id_permiso, String nombre, String apellido, String dni, String fecha_nacimiento, String correo, String cuenta_banco, String seguridad_social, String biblioteca) {
        this.id_permiso = id_permiso;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.correo = correo;
        this.cuenta_banco = cuenta_banco;
        this.seguridad_social = seguridad_social;
        this.biblioteca = biblioteca;
    }

    public int getId_permiso() {
        return id_permiso;
    }

    public void setId_permiso(int id_permiso) {
        this.id_permiso = id_permiso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(String biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    
    
}
