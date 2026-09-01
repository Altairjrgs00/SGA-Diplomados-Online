import os

# =====================================================================
# 1. ARQUITECTURA DE CLASES (MODELADO DEL UML. Herencia Y Reglas)
# =====================================================================

class Persona:
    def __init__(self, cedula, nombre, correo):
        # El guion bajo protege los datos dentro de la clase (Encapsulamiento)
        self._cedula = str(cedula)
        self._nombre = str(nombre)
        self._correo = str(correo)

    def mostrar_datos(self):
        return f"Cédula: {self._cedula} | Nombre: {self._nombre} | Correo: {self._correo}"


class Alumno(Persona):
    def __init__(self, cedula, nombre, correo, tipo_programa):
        # Con super() traemos los datos del padre Persona para no repetir código
        super().__init__(cedula, nombre, correo)
        self._tipo_programa = tipo_programa  # 'Curso', 'Diplomado' o 'Bootcamp'
        self._notas = []                     # Lista vacía para ir guardando los decimales

    def poner_nota(self, nota):
        self._notas.append(float(nota))

    def borrar_ultima_nota(self):
        # Lógica de Pila: si hay notas guardadas, saca la última nota ingresada
        if self._notas:
            return self._notas.pop()         
        return None

    def sacar_promedio(self):
        if not self._notas:
            return 0.0
        # Sumamos todas las notas y dividimos entre la cantidad
        return sum(self._notas) / len(self._notas)

    def chequear_aprobacion(self):
        promedio_final = self.sacar_promedio()
        
        # POLIMORFISMO: Cambiamos las reglas automáticamente según la modalidad
        if self._tipo_programa == "Curso":
            return promedio_final >= 10.0
            
        elif self._tipo_programa == "Diplomado":
            return promedio_final >= 14.0
            
        elif self._tipo_programa == "Bootcamp":
            if not self._notas:
                return False
            # Regla estricta: Promedio de 14 y que no tenga ninguna nota aplazada
            todo_bien = True
            for n in self._notas:
                if n < 14.0:
                    todo_bien = False
            return promedio_final >= 14.0 and todo_bien
            
        return False


class Profesor(Persona):
    def __init__(self, cedula, nombre, correo, materia):
        super().__init__(cedula, nombre, correo)
        self._materia = str(materia)


# =====================================================================
# 2. MOTOR DEL SISTEMA (PERSISTENCIA DE ARCHIVOS TXT)
# =====================================================================

class GestorAcademico:
    def __init__(self):
        # Usamos diccionarios para manejar las colecciones en memoria de forma limpia
        self.alumnos = {}
        self.profesores = {}
        self.pila_deshacer = []  # Guarda las cédulas en orden para simular el Ctrl+Z
        self.cargar_datos()

    def guardar_datos(self):
        # Guardamos alumnos en el archivo txt separando los datos con punto y coma
        with open("alumnos.txt", "w", encoding="utf-8") as f:
            for alu in self.alumnos.values():
                notas_str = ",".join(map(str, alu._notas))
                f.write(f"{alu._cedula};{alu._nombre};{alu._correo};{alu._tipo_programa};{notas_str}\n")
        
        # Guardamos profesores en su propio bloc de notas
        with open("profesores.txt", "w", encoding="utf-8") as f:
            for prof in self.profesores.values():
                f.write(f"{prof._cedula};{prof._nombre};{prof._correo};{prof._materia}\n")

    def cargar_datos(self):
        # Si el archivo existe en la computadora, recuperamos los alumnos guardados
        if os.path.exists("alumnos.txt"):
            with open("alumnos.txt", "r", encoding="utf-8") as f:
                for linea in f:
                    partes = linea.strip().split(";")
                    if len(partes) >= 4:
                        cedula, nombre, correo, programa = partes[0], partes[1], partes[2], partes[3]
                        alu = Alumno(cedula, nombre, correo, programa)
                        if len(partes) == 5 and partes[4]:
                            for n in partes[4].split(","):
                                alu.poner_nota(float(n))
                        self.alumnos[cedula] = alu

        # Si el archivo existe, recuperamos los profesores guardados
        if os.path.exists("profesores.txt"):
            with open("profesores.txt", "r", encoding="utf-8") as f:
                for linea in f:
                    partes = linea.strip().split(";")
                    if len(partes) == 4:
                        cedula, nombre, correo, materia = partes[0], partes[1], partes[2], partes[3]
                        self.profesores[cedula] = Profesor(cedula, nombre, correo, materia)

    def generar_reporte_graduados(self):
        # Recorremos la cola de alumnos y escribimos a los aprobados en certificados_pendientes.txt
        with open("certificados_pendientes.txt", "w", encoding="utf-8") as f:
            for alu in self.alumnos.values():
                if alu.chequear_aprobacion():
                    f.write(f"GRADUADO: {alu._nombre} ({alu._cedula}) | Programa: {alu._tipo_programa} | Promedio: {alu.sacar_promedio():.2f}\n")


