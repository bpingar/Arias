package com.bpingar.arias.activity;

import java.util.List;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.adapter.UsuarioAdapter;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Usuario;

public class UsuariosActivity extends MenuActivity {

	protected static final int _USUARIO_REGISTRADO = 2;

	private UsuarioAdapter usuariosAdapter;
	private List<Usuario> usuarios;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuarios);

		final ListView usuariosListView = (ListView) findViewById(android.R.id.list);

		usuarios = getHelper().getUsuarioDAO().queryForAll();
		usuariosAdapter = new UsuarioAdapter(this,
				android.R.layout.simple_list_item_1, usuarios);
		usuariosListView.setAdapter(usuariosAdapter);

		registerForContextMenu(usuariosListView);
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
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_usuarios:
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
