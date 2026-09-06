package Java;

import java.util.Scanner; 

public class Main {
        public static void main(String[] args) {
        
        //Necesitamos una herramienta para leer la entrada del usuario, por lo que importamos la clase Scanner de java.util.
        
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        //Creamos un bucle continuo ¨While¨ que se ejecutará hasta que el usuario decida salir del programa.
        while (opcion != 7) {
            System.out.println("==================================================================");
            System.out.println("============== SGA:DO - SISTEMA DE DIPLOMADOSONLINE ==============");
            System.out.println("==================================================================");
            System.out.println("1. Registrar Alumno");
            System.out.println("2. Registrar Profesor");
            System.out.println("3. Registrar notas a un alumno");
            System.out.println("4. Deshacer ultimo registro de nota");
            System.out.println("5. Generar cola de certificados");
            System.out.println("6. Mostrar Reporte General ");
            System.out.println("7. Salir del Sistema");
            System.out.println("==================================================================");
            System.out.print("Ingrese una opción: ");
        
            //Creamos un salvavidas para evitar que el programa se caiga si el usuario ingresa un valor no numérico.
            try {
                //hacemos que el programa lea la opción ingresada por el usuario y la lea como un numero
                opcion = Integer.parseInt(teclado.nextLine());

                //hacemos un switch para que el programa ejecute la opción ingresada por el usuario.
                switch (opcion) {

                   case 1: 
                        System.out.println("\nRegistrar alumno\n");
                        break;
                    case 2:
                        System.out.println("\nRegistrar profesor\n");
                        break;
                    case 3:
                        System.out.println("\nRegistrar notas a un alumno\n");
                        break;
                    case 4:
                        System.out.println("\nDeshacer ultimo registro de nota\n");
                        break;
                    case 5:
                        System.out.println("\nGenerar cola de certificados\n");
                        break;
                    case 6:
                        System.out.println("\nMostrar Reporte General\n");
                        break;
                    case 7:
                        System.out.println("\nSaliendo del programa...\n");
                        break;
                    default:
                        System.out.println("\nError: Opción inválida, ingrese un número del 1 al 7.\n");
                        System.out.println("Presione Enter para continuar...");
                        teclado.nextLine();
                        break;
               
                    }

                } catch (NumberFormatException e) {
                // Si el usuario escribe una letra o deja vacio, Java atrapa el error aqui
                System.out.println("Error: Ingrese un valor numérico válido (1-7)");
                System.out.println("Presione Enter para continuar...");
                teclado.nextLine(); // Esperamos a que el usuario presione Enter antes de continuar
                opcion = 0; // Reseteamos la opcion para que el bucle continue normal
            }
        }
        // Cerramos el lector de teclado al finalizar para liberar memoria
        teclado.close();
    }

}

    