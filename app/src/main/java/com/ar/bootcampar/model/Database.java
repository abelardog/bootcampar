package com.ar.bootcampar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper implements IDatabase {
    private static final String LOGCAT = "Database";
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

    public Database(Context applicationContext, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(applicationContext, name, factory, version);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
    }

    public Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono) {
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("email", email);
            values.put("clave", clave);
            values.put("rol", Rol.asInt(rol));
            values.put("telefono", telefono);
            long id = database.insert("Usuarios", null, values);

            if (id != -1) {
                return new Usuario(id, nombre, apellido, email, clave, rol, telefono);
            }

            throw new RuntimeException("Error creando usuario");
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public Usuario buscarUsuario(long id) {
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = this.getReadableDatabase();
            String[] param = new String[1];
            param[0] = Long.toString(id);

            String selectQuery = "SELECT id, nombre, apellido, email, clave, rol, telefono FROM Usuarios where Id=?";
            cursor = database.rawQuery(selectQuery, param);
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                CursorHelper cursorHelper = new CursorHelper(cursor);
                Usuario usuario = new Usuario(
                        cursorHelper.getLongFrom("id"),
                        cursorHelper.getStringFrom("nombre"),
                        cursorHelper.getStringFrom("apellido"),
                        cursorHelper.getStringFrom("email"),
                        cursorHelper.getStringFrom("clave"),
                        Rol.fromInt(cursorHelper.getIntFrom("rol")),
                        cursorHelper.getStringFrom("telefono"));
                return usuario;
            }

            throw new RuntimeException(String.format("Se encontraron varios usuarios con el mismo id %d", id));
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

    public Usuario modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoEmail, String nuevaClave, Rol nuevoRol, String nuevoTelefono) {
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nuevoNombre);
            values.put("apellido", nuevoApellido);
            values.put("email", nuevoEmail);
            values.put("clave", nuevaClave);
            values.put("rol", Rol.asInt(nuevoRol));
            values.put("telefono", nuevoTelefono);
            int affected = database.update("Usuarios", values, "id = ?", new String[] { Long.toString(usuario.getId()) });
            if (affected == 1) {
                return new Usuario(usuario.getId(), nuevoNombre, nuevoApellido, nuevoEmail, nuevaClave, nuevoRol, nuevoTelefono);
            }

            throw new RuntimeException(String.format("Se esperaba modificar un único usuario pero se modificaron %d", affected));
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public void borrarUsuario(Usuario usuario) {
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            int affected = database.delete("Usuarios", "id = ?", new String[] { Long.toString(usuario.getId()) });
            if (affected != 1) {
                throw new RuntimeException(String.format("Se esperaba borrar un único usuario pero se borraron %d", affected));
            }
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }
}