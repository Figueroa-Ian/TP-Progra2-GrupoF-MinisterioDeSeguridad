# Sistema de Gestión del Ministerio de Seguridad - Back-End API

Este repositorio contiene el código fuente del **Back-End** para el Trabajo Práctico Final de la materia Programación 2 (Instituto Nacional Superior del Profesorado Técnico). 

El sistema permite gestionar sucursales, personal de vigilancia, jueces, delincuentes, bandas organizadas y expedientes judiciales a través de una API RESTful, aplicando los pilares de la Programación Orientada a Objetos y los principios SOLID.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 21
* **Framework Principal:** Spring Boot 4.1.1
* **Persistencia:** Spring Data JPA e Hibernate
* **Seguridad:** Spring Security
* **Base de Datos:** MySQL
* **Gestor de Dependencias:** Maven
* **Utilidades:** Lombok (generación de boilerplate)

## 🏗️ Arquitectura del Sistema

El proyecto sigue una arquitectura en capas estrictamente delimitadas, donde cada capa interactúa únicamente con la inmediata inferior:

1. **Controlador (`controller`):** Recibe las peticiones HTTP, delega la lógica y devuelve respuestas en formato JSON.
2. **Servicio (`services`):** Contiene la lógica de negocio y validaciones. Se define una interfaz por entidad (ej. `IJuezService`).
3. **Repositorio (`repository`):** Capa de acceso a datos utilizando interfaces de Spring Data JPA.
4. **Modelo (`models`):** Entidades JPA mapeadas directamente a las tablas de la base de datos MySQL.
5. **Seguridad (`security`):** Filtros de autenticación, autorización por rol y protección contra ataques.
6. **Excepciones (`exceptions`):** Manejo global de errores (vía `@RestControllerAdvice`) traduciendo excepciones de negocio a códigos de estado HTTP.

### Principios POO y SOLID Aplicados
* **Herencia y Polimorfismo:** `Juez`, `Delincuente` y `Vigilante` extienden de la clase base `Persona` utilizando la estrategia de herencia `JOINED` en JPA.
* **Responsabilidad Única (SRP):** Cada servicio gestiona una única entidad.
* **Segregación de Interfaces (ISP) e Inversión de Dependencias (DIP):** Los controladores dependen de interfaces pequeñas (`IJuezService`) y Spring inyecta la implementación concreta.

## 🔒 Seguridad

El sistema implementa múltiples capas de seguridad mediante **Spring Security**:
* **Autorización por Roles:** Accesos restringidos según el rol (Administrador, Investigador, Vigilante).
* **Defensa contra Fuerza Bruta:** `LoginAttemptService` bloquea cuentas (15 min) tras 5 intentos fallidos y bloquea IPs tras 20 intentos.
* **Contraseñas Cifradas:** Uso de `BCryptPasswordEncoder` con salt.
* **Fijación de Sesión:** Creación de nuevas sesiones seguras en cada inicio de sesión.
* **Prevención SQL Injection:** Uso de Spring Data JPA y consultas parametrizadas.

## 🗄️ Base de Datos

El modelo de datos relacional modela el dominio del proceso judicial y el control de sucursales:
* Herencia de Personas (`personas`, `jueces`, `vigilantes`, `delincuentes`).
* Relaciones transaccionales (`contrataciones`, `expedientes`).
* Catálogos de tipificación penal (`delitos`, `tipo_juridico`, `tipo_accion`, `tipo_intencion`).

📊 **[Ver Diagrama Entidad-Relación (DER)](https://drive.google.com/file/d/1yA2N_jhj2BfOzbV2gUOXUBE9ivpSwOe8/view?usp=sharing)**

## 👥 Equipo de Desarrollo (Grupo F)
* Figueroa Milagros
* Figueroa Ian
* Fernandez Mayra
