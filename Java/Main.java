package Java;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("----- PRUEBA DE REGLAS EN JAVA -----");
        System.out.println("==================================================");
   
        //Caso 1: Wade Wilson debe aprobar un curso con promedio de 10   
        Alumno alumnoCurso = new Alumno("V-101", "Wade Wilson", "wade.wilson@ejemplo.com", "Curso"); 
        alumnoCurso.agregarNota(10.0);
        alumnoCurso.agregarNota(10.0);
        alumnoCurso.agregarNota(10.0);

        System.out.println("Alumno: " + alumnoCurso.getNombre() + " | Programa: " + alumnoCurso.getTipoPrograma());
        System.out.println("Promedio: " + alumnoCurso.calcularPromedio() + " | Estatus: " + alumnoCurso.chequearEstatus());
    
    
        System.out.println("==================================================");
    
        // Caso 2: Peter Parker debe aprobar un Bootcamp pero tiene una nota de 13)
        // La regla estricta exige aplazarlo automáticamente por tener una nota menor a 14
        Alumno alumnoBootcamp = new Alumno("V-202", "Peter Parker", "peter.parker@ejemplo.com", "Bootcamp");
        alumnoBootcamp.agregarNota(20.0);
        alumnoBootcamp.agregarNota(20.0);
        alumnoBootcamp.agregarNota(13.0); 

        System.out.println("Alumno: " + alumnoBootcamp.getNombre() + " | Programa: " + alumnoBootcamp.getTipoPrograma());
        System.out.println("Promedio: " + alumnoBootcamp.calcularPromedio() + " | Estatus: " + alumnoBootcamp.chequearEstatus());
        System.out.println("==================================================");

    }

}
