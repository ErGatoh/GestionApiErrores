# CONTEXTO COMPLETO PARA CODEX — PERSONA 1
## Práctica 2: Diseño correcto de una API REST — Grupo 5: Tratamiento global de errores

> **Objetivo de este archivo:** transferir a Codex todo el contexto relevante de la conversación para que continúe ayudando **solo con la parte de la Persona 1** del trabajo.
> Codex debe respetar estrictamente el alcance indicado aquí y **no implementar las partes de la Persona 2 ni de la Persona 3** salvo que sea imprescindible para que la base de la Persona 1 compile o funcione.

---

# 1. CONTEXTO GENERAL DE LA PRÁCTICA

La práctica consiste en desarrollar una pequeña API REST con Spring Boot aplicando buenas prácticas profesionales.

El grupo asignado es:

- **Grupo 5 — Tratamiento global de errores**

El objetivo global del grupo completo será demostrar cómo gestionar correctamente errores y excepciones en una API REST.

La práctica exige, a nivel de equipo:

- Explicación técnica.
- Código.
- API funcionando.
- Peticiones reales.
- Ejemplo de mala práctica.
- Solución propuesta.
- Tratamiento centralizado de excepciones.
- Uso de `@ControllerAdvice` / `@RestControllerAdvice`.
- Uso de `@ExceptionHandler`.
- Al menos una excepción personalizada.
- Provocar al menos tres errores diferentes.
- Mantener una estructura coherente de respuesta de error.
- Entregar:
  - proyecto Spring Boot completo;
  - código fuente;
  - `README.md`;
  - material de exposición.

El profesor puede preguntar a cualquier miembro por cualquier parte del código, por lo que todo debe quedar comprensible y limpio.

---

# 2. REPARTO DEL EQUIPO

El equipo tiene tres personas.

## Persona 1 — ESTA ES MI PARTE

Debe construir la **base funcional de la API** y dejarla preparada para que las otras dos personas continúen.

Responsabilidades de Persona 1:

- Estructura del proyecto Spring Boot.
- Configuración Maven.
- Conexión con MySQL en Aiven.
- Definición de la estructura SQL mediante `database/schema.sql`.
- Entidad `Producto`.
- Repositorio JPA.
- Servicio con lógica CRUD básica.
- Controlador REST.
- Endpoints CRUD.
- Configuración mediante variables de entorno.
- Comprobación de conexión con la base de datos.
- Comprobación de persistencia.
- Pruebas básicas de la API.
- Estructura inicial del `README.md` si hace falta para que el equipo pueda arrancar.
- Dejar el repositorio listo para que Persona 2 y Persona 3 trabajen encima.

## Persona 2 — NO HACER

Responsable del tratamiento global de errores.

No debo implementar:

- `GlobalExceptionHandler`
- `ErrorResponse`
- `ProductoNoEncontradoException`
- `ProductoDuplicadoException`
- Excepciones personalizadas definitivas.
- `@RestControllerAdvice`
- `@ControllerAdvice`
- `@ExceptionHandler`
- Estructura JSON global de errores.
- Gestión profesional de `404`, `409`, etc.
- Comparativa final de mala práctica vs buena práctica de excepciones.

Persona 2 modificará posteriormente el servicio para sustituir excepciones genéricas por excepciones personalizadas.

## Persona 3 — NO HACER

Responsable de validaciones, DTOs, Bruno y documentación final.

No debo implementar como parte de mi trabajo:

- `ProductoCreateDTO`
- DTOs de entrada/salida definitivos.
- Bean Validation completa.
- `@NotBlank`
- `@NotNull`
- `@Positive`
- `@Valid`
- Gestión final de `400 Bad Request` por validaciones.
- Colección final de Bruno.
- Casos de prueba finales `400`, `404`, `409`.
- README final completo.
- Capturas de documentación.
- Material final de presentación.

Puedo dejar los paquetes `dto` y `exception` creados y vacíos para facilitar su trabajo.

---

# 3. STACK TECNOLÓGICO DEFINITIVO

El stack acordado para el proyecto es:

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **MySQL Connector/J**
- **MySQL 8.4 en Aiven**
- **Eclipse**
- **Maven**
- **Git**
- **GitHub**
- **Bruno** para pruebas HTTP, aunque la colección final es responsabilidad de Persona 3.
- `database/schema.sql` versionado en GitHub.
- `README.md`

Ya NO se utilizará:

- XAMPP como base definitiva.
- H2.
- phpMyAdmin local como solución principal.
- Postman.

Se habló inicialmente de H2 y XAMPP/MySQL local, pero esa decisión se descartó a favor de una base MySQL compartida en la nube.

---

# 4. BASE DE DATOS: DECISIÓN FINAL

La base de datos definitiva será:

- **MySQL 8.4**
- Alojada en **Aiven**
- Plan gratuito de Aiven
- Compartida por los tres miembros del equipo

Ventajas buscadas:

- Todos trabajan contra la misma base.
- No hace falta que cada miembro configure XAMPP/MySQL local.
- Los datos de prueba son visibles por todo el equipo.
- El proyecto es más fácil de compartir.
- GitHub almacena la definición de la estructura, no una copia física de la base.

Arquitectura:

```text
Persona 1 ─┐
Persona 2 ─┼── Spring Boot local ── JDBC ── MySQL 8.4 en Aiven
Persona 3 ─┘
```

IMPORTANTE:

- Las credenciales reales de Aiven **NO deben subirse a GitHub**.
- No poner usuario/contraseña reales directamente en `application.properties`.
- Usar variables de entorno.
- Mantener `database/schema.sql` para poder recrear la estructura de la base si fuera necesario.

---

# 5. AIVEN — CONTEXTO ACTUAL

Se creó inicialmente un servicio llamado:

```text
api-error-hub
```

El servicio usaba:

- MySQL 8.4
- plan `Free-1-1gb`
- 1 CPU
- 1 GB RAM
- 1 GB storage
- región europea

Hubo confusión inicial porque Aiven mostraba un plan `developer-1` dentro del trial de $50, con coste posterior de $5/mes.

Se corrigió el planteamiento:

- utilizar el plan **Free**
- evitar `developer-1`

También hubo un error al crear el proyecto de Aiven usando un nombre personal en vez del nombre del equipo.

Se decidió recrear el proyecto con un nombre de equipo.

Nombres sugeridos:

- Proyecto Aiven: `grupo4-api-rest`
- Servicio MySQL: `api-error-hub-db`

El nombre exacto final puede cambiar, pero los conceptos son:

- un proyecto compartido del equipo;
- un servicio MySQL gratuito;
- los compañeros pueden ser invitados al proyecto.

Antes de configurar Spring Boot, verificar en Aiven:

- que el servicio esté `Running`;
- host;
- port;
- database;
- username;
- password;
- requisitos SSL/certificados si Aiven los exige.

---

# 6. PROYECTO SPRING BOOT EXISTENTE

El proyecto Spring Boot real es:

```text
tema5
```

NO confundir con un proyecto Java normal anterior llamado `apiErrores`.

El proyecto `apiErrores` antiguo se creó/importó incorrectamente como proyecto Java normal y no es el que se debe continuar.

El proyecto válido es el que aparece en Eclipse como algo parecido a:

```text
tema5 [boot]
```

Características conocidas:

- Maven.
- Java 17.
- `src/main/java`
- `src/main/resources`
- `src/test/java`
- `Maven Dependencies`
- `pom.xml`
- `mvnw`
- `mvnw.cmd`

Paquete principal:

```text
com.trinitarias.grupo4.tema5
```

Clase principal:

```text
Tema5Application.java
```

Paquetes ya creados:

```text
com.trinitarias.grupo4.tema5.controller
com.trinitarias.grupo4.tema5.dto
com.trinitarias.grupo4.tema5.exception
com.trinitarias.grupo4.tema5.model
com.trinitarias.grupo4.tema5.repository
com.trinitarias.grupo4.tema5.service
```

