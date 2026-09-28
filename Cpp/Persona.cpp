#include "Persona.h"

// Implementación del Constructor
Persona::Persona(std::string _cedula, std::string _nombre, std::string _correo) {
    cedula = _cedula;
    nombre = _nombre;
    correo = _correo;
}

Persona::~Persona() {
}

// Implementación de los Getters
std::string Persona::getCedula() const {
    return cedula;
}

std::string Persona::getNombre() const {
    return nombre;
}

std::string Persona::getCorreo() const {
    return correo;
}