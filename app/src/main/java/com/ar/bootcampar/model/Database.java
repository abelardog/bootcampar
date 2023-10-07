package com.ar.bootcampar.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.ar.bootcampar.model.utilities.Guardia;

import java.util.ArrayList;
import java.util.List;

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
    private static final String TablaDivision = "Divisiones";
    private static final String ColumnaTitulo = "Titulo";
    private static final String ColumnaDescripcion = "Descripcion";
    private static final String ColumnaNivel = "Nivel";
    private static final String TablaCurso = "Cursos";
    private static final String ColumnaRelacionUsuario = "UsuarioId";
    private static final String ColumnaPuntuacion = "Puntuacion";
    private static final String ColumnaFavorito = "Favorito";
    private static final String TablaInscripcion = "Inscripciones";
    private static final String TablaCurricula = "Curriculas";
    private static final String ColumnaContenido = "Contenido";
    private static final String ColumnaDuracion = "Duracion";
    private static final String ColumnaOrden = "Orden";
    private static final String[] CamposLeccion = new String[] { ColumnaId, ColumnaTitulo, ColumnaContenido, ColumnaDuracion, ColumnaOrden };
    private static final String TablaLeccion = "Lecciones";
    private static final String TablaCategoria = "Categorias";
    private static final String ColumnaRelacionCategoria  = "CategoriaId";
    private static final String TablaCategorizacion = "Categorizaciones";

    public static IDatabase CreateWith(Context applicationContext) {
        return new Database(applicationContext, "bootcampar.db", null, 1);
    }

    protected Database(Context applicationContext, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(applicationContext, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {
        ISQLiteDatabaseWrapper db = new SQLiteDatabaseWrapper(sqliteDatabase);
        createDatabase(db);
    }

    private static void createDatabase(ISQLiteDatabaseWrapper db) {
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
                ColumnaNivel + " INTEGER NOT NULL\n);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TablaInscripcion + " (\n" +
                ColumnaId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                ColumnaRelacionUsuario + " INTEGER NOT NULL,\n" +
                ColumnaRelacionCurso + " INTEGER NOT NULL,\n" +
                ColumnaPuntuacion + " INTEGER,\n" +
                ColumnaFavorito + " INTEGER,\n" +
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
        db.execSQL("INSERT INTO " + TablaGrupo + "(Nombre, Invitacion) VALUES ('Grupo de Programadores', '112233')");
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

    public Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaNombre, nombre);
            values.put(ColumnaApellido, apellido);
            values.put(ColumnaEmail, email);
            values.put(ColumnaClave, clave);
            values.put(ColumnaRol, Rol.asInt(rol));
            values.put(ColumnaTelefono, telefono);
            database.beginTransaction();
            long id = database.insert(TablaUsuario, null, values);

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
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaUsuario, CamposUsuario,
                    ColumnaId + "=?", new String[] { Long.toString(id) },
                    null, null, null);
            if (cursor.getCount() == 1) {
                return obtenerUsuarioDeCursor(cursor);
            }

            throw new RuntimeException(String.format("Se esperaba encontrar un único usuario con id %d, se encontraron %d", id, cursor.getCount()));
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
    private static Usuario obtenerUsuarioDeCursor(ICursorWrapper cursor) {
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
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaUsuario, CamposUsuario,
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
        Guardia.esObjetoValido(usuario, "El usuario es nulo");
        ISQLiteDatabaseWrapper database = null;

        try {
            Usuario nuevoUsuario = new Usuario(usuario.getId(), nuevoNombre, nuevoApellido, nuevoEmail, nuevaClave, nuevoRol, nuevoTelefono);
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaNombre, nuevoNombre);
            values.put(ColumnaApellido, nuevoApellido);
            values.put(ColumnaEmail, nuevoEmail);
            values.put(ColumnaClave, nuevaClave);
            values.put(ColumnaRol, Rol.asInt(nuevoRol));
            values.put(ColumnaTelefono, nuevoTelefono);
            int affected = database.update(TablaUsuario, values, ColumnaId + "=?",
                    new String[] { Long.toString(usuario.getId()) });
            if (affected == 1) {
                return nuevoUsuario;
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
        Guardia.esObjetoValido(usuario, "El usuario es nulo");

        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            int affected = database.delete(TablaUsuario, ColumnaId + "=?",
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
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaRelacionUsuario, usuario.getId());
            values.put(ColumnaRelacionGrupo, grupo.getId());
            database.beginTransaction();
            long id = database.insert(TablaDivision, null, values);
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

    public Grupo crearGrupo(String nombre, String invitacion) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaNombre, nombre);
            values.put(ColumnaInvitacion, invitacion);
            database.beginTransaction();
            long id = database.insert(TablaGrupo, null, values);
            if (id != -1) {
                database.setTransactionSuccessful();
                return new Grupo(id, nombre, invitacion);
            }

            throw new RuntimeException("Error creando grupo");
        }
        finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    public Grupo buscarGrupoONada(String invitacion) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaGrupo, CamposGrupo,
                    ColumnaInvitacion + "=?", new String[] { invitacion },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                return obtenerGrupoDeCursor(cursor);
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

    @NonNull
    private static Grupo obtenerGrupoDeCursor(ICursorWrapper cursor) {
        cursor.moveToFirst();
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Grupo(
                cursorHelper.getLongFrom(ColumnaId),
                cursorHelper.getStringFrom(ColumnaNombre),
                cursorHelper.getStringFrom(ColumnaInvitacion));
    }

    public Grupo buscarGrupoOExplotar(long id) {
        ISQLiteDatabaseWrapper database = null;
        ICursorWrapper cursor = null;

        try {
            database = getInternalReadableDatabase();
            cursor = database.query(TablaGrupo, CamposGrupo,
                    ColumnaId + "=?", new String[] { String.valueOf(id) },
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            else if (cursor.getCount() == 1) {
                return obtenerGrupoDeCursor(cursor);
            }

            throw new RuntimeException(String.format("Se esperaba encontrar un grupo con id %d pero se encontraron %d", id, cursor.getCount()));
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

    public void borrarGrupo(Grupo grupo) {
        Guardia.esObjetoValido(grupo, "El grupo es nulo");

        ISQLiteDatabaseWrapper database = null;
        try {
            database = getInternalWritableDatabase();
            int affected = database.delete(TablaGrupo, ColumnaId + "=?",
                    new String[] { Long.toString(grupo.getId()) });
            if (affected != 1) {
                throw new RuntimeException(String.format("Se esperaba borrar un único grupo pero se borraron %d", affected));
            }
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public Grupo modificarGrupo(Grupo grupo, String nuevoNombre, String nuevaInvitacion) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaNombre, nuevoNombre);
            values.put(ColumnaInvitacion, nuevaInvitacion);
            int affected = database.update(TablaGrupo, values, ColumnaId + "=?",
                    new String[] { Long.toString(grupo.getId()) });
            if (affected == 1) {
                return new Grupo(grupo.getId(), nuevoNombre, nuevaInvitacion);
            }

            throw new RuntimeException(String.format("Se esperaba modificar un único grupo pero se modificaron %d", affected));
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Categoria crearCategoria(String nombre, String descripcion) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaNombre, nombre);
            values.put(ColumnaDescripcion, descripcion);
            database.beginTransaction();
            long id = database.insert(TablaCategoria, null, values);
            if (id != -1) {
                database.setTransactionSuccessful();
                return new Categoria(id, nombre, descripcion);
            }

            throw new RuntimeException("Error creando categoría");
        }
        finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    @Override
    public void borrarCategoria(Categoria categoria) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            int affected = database.delete(TablaCategoria, ColumnaId + "=?",
                    new String[] { Long.toString(categoria.getId()) });
            if (affected != 1) {
                throw new RuntimeException(String.format("Se esperaba borrar una única categoría pero se borraron %d", affected));
            }
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public Categoria modificarCategoria(Categoria categoria, String nuevoNombre, String nuevaDescripcion) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaNombre, nuevoNombre);
            values.put(ColumnaDescripcion, nuevaDescripcion);
            int affected = database.update(TablaCategoria, values, ColumnaId + "=?",
                    new String[] { Long.toString(categoria.getId()) });
            if (affected == 1) {
                return new Categoria(categoria.getId(), nuevoNombre, nuevaDescripcion);
            }

            throw new RuntimeException(String.format("Se esperaba modificar una única categoría pero se modificaron %d", affected));
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public Leccion crearLeccion(String titulo, String contenido, int duracion, int orden, Course curso) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaTitulo, titulo);
            values.put(ColumnaContenido, contenido);
            values.put(ColumnaDuracion, duracion);
            values.put(ColumnaOrden, orden);
            values.put(ColumnaRelacionCurso, curso.getId());
            database.beginTransaction();
            long id = database.insert(TablaLeccion, null, values);

            if (id != -1) {
                Leccion leccion = new Leccion(id, titulo, contenido, duracion, orden, curso);
                database.setTransactionSuccessful();
                return leccion;
            }

            throw new RuntimeException("Error creando lección");
        }
        finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    public Leccion modificarLeccion(Leccion leccion, String nuevoTitulo, String nuevoContenido, int nuevaDuracion, int nuevoOrden, Course nuevoCurso) {
        ISQLiteDatabaseWrapper database = null;

        try {
            Leccion nuevaLeccion = new Leccion(leccion.getId(), nuevoTitulo, nuevoContenido, nuevaDuracion, nuevoOrden, nuevoCurso);

            database = getInternalWritableDatabase();
            IContentValuesWrapper values = createContentValues();
            values.put(ColumnaTitulo, nuevoTitulo);
            values.put(ColumnaContenido, nuevoContenido);
            values.put(ColumnaDuracion, nuevaDuracion);
            values.put(ColumnaOrden, nuevoOrden);
            values.put(ColumnaRelacionCurso, nuevoCurso.getId());
            int affected = database.update(TablaLeccion, values, ColumnaId + "=?",
                    new String[] { Long.toString(leccion.getId()) });
            if (affected == 1) {
                return nuevaLeccion;
            }

            throw new RuntimeException(String.format("Se esperaba modificar una única lección pero se modificaron %d", affected));
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public void borrarLeccion(Leccion leccion) {
        ISQLiteDatabaseWrapper database = null;

        try {
            database = getInternalWritableDatabase();
            int affected = database.delete(TablaLeccion, ColumnaId + "=?",
                    new String[] { Long.toString(leccion.getId()) });
            if (affected != 1) {
                throw new RuntimeException(String.format("Se esperaba borrar una única lección pero se borraron %d", affected));
            }
        }
        finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public List<Leccion> buscarLecciones(Course curso) {
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

    protected ISQLiteDatabaseWrapper getInternalReadableDatabase() {
        return new SQLiteDatabaseWrapper(this.getReadableDatabase());
    }

    protected ISQLiteDatabaseWrapper getInternalWritableDatabase() {
        return new SQLiteDatabaseWrapper(this.getWritableDatabase());
    }

    protected IContentValuesWrapper createContentValues() {
        return new ContentValuesWrapper();
    }
}