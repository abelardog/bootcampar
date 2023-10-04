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
    private static final String ColumnaId = "Id";
    private static final String ColumnaNombre = "Nombre";
    private static final String ColumnaApellido = "Apellido";
    private static final String ColumnaEmail = "Email";
    private static final String ColumnaClave = "Clave";
    private static final String ColumnaRol = "Rol";
    private static final String ColumnaTelefono = "Telefono";
    private static final String[] UsuarioFields = new String[] {ColumnaId, ColumnaNombre, ColumnaApellido, ColumnaEmail, ColumnaClave, ColumnaRol, ColumnaTelefono};
    private static final String UsuarioTable = "Usuarios";
    private static final String ColumnaInvitacion = "Invitacion";
    private static final String[] GrupoFields = new String[] { ColumnaId, ColumnaNombre, ColumnaInvitacion };
    private static final String GrupoTable = "Grupos";
    private static final String ColumnaRelacionCurso = "CursoId";
    private static final String ColumnaRelacionGrupo = "GrupoId";
    private static final String DivisionTable = "Divisiones";
    private static final String ColumnaTitulo = "Titulo";
    private static final String ColumnaDescripcion = "Descripcion";
    private static final String ColumnaNivel = "Nivel";
    private static final String CursoTable = "Cursos";
    private static final String ColumnaRelacionUsuario = "UsuarioId";
    private static final String ColumnaPuntuacion = "Puntuacion";
    private static final String ColumnaFavorito = "Favorito";
    private static final String InscripcionTable = "Inscripciones";
    private static final String CurriculaTable = "Curriculas";
    private static final String ColumnaContenido = "Contenido";
    private static final String ColumnaDuracion = "Duracion";
    private static final String ColumnaOrden = "Orden";
    private static final String LeccionTable = "Lecciones";
    private static final String CategoriaTable = "Categorias";
    private static final String ColumnaRelacionCategoria  = "CategoriaId";
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
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaNombre + " TEXT NOT NULL,\n" +
                ColumnaApellido + " TEXT NOT NULL,\n" +
                ColumnaEmail + " TEXT NOT NULL,\n" +
                ColumnaClave + " TEXT NOT NULL,\n" +
                ColumnaRol + " INTEGER,\n" +
                ColumnaTelefono + " TEXT\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CursoTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaTitulo + " TEXT NOT NULL,\n" +
                ColumnaDescripcion + " TEXT,\n" +
                ColumnaNivel + " INTEGER NOT NULL\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + InscripcionTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionUsuario + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaPuntuacion + " INTEGER,\n" +
                ColumnaFavorito + " INTEGER,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionUsuario + ") REFERENCES " + UsuarioTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionCurso + ") REFERENCES " + CursoTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CategoriaTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaNombre + " TEXT NOT NULL,\n" +
                ColumnaDescripcion + " TEXT\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CategorizacionTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCategoria + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY ("+ ColumnaRelacionCurso + ") REFERENCES " + CursoTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY ("+ ColumnaRelacionCategoria + ") REFERENCES " + CategoriaTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LeccionTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaTitulo + " TEXT NOT NULL,\n" +
                ColumnaContenido + " TEXT,\n" +
                ColumnaDuracion + " INTEGER NOT NULL,\n" +
                ColumnaOrden + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY ("+ ColumnaRelacionCurso + ") REFERENCES " + CursoTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GrupoTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaNombre + " TEXT NOT NULL,\n" +
                ColumnaInvitacion + " TEXT NOT NULL\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CurriculaTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaRelacionGrupo + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (GrupoId) REFERENCES " + GrupoTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (CursoId) REFERENCES " + CursoTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DivisionTable + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionUsuario + " INTEGER NOT NULL,\n" +
                ColumnaRelacionGrupo + " INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionGrupo + ") REFERENCES " + GrupoTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (" + ColumnaRelacionUsuario + ") REFERENCES " + UsuarioTable + " (" + ColumnaId + ") ON DELETE CASCADE ON UPDATE NO ACTION\n);");
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
            values.put(ColumnaNombre, nombre);
            values.put(ColumnaApellido, apellido);
            values.put(ColumnaEmail, email);
            values.put(ColumnaClave, clave);
            values.put(ColumnaRol, Rol.asInt(rol));
            values.put(ColumnaTelefono, telefono);
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
                    ColumnaId + "=?", new String[] { Long.toString(id) },
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
                cursorHelper.getLongFrom(ColumnaId),
                cursorHelper.getStringFrom(ColumnaNombre),
                cursorHelper.getStringFrom(ColumnaApellido),
                cursorHelper.getStringFrom(ColumnaEmail),
                cursorHelper.getStringFrom(ColumnaClave),
                Rol.fromInt(cursorHelper.getIntFrom(ColumnaRol)),
                cursorHelper.getStringFrom(ColumnaTelefono));
    }

    public Usuario buscarUsuarioONada(String email) {
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = this.getReadableDatabase();
            cursor = database.query(UsuarioTable, UsuarioFields,
                    ColumnaEmail + "=?", new String[] { email },
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
            values.put(ColumnaNombre, nuevoNombre);
            values.put(ColumnaApellido, nuevoApellido);
            values.put(ColumnaEmail, nuevoEmail);
            values.put(ColumnaClave, nuevaClave);
            values.put(ColumnaRol, Rol.asInt(nuevoRol));
            values.put(ColumnaTelefono, nuevoTelefono);
            int affected = database.update(UsuarioTable, values, ColumnaId + "=?",
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
            int affected = database.delete(UsuarioTable, ColumnaId + "=?",
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
            values.put(ColumnaRelacionUsuario, usuario.getId());
            values.put(ColumnaRelacionGrupo, grupo.getId());
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
                    ColumnaInvitacion + "=?", new String[] { invitacion },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                CursorHelper cursorHelper = new CursorHelper(cursor);
                Grupo grupo = new Grupo(
                        cursorHelper.getLongFrom(ColumnaId),
                        cursorHelper.getStringFrom(ColumnaNombre),
                        cursorHelper.getStringFrom(ColumnaInvitacion));

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