package Java;

import java.util.ArrayList;

public class Alumno extends Persona {
    // Atributos privados de Alumno
    private String tipoPrograma;
    private ArrayList<Double> notas; //Sera la lista dinamica para numeros con decimales

    // Creamos el constructor de Alumno
    public Alumno(String cedula, String nombre, String correo, String tipoPrograma) {
        super(cedula, nombre, correo); // Le pasa los datos basicos a la clase padre Persona
        this.tipoPrograma = tipoPrograma;
        this.notas = new ArrayList<Double>(); // Para crear la lista vacia en la memoria

    }

    // Hacemos un metodo para agregar una nota (con limite de 3 notas)
    public void agregarNota(double nota) {
        if (this.notas.size() < 3) { // Limite de 3 notas
            this.notas.add(nota);
        }
    }

    //Usamos un Getter para consultar el tipo de programa desde afuera
    public String getTipoPrograma() {
        return this.tipoPrograma;
    }

    public ArrayList<Double> getNotas() {
        return this.notas;
    }

 }