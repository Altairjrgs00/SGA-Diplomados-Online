package Java;

import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class GestorAcademico {
    //Seran los Diccionarios que almacenaran los alumnos y profesores registrados en el sistema
    private HashMap<String, Alumno> mapaAlumnos;
    private HashMap<String, Profesor> mapaProfesores;

    //Pila y cola para almacenar el historial de registros de notas y la cola de certificados
    private Stack<Alumno> historialNotas; //Pila (LIFO)
    private Queue<Alumno> colaCertificados; //Cola (FIFO) 

    //Constructor de la clase GestorAcademico, inicializa los diccionarios, pila y cola
    public GestorAcademico() {
        this.mapaAlumnos = new HashMap<>();
        this.mapaProfesores = new HashMap<>();
        this.historialNotas = new Stack<>();
        this.colaCertificados = new LinkedList<>();
    }

    //Operaciones para registrar alumnos y profesores, registrar notas, deshacer registros de notas, generar cola de certificados y mostrar reporte general
    
    //Registrar un alumno en el sistema
    public void registrarAlumno(Alumno al) {
        this.mapaAlumnos.put(al.getCedula(), al);
        System.out.println("Alumno registrado exitosamente: " + al.getNombre() + " registrado con exito");
    }

    //Registrar un profesor en el sistema
    public void registrarProfesor(Profesor prof) {
        this.mapaProfesores.put(prof.getCedula(), prof);
        System.out.println("Profesor registrado exitosamente: " + prof.getNombre() + " registrado con exito");
    }

    //Buscar un alumno por su cédula y agregarle una nota
    public void registrarNotaAAlumno(String cedulaAlumno, double nota) {
        Alumno al = this.mapaAlumnos.get(cedulaAlumno);
        if (al != null) {
            al.agregarNota(nota);
            this.historialNotas.push(al); //Agregamos el alumno a la pila de historial de notas
            System.out.println("Nota " + nota + " registrada exitosamente para el alumno: " + al.getNombre());
            System.out.println("Promedio Actual de " + al.calcularPromedio() + " | Estatus " + al.chequearEstatus());
        } else {
            System.out.println("Error: no se encontro ningun alumno que coincida con la cédula: " + cedulaAlumno);
        }
    }

    //Deshacer el último registro de nota realizado
    public void deshacerUltimaNota() {
        if (!this.historialNotas.isEmpty()) {Alumno al = this.historialNotas.pop(); //Para sacar el último alumno de la pila y deshacer su última nota registrada

            if (!al.getNotas().isEmpty()); {
            //Obtenemos la última posición de la lista de notas del alumno y la eliminamos
                int ultimaPosicion = al.getNotas().size() - 1;
                al.getNotas().remove(ultimaPosicion);
                System.out.println(" Eliminación exitosa para el alumno: " + al.getNombre() + ". Ultima nota removia. ");
            }
        } else {
            System.out.println("Error: No hay registros de notas para deshacer.");
        }
    }
    //Mostrar Reporte de lo que hay guardado actualmente en memoria
    public void mostrarReporteGeneral() {
        System.out.println("\n--- LISTA DE PROFESORES REGISTRADOS ---");
        if (this.mapaProfesores.isEmpty()) {
            System.out.println("No hay profesores registrados.");
        }
        for (Profesor prof : this.mapaProfesores.values()) {
            System.out.println("- [" + prof.getCedula() + "] " + prof.getNombre() + " | Materia: " + prof.getMateria());
        }

        System.out.println("\n--- LISTA DE ALUMNOS REGISTRADOS ---");
        if (this.mapaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        }
        for (Alumno al : this.mapaAlumnos.values()) {
            System.out.println("- [" + al.getCedula() + "] " + al.getNombre() + 
                               " | Programa: " + al.getTipoPrograma() + 
                               " | Notas: " + al.getNotas() + 
                               " | Prom: " + al.calcularPromedio() + 
                               " | Estatus: " + al.chequearEstatus());
        }
        System.out.println();
    }
    //Generar la cola de certificados para los alumnos que han aprobado el curso
    public void generarColaDeCertificados() {
        this.colaCertificados.clear(); // Limpiamos la cola antes de generar una nueva
        for (Alumno al : this.mapaAlumnos.values()) {
            if (al.chequearEstatus().equals("Aprobado")) {
                this.colaCertificados.add(al);
            }
        }

        System.out.println("Generando cola de certificados para los alumnos aprobados...");
        System.out.println("Total de alumnos en cola: " + this.colaCertificados.size());

        int contador = 1;
        while (!this.colaCertificados.isEmpty()) {
            Alumno graduado = this.colaCertificados.poll(); // Sacamos el primer alumno de la cola
            System.out.println(contador + ". [" + graduado.getCedula() + "] " + graduado.getNombre());
            System.out.println("    -Programa: " + graduado.getTipoPrograma());
            System.out.println("    -Promedio: " + graduado.calcularPromedio());
            System.out.println("    -Estatus: " + graduado.chequearEstatus());
            contador++;
        }
        System.out.println("===================================================================");
    }
}    