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
        String sql = "SELECT ISBN_Libros FROM libros WHERE ISBN_Libros = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idLibro);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true si el libro existe
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
        String sql = "SELECT ID_Biblioteca FROM biblioteca WHERE ID_Biblioteca = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idBiblioteca);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true si la biblioteca existe
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
        // Primero, verifica si el libro y la biblioteca existen en sus respectivas tablas
        if (!existeLibro(ubicacion.getID_Libro())) {
            System.out.println("El libro con el ID " + ubicacion.getID_Libro() + " no existe.");
            return false;
        }

        if (!existeBiblioteca(ubicacion.getID_Biblioteca())) {
            System.out.println("La biblioteca con el ID " + ubicacion.getID_Biblioteca() + " no existe.");
            return false;
        }

        // Si ambos existen, proceder a registrar la ubicación
        String sql = "INSERT INTO ubicacion (ID_Biblioteca, ID_Libro, Estanteria, Seccion, Piso, Cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ubicacion.getID_Biblioteca());
            stmt.setInt(2, ubicacion.getID_Libro());
            stmt.setString(3, ubicacion.getEstanteria());
            stmt.setString(4, ubicacion.getSeccion());
            stmt.setString(5, ubicacion.getPiso());
            stmt.setInt(6, ubicacion.getCantidad());
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;  // Si se insertó alguna fila, se registró correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
