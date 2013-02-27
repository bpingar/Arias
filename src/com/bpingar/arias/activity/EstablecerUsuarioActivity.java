package com.bpingar.arias.activity;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bpingar.arias.R;
import com.bpingar.arias.database.DatabaseHelper;
import com.bpingar.arias.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class EstablecerUsuarioActivity extends
		OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_establecer_usuario);

		final Button boton = (Button) findViewById(R.id.establecer_usuario);
		boton.setOnClickListener(this);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.establecer_usuario:
			final EditText nombreUsuario = (EditText) findViewById(R.id.usuario);
			final String nombreUsr = nombreUsuario.getText().toString();

			final SharedPreferences.Editor editor = getSharedPreferences(
					Arias.PREFERENCIAS, MODE_PRIVATE).edit();
			editor.putString(Arias.USUARIO, nombreUsr);
			editor.commit();

			final List<Usuario> usuarios = getHelper().getUsuarioDAO()
					.queryForEq("nombreUsuario", nombreUsr);
			Usuario usuario;
			if (!usuarios.isEmpty()) {
				usuario = usuarios.get(0);
			} else {
				usuario = new Usuario(nombreUsr);
				getHelper().getUsuarioDAO().create(usuario);
			}

			((Arias) getApplication()).setUsuario(usuario);

			final Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}

}
