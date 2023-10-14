
---

# Especificación de requisitos de software

<p style="text-align: right" >Proyecto: App Mobile "BootcampAr"
</p>

---

<p style="text-align: right">
Año: 2023
</p>

---

### Ficha del Documento

| Fecha | Revisión | Autor | Verificado dep. Calidad
| - | - | - | - |
| 2023 | | Pamela Sol Pilotti - Lidio A. Guedez - Yuliana Paula Capdevila- Leonardo Vivas - Laura Natalia Laslo Veron - Lucia Ailen Leonetti - Roberto Alfonso - Facundo M. Díaz C. - Celeste Esquivel | |

---

## Contenido

- [1 - Introducción](#1-introducción)

  - [1.1 - Propósito](#11-propósito)

  - [1.2 - Alcance](#12-alcance)

  - [1.3 - Personal Involucrado](#13-personal-involucrado)

  - [1.4 - Definiciones, acrónimos y abreviaturas](#14-definiciones-acrónimos-y-abreviaturas)

  - [1.5 - Referencias](#15-referencias)

  - [1.6 - Resumen](#16-resumen)

- [2 - Descripción General](#2-descripción-general)

  - [2.1 - Perspectiva del producto](#21-perspectiva-del-producto)

  - [2.2 - Características de los usuarios](#22-características-de-los-usuarios)

  - [2.3 - Restricciones](#23-restricciones)

  - [2.4 - Suposiciones y dependencias](#24-suposiciones-y-dependencias)

- [3. Requisitos Específicos](#3-requisitos-específicos)

  - [3.1 - Product Backlog](#31-product-backlog)

    - [3.1.1 - Historias de usuarios y sus tareas](#311-historias-de-usuarios-y-sus-tareas)

    - [3.1.2 - Requerimientos Funcionales](#312-requerimientos-funcionales)

    - [3.1.3 - Requerimientos No Funcionales*](#313-requerimientos-no-funcionales)

    - [3.1.4 - Sprints](#314-sprints)

- [4. Anexo de bitácora II](#4-anexo-de-bitácora-ii)

  - [4.1 Sprint 1](#41-sprint-1)

---

### 1 Introducción
Este documento es una Especificación de Requisitos Software (ERS) para la aplicación **"BootcampAr - Mobile"** para la gestión de procesos y control de inventarios. Esta especificación se ha estructurado basándose en las directrices dadas por el estándar IEEE Práctica Recomendada para Especificaciones de Requisitos Software ANSI/IEEE 830, 1998.

#### 1.1 Propósito
Nuestro propósito como una página de venta de cursos es informar, generar interés, convencer y facilitar el proceso de inscripción en los cursos ofrecidos, brindando a los visitantes la información y la confianza necesarios para tomar una decisión fundada y realizar la compra.

#### 1.2 Alcance
BootcampAr está dirigido a personas mayores de 16 años que desean capacitarse e incorporar herramientas de desarrollo web para poder insertarse en el mercado laboral o bien para su crecimiento personal. Las empresas también pueden solicitar estos servicios para capacitar a sus empleados.

Las personas que quieran capacitarse en esta plataforma no necesitarán poseer conocimiento previo sobre las herramientas de desarrollo, aunque aquellas personas que tengan un nivel intermedio o avanzado podrán continuar con una capacitación más profunda.

#### 1.3 Personal involucrado

| Nombre | Rol | Categoria Profesional | Responsabilidad | Información de contacto
| - | - | - | - | - |
| Pamela Pilotti| Desarrolladora / Scrum Master | FullStack | Backend - Frontend - Base de Datos - API REST | pamelasol13@hotmail.com |
| Leonardo Vivas | Desarrollador | FullStack | Backend - Frontend - Base de Datos - API REST | leonardoa173@gmail.com |
| Lucia Ailen Leonetti | Desarrolladora | FullStack | Backend - Frontend - Base de Datos - API REST | leonettil152@gmail.com |
| Laura Natalia Laslo Veron | Desarrolladora | FullStack | Backend - Frontend - Base de Datos - API REST - QA Analyst | lauranatalialasloveron@gmail.com |
| Yuliana Paula Capdevila | Desarrolladora | FullStack | Backend - Frontend - Base de Datos - API REST | yulicapdevila92@gmail.com |
| Lidio A. Guedez | Desarrollador | FullStack | Backend - Frontend - Base de Datos - API REST | g.abelardo@gmail.com |
| Roberto Alfonso | Desarrollador | FullStack | Backend - Frontend - Base de Datos - API REST | rpgrca@gmail.com |
| Facundo Manuel Díaz Córdoba | Desarrollador | FullStack | Backend - Frontend - Base de Datos - API REST | facudiaz1738@hotmail.com |
| Celeste Esquivel | Desarrolladora | FullStack | Backend - Frontend - Base de Datos - API REST | cele-esquivel@hotmail.com |

#### 1.4 Definiciones, acrónimos y abreviaturas

| Nombre | Descripción |
| - | - |
| QA | *Question/Answer* |
| Administrador | Persona que utilizará el sistema para gestionar altas y bajas de productos |
| Alumno | Persona que utilizará el sistema para realizar compras de los productos |
| *Product Backlog* | Pila de Producto |
| CRUD / ABMC | *create read update delete* / Alta, Baja, Modificación y Consulta |
| Productos | Cursos Publicados |
| SQL | Base de Datos Relacional |
| DB | *Database* / Base de Datos |
| DER | Diagrama Entidad Relación |
| *Branch* | Rama |
| GIT | Controlador de Versiones |
| POO | Programación Orientada a Objetos |
| *Project* | Proyecto |
| *Activity* | Actividad (Pantalla) |

#### 1.5 Referencias

| Título del Documento | Referencia |
| - | - |
| Standard IEEE 830 - 1998 | IEEE |

#### 1.6 Resumen

"BootcampAr Mobile" es una plataforma de aprendizaje en línea que ofrece cursos sobre tecnología y desarrollo de software. Los usuarios pueden registrarse, acceder a una variedad de cursos, comprarlos con una pasarela de pago integrada y realizar un seguimiento de su progreso en los mismos.

---

### 2 Descripción General

#### 2.1 Perspectiva del producto
El sistema BootcampAr será un producto diseñado para trabajar en entornos mobile, lo que permitirá su utilización de forma rápida y eficaz, además de brindar herramientas para la formación y fortalecimiento de conocimientos en el área de la matemática, lógica y a los algoritmos orientados a la programación en diversas áreas y lenguajes

[Link al documento de Figma con “Maquetado de Pantallas”](https://www.figma.com/file/8UGS2u1XMBbejPGKUqwqHW/Untitled?type=design&node-id=0-1&mode=design)

![Image](https://github.com/abelardog/bootcampar/assets/95236196/9fdac492-ddd4-4240-9123-1e6a567cdf9b)

#### 2.2 Características de los usuarios

| Tipo de usuario | Formación | Actividades |
| - | - | - |
| Administrador | Manejo de herramientas informáticas | Control y manejo del sistema en general |
| Profesor | Manejo de herramientas informáticas y Conocimientos referidos al area a enseñar | ABMC Cursos |
| Alumno | Manejo de aplicación mobile | Formación Académica |
| Visitante | Manejo de entornos web | Navegación Básica |

#### 2.3 Restricciones

- Interfaz para ser usada con internet (solamente).
- Lenguajes y tecnologías en uso: Android, Java, Python, MySQL y/o Firebase
- El sistema se diseñará según un modelo cliente/servidor.
- El sistema deberá tener un diseño e implementación sencillos

#### 2.4 Suposiciones y Dependencias

- Se asume que los requisitos aquí descritos son estables.
- Los equipos en los que se vaya a ejecutar el sistema deben cumplir los requisitos
antes indicados para garantizar una ejecución correcta de la misma, suponiendo continua comunicación y colaboración en pos de crear robustez, escalabilidad y mantenibilidad.

---

### 3 Requisitos específicos

#### 3.1 Product Backlog

##### 3.1.1 Historias de Usuarios y sus Tareas

- US/TK’s - Sprint 0

  - #US01 Como visitante quiero conocer la oferta educativa para evaluar mis opciones.
  - #TK01 Crear listado de cursos disponibles en el sistema accesible para invitados

  - #US02 como visitante quiero poder registrarme para poder acceder a la oferta educativa.
  - #TK02 Crear formulario de registración
  - #TK03 Grabar datos de registración en base de datos

  - #US03 Como usuario registrado quisiera poder recuperar mi contraseña, para facilitar el acceso en caso de olvidarla.
  - #TK04 Crear actividad para recuperar la clave

  - #US04 Como usuario registrado quisiera contar con un buscador para que sea más fácil la navegación.
  - #TK05 Crear actividad de búsqueda de cursos simple por nombre

  - #US05 Como usuario registrado quisiera poder marcar mis cursos favoritos para poder encontrarlos más tarde con mayor facilidad.
  - #TK06 Agregar la opción de marcar cursos como favoritos en la interfaz de usuario de la aplicación.
  - #TK07 Implementar la funcionalidad de guardar cursos como favoritos para cada usuario.

  - #US06 Como usuario registrado quisiera contar con una sección "Perfil" para poder acceder a él y modificarlo en caso de que sea necesario.
  - #TK08 Mostrar la actividad de perfil con la información personal actual del usuario.
  - #TK09 Implementar la funcionalidad de edición de información personal, como nombre, foto de perfil, dirección, etc.
  - #TK10 Validar y guardar los cambios realizados en la información personal del usuario.

  - #US08 como alumno quisiera conocer detalles del curso para estar informado del mismo.
  - #TK11 Agregar actividad de descripción del curso

  - #US09 como alumno quisiera acceder al curso para poder hacer uso de él.
  - #TK16 Crear modo "offline" para los cursos

  - #US10 Como alumno quisiera poder calificar los cursos consumidos para dar a conocer mi opinión sobre ellos.

  - #TK12 Crear el Project Backlog
  - #TK13 Actualizar el documento IEEE830 documentación
  - #TK14 Crear un Diagrama de Clases y Casos de Uso
  - #TK15 Crear Modelo de bases de datos

- US/TK’s - Sprint 1

  - #TK22 Caso de uso Registración de estudiante
  - #TK23 Caso de uso Ingreso de estudiante
  - #TK24 Caso de uso Listado de cursos
  - #TK21 Crear diagramas de caso de uso general y particulares
  - #TK25 Caso de uso Inscripción a curso
  - #TK26 Caso de uso Hacer curso
  - #TK27 Caso de uso Marcar como favorito
  - #TK28 Caso de uso Calificar
  - #TK29 Crear script de base de datos
  - #TK30 Realizar 3 casos de prueba para la app
  - #TK31 Crear actividades de la app
  - #TK32 Actividad Inicio de Sesión
  - #TK33 Actividad Home
  - #TK34 Actividad Perfil
  - #TK35 Actividad Listado de Cursos
  - #TK36 Actividad de contacto
  - #TK37 Crear actividad de ayuda / FAQ
  - #TK38 Borrar botón de configuración del perfil
  - #TK39 Levantar actividad con lista de cursos al presionar favoritos
  - #TK40 Implementar actualizar simple y que vaya para atrás
  - #TK41 Click en detalles del curso sin loguearse
  - #TK42 Mostrar fragmento de contacto
  - #TK43 Crear actividad de listado de videos de curso
  - #TK44 Creación de Demostración del Proyecto en video


##### 3.1.2 Requerimientos Funcionales

| Identificación del Requerimiento | Nombre del Requerimiento | Características | Descripción del Requerimiento | Requerimiento No Funcional | Prioridad del Requerimiento |
| - | - | - | - | - | - |
| RF01 | Catálogo de Cursos | Para evaluar opciones | Sección con los cursos disponibles. | RNF01 - RNF03 - RNF04 | Alta |
| RF02 | Registro de Usuarios | Para poder acceder a la oferta educativa | Formulario de Registro | RNF03 - RNF05 | Alta |
| RF03 | Recuperar Contraseña | Facilitar el acceso en caso de olvidarla | Formulario de recuperación de contraseña | RNF05 - RNF05 | Media/Alta |
| RF04 | Buscador | Facilitar la navegación | Campo Input de Búsqueda | RNF01 - RNF04 | Alta |
| RF05 | Favoritos | Forma de guardar cursos | Encontrar cursos guardados con mayor facilidad | RNF05 |Alta |
| RF06 | Perfil | Información del Usuario | Para su acceso y modificación | RNF05 | Media/Alta
| RF07 | Detalles del Curso | Información del Curso |Para saber de qué se trata el curso | RNF01 - RNF04 | Alta |
| RF08 | Acceso al Curso | Para poder realizarlo | Debe tener acceso a su material también | RNF01 - RNF04 | Alta |
| RF09 | Calificar Curso | Dar puntaje a los cursos | Para conocer la opinión de otros usuarios | RNF03 - RNF02 | Alta |

##### 3.1.3 Requerimientos No Funcionales

| Identificación del Requerimiento | Nombre del Requerimiento | Características | Descripción del Requerimiento | Prioridad del Requerimiento |
| - | - | - | - | - |
| RNF01 | Confiabilidad en el Sistema | Manejo adecuado de errores de red y caídas del API | Evitar disgustos al usuario | Media |
| RNF02 | Mantenimiento | Usar las prácticas correctas | Facilitar los futuros cambios | Alta |
| RNF03 | Diseño UI/UX | Facilitar la navegación al usuario | Que la interfaz sea sencilla | Media |
| RNF04 | Desempeño | La app debe reaccionar bien en las llamadas a la API | Evitar que largos tiempos de espera | Alta |
| RNF05 | Seguridad de la Información | Proteger información sensible | La información del usuario no debe verse comprometida | Alta |

##### 3.1.4 Sprints

| Nro de sprint | Calendario | Sprint Backlog | Responsabilidades | Inconvenientes: |
| - | - | - | - | - |
| 0 | Fecha de inicio: 14/08/2023 / Fecha Cierre: 05/09/2023 | #US01, #US02, #US03, #US04, #US05, #US06, #US07, #US08, #US09, #US10 | - Definir requerimientos funcionales para la app a desarrollar (colocarlos en el Product Backlog del Project), a su vez revisar si han cumplimentado todos los requerimientos previos, realizando mejoras del mismo - Plantear Historias de Usuarios y Tareas dependientes de las US para incorporarlas en el repositorio remoto gitHub. (Issues y Milestones) / Tener en cuenta la redacción adecuada para las US y nomenclatura, ej “#US01 Como usuario quiero ingresar al carrito para poder comprar” - Definir tareas dentro de las Historias de Usuario (GITHUB) ej dentro de las ISSUES  #TK01 importar repositorio | La Organización de los horarios de las reuniones llevó buena parte del tiempo y no todos pudieron conectarse |
| 1 | Fecha de inicio: 05/09/2023 / Fecha Cierre: 30/09/2023 | #TK22, #TK23, #TK24, #TK25, #TK26, #TK27, #TK28, #TK29, #TK30, #TK31, #TK32, #TK33, #TK34, #TK35, #TK36, #TK37, #TK38, #TK39, #TK40, #TK41, #TK42, #TK43, #TK44 | - Crear su propio DER y Modelo relacional para documentar las tablas en la DB. - Crear un Diagrama de Clases y Casos de Uso para facilitar el modelado en POO. Script sql de la base de datos actualizada con las tablas nuevas. - Actualizar el repositorio grupal, creando una carpeta dentro de la branch **Diseño de pantallas de Activity** Se puede presentar maquetación en Figma y principalmente navegabilidad el Proyecto de Android Studio (paso previo sin funcionalidad). - Incluir una pantalla para contacto. - Incorporar la navegabilidad de la aplicación completa - Subirla al repo grupal, con GIT , en una branch por desarrollador para que cada uno tenga una copia. Luego crear una branch feature, para realizar nuestros cambios que no se encuentran aún en condiciones de incorporarse a la branch develop. - La branch “MAIN” o “MASTER” es la que suele usarse para mantener las versiones estables (productivas) de nuestros proyectos. Listas para salir a producción (Aquí deben dejan las versiones al cierre del SPRINT). *Pueden activar en GitHub la opción WIKI para reflejar avances individuales y de equipo.* - NOTA para Evidencia Sprint1: para agilizar la revisión de la navegabilidad, se recomienda hacen un video breve, de no mas de 5 minutos, donde se observe el recorrido (lo puede hacer uno solo de los integrantes) y Documentar las capturas de las Activitys (diseño - print screen o link figma - y código) en un ANEXO II del IEEE830. | Las diferencias con la familiarización y manejo de las nuevas tecnologías y a su vez, la complejidad de algunas pantallas generaron dificultad a la hora de encarar algunas tareas y los tiempos se extendieron un poco más de lo planeado |
| 2 | Fecha de inicio: 30/09/2023 / Fecha Cierre: - | - | - Funcionalidades completas (diseño y desarrollo integrado) - CRUD básico funcional de producto y cliente. - Actualizar documentación IEEE830 - tablero Kanban (project) y Wiki. - Actualizado todo el proyecto en GitHub como respaldo - Demo final en video con participación de todo el equipo describiendo todas las funcionalidades. - *OPCIONALES: aplicación publicada.* | - |

### 4. Anexo de Bitácora II

#### 4.1 Sprint 1

![Image](https://github.com/abelardog/bootcampar/assets/95236196/d3bd6bfa-8cf7-45ef-ba3a-da0a1362e4d1)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/b76050d3-6dc2-4ea5-bc30-337cec022b47)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/a5b21fd3-1a7f-4ca4-9a86-37e95fece689)

- Agregamos las pantallas: Main, Iniciar Sesión y Registro. Ambas actividades con formularios tienen validaciones en caso de intentar ingresar con campos vacíos, contraseñas diferentes, etc. Si se ingresa con la cuenta de “alumno@gmail.com” y contraseña “123456”, se es redirigido al Home/Inicio.

![Image](https://github.com/abelardog/bootcampar/assets/95236196/f63d3692-3e29-4336-87fe-b7100bac2ca8)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/69b98b46-cc14-452d-acb7-4d4c1f2a28ee)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/66b19dac-c714-4ff2-a187-e41f8aa95f15)

- Tenemos también la pantalla para recuperar contraseña, por otro lado, si navegamos por el Inicio, podemos hacer clic en uno de los cursos disponibles y ver su detalle: Título, Descripción e Imagen.

![Image](https://github.com/abelardog/bootcampar/assets/95236196/b7d5abfb-0286-4034-b8d1-a9fc3b3a5711)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/850d26ac-addc-4bcc-97bf-229ee0673a1a)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/46dbbb1d-6973-4943-ac3e-137d37076dd4)

- Si nos inscribimos a un curso (Solo puede hacerse si el usuario está logueado), accederemos a las lecciones disponibles y sus respectivos videos. Por otro lado, se listan los “Cursos Disponibles” y se pueden agregar a favoritos.

![Image](https://github.com/abelardog/bootcampar/assets/95236196/284c7b7e-5ffb-40bc-8b4b-79da8204687f)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/540db45a-0212-49a2-933f-80e16099c8c7)
![Image](https://github.com/abelardog/bootcampar/assets/95236196/e7c3a3fe-f450-4b6c-a64b-400be08938a1)

- Podemos encontrar secciones como “Preguntas Frecuentes”, “Perfil” y “Editar Perfil” para editar datos de la cuenta en caso de que el usuario así lo quisiera

![Image](https://github.com/abelardog/bootcampar/assets/95236196/be78e4c0-3b82-48b4-98e8-8914d617126c)

- Y por último, agregamos una sección de “Contacto”, debido a que era un requisito para este sprint.
