# API Gestión de Errores — base de Persona 1

Proyecto Spring Boot con Java 17, Maven, Spring MVC, Spring Data JPA y MySQL en Aiven. Esta etapa prepara la conexión y el esquema; todavía no incluye la entidad `Producto` ni los endpoints CRUD. El tratamiento global de errores corresponde a la Persona 2. Los DTO, las validaciones y la documentación final corresponden a la Persona 3.

## Una base, tres personas

El proyecto Maven está en la raíz de este repositorio. Los tres integrantes usan el mismo servicio, la base `defaultdb` y el usuario MySQL `avnadmin` para la aplicación. Cada persona puede entrar en la consola con su propia cuenta de Aiven; esa cuenta no sustituye las credenciales MySQL que utiliza Spring Boot.

El responsable del proyecto invita a los compañeros a la organización de Aiven y les concede acceso al proyecto. Para esta práctica, `avnadmin` se utiliza tanto para preparar el esquema como para conectar la aplicación.

## Preparación de Aiven

1. Comprobar en Aiven que el servicio MySQL está activo. Los datos proporcionados son host `api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com`, puerto `12698` y base `defaultdb`.
2. Seleccionar `defaultdb` y ejecutar una vez `database/schema.sql` con `avnadmin`. El script no crea otra base.
3. Compartir la contraseña de `avnadmin` con los compañeros por un canal privado. No incluirla en Git.

Para entrar en `defaultdb` como administrador, abrir una terminal con el cliente `mysql` instalado y ejecutar:

```bash
mysql -h api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com -P 12698 -u avnadmin -p --ssl-mode=REQUIRED defaultdb
```

Después, introducir la **contraseña de `avnadmin`** cuando `mysql` la solicite. La opción `-p` abre esa petición interactiva: no añadir la contraseña al comando. Spring Boot utiliza ese mismo usuario mediante las variables de entorno.

El script crea la tabla `productos` dentro de `defaultdb`. No hace falta crear ni configurar un usuario MySQL adicional para esta práctica.

## Variables de entorno

La aplicación lee estas tres variables al arrancar. `.env.example` muestra el formato, pero Spring Boot no carga archivos `.env` por sí solo.

| Variable | Valor que debe poner cada integrante |
| --- | --- |
| `DB_URL` | `jdbc:mysql://api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com:12698/defaultdb?sslMode=REQUIRED` |
| `DB_USER` | `avnadmin` |
| `DB_PASSWORD` | La contraseña real de `avnadmin` |

No incluir credenciales reales en `application.properties`, `.env.example`, commits, capturas o mensajes del equipo. Un archivo `.env` local queda excluido por `.gitignore`, pero para ejecutar Spring Boot hay que cargar sus valores en el entorno.

### Configuración en Eclipse

1. Importar **la raíz de este repositorio** mediante `File → Import → Maven → Existing Maven Projects`. Utilizar un JDK 17.
2. Abrir `Run → Run Configurations… → Spring Boot App` y seleccionar `Tema5Application`.
3. En la pestaña `Environment`, añadir `DB_URL`, `DB_USER` y `DB_PASSWORD` con los valores de la base compartida. Guardar la configuración solo en el equipo local; no exportar un archivo `.launch` que contenga la contraseña.
4. Ejecutar la aplicación después de crear la tabla `productos`.

### Configuración temporal en PowerShell

En una terminal local, definir las variables antes de ejecutar Maven:

```powershell
$env:DB_URL = 'jdbc:mysql://api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com:12698/defaultdb?sslMode=REQUIRED'
$env:DB_USER = 'avnadmin'
$env:DB_PASSWORD = 'TU_PASSWORD_REAL'
.\mvnw.cmd spring-boot:run
```

Los valores del ejemplo son marcadores. Sustituirlos localmente y evitar guardar comandos con contraseñas reales en archivos o historiales compartidos.

## SSL y certificado CA

`sslMode=REQUIRED` exige una conexión cifrada, pero no valida explícitamente el CA ni el nombre del servidor. Es la configuración inicial solicitada. La variable opcional `AIVEN_CA_PATH` de la plantilla indica dónde se guardaría **localmente** el certificado CA descargado de Aiven; la aplicación no la utiliza automáticamente con este modo.

Si después se decide verificar el CA y el nombre del servidor, cada integrante deberá importar el CA real a un truststore local, configurar su ruta y contraseña en la ejecución de Java y cambiar **solo su variable `DB_URL`** a `sslMode=VERIFY_IDENTITY`. Por ejemplo, los argumentos de la máquina virtual en Eclipse serían `-Djavax.net.ssl.trustStore=RUTA_LOCAL_AL_TRUSTSTORE` y `-Djavax.net.ssl.trustStorePassword=PASSWORD_LOCAL_DEL_TRUSTSTORE`. No escribir esos valores reales en el repositorio. Un certificado PEM no se puede indicar como si fuera directamente un truststore Java; primero hay que importarlo.

## Comprobación y diagnóstico

1. Con `defaultdb` y la tabla `productos` preparados, configurar las tres variables y arrancar `Tema5Application` desde Eclipse.
2. Comprobar que el registro muestra que el pool JDBC ha iniciado y que aparece `Started Tema5Application`. Esto indica que la aplicación ha arrancado y ha podido inicializar la conexión.
3. En un cliente MySQL conectado a `defaultdb`, ejecutar `SHOW TABLES LIKE 'productos';` y `DESCRIBE productos;` para confirmar la tabla y sus columnas. Aún no existe la entidad `Producto`, por lo que `ddl-auto=validate` todavía no puede comparar esa tabla con un mapeo JPA.

| Mensaje habitual | Qué revisar |
| --- | --- |
| `Access denied` | `DB_USER`, `DB_PASSWORD` y acceso del usuario `avnadmin`. |
| `Communications link failure` | Host, puerto, servicio activo, conexión de red y cortafuegos. |
| `Unknown database` | Nombre `defaultdb` dentro de `DB_URL`. |
| Error SSL o certificado | Modo SSL, CA o truststore local si se usa verificación. |
| Error de validación de esquema | Conexión establecida, pero tabla o columnas distintas de la entidad JPA; revisar `database/schema.sql`. Este caso será comprobable cuando exista `Producto.java`. |

Un fallo de conexión aparece antes de poder consultar la base y suele mencionar autenticación, red o TLS. Un fallo de esquema aparece después de conectar, durante la validación JPA, e identifica una tabla o columna que falta o no coincide.

## Estado actual

- El proyecto contiene la clase principal y la configuración Maven.
- `database/schema.sql` define la tabla `productos`.
- Están pendientes la entidad, el repositorio, el servicio, el controlador y las pruebas CRUD de Persona 1.
- La conexión real a Aiven y la persistencia todavía requieren los datos y una comprobación con el servicio activo.
