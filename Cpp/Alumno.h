#ifndef ALUMNO_H
#define ALUMNO_H

#include <string>

class Alumno {
private:
    std::string cedula;
    std::string nombre;
    double notas[3];       // Espacio para un maximo de 3 notas (evaluaciones)
    int cantidadNotas;     // Controla cuantas notas reales tiene registradas (0 a 3)

public:
    // Constructor vacio
    Alumno();

    // Constructor para registrar un alumno con sus datos basicos
    Alumno(std::string _cedula, std::string _nombre);

    // Metodos para gestionar las notas dinamicamente
    bool agregarNota(double nuevaNota); // Agrega una nota al arreglo si hay espacio
    bool deshacerUltimaNota();          // Elimina la ultima nota agregada por error

    // Metodos para obtener los datos (Getters)
    std::string getCedula() const;
    std::string getNombre() const;
    int getCantidadNotas() const;
    double getNota(int indice) const;

    // Metodos para calcular el rendimiento (basado en las notas actuales)
    double calcularPromedio() const;
    std::string determinarEstado() const;
};

#endif
