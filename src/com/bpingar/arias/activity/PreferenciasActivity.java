package com.bpingar.arias.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;

import com.bpingar.arias.R;

public class PreferenciasActivity extends Activity {

	protected static final int _USUARIO_REGISTRADO = 2;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PreferenciasFragment())
				.commit();
	}

	public static class PreferenciasFragment extends PreferenceFragment {
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferencias);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.menu_base, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_mis_compras:
			startActivity(new Intent(this, MisComprasActivity.class));
			break;

		case R.id.menu_usuario:
			startActivityForResult(new Intent(this,
					EstablecerUsuarioActivity.class), _USUARIO_REGISTRADO);
			break;

		case R.id.menu_usuarios:
			startActivity(new Intent(this, UsuariosActivity.class));
			break;

		case R.id.menu_arias:
			startActivity(new Intent(this, InformacionAriasActivity.class));
			break;

		case R.id.menu_preferencias:
			// startActivity(new Intent(this, PreferenciasActivity.class));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
