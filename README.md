# API Gestión de Errores — base de Jorge Fernández

Proyecto de clase del Grupo 5. Jorge Fernández ha preparado la conexión a Aiven y una API REST básica para gestionar productos. El tratamiento global de errores corresponde a la Persona 2; los DTO, las validaciones y la documentación final corresponden a la Persona 3.

## Bitácora breve de Jorge Fernández

| Qué se hizo | Por qué y cómo |
| --- | --- |
| Se dejó un único proyecto Maven con Java 17, Spring MVC, Spring Data JPA y MySQL Connector/J. | Así los tres compañeros parten de la misma base en Eclipse. |
| Se eligió MySQL en Aiven, con `defaultdb` compartida y conexión SSL `REQUIRED`. | Todos pueden trabajar con los mismos datos sin montar MySQL local. |
| Se configuraron `DB_URL`, `DB_USER` y `DB_PASSWORD` como variables de entorno. | La contraseña no queda escrita en el código ni en Git. |
| Se definió `database/schema.sql` y `ddl-auto=validate`. | El script crea `productos`; Hibernate comprueba la estructura sin crear ni modificar tablas. |
| Se creó la entidad `Producto` con `BigDecimal` para el precio y un repositorio JPA con `existsByCodigo`. | El modelo coincide con MySQL y permite detectar códigos repetidos. |
| Se añadieron servicio y controlador CRUD. | El servicio concentra la lógica; el controlador expone cinco operaciones HTTP. Los errores usan excepciones estándar provisionales para que la Persona 2 los sustituya. |

**Estado comprobado:** Jorge confirmó que Spring Boot conectaba con Aiven antes de crear la tabla. Las seis fuentes Java actuales compilaron con `javac --release 17`. Sigue pendiente ejecutar el esquema y probar el CRUD contra Aiven; no se ha verificado todavía el empaquetado con Maven en este entorno.

## Una base, tres personas

El proyecto Maven está en la raíz de este repositorio. Los tres integrantes usan el mismo servicio, la base `defaultdb` y el usuario MySQL `avnadmin` para la aplicación. Cada persona puede entrar en la consola con su propia cuenta de Aiven; esa cuenta no sustituye las credenciales MySQL que utiliza Spring Boot.

El responsable del proyecto invita a los compañeros a la organización de Aiven y les concede acceso al proyecto. Para esta práctica, `avnadmin` se utiliza tanto para preparar el esquema como para conectar la aplicación.

## Preparación de Aiven

1. Comprobar en Aiven que el servicio MySQL está activo. Los datos proporcionados son host `api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com`, puerto `12698` y base `defaultdb`.
2. Abrir una terminal en la raíz de este repositorio, conectar a `defaultdb` y ejecutar una vez `database/schema.sql` con `avnadmin`. El script no crea otra base.
3. Compartir la contraseña de `avnadmin` con los compañeros por un canal privado. No incluirla en Git.

Para entrar en `defaultdb` como administrador, abrir una terminal con el cliente `mysql` instalado y ejecutar:

```bash
mysql -h api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com -P 12698 -u avnadmin -p --ssl-mode=REQUIRED defaultdb
```

Después, introducir la **contraseña de `avnadmin`** cuando `mysql` la solicite. La opción `-p` abre esa petición interactiva: no añadir la contraseña al comando. Spring Boot utiliza ese mismo usuario mediante las variables de entorno.

Dentro del cliente MySQL, crear y comprobar la tabla:

```sql
SOURCE database/schema.sql;
SHOW TABLES LIKE 'productos';
DESCRIBE productos;
```

El script crea solo la tabla `productos` dentro de `defaultdb`. No hace falta otro usuario MySQL para esta práctica.

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
3. Si la tabla falta o no coincide con `Producto.java`, `ddl-auto=validate` impedirá el arranque.

Quien no tenga la contraseña de Aiven puede revisar el código o ver una demostración en el equipo del grupo. Para ejecutarla en su propio equipo necesitará las credenciales entregadas por privado.

| Mensaje habitual | Qué revisar |
| --- | --- |
| `Access denied` | `DB_USER`, `DB_PASSWORD` y acceso del usuario `avnadmin`. |
| `Communications link failure` | Host, puerto, servicio activo, conexión de red y cortafuegos. |
| `Unknown database` | Nombre `defaultdb` dentro de `DB_URL`. |
| Error SSL o certificado | Modo SSL, CA o truststore local si se usa verificación. |
| Error de validación de esquema | Conexión establecida, pero falta `productos` o sus columnas no coinciden con `Producto.java`; revisar `database/schema.sql`. |

Un fallo de conexión aparece antes de poder consultar la base y suele mencionar autenticación, red o TLS. Un fallo de esquema aparece después de conectar, durante la validación JPA, e identifica una tabla o columna que falta o no coincide.

## Límites de esta etapa

`ProductoService` usa temporalmente `NoSuchElementException` e `IllegalStateException`. Hasta que la Persona 2 añada el tratamiento global, los errores no tienen todavía la respuesta JSON definitiva ni los códigos `404` y `409` previstos. La Persona 3 añadirá DTO, validaciones y las pruebas de los endpoints.
