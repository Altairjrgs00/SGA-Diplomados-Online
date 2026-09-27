#ifndef GESTOR_H
#define GESTOR_H

#include <string>
#include "Alumno.h"

class Gestor {
private:
    std::string nombreArchivo; // Aqui guardaremos el nombre del archivo (ej. "alumnos.txt")

public:
    // Constructor: Define con que archivo de texto va a trabajar el sistema
    Gestor(std::string _nombreArchivo);

    // Metodo para guardar un alumno nuevo en el archivo de texto
    bool guardarAlumno(const Alumno& alumno);

    // Metodo para buscar un alumno en el archivo usando su cedula
    bool buscarAlumnoPorCedula(std::string cedulaBuscar) const;

    // Metodo para mostrar el reporte de todos los alumnos en la consola
    void mostrarReporteGeneral() const;
};

#endif
