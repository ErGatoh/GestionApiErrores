# Reglas de trabajo para este proyecto

## Contexto y alcance

- Leer `CONTEXTO_CODEX_PERSONA_1.md` antes de planificar trabajo sobre el proyecto. Usarlo como contexto del reparto, las decisiones técnicas y el progreso documentado; comprobar el estado real de los archivos antes de dar por hecho que una tarea está terminada.
- Trabajar por ahora solo en la parte de la Persona 1: base funcional de la API REST de productos con Java 17, Spring Boot, Maven y MySQL en Aiven. Las partes de las Personas 2 y 3 quedan fuera del alcance hasta que el usuario lo pida expresamente. Si solicita una guía para la Persona 2, proporcionar la guía sin implementarla, salvo autorización específica.
- No añadir funcionalidades ajenas a la práctica ni adelantar el tratamiento global de errores, los DTO, las validaciones o la documentación final asignados a los compañeros.

## Acuerdo previo a cada cambio

1. Comenzar cada nuevo encargo de desarrollo en una **fase de planificación**. Inspeccionar de forma no destructiva los archivos relevantes, la estructura y el estado de Git. Distinguir los hechos comprobados de lo que solo figura en el documento de contexto.
2. Presentar al usuario un plan concreto: objetivo, archivos exactos que se crearían o modificarían, cambios previstos en cada uno, orden de trabajo y forma de comprobar el resultado. Explicar las decisiones que puedan afectar al trabajo de los compañeros.
3. **Pedir la validación expresa del usuario y esperar su respuesta antes de editar código, configuración, base de datos, documentación o Git.** No interpretar el silencio como aprobación. La aprobación se aplica solo al plan presentado.
4. Tras la aprobación, ejecutar únicamente los cambios acordados. Si aparece una necesidad nueva, un archivo adicional o una modificación distinta, detener esa parte, actualizar el plan y pedir otra validación antes de continuar.
5. No reescribir ni sustituir archivos importantes o extensos sin una petición explícita. Preferir cambios pequeños y localizados; preservar el trabajo existente, incluidos los cambios sin confirmar de otras personas.
6. No hacer commits, push, cambios de rama, operaciones sobre Aiven ni otras acciones externas sin incluirlas claramente en el plan validado.

La creación de este `AGENTS.md` responde a una petición explícita del usuario. Para los siguientes encargos se aplica el flujo de planificación y validación anterior.

## Comunicación y control del usuario

- Antes de actuar, indicar qué se está inspeccionando. Durante la ejecución de un plan aprobado, comunicar los cambios relevantes y cualquier desviación detectada. El usuario debe poder saber qué archivos se están tocando y por qué.
- No ampliar el alcance por iniciativa propia ni aprovechar una tarea para hacer refactorizaciones, limpiezas o mejoras no acordadas. Si surge una idea útil, proponerla para un plan posterior.
- Ante ambigüedades que afecten al alcance o a datos reales de Aiven, preguntar antes de tomar una decisión con efectos. No inventar credenciales, resultados de pruebas ni progreso.
- Al terminar, resumir los archivos modificados, las comprobaciones realizadas y lo que siga pendiente. Si una comprobación no se pudo ejecutar, decirlo con claridad.

## Código y seguridad

- Escribir nombres y comentarios nuevos en español cuando el lenguaje y las convenciones del proyecto lo permitan. Mantener los nombres técnicos establecidos por Java, Spring, Maven y los contratos existentes.
- Priorizar un código fácil de leer de un vistazo por las Personas 2 y 3: métodos breves, nombres descriptivos, estructura sencilla y formato consistente. Evitar abreviaturas poco claras, trucos compactos y abstracciones innecesarias.
- Incluir comentarios breves en español donde aclaren la intención o una decisión no evidente. No saturar el código con comentarios que repitan literalmente lo que ya expresa.
- Conservar compatibilidad con Java 17, Eclipse y Maven. Comprobar compilación y funcionamiento según lo acordado en cada plan, sin presentar como verificado algo que no se haya probado.
- No escribir credenciales en archivos versionados ni mostrar secretos en respuestas o registros. Usar variables de entorno para la conexión a Aiven y respetar `.gitignore`.
- Antes de modificar archivos, resolver cuál de las estructuras presentes en el repositorio corresponde al proyecto Spring Boot `tema5`; no deducirlo solo por el nombre de una carpeta.
