#ifndef PROGRAMAS_H
#define PROGRAMAS_H
#include <string>

class ProgramaAcademico {
public:
    virtual ~ProgramaAcademico();
    virtual bool evaluarAprobacion(float nota1, float nota2, float nota3) = 0;
    virtual std::string getTipo() = 0;
};

class Curso : public ProgramaAcademico {
public:
    bool evaluarAprobacion(float nota1, float nota2, float nota3) override;
    std::string getTipo() override { return "Curso"; }
};

class Diplomado : public ProgramaAcademico {
public:
    bool evaluarAprobacion(float nota1, float nota2, float nota3) override;
    std::string getTipo() override { return "Diplomado"; }
};

class Bootcamp : public ProgramaAcademico {
public:
    bool evaluarAprobacion(float nota1, float nota2, float nota3) override;
    std::string getTipo() override { return "Bootcamp"; }
};

#endif