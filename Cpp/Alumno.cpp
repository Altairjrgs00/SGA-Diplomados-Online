#include "Alumno.h"
#include <iostream>

// El constructor pasa los datos básicos a Persona y guarda el puntero del programa
Alumno::Alumno(std::string _cedula, std::string _nombre, std::string _correo, ProgramaAcademico* _programa)
    : Persona(_cedula, _nombre, _correo) {
    programa = _programa;
}

// Limpiamos la memoria dinámica del programa
Alumno::~Alumno() {
    if (programa != nullptr) {
        delete programa;
    }
}

void Alumno::agregarNota(float nota) {
    if (notas.size() < 3) {
        notas.push_back(nota);
    } else {
        std::cout << "El alumno ya tiene las 3 notas maximas permitidas.\n\n";
    }
}

void Alumno::removerUltimaNota() {
    if (!notas.empty()) {
        notas.pop_back(); // Remueve la última nota insertada (Lógica LIFO para el Ctrl+Z)
    }
}

bool Alumno::estaAprobado() {
    // Si ya tiene las 3 notas, invocamos el polimorfismo para evaluar la aprobación según el programa académico
    if (notas.size() == 3) {
        return programa->evaluarAprobacion(notas[0], notas[1], notas[2]);
    }
    return false; // Si le faltan notas, no puede estar aprobado aún
}

int Alumno::getCantidadNotas() const {
    return notas.size();
}

float Alumno::getNota(int indice) const {
    if (indice >= 0 && indice < notas.size()) {
        return notas[indice];
    }
    return 0.0f;
}