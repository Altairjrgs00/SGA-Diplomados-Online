#include <iostream>
#include <string>
#include <limits>
#include <stdexcept>
#include "Gestor.h"
#include "ProgramaAcademico.h"

using namespace std;

int main() {
    GestorAcademico gestor;
    int opcion = 0;

    // Bucle continuo exigido por el diplomado
    while (opcion != 7) {
        cout << "\n==========================================================\n";
        cout << "=========  SGA:DO - SISTEMA DE DIPLOMADOSONLINE  =========\n";
        cout << "==========================================================\n\n";
        cout << "1. Registrar Alumno\n";
        cout << "2. Registrar Profesor\n";
        cout << "3. Registrar Notas a un Alumno\n";
        cout << "4. Deshacer Ultimo Registro de Nota\n";
        cout << "5. Generar Cola de Certificados\n";
        cout << "6. Mostrar Reporte General\n";
        cout << "7. Salir\n\n";
        cout << "===========================================================\n\n";
        cout << "Seleccione una opcion (1-7): ";

        try {
            if (!(cin >> opcion)) {
                cin.clear(); // Limpia el estado de error de la consola
                cin.ignore(numeric_limits<streamsize>::max(), '\n'); // Descarta la entrada de texto basura
                throw invalid_argument("Error: Ingrese un valor numerico valido");
            }

            cin.ignore();

            switch (opcion) {
                case 1: {
                    string ced, nom, cor;
                    int tipoProg;
                    cout << "Cedula: "; getline(cin, ced);
                    cout << "Nombre: "; getline(cin, nom);
                    cout << "Correo: "; getline(cin, cor);
                    cout << "Programa (1 = Curso, 2 = Diplomado, 3 = Bootcamp): ";
                    cin >> tipoProg;

                    ProgramaAcademico* prog = nullptr;
                    if (tipoProg == 1) prog = new Curso();
                    else if (tipoProg == 2) prog = new Diplomado();
                    else if (tipoProg == 3) prog = new Bootcamp();

                    if (prog != nullptr) {
                        Alumno* nuevoAlumno = new Alumno(ced, nom, cor, prog);
                        gestor.registrarAlumno(nuevoAlumno);
                    } else {
                        cout << "Tipo de programa invalido.\n";
                    }
                    break;
                }
                case 2: {
                    string ced, nom, cor, mat;
                    cout << "Cedula: "; getline(cin, ced);
                    cout << "Nombre: "; getline(cin, nom);
                    cout << "Correo: "; getline(cin, cor);
                    cout << "Materia: "; getline(cin, mat);

                    Profesor* nuevoProf = new Profesor(ced, nom, cor, mat);
                    gestor.registrarProfesor(nuevoProf);
                    break;
                }
                case 3: {
                    string ced;
                    float nota;
                    cout << "Ingrese Cedula del Alumno: "; getline(cin, ced);
                    cout << "Ingrese Nota: "; cin >> nota;
                    gestor.registrarNota(ced, nota);
                    break;
                }
                case 4:
                    // Logica LIFO
                    gestor.deshacerUltimaNota();
                    break;
                case 5:
                    // Logica FIFO
                    gestor.prepararColaCertificados();
                    break;
                case 6:
                    gestor.mostrarReporteGeneral();
                    break;
                case 7:
                    cout << "Saliendo del sistema...\n\n";
                    break;
                default:
                    cout << "Opcion fuera de rango (1-7).\n";
                    break;
            }
        } 
        catch (const exception& e) {
            // Capturamos el error y mostramos el mensaje sin cerrar el programa
            cout << "\n" << e.what() << "\n";
        }
    }

    return 0;
}