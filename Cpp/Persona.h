#ifndef PERSONA_H
#define PERSONA_H

#include <string>

class Persona {
protected:
    // Usamos protected para que las clases hijas (Alumno y Profesor) puedan acceder a ellos
    std::string cedula;
    std::string nombre;
    std::string correo;

public:
    // Constructor
    Persona(std::string _cedula, std::string _nombre, std::string _correo);
    
    virtual ~Persona();

    // Getters (Métodos consultores)
    std::string getCedula() const;
    std::string getNombre() const;
    std::string getCorreo() const;
};

#endif