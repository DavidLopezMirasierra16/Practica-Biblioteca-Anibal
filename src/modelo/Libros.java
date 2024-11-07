package modelo;

public class Libros {
    
    private int idLibros; // Se ajusta a `idLibros` (ID_Libros)
    private String titulo;
    private String isbn;
    private String genero;
    private String year;
    private String editorial;
    private int idAutor; // Se ajusta a `idAutor` (ID_AUTOR)

    public Libros(int idLibros, String titulo, String isbn, String genero, String year, String editorial, int idAutor) {
        this.idLibros = idLibros;
        this.titulo = titulo;
        this.isbn = isbn;
        this.genero = genero;
        this.year = year;
        this.editorial = editorial;
        this.idAutor = idAutor;
    }

    public int getIdLibros() {
        return idLibros;
    }

    public void setIdLibros(int idLibros) {
        this.idLibros = idLibros;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
}
