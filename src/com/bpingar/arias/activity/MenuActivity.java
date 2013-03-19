package com.bpingar.arias.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.bpingar.arias.R;
import com.bpingar.arias.database.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class MenuActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	protected static final int _USUARIO_REGISTRADO = 2;

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

		case R.id.menu_marcas:
			startActivity(new Intent(this, ItemListActivity.class));
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
			startActivity(new Intent(this, PreferenciasActivity.class));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
