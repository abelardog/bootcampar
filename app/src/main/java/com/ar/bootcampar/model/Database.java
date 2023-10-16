package com.ar.bootcampar.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.ar.bootcampar.model.utilities.Guardia;

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
    private static final String TablaDivision = "Divisiones";
    private static final String ColumnaTitulo = "Titulo";
    private static final String ColumnaDescripcion = "Descripcion";
    private static final String ColumnaNivel = "Nivel";
    private static final String ColumnaImagen = "Imagen";
    private static final String[] CamposCurso = new String[] { ColumnaId, ColumnaTitulo, ColumnaDescripcion, ColumnaNivel, ColumnaImagen };
    private static final String TablaCurso = "Cursos";
    private static final String ColumnaRelacionUsuario = "UsuarioId";
    private static final String ColumnaPuntuacion = "Puntuacion";
    private static final String ColumnaFavorito = "Favorito";
    private static final String ColumnaUltimaLeccion = "UltimaLeccion";
    private static final String[] CamposInscripcion = new String[] { ColumnaId, ColumnaRelacionUsuario, ColumnaRelacionCurso, ColumnaPuntuacion, ColumnaFavorito, ColumnaUltimaLeccion };
    private static final String TablaInscripcion = "Inscripciones";
    private static final String TablaCurricula = "Curriculas";
    private static final String ColumnaContenido = "Contenido";
    private static final String ColumnaDuracion = "Duracion";
    private static final String ColumnaOrden = "Orden";
    private static final String[] CamposLeccion = new String[] { ColumnaId, ColumnaTitulo, ColumnaContenido, ColumnaDuracion, ColumnaOrden };
    private static final String TablaLeccion = "Lecciones";
    private static final String[] CamposCategoria = new String[] { ColumnaId, ColumnaNombre, ColumnaDescripcion };
    private static final String TablaCategoria = "Categorias";
    private static final String ColumnaRelacionCategoria  = "CategoriaId";
    private static final String TablaCategorizacion = "Categorizaciones";
    private Object categorizaciones ;

    public static IDatabase CreateWith(Context applicationContext) {
        // Version 2: Agregar administrador en base de datos
        // Version 3: Agregar imagen al curso en base de datos
        return new Database(applicationContext, "bootcampar.db", null, 3);
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
        db.execSQL("INSERT INTO " + TablaGrupo + "(" + ColumnaNombre + ", " + ColumnaInvitacion + ") VALUES ('Grupo de Programadores', '112233')");
        db.execSQL("INSERT INTO " + TablaUsuario + "("+ ColumnaNombre + ", " + ColumnaApellido + ", " + ColumnaEmail + ", " + ColumnaClave + ", " + ColumnaRol + ", " + ColumnaTelefono + ") VALUES ('Admin', 'Admin', 'admin@gmail.com', '123456', 1, '')");
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
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionUsuario, usuario.getId());
        values.put(ColumnaRelacionGrupo, grupo.getId());

        return (Division)crearElemento(TablaDivision, values, id -> new Division(id, usuario, grupo), "Error creando usuario");
    }

    @Override
    public void borrarDivision(Division division) {
        Guardia.esObjetoValido(division, "La division es nula");
        borrarElemento(TablaDivision, division.getId(), "Se esperaba borrar una única division");
    }

    @Override
    public Division modificarDivision(Division division, Usuario nuevoUsuario, Grupo nuevoGrupo) {
        Guardia.esObjetoValido(division, "La division es nula");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoUsuario.getId());
        values.put(ColumnaRelacionGrupo, nuevoGrupo.getId());

        return (Division) modificarElemento(TablaDivision, division.getId(), values, id -> new Division(division.getId(), nuevoUsuario, nuevoGrupo),"Se esperaba modificar una unica Division");

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

    @NonNull
    private static Grupo obtenerGrupoDeCursor(ICursorWrapper cursor) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Grupo(
                cursorHelper.getLongFrom(ColumnaId),
                cursorHelper.getStringFrom(ColumnaNombre),
                cursorHelper.getStringFrom(ColumnaInvitacion));
    }

    @NonNull
    private static Curso obtenerCursoDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Curso(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                cursorHelper.getStringFrom(prefijo + ColumnaTitulo),
                cursorHelper.getStringFrom(prefijo + ColumnaDescripcion),
                false, "", cursorHelper.getIntFrom(prefijo + ColumnaNivel));
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
                obtenerCursoDeCursor(cursor, TablaCurso + "."));
    }

    @NonNull
    private static Inscripcion obtenerInscripcionDeCursor(ICursorWrapper cursor, Usuario usuario) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Inscripcion(
                cursorHelper.getLongFrom(ColumnaId),
                usuario,
                obtenerCursoDeCursor(cursor, TablaCurso + "." + ColumnaId),
                cursorHelper.getIntFrom(ColumnaPuntuacion),
                cursorHelper.getIntFrom(ColumnaFavorito) != 0,
                cursorHelper.getIntFrom(ColumnaUltimaLeccion));
    }

    @NonNull
    private static Inscripcion obtenerInscripcionDeCursor(ICursorWrapper cursor, String prefijo) {
        CursorHelper cursorHelper = new CursorHelper(cursor);
        return new Inscripcion(
                cursorHelper.getLongFrom(prefijo + ColumnaId),
                obtenerUsuarioDeCursor(cursor, TablaUsuario + "."),
                obtenerCursoDeCursor(cursor, TablaCurso + "."),
                cursorHelper.getIntFrom(prefijo + ColumnaPuntuacion),
                cursorHelper.getIntFrom(prefijo + ColumnaFavorito) == 0,
                cursorHelper.getIntFrom(prefijo + ColumnaUltimaLeccion));
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
    public Categoria buscarCategoriaONada(String nombre) {
        return (Categoria)buscarElementoONada(TablaCategoria, CamposCategoria, ColumnaNombre, nombre,
                Database::obtenerGrupoDeCursor,"Se encontraron varias categorías con el mismo nombre %s");
    }

    @Override
    public Categoria modificarCategoria(Categoria categoria, String nuevoNombre, String nuevaDescripcion) {
        Guardia.esObjetoValido(categoria, "La categoría es nula");

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
    public Leccion crearLeccion(String titulo, String contenido, int duracion, int orden, Curso curso) {
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, titulo);
        values.put(ColumnaContenido, contenido);
        values.put(ColumnaDuracion, duracion);
        values.put(ColumnaOrden, orden);
        values.put(ColumnaRelacionCurso, curso.getId());
        return (Leccion)crearElemento(TablaLeccion, values, id -> new Leccion(id, titulo, contenido, duracion, orden, curso), "Error creando lección");
    }

    @Override
    public Leccion modificarLeccion(Leccion leccion, String nuevoTitulo, String nuevoContenido, int nuevaDuracion, int nuevoOrden, Curso nuevoCurso) {
        Guardia.esObjetoValido(leccion, "La lección es nula");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, nuevoTitulo);
        values.put(ColumnaContenido, nuevoContenido);
        values.put(ColumnaDuracion, nuevaDuracion);
        values.put(ColumnaOrden, nuevoOrden);
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());

        return (Leccion)modificarElemento(TablaLeccion, leccion.getId(), values, id -> new Leccion(id, nuevoTitulo, nuevoContenido, nuevaDuracion, nuevoOrden, nuevoCurso), "Se esperaba modificar una única lección pero se modificaron %d");
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
                    Leccion leccion = obtenerLeccionDeCursor(cursor, TablaLeccion + ".");
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
                    ColumnaRelacionUsuario + "=? AND " + ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId,
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
                    ColumnaRelacionUsuario + "=? AND " + ColumnaRelacionCurso + " = " + TablaCurso + "." + ColumnaId +
                            " AND " + ColumnaRelacionUsuario + " = " + TablaUsuario + "." + ColumnaId,
                    new String[] { String.valueOf(id) }, null, null, null);

            if (cursor.getCount() == 1) {
                return obtenerInscripcionDeCursor(cursor, TablaInscripcion + ".");
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
    public Curricula crearCurricula(Curso nuevoCurso, Grupo nuevoGrupo) {
        IContentValuesWrapper values = createContentValues();

        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionGrupo, nuevoGrupo.getId());

        return (Curricula)crearElemento(TablaCurricula, values, id -> new Curricula(id, nuevoCurso, nuevoGrupo), "Error crear curricula");
    }

    @Override
    public Categorizacion modificarCategorizacion(Categorizacion categorizacion, Curso nuevoCurso, Categoria nuevaCategoria) {
        Guardia.esObjetoValido(categorizacion, "La categorización es nula");
        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionCategoria, nuevaCategoria.getId());
        return (Categorizacion) modificarElemento(TablaCategorizacion, categorizacion.getId(), values, id -> new Categorizacion(categorizacion.getId(), nuevoCurso, nuevaCategoria),"Se esperaba modificar una unica categorización pero se modificaron %d");
    }

    // TODO: Borrar isFavorite
    @Override
    public Curso crearCurso(String title, String description, Boolean isFavorite, String imageName, int nivel) {

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, title);
        values.put(ColumnaDescripcion, description);
        values.put(ColumnaIsFavorite, isFavorite);
        values.put(ColumnaImageName, imageName);
        values.put(ColumnaNivel, nivel);

        return (Curso)crearElemento(TablaCurso, values, id -> new Curso(id, title, description, isFavorite, imageName, nivel), "Error creando curso");
    }

    @Override
    public Curso modificarCurso(Curso curso, String title, String description, Boolean isFavorite, String imageName, int nivel) {
        Guardia.esObjetoValido(curso, "El curso es nulo");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaTitulo, title);
        values.put(ColumnaDescripcion, description);
        values.put(ColumnaIsFavorite, isFavorite);
        values.put(ColumnaImageName, imageName);
        values.put(ColumnaNivel, nivel);

        return (Curso)modificarElemento(TablaCurso, curso.getId(), values, id -> new Curso(curso.getId(), title, description, isFavorite, imageName, nivel), "Se esperaba modificar un único curso pero se modificaron %d");
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
                            cursorHelper.getIntFrom(ColumnaNivel),
                            cursorHelper.getStringFrom(ColumnaImagen));

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
                Database::obtenerCursoDeCursor,"Se encontraron varios cursos con el mismo nombre %s");
    }

    @Override
    public Curricula modificarCurricula(Curricula curricula, Curso nuevoCurso, Grupo nuevoGrupo) {
        Guardia.esObjetoValido(curricula, "La currícula son nulas");

        IContentValuesWrapper values = createContentValues();
        values.put(ColumnaRelacionCurso, nuevoCurso.getId());
        values.put(ColumnaRelacionGrupo, nuevoGrupo.getId());

        return (Curricula)modificarElemento(TablaCurricula, curricula.getId(), values, id -> new Curricula(curricula.getId(), nuevoCurso, nuevoGrupo),"Se esperaba modificar una única currícula pero se modificaron %d");
    }

    @Override
    public void borrarCurricula(Curricula curricula) {
        Guardia.esObjetoValido(curricula, "La currícula es nula");
        borrarElemento(TablaCurricula, curricula.getId(), "Se esperaba borrar una única curricula pero se borraron %d");
    }

    private String[] agregarNombreDeTablaEnColumnas(String tabla, String[] campos) {
        return Arrays.stream(campos).map(s -> tabla + "." + s).toArray(String[]::new);
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