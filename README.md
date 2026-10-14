# API Gestión de Errores — base de Persona 1

Proyecto Spring Boot con Java 17, Maven, Spring MVC, Spring Data JPA y MySQL en Aiven. Esta etapa prepara la conexión y el esquema; todavía no incluye la entidad `Producto` ni los endpoints CRUD. El tratamiento global de errores corresponde a la Persona 2. Los DTO, las validaciones y la documentación final corresponden a la Persona 3.

## Una base, tres personas

El proyecto Maven está en la raíz de este repositorio. Los tres integrantes usan el mismo servicio y la misma base MySQL de Aiven, pero cada uno debe tener su propio **usuario MySQL** y contraseña. La cuenta de la consola de Aiven sirve para acceder al proyecto y administrar el servicio; no sustituye las credenciales MySQL que utiliza Spring Boot.

El responsable del proyecto invita a los compañeros a la organización de Aiven y les concede acceso al proyecto. Después crea un usuario del servicio MySQL para cada integrante y revisa sus permisos. No se debe compartir el usuario administrador `avnadmin` para el desarrollo habitual.

## Preparación de Aiven

1. Comprobar que el servicio MySQL está activo y anotar su host y puerto desde la consola.
2. Crear o elegir la base de datos común y anotar su nombre exacto.
3. Crear los usuarios MySQL individuales y entregar a cada persona sus credenciales por un canal privado.
4. Seleccionar la base de datos común y ejecutar una vez `database/schema.sql` con un usuario autorizado para crear tablas.

El script crea la tabla `productos` dentro de la base seleccionada. No crea otra base de datos. Todos deben utilizar el mismo nombre de base en su URL JDBC.

## Variables de entorno

La aplicación lee estas tres variables al arrancar. `.env.example` muestra el formato, pero Spring Boot no carga archivos `.env` por sí solo.

| Variable | Valor que debe poner cada integrante |
| --- | --- |
| `DB_URL` | `jdbc:mysql://HOST:PUERTO/NOMBRE_BASE?sslMode=REQUIRED` con los datos reales del servicio común |
| `DB_USER` | Su propio usuario del servicio MySQL |
| `DB_PASSWORD` | La contraseña de ese usuario MySQL |

No incluir credenciales reales en `application.properties`, `.env.example`, commits, capturas o mensajes del equipo. Un archivo `.env` local queda excluido por `.gitignore`, pero para ejecutar Spring Boot hay que cargar sus valores en el entorno.

### Configuración en Eclipse

1. Importar **la raíz de este repositorio** mediante `File → Import → Maven → Existing Maven Projects`.
2. Abrir `Run → Run Configurations…` y seleccionar la configuración de `Tema5Application`.
3. En la pestaña `Environment`, añadir `DB_URL`, `DB_USER` y `DB_PASSWORD` con los valores personales. Guardar la configuración solo en el equipo local; no exportar un archivo `.launch` que contenga la contraseña.
4. Ejecutar la aplicación después de crear la tabla `productos`.

### Configuración temporal en PowerShell

En una terminal local, definir las variables antes de ejecutar Maven:

```powershell
$env:DB_URL = 'jdbc:mysql://HOST:PUERTO/NOMBRE_BASE?sslMode=REQUIRED'
$env:DB_USER = 'USUARIO_MYSQL_PERSONAL'
$env:DB_PASSWORD = 'CONTRASENA_MYSQL_PERSONAL'
.\mvnw.cmd spring-boot:run
```

Los valores del ejemplo son marcadores. Sustituirlos localmente y evitar guardar comandos con contraseñas reales en archivos o historiales compartidos.

`sslMode=REQUIRED` exige una conexión cifrada. La verificación de la identidad del servidor mediante el certificado CA de Aiven se configurará cuando se compruebe el certificado y la forma de distribución entre los tres equipos.

## Estado actual

- El proyecto contiene la clase principal y la configuración Maven.
- `database/schema.sql` define la tabla `productos`.
- Están pendientes la entidad, el repositorio, el servicio, el controlador y las pruebas CRUD de Persona 1.
- La conexión real a Aiven y la persistencia todavía requieren los datos y una comprobación con el servicio activo.
