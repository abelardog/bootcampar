package com.ar.bootcampar.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    private static final String LOGCAT = null;
    private static final String INITIAL_SQL = "-- SQLite Script\n" +
            "-- Generated from MySQL Script\n" +
            "\n" +
            "-- PRAGMA statements to set foreign keys and enforce integrity\n" +
            "PRAGMA foreign_keys=ON;\n" +
            "PRAGMA foreign_key_check;\n" +
            "\n" +
            "-- Create the database if it doesn't exist (SQLite does not have CREATE SCHEMA)\n" +
            "ATTACH DATABASE 'bootcampar.db' AS bootcampar;\n" +
            "\n" +
            "-- Table `Usuarios`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Usuarios (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  Nombre TEXT NOT NULL,\n" +
            "  Apellido TEXT NOT NULL,\n" +
            "  Email TEXT NOT NULL,\n" +
            "  Clave TEXT NOT NULL,\n" +
            "  Rol INTEGER,\n" +
            "  Telefono TEXT\n" +
            ");\n" +
            "\n" +
            "-- Table `Cursos`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Cursos (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  Titulo TEXT NOT NULL,\n" +
            "  Descripcion TEXT,\n" +
            "  Nivel INTEGER NOT NULL\n" +
            ");\n" +
            "\n" +
            "-- Table `Inscripciones`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Inscripciones (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  UsuarioId INTEGER NOT NULL,\n" +
            "  CursoId INTEGER NOT NULL,\n" +
            "  Puntuacion INTEGER,\n" +
            "  Favorito INTEGER,\n" +
            "  FOREIGN KEY (UsuarioId) REFERENCES Usuarios (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ");\n" +
            "\n" +
            "-- Table `Categorias`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Categorias (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  Nombre TEXT NOT NULL,\n" +
            "  Descripcion TEXT\n" +
            ");\n" +
            "\n" +
            "-- Table `Categorizaciones`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Categorizaciones (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  CursoId INTEGER NOT NULL,\n" +
            "  CategoriaId INTEGER NOT NULL,\n" +
            "  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  FOREIGN KEY (CategoriaId) REFERENCES Categorias (Id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ");\n" +
            "\n" +
            "-- Table `Lecciones`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Lecciones (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  Titulo TEXT NOT NULL,\n" +
            "  Contenido TEXT,\n" +
            "  Duracion INTEGER NOT NULL,\n" +
            "  Orden INTEGER NOT NULL,\n" +
            "  CursoId INTEGER NOT NULL,\n" +
            "  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ");\n" +
            "\n" +
            "-- Table `Grupos`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Grupos (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  Nombre TEXT NOT NULL,\n" +
            "  Invitacion TEXT NOT NULL\n" +
            ");\n" +
            "\n" +
            "-- Table `Curriculas`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Curriculas (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  CursoId INTEGER NOT NULL,\n" +
            "  GrupoId INTEGER NOT NULL,\n" +
            "  FOREIGN KEY (GrupoId) REFERENCES Grupos (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ");\n" +
            "\n" +
            "-- Table `Divisiones`\n" +
            "CREATE TABLE IF NOT EXISTS bootcampar.Divisiones (\n" +
            "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  UsuarioId INTEGER NOT NULL,\n" +
            "  GrupoId INTEGER NOT NULL,\n" +
            "  FOREIGN KEY (GrupoId) REFERENCES Grupos (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  FOREIGN KEY (UsuarioId) REFERENCES Usuarios (Id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ");\n" +
            "\n" +
            "-- PRAGMA statements to set foreign keys and enforce integrity\n" +
            "PRAGMA foreign_keys=ON;\n" +
            "PRAGMA foreign_key_check;";

    private String sqlCreate;

    public Database(Context applicationContext) {
        super(applicationContext, "bootcampar.db", null, 1);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ");
        db.execSQL(sqlCreate);
    }
}
