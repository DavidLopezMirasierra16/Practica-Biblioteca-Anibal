package modelo;

import controlador.BaseDatosController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarLibros;

public class UbicacionLibroModelo {

    private BaseDatosController bd_controller;
    private TrabajadorModelo trabajador;
    private Connection conexion;
    private PreparedStatement prepare;
    private ResultSet resultado;

    public UbicacionLibroModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.trabajador = new TrabajadorModelo();
        this.conexion = this.bd_controller.conectar();
    }

    /**
     * Verifica si el libro con el ID especificado existe en la base de datos.
     * @param idLibro ID del libro a verificar.
     * @return true si el libro existe, false en caso contrario.
     */
    public boolean existeLibro(int idLibro) {
        String consulta_Existencia = "SELECT ISBN_Libros FROM libros WHERE ISBN_Libros = ?";
        try (PreparedStatement parametro = conexion.prepareStatement(consulta_Existencia)) {
            parametro.setInt(1, idLibro);
            ResultSet rs = parametro.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si la biblioteca con el ID especificado existe en la base de datos.
     * @param idBiblioteca ID de la biblioteca a verificar.
     * @return true si la biblioteca existe, false en caso contrario.
     */
    public boolean existeBiblioteca(int idBiblioteca) {
        idBiblioteca = trabajador.getIdBiblioteca();
        String consulta_ExistenciaB = "SELECT ID_Biblioteca FROM biblioteca WHERE ID_Biblioteca = ?";
        try (PreparedStatement parametro = conexion.prepareStatement(consulta_ExistenciaB)) {
            parametro.setInt(1, idBiblioteca);
            ResultSet rs = parametro.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta la ubicación de un libro en la base de datos.
     * @param ubicacion El objeto UbicacionLibro con los datos de la ubicación.
     * @return true si la inserción es exitosa, false si ocurre un error.
     */
    public boolean registrarUbicacion(UbicacionLibro ubicacion) {
        if (!existeLibro(ubicacion.getID_Libro())) {
            System.out.println("El libro con el ID " + ubicacion.getID_Libro() + " no existe");
            return false;
        }

        if (!existeBiblioteca(ubicacion.getID_Biblioteca())) {
            System.out.println("La biblioteca con el ID " + ubicacion.getID_Biblioteca() + " no existe");
            return false;
        }

        String inserta_UbicacionLibro = "INSERT INTO ubicacion (ID_Biblioteca, ID_Libro, Estanteria, Seccion, Piso, Cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement parametro = conexion.prepareStatement(inserta_UbicacionLibro)) {
            parametro.setInt(1, ubicacion.getID_Biblioteca());
            parametro.setInt(2, ubicacion.getID_Libro());
            parametro.setString(3, ubicacion.getEstanteria());
            parametro.setString(4, ubicacion.getSeccion());
            parametro.setString(5, ubicacion.getPiso());
            parametro.setInt(6, ubicacion.getCantidad());
            int filasAfectadas = parametro.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
