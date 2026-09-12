package Java;

import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

//Herramientas para la lectura y escritura de archivos
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        this.cargarDatosDesdeArchivos(); //Cargar datos desde el archivo al iniciar el sistema
    }

    //Operaciones para registrar alumnos y profesores, registrar notas, deshacer registros de notas, generar cola de certificados y mostrar reporte general
    
    //Registrar un alumno en el sistema
    public void registrarAlumno(Alumno al) {
        this.mapaAlumnos.put(al.getCedula(), al);
        System.out.println("Alumno registrado exitosamente: " + al.getNombre() + " registrado con exito");
        this.guardarAlumnosEnArchivo(); // Guardar datos en el archivo después de registrar un alumno
    }

    //Registrar un profesor en el sistema
    public void registrarProfesor(Profesor prof) {
        this.mapaProfesores.put(prof.getCedula(), prof);
        System.out.println("Profesor registrado exitosamente: " + prof.getNombre() + " registrado con exito");
        this.guardarProfesoresEnArchivo(); // Guardar datos en el archivo después de registrar un profesor
    }

    //Buscar un alumno por su cédula y agregarle una nota
    public void registrarNotaAAlumno(String cedulaAlumno, double nota) {
        Alumno al = this.mapaAlumnos.get(cedulaAlumno);
        if (al != null) {
            al.agregarNota(nota);
            this.historialNotas.push(al); //Agregamos el alumno a la pila de historial de notas
            System.out.println("Nota " + nota + " registrada exitosamente para el alumno: " + al.getNombre());

            this.guardarAlumnosEnArchivo(); // Guardar datos en el archivo después de registrar una nota
        } else {
            System.out.println("Error: no se encontro ningun alumno que coincida con la cédula: " + cedulaAlumno);
        }
    }
//Para la PERSISTENCIA TEXTUAL (.TXT)

    // Para la escritura de Alumnos
    private void guardarAlumnosEnArchivo() {
        // Usamos try-with-resources para que Java cierre el archivo automaticamente al terminar
        try (FileWriter escritor = new FileWriter("Java/alumnos.txt")) {
            for (Alumno al : this.mapaAlumnos.values()) {
                // Formato: Cedula,Nombre,Correo,TipoPrograma,Nota1,Nota2,Nota3
                double n1 = al.getNotas().size() > 0 ? al.getNotas().get(0) : 0.0;
                double n2 = al.getNotas().size() > 1 ? al.getNotas().get(1) : 0.0;
                double n3 = al.getNotas().size() > 2 ? al.getNotas().get(2) : 0.0;

                escritor.write(al.getCedula() + "," + al.getNombre() + "," + al.getCorreo() + "," +
                               al.getTipoPrograma() + "," + n1 + "," + n2 + "," + n3 + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error critico al escribir en alumnos.txt: " + e.getMessage());
        }
    }

    // Para la escritura de Profesores
    private void guardarProfesoresEnArchivo() {
        try (FileWriter escritor = new FileWriter("Java/profesores.txt")) {
            for (Profesor prof : this.mapaProfesores.values()) {
                // Formato: Cedula, Nombre, Correo, Materia
                escritor.write(prof.getCedula() + "," + prof.getNombre() + "," + prof.getCorreo() + "," + prof.getMateria() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error critico al escribir en profesores.txt: " + e.getMessage());
        }
    }

    // Para la lectura automática al arrancar el programa (Evita pérdidas por fallas de energía o cierres inesperados)
    private void cargarDatosDesdeArchivos() {
        // Cargar Profesores
        try (BufferedReader lector = new BufferedReader(new FileReader("Java/profesores.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    Profesor prof = new Profesor(datos[0], datos[1], datos[2], datos[3]);
                    this.mapaProfesores.put(prof.getCedula(), prof);
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe en la primera corrida, no pasa nada, se creara solo despues
        }

        // Cargar Alumnos
        try (BufferedReader lector = new BufferedReader(new FileReader("Java/alumnos.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 7) {
                    Alumno al = new Alumno(datos[0], datos[1], datos[2], datos[3]);
                    // Recuperamos sus notas guardadas
                    double n1 = Double.parseDouble(datos[4]);
                    double n2 = Double.parseDouble(datos[5]);
                    double n3 = Double.parseDouble(datos[6]);
                    
                    if (n1 > 0) al.agregarNota(n1);
                    if (n2 > 0) al.agregarNota(n2);
                    if (n3 > 0) al.agregarNota(n3);

                    this.mapaAlumnos.put(al.getCedula(), al);
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe, se ignora de forma segura
        }
    }

    //Deshacer el último registro de nota realizado
    public void deshacerUltimaNota() {
        if (!this.historialNotas.isEmpty()) {Alumno al = this.historialNotas.pop(); //Para sacar el último alumno de la pila y deshacer su última nota registrada
            if (!al.getNotas().isEmpty()); {
            //Obtenemos la última posición de la lista de notas del alumno y la eliminamos
                int ultimaPosicion = al.getNotas().size() - 1;
                al.getNotas().remove(ultimaPosicion);
                this.guardarAlumnosEnArchivo(); // Guardar datos en el archivo después de deshacer una nota
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
    public void generarColaCertificadosConBusqueda(String cedula) {
        Alumno alumnoConsultado = this.mapaAlumnos.get(cedula);
        if (alumnoConsultado != null) {
            System.out.println("\nAlumno: " + alumnoConsultado.getNombre() +
                               "  Notas registradas: " + alumnoConsultado.getNotas());
        } else {
            System.out.println("No se encontró ningún alumno con la cédula: " + cedula);
        }
        this.colaCertificados.clear(); // Limpiamos la cola antes de generar una nueva
        for (Alumno al : this.mapaAlumnos.values()) {
            if (al.chequearEstatus().equals("Aprobado")) {
                this.colaCertificados.add(al);
            }
        }

        System.out.println("\nGenerando cola de certificados para los alumnos aprobados...");
        System.out.println("Total de alumnos en cola: " + this.colaCertificados.size());

        // Para exportar el reporte a un archivo de texto
        try (FileWriter reporte = new FileWriter("Java/certificados_pendientes.txt")) {
            reporte.write("==============================================\n");
            reporte.write("===== REPORTE DE CERTIFICADOS PENDIENTES =====\n");
            reporte.write("==============================================\n");
            reporte.write("Total de graduandos en cola: " + this.colaCertificados.size() + "\n\n");

            int contador = 1;
            while (!this.colaCertificados.isEmpty()) {
                Alumno graduado = this.colaCertificados.poll(); // Sacamos el primer alumno de la cola
                            
                reporte.write(contador + ". [" + graduado.getCedula() + "] " + graduado.getNombre() + "\n");
                reporte.write("   - Programa: " + graduado.getTipoPrograma() + "\n");
                reporte.write("   - Promedio Final: " + graduado.calcularPromedio() + "\n");
                reporte.write("   - Estatus: APROBADO\n\n");
                contador++;
            }
            reporte.write("=============================================\n");
            reporte.write("*             Fin del reporte               *\n");
            reporte.write("=============================================\n");
        } catch (IOException e) {
            System.out.println("Error al exportar certificados: " + e.getMessage());
        }
        
        System.out.println("===================================================================");
    }
}    