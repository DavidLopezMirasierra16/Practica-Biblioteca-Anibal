package modelo;

import java.sql.CallableStatement;
import controlador.BaseDatosController;
import controlador.LoginController;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.ConsultarLibros;

public class LibrosModelo {
    private BaseDatosController bd_controller;
    private TrabajadorModelo trabajador;
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public LibrosModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
        this.trabajador = new TrabajadorModelo();
        this.conexion = this.bd_controller.conectar();
    }
        
    //---------------------------INSERTAR---------------------------
    
    /**
     * Nos crea un libro en funcion de los datos que nosotros le pasamos
     * @param titulo
     * @param isbn
     * @param genero
     * @param year
     * @param editorial
     * @param idAutor
     * @return 
     */
    public Libros crearLibro(String titulo, String genero, String year, String editorial, int idAutor){
        Libros libro = new Libros(titulo, genero, year, editorial, idAutor);
        
        insertarDatos(libro);
        
        return libro;
    }    
    
    /**
     * Funcion que nos inserta en la bd un libro
     * @param libro
     */ 
    public void insertarDatos(Libros libro){
        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.libros (Titulo, Genero, Anio, Editorial, ID_Autor_FK)" + "VALUES (?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            //prepare.setInt(1, libro.getISBN());
            prepare.setString(1, libro.getTitulo());
            prepare.setString(2, libro.getGenero());
            prepare.setString(3, libro.getYear());
            prepare.setString(4, libro.getEditorial());
            prepare.setInt(5, libro.getIdAutor());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Libro " + libro.getTitulo() + " agregado correctamente a la BD");
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }    

    /**
     * Registra un libro en la base de datos si no existe previamente.
     * 
     * @param ISBN El ISBN del libro
     * @param titulo El título del libro
     * @param genero El género del libro
     * @param year El año de publicación del libro
     * @param editorial La editorial del libro
     * @param idAutor El ID del autor del libro
     * @return el libro registrado o null si ya existe
     */
    public Libros registrarLibro(int ISBN, String titulo, String genero, String year, String editorial, int idAutor) {
        Libros libro = new Libros(ISBN, titulo, genero, year, editorial, idAutor);

        if (!comprobarLibroExistente(ISBN)) {
            ingresarLibroEnBd(libro);
            return libro;
        }
        
        return null;
    }

    /**
     * Verifica si un libro con el ISBN especificado ya está registrado en la base de datos.
     * 
     * @param ISBN El ISBN del libro
     * @return true si el libro existe, false en caso contrario
     */
    public boolean comprobarLibroExistente(int ISBN) {
        try {
            String comprueba_Libro = "SELECT ISBN_Libros FROM bd_biblioteca.libros WHERE ISBN_Libros = ?;";
            prepare = conexion.prepareStatement(comprueba_Libro);
            prepare.setInt(1, ISBN);
            
            resultado = prepare.executeQuery();
            
            return resultado.next(); // Retorna true si encuentra el libro
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta un nuevo libro en la base de datos.
     * 
     * @param libro el objeto Libros que queremos registrar en la base de datos
     */
    public void ingresarLibroEnBd(Libros libro) {
        try {
            String insertar_Libro = "INSERT INTO bd_biblioteca.libros (ISBN_Libros, Titulo, Genero, Year, Editorial, ID_AUTOR) " +
                         "VALUES (?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(insertar_Libro);
            prepare.setInt(1, libro.getIdLibros());
            prepare.setString(2, libro.getTitulo());
            prepare.setString(3, libro.getGenero());
            prepare.setString(4, libro.getYear());
            prepare.setString(5, libro.getEditorial());
            prepare.setInt(6, libro.getIdAutor());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Libro " + libro.getTitulo() + " agregado correctamente a la BD");
            }
                System.out.println("Libro '" + libro.getTitulo() + "' agregado correctamente a la BD");
            }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Selecciona el ID del autor que nosotros le pasamos.
     * @param autor
     * @return 
     */
    public int seleccionarAutor(String autor){
        int id = 0;
        
        try {
            
            String comprobar_id_autor = "SELECT id_autor from bd_biblioteca.autores WHERE nombre_autor = ?;";
            
            prepare=conexion.prepareStatement(comprobar_id_autor);
            
            prepare.setString(1, autor);
            
            resultado = prepare.executeQuery();
            
            if (resultado.next()) {
                id=resultado.getInt("id_autor");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    
    //---------------------------CONSULTAS---------------------------
    
    /**
     * Funcion que nos muestra todos los libros en una tabla
     * @param table 
     */
    public void consultarLibros(JTable table) {
        int idBiblioteca = trabajador.getIdBiblioteca();
        System.out.println(idBiblioteca);

        if (idBiblioteca == 0) {
            System.out.println("No se pudo obtener el ID de la biblioteca.");
            return;
        }

        String consulta_Libros = "CALL mostrarLibrosConUbicacion(?)";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(consulta_Libros)) {

            parametro.setInt(1, idBiblioteca);

            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                while (result.next()) {
                    Object[] row = {
                        result.getInt("ISBN_Libros"),
                        result.getString("Titulo"),
                        result.getString("Genero"),
                        result.getString("Anio"),
                        result.getString("Editorial"),
                        result.getString("nombre_autor"),
                        result.getString("Estanteria"),
                        result.getString("Seccion"),
                        result.getString("Piso"),
                        result.getInt("Cantidad")
                    };
                    model.addRow(row);
                }
                table.setModel(model);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setResizable(false);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al consultar los libros: " + e.getMessage());
        }
    }

    /**
     * Funcion que funciona de filtro para buscar
     * @param consultarLibros 
     */
    public void filtrarLibros(ConsultarLibros consultarLibros) {
        String filtro = (String) consultarLibros.getCbFiltro().getSelectedItem();
        String busqueda = consultarLibros.getTxtBusqueda().getText().trim();

        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna;
        switch (filtro) {
            case "Nombre":
                columna = "Titulo";
                break;
            case "Editorial":
                columna = "Editorial";
                break;
            case "Autor":
                columna = "nombre_autor";
                break;
            default:
                System.out.println("Filtro no válido.");
                return;
        }

        String consulta_LibrosFiltro = "CALL mostrarLibrosConUbicacion(?)";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conection = baseDatosController.conectar();
             PreparedStatement parametro = conection.prepareStatement(consulta_LibrosFiltro)) {

            parametro.setInt(1, trabajador.getIdBiblioteca());

            try (ResultSet result = parametro.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) consultarLibros.getTablaLibros().getModel();
                model.setRowCount(0);

                while (result.next()) {
                    if (result.getString(columna).toLowerCase().contains(busqueda.toLowerCase())) {
                        Object[] row = {
                            result.getInt("ISBN_Libros"),
                            result.getString("Titulo"),
                            result.getString("Genero"),
                            result.getString("Anio"),
                            result.getString("Editorial"),
                            result.getString("nombre_autor"),
                            result.getString("Estanteria"),
                            result.getString("Seccion"),
                            result.getString("Piso"),
                            result.getInt("Cantidad")
                        };
                        model.addRow(row);
                    }
                }

                consultarLibros.getTablaLibros().setModel(model);

                for (int i = 0; i < consultarLibros.getTablaLibros().getColumnCount(); i++) {
                    consultarLibros.getTablaLibros().getColumnModel().getColumn(i).setResizable(false);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
