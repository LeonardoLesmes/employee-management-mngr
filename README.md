# Sistema de Gestión de Empleados - Backend

Una aplicación integral de Spring Boot para gestionar empleados, solicitudes de acceso a sistemas y asignaciones de computadoras en una organización. El sistema implementa principios de arquitectura hexagonal y proporciona APIs RESTful para todas las operaciones.

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitectura](#arquitectura)
- [Prerrequisitos](#prerrequisitos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Documentación de la API](#documentación-de-la-api)
- [Mapa de Permisos de Roles](#mapa-de-permisos-de-roles)
- [Esquema de Base de Datos](#esquema-de-base-de-datos)
- [Campos Automáticos de Fecha](#campos-automáticos-de-fecha)
- [Formateo de Código](#formateo-de-código)
- [Seguridad](#seguridad)
- [Manejo de Errores](#manejo-de-errores)
- [Contribuir](#contribuir)

## 🎯 Descripción General

El Sistema de Gestión de Empleados proporciona una solución completa para gestionar:

- **Gestión de Empleados**: Crear empleados, asignar roles, seguimiento de estados
- **Gestión de Solicitudes de Acceso**: Administrar solicitudes de acceso a sistemas con flujos de aprobación
- **Gestión de Asignaciones de Computadoras**: Manejar la asignación y seguimiento de computadoras
- **Gestión de Roles y Sistemas**: Administrar roles organizacionales y sistemas
- **Autenticación y Autorización**: Sistema de seguridad basado en JWT

### Características Principales

- ✅ Arquitectura Hexagonal (Puertos y Adaptadores)
- ✅ Endpoints de API RESTful
- ✅ Autenticación JWT
- ✅ Capacidades avanzadas de filtrado y búsqueda
- ✅ Consultas por rango de IDs con filtrado opcional
- ✅ Manejo integral de excepciones
- ✅ Integración con PostgreSQL
- ✅ Automatización de formato de código

## 🛠 Stack Tecnológico

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.0** - Framework
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos
- **Lombok** - Generación de código
- **Jakarta Persistence API (JPA)** - ORM
- **Maven** - Herramienta de construcción

### Seguridad y Autenticación
- **JWT (JSON Web Tokens)** - Autenticación basada en tokens
- **BCrypt** - Hash de contraseñas

### Herramientas de Desarrollo
- **Plugin Maven Formatter** - Formato de código
- **Spring Boot DevTools** - Utilidades de desarrollo

## 🏗 Arquitectura

La aplicación sigue la **Arquitectura Hexagonal** (patrón de Puertos y Adaptadores) con una clara separación de responsabilidades:

```
src/main/java/com/employee_management_mngr/
├── auth/                           # Módulo de autenticación
│   ├── application/               # Lógica de negocio
│   │   ├── dtos/                 # Objetos de transferencia de datos
│   │   ├── orchestrators/        # Orquestadores de negocio
│   │   ├── ports/                # Definiciones de interfaces
│   │   └── services/             # Servicios de negocio centrales
│   ├── domain/                   # Entidades de dominio
│   └── infrastructure/           # Adaptadores externos
│       ├── inputs/               # Controladores REST
│       └── outputs/              # Repositorios de base de datos
├── employee/                      # Módulo de gestión de empleados
│   ├── application/
│   │   ├── exceptions/           # Excepciones personalizadas
│   │   ├── orchestrator/         # Orquestadores de negocio
│   │   ├── ports/               # Interfaces (entrada/salida)
│   │   └── services/            # Servicios de lógica de negocio
│   ├── domain/                   # Entidades de dominio y objetos de valor
│   │   ├── employee/            # Entidad empleado
│   │   └── role/                # Entidad rol y enums
│   └── infrastructure/
│       └── adapters/
│           ├── inputs/          # Controladores y DTOs
│           └── outputs/         # Implementaciones de repositorio
├── access/                        # Gestión de solicitudes de acceso
│   ├── application/
│   ├── domain/
│   └── infrastructure/
├── computer/                      # Gestión de asignaciones de computadoras
│   ├── application/
│   ├── domain/
│   └── infrastructure/
└── EmployeeManagementApplication.java  # Clase principal de la aplicación
```

### Entidades de Dominio

#### Employee (Empleado)
- **ID**: Identificador único
- **Name**: Nombre completo del empleado
- **Email**: Dirección de email única
- **Department**: Asignación de departamento
- **Role**: Rol asociado (Desarrollador, Admin, etc.)
- **Status**: PENDING, ACTIVE, INACTIVE
- **AssignedBy**: ID de la persona que asignó el rol
- **Timestamps**: Fechas de creación y asignación de rol

#### Role (Rol)
- **ID**: Identificador único
- **Type**: Tipo de rol enum (ADMIN, DEVELOPER, QA, etc.)
- **Description**: Descripción del rol
- **CanLogin**: Si el rol puede autenticarse

#### AccessRequest (Solicitud de Acceso)
- **ID**: Identificador único
- **Employee**: Empleado asociado
- **System**: Sistema objetivo
- **Status**: PENDING, APPROVED, REJECTED, CANCELLED
- **AssignedBy**: Empleado que asignó la solicitud
- **Timestamps**: Fechas de solicitud y resolución

#### ComputerAssignment (Asignación de Computadora)
- **ID**: Identificador único
- **Employee**: Empleado asignado
- **Computer**: Computadora asignada
- **Status**: PENDING, APPROVED, REJECTED, CANCELED
- **AssignedBy**: Empleado que hizo la asignación
- **Timestamps**: Fechas de solicitud, resolución, asignación y devolución

## 📋 Prerrequisitos

- **Java 21** o superior
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git**

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone <repository-url>
cd employee-management-mngr
```

### 2. Configuración de Variables de Entorno
El proyecto utiliza un archivo `.env` para la configuración. Se proporciona un archivo `.env.example` como plantilla:

1. **Copia el archivo de ejemplo**:
```bash
copy .env.example .env
```

2. **Edita el archivo `.env`** con tu configuración:
```
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/employee_management_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_database_password

# JPA/Hibernate Configuration
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_here_base64_encoded
JWT_EXPIRATION=60
```
### 3. Construir la Aplicación
```bash
mvn clean install
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

### 5. Formateo de Código
Formatear código usando el plugin Maven formatter:
```bash
mvn formatter:format
```

## 📖 Documentación de la API

### Endpoints de Autenticación

#### Iniciar Sesión
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
-H "Content-Type: application/json" \
-d '{
    "email": "user@example.com",
    "password": "password123"
}'
```

#### Establecer Contraseña
```bash
curl -X POST "http://localhost:8080/api/auth/set-password" \
-H "Content-Type: application/json" \
-d '{
    "email": "user@example.com",
    "password": "newpassword123"
}'
```

#### Validar Token
```bash
curl -X POST "http://localhost:8080/api/auth/validate-token" \
-H "Content-Type: application/json" \
-d '{
    "token": "your-jwt-token"
}'
```

### Gestión de Empleados

#### Crear Empleado
```bash
curl -X POST "http://localhost:8080/api/employees" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "name": "John Doe",
    "email": "john.doe@company.com",
    "department": "Engineering",
    "roleId": 2,
    "assignedBy": 1
}'
```

#### Obtener Empleado por ID
```bash
curl -X GET "http://localhost:8080/api/employees/1" \
-H "Authorization: Bearer your-jwt-token"
```

#### Obtener Empleados por ID del Asignador
```bash
curl -X GET "http://localhost:8080/api/employees/assigned-by/1" \
-H "Authorization: Bearer your-jwt-token"
```

#### Actualizar Estado de Empleado
```bash
curl -X PUT "http://localhost:8080/api/employees/1/status" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "status": "APPROVED"
}'
```

### Gestión de Solicitudes de Acceso

#### Crear Solicitud de Acceso
```bash
curl -X POST "http://localhost:8080/api/access-requests" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "employeeId": 1,
    "systemIds": [1, 2, 3],
    "assignedById": 2
}'
```

#### Obtener Solicitudes de Acceso por Empleado
```bash
# Sin filtro de assignedById
curl -X GET "http://localhost:8080/api/access-requests/employee/1" \
-H "Authorization: Bearer your-jwt-token"

# Con filtro de assignedById
curl -X GET "http://localhost:8080/api/access-requests/employee/1?assignedById=2" \
-H "Authorization: Bearer your-jwt-token"
```

#### Obtener Solicitudes de Acceso por ID del Asignador
```bash
curl -X GET "http://localhost:8080/api/access-requests/assigned-by/1" \
-H "Authorization: Bearer your-jwt-token"
```

#### Actualizar Estado de Solicitud de Acceso
```bash
curl -X PUT "http://localhost:8080/api/access-requests/1/status" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "status": "APPROVED"
}'
```

### Gestión de Asignaciones de Computadoras

#### Crear Asignación de Computadora
```bash
curl -X POST "http://localhost:8080/api/computer-assignments" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "employeeId": 1,
    "computerId": 2,
    "assignedById": 3
}'
```

#### Actualizar Estado de Asignación de Computadora
```bash
curl -X PUT "http://localhost:8080/api/computer-assignments/1/status" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "status": "APPROVED"
}'
```

#### Obtener Asignaciones de Computadoras por Empleado
```bash
curl -X GET "http://localhost:8080/api/computer-assignments/employee/1" \
-H "Authorization: Bearer your-jwt-token"
```

#### Obtener Asignaciones de Computadoras por ID del Asignador
```bash
curl -X GET "http://localhost:8080/api/computer-assignments/assigned-by/1" \
-H "Authorization: Bearer your-jwt-token"
```

#### Obtener Computadoras Disponibles
```bash
curl -X GET "http://localhost:8080/api/computer-assignments/available" \
-H "Authorization: Bearer your-jwt-token"
```

### Gestión de Roles

#### Obtener Todos los Roles (Solo Roles Sin Login)
```bash
curl -X GET "http://localhost:8080/api/roles" \
-H "Authorization: Bearer your-jwt-token"
```

#### Obtener Rol por ID
```bash
curl -X GET "http://localhost:8080/api/roles/1" \
-H "Authorization: Bearer your-jwt-token"
```

### Gestión de Sistemas

#### Obtener Todos los Sistemas
```bash
curl -X GET "http://localhost:8080/api/systems" \
-H "Authorization: Bearer your-jwt-token"
```

#### Obtener Sistemas Disponibles para un Rol
```bash
curl -X GET "http://localhost:8080/api/systems/available/23" \
-H "Authorization: Bearer your-jwt-token"
```

### Mapa de Permisos de Roles

La aplicación implementa un sistema de validación de permisos basado en roles que determina qué sistemas pueden ser accedidos por cada rol. Esto se gestiona a través del componente `RolePermissionMap`.

#### Características del Mapa de Permisos

- Cada rol tiene asignado un conjunto específico de sistemas a los que puede acceder
- Las solicitudes de acceso se validan contra este mapa para prevenir accesos no autorizados
- El sistema rechaza automáticamente las solicitudes para sistemas no permitidos para el rol del empleado

#### Ejemplo de Asignaciones

```
DEV_JUNIOR (ID: 23) - Acceso a sistemas: 2, 3, 5, 7, 9
DEV_SEMI (ID: 24) - Acceso a sistemas: 2, 3, 5, 7, 9
```

#### Validación de Permisos

La validación ocurre automáticamente cuando:
- Se crea una solicitud de acceso a un sistema
- Un empleado intenta acceder a un sistema no autorizado para su rol

#### Endpoints de Permisos de Roles

##### Obtener todos los mapas de permisos
```bash
curl -X GET "http://localhost:8080/api/role-permissions" \
-H "Authorization: Bearer your-jwt-token"
```

##### Obtener permisos para un rol específico
```bash
curl -X GET "http://localhost:8080/api/role-permissions/23" \
-H "Authorization: Bearer your-jwt-token"
```

##### Verificar permiso específico
```bash
curl -X GET "http://localhost:8080/api/role-permissions/check/23/5" \
-H "Authorization: Bearer your-jwt-token"
```

## 🗄 Esquema de Base de Datos

### Tablas Principales

- **employees**: Información de empleados y asignaciones de roles
- **roles**: Roles disponibles en la organización
- **access_requests**: Solicitudes de acceso a sistemas
- **computer_assignments**: Seguimiento de asignación de computadoras
- **computers**: Computadoras disponibles
- **systems**: Sistemas disponibles para acceso
- **credentials**: Credenciales de autenticación

### Valores de Estado

#### Estado de Empleado
- `PENDING`: Recién creado, esperando activación
- `APPROVED`: Empleado aprobado y activo
- `REJECTED`: Empleado rechazado
- `INACTIVE`: Empleado inactivo

#### Estado de Solicitud de Acceso
- `PENDING`: Estado inicial cuando se crea la solicitud
- `APPROVED`: Solicitud ha sido aprobada
- `REJECTED`: Solicitud ha sido rechazada
- `CANCELLED`: Solicitud ha sido cancelada

#### Estado de Asignación de Computadora
- `PENDING`: Estado inicial cuando se crea la asignación
- `APPROVED`: Asignación ha sido aprobada
- `REJECTED`: Asignación ha sido rechazada
- `CANCELED`: Asignación ha sido cancelada

#### Estado de Computadora
- `AVAILABLE`: Computadora está disponible para asignación
- `ASSIGNED`: Computadora está actualmente asignada a un empleado

### Tipos de Roles Disponibles

#### Roles con Capacidad de Login (Administrativos)
- `ADMIN`: Administrador del Sistema
- `IT`: Soporte de TI
- `LEADER_DEV`: Líder del Equipo de Desarrollo
- `LEADER_QA`: Líder del Equipo de QA
- `LEADER_PRODUCT`: Líder del Equipo de Producto
- `LEADER_AGILE`: Líder Ágil
- `LEADER_UX`: Líder del Equipo de UX
- `LEADER_DEVOPS`: Líder del Equipo de DevOps

#### Roles de Empleado Estándar (Sin Login)
- `DEV_JUNIOR`: Desarrollador Junior
- `DEV_SEMI`: Desarrollador Semi-Senior
- `DEV_SENIOR`: Desarrollador Senior
- `TECH_LEAD`: Líder Técnico
- `QA_ANALYST`: Analista de Aseguramiento de Calidad
- `QA_AUTOMATION`: Ingeniero de Automatización QA
- `DEVOPS_ENGINEER`: Ingeniero DevOps
- `SRE`: Ingeniero de Confiabilidad del Sitio
- `UX_DESIGNER`: Diseñador de Experiencia de Usuario
- `UI_DESIGNER`: Diseñador de Interfaz de Usuario
- `SCRUM_MASTER`: Scrum Master
- `AGILE_COACH`: Coach Ágil
- `PRODUCT_OWNER`: Propietario del Producto
- `PROJECT_MANAGER`: Gerente de Proyecto

## 🎨 Formateo de Código

El proyecto utiliza el Plugin Maven Formatter para mantener un estilo de código consistente:

### Configuración
```xml
<plugin>
    <groupId>net.revelc.code.formatter</groupId>
    <artifactId>formatter-maven-plugin</artifactId>
    <version>2.22.0</version>
    <configuration>
        <encoding>UTF-8</encoding>
        <lineEnding>LF</lineEnding>
        <compilerSource>21</compilerSource>
        <compilerCompliance>21</compilerCompliance>
        <compilerTargetPlatform>21</compilerTargetPlatform>
    </configuration>
