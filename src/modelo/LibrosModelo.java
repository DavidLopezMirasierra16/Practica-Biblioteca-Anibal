package modelo;

import controlador.BaseDatosController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import vista.ConsultarLibros;

public class LibrosModelo {

    private BaseDatosController bd_controller;
    
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private PreparedStatement prepare;
    private CallableStatement consultas_funciones;

    public LibrosModelo() throws SQLException {
        this.bd_controller = new BaseDatosController();
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
    public Libros crearLibro(String titulo, String isbn, String genero, String year, String editorial, int idAutor){
        Libros libro = new Libros(titulo, isbn, genero, year, editorial, idAutor);
        
        insertarDatos(libro);
        
        return null;
    }    
    
    /**
     * Funcion que nos inserta en la bd un libro
     * @param libro
     */ 
    public void insertarDatos(Libros libro){
        try {
            //this.bd_controller.conectarBd();

            String sentencia_slq = "INSERT INTO bd_biblioteca.libros (Titulo, ISBN, Genero, Anio, Editorial, ID_Autor_FK)" + "VALUES (?, ?, ?, ?, ?, ?);";

            prepare = conexion.prepareStatement(sentencia_slq);
            
            prepare.setString(1, libro.getTitulo());
            prepare.setString(2, libro.getIsbn());
            prepare.setString(3, libro.getGenero());
            prepare.setString(4, libro.getYear());
            prepare.setString(5, libro.getEditorial());
            prepare.setInt(6, libro.getIdAutor());
            
            int ejecutar = prepare.executeUpdate();
            
            if (ejecutar == 1) {
                System.out.println("Libro " + libro.getTitulo() + " agregado correctamente a la BD");
            }
            
        } catch (SQLException ex) {
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
    
    // Método para consultar todos los libros y mostrar en la tabla
    public void consultarLibros(JTable table) {
        String sql = "SELECT ID_Libros, Titulo, Genero, Year, Editorial, ID_AUTOR FROM libros";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Crear un modelo para la tabla y limpiar las filas actuales
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Limpiar las filas actuales antes de agregar nuevas

            // Agregar las filas a la tabla
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ID_Libros"),
                    rs.getString("Titulo"),
                    rs.getString("Genero"),
                    rs.getString("Year"),
                    rs.getString("Editorial"),
                    rs.getInt("ID_AUTOR")
                };
                model.addRow(row);
            }

            // Establecer el modelo en la tabla
            table.setModel(model);

            // No permitir redimensionar las columnas
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para filtrar libros según el filtro y la búsqueda
    public void filtrarLibros(ConsultarLibros consultarLibros) {
        String filtro = (String) consultarLibros.getCbFiltro().getSelectedItem();
        String busqueda = consultarLibros.getTxtBusqueda().getText().trim();

        if (filtro.equals("Seleccione una opción") || busqueda.isEmpty()) {
            System.out.println("Seleccione un filtro válido y un valor de búsqueda.");
            return;
        }

        String columna = "";
        switch (filtro) {
            case "Nombre":
                columna = "Titulo";
                break;
            case "Editorial":
                columna = "Editorial";
                break;
            case "Autor":
                columna = "ID_AUTOR";
                break;
        }

        String sql = "SELECT ID_Libros, Titulo, Genero, Year, Editorial, ID_AUTOR FROM libros WHERE " + columna + " LIKE ?";
        BaseDatosController baseDatosController = new BaseDatosController();

        try (Connection conn = baseDatosController.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + busqueda + "%");  // Usamos % para buscar coincidencias parciales

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) consultarLibros.getTablaLibros().getModel();
                model.setRowCount(0); // Limpiar las filas actuales antes de filtrar

                // Agregar las filas filtradas a la tabla
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("ID_Libros"),
                        rs.getString("Titulo"),
                        rs.getString("Genero"),
                        rs.getString("Year"),
                        rs.getString("Editorial"),
                        rs.getInt("ID_AUTOR")
                    };
                    model.addRow(row);
                }

                // Establecer el modelo en la tabla
                consultarLibros.getTablaLibros().setModel(model);

                // No permitir redimensionar las columnas
                for (int i = 0; i < consultarLibros.getTablaLibros().getColumnCount(); i++) {
                    consultarLibros.getTablaLibros().getColumnModel().getColumn(i).setResizable(false);  // Deshabilitar redimensionado de columnas
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
