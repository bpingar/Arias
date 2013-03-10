package com.bpingar.arias.activity;

import java.util.List;

import android.content.Context;

import com.bpingar.arias.database.DatabaseHelper;
import com.bpingar.arias.model.Usuario;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class GestionUsuarios {

	private DatabaseHelper databaseHelper;

	public GestionUsuarios(final Context context) {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context,
					DatabaseHelper.class);
		}
	}

	public Usuario buscarUsuario(final String nombre) {
		final List<Usuario> usuarios = databaseHelper.getUsuarioDAO()
				.queryForEq("nombreUsuario", nombre);
		if (usuarios.isEmpty()) {
			return null;
		}
		return usuarios.get(0);
	}

}
