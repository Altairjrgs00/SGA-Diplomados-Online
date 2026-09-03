package Java;

public class Persona {
    // Seran loa atributos o variables
    protected String Cedula;
    protected String Nombre;
    protected String Correo;

    // Ahora los constructores
    public Persona(String cedula, String nombre, String correo) {
        this.Cedula = cedula;
        this.Nombre = nombre;
        this.Correo = correo;
    }

    //Establecemos las funciones get para leer los datos desde afuera
    public String getCedula() {
        return this.Cedula;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public String getCorreo() {
        return this.Correo;
    }
}