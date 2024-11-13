package modelo;

public class Libros {
    
    private int ISBN; // Se ajusta a `idLibros` (ID_Libros)
    private String titulo;
    private String genero;
    private String year;
    private String editorial;
    private int idAutor; // Se ajusta a `idAutor` (ID_AUTOR)

    public Libros(int ISBN, String titulo, String genero, String year, String editorial, int idAutor) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.genero = genero;
        this.year = year;
        this.editorial = editorial;
        this.idAutor = idAutor;
    }

    public int getIdLibros() {
        return ISBN;
    }

    public void setIdLibros(int idLibros) {
        this.ISBN = idLibros;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Este consulta los libros
     * @param idLibros
     * @param titulo
     * @param isbn
     * @param genero
     * @param year
     * @param editorial
     * @param idAutor 
     */
    }