</plugin>
```

### Uso
```bash
# Formatear todo el código
mvn formatter:format

# Validar formato
mvn formatter:validate
```

## 🔐 Seguridad

### Autenticación
Todos los endpoints excepto `/api/auth/*` requieren un token JWT válido en el header de Authorization:
```
Authorization: Bearer your-jwt-token
```

### Seguridad de Contraseñas
- Las contraseñas se hashean usando BCrypt
- Los tokens JWT tienen expiración configurable
- Control de acceso basado en roles implementado

### Seguridad de la API
- CORS configurado para solicitudes de origen cruzado
- Prevención de inyección SQL a través de JPA
- Validación de entrada en todos los endpoints

## ⚠️ Manejo de Errores

### Excepciones Personalizadas

La aplicación incluye manejo robusto de errores con excepciones personalizadas:

#### ComputerAssignmentException
```java
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ComputerAssignmentException extends RuntimeException {
    public ComputerAssignmentException(String message) {
        super(message);
    }
}
```

#### EmployeeNotApprovedException
```java
@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeNotApprovedException extends RuntimeException {
    public EmployeeNotApprovedException(Integer employeeId) {
        super("El empleado con ID " + employeeId + " no está aprobado");
    }
}
```

#### UnauthorizedSystemAccessException
```java
@ResponseStatus(HttpStatus.CONFLICT)
public class UnauthorizedSystemAccessException extends RuntimeException {
    public UnauthorizedSystemAccessException(Integer roleId, Integer systemId) {
        super("El rol " + roleId + " no tiene permiso para acceder al sistema " + systemId);
    }
}
```

### Respuestas de Error Estándar

Todas las respuestas de error siguen un formato consistente:

```json
{
    "timestamp": "2025-06-03T10:15:30.000Z",
    "status": 400,
    "error": "Bad Request",
    "message": "Employee with ID 999 not found",
    "path": "/api/employees/999"
}
```

### Códigos de Estado HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **400 Bad Request**: Datos de entrada inválidos
- **401 Unauthorized**: Token JWT inválido o faltante
- **403 Forbidden**: Acceso denegado
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error interno del servidor

### Validación de Entrada

- Validación de email con formato correcto
- Validación de rangos de ID (startId ≤ endId)
- Validación de existencia de entidades relacionadas
- Validación de estados válidos para transiciones

## Campos Automáticos de Fecha

Todas las entidades principales incluyen un campo `created_at` que se genera automáticamente al crear nuevos registros:

### @PrePersist

Todas las entidades utilizan el método `@PrePersist` para inicializar automáticamente los campos de fecha al momento de la creación:

```java
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@PrePersist
protected void onCreate() {
    createdAt = LocalDateTime.now();
}
```

Esta característica asegura la consistencia en las marcas de tiempo y elimina la necesidad de establecer estas fechas manualmente en los servicios.
