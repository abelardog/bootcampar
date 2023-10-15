package com.ar.bootcampar.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.ar.bootcampar.model.Usuario;
import com.google.gson.Gson;

public class SharedPreferencesManager {
    private final String PREFS_NAME = "com.ar.bootcampar";
    private final String KEY_USUARIO = "usuarioActivo";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void grabarUsuario(Usuario usuario) {
        sharedPreferences.edit().putString(KEY_USUARIO, new Gson().toJson(usuario)).apply();
    }

    public Usuario cargarUsuario() {
        String json = sharedPreferences.getString(KEY_USUARIO, null);
        if (json == null) {
            return null;
        }

        return new Gson().fromJson(json, Usuario.class);
    }
}
