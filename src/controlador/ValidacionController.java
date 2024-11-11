package controlador;

public class ValidacionController {

    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public static boolean validarApellidos(String apellidos) {
        return apellidos != null && !apellidos.trim().isEmpty();
    }

    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{10}");
    }

    public static boolean validarCorreoValido(String correo) {
        return correo != null && correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    public static boolean validarFecha(String fecha) {
        return fecha != null && fecha.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
