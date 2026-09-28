#ifndef ALUMNO_H
#define ALUMNO_H

#include "Persona.h"
#include "ProgramaAcademico.h"
#include <vector>

class Alumno : public Persona {
private:
    ProgramaAcademico* programa; 
    std::vector<float> notas;    // Usamos vector para manejar fácilmente la lista de notas

public:
    // Constructor
    Alumno(std::string _cedula, std::string _nombre, std::string _correo, ProgramaAcademico* _programa);
    
        ~Alumno() override;

    // Métodos para las notas
    void agregarNota(float nota);
    void removerUltimaNota(); // Apoyo para la función deshacer (LIFO)
    
    // Método que ejecuta el polimorfismo
    bool estaAprobado(); 
    
    // Getters
    int getCantidadNotas() const;
    float getNota(int indice) const;
    std::string getNombrePrograma() const { return programa->getTipo(); }
};

#endif