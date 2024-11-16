/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author pablo
 */
public class UbicacionLibro {
    private int ID_Ubicacion;
    private int ID_Biblioteca;
    private int ID_Libro;
    private String Estanteria;
    private String Seccion;
    private String Piso;
    private int Cantidad;

    public UbicacionLibro(int ID_Ubicacion, int ID_Biblioteca, int ID_Libro, String Estanteria, String Seccion, String Piso, int Cantidad) {
        this.ID_Ubicacion = ID_Ubicacion;
        this.ID_Biblioteca = ID_Biblioteca;
        this.ID_Libro = ID_Libro;
        this.Estanteria = Estanteria;
        this.Seccion = Seccion;
        this.Piso = Piso;
        this.Cantidad = Cantidad;
    }

    public UbicacionLibro(int ID_Biblioteca, int ID_Libro, String Estanteria, String Seccion, String Piso, int Cantidad) {
        this.ID_Biblioteca = ID_Biblioteca;
        this.ID_Libro = ID_Libro;
        this.Estanteria = Estanteria;
        this.Seccion = Seccion;
        this.Piso = Piso;
        this.Cantidad = Cantidad;
    }

    public int getID_Ubicacion() {
        return ID_Ubicacion;
    }

    public void setID_Ubicacion(int ID_Ubicacion) {
        this.ID_Ubicacion = ID_Ubicacion;
    }

    public int getID_Biblioteca() {
        return ID_Biblioteca;
    }

    public void setID_Biblioteca(int ID_Biblioteca) {
        this.ID_Biblioteca = ID_Biblioteca;
    }

    public int getID_Libro() {
        return ID_Libro;
    }

    public void setID_Libro(int ID_Libro) {
        this.ID_Libro = ID_Libro;
    }

    public String getEstanteria() {
        return Estanteria;
    }

    public void setEstanteria(String Estanteria) {
        this.Estanteria = Estanteria;
    }

    public String getSeccion() {
        return Seccion;
    }

    public void setSeccion(String Seccion) {
        this.Seccion = Seccion;
    }

    public String getPiso() {
        return Piso;
    }

    public void setPiso(String Piso) {
        this.Piso = Piso;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
    
}
