
-------

# Especificación de requisitos de software

<p style="text-align: right" >Proyecto: App Mobile "BootcampAr"
</p>

-------

<p style="text-align: right">
Año: 2023
</p>

-------

### Ficha del Documento

| Fecha | Revisión | Autor | Verificado dep. Calidad
| ----- | ----- | ----- | ----- |
| 2023 | | Pamela Sol Pilotti - Lidio A. Guedez - Yuliana Paula Capdevila- Leonardo Vivas - Laura Natalia Laslo Veron - Lucia Ailen Leonetti - Roberto Alfonso - Facundo M. Díaz C. - Celeste Esquivel | |

-------

## Contenido

- [1 - Introducción](#1-introducción)

  - [1.1 - Propósito](#11-propósito)

  - [1.2 - Alcance](#12-alcance)

  - [1.3 - Personal Involucrado](#14-personal-involucrado)

  - [1.4 - Definiciones, acrónimos y abreviaturas](#15-definiciones-acrónimos-y-abreviaturas)

  - [1.5 - Referencias](#16-referencias)

  - [1.6 - Resumen](#17-resumen)

- [2 - Descripción General](#2-descripción-general)

  - [2.1 - Perspectiva del producto](#21-perspectiva-del-producto)

  - [2.2 - Características de los usuarios](#22-características-de-los-usuarios)

  - [2.3 - Restricciones](#23-restricciones)

  - [*2.4 - Suposiciones y dependencias*]

- [3. Requisitos Específicos](#3-requisitos-específicos)

  - [3.1 - Product Backlog](#31-product-backlog)

    - [3.1.1 - Historias de usuarios y sus tareas](#311-historias-de-usuarios-y-sus-tareas)

    - [*3.1.2 - Requerimientos Funcionales*]

    - [*3.1.3 - Requerimientos No Funcionales*]

    - [3.1.4 - Sprints](#314-sprints)



-------

### 1 Introducción
Este documento es una Especificación de Requisitos Software (ERS) para la aplicación **"BootcampAr - Mobile"** para la gestión de procesos y control de inventarios. Esta especificación se ha estructurado basándose en las directrices dadas por el estándar IEEE Práctica Recomendada para Especificaciones de Requisitos Software ANSI/IEEE 830, 1998.

#### 1.1 Propósito
Nuestro propósito como una página de venta de cursos es informar, generar interés, convencer y facilitar el proceso de inscripción en los cursos ofrecidos, brindando a los visitantes la información y la confianza necesarios para tomar una decisión fundada y realizar la compra.

#### 1.2 Alcance
BootcampAr está dirigido a personas mayores de 16 años que desean capacitarse e incorporar herramientas de desarrollo web para poder insertarse en el mercado laboral o bien para su crecimiento personal. Las empresas también pueden solicitar estos servicios para capacitar a sus empleados.

Las personas que quieran capacitarse en esta plataforma no necesitarán poseer conocimiento previo sobre las herramientas de desarrollo, aunque aquellas personas que tengan un nivel intermedio o avanzado podrán continuar con una capacitación más profunda.

#### 1.3 Personal involucrado

| Nombre | Rol | Categoria Profesional | Responsabilidad | Información de contacto
| ----- | ----- | ----- | ----- | ----- |
| Pamela Pilotti | Desarrolladora / Scrum Master | FullStack | Backend - Frontend - Base de Datos - API REST | pamelasol13@hotmail.com |
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
| ----- | ----- |
| QA | Question/Answer |
| Administrador | Persona que utilizará el sistema para gestionar altas y bajas de productos |
| Alumno | Persona que utilizará el sistema para realizar compras de los productos |
| Productos | Cursos publicados |
| ABMC | Alta, Baja, Modificación y Consulta |


#### 1.5 Referencias

| Título del Documento | Referencia |
| ----- | ----- |
| Standard IEEE 830 - 1998 | IEEE |

#### 1.6 Resumen

"BootcampAr Mobile" es una plataforma de aprendizaje en línea que ofrece cursos sobre tecnología y desarrollo de software. Los usuarios pueden registrarse, acceder a una variedad de cursos, comprarlos con una pasarela de pago integrada y realizar un seguimiento de su progreso en los mismos.

-------

### 2 Descripción General

#### 2.1 Perspectiva del producto
El sistema BootcampAr será un producto diseñado para trabajar en entornos mobile, lo que permitirá su utilización de forma rápida y eficaz, además de brindar herramientas para la formación y fortalecimiento de conocimientos en el área de la matemática, lógica y a los algoritmos orientados a la programación en diversas áreas y lenguajes

#### 2.2 Características de los usuarios

| Tipo de usuario | Formación | Actividades |
| --------------- | --------- | -------------------------------------------------------------------------------------------|
| Administrador | Manejo de herramientas informáticas | Control y manejo del sistema en general |
| Profesor | Manejo de herramientas informáticas y Conocimientos referidos al area a enseñar | ABMC Cursos |
| Alumno | Manejo de aplicación mobile | Formación Académica |
| Visitante | Manejo de entornos web | Navegación Básica |

#### 2.3 Restricciones

- Interfaz para ser usada con internet (solamente).
- Lenguajes y tecnologías en uso: Android, Java, Python, MySQL y/o Firebase
- El sistema se diseñará según un modelo cliente/servidor.
- El sistema deberá tener un diseño e implementación sencillos

-------

### 3 Requisitos específicos

#### 3.1 Product Backlog

##### 3.1.1 Historias de Usuarios y sus Tareas

- #US01 Como visitante quiero conocer la oferta educativa para evaluar mis opciones.
- #TK01 crear sección con los cursos disponibles.

- #US02 como visitante quiero poder registrarme para poder acceder a la oferta educativa.
- #TK02 Crear sección para el registro de usuario "Quiero Registrarme"

- #US03 Como usuario registrado quisiera poder recuperar mi contrasña, para facilitar el acceso en caso de olvidarla.
- #TK03 crear sección "Recuperar Contraseña"

- #US04 Como usuario registrado quisiera contar con un buscador para que sea mas facil la navegacion.
- #TK4 Crear sección "Buscador"

- #US05 Como usuario registrado quisiera poder marcar mis cursos favoritos para poder encontrarlos mas tarde con mayor facilidad.
- #TK05 Crear boton "Favorito" en los cursos disponibles.

- #US06 Como usuario registrado quisiera contar con una sección "Perfil" para poder acceder a el y modificarlo en caso de que sea necesario.
- #TK06 Crear sección "Perfil"

- #US07 como usuario registrado quisiera poder acceder a la compra del curso para poder hacer uso de el.
- #TK07 Crear sección "carrito de compras"

- #US08 como alumno quisiera conocer detalles del curso para estar informado del mismo.
- #TK08 Crear sección "Detalles del curso"

- #US09 como alumno quisiera acceder al curso para poder hacer uso de el.
- #TK09 Crear sección con acceso a los cursos y su material.

- #US10 Como alumno quisiera poder calificar los cursos consumidos para dar a conocer mi opinión sobre ellos.
- #TK10 Crear sección "Calificar Curso"

- #US11 Como profesor quisiera disponer de la creacion y manipulación de los cursos para una mejor administración de las clases.
- #TK11 Crear la sección "Crear Nuevo Curso" la cual permita agregar un nuevo curso y dejar la posibilidad de edición del mismo desde el perfil profesor.

##### 3.1.4 Sprints

| Nro de sprint | 0 |
| ------- | ------- |
| Calendario | Fecha de inicio: 14/08/2023 - Fecha Cierre: 1/09/2023 |
| Sprint Backlog | #US01, #US02, #US03, #US04, #US05, #US06, #US07, #US08, #US09, #US10 |
| Responsabilidades | - [x] Definir requerimientos funcionales para la app a desarrollar (colocarlos en el Product Backlog del Project), a su vez revisar si han cumplimentado todos los requerimientos previos, realizando mejoras del mismo - [x] Plantear Historias de Usuarios y Tareas dependientes de las US para incorporarlas en el repositorio remoto gitHub. (Issues y Milestones) / Tener en cuenta la redacción adecuada para las US y nomenclatura, ej “#US01 Como usuario quiero ingresar al carrito para poder comprar” - [x] Definir tareas dentro de las Historias de Usuario (GITHUB) ej dentro de las ISSUES  #TK01 importar repositorio |
|Inconvenientes: | La Organización de los horarios de las reuniones llevó buena parte del tiempo y no todos pudieron conectarse |
