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

**Estado actual:** la aplicación arrancó desde Eclipse con JDK 17, conectó con MySQL 8.4.11 en Aiven e inicializó JPA sin errores de esquema. Tomcat quedó escuchando en el puerto 8080 y apareció `Started Tema5Application`. Las seis fuentes Java también compilaron con `javac --release 17`. Aún no se han comprobado todas las operaciones CRUD contra Aiven ni el empaquetado completo con Maven.

## Una base, tres personas

El proyecto Maven está en la raíz de este repositorio. Los tres integrantes usan el mismo servicio, la base `defaultdb` y el usuario MySQL `avnadmin` para la aplicación. Cada persona puede entrar en la consola con su propia cuenta de Aiven; esa cuenta no sustituye las credenciales MySQL que utiliza Spring Boot.

El responsable del proyecto invita a los compañeros a la organización de Aiven y les concede acceso al proyecto. Para esta práctica, `avnadmin` se utiliza tanto para preparar el esquema como para conectar la aplicación.

## Preparación de Aiven

1. Comprobar en Aiven que el servicio MySQL está activo. Los datos proporcionados son host `api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com`, puerto `12698` y base `defaultdb`.
2. La tabla `productos` ya está creada en `defaultdb`. Si hace falta reproducirla en otra instalación, ejecutar `database/schema.sql` con `avnadmin`; el script no crea otra base.
3. Compartir la contraseña de `avnadmin` con los compañeros por un canal privado. No incluirla en Git.

Para comprobar la tabla o reproducirla en otra instalación, abrir una terminal con el cliente `mysql` instalado y ejecutar:

```bash
mysql -h api-errores-hub-grupo4-proyecto5-apirest.a.aivencloud.com -P 12698 -u avnadmin -p --ssl-mode=REQUIRED defaultdb
```

Después, introducir la **contraseña de `avnadmin`** cuando `mysql` la solicite. La opción `-p` abre esa petición interactiva: no añadir la contraseña al comando. Spring Boot utiliza ese mismo usuario mediante las variables de entorno.

Dentro del cliente MySQL, comprobar la tabla actual o ejecutar el script si es necesario reproducirla:

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
4. Ejecutar la aplicación con la tabla `productos` ya creada.

## Cómo funciona la aplicación

Spring Boot recibe las peticiones HTTP en `ProductoController`, que delega en `ProductoService`. El servicio decide cómo crear, consultar, actualizar o borrar un producto y utiliza `ProductoRepository` para acceder a la tabla `productos` de Aiven mediante Spring Data JPA. `Producto.java` representa cada fila de esa tabla.

La aplicación se ejecuta en el equipo donde se inicia Eclipse. En el arranque comprobado, Tomcat usó el puerto **8080** y la dirección base de la API fue **[http://localhost:8080/api/productos](http://localhost:8080/api/productos)**. `localhost` apunta al propio equipo; esta dirección no publica la API en Internet. El puerto de Aiven (`12698`) corresponde a la conexión MySQL y no a las peticiones HTTP de la API.

| Método | Ruta | Función |
| --- | --- | --- |
| `GET` | `/api/productos` | Lista los productos. |
| `GET` | `/api/productos/{id}` | Obtiene un producto por su ID. |
| `POST` | `/api/productos` | Crea un producto. |
| `PUT` | `/api/productos/{id}` | Actualiza un producto existente. |
| `DELETE` | `/api/productos/{id}` | Borra un producto. |

Hibernate tiene `ddl-auto=validate`: al arrancar comprueba el esquema existente sin crear ni modificar tablas. También se configuró `spring.jpa.open-in-view=false` para que la sesión JPA no permanezca abierta durante la generación de la respuesta HTTP.

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
| Error SSL | Comprobar que `DB_URL` incluye `sslMode=REQUIRED` y que la conexión permite TLS. |
| Error de validación de esquema | Conexión establecida, pero falta `productos` o sus columnas no coinciden con `Producto.java`; revisar `database/schema.sql`. |

Un fallo de conexión aparece antes de poder consultar la base y suele mencionar autenticación, red o TLS. Un fallo de esquema aparece después de conectar, durante la validación JPA, e identifica una tabla o columna que falta o no coincide.

## Límites de esta etapa

`ProductoService` usa temporalmente `NoSuchElementException` e `IllegalStateException`. Hasta que la Persona 2 añada el tratamiento global, los errores no tienen todavía la respuesta JSON definitiva ni los códigos `404` y `409` previstos. La Persona 3 añadirá DTO, validaciones y las pruebas de los endpoints.
