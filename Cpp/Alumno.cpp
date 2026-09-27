#include "Alumno.h"
#include <iostream> // Agregamos esto para poder usar std::cout y mostrar el mensaje de error

// Constructor vacio: Inicializa los textos en blanco y el contador en cero
Alumno::Alumno() {
    cedula = "";
    nombre = "";
    cantidadNotas = 0;
    // Llenamos el arreglo con 0.0 usando un ciclo basico
    for (int i = 0; i < 4; i++) {
        notas[i] = 0.0;
    }
}

// Usamos un Constructor: Para crear al alumno con su cedula y nombre
Alumno::Alumno(std::string _cedula, std::string _nombre) {
    cedula = _cedula;
    nombre = _nombre;
    cantidadNotas = 0;
    for (int i = 0; i < 4; i++) {
        notas[i] = 0.0;
    }
}

// Para agregar una nota
bool Alumno::agregarNota(double nuevaNota) {
    // Si ya tiene 3 notas, frena el programa y avisa al usuario
    if (cantidadNotas >= 3) {
        std::cout << "[ERROR] No se puede agregar nota. El alumno ya tiene las 3 notas cargadas." << std::endl;
        return false; 
    }
    
    // Si tiene menos de 3, guarda la nota en la posicion que diga el contador
    notas[cantidadNotas] = nuevaNota;
    cantidadNotas++; // Sumamos 1 al contador para la siguiente nota
    return true;     
}

// Funcion deshacer: Si el usuario se equivoca, borramos la ultima nota
bool Alumno::deshacerUltimaNota() {
    if (cantidadNotas > 0) {
        cantidadNotas--; // Al restar 1, la ultima nota queda eliminada para el sistema
        std::cout << "Ultima nota eliminada con exito." << std::endl;
        return true;     
    }
    std::cout << "[ERROR] No hay notas registradas para deshacer." << std::endl;
    return false; 
}

// Funciones basicas para obtener los datos (Getters)
std::string Alumno::getCedula() const { 
    return cedula; 
}

std::string Alumno::getNombre() const { 
    return nombre; 
}

int Alumno::getCantidadNotas() const { 
    return cantidadNotas; 
}

// Calcula el promedio usando solo las notas guardadas
double Alumno::calcularPromedio() const {
    if (cantidadNotas == 0) return 0.0;
    
    double suma = 0.0;
    for (int i = 0; i < cantidadNotas; i++) {
        suma += notas[i];
    }
    return suma / cantidadNotas;
}

// Determina si aprueba o reprueba
std::string Alumno::determinarEstado() const {
    if (calcularPromedio() >= 9.5) { 
        return "Aprobado";
    }
    return "Reprobado";
}