# =====================================================================
# 3. INTERFAZ DE USUARIO (MENÚ CONTINUO DE CONSOLA)
# =====================================================================

def ejecutar_sistema():
    gestor = GestorAcademico()

    while True:
        print("\n==========================================")
        print("    SGA-DO: SISTEMA DIPLOMADOSONLINE     ")
        print("==========================================")
        print("1. Registrar Alumno")
        print("2. Registrar Profesor")
        print("3. Registrar notas a un Alumno")
        print("4. Deshacer Última Registro de Nota")
        print("5. Generar Cola de Certificados")
        print("6. Mostrar Reporte General")
        print("7. Salir del Sistema")
        print("==========================================")
        
        # El try/except atrapa el error si meten una letra para que el menú no explote
        try:
            opcion = int(input("Seleccione una opción (1-7): "))
        except ValueError:
            print("\n[Error] Pusiste una letra, por favor pon un número del 1 al 7.")
            continue

        if opcion == 1:
            cedula = input("Cédula o ID: ")
            nombre = input("Nombre Completo: ")
            correo = input("Correo Electrónico: ")
            print("Seleccione Modalidad: 1) Curso | 2) Diplomado | 3) Bootcamp")
            opc_prog = input("Opción: ")
            programa = "Curso" if opc_prog == "1" else "Diplomado" if opc_prog == "2" else "Bootcamp"
            
            gestor.alumnos[cedula] = Alumno(cedula, nombre, correo, programa)
            gestor.guardar_datos()
            print("\n¡Alumno registrado y respaldado en TXT con éxito!")

        elif opcion == 2:
            cedula = input("Cédula o ID: ")
            nombre = input("Nombre Completo: ")
            correo = input("Correo Electrónico: ")
            materia = input("Materia o Especialidad: ")
            
            gestor.profesores[cedula] = Profesor(cedula, nombre, correo, materia)
            gestor.guardar_datos()
            print("\n¡Profesor registrado y respaldado en TXT con éxito!")

        elif opcion == 3:
            cedula = input("Ingrese la Cédula del Alumno: ")
            if cedula in gestor.alumnos:
                try:
                    nota = float(input("Ingrese la calificación: "))
                    gestor.alumnos[cedula].poner_nota(nota)
                    gestor.pila_deshacer.append(cedula)  # Anotamos en la pila quién recibió la nota
                    gestor.guardar_datos()
                    print("\n¡Calificación cargada exitosamente!")
                except ValueError:
                    print("\n[Error] La nota debe ser un número decimal válido.")
            else:
                print("\n[Error] El estudiante no se encuentra registrado.")

        elif opcion == 4:
            # Lógica de Pila: el pop saca el último ID registrado en el historial
            if gestor.pila_deshacer:
                ultima_cedula = gestor.pila_deshacer.pop()
                if ultima_cedula in gestor.alumnos:
                    nota_borrada = gestor.alumnos[ultima_cedula].borrar_ultima_nota()
                    gestor.guardar_datos()
                    print(f"\n¡Acción Deshecha! Se eliminó la nota ({nota_borrada}) del estudiante ID: {ultima_cedula}.")
            else:
                print("\nNo existen modificaciones de notas en esta sesión para deshacer.")

        elif opcion == 5:
            cedula = input("Ingrese la Cédula del Alumno: ")
            if cedula in gestor.alumnos:
                alu = gestor.alumnos[cedula]
                condicion = "APROBADO" if alu.chequear_aprobacion() else "REPROBADO / EN CURSO"
                print(f"\n--- REPORTE ACADÉMICO: {alu._nombre.upper()} ---")
                print(f"Programa: {alu._tipo_programa}")
                print(f"Historial de Notas: {alu._notas}")
                print(f"Promedio Obtenido: {alu.sacar_promedio():.2f}")
                print(f"Estado de Certificación: {condicion}")
            else:
                print("\n[Error] El estudiante no se encuentra registrado.")

        elif opcion == 6:
            gestor.generar_reporte_graduados()
            print("\n¡Reporte Procesado! Se generó el archivo 'certificados_pendientes.txt' con los graduados.")

        elif opcion == 7:
            print("\nGuardando estados finales... ¡Operación finalizada con éxito!")
            break
        else:
            print("\nOpción fuera de rango. Seleccione del 1 al 7.")

if __name__ == "__main__":
    ejecutar_sistema()


