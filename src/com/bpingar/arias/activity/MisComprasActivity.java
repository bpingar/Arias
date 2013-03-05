package com.bpingar.arias.activity;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.adapter.CompraAdapter;
import com.bpingar.arias.database.DatabaseHelper;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

public class MisComprasActivity extends OrmLiteBaseListActivity<DatabaseHelper>
		implements OnClickListener {

	private static final int _NUEVA_COMPRA_GRABADA = 1;
	private static final int _USUARIO_REGISTRADO = 2;

	private CompraAdapter misComprasAdapter;
	private List<Compra> misCompras;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mis_compras);

		final Button botonAnadirCompra = (Button) findViewById(R.id.anadirCompra);
		botonAnadirCompra.setOnClickListener(this);

		misCompras = getHelper().getCompraDAO().queryForAll();
		misComprasAdapter = new CompraAdapter(this,
				android.R.layout.simple_list_item_1, misCompras);
		setListAdapter(misComprasAdapter);

		registerForContextMenu(getListView());

		precargarUsuario();
		establecerTitulo();
		mostrarSaludo();
	}

	private void mostrarSaludo() {
		final SharedPreferences general = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		final boolean mostrar_saludo = general.getBoolean(
				getString(R.string.saludo), false);
		if (mostrar_saludo) {
			Toast.makeText(
					this,
					general.getString(getString(R.string.texto_saludo),
							getString(R.string.saludo_defecto)),
					Toast.LENGTH_LONG).show();

		}
	}

	private void precargarUsuario() {
		final SharedPreferences preferencias = getSharedPreferences(
				Arias.PREFERENCIAS, MODE_PRIVATE);

		// final SharedPreferences.Editor editor = preferencias.edit();
		// editor.putString(Arias.USUARIO, "");
		// editor.commit();

		final String nombreUsr = preferencias.getString(Arias.USUARIO, "");
		if (nombreUsr.equals("")) {
			startActivityForResult(new Intent(this,
					EstablecerUsuarioActivity.class), _USUARIO_REGISTRADO);
		} else {
			final List<Usuario> usuarios = getHelper().getUsuarioDAO()
					.queryForEq("nombreUsuario", nombreUsr);
			Usuario usuario;
			if (!usuarios.isEmpty()) {
				usuario = usuarios.get(0);
				((Arias) getApplication()).setUsuario(usuario);
			} else {
				startActivityForResult(new Intent(this,
						EstablecerUsuarioActivity.class), _USUARIO_REGISTRADO);
			}
		}
	}

	private void establecerTitulo() {
		setTitle(getString(R.string.compras_usuario, ((Arias) getApplication())
				.getUsuario().getNombreUsuario()));
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v,
			final ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(R.string.eliminar_compra);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final Compra compra = misCompras.get(info.position);
		getHelper().getCompraDAO().delete(compra);
		misCompras.remove(compra);
		misComprasAdapter.notifyDataSetChanged();
		Toast.makeText(this, R.string.eliminar_compra_ok, Toast.LENGTH_SHORT)
				.show();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.activity_mis_compras, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
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

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.anadirCompra:
			startActivityForResult(new Intent(this, NuevaCompraActivity.class),
					_NUEVA_COMPRA_GRABADA);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		switch (requestCode) {
		case _NUEVA_COMPRA_GRABADA:
			if (resultCode == RESULT_OK) {
				misCompras.clear();
				misCompras.addAll(getHelper().getCompraDAO().queryForAll());
				misComprasAdapter.notifyDataSetChanged();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, R.string.nueva_compra_guardada_error,
						Toast.LENGTH_SHORT).show();
			}
			break;
		case _USUARIO_REGISTRADO:
			if (resultCode == RESULT_OK) {
				// TODO rellenar lista con compras del usuario
				establecerTitulo();
				break;
			}
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
