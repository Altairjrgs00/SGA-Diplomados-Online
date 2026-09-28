#include "Gestor.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <iomanip>

GestorAcademico::GestorAcademico() {
    std::string linea;
    
    //Cargar alumnos desde el disco duro a la RAM
    std::ifstream archAlumnos("alumnos.txt");
    if (archAlumnos.is_open()) {
        while (getline(archAlumnos, linea)) {
            if (linea.empty()) continue; // Protección contra líneas vacías
            
            std::stringstream ss(linea);
            std::string ced, nom, cor, prog, n1, n2, n3;
            
            getline(ss, ced, ','); getline(ss, nom, ','); getline(ss, cor, ',');
            getline(ss, prog, ','); getline(ss, n1, ','); getline(ss, n2, ','); getline(ss, n3, ',');
            
            ProgramaAcademico* p = nullptr;
            if (prog == "Curso") p = new Curso();
            else if (prog == "Diplomado") p = new Diplomado();
            else if (prog == "Bootcamp") p = new Bootcamp();
            
            if (p != nullptr) {
                // Instanciamos usando memoria dinámica
                Alumno* a = new Alumno(ced, nom, cor, p); 
                float nota1 = std::stof(n1);
                float nota2 = std::stof(n2);
                float nota3 = std::stof(n3);
                
                // Filtramos los ceros por defecto para no afectar el estatus matemático
                if (nota1 != 0) a->agregarNota(nota1);
                if (nota2 != 0) a->agregarNota(nota2);
                if (nota3 != 0) a->agregarNota(nota3);
                
                alumnos.push_back(a); 
            }
        }
        archAlumnos.close();
    }

    //Cargar profesores
    std::ifstream archProfesores("profesores.txt");
    if (archProfesores.is_open()) {
        while (getline(archProfesores, linea)) {
            if (linea.empty()) continue;
            
            std::stringstream ss(linea);
            std::string ced, nom, cor, mat;
            
            getline(ss, ced, ','); getline(ss, nom, ','); getline(ss, cor, ','); getline(ss, mat, ',');
            profesores.push_back(new Profesor(ced, nom, cor, mat));
        }
        archProfesores.close();
    }
}

GestorAcademico::~GestorAcademico() {
    for (Alumno* a : alumnos) {
        delete a;
    }
    for (Profesor* p : profesores) {
        delete p;
    }
}

void GestorAcademico::registrarAlumno(Alumno* alumno) {
    alumnos.push_back(alumno);
    
    // Persistencia inmediata
    std::ofstream archivo("alumnos.txt", std::ios::app);
    if (archivo.is_open()) {
        archivo << alumno->getCedula() << "," << alumno->getNombre() << "," 
                << alumno->getCorreo() << "," << alumno->getNombrePrograma() << ",0,0,0\n";
        archivo.close();
    }
    
    std::cout << "\nAlumno registrado exitosamente.\n";
}

void GestorAcademico::registrarProfesor(Profesor* profesor) {
    profesores.push_back(profesor);
    
    // Persistencia inmediata
    std::ofstream archivo("profesores.txt", std::ios::app);
    if (archivo.is_open()) {
        archivo << profesor->getCedula() << "," << profesor->getNombre() << "," 
                << profesor->getCorreo() << "," << profesor->getMateria() << "\n";
        archivo.close();
    }
    
    std::cout << "\nProfesor registrado exitosamente.\n\n";
}

void GestorAcademico::registrarNota(std::string cedula, float nota) {
    for (Alumno* a : alumnos) {
        if (a->getCedula() == cedula) {
            a->agregarNota(nota);
            historialNotas.push(a); // Lo guardamos en la Pila LIFO
            
            // Sobrescribimos el archivo para reflejar las nuevas notas
            std::ofstream archivo("alumnos.txt");
            for (Alumno* al : alumnos) {
                archivo << al->getCedula() << "," << al->getNombre() << "," 
                        << al->getCorreo() << "," << al->getNombrePrograma() << "," 
                        << al->getNota(0) << "," << al->getNota(1) << "," << al->getNota(2) << "\n";
            }
            archivo.close();
            
            std::cout << "\nNota agregada con exito.\n";
            return;
        }
    }
    std::cout << "\nERROR: Alumno no encontrado.\n";
}

