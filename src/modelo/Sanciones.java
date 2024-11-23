/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author pablo
 */
public class Sanciones {
    private int ID_Sancion;
    private int ID_Socio_FK;
    private int ID_Prestamo_FK;
    private String Tipo_Sancion;

    /**
     * Constructor para consultar
     * @param ID_Sancion
     * @param ID_Socio_FK
     * @param ID_Prestamo_FK
     * @param Tipo_Sancion 
     */
    public Sanciones(int ID_Sancion, int ID_Socio_FK, int ID_Prestamo_FK, String Tipo_Sancion) {
        this.ID_Sancion = ID_Sancion;
        this.ID_Socio_FK = ID_Socio_FK;
        this.ID_Prestamo_FK = ID_Prestamo_FK;
        this.Tipo_Sancion = Tipo_Sancion;
    }

    /**
     * Constructor para insertar
     * @param ID_Socio_FK
     * @param ID_Prestamo_FK
     * @param Tipo_Sancion 
     */
    public Sanciones(int ID_Socio_FK, int ID_Prestamo_FK, String Tipo_Sancion) {
        this.ID_Socio_FK = ID_Socio_FK;
        this.ID_Prestamo_FK = ID_Prestamo_FK;
        this.Tipo_Sancion = Tipo_Sancion;
    }

    public int getID_Sancion() {
        return ID_Sancion;
    }

    public void setID_Sancion(int ID_Sancion) {
        this.ID_Sancion = ID_Sancion;
    }

    public int getID_Socio_FK() {
        return ID_Socio_FK;
    }

    public void setID_Socio_FK(int ID_Socio_FK) {
        this.ID_Socio_FK = ID_Socio_FK;
    }

    public int getID_Prestamo_FK() {
        return ID_Prestamo_FK;
    }

    public void setID_Prestamo_FK(int ID_Prestamo_FK) {
        this.ID_Prestamo_FK = ID_Prestamo_FK;
    }

    public String getTipo_Sancion() {
        return Tipo_Sancion;
    }

    public void setTipo_Sancion(String Tipo_Sancion) {
        this.Tipo_Sancion = Tipo_Sancion;
    }
    
}
