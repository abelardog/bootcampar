
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

  - [4.1 Sprint 2](#42-sprint-2)

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

- US/TK’s - Sprint 2

  - #TK45 Crear conexión a base de datos SQLite
  - #TK46 Crear clase Database e interfaz IDatabase
  - #TK47 Crear métodos ABMC de Usuarios
  - #TK48 Crear métodos ABMC de Cursos
  - #TK49 Crear métodos ABMC de Inscripciones
  - #TK50 Crear métodos ABMC de Categorias
  - #TK51 Crear métodos ABMC de Categorizaciones
  - #TK52 Crear métodos ABMC de Lecciones
  - #TK53 Crear métodos ABMC de Grupos
  - #TK54 Crear métodos ABMC de Currículas
  - #TK55 Crear métodos ABMC de Divisiones
  - #US22 Listar cursos disponibles
  - #TK56 Cargar información del Curso en CourseDetailActivity
  - #TK57 Pasar modelo de curso a VideoListActivity
  - #TK58 VideoListActivity tiene que levantar las lecciones de base de datos
  - #TK59 Ajustar modelo de cursos a la base de datos
  - #TK60 VideoCourseActivity tiene que recibir un modelo Leccion
  - #TK61 Ajustar el RecyclerView en la actividad principal con datos de la DB
  - #TK62 Reset Password debe comunicarse con una API externa
  - #TK63 Mover método isValidEmail de la actividad a lógica
  - #TK64 Hacer la query a la base de datos y mapear los resultados a objetos Curso
  - #TK65 Hacer funcionar el login
  - #TK66 Encriptar clave de usuario
  - #TK67 Ajustar la actividad para ir al detalle del curso
  - #TK68 Ajustar actividad para poder darle a curso deseado
  - #TK69 Ajustar actividad para el que el boton de deseado solo se vea a los que estan logeados
  - #TK70 Cargar información de perfil en EditProfileActivity
  - #TK71 Grabar cambios de perfil en base de datos
  - #TK72 Crear usuario administrador en base de datos
  - #TK73 Agregar funcionalidad de favorito
  - #TK74 Agregar visual para crear un curso
  - #TK75 Agregar actividad o fragmento para crear lecciones
  - #TK76 Agregar reproductor de video
  - #TK77 Mostrar los cursos más nuevos en el home
  - #TK78 Implementar botón inscripción en CourseDetailActivity
  - #TK79 Al ir atrás desde Detalle del Curso sale de la aplicación
  - #TK80 Cambiar el ícono del listado de cursos
  - #TK81 Reemplazar el Help con Búsqueda
  - #TK82 Hay que agregar scroll al FAQ
  - #TK83 Mostrar listado de cursos favoritos
  - #TK84 Implementar cursos inscriptos
  - #TK85 Arreglar error de ortografia, curos
  - #TK86 Tomar datos de Contacto de modelo de usuario y no permitir cambiarlos
  - #TK87 Flecha para atrás en contacto envía a la pantalla de login
  - #TK88 Flecha para atrás en listado de Cursos envía al login
  - #TK89 La lista de videos no tiene scroll
  - #TK90 Mover calificación a curso en lugar de lección
  - #TK91 Implementar calificación de curso
  - #TK92 Agregar pruebas unitarias a modelo de Categoria
  - #TK93 Agregar pruebas unitarias a modelo de Curricula
  - #TK94 Agregar pruebas unitarias a modelo de Grupo
  - #TK95 Agregar pruebas unitarias a modelo de Division
  - #TK96 Agregar pruebas unitarias a modelo de Inscripcion
  - #TK97 Agregar pruebas unitarias a modelo de Categorizacion
  - #TK98 Agregar pruebas unitarias a modelo de Leccion
  - #TK99 Agregar pruebas unitarias a modelo de Curso
  - #TK100 Renombrar modelo de Course a Curso
  - #TK101 Agregar pruebas unitarias a Database
  - #TK102 Agregar al pipeline las pruebas
  - #TK103 Agregar nuevos modelos e interfaces a diagrama de clases
  - #TK104 Crear pruebas unitarias para modelo de Usuario
  - #TK105 Arreglar tareas del Sprint 0
  - #TK106 Crear modelo Currículas
  - #TK107 Agregar logo a la actividad de login
  - #TK108 Agregar logo a la pantalla de recuperación de clave
  - #TK109 Flecha atrás en recuperación de clave lleva a main
  - #TK110 Renombrar HomeActivity a Home
  - #TK111 Flecha para atrás en Home envía a pantalla
  - #TK112 Sacar cursos recientes de la base de datos
  - #TK113 Implementar inscripción a curso
  - #TK114 Levantar información del video del modelo de lección
  - #TK115 Agregar cursos por defecto
  - #TK116 Crear componente para mostrar calificación
  - #TK117 Calificación promedio del curso
  - #TK118 Mejorar diseño página de ingreso
  - #TK119 Mejorar pagina Home
  - #TK120 Mejorar pagina Registro
  - #TK121 Mejorar página de Perfil
  - #TK122 Mejorar página de Contacto enhancement
  - #TK123 Mejorar página Cursos Disponibles
  - #TK124 Mejorar página Cambio de Contraseña
  - #TK124.1 Mejorar página de Crear Curso
  - #TK125 Mejorar página Crear Lecciones
  - #TK126 Crear icono para App
  - #TK127 Crear botones naranjas
  - #TK128 Cambiar el formato del boton azul
  - #TK129 Botón para atrás en lista de videos retorna al home
  - #TK130 El botón para atrás en video retorna al detalle del curso
  - #TK131 Acortar la recycler view del administrador
  - #TK132 Ajustar altura de detalle del curso
  - #TK133 Listado de videos no se ve bien
  - #TK134 Modificar los botones de las pantallas segun ultimo maquetado
  - #RNF02 Uso de una base de datos local SQLite para almacenar información del usuario
  - #US20 Como administrador de la aplicación, quiero poder gestionar los cursos para garantizar un funcionamiento fluido de la plataforma
  - #US17 Como estudiante quiero cambiar velocidad de reproducción del video en cursos
  - #TK135 Video de accesibilidad
  - #TK136 Video de Selenium
  - #TK137 Agregar descripción a cada estrella de activity_course_detail.xml
  - #TK138 Agregar contentDescription a detailImageView en activity_course_detail.xml
  - #TK139 Ajustar el contraste en botón Inscribirse en activity_course_detail.xml
  - #TK140 El botón de ingresar en el login es muy chico
  - #TK141 Aumentar el tamaño del email en activity_login.xml
  - #TK142 Aumentar el tamaño del password en activity_login.xml
  - #TK143 Aumentar el tamaña del link de olvidó su clave
  - #TK144 Agregar content description al logo
  - #TK145 Modificar LayoutParams para permitir expansión del texto
  - #TK146 Agregar autofill de texto
  - #TK147 Modificar layout params para permitir expansión de texto
  - #TK148 Modificar layout params para permitir expansión del texto
  - #TK149 Modificar layout params para permitir expansión de texto
  - #TK150 Modificar layout params para permitir expansión de texto
  - #TK151 Agregar content description al logo de activity_reset_password.xml
  - #TK152 Agregar content description a las imágenes de las estrellas en activity_video_list.xml
  - #TK153 Modificar layout params para permitir expansión del texto en category_list_item.xml
  - #TK154 Agregar speakable text para el corazón de favoritos
  - #TK155 Agregar content description al logo en fragment_create_course.xml
  - #TK156 Agregar speakable text al botón flotante en fragment_create_course.xml
  - #TK157 Hacer más grande el botón de Agregar en fragment_create_course.xml
  - #TK158 Agregar content description al logo en fragment_create_lessons.xml
  - #TK159 Agregar autotext al editCategoryName en fragment_edit_categories.xm
  - #TK160 Agregar autofill al editCategoryDescription en fragment_edit_categories.xml
  - #TK161 Agregar autofill al fragment_edit_courses.xml
  - #TK162 Agregar autofill y hacer edittext más grandes en fragment_edit_lessons.xml
  - #TK163 Agregarle un speakable text al ícono de la persona
  - #TK164 Agregar content description al logo en fragment_edit_profile.xml
  - #TK165 El texto naranja con fondo blanco no tiene suficiente contraste en fragment_help.xml
  - #TK166 Mejorar el contraste de fragment_rating.xml
  - #TK167 Hacer el texto de editTextText en fragment_search.xml más grande enhancement
  - #TK168 Modificar el layout params para que acepte expansión de texto en lesson_list_item.xml
  - #TK169 Agregar content description a la imagen en recently_added_course.xml
  - #TK170 Pasar todos los hardcoded texts de todos los recursos a strings.xml
  - #TK171 Refrescar la calificación del listado de videos
  - #TK172 Cambiar nombre de usuario en perfil no actualiza contacto
  - #TK173 Quitar de favorito a un curso revienta
  - #US12 Como usuario quiero ser recibido con una pantalla inicial que muestre una selección de cursos más nuevos
  - #US19 Como usuario administrador quiero poder manejar categorías de cursos
  - #US18 Como usuario administrador quiero poder manejar grupos de alumnos
  - #US21 Como usuario administrador quiero agregar y borrar lecciones de cursos
  - #US23 Como estudiante quiero poder utilizar autenticación externa para ingresar a la aplicación
  - #TK174 Implementar autenticación por Auth0
  - #TK175 Problema de solapamiento de fragments

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
| 2 | Fecha de inicio: 30/09/2023 / Fecha Cierre: 29/10/2023 | #TK45, #TK46, #TK47, #TK48, #TK49, #TK50, #TK51, #TK52, #TK53, #TK54, #TK55, #US22, #TK56, #TK57, #TK58, #TK59, #TK60, #TK61, #TK62, #TK63, #TK64, #TK65, #TK66, #TK67, #TK68, #TK69, #TK70, #TK71, #TK72, #TK73, #TK74, #TK75, #TK76, #TK77, #TK78, #TK79, #TK80, #TK81, #TK82, #TK83, #TK84, #TK85, #TK86, #TK87, #TK88, #TK89, #TK90, #TK91, #TK92, #TK93, #TK94, #TK95, #TK96, #TK97, #TK98, #TK99, #TK100, #TK101, #TK102, #TK103, #TK104, #TK105, #TK106, #TK107, #TK108, #TK109, #TK110, #TK111, #TK112, #TK113, #TK114, #TK115, #TK116, #TK117, #TK118, #TK119, #TK120, #TK121, #TK122, #TK123, #TK124.1, #TK124, #TK125, #TK126, #TK127, #TK128, #TK129, #TK130, #TK131, #TK132, #TK133, #TK134, #RNF02, #US20, #US17, #TK135, #TK136, #TK137, #TK138,
#TK139, #TK140, #TK141, #TK142, #TK143, #TK144, #TK145, #TK146, #TK147, #TK148, #TK149, #TK150, #TK151, #TK152, #TK153, #TK154, #TK155, #TK156, #TK157, #TK158, #TK159, #TK160, #TK161, #TK162, #TK163, #TK164, #TK165, #TK166, #TK167, #TK168, #TK169, #TK170, #TK171, #TK172, #TK173, #US12, #US19, #US18, #US21, #US23, #TK174, #TK175
 | - Funcionalidades completas (diseño y desarrollo integrado) - CRUD básico funcional de producto y cliente. - Actualizar documentación IEEE830 - tablero Kanban (project) y Wiki. - Actualizado todo el proyecto en GitHub como respaldo - Demo final en video con participación de todo el equipo describiendo todas las funcionalidades. - *OPCIONALES: aplicación publicada.* - TESTING: 1 - un video donde muestren de manera rápida (1 minuto) como aplicaron Accesibilidad en su proyecto 2 - Automatizar con Selenium IDE el siguiente material publicado Material 3 de estudio obligatorio Eje Temático N° 1 (Archivo .side) | Complicaciones para reunirse para el video final por horarios cruzados debido al trabajo de cada uno y tardanza en la entrega de algunas tareas asignadas para realizar. |

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

#### 4.2 Sprint 2

![image](https://github.com/abelardog/bootcampar/assets/95236196/202a0c8b-1a34-4952-8c05-e3f36c1c2705)

- Home, autentificación y Auth0: Debe registrar un nuevo usuario usar el código de invitación `112233` (la cuenta hardcodeada de alumno@gmail.com ya no existe más)

![image](https://github.com/abelardog/bootcampar/assets/95236196/15100afc-cf08-40bb-b841-0bd9a295ee4b)

- Vemos la vista del estudiante y la búsqueda de cursos (La recuperación de clave no entra en los alcances del sistema, ya que no se comunica con una API externa).

![image](https://github.com/abelardog/bootcampar/assets/95236196/a44a0112-4b84-48b1-96f7-be3dbf8ecdec)

![image](https://github.com/abelardog/bootcampar/assets/95236196/616d5ae6-c130-40cb-8866-bdf437c16673)

- Lecciones y videos asociados a cada lección, por otro lado, Botón de inscripción y calificación.

![image](https://github.com/abelardog/bootcampar/assets/95236196/4e8fc8a4-bfbc-4c5a-9606-80f748828010)

- Preguntas frecuentas o FAQ con accesibilidad visual contemplada y la sección de contacto que tampoco se comunica con una API externa aún.

![image](https://github.com/abelardog/bootcampar/assets/95236196/e47e02bb-5ab7-4149-aff9-a9d840018c18)


- Por último, pero no menos importante, la parte de administrador con sus diferentes secciones. Para ingresar con esta cuenta usar las siguientes credenciales: email: `admin@gmail.com` - contraseña: `123456`