También existe:

```text
src/main/resources/application.properties
```

Y se creó una carpeta:

```text
database/
```

con:

```text
database/schema.sql
```

---

# 7. ESTRUCTURA OBJETIVO DEL REPOSITORIO

La estructura recomendada para la parte de Persona 1 es:

```text
tema5/
├── database/
│   └── schema.sql
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/trinitarias/grupo4/tema5/
│   │   │       ├── controller/
│   │   │       │   └── ProductoController.java
│   │   │       ├── dto/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       │   └── Producto.java
│   │   │       ├── repository/
│   │   │       │   └── ProductoRepository.java
│   │   │       ├── service/
│   │   │       │   └── ProductoService.java
│   │   │       └── Tema5Application.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── .gitignore
├── README.md
├── pom.xml
├── mvnw
└── mvnw.cmd
```

Opcionalmente pueden existir:

```text
docs/
bruno/
```

pero Persona 1 no necesita preparar la colección final de Bruno.

---

# 8. `pom.xml` — DEPENDENCIAS NECESARIAS

El proyecto fue generado inicialmente con Spring Initializr.

Debe contener como mínimo:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

También se había seleccionado Validation:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Puede mantenerse aunque Persona 1 no implemente aún las validaciones finales.

H2 debe estar eliminado.

Si existe:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

eliminarla.

Después:

```text
Eclipse → clic derecho proyecto → Maven → Update Project
```

---

# 9. PROBLEMAS PREVIOS CON `pom.xml`

En Eclipse aparecieron cruces rojas en el `pom.xml`, especialmente en:

```xml
<project ...>
xsi:schemaLocation="..."
```

Posible causa detectada:

- validador XML/XSD de Eclipse;
- no necesariamente un error real de Maven.

Procedimiento sugerido:

1. Guardar `pom.xml`.
2. Clic derecho en el proyecto.
3. `Maven → Update Project`.
4. Si existe, marcar `Force Update of Snapshots/Releases`.
5. `Project → Clean`.
6. Revisar `Window → Show View → Problems`.

Antes de continuar con una entrega final, el proyecto debería quedar sin errores reales de compilación.

---

# 10. CONFIGURACIÓN DE LA CONEXIÓN A AIVEN

NO usar credenciales reales hardcodeadas.

`application.properties` debe usar variables de entorno, por ejemplo:

```properties
spring.application.name=tema5

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Si Aiven necesita parámetros JDBC adicionales para SSL, añadirlos cuando se conozcan sus datos reales.

Ejemplo conceptual:

```text
DB_URL=jdbc:mysql://HOST:PORT/NOMBRE_BD
DB_USER=usuario
DB_PASSWORD=contraseña
```

Las variables reales se configuran localmente en Eclipse / sistema operativo.

NO subir:

```text
.env
```

con secretos reales.

Añadir `.env` al `.gitignore`.

---

# 11. POR QUÉ `ddl-auto=validate`

Se decidió trabajar con una estructura SQL explícita mediante:

```text
database/schema.sql
```

Por eso se prefiere:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

y NO:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Con `validate`:

- Hibernate no crea tablas.
- Hibernate no modifica tablas.
- Comprueba que la tabla y la entidad coinciden.

Esto obliga a mantener sincronizados:

```text
database/schema.sql
```

y:

```text
Producto.java
```

---

# 12. `database/schema.sql`

Debe estar versionado en GitHub.

Versión base propuesta:

```sql
CREATE DATABASE IF NOT EXISTS api_errores
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE api_errores;

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    precio DECIMAL(10,2) NOT NULL
);
```

IMPORTANTE con Aiven:

Aiven puede proporcionar una base inicial como `defaultdb` o una base ya creada.

Si el usuario de Aiven no permite:

```sql
CREATE DATABASE
```

adaptar el script para trabajar dentro de la base asignada.

En ese caso `schema.sql` puede limitarse a:

```sql
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    precio DECIMAL(10,2) NOT NULL
);
```

Antes de ejecutar el script, comprobar:

- nombre de base proporcionado por Aiven;
- permisos del usuario;
- herramienta usada para ejecutar SQL.

---

# 13. MODELO DE DATOS

Recurso principal:

```text
Producto
```

Tabla:

```text
productos
```

Campos:

```text
id
nombre
codigo
precio
```

Correspondencia:

| Java | MySQL |
|---|---|
| `Long id` | `BIGINT AUTO_INCREMENT PRIMARY KEY` |
| `String nombre` | `VARCHAR(100) NOT NULL` |
| `String codigo` | `VARCHAR(50) NOT NULL UNIQUE` |
| `BigDecimal precio` | `DECIMAL(10,2) NOT NULL` |

Usar `BigDecimal` para el precio, no `double`.

---

# 14. `Producto.java`

Ubicación:

```text
src/main/java/com/trinitarias/grupo4/tema5/model/Producto.java
```

Implementación base propuesta:

```java
package com.trinitarias.grupo4.tema5.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    public Producto() {
    }

    public Producto(String nombre, String codigo, BigDecimal precio) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
