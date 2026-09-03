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

    //Agregamos la funcion para calcular el promedio de las notas
    public double calcularPromedio() {
        if (this.notas.isEmpty()) {
            return 0.0; // Si no hay notas, el promedio es 0
        }

        double suma = 0.0;
        //Ciclo para recorrer la lista de notas y sumarlas
        for (double n : this.notas) {
            suma = suma + n;
        }

        return suma / this.notas.size(); // Divide el total entre la cantidad de notas para obtener el promedio
    }

    //Agregamos la funcion para determinar si el alumno aprueba o no
    public String chequearEstatus() {
        double prom = this.calcularPromedio();

        //Regla del Curso: promedio mayor o igual a 10
        if (this.tipoPrograma.equals("Curso")) {
            if (prom >= 10) { return "Aprobado"; }
            else { return "Reprobado"; }
        }
    
        //Regla del Diplomado: promedio mayor o igual a 14
        if (this.tipoPrograma.equals("Diplomado")) {
            if (prom >= 14) { return "Aprobado"; }
            else { return "Reprobado"; }
        }

        //Regla del Bootcamp: ninguna nota idividual puede ser menor a 14
        if (this.tipoPrograma.equals("Bootcamp")) {
            if (this.notas.isEmpty()) { return "Reprobado"; }

            //Revisando las notas de forma individual
            for (double n : this.notas) {
                if (n < 14) { return "Reprobado"; } //Si alguna nota es menor a 14
            }

            //Si todas las notas son mayores o iguales a 14
            if (prom >= 14) { return "Aprobado"; }
            else { return "Reprobado"; }
        }
        
        return "Invalido"; //Si el tipo de programa no coincide con ninguno de los anteriores
    
    }
    //Usamos un Getter para consultar el tipo de programa desde afuera
    public String getTipoPrograma() { return this.tipoPrograma; }
    public ArrayList<Double> getNotas() { return this.notas; }

 }