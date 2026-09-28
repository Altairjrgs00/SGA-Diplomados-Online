#ifndef GESTOR_H
#define GESTOR_H

#include "Alumno.h"
#include "Profesor.h"
#include <vector>
#include <stack>
#include <queue>
#include <string>

class GestorAcademico {
private:
    std::vector<Alumno*> alumnos;
    std::vector<Profesor*> profesores;
    
    // Estructuras exigidas
    std::stack<Alumno*> historialNotas;   // Pila LIFO (Último en entrar, primero en salir)
    std::queue<Alumno*> colaCertificados; // Cola FIFO (Primero en entrar, primero en salir)

public:
    GestorAcademico();
    ~GestorAcademico();
            
    void registrarAlumno(Alumno* alumno);
    void registrarProfesor(Profesor* profesor);
    
    void registrarNota(std::string cedula, float nota);
    void deshacerUltimaNota();
    void prepararColaCertificados();
    void mostrarReporteGeneral();
};

#endif