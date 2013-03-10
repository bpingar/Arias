package com.bpingar.arias.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.adapter.UsuarioAdapter;
import com.bpingar.arias.database.DatabaseHelper;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

public class UsuariosActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	protected static final int _USUARIO_REGISTRADO = 2;

	private UsuarioAdapter usuariosAdapter;
	private List<Usuario> usuarios;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuarios);

		usuarios = getHelper().getUsuarioDAO().queryForAll();
		// usuarios.remove(((Arias) getApplication()).getUsuario());
		usuariosAdapter = new UsuarioAdapter(this,
				android.R.layout.simple_list_item_1, usuarios);
		setListAdapter(usuariosAdapter);

		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v,
			final ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(R.string.eliminar_usuario);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final Usuario usuario = usuarios.get(info.position);

		if (usuario.getId().equals(
				((Arias) getApplication()).getUsuario().getId())) {
			Toast.makeText(this, R.string.eliminar_usuario_no_posible,
					Toast.LENGTH_SHORT).show();
		} else {

			final List<Compra> comprasUsuario = getHelper().getCompraDAO()
					.queryForEq("usuarioId", usuario.getId());
			if (comprasUsuario.isEmpty()) {
				getHelper().getUsuarioDAO().delete(usuario);
				usuarios.remove(usuario);
				usuariosAdapter.notifyDataSetChanged();
				Toast.makeText(
						this,
						getString(R.string.eliminar_usuario_ok,
								usuario.getNombreUsuario()), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(this,
						R.string.eliminar_usuario_no_posible_tiene_compras,
						Toast.LENGTH_SHORT).show();
			}
		}
		return true;
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