```

---

# 15. `ProductoRepository.java`

Ubicación:

```text
src/main/java/com/trinitarias/grupo4/tema5/repository/ProductoRepository.java
```

Implementación propuesta:

```java
package com.trinitarias.grupo4.tema5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinitarias.grupo4.tema5.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCodigo(String codigo);
}
```

---

# 16. `ProductoService.java`

Ubicación:

```text
src/main/java/com/trinitarias/grupo4/tema5/service/ProductoService.java
```

Implementación base propuesta:

```java
package com.trinitarias.grupo4.tema5.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.trinitarias.grupo4.tema5.model.Producto;
import com.trinitarias.grupo4.tema5.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                    new NoSuchElementException(
                        "No existe el producto con ID " + id
                    )
                );
    }

    public Producto crear(Producto producto) {

        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new IllegalStateException(
                "Ya existe un producto con el código "
                + producto.getCodigo()
            );
        }

        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto datosProducto) {

        Producto producto = obtenerPorId(id);

        producto.setNombre(datosProducto.getNombre());
        producto.setCodigo(datosProducto.getCodigo());
        producto.setPrecio(datosProducto.getPrecio());

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {

        Producto producto = obtenerPorId(id);

        productoRepository.delete(producto);
    }
}
```

Estas excepciones son deliberadamente provisionales:

```java
NoSuchElementException
IllegalStateException
```

Persona 2 deberá sustituirlas posteriormente por:

```text
ProductoNoEncontradoException
ProductoDuplicadoException
```

Persona 1 NO debe adelantarse a esa implementación.

---

# 17. `ProductoController.java`

Ubicación:

```text
src/main/java/com/trinitarias/grupo4/tema5/controller/ProductoController.java
```

Endpoints objetivo:

```text
GET    /api/productos
GET    /api/productos/{id}
POST   /api/productos
PUT    /api/productos/{id}
DELETE /api/productos/{id}
```

Implementación base propuesta:

```java
package com.trinitarias.grupo4.tema5.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinitarias.grupo4.tema5.model.Producto;
import com.trinitarias.grupo4.tema5.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto productoCreado = productoService.crear(producto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        return ResponseEntity.ok(
            productoService.actualizar(id, producto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
```

Persona 3 añadirá posteriormente DTOs y `@Valid`.

---

# 18. CÓDIGOS HTTP BÁSICOS DE PERSONA 1

La base de Persona 1 debería devolver:

```text
GET correcto     → 200 OK
POST correcto    → 201 Created
PUT correcto     → 200 OK
DELETE correcto  → 204 No Content
```

La gestión profesional de errores:

```text
400
404
409
500
```

es responsabilidad principalmente de Persona 2 / Persona 3.

---

# 19. PRUEBAS BÁSICAS DE PERSONA 1

Aunque la colección definitiva de Bruno sea de Persona 3, Persona 1 debe verificar que el CRUD funciona.

Base URL:

```text
http://localhost:8080
```

## Crear producto

```http
POST /api/productos
Content-Type: application/json
```

Body:

```json
{
  "nombre": "Teclado Logitech",
  "codigo": "TEC001",
  "precio": 49.99
}
```

Esperado:

```text
201 Created
```

## Obtener todos

```http
GET /api/productos
```

Esperado:

```text
200 OK
```

## Obtener por ID

```http
GET /api/productos/1
```

Esperado:

```text
200 OK
```

## Actualizar

```http
PUT /api/productos/1
```

Body:

```json
{
  "nombre": "Teclado Logitech MX",
  "codigo": "TEC001",
  "precio": 79.99
}
```

Esperado:

```text
200 OK
```

## Eliminar

```http
DELETE /api/productos/1
```

Esperado:

```text
204 No Content
```

---

# 20. COMPROBACIÓN DE PERSISTENCIA

Persona 1 debe verificar:

1. La API arranca.
2. JPA se conecta a Aiven.
3. La tabla `productos` existe.
4. Un `POST` inserta realmente una fila.
5. Reiniciar Spring Boot.
6. Hacer un `GET`.
7. Los datos siguen existiendo.

---

# 21. GIT / GITHUB

Ya existe un repositorio GitHub donde se subirá el trabajo.

Se habló de un flujo tipo:

```text
main
develop
feature/api-productos
feature/global-exceptions
feature/validaciones-bruno
```

Rama recomendada para Persona 1:

```text
feature/api-productos
```

Si el equipo ya decidió otro GitFlow, respetarlo.

Persona 1 debería subir:

```text
src/
database/schema.sql
pom.xml
mvnw
mvnw.cmd
README.md
.gitignore
```

NO subir:

```text
target/
.env
credenciales
contraseñas
archivos locales del IDE
```

---

# 22. `.gitignore`

Debe incluir al menos:

```gitignore
# Maven
target/

# Eclipse
.classpath
.project
.settings/

# IntelliJ
.idea/
*.iml

# VS Code
.vscode/

# Secrets
.env

# OS
.DS_Store
Thumbs.db
```

---

# 23. README MÍNIMO DE PERSONA 1

Persona 3 hará la documentación final, pero Persona 1 debería dejar instrucciones suficientes para que los compañeros puedan arrancar el proyecto.

Ejemplo:

```markdown
# API Gestión de Errores

API REST desarrollada con Spring Boot para la práctica de tratamiento global de errores.

## Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL 8.4
- Aiven
- Maven
- Bruno

## Configuración de base de datos

La aplicación utiliza una instancia MySQL compartida en Aiven.

Configurar las variables de entorno:

- DB_URL
- DB_USER
- DB_PASSWORD

Nunca subir las credenciales al repositorio.

## Base de datos

La estructura está definida en:

database/schema.sql

## Ejecutar

1. Configurar las variables de entorno.
2. Ejecutar `Tema5Application`.
3. La API quedará disponible en `http://localhost:8080`.

## Endpoints base

- GET `/api/productos`
- GET `/api/productos/{id}`
- POST `/api/productos`
- PUT `/api/productos/{id}`
- DELETE `/api/productos/{id}`

## Pendiente

El tratamiento global de errores, DTOs, validaciones y pruebas finales serán añadidos por los otros miembros del equipo.
```

---

# 24. QUÉ DEBE QUEDAR LISTO PARA PERSONA 2

Persona 2 debe poder encontrarse:

- proyecto compilando;
- base compartida accesible;
- entidad `Producto`;
- repositorio funcional;
- servicio funcional;
- controlador funcional;
- CRUD operativo;
- duplicidad detectada provisionalmente mediante `IllegalStateException`;
- producto inexistente detectado provisionalmente mediante `NoSuchElementException`.

Entonces podrá reemplazar esas excepciones y añadir:

```text
GlobalExceptionHandler
@RestControllerAdvice
@ExceptionHandler
ErrorResponse
```

sin rehacer arquitectura.

---

# 25. QUÉ DEBE QUEDAR LISTO PARA PERSONA 3

Persona 3 debe poder trabajar sobre:

```text
ProductoController
ProductoService
Producto
```

para añadir:

- DTOs.
- Validaciones.
- `@Valid`.
- gestión de `400`.
- Bruno.
- README final.
- capturas.

---

# 26. COSAS QUE PERSONA 1 NO DEBE HACER

No implementar:

```text
ProductoNoEncontradoException
ProductoDuplicadoException
GlobalExceptionHandler
ErrorResponse
@ControllerAdvice
@RestControllerAdvice
@ExceptionHandler
ProductoCreateDTO
ProductoResponseDTO
@NotBlank
@NotNull
@Positive
@Valid
colección final Bruno
capturas finales
documentación final de errores
presentación
```

Tampoco:

- JWT.
- autenticación.
- login.
- usuarios.
- roles.
- seguridad avanzada.
- Docker.
- frontend.
- Swagger/OpenAPI.
- relaciones innecesarias.
- más tablas sin necesidad.

---

# 27. ERRORES / DECISIONES ANTERIORES IMPORTANTES

## Error 1 — Proyecto Java normal en Eclipse

Inicialmente apareció un proyecto:

```text
apiErrores
```

con:

```text
JRE System Library [JavaSE-25]
src/
module-info.java
```

Era un proyecto Java normal.

Solución:

- importar el proyecto descargado de Spring Initializr como:
  - `File`
  - `Import`
  - `Maven`
  - `Existing Maven Projects`
- seleccionar la carpeta extraída, no el ZIP.

El proyecto correcto es `tema5 [boot]`.

## Error 2 — H2

Inicialmente se seleccionó H2 desde Spring Initializr.

Después se decidió no utilizarla.

Solución:

- eliminar dependencia H2 de `pom.xml`;
- usar MySQL Connector/J.

## Error 3 — XAMPP

Se planteó usar XAMPP/MySQL local.

Después se descartó para facilitar trabajo colaborativo.

Solución final:

- MySQL 8.4 en Aiven.

## Error 4 — Base física en GitHub

Decisión:

- NO subir la base física.
- Sí subir `database/schema.sql`.

## Error 5 — Aiven trial

Se mostró `developer-1`, $0 durante trial y $5/mes después.

Solución:

- seleccionar `Free-1-1gb`.

## Error 6 — Nombre de proyecto Aiven

Se creó inicialmente con nombre personal.

Decisión:

- crear proyecto de equipo;
- servicio con nombre relacionado con la API.

---

# 28. ORDEN RECOMENDADO PARA CODEX

Codex debe guiar Persona 1 en este orden:

## Fase 0 — Inspección

Antes de modificar:

- leer `pom.xml`;
- leer `application.properties`;
- inspeccionar paquetes existentes;
- inspeccionar `database/schema.sql`;
- comprobar `.gitignore`;
- comprobar estado Git;
- NO sobrescribir cambios del usuario.

## Fase 1 — Maven

Verificar:

```text
Spring Web
Spring Data JPA
MySQL Connector/J
Validation (si ya está)
```

Eliminar H2 si todavía existe.

## Fase 2 — Aiven

Recopilar:

```text
host
port
database
user
password
SSL requirements
```

NO guardar secrets en Git.

## Fase 3 — Schema

Ajustar `database/schema.sql` a la instancia Aiven.

Ejecutar script contra Aiven.

## Fase 4 — Entity

Crear:

```text
Producto.java
```

## Fase 5 — Repository

Crear:

```text
ProductoRepository.java
```

## Fase 6 — Service

Crear:

```text
ProductoService.java
```

con errores genéricos provisionales.

## Fase 7 — Controller

Crear:

```text
ProductoController.java
```

## Fase 8 — Arranque

Ejecutar Spring Boot.

Solucionar errores de conexión / JPA.

## Fase 9 — CRUD

Probar:

```text
POST
GET
GET /{id}
PUT
DELETE
```

## Fase 10 — Persistencia

Verificar datos en Aiven y tras reiniciar Spring Boot.

## Fase 11 — GitHub

Revisar:

```text
git status
```

Asegurarse de no subir secretos ni `target/`.

Commit limpio.

Push a la rama correspondiente.

## Fase 12 — Handoff

Dejar instrucciones claras para Persona 2 y Persona 3.

---

# 29. CRITERIOS DE FINALIZACIÓN DE PERSONA 1

La Persona 1 puede considerarse terminada SOLO si:

- [ ] El proyecto Spring Boot compila.
- [ ] Arranca sin errores.
- [ ] Maven está correctamente configurado.
- [ ] H2 ya no está.
- [ ] MySQL Connector/J está instalado.
- [ ] La instancia Aiven está operativa.
- [ ] `application.properties` no contiene secretos.
- [ ] `schema.sql` existe y es reproducible.
- [ ] La tabla `productos` existe en Aiven.
- [ ] `Producto.java` existe.
- [ ] `ProductoRepository.java` existe.
- [ ] `ProductoService.java` existe.
- [ ] `ProductoController.java` existe.
- [ ] GET todos funciona.
- [ ] GET por ID funciona para un producto existente.
- [ ] POST funciona.
- [ ] PUT funciona.
- [ ] DELETE funciona.
- [ ] Los datos persisten realmente en Aiven.
- [ ] `.gitignore` evita secrets y archivos generados.
- [ ] El código está subido a GitHub.
- [ ] Persona 2 puede continuar sin rehacer la arquitectura.
- [ ] Persona 3 puede continuar sin rehacer la arquitectura.

---

# 30. PRINCIPIO GENERAL PARA CODEX

La prioridad no es añadir muchas funcionalidades.

La prioridad es dejar una base:

```text
simple
+
correcta
+
limpia
+
funcional
+
comprensible
+
preparada para extensión
```

El tema evaluado por el profesor será el tratamiento global de errores, pero **Persona 1 no debe implementar esa parte**.

---

# 31. FORMA DE TRABAJO DESEADA

El usuario quiere:

- guía muy práctica;
- instrucciones paso a paso;
- saber exactamente dónde crear cada archivo;
- código listo para poder usarlo;
- explicaciones suficientes para entender lo que presenta;
- proyecto preparado para trabajo colaborativo;
- GitHub limpio;
- evitar soluciones innecesariamente complejas.

Codex puede modificar directamente archivos del proyecto si tiene acceso, pero debe:

1. inspeccionar primero;
2. explicar brevemente lo que va a cambiar;
3. no tocar Persona 2 / Persona 3;
4. no borrar trabajo existente sin comprobarlo;
5. mantener compatibilidad con Eclipse + Maven + Java 17;
6. evitar secretos en Git;
7. verificar que el proyecto compile después de los cambios.

---

# 32. PRIMERA ACCIÓN RECOMENDADA PARA CODEX

Al recibir este contexto, Codex debería comenzar con algo equivalente a:

> “Voy a revisar primero el estado actual del proyecto (`pom.xml`, `application.properties`, paquetes, `schema.sql` y `.gitignore`) sin modificar nada. Después corregiré únicamente lo necesario para dejar lista la base de Persona 1.”

A continuación:

1. inspeccionar proyecto;
2. informar de discrepancias respecto a este documento;
3. proponer/corregir los archivos de Persona 1;
4. configurar Aiven cuando el usuario proporcione la conexión;
5. probar CRUD;
6. preparar commit/push.

---

# FIN DEL CONTEXTO

**Restricción fundamental para Codex: trabajar SOLO en la Persona 1.**
