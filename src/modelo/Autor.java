/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author 34662
 */
public class Autor {

    private int id;
    private String nombre;
    private String fecha_nacimiento;
    private String email;

    /**
     * Este constructor nos sirve para consultar.
     * @param id
     * @param nombre
     * @param fecha_nacimiento
     * @param email 
     */
    public Autor(int id, String nombre, String fecha_nacimiento, String email) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.email = email;
    }

    /**
     * Este constructor nos sirve para insertar.s
     * @param nombre
     * @param fecha_nacimiento
     * @param email 
     */
    public Autor(String nombre, String fecha_nacimiento, String email) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.email = email;
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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
}
