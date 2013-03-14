package com.bpingar.arias.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.adapter.CompraAdapter;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Usuario;

public class MisComprasActivity extends MenuActivity implements OnClickListener {

	private static final int _NUEVA_COMPRA_GRABADA = 1;

	private CompraAdapter misComprasAdapter;
	private List<Compra> misCompras;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mis_compras);

		precargarUsuario();
		establecerTitulo();
		mostrarSaludo();

		final Button botonAnadirCompra = (Button) findViewById(R.id.anadirCompra);
		botonAnadirCompra.setOnClickListener(this);

		final ListView misComprasListView = (ListView) findViewById(android.R.id.list);

		final Long usuarioId = ((Arias) getApplication()).getUsuario().getId();
		if (usuarioId != null) {
			misCompras = getHelper().getCompraDAO().queryForEq("usuarioId",
					usuarioId);
		} else {
			misCompras = new ArrayList<Compra>();
		}
		misComprasAdapter = new CompraAdapter(this,
				android.R.layout.simple_list_item_1, misCompras);
		misComprasListView.setAdapter(misComprasAdapter);

		registerForContextMenu(misComprasListView);
	}

	private void mostrarSaludo() {
		final SharedPreferences general = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		final boolean mostrar_saludo = general.getBoolean("saludo_checkbox",
				false);
		if (mostrar_saludo && !((Arias) getApplication()).isSaludoMostrado()) {
			Toast.makeText(
					this,
					general.getString("saludo_texto",
							getString(R.string.pref_default_texto_saludo)),
					Toast.LENGTH_LONG).show();
			((Arias) getApplication()).setSaludoMostrado(true);
		}
	}

	private void precargarUsuario() {
		final SharedPreferences preferencias = getSharedPreferences(
				Arias.PREFERENCIAS, MODE_PRIVATE);

		// final SharedPreferences.Editor editor = preferencias.edit();
		// editor.putString(Arias.USUARIO, "");
		// editor.commit();

		final String nombreUsuario = preferencias.getString(Arias.USUARIO, "");
		if ("".equals(nombreUsuario)) {
			startActivityForResult(new Intent(this,
					EstablecerUsuarioActivity.class), _USUARIO_REGISTRADO);
		} else {
			final Usuario usuario = new GestionUsuarios(this)
					.buscarUsuario(nombreUsuario);
			if (usuario == null) {
				startActivityForResult(new Intent(this,
						EstablecerUsuarioActivity.class), _USUARIO_REGISTRADO);
			} else {
				((Arias) getApplication()).setUsuario(usuario);
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
				final Compra nuevaCompra = (Compra) data
						.getSerializableExtra("nuevaCompra");
				misCompras.add(nuevaCompra);
				misComprasAdapter.notifyDataSetChanged();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, R.string.nueva_compra_guardada_error,
						Toast.LENGTH_SHORT).show();
			}
			break;
		case _USUARIO_REGISTRADO:
			if (resultCode == RESULT_OK) {
				misCompras.clear();
				misCompras.addAll(getHelper().getCompraDAO().queryForEq(
						"usuarioId",
						((Arias) getApplication()).getUsuario().getId()));
				misComprasAdapter.notifyDataSetChanged();
				establecerTitulo();
				break;
			}
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_mis_compras:
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
