package com.ar.bootcampar.model;

import android.content.Context;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.services.SharedPreferencesManager;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogicServices {
    // Copiado de Patterns.EMAIL_ADDRESS para las pruebas unitarias
    private static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private final Context context;
    private final IDatabase database;

    public LogicServices(Context context) {
        this.context = context;
        this.database = Database.CreateWith(context);
    }

    public LogicServices(Context context, IDatabase database) {
        this.context = context;
        this.database = database;
    }

    public Tupla<Usuario, String> registrarUsuario(String nombre, String apellido, String email, String clave, String confirmarClave, Rol rol, String telefono, String invitacion) {
        if (!esCadenaInvalida(nombre) && !esCadenaInvalida(apellido) && esEmailValido(email) && !esCadenaInvalida(clave) && !esCadenaInvalida(confirmarClave) && !esCadenaInvalida(invitacion)) {
            if (clave.equals(confirmarClave)) {
                Grupo grupo = database.buscarGrupoONada(invitacion);
                if (grupo == null) {
                    return new Tupla<>(null, String.format(getStringFromContext(R.string.invalid_invitation_code), invitacion));
                }

                Usuario usuario = database.buscarUsuarioONada(email);
                if (usuario != null) {
                    return new Tupla<>(null, String.format(getStringFromContext(R.string.registered_user_with_same_mail_exists), email));
                }

                usuario = database.crearUsuario(nombre, apellido, email, clave, rol, telefono);
                Division division = database.crearDivision(usuario, grupo);

                return new Tupla<>(usuario, getStringFromContext(R.string.registration_success_message));
            }
            else {
                return new Tupla<>(null, getStringFromContext(R.string.password_dont_match_message));
            }
        }
        else {
            return new Tupla<>(null, getStringFromContext(R.string.please_complete_data_message));
        }
    }

    public Tupla<Usuario, String> ingresarUsuario(String email, String clave) {
        if (!esCadenaInvalida(email) && esEmailValido(email) && !esCadenaInvalida(clave)) {
            Usuario usuario = database.buscarUsuarioONada(email);
            if (usuario != null) {
                if (usuario.getClave().equals(clave)) {
                    return new Tupla<>(usuario, getStringFromContext(R.string.welcomeMessage));
                }
            }
        }

        return new Tupla<>(null, getStringFromContext(R.string.invalidLoginMessage));
    }

    private static boolean esCadenaInvalida(String valor) {
        return (valor == null || valor.trim().isEmpty());
    }

    protected String getStringFromContext(int id) {
        return context.getString(id);
    }

    public void grabarUsuarioActivoEnPreferencias(Usuario usuario) {
        SharedPreferencesManager manager = new SharedPreferencesManager(context);
        manager.grabarUsuario(usuario);
    }

    public Usuario obtenerUsuarioActivoDePreferencias() {
        SharedPreferencesManager manager = new SharedPreferencesManager(context);
        return manager.cargarUsuario();
    }

    public void borrarGrupo(Grupo grupo) {
        database.borrarGrupo(grupo);
    }

    public List<Grupo> listarGrupos() {
        return database.listarGrupos();
    }

    public static boolean esEmailValido(CharSequence target) {
        return (target != null && !esCadenaInvalida(target) && EMAIL_ADDRESS.matcher(target).matches());
    }

    private static boolean esCadenaInvalida(CharSequence valor) {
        return esCadenaInvalida(valor.toString());
    }

    public void borrarCurso(Curso curso) {
        database.borrarCurso(curso);
    }

    public List<Curso> listarCursos(Usuario usuario) {
        return database.buscarCursos(usuario);
    }

    public List<Curso> listarCursos() {
        return database.listarCursos();
    }

    public List<Curso> listarCursosMasNuevos() {
        return database.listarCursos();
    }

    public void borrarLeccion(Leccion leccion) {
        database.borrarLeccion(leccion);
    }

    public List<Leccion> listarLecciones() {
        return database.listarLecciones();
    }

    public List<Leccion> buscarLecciones(Curso curso) {
        return database.buscarLecciones(curso);
    }
      
    public void borrarCategoria(Categoria categoria) {
        this.database.borrarCategoria(categoria);
    }

    public List<Categoria> listarCategorias() {
        return this.database.listarCategorias();
    }

    public void borrarCurricula(Curricula curricula) {
        database.borrarCurricula(curricula);
    }

    public List<Curricula> listarCurriculas() {
        return this.database.listarCurriculas();
    }

    public Tupla<Inscripcion, String> inscribirCurso(Usuario usuario, Curso curso) {
        if (usuario != null && curso != null) {
            if (usuario.getRol() == Rol.Estudiante) {
                Inscripcion inscripcion = database.buscarInscripcionONada(usuario, curso);
                if (inscripcion == null) {
                    inscripcion = database.crearInscripcion(usuario, curso, 0, false, 0);
                    if (inscripcion != null) {
                        return new Tupla<>(inscripcion, getStringFromContext(R.string.enrollment_success));
                    } else {
                        return new Tupla<>(null, context.getString(R.string.error_inscribiendo_al_curso));
                    }
                } else {
                    return new Tupla<>(inscripcion, context.getString(R.string.ya_esta_inscripto_en_el_curso));
                }
            }
            else {
                return new Tupla<>(null, context.getString(R.string.administrador_no_puede_inscribirse));
            }
        }
        else {
            return new Tupla<>(null, context.getString(R.string.los_datos_son_invalidos));
        }
    }

    public Tupla<Usuario, String> modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String oldPassword, String newPassword, String confirmPassword) {
        try {
            if (usuario == null) {
                return new Tupla<>(null, getStringFromContext(R.string.cannot_update_profile));
            }

            String nuevaClave = usuario.getClave();

            if (oldPassword.isEmpty() && (!newPassword.isEmpty() || !confirmPassword.isEmpty())) {
                return new Tupla<>(null, getStringFromContext(R.string.old_password_is_required));
            }

            if (!esCadenaInvalida(oldPassword)) {
                if (! usuario.getClave().equals(oldPassword)) {
                    return new Tupla<>(null, getStringFromContext(R.string.old_password_does_not_match));
                }

                if (! newPassword.equals(confirmPassword)) {
                    return new Tupla<>(null, getStringFromContext(R.string.new_password_dont_match));
                }

                if (newPassword.isEmpty()) {
                    return new Tupla<>(null, getStringFromContext(R.string.invalid_blank_password));
                }

                nuevaClave = newPassword;
            }

            if (!esCadenaInvalida(nuevoNombre) && !esCadenaInvalida(nuevoApellido)) {
                Usuario nuevoUsuario = database.modificarUsuario(usuario, nuevoNombre, nuevoApellido, usuario.getEmail(), nuevaClave, usuario.getRol(), usuario.getTelefono());
                return new Tupla<>(nuevoUsuario, getStringFromContext(R.string.profile_updated_successfully));
            }

            return new Tupla<>(null, getStringFromContext(R.string.complete_profile_data_please));
        }
        catch (Exception exception) {
            return new Tupla<>(null, exception.getMessage());
        }
    }

    public Inscripcion buscarInscripcion(Usuario usuario, Curso curso) {
        try {
            if (usuario != null && curso != null) {
                return database.buscarInscripcionONada(usuario, curso);
            }

            return null;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public List<Inscripcion> listarInscripcionesFavoritas(Usuario usuario) {
        return database.buscarInscripcionesFavoritas(usuario);
    }

    public Inscripcion alternarFavoritismo(Inscripcion inscripcion) {
        return database.modificarInscripcion(inscripcion, inscripcion.getUsuario(), inscripcion.getCurso(), inscripcion.getPuntuacion(), ! inscripcion.getFavorito(), inscripcion.getUltimaLeccion());
    }

    public List<Inscripcion> buscarInscripciones(Usuario usuario) {
        return database.buscarInscripciones(usuario);
    }

    public Usuario buscarUsuario(String email) {
        return database.buscarUsuarioONada(email);
    }

    public double obtenerRatingDe(Curso curso) {
        List<Inscripcion> inscripciones = database.buscarInscripciones(curso);
        return inscripciones.stream()
                .mapToDouble(Inscripcion::getPuntuacion)
                .average()
                .orElse(0);
    }

    public Inscripcion actualizarPuntuacion(Inscripcion inscripcion, int nuevaPuntuacion) {
        return database.modificarInscripcion(inscripcion, inscripcion.getUsuario(), inscripcion.getCurso(), nuevaPuntuacion, inscripcion.getFavorito(), inscripcion.getUltimaLeccion());
    }

    public List<Curso> buscarCursos(String query) {

        List<Curso> cursos = database.listarCursos();

        return cursos.stream()
                .filter(c -> c.getTitulo().toLowerCase().contains(query.toLowerCase()) ||
                        c.getDescripcion().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

    }

    public List<Categorizacion> buscarCategorizaciones(Curso curso) {
        return database.buscarCategorizaciones(curso);
    }
}