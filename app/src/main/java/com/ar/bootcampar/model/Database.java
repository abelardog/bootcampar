package com.ar.bootcampar.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.ar.bootcampar.model.utilities.ContentValuesWrapper;
import com.ar.bootcampar.model.utilities.CursorHelper;
import com.ar.bootcampar.model.utilities.Guardia;
import com.ar.bootcampar.model.utilities.IContentValuesWrapper;
import com.ar.bootcampar.model.utilities.ICursorWrapper;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Database extends SQLiteOpenHelper implements IDatabase {
    private static final String ColumnaId = "Id";
    private static final String ColumnaNombre = "Nombre";
    private static final String ColumnaApellido = "Apellido";
    private static final String ColumnaEmail = "Email";
    private static final String ColumnaClave = "Clave";
    private static final String ColumnaRol = "Rol";
    private static final String ColumnaTelefono = "Telefono";
    private static final String[] CamposUsuario = new String[] {ColumnaId, ColumnaNombre, ColumnaApellido, ColumnaEmail, ColumnaClave, ColumnaRol, ColumnaTelefono};
    private static final String TablaUsuario = "Usuarios";
    private static final String ColumnaInvitacion = "Invitacion";
    private static final String[] CamposGrupo = new String[] { ColumnaId, ColumnaNombre, ColumnaInvitacion };
    private static final String TablaGrupo = "Grupos";
    private static final String ColumnaRelacionCurso = "CursoId";
    private static final String ColumnaRelacionGrupo = "GrupoId";
    private static final String ColumnaRelacionUsuario = "UsuarioId";
    private static final String[] CamposDivision = new String[] { ColumnaId, ColumnaRelacionUsuario, ColumnaRelacionGrupo };
    private static final String TablaDivision = "Divisiones";
    private static final String ColumnaTitulo = "Titulo";
    private static final String ColumnaDescripcion = "Descripcion";
    private static final String ColumnaNivel = "Nivel";
    private static final String ColumnaImagen = "Imagen";
    private static final String[] CamposCurso = new String[] { ColumnaId, ColumnaTitulo, ColumnaDescripcion, ColumnaNivel, ColumnaImagen };
    private static final String TablaCurso = "Cursos";
    private static final String ColumnaPuntuacion = "Puntuacion";
    private static final String ColumnaFavorito = "Favorito";
    private static final String ColumnaUltimaLeccion = "UltimaLeccion";
    private static final String[] CamposInscripcion = new String[] { ColumnaId, ColumnaRelacionUsuario, ColumnaRelacionCurso, ColumnaPuntuacion, ColumnaFavorito, ColumnaUltimaLeccion };
    private static final String TablaInscripcion = "Inscripciones";
    private static final String[] CamposCurricula = new String[] { ColumnaId, ColumnaRelacionCurso, ColumnaRelacionGrupo };
    private static final String TablaCurricula = "Curriculas";
    private static final String ColumnaContenido = "Contenido";
    private static final String ColumnaDuracion = "Duracion";
    private static final String ColumnaVinculo = "Vinculo";
    private static final String ColumnaOrden = "Orden";
    private static final String[] CamposLeccion = new String[] { ColumnaId, ColumnaTitulo, ColumnaContenido, ColumnaDuracion, ColumnaOrden, ColumnaVinculo };
    private static final String TablaLeccion = "Lecciones";
    private static final String[] CamposCategoria = new String[] { ColumnaId, ColumnaNombre, ColumnaDescripcion };
    private static final String TablaCategoria = "Categorias";
    private static final String ColumnaRelacionCategoria  = "CategoriaId";
    private static final String[] CamposCategorizacion = new String[] { ColumnaId, ColumnaRelacionCurso, ColumnaRelacionCategoria };
    private static final String TablaCategorizacion = "Categorizaciones";

    public static IDatabase CreateWith(Context applicationContext) {
        // Version 2: Agregar administrador en base de datos
        // Version 3: Agregar campo imagen al curso en base de datos
        // Version 4: Agregar cursos por defecto a la base de datos
        // Version 5: Agregar link a la lección, agregar curso con lecciones de prueba
        // Version 6: Agregar url de youtube para embeber
        // Version 7-8: Se había agregado el curso dos veces
        // Version 9: Los cursos se asocian por defecto al  grupo por defecto
        // Version 10: Agregar curso de HTML
        // Version 11: Agregar curso de C#
        // Version 12: Acomodar nombres de lecciones
        // Version 13: Agregar curso fullstack
        // Version 14: Borrar cursos innecesarios
        return new Database(applicationContext, "bootcampar.db", null, 14);
    }

    protected Database(Context applicationContext, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(applicationContext, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {
        ISQLiteDatabaseWrapper db = new SQLiteDatabaseWrapper(sqliteDatabase);
        createDatabase(db);
    }

    private void createDatabase(ISQLiteDatabaseWrapper db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("PRAGMA foreign_key_check");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaUsuario + "(\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaNombre + " TEXT NOT NULL,\n" +
                ColumnaApellido + " TEXT NOT NULL,\n" +
                ColumnaEmail + " TEXT NOT NULL,\n" +
                ColumnaClave + " TEXT NOT NULL,\n" +
                ColumnaRol + " INTEGER,\n" +
                ColumnaTelefono + " TEXT\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaCurso + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaTitulo + " TEXT NOT NULL,\n" +
                ColumnaDescripcion + " TEXT,\n" +
                ColumnaNivel + " INTEGER NOT NULL,\n" +
                ColumnaImagen + " TEXT\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaInscripcion + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionUsuario + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaPuntuacion + " INTEGER,\n" +
                ColumnaFavorito + " INTEGER,\n" +
                ColumnaUltimaLeccion + " INTEGER,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionUsuario + ") REFERENCES " + TablaUsuario + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionCurso + ") REFERENCES " + TablaCurso + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaCategoria + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaNombre + " TEXT NOT NULL,\n" +
                ColumnaDescripcion + " TEXT\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaCategorizacion + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCategoria + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY ("+ ColumnaRelacionCurso + ") REFERENCES " + TablaCurso + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY ("+ ColumnaRelacionCategoria + ") REFERENCES " + TablaCategoria + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaLeccion + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaTitulo + " TEXT NOT NULL,\n" +
                ColumnaVinculo + " TEXT,\n" +
                ColumnaContenido + " TEXT,\n" +
                ColumnaDuracion + " INTEGER NOT NULL,\n" +
                ColumnaOrden + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY ("+ ColumnaRelacionCurso + ") REFERENCES " + TablaCurso + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaGrupo + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaNombre + " TEXT NOT NULL,\n" +
                ColumnaInvitacion + " TEXT NOT NULL\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaCurricula + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaRelacionGrupo + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (GrupoId) REFERENCES " + TablaGrupo + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (CursoId) REFERENCES " + TablaCurso + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaDivision + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionUsuario + " INTEGER NOT NULL,\n" +
                ColumnaRelacionGrupo + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionGrupo + ") REFERENCES " + TablaGrupo + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionUsuario + ") REFERENCES " + TablaUsuario + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("PRAGMA foreign_key_check");

        insertarGrupoPorDefecto(db);
        insertarAdministrator(db);
        insertarCursoDePythonBasicsWithSam(db, 1);
        insertarCursoDePatrones(db, 2);
        insertarCursoDeHtml(db, 3);
        insertarCursoDeCsharp(db, 4);
        instertarCursoErreArgentinaPrograma(db, 5);

        asociarCursosConGrupoPorDefecto(db);
    }

    /* imagen puede ser java, js, python, html, wordpress, css, angular */
    private void instertarCursoErreArgentinaPrograma(ISQLiteDatabaseWrapper db, int cursoId) {
        db.execSQL("INSERT INTO " + TablaCurso + "(" + ColumnaTitulo + ", " + ColumnaDescripcion + ", " + ColumnaImagen + ", " + ColumnaNivel + ") VALUES (" +
                "'Curso Fullstack de r/ArgentinaPrograma', 'Este es un curso para aprender JavaScript desde 0, gratis y en español. Para más info, visita: https://argentinaprograma.com/', 'js', 2)");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 1', 'Introducción a la programación', 9283, 1, 'https://www.youtube.com/embed/JkwDaUSivJA?si=sgfrUmxTQWxh6qT5', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 2', 'Tarea de Clase 1 + Scope + Debugging + IF/ELSE', 6504, 2, 'https://www.youtube.com/embed/366HDMfd2-E?si=AFwRILllWsOFJ--G', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 3', 'Finalización de nivel1 + interpolación de strings + tarea', 2846, 3, 'https://www.youtube.com/embed/I31mC2TXOxQ?si=XV6r1wrX5jzuRiYO', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 4', 'Corrección de Tarea 3+ Operadores Lógicos + NaN + Nivel 2 completo', 8325, 4, 'https://www.youtube.com/embed/sI_u2Nj84Ls?si=CswJqyp4DfHxZa7w', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 5', 'Corrección Tarea Clase 4 + Git/GitHub/GitHub Desktop + HTML, CSS, EventListeners', 9648, 5, 'https://www.youtube.com/embed/52AYl1AAs1o?si=MJsyeDESyJKLpHzz', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 6', 'Repaso clase 5, cómo manipular HTML con JS, primer tarea de la clase 5 hecha en vivo.', 4448, 6, 'https://www.youtube.com/embed/pHDBIy1qUF4?si=t3o74KJp8rbptWN3', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 7', 'Corrección tarea clase 6 e Introducción a validación de formularios + Unit Tests', 5440, 7, 'https://www.youtube.com/embed/da8Ekd47IOY?si=Kwt4bIvSK9jhFokG', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 8', 'Corrección Tarea 7 + Regular Expression + Objetos', 7495, 8, 'https://www.youtube.com/embed/R4foJQCAZvQ?si=EDVmAjNJFZHUXgIL', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 9', 'Repaso de Git y Github (con GitHub Desktop)', 2585, 9, 'https://www.youtube.com/embed/QWeDBSHHPQI?si=g53FHGRfd-kHC2rX', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 10', 'Juego simón dice, Bootstrap y NPM', 5728, 10, 'https://www.youtube.com/embed/sbUPqBh0KfA?si=Bhflz2cEnmZFZZPO', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 11', 'Memotest + Cypress', 6258, 11, 'https://www.youtube.com/embed/3PwEu0YJnX8?si=YOTIpj6-9Tsl0fUi', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 12', 'Corrección Tarea 11 + jQuery + Promises + API', 7892, 12, 'https://www.youtube.com/embed/ZFx2bd91KyU?si=pRy_5i2RTTDpmpyZ', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 13', 'Corrección Tarea 12, ESLint, ESM, Async/Await.', 6722, 13, 'https://www.youtube.com/embed/cY-OQERZBR0?si=bULnwaMi0zDtLept', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 14', 'localStorage, ESM vs CJS, Babel, Jest, network requests con Cypress', 7481, 14, 'https://www.youtube.com/embed/yQxobcVFj7w?si=tdDZFJAzzTbH4vVj', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 15', 'Qué viene en el resto del curso + Clases (OOP)', 7457, 15, 'https://www.youtube.com/embed/EfIDTQET0e0?si=meX5ClkavdmloJbT', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 16', 'Corrección clase 15 + Teoría de diseño de software', 6135, 16, 'https://www.youtube.com/embed/Ymth8fks8xc?si=y_eBOO6yx1fZiznD', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 17', 'Introducción a Node.js', 6559, 17, 'https://www.youtube.com/embed/8LxxQeNCu4U?si=LOg0Z_ep1Itfo8We', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 18.1', 'Dependency Inversion Principle', 3004, 18, 'https://www.youtube.com/embed/6D5kLEK-LAw?si=yRUnfBFfYhVrTpQ2', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 18.2', 'Explicación de MVC, Dotenv, Sesiones, Cookies y corrección de tarea 17', 5712, 19, 'https://www.youtube.com/embed/Rqb8OKkW-aE?si=Dzw34jG53DIaLdtw', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 18.3', 'Intro a Base de Datos, SQL con SQLite', 3944, 20, 'https://www.youtube.com/embed/2qtJ8ybNOFM?si=AUgqHJIu3G1IsxVv', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 18.4', 'Índices, Claves Foráneas, ejecutando SQL en JS, Implementación de SQLite', 4459, 21, 'https://www.youtube.com/embed/HKT4N9XC9KI?si=ac5p53oZvzGkqD5_', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 18.5', 'ORM (Sequelize)', 7614, 22, 'https://www.youtube.com/embed/s-AFZjRijD0?si=OQdMoxevCFinHWhb', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 19.1', 'Corrección sistema de alquiler de autos (básico)', 4930, 23, 'https://www.youtube.com/embed/1F9ma_Nb_gw?si=51hJVe68e5kjU33k', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 19.2', 'SASS y GULP', 4748, 24, 'https://www.youtube.com/embed/QDS-y_opsZM?si=3PQCQ20P_WOJyQHQ', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Clase 19.3', 'Introducción a TypeScript', 4039, 25, 'https://www.youtube.com/embed/1tfSmvLIof0?si=kBbr0IWYIAZ7O2A9', " + cursoId + ")");
    }

    private void insertarCursoDeCsharp(ISQLiteDatabaseWrapper db, int cursoId) {
        db.execSQL("INSERT INTO " + TablaCurso + "(" + ColumnaTitulo + ", " + ColumnaDescripcion + ", " + ColumnaImagen + ", " + ColumnaNivel + ") VALUES (" +
                "'How to Program In C#', 'Coding can seem scary at first - but its actually not that hard! Lets learn how to program in C#.', 'csharp', 1)");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Introduction', 'Getting Started!', 635, 1, 'https://www.youtube.com/embed/N775KsWQVkw?si=rO-p2hctAbTiBLqo', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 1', 'Basics', 707, 2, 'https://www.youtube.com/embed/jGD0vn-QIkg?si=h7_PDyUFexodVUPk', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 2', 'Variables', 829, 3, 'https://www.youtube.com/embed/g-9Jp4dmOBo?si=oO8kneV-bl-RYmN7', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 3', 'Conditions', 895, 4, 'https://www.youtube.com/embed/u_Qv5IrMUqg?si=V4SOdQmNrxMG5FkT', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 4', 'Loops', 1107, 5, 'https://www.youtube.com/embed/9ozOSKCiO0I?si=rWkiGrUzJieZBZhP', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 5', 'Arrays', 1020, 6, 'https://www.youtube.com/embed/YiE0oetGMAg?si=iRkH28bcaDK_tnrG', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 6', 'Methods', 1039, 7, 'https://www.youtube.com/embed/bPQx0paXrbw?si=P_FC8zQCbF9FTYll', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 7', 'Classes', 1180, 8, 'https://www.youtube.com/embed/WKjfCHxeEz4?si=NPCQAzSX0KCIVF4f', " + cursoId + ")");
    }

    private void insertarCursoDeHtml(ISQLiteDatabaseWrapper db, int cursoId) {
        db.execSQL("INSERT INTO " + TablaCurso + "(" + ColumnaTitulo + ", " + ColumnaDescripcion + ", " + ColumnaImagen + ", " + ColumnaNivel + ") VALUES (" +
                "'HTML Tutorial for Beginners', 'This series will cover the latest concepts including HTML5.', 'html', 1)");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Introduction', 'Introduction to HTML', 211, 1, 'https://www.youtube.com/embed/dD2EISBDjWM?si=x4wbKpvEonfTmEmi', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 1', 'Creating the first web page', 609, 2, 'https://www.youtube.com/embed/-USAeFpVf_A?si=mkhmMOnwAsFvGRkm', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 2', 'Line breaks, spacing, and comments', 389, 3, 'https://www.youtube.com/embed/i3GE-toQg-o?si=MfhWW-S2ahK5LahT', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 3', 'Ordered and Unordered lists', 244, 4, 'https://www.youtube.com/embed/09oErCBjVns?si=RVxHitymn_9wyKlc', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 4', 'Creating a table', 251, 5, 'https://www.youtube.com/embed/wvR40su_XBM?si=p0strwy8DK1frI8U', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 5', 'Creating a web link', 194, 6, 'https://www.youtube.com/embed/U4UHoiK6Oo4?si=o5VTDPrbIlwNiwG-', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 6', 'Creating links within same web page', 254, 7, 'https://www.youtube.com/embed/bCt2FnyY7AE?si=B5UcMPno1qY5k4r5', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 7', 'Adding a image to a web page', 101, 8, 'https://www.youtube.com/embed/Zy4KJeVN7Gk?si=VNeA4iD-HzsJuGZZ', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 8', 'Resizing and sizing images', 192, 9, 'https://www.youtube.com/embed/dM12ctixdT4?si=FF_FXi5CjPSQA63x', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 9', 'Nested elements', 83, 10, 'https://www.youtube.com/embed/rO6_MZLIzCg?si=oUCWwvCbozPEK2jP', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 10', 'One-line text box', 226, 11, 'https://www.youtube.com/embed/wvU1mmJn_UI?si=QAS07spznbFyrv33', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 11', 'Add label to text box', 172, 12, 'https://www.youtube.com/embed/f9QXNFVlsls?si=x1-ERFIJ56oUpXMb', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 12', 'Multi-line text box', 149, 13, 'https://www.youtube.com/embed/onKF88kRK3Q?si=6sjbBbDcGNCPoacg', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 13', 'Radio buttons', 145, 14, 'https://www.youtube.com/embed/NDAa6EaKce8?si=YoqeoyGbKxeEEr65', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 14', 'Checkbox', 87, 15, 'https://www.youtube.com/embed/g4UAV1lIHyA?si=G4l1qVIKGl1Anz0E', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 15', 'Number input box', 96, 16, 'https://www.youtube.com/embed/NPfy-hKOGfk?si=TNhIYFtTVGUf8Z9K', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 16', 'Drop-down list', 146, 17, 'https://www.youtube.com/embed/yWuAsqUnNsA?si=vg9LLzhsEaHjVyyi', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 17', 'Date and number box', 158, 18, 'https://www.youtube.com/embed/H6BSr5UOg2Y?si=UEt5tNfEXrPvAKor', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 18', 'Fieldbox and Legend elements', 112, 19, 'https://www.youtube.com/embed/x13Uxl6_dyw?si=70XIfDHYvN-Btq5F', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 19', 'Attributes', 193, 20, 'https://www.youtube.com/embed/iWWTtYGZ4YA?si=jpkJs4DHG-aqcF9m', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 20', 'The meta element', 244, 21, 'https://www.youtube.com/embed/sx4kaeyzQzw?si=6iXGuZGviiv6N9n7', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 21', 'Escape characters', 122, 22, 'https://www.youtube.com/embed/s3h5FLBon88?si=_EBkLlPA_I6Sjc4g', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 22', 'Bold and italic elements', 164, 23, 'https://www.youtube.com/embed/X_OROia6aPo?si=p-98kUO_-yUfh4-6', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 23', 'iframe element', 307, 24, 'https://www.youtube.com/embed/7PORMYx30xE?si=4PZKQdirOV_kDT4T', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 24', 'sup and sub elements', 101, 25, 'https://www.youtube.com/embed/iG703SLOJ-Q?si=g95sORizhUz0d5yn', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 25', 'Title and alt attributes', 145, 26, 'https://www.youtube.com/embed/kA5pqPA5eZE?si=NDLTzF5prXgmj6wS', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 26', 'Audio element', 169, 27, 'https://www.youtube.com/embed/7tWBcT83hek?si=SfDt7EBRnG2Im5jd', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 27', 'Audio element attributes', 135, 28, 'https://www.youtube.com/embed/2aenvVrYWjg?si=5akApfO68thuRjn9', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 28', 'Video element', 108, 29, 'https://www.youtube.com/embed/iIgFqkDs4tY?si=l2bJhsIFCOfnrwUp', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 29', 'doctype', 143, 30, 'https://www.youtube.com/embed/c625P4B0OY0?si=pjboIEh2FgzZSwqW', " + cursoId + ")");
    }

    private void insertarCursoDePatrones(ISQLiteDatabaseWrapper db, int cursoId) {
        db.execSQL("INSERT INTO " + TablaCurso + "(" + ColumnaTitulo + ", " + ColumnaDescripcion + ", " + ColumnaImagen + ", " + ColumnaNivel + ") VALUES (" +
                "'Design Patterns', 'Learn about Design Patterns in JavaScript with Beau', 'js', 2)");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 1', 'Singleton Design Pattern', 290, 1, 'https://www.youtube.com/embed/bgU7FeiWKzc?si=M4Ueo_ghh9tLOjZK', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 2', 'Observer Design Pattern', 236, 2, 'https://www.youtube.com/embed/3PUVr8jFMGg?si=MStdKuUxtU69R0yj', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 3', 'Module Design Pattern', 164, 3, 'https://www.youtube.com/embed/3pXVHRT-amw?si=G5XIaQftkSQPVVr-', "+ cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 4', 'Mediator Design Pattern', 308, 4, 'https://www.youtube.com/embed/KOVc5o5kURE?si=l6faPVQUgaf37jnX', " + cursoId + ")");
    }

    private void insertarCursoDePythonBasicsWithSam(ISQLiteDatabaseWrapper db, int cursoId) {
        db.execSQL("INSERT INTO " + TablaCurso + "(" + ColumnaTitulo + ", " + ColumnaDescripcion + ", " + ColumnaImagen + ", " + ColumnaNivel + ") VALUES (" +
                "'Python Basics with Sam', 'Learn the basics of Python live from Sam Focht every Tuesday. This is part of a series that will cover the entire Python Programming language.', 'python', 1)");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 1', 'Intro to Python Livestream', 7157, 1, 'https://www.youtube.com/embed/z2k9Jh3jDVU?si=Usm-VZf3o8NG6QIP', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 2', 'Python For Loops, Functions, and Random', 7129, 2, 'https://www.youtube.com/embed/4UuMrebbwIo?si=8i4VTWPAynhgu_Aq', " + cursoId +")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 3', 'Prime Numbers, Times Tables, & More', 7185, 3, 'https://www.youtube.com/embed/DhdOKh5Issw?si=WKku1m1eUUr5SW8Y', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 4', 'Find Longest Substring / Guessing Game', 7135, 4, 'https://www.youtube.com/embed/hoP7_DkrmiA?si=ZwoTyReFvy3wYaX3', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 5', 'Command Line and Recursion in Python', 5644, 5, 'https://www.youtube.com/embed/2T8BFVPhYPs?si=v3GGjaLFesrJjfKl', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 6', 'Scope and Decorators', 6353, 6, 'https://www.youtube.com/embed/VckRJ6v1yWU?si=tFP2-QQkVnzPjMU0', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 7', 'Build a Shopping List for the Command Line', 3990, 7, 'https://www.youtube.com/embed/xapvhkhlPNI?si=f2ydLAAG0y7qTc3_', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 8', 'Generators and Classes', 4712, 8, 'https://www.youtube.com/embed/UzDuMsnTIGQ?si=WJu3elsQF7izyxpb', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 9', 'Board Game, Lists and More', 5024, 9, 'https://www.youtube.com/embed/1vMtftJf7tQ?si=nyMJFceeo2mR-h0D', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 10', 'Chicken Nuggets and itertools', 3586, 10, 'https://www.youtube.com/embed/kZNIHeCaZiM?si=Qh6cZj0zpN_Hx6hG', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 11', 'Tkinter Calculator', 3710, 11, 'https://www.youtube.com/embed/PkLwJicRI8s?si=8NABvmOWtl-Wt911',  " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 12', 'Random Password Generator', 3440, 12, 'https://www.youtube.com/embed/3j6v4wBZWR8?si=jAU0j1Xja6xV8sIZ', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 13', 'Solving Python Challenges', 3874, 13, 'https://www.youtube.com/embed/iVajTZgMk4M?si=cU4eiqT_M3YlPfNy', " + cursoId + ")");
        db.execSQL("INSERT INTO " + TablaLeccion + "(" + ColumnaTitulo + ", " + ColumnaContenido + ", " + ColumnaDuracion + ", " + ColumnaOrden + ", " + ColumnaVinculo + ", " + ColumnaRelacionCurso + ") VALUES (" +
                "'Lesson 14', 'Python Main Function', 3772, 14, 'https://www.youtube.com/embed/mvXDQNNcDu4?si=IHBFPK-ecmuwlRRf', " + cursoId + ")");
    }

    private void asociarCursosConGrupoPorDefecto(ISQLiteDatabaseWrapper db) {
        db.execSQL("INSERT INTO " + TablaCurricula + "(" + ColumnaRelacionGrupo + ", " + ColumnaRelacionCurso + ") VALUES (1, 1)");
        db.execSQL("INSERT INTO " + TablaCurricula + "(" + ColumnaRelacionGrupo + ", " + ColumnaRelacionCurso + ") VALUES (1, 2)");
        db.execSQL("INSERT INTO " + TablaCurricula + "(" + ColumnaRelacionGrupo + ", " + ColumnaRelacionCurso + ") VALUES (1, 3)");
        db.execSQL("INSERT INTO " + TablaCurricula + "(" + ColumnaRelacionGrupo + ", " + ColumnaRelacionCurso + ") VALUES (1, 4)");
        db.execSQL("INSERT INTO " + TablaCurricula + "(" + ColumnaRelacionGrupo + ", " + ColumnaRelacionCurso + ") VALUES (1, 5)");
    }

    private void insertarAdministrator(ISQLiteDatabaseWrapper db) {
        db.execSQL("INSERT INTO " + TablaUsuario + "("+ ColumnaNombre + ", " + ColumnaApellido + ", " + ColumnaEmail + ", " + ColumnaClave + ", " + ColumnaRol + ", " + ColumnaTelefono + ") VALUES ('Admin', 'Admin', 'admin@gmail.com', '123456', 1, '')");
    }

    private void insertarGrupoPorDefecto(ISQLiteDatabaseWrapper db) {
        db.execSQL("INSERT INTO " + TablaGrupo + "(" + ColumnaNombre + ", " + ColumnaInvitacion + ") VALUES ('Grupo de Programadores', '112233')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, int previousVersion, int newVersion) {
        ISQLiteDatabaseWrapper db = new SQLiteDatabaseWrapper(sqliteDatabase);
        db.execSQL("DROP TABLE IF EXISTS " + TablaDivision);
        db.execSQL("DROP TABLE IF EXISTS " + TablaCurricula);
        db.execSQL("DROP TABLE IF EXISTS " + TablaGrupo);
        db.execSQL("DROP TABLE IF EXISTS " + TablaLeccion);
        db.execSQL("DROP TABLE IF EXISTS " + TablaCategorizacion);
        db.execSQL("DROP TABLE IF EXISTS " + TablaCategoria);
        db.execSQL("DROP TABLE IF EXISTS " + TablaInscripcion);
        db.execSQL("DROP TABLE IF EXISTS " + TablaCurso);
        db.execSQL("DROP TABLE IF EXISTS " + TablaUsuario);
        createDatabase(db);
    }

    @Override
    public Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono) {
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaNombre, nombre);
        values.put(ColumnaApellido, apellido);
        values.put(ColumnaEmail, email);
        values.put(ColumnaClave, clave);
        values.put(ColumnaRol, Rol.asInt(rol));
        values.put(ColumnaTelefono, telefono);

        return (Usuario)crearElemento(TablaUsuario, values, id -> new Usuario(id, nombre, apellido, email, clave, rol, telefono), "Error creando usuario");
    }

    @Override
    public Usuario buscarUsuarioOExplotar(long id) {
        return (Usuario)buscarElementoOExplotar(TablaUsuario, CamposUsuario, id,
                Database::obtenerUsuarioDeCursor, "Se esperaba encontrar un único usuario con id %d, se encontraron %d");
    }

    @NonNull
    private static Usuario obtenerUsuarioDeCursor(ICursorWrapper cursor) {
        return obtenerUsuarioDeCursor(cursor, "");
    }

    @NonNull
    private static Usuario obtenerUsuarioDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Usuario(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                cursorHelper.getStringFrom(prefijo + ColumnaNombre),
                cursorHelper.getStringFrom(prefijo + ColumnaApellido),
                cursorHelper.getStringFrom(prefijo + ColumnaEmail),
                cursorHelper.getStringFrom(prefijo + ColumnaClave),
                Rol.fromInt(cursorHelper.getIntFrom(prefijo + ColumnaRol)),
                cursorHelper.getStringFrom(prefijo + ColumnaTelefono));
    }

    @Override
    public Usuario buscarUsuarioONada(String email) {
        return (Usuario)buscarElementoONada(TablaUsuario, CamposUsuario, ColumnaEmail, email,
                Database::obtenerUsuarioDeCursor, "Se encontraron varios usuarios con el mismo email %s");
    }

    @Override
    public Usuario modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoEmail, String nuevaClave, Rol nuevoRol, String nuevoTelefono) {
        Guardia.esObjetoValido(usuario, "El usuario es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaNombre, nuevoNombre);
        values.put(ColumnaApellido, nuevoApellido);
        values.put(ColumnaEmail, nuevoEmail);
        values.put(ColumnaClave, nuevaClave);
        values.put(ColumnaRol, Rol.asInt(nuevoRol));
        values.put(ColumnaTelefono, nuevoTelefono);

        return (Usuario)modificarElemento(TablaUsuario, usuario.getId(), values, id -> new Usuario(id, nuevoNombre, nuevoApellido, nuevoEmail, nuevaClave, nuevoRol, nuevoTelefono), "Se esperaba modificar un único usuario pero se modificaron %d");
    }

    @Override
    public void borrarUsuario(Usuario usuario) {
        Guardia.esObjetoValido(usuario, "El usuario es nulo");
        borrarElemento(TablaUsuario, usuario.getId(), "Se esperaba borrar un único usuario pero se borraron %d");
    }

    @Override
    public Division crearDivision(Usuario usuario, Grupo grupo) {
        Guardia.esObjetoValido(usuario, "El usuario es nulo");
        Guardia.esObjetoValido(grupo, "El grupo es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionUsuario, usuario.getId());
        values.put(ColumnaRelacionGrupo, grupo.getId());

        return (Division)crearElemento(TablaDivision, values, id -> new Division(id, usuario, grupo), "Error creando división");
    }

    @Override
    public void borrarDivision(Division division) {
        Guardia.esObjetoValido(division, "La división es nula");
        borrarElemento(TablaDivision, division.getId(), "Se esperaba borrar una única división pero se borraron %d");
    }

    @Override
    public Division modificarDivision(Division division, Usuario nuevoUsuario, Grupo nuevoGrupo) {
        Guardia.esObjetoValido(division, "La división es nula");
        Guardia.esObjetoValido(nuevoUsuario, "El usuario es nulo");
        Guardia.esObjetoValido(nuevoGrupo, "El grupo es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionUsuario, nuevoUsuario.getId());
        values.put(ColumnaRelacionGrupo, nuevoGrupo.getId());

        return (Division) modificarElemento(TablaDivision, division.getId(), values, id -> new Division(division.getId(), nuevoUsuario, nuevoGrupo),"Se esperaba modificar una única división pero se modificaron %d");

    }

    @Override
    public Division buscarDivisionOExplotar(long id) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaDivision + ", " + TablaGrupo + ", " + TablaUsuario,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaDivision, CamposDivision),
                            agregarNombreDeTablaEnColumnas(TablaGrupo, CamposGrupo),
                            agregarNombreDeTablaEnColumnas(TablaUsuario, CamposUsuario)),
                    TablaDivision + "." + ColumnaId + "=? AND " + ColumnaRelacionGrupo + " = " + TablaGrupo + "." + ColumnaId +
                            " AND " + ColumnaRelacionUsuario + " = " + TablaUsuario + "." + ColumnaId,
                    new String[] { String.valueOf(id) }, null, null, null);

            if (cursor.getCount() == 1) {
                return obtenerDivisionDeCursor(cursor, TablaDivision + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única división con id %d, se encontraron %d", id, cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Division buscarDivisionONada(Usuario usuario, Grupo grupo) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaDivision + ", " + TablaGrupo + ", " + TablaUsuario,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaDivision, CamposDivision),
                            agregarNombreDeTablaEnColumnas(TablaUsuario, CamposUsuario),
                            agregarNombreDeTablaEnColumnas(TablaGrupo, CamposGrupo)),
                   ColumnaRelacionGrupo + " = " + TablaGrupo + "." + ColumnaId + " AND " +
                            ColumnaRelacionUsuario + " = " + TablaUsuario + "." + ColumnaId + " AND " +
                            ColumnaRelacionGrupo + " =? AND " + ColumnaRelacionUsuario + " =?",
                    new String[] { String.valueOf(grupo.getId()), String.valueOf(usuario.getId()) }, null, null, null);

            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return obtenerDivisionDeCursor(cursor, TablaDivision + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única división pero se encontraron %d", cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Grupo crearGrupo(String nombre, String invitacion) {
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaNombre, nombre);
        values.put(ColumnaInvitacion, invitacion);

        return (Grupo)crearElemento(TablaGrupo, values, id -> new Grupo(id, nombre, invitacion), "Error creando grupo");
    }

    @Override
    public Grupo buscarGrupoONada(String invitacion) {
        return (Grupo)buscarElementoONada(TablaGrupo, CamposGrupo, ColumnaInvitacion, invitacion,
                Database::obtenerGrupoDeCursor,"Se encontraron varios grupos con la misma invitación %s");
    }

    @Override
    public List<Grupo> listarGrupos() {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Grupo> resultado = new ArrayList<>();

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaGrupo, CamposGrupo, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Grupo grupo = new Grupo(
                            cursorHelper.getLongFrom(ColumnaId),
                            cursorHelper.getStringFrom(ColumnaNombre),
                            cursorHelper.getStringFrom(ColumnaInvitacion));

                    resultado.add(grupo);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    private Division obtenerDivisionDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Division(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                obtenerUsuarioDeCursor(cursor, TablaUsuario + "_"),
                obtenerGrupoDeCursor(cursor, TablaGrupo + "_"));
    }

    @NonNull
    private static Grupo obtenerGrupoDeCursor(ICursorWrapper cursor) {
        return obtenerGrupoDeCursor(cursor, "");
    }

    private static Categoria obtenerCategoriaDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Categoria(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                cursorHelper.getStringFrom(prefijo + ColumnaNombre),
                cursorHelper.getStringFrom(prefijo + ColumnaDescripcion));
    }

    private static Categoria obtenerCategoriaDeCursor(ICursorWrapper cursor) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Categoria(
                cursorHelper.getLongFrom(ColumnaId),
                cursorHelper.getStringFrom(ColumnaNombre),
                cursorHelper.getStringFrom(ColumnaDescripcion));
    }

    @NonNull
    private static Grupo obtenerGrupoDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Grupo(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                cursorHelper.getStringFrom(prefijo + ColumnaNombre),
                cursorHelper.getStringFrom(prefijo + ColumnaInvitacion));
    }

    @NonNull
    private static Curso obtenerCursoDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Curso(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                cursorHelper.getStringFrom(prefijo + ColumnaTitulo),
                cursorHelper.getStringFrom(prefijo + ColumnaDescripcion),
                cursorHelper.getStringFrom(prefijo + ColumnaImagen),
                cursorHelper.getIntFrom(prefijo + ColumnaNivel));
    }

    @NonNull
    private static Curso obtenerCursoDeCursor(ICursorWrapper cursor) {
        return obtenerCursoDeCursor(cursor, "");
    }

    @NonNull
    private static Leccion obtenerLeccionDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Leccion(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                cursorHelper.getStringFrom(prefijo + ColumnaTitulo),
                cursorHelper.getStringFrom(prefijo + ColumnaContenido),
                cursorHelper.getIntFrom(prefijo + ColumnaDuracion),
                cursorHelper.getIntFrom(prefijo + ColumnaOrden),
                cursorHelper.getStringFrom(prefijo + ColumnaVinculo),
                obtenerCursoDeCursor(cursor, TablaCurso + "_"));
    }

    @NonNull
    private static Inscripcion obtenerInscripcionDeCursor(ICursorWrapper cursor, Usuario usuario) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Inscripcion(
                cursorHelper.getLongFrom(TablaInscripcion + "_" + ColumnaId),
                usuario,
                obtenerCursoDeCursor(cursor, TablaCurso + "_"),
                cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaPuntuacion),
                cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaFavorito) != 0,
                cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaUltimaLeccion));
    }

    @NonNull
    private static Inscripcion obtenerInscripcionDeCursor(ICursorWrapper cursor, Curso curso) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Inscripcion(
                cursorHelper.getLongFrom(TablaInscripcion + "_" + ColumnaId),
                obtenerUsuarioDeCursor(cursor, TablaUsuario + "_"),
                curso,
                cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaPuntuacion),
                cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaFavorito) != 0,
                cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaUltimaLeccion));
    }

    @NonNull
    private static Inscripcion obtenerInscripcionDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Inscripcion(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                obtenerUsuarioDeCursor(cursor, TablaUsuario + "_"),
                obtenerCursoDeCursor(cursor, TablaCurso + "_"),
                cursorHelper.getIntFrom(prefijo + ColumnaPuntuacion),
                cursorHelper.getIntFrom(prefijo + ColumnaFavorito) != 0,
                cursorHelper.getIntFrom(prefijo + ColumnaUltimaLeccion));
    }

    @NonNull
    private static Categorizacion obtenerCategorizacionDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Categorizacion(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                obtenerCursoDeCursor(cursor, TablaCurso + "_"),
                obtenerCategoriaDeCursor(cursor, TablaCategoria + "_"));
    }

    @NonNull
    private static Curricula obtenerCurriculaDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Curricula(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                obtenerCursoDeCursor(cursor, TablaCurso + "_"),
                obtenerGrupoDeCursor(cursor, TablaGrupo + "_"));
    }

    @Override
    public Grupo buscarGrupoOExplotar(long id) {
        return (Grupo)buscarElementoOExplotar(TablaGrupo, CamposGrupo, id,
                Database::obtenerGrupoDeCursor, "Se esperaba encontrar un grupo con id %d pero se encontraron %d");
    }

    @Override
    public void borrarGrupo(Grupo grupo) {
        Guardia.esObjetoValido(grupo, "El grupo es nulo");
        borrarElemento(TablaGrupo, grupo.getId(), "Se esperaba borrar un único grupo pero se borraron %d");
    }

    @Override
    public Grupo modificarGrupo(Grupo grupo, String nuevoNombre, String nuevaInvitacion) {
        Guardia.esObjetoValido(grupo, "El grupo es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaNombre, nuevoNombre);
        values.put(ColumnaInvitacion, nuevaInvitacion);

        return (Grupo)modificarElemento(TablaGrupo, grupo.getId(), values, id -> new Grupo(grupo.getId(), nuevoNombre, nuevaInvitacion), "Se esperaba modificar un único grupo pero se modificaron %d");
    }

    @Override
    public Categoria crearCategoria(String nombre, String descripcion) {
        Guardia.esCadenaNoVacia(nombre, "El nombre es inválido");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaNombre, nombre);
        values.put(ColumnaDescripcion, descripcion);
        return (Categoria)crearElemento(TablaCategoria, values, id -> new Categoria(id, nombre, descripcion), "Error creando categoría");
    }

    @Override
    public void borrarCategoria(Categoria categoria) {
        Guardia.esObjetoValido(categoria, "La categoría es nula");
        borrarElemento(TablaCategoria, categoria.getId(), "Se esperaba borrar una única categoría pero se borraron %d");
    }

    @Override
    public Categoria buscarCategoriaOExplotar(long id) {
        return (Categoria)buscarElementoOExplotar(TablaCategoria, CamposCategoria, id,
                Database::obtenerCategoriaDeCursor, "Se esperaba encontrar una única categoría con id %d, se encontraron %d");
    }

    @Override
    public Categoria buscarCategoriaONada(String nombre) {
        return (Categoria)buscarElementoONada(TablaCategoria, CamposCategoria, ColumnaNombre, nombre,
                Database::obtenerCategoriaDeCursor,"Se encontraron varias categorías con el mismo nombre %s");
    }

    @Override
    public Categoria modificarCategoria(Categoria categoria, String nuevoNombre, String nuevaDescripcion) {
        Guardia.esObjetoValido(categoria, "La categoría es nula");
        Guardia.esCadenaNoVacia(nuevoNombre, "El nombre es inválido");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaNombre, nuevoNombre);
        values.put(ColumnaDescripcion, nuevaDescripcion);

        return (Categoria)modificarElemento(TablaCategoria, categoria.getId(), values, id -> new Categoria(categoria.getId(), nuevoNombre, nuevaDescripcion), "Se esperaba modificar una única categoría pero se modificaron %d");
    }

    @Override
    public List<Categoria> listarCategorias() {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Categoria> resultado = new ArrayList<>();

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCategoria, CamposCategoria, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Categoria categoria = new Categoria(
                            cursorHelper.getLongFrom(ColumnaId),
                            cursorHelper.getStringFrom(ColumnaNombre),
                            cursorHelper.getStringFrom(ColumnaDescripcion));

                    resultado.add(categoria);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Leccion crearLeccion(String titulo, String contenido, int duracion, int orden, String vinculo, Curso curso) {
        Guardia.esObjetoValido(curso, "El curso es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, titulo);
        values.put(ColumnaContenido, contenido);
        values.put(ColumnaDuracion, duracion);
        values.put(ColumnaOrden, orden);
        values.put(ColumnaVinculo, vinculo);
        values.put(ColumnaRelacionCurso, curso.getId());
        return (Leccion)crearElemento(TablaLeccion, values, id -> new Leccion(id, titulo, contenido, duracion, orden, vinculo, curso), "Error creando lección");
    }

    @Override
    public Leccion modificarLeccion(Leccion leccion, String nuevoTitulo, String nuevoContenido, int nuevaDuracion, int nuevoOrden, String nuevoVinculo, Curso nuevoCurso) {
        Guardia.esObjetoValido(leccion, "La lección es nula");
        Guardia.esObjetoValido(nuevoCurso, "El curso es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, nuevoTitulo);
        values.put(ColumnaContenido, nuevoContenido);
        values.put(ColumnaDuracion, nuevaDuracion);
        values.put(ColumnaOrden, nuevoOrden);
        values.put(ColumnaVinculo, nuevoVinculo);
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());

        return (Leccion)modificarElemento(TablaLeccion, leccion.getId(), values, id -> new Leccion(id, nuevoTitulo, nuevoContenido, nuevaDuracion, nuevoOrden, nuevoVinculo, nuevoCurso), "Se esperaba modificar una única lección pero se modificaron %d");
    }

    @Override
    public List<Leccion> buscarLecciones(Curso curso) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaLeccion, CamposLeccion,
                    ColumnaRelacionCurso + "=?", new String[] { String.valueOf(curso.getId()) },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return new ArrayList<>();
            }

            List<Leccion> resultado = new ArrayList<>();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Leccion leccion = new Leccion(
                            cursorHelper.getLongFrom(ColumnaId),
                            cursorHelper.getStringFrom(ColumnaTitulo),
                            cursorHelper.getStringFrom(ColumnaContenido),
                            cursorHelper.getIntFrom(ColumnaDuracion),
                            cursorHelper.getIntFrom(ColumnaOrden),
                            cursorHelper.getStringFrom(ColumnaVinculo),
                            curso);

                    resultado.add(leccion);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Leccion buscarLeccionOExplotar(long id) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaLeccion + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCurricula, CamposCurricula),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso)),
                    ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + TablaLeccion + "." + ColumnaId + " =?",
                    new String[] { String.valueOf(id) }, null, null, null);

            if (cursor.getCount() == 1) {
                return obtenerLeccionDeCursor(cursor, TablaLeccion + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única lección con id %d pero se encontraron %d", id, cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<Leccion> listarLecciones() {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Leccion> resultado = new ArrayList<>();

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaLeccion + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaLeccion, CamposLeccion),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso)),
                    TablaLeccion + "." + ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId,
                    null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Leccion leccion = obtenerLeccionDeCursor(cursor, TablaLeccion + "_");
                    resultado.add(leccion);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public void borrarLeccion(Leccion leccion) {
        Guardia.esObjetoValido(leccion, "La lección es nula");
        borrarElemento(TablaLeccion, leccion.getId(), "Se esperaba borrar una única lección pero se borraron %d");
    }

    @Override
    public Inscripcion crearInscripcion(Usuario usuario, Curso curso, int puntuacion, boolean favorito, int ultimaLeccion) {
        Guardia.esObjetoValido(usuario, "El usuario es nulo");
        Guardia.esObjetoValido(curso, "El curso es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionUsuario, usuario.getId());
        values.put(ColumnaRelacionCurso, curso.getId());
        values.put(ColumnaPuntuacion, puntuacion);
        values.put(ColumnaFavorito, favorito);
        values.put(ColumnaUltimaLeccion, ultimaLeccion);

        return (Inscripcion)crearElemento(TablaInscripcion, values, id -> new Inscripcion(id, usuario, curso, puntuacion, favorito, ultimaLeccion), "Error creando inscripción");
    }

    @Override
    public void borrarInscripcion(Inscripcion inscripcion) {
        Guardia.esObjetoValido(inscripcion, "La inscripción es nula");
        borrarElemento(TablaInscripcion, inscripcion.getId(), "Se esperaba borrar una única inscripción pero se borraron %d");
    }

    @Override
    public Inscripcion modificarInscripcion(Inscripcion inscripcion, Usuario nuevoUsuario, Curso nuevoCurso, int nuevaPuntuacion, boolean nuevoFavorito, int nuevaUltimaLeccion) {
        Guardia.esObjetoValido(inscripcion, "La inscripción es nula");
        Guardia.esObjetoValido(nuevoUsuario, "El usuario es nulo");
        Guardia.esObjetoValido(nuevoCurso, "El curso es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionUsuario, nuevoUsuario.getId());
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaPuntuacion, nuevaPuntuacion);
        values.put(ColumnaFavorito, nuevoFavorito);
        values.put(ColumnaUltimaLeccion, nuevaUltimaLeccion);

        return (Inscripcion)modificarElemento(TablaInscripcion, inscripcion.getId(), values, id -> new Inscripcion(inscripcion.getId(), nuevoUsuario, nuevoCurso, nuevaPuntuacion, nuevoFavorito, nuevaUltimaLeccion), "Se esperaba modificar una única inscripción pero se modificaron %d");
    }

    @Override
    public List<Inscripcion> buscarInscripciones(Usuario usuario) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaInscripcion + ", " + TablaCurso,
                     concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaInscripcion, CamposInscripcion),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso)),
                   TablaInscripcion + "_" + ColumnaRelacionUsuario + "=? AND " + TablaInscripcion + "_" + ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId,
                    new String[] { String.valueOf(usuario.getId()) }, null, null, null);
            if (cursor.getCount() == 0) {
                return new ArrayList<>();
            }

            List<Inscripcion> resultado = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    resultado.add(obtenerInscripcionDeCursor(cursor, usuario));
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<Inscripcion> buscarInscripciones(Curso curso) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaInscripcion + ", " + TablaUsuario,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaInscripcion, CamposInscripcion),
                            agregarNombreDeTablaEnColumnas(TablaUsuario, CamposUsuario)),
                    TablaInscripcion + "_" + ColumnaRelacionCurso + "=? AND " + TablaInscripcion + "_" + ColumnaRelacionUsuario + " = " + TablaUsuario + "." + ColumnaId,
                    new String[] { String.valueOf(curso.getId()) }, null, null, null);
            if (cursor.getCount() == 0) {
                return new ArrayList<>();
            }

            List<Inscripcion> resultado = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    resultado.add(obtenerInscripcionDeCursor(cursor, curso));
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Inscripcion buscarInscripcionOExplotar(long id) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaInscripcion + ", " + TablaCurso + ", " + TablaUsuario,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaInscripcion, CamposInscripcion),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaUsuario, CamposUsuario)),
                    TablaInscripcion + "." + ColumnaId + "=? AND " + ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionUsuario + " = " + TablaUsuario + "." + ColumnaId,
                    new String[] { String.valueOf(id) }, null, null, null);

            if (cursor.getCount() == 1) {
                return obtenerInscripcionDeCursor(cursor, TablaInscripcion + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única inscripción con id %d, se encontraron %d", id, cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Inscripcion buscarInscripcionONada(Usuario usuario, Curso curso) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaInscripcion + ", " + TablaUsuario + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaInscripcion, CamposInscripcion),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaUsuario, CamposUsuario)),
                    ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionUsuario + " = " + TablaUsuario + "." + ColumnaId + " AND " +
                            ColumnaRelacionCurso + " =? AND " + ColumnaRelacionUsuario + " =?",
                    new String[] { String.valueOf(curso.getId()), String.valueOf(usuario.getId()) }, null, null, null);

            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return obtenerInscripcionDeCursor(cursor, TablaInscripcion + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única inscripción pero se encontraron %d", cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<Inscripcion> buscarInscripcionesFavoritas(Usuario usuario) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Inscripcion> resultado = new ArrayList<>();

        try {
            if (usuario == null) return resultado;

            database = getInternalReadableDatabase();
            cursor = database.query(TablaInscripcion + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaInscripcion, CamposInscripcion),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso)),
                    TablaInscripcion + "." + ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId + " AND " +
                            TablaInscripcion + "." + ColumnaRelacionUsuario + " =? AND " + TablaInscripcion + "." + ColumnaFavorito + " = 1",
                    new String[] { String.valueOf(usuario.getId()) }, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Inscripcion inscripcion = new Inscripcion(
                            cursorHelper.getLongFrom(TablaInscripcion + "_" + ColumnaId),
                            usuario,
                            obtenerCursoDeCursor(cursor, TablaCurso + "_"),
                            cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaPuntuacion),
                            cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaFavorito) != 0,
                            cursorHelper.getIntFrom(TablaInscripcion + "_" + ColumnaUltimaLeccion));

                    resultado.add(inscripcion);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public void borrarCategorizacion(Categorizacion categorizacion) {
        Guardia.esObjetoValido(categorizacion,"La categorización es nula");
        borrarElemento(TablaCategorizacion, categorizacion.getId(), "Se esperaba borrar una única categorización pero se borraron %d" );
    }

    @Override
    public Categorizacion crearCategorizacion(Curso nuevoCurso, Categoria nuevaCategoria) {
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionCategoria, nuevaCategoria.getId());
        return (Categorizacion) crearElemento(TablaCategorizacion, values, id -> new Categorizacion(id,nuevoCurso,nuevaCategoria), "Error crear nueva categorizaciones");
    }

    @Override
    public Categorizacion buscarCategorizacionONada(Curso curso, Categoria categoria) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCategorizacion + ", " + TablaCurso + ", " + TablaCategoria,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCategorizacion, CamposCategorizacion),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaCategoria, CamposCategoria)),
                    ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionCategoria + " = " + TablaCategoria + "." + ColumnaId + " AND " +
                            ColumnaRelacionCurso + " =? AND " + ColumnaRelacionCategoria + " =?",
                    new String[] { String.valueOf(curso.getId()), String.valueOf(categoria.getId()) }, null, null, null);

            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return obtenerCategorizacionDeCursor(cursor, TablaCategorizacion + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única categorización pero se encontraron %d", cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Curricula crearCurricula(Curso nuevoCurso, Grupo nuevoGrupo) {
        Guardia.esObjetoValido(nuevoCurso, "El curso es nulo");
        Guardia.esObjetoValido(nuevoGrupo, "El grupo es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionGrupo, nuevoGrupo.getId());

        return (Curricula)crearElemento(TablaCurricula, values, id -> new Curricula(id, nuevoCurso, nuevoGrupo), "Error creando currícula");
    }

    @Override
    public Categorizacion modificarCategorizacion(Categorizacion categorizacion, Curso nuevoCurso, Categoria nuevaCategoria) {
        Guardia.esObjetoValido(categorizacion, "La categorización es nula");
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionCategoria, nuevaCategoria.getId());
        return (Categorizacion) modificarElemento(TablaCategorizacion, categorizacion.getId(), values, id -> new Categorizacion(categorizacion.getId(), nuevoCurso, nuevaCategoria),"Se esperaba modificar una unica categorización pero se modificaron %d");
    }

    @Override
    public List<Categorizacion> buscarCategorizaciones(Curso curso) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Categorizacion> resultado = new ArrayList<>();

        try {
            if (curso == null) return resultado;

            database = getInternalReadableDatabase();
            cursor = database.query(TablaCategorizacion + ", " + TablaCategoria,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCategorizacion, CamposCategorizacion),
                            agregarNombreDeTablaEnColumnas(TablaCategoria, CamposCategoria)),
                    TablaCategorizacion + "." + ColumnaRelacionCategoria + " = " + TablaCategoria + "." + ColumnaId + " AND " +
                            TablaCategorizacion + "." + ColumnaRelacionCurso + " =?",
                    new String[] { String.valueOf(curso.getId()) }, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Categorizacion categorizacion = new Categorizacion(
                            cursorHelper.getLongFrom(TablaCategorizacion + "_" + ColumnaId),
                            curso,
                            obtenerCategoriaDeCursor(cursor, TablaCategoria + "_"));

                    resultado.add(categorizacion);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Curso crearCurso(String title, String description, String imagen, int nivel) {

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, title);
        values.put(ColumnaDescripcion, description);
        values.put(ColumnaImagen, imagen);
        values.put(ColumnaNivel, nivel);

        return (Curso)crearElemento(TablaCurso, values, id -> new Curso(id, title, description, imagen, nivel), "Error creando curso");
    }

    @Override
    public Curso modificarCurso(Curso curso, String title, String description, String imagen, int nivel) {
        Guardia.esObjetoValido(curso, "El curso es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, title);
        values.put(ColumnaDescripcion, description);
        values.put(ColumnaImagen, imagen);
        values.put(ColumnaNivel, nivel);

        return (Curso)modificarElemento(TablaCurso, curso.getId(), values, id -> new Curso(curso.getId(), title, description, imagen, nivel), "Se esperaba modificar un único curso pero se modificaron %d");
    }

    @Override
    public void borrarCurso(Curso curso) {
        Guardia.esObjetoValido(curso, "El curso es nulo");
        borrarElemento(TablaCurso, curso.getId(), "Se esperaba borrar un único curso pero se borraron %d");
    }

    @Override
    public List<Curso> listarCursos() {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Curso> resultado = new ArrayList<>();

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCurso, CamposCurso, null,
                    null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Curso curso = new Curso(
                            cursorHelper.getLongFrom(ColumnaId),
                            cursorHelper.getStringFrom(ColumnaTitulo),
                            cursorHelper.getStringFrom(ColumnaDescripcion),
                            cursorHelper.getStringFrom(ColumnaImagen),
                            cursorHelper.getIntFrom(ColumnaNivel));

                    resultado.add(curso);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Curso buscarCursoONada(String titulo) {
        return (Curso)buscarElementoONada(TablaCurso, CamposCurso, ColumnaTitulo, titulo,
                Database::obtenerCursoDeCursor,"Se encontraron varios cursos con el mismo título %s");
    }

    @Override
    public Curso buscarCursoOExplotar(long id) {
        return (Curso)buscarElementoOExplotar(TablaCurso, CamposCurso, id,
                Database::obtenerCursoDeCursor, "Se esperaba encontrar un único curso con id %d, se encontraron %d");
    }

    @Override
    public List<Curso> buscarCursos(Usuario usuario) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Curso> resultado = new ArrayList<>();

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCurso + ", " + TablaCurricula + ", " + TablaGrupo + ", " + TablaUsuario + ", " + TablaDivision,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaCurricula, CamposCurricula),
                            agregarNombreDeTablaEnColumnas(TablaGrupo, CamposGrupo),
                            agregarNombreDeTablaEnColumnas(TablaDivision, CamposDivision),
                            agregarNombreDeTablaEnColumnas(TablaUsuario, CamposUsuario)),
                   TablaCurso + "." + ColumnaId + " = " + TablaCurricula + "." + ColumnaRelacionCurso + " AND " +
                            TablaGrupo + "." + ColumnaId + " = " + TablaCurricula + "." + ColumnaRelacionGrupo + " AND " +
                            TablaGrupo + "." + ColumnaId + " = " + TablaDivision + "." + ColumnaRelacionGrupo + " AND " +
                            TablaUsuario + "." + ColumnaId + " = " + TablaDivision + "." + ColumnaRelacionUsuario + " AND " +
                            TablaUsuario + "." + ColumnaId + " =? ",
                            new String[] { String.valueOf(usuario.getId()) }, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CursorHelper cursorHelper = new CursorHelper(cursor);
                    Curso curso = obtenerCursoDeCursor(cursor, TablaCurso + "_");
                    resultado.add(curso);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Curricula modificarCurricula(Curricula curricula, Curso nuevoCurso, Grupo nuevoGrupo) {
        Guardia.esObjetoValido(curricula, "La currícula es nula");
        Guardia.esObjetoValido(nuevoCurso, "El curso es nulo");
        Guardia.esObjetoValido(nuevoGrupo, "El grupo es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionGrupo, nuevoGrupo.getId());

        return (Curricula)modificarElemento(TablaCurricula, curricula.getId(), values, id -> new Curricula(curricula.getId(), nuevoCurso, nuevoGrupo),"Se esperaba modificar una única currícula pero se modificaron %d");
    }

    @Override
    public void borrarCurricula(Curricula curricula) {
        Guardia.esObjetoValido(curricula, "La currícula es nula");
        borrarElemento(TablaCurricula, curricula.getId(), "Se esperaba borrar una única currícula pero se borraron %d");
    }

    @Override
    public List<Curricula> listarCurriculas() {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;
        List<Curricula> resultado = new ArrayList<>();

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCurricula + ", " + TablaGrupo + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCurricula, CamposCurricula),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaGrupo, CamposGrupo)),
                    ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionGrupo + " = " + TablaGrupo + "." + ColumnaId,
                    null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Curricula curricula = obtenerCurriculaDeCursor(cursor, TablaCurricula + "_");
                    resultado.add(curricula);
                    cursor.moveToNext();
                }
            }

            return resultado;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Curricula buscarCurriculaONada(Curso curso, Grupo grupo) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCurricula + ", " + TablaGrupo + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCurricula, CamposCurricula),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaGrupo, CamposGrupo)),
                    ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionGrupo + " = " + TablaGrupo + "." + ColumnaId + " AND " +
                            ColumnaRelacionCurso + " =? AND " + ColumnaRelacionGrupo + " =?",
                    new String[] { String.valueOf(curso.getId()), String.valueOf(grupo.getId()) }, null, null, null);

            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return obtenerCurriculaDeCursor(cursor, TablaCurricula + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única currícula pero se encontraron %d", cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Curricula buscarCurriculaOExplotar(long id) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaCurricula + ", " + TablaGrupo + ", " + TablaCurso,
                    concatenarVectores(
                            agregarNombreDeTablaEnColumnas(TablaCurricula, CamposCurricula),
                            agregarNombreDeTablaEnColumnas(TablaCurso, CamposCurso),
                            agregarNombreDeTablaEnColumnas(TablaGrupo, CamposGrupo)),
                    ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionGrupo + " = " + TablaGrupo + "." + ColumnaId + " AND " +
                            ColumnaId + " =?",
                    new String[] { String.valueOf(id) }, null, null, null);

            if (cursor.getCount() == 1) {
                return obtenerCurriculaDeCursor(cursor, TablaCurricula + "_");
            }

            throw new RuntimeException(String.format("Se esperaba encontrar una única currícula con id %d pero se encontraron %d", id, cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    private String[] agregarNombreDeTablaEnColumnas(String tabla, String[] campos) {
        return Arrays.stream(campos).map(s -> tabla + "." + s + " AS " + tabla + "_" + s).toArray(String[]::new);
    }

    private String[] concatenarVectores(String[]... vectores) {
        String[] resultado = new String[] { };
        for (String[] v : vectores) {
            resultado = Stream.concat(Arrays.stream(resultado), Arrays.stream(v)).toArray(String[]::new);
        }

        return resultado;
    }

    protected ISQLiteDatabaseWrapper getInternalReadableDatabase() {
        return new SQLiteDatabaseWrapper(this.getReadableDatabase());
    }

    protected ISQLiteDatabaseWrapper getInternalWritableDatabase() {
        return new SQLiteDatabaseWrapper(this.getWritableDatabase());
    }

    protected IContentValuesWrapper createContentValues() {
        return new ContentValuesWrapper();
    }

    private void borrarElemento(String tabla, long id, String mensajeError) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            int affected = database.delete(tabla, ColumnaId + "=?", new String[] { Long.toString(id) });
            if (affected != 1) {
                throw new RuntimeException(String.format(mensajeError, affected));
            }
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    private Object crearElemento(String tabla, IContentValuesWrapper valores, Function<Long, Object> creador, String mensajeError) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            database.beginTransaction();
            long id = database.insert(tabla, null, valores);

            if (id != -1) {
                Object object = creador.apply(id);
                database.setTransactionSuccessful();
                return object;
            }

            throw new RuntimeException(mensajeError);
        }
        finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    private Object modificarElemento(String tabla, long id, IContentValuesWrapper values, Function<Long, Object> creador, String mensajeError) {
        ISQLiteDatabaseWrapper database = null;

        try {
            Object nuevoElemento = creador.apply(id);
            database = getInternalWritableDatabase();
            int affected = database.update(tabla, values, ColumnaId + "=?",
                    new String[] { Long.toString(id) });
            if (affected == 1) {
                return nuevoElemento;
            }

            throw new RuntimeException(String.format(mensajeError, affected));
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    private Object buscarElementoONada(String tabla, String[] campos, String columna, String valor, Function<ICursorWrapper, Object> creator, String mensajeError) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(tabla, campos,
                    columna + "=?", new String[] { valor },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return creator.apply(cursor);
            }

            throw new RuntimeException(String.format(mensajeError, valor));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }

    private Object buscarElementoOExplotar(String tabla, String[] campos, long id, Function<ICursorWrapper, Object> creador, String mensajeError) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(tabla, campos,
                    ColumnaId + "=?", new String[] { Long.toString(id) },
                    null, null, null);
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return creador.apply(cursor);
            }

            throw new RuntimeException(String.format(mensajeError, id, cursor.getCount()));
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
    }
}