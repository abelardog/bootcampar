package com.ar.bootcampar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

public class Database extends SQLiteOpenHelper implements IDatabase {
    private static final String LOGCAT = "Database";
    private static final String[] UsuarioFields = new String[] { "Id", "Nombre", "Apellido", "Email", "Clave", "Rol", "Telefono" };
    private static final String[] GrupoFields = new String[] { "Id", "Nombre", "Invitacion" };
    private static final String UsuarioTable = "Usuarios";
    private static final String DivisionTable = "Divisiones";
    private static final String GrupoTable = "Grupos";
    private static final String CursoTable = "Cursos";
    private static final String InscripcionTable = "Inscripciones";
    private static final String CurriculaTable = "Curriculas";
    private static final String LeccionTable = "Lecciones";
    private static final String CategoriaTable = "Categorias";
    private static final String CategorizacionTable = "Categorizaciones";

    public static IDatabase CreateWith(Context applicationContext) {
        return new Database(applicationContext, "bootcampar.db", null, 1);
    }

    private Database(Context applicationContext, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(applicationContext, name, factory, version);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("PRAGMA foreign_key_check");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + UsuarioTable + "(\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  Nombre TEXT NOT NULL,\n" +
                "  Apellido TEXT NOT NULL,\n" +
                "  Email TEXT NOT NULL,\n" +
                "  Clave TEXT NOT NULL,\n" +
                "  Rol INTEGER,\n" +
                "  Telefono TEXT\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CursoTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  Titulo TEXT NOT NULL,\n" +
                "  Descripcion TEXT,\n" +
                "  Nivel INTEGER NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + InscripcionTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  UsuarioId INTEGER NOT NULL,\n" +
                "  CursoId INTEGER NOT NULL,\n" +
                "  Puntuacion INTEGER,\n" +
                "  Favorito INTEGER,\n" +
                "  FOREIGN KEY (UsuarioId) REFERENCES " + UsuarioTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (CursoId) REFERENCES " + CursoTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CategoriaTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  Nombre TEXT NOT NULL,\n" +
                "  Descripcion TEXT\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CategorizacionTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  CursoId INTEGER NOT NULL,\n" +
                "  CategoriaId INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (CursoId) REFERENCES " + CursoTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (CategoriaId) REFERENCES " + CategoriaTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LeccionTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  Titulo TEXT NOT NULL,\n" +
                "  Contenido TEXT,\n" +
                "  Duracion INTEGER NOT NULL,\n" +
                "  Orden INTEGER NOT NULL,\n" +
                "  CursoId INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (CursoId) REFERENCES " + CursoTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GrupoTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  Nombre TEXT NOT NULL,\n" +
                "  Invitacion TEXT NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CurriculaTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  CursoId INTEGER NOT NULL,\n" +
                "  GrupoId INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (GrupoId) REFERENCES " + GrupoTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (CursoId) REFERENCES " + CursoTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION\n" +
                ");\n");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DivisionTable + " (\n" +
                "  Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  UsuarioId INTEGER NOT NULL,\n" +
                "  GrupoId INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (GrupoId) REFERENCES " + GrupoTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (UsuarioId) REFERENCES " + UsuarioTable + " (Id) ON DELETE CASCADE ON UPDATE NO ACTION\n" +
                ");\n");
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("PRAGMA foreign_key_check");
        db.execSQL("INSERT INTO " + GrupoTable + "(Nombre, Invitacion) VALUES ('Grupo de Programadores', '112233')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DivisionTable);
        db.execSQL("DROP TABLE IF EXISTS " + CurriculaTable);
        db.execSQL("DROP TABLE IF EXISTS " + GrupoTable);
        db.execSQL("DROP TABLE IF EXISTS " + LeccionTable);
        db.execSQL("DROP TABLE IF EXISTS " + CategorizacionTable);
        db.execSQL("DROP TABLE IF EXISTS " + CategoriaTable);
        db.execSQL("DROP TABLE IF EXISTS " + InscripcionTable);
        db.execSQL("DROP TABLE IF EXISTS " + CursoTable);
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioTable);
        onCreate(db);
    }

    public Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono) {
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Nombre", nombre);
            values.put("Apellido", apellido);
            values.put("Email", email);
            values.put("Clave", clave);
            values.put("Rol", Rol.asInt(rol));
            values.put("Telefono", telefono);
            database.beginTransaction();
            long id = database.insert(UsuarioTable, null, values);

            if (id != -1) {
                Usuario usuario = new Usuario(id, nombre, apellido, email, clave, rol, telefono);
                database.setTransactionSuccessful();
                return usuario;
            }

            throw new RuntimeException("Error creando usuario");
        }
        finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    public Usuario buscarUsuarioOExplotar(long id) {
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = this.getReadableDatabase();
            cursor = database.query(UsuarioTable, UsuarioFields,
                    "Id=?", new String[] { Long.toString(id) },
                    null, null, null);
            if (cursor.getCount() == 1) {
                return obtenerUsuarioDeCursor(cursor);
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

    @NonNull
    private static Usuario obtenerUsuarioDeCursor(Cursor cursor) {
        cursor.moveToFirst();
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Usuario(
                cursorHelper.getLongFrom("Id"),
                cursorHelper.getStringFrom("Nombre"),
                cursorHelper.getStringFrom("Apellido"),
                cursorHelper.getStringFrom("Email"),
                cursorHelper.getStringFrom("Clave"),
                Rol.fromInt(cursorHelper.getIntFrom("Rol")),
                cursorHelper.getStringFrom("Telefono"));
    }

    public Usuario buscarUsuarioONada(String email) {
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = this.getReadableDatabase();
            cursor = database.query(UsuarioTable, UsuarioFields,
                    "Email=?", new String[] { email },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                return obtenerUsuarioDeCursor(cursor);
            }

            throw new RuntimeException(String.format("Se encontraron varios usuarios con el mismo email %s", email));
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
            values.put("Nombre", nuevoNombre);
            values.put("Apellido", nuevoApellido);
            values.put("Email", nuevoEmail);
            values.put("Clave", nuevaClave);
            values.put("Rol", Rol.asInt(nuevoRol));
            values.put("Telefono", nuevoTelefono);
            int affected = database.update(UsuarioTable, values, "Id = ?",
                    new String[] { Long.toString(usuario.getId()) });
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
            int affected = database.delete(UsuarioTable, "Id = ?",
                    new String[] { Long.toString(usuario.getId()) });
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

    public Division crearDivision(Usuario usuario, Grupo grupo) {
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("UsuarioId", usuario.getId());
            values.put("GrupoId", grupo.getId());
            database.beginTransaction();
            long id = database.insert(DivisionTable, null, values);
            if (id != -1) {
                database.setTransactionSuccessful();
                return new Division(id, usuario, grupo);
            }

            throw new RuntimeException("Error creando usuario");
        }
        finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    public Grupo buscarGrupoONada(String invitacion) {
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = this.getReadableDatabase();
            cursor = database.query(GrupoTable, GrupoFields,
                    "invitacion = ?", new String[] { invitacion },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                CursorHelper cursorHelper = new CursorHelper(cursor);
                Grupo grupo = new Grupo(
                        cursorHelper.getLongFrom("Id"),
                        cursorHelper.getStringFrom("Nombre"),
                        cursorHelper.getStringFrom("Invitacion"));

                return grupo;
            }

            throw new RuntimeException(String.format("Se encontraron varios grupos con la misma invitación %s", invitacion));
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