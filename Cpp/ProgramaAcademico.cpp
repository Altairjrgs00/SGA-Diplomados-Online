#include "ProgramaAcademico.h"

ProgramaAcademico::~ProgramaAcademico() {
}

// Regla del Curso: Promedio >= 10
bool Curso::evaluarAprobacion(float nota1, float nota2, float nota3) {
    float promedio = (nota1 + nota2 + nota3) / 3.0f;
    return promedio >= 10.0f;
}

// Regla del Diplomado: Promedio >= 14
bool Diplomado::evaluarAprobacion(float nota1, float nota2, float nota3) {
    float promedio = (nota1 + nota2 + nota3) / 3.0f;
    return promedio >= 14.0f;
}

// Regla del Bootcamp: Ninguna nota menor a 14
bool Bootcamp::evaluarAprobacion(float nota1, float nota2, float nota3) {
    // Si alguna nota baja de 14, reprueba automáticamente
    if (nota1 < 14.0f || nota2 < 14.0f || nota3 < 14.0f) {
        return false; 
    }
    return true; 
}