#include "Profesor.h"

// Al crear al Profesor, le pasamos la cédula, nombre y correo directamente al constructor de Persona
Profesor::Profesor(std::string _cedula, std::string _nombre, std::string _correo, std::string _materia) 
    : Persona(_cedula, _nombre, _correo) {
    
    // Solo nos preocupamos por guardar los datos exclusivos del profesor
       materia = _materia;
}

std::string Profesor::getMateria() const {
    return materia;
}