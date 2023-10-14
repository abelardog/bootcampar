package com.ar.bootcampar.model;

import android.content.Context;
import android.util.Pair;

import com.ar.bootcampar.R;
import com.ar.bootcampar.activities.ResetPasswordActivity;

public class LogicServices {
    private Context context;
    private IDatabase database;

    public LogicServices(Context context) {
        this.context = context;
        database = Database.CreateWith(context);
    }

    public Pair<Usuario, String> registrarUsuario(String nombre, String apellido, String email, String clave, String confirmarClave, Rol rol, String telefono, String invitacion) {
        if (! nombre.isEmpty() && ! apellido.isEmpty() && ResetPasswordActivity.isValidEmail(email) && !clave.isEmpty() && !confirmarClave.isEmpty() && !invitacion.isEmpty()) {
            if (clave.equals(confirmarClave)) {
                Grupo grupo = database.buscarGrupoONada(invitacion);
                if (grupo == null) {
                    return Pair.create(null, String.format(context.getString(R.string.invalid_invitation_code), invitacion));
                }

                Usuario usuario = database.buscarUsuarioONada(email);
                if (usuario != null) {
                    return Pair.create(null, String.format(context.getString(R.string.registered_user_with_same_mail_exists), email));
                }

                usuario = database.crearUsuario(nombre, apellido, email, clave, rol, telefono);
                Division division = database.crearDivision(usuario, grupo);

                return Pair.create(usuario, context.getString(R.string.registration_success_message));
            }
            else {
                return Pair.create(null, context.getString(R.string.password_dont_match_message));
            }
        }
        else {
            return Pair.create(null, context.getString(R.string.please_complete_data_message));
        }
    }
}