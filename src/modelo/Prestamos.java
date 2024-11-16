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
public class Prestamos {
    private int ID_Prestamo;
    private int ID_Libro_FK;
    private int ID_Socio_FK;
    private Date Fecha_Prestamo;
    private Date Fecha_Devolucion;

    public Prestamos(int ID_Prestamo, int ID_Libro_FK, int ID_Socio_FK, Date Fecha_Prestamo, Date Fecha_Devolucion) {
        this.ID_Prestamo = ID_Prestamo;
        this.ID_Libro_FK = ID_Libro_FK;
        this.ID_Socio_FK = ID_Socio_FK;
        this.Fecha_Prestamo = Fecha_Prestamo;
        this.Fecha_Devolucion = Fecha_Devolucion;
    }

    public Prestamos(int ID_Libro_FK, int ID_Socio_FK, Date Fecha_Prestamo, Date Fecha_Devolucion) {
        this.ID_Libro_FK = ID_Libro_FK;
        this.ID_Socio_FK = ID_Socio_FK;
        this.Fecha_Prestamo = Fecha_Prestamo;
        this.Fecha_Devolucion = Fecha_Devolucion;
    }

    public Prestamos(int ID_Prestamo, int ID_Libro_FK, int ID_Socio_FK, Date Fecha_Prestamo) {
        this.ID_Prestamo = ID_Prestamo;
        this.ID_Libro_FK = ID_Libro_FK;
        this.ID_Socio_FK = ID_Socio_FK;
        this.Fecha_Prestamo = Fecha_Prestamo;
    }

    public int getID_Prestamo() {
        return ID_Prestamo;
    }

    public void setID_Prestamo(int ID_Prestamo) {
        this.ID_Prestamo = ID_Prestamo;
    }

    public int getID_Libro_FK() {
        return ID_Libro_FK;
    }

    public void setID_Libro_FK(int ID_Libro_FK) {
        this.ID_Libro_FK = ID_Libro_FK;
    }

    public int getID_Socio_FK() {
        return ID_Socio_FK;
    }

    public void setID_Socio_FK(int ID_Socio_FK) {
        this.ID_Socio_FK = ID_Socio_FK;
    }

    public Date getFecha_Prestamo() {
        return Fecha_Prestamo;
    }

    public void setFecha_Prestamo(Date Fecha_Prestamo) {
        this.Fecha_Prestamo = Fecha_Prestamo;
    }

    public Date getFecha_Devolucion() {
        return Fecha_Devolucion;
    }

    public void setFecha_Devolucion(Date Fecha_Devolucion) {
        this.Fecha_Devolucion = Fecha_Devolucion;
    }
    
}
