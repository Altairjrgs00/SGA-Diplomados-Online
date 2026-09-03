package Java;

public class Profesor extends Persona {
    // Atributo exclusivo de profesor
    private String materia;

    // Creamos el constructor de profesor con todos sus datos
    public Profesor(String cedula, String nombre, String correo, String materia) {
        super(cedula, nombre, correo);// Le pasa los datos a la clase padre Persona
        this.materia = materia;
    }

    // Usamos Getter para leer la materia desde afuera
    public String getMateria() {
        return this.materia;
    }
}