// LIFO
void GestorAcademico::deshacerUltimaNota() {
    if (!historialNotas.empty()) {
        Alumno* ultimoAlumno = historialNotas.top();
        historialNotas.pop();
        
        ultimoAlumno->removerUltimaNota();
        
        // Sobrescribimos el archivo para reflejar la nota eliminada
        std::ofstream archivo("alumnos.txt");
        for (Alumno* al : alumnos) {
            archivo << al->getCedula() << "," << al->getNombre() << "," 
                    << al->getCorreo() << "," << al->getNombrePrograma() << "," 
                    << al->getNota(0) << "," << al->getNota(1) << "," << al->getNota(2) << "\n";
        }
        archivo.close();
        
        std::cout << "\nSe ha deshecho el ultimo registro de nota.\n\n";
    } else {
        std::cout << "\nNo hay registros de notas recientes para deshacer.\n\n";
    }
}

// FIFO
void GestorAcademico::prepararColaCertificados() {
    while (!colaCertificados.empty()) colaCertificados.pop();

    //Polimorfismo
    for (Alumno* a : alumnos) {
        if (a->estaAprobado()) {
            colaCertificados.push(a);
        }
    }

    std::ofstream archivo("certificados_pendientes.txt"); 
    if (archivo.is_open()) {
        archivo << "===============================================\n";
        archivo << "REPORTE DE CERTIFICADOS PENDIENTES\n";
        archivo << "===============================================\n";
        archivo << "Total de graduandos en cola: " << colaCertificados.size() << "\n\n";
        
        int contador = 1;
        while (!colaCertificados.empty()) {
            Alumno* graduando = colaCertificados.front(); 
            float nota1 = graduando->getNota(0);
            float nota2 = graduando->getNota(1);
            float nota3 = graduando->getNota(2);
            float promedio = (nota1 + nota2 + nota3) / 3.0f;
            
            std::string etiquetaEstatus = "APROBADO";
            if (graduando->getNombrePrograma() == "Bootcamp") {
                etiquetaEstatus = "APROBADO (Cumple regla de ninguna nota < 14)";
            }
            
            archivo << contador << ". [" << graduando->getCedula() << "] " << graduando->getNombre() << "\n";
            archivo << "   - Programa: " << graduando->getNombrePrograma() << "\n";
            archivo << std::fixed << std::setprecision(1); 
            archivo << "   - Promedio Final: " << promedio << "\n";
            archivo << "   - Estatus: " << etiquetaEstatus << "\n\n";
            
            colaCertificados.pop();
            contador++;
        }
        
        archivo << "===============================================\n";
        archivo << "* Fin del reporte - Generado por SGA-DO *\n";
        archivo.close();
        
        std::cout << "\nCola de certificados generada con exito.\n\n";
    }
}

void GestorAcademico::mostrarReporteGeneral() {
    std::cout << "\n===============================================\n";
    std::cout << "REPORTE GENERAL DE ALUMNOS\n";
    std::cout << "===============================================\n\n";
    
    if (alumnos.empty()) {
        std::cout << "No hay alumnos registrados aun.\n\n";
    } else {
        int contador = 1;
        for (Alumno* a : alumnos) {
            float n1 = a->getNota(0);
            float n2 = a->getNota(1);
            float n3 = a->getNota(2);
            float promedio = (n1 + n2 + n3) / 3.0f;
            
            std::string estatus = "REPROBADO";
            if (a->getCantidadNotas() < 3) {
                estatus = "SIN NOTAS / INCOMPLETO";
            } else if (a->estaAprobado()) {
                estatus = "APROBADO";
                if (a->getNombrePrograma() == "Bootcamp") estatus;
            }
            
            std::cout << contador << ". [" << a->getCedula() << "] " << a->getNombre() << "\n";
            std::cout << "   - Programa: " << a->getNombrePrograma() << "\n";
            std::cout << std::fixed << std::setprecision(1);
            std::cout << "   - Promedio Final: " << promedio << "\n";
            std::cout << "   - Estatus: " << estatus << "\n\n";
            contador++;
        }
    }

    std::cout << "===============================================\n";
    std::cout << "REPORTE DE PROFESORES ACTIVOS\n";
    std::cout << "===============================================\n\n";
    
    if (profesores.empty()) {
        std::cout << "No hay profesores registrados aun.\n";
    } else {
        int contadorProf = 1;
        for (Profesor* p : profesores) {
            std::cout << contadorProf << ". [" << p->getCedula() << "] " << p->getNombre() << "\n";
            std::cout << "   - Materia: " << p->getMateria() << "\n";
            contadorProf++;
        }
    }
}