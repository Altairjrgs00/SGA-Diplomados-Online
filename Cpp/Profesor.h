#ifndef PROFESOR_H
#define PROFESOR_H

#include "Persona.h" // Importamos la clase base
#include <string>

// Al poner ": public Persona", le decimos que herede todo de Persona
class Profesor : public Persona {
private:
    std::string materia;

public:
    // El constructor pide los datos básicos del profesor
    Profesor(std::string _cedula, std::string _nombre, std::string _correo, std::string _materia);
    
    // Métodos para consultar los datos nuevos
    std::string getMateria() const;
};

#endif