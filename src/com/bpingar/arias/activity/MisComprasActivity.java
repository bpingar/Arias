package com.bpingar.arias.activity;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

public class MisComprasActivity extends OrmLiteBaseListActivity<DatabaseHelper>
		implements OnClickListener {

	private static final int _NUEVA_COMPRA_GRABADA = 1;

	private CompraAdapter misComprasAdapter;
	private List<Compra> misCompras;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mis_compras);

		guardarUsuario();
		fijarTitulo();

		final Arias arias = (Arias) getApplication();

		final Button botonAnadirCompra = (Button) findViewById(R.id.anadirCompra);
		botonAnadirCompra.setOnClickListener(this);

		Toast.makeText(this, "Saved: " + getHelper().getCompraDAO().countOf(),
				Toast.LENGTH_SHORT).show();

		misCompras = getHelper().getCompraDAO().queryForAll();
		misComprasAdapter = new CompraAdapter(this,
				android.R.layout.simple_list_item_1, misCompras);
		setListAdapter(misComprasAdapter);

		registerForContextMenu(getListView());
	}

	private void guardarUsuario() {
		final SharedPreferences.Editor editor = getSharedPreferences(
				Arias.PREFERENCIAS, MODE_PRIVATE).edit();
		editor.putString(Arias.USUARIO, "bpingar");
		editor.commit();
	}

	private void fijarTitulo() {
		final SharedPreferences preferencias = getSharedPreferences(
				Arias.PREFERENCIAS, MODE_PRIVATE);
		// final TextView tituloMisCompras = (TextView)
		// findViewById(R.id.titulo_mis_compras);
		// tituloMisCompras.setText(getString(R.string.compras_usuario,
		// preferencias.getString(Arias.USUARIO, "-")));
		setTitle(getString(R.string.compras_usuario,
				preferencias.getString(Arias.USUARIO, "-")));
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v,
			final ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(R.string.borrar_compra);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final Compra compra = misCompras.get(info.position);
		getHelper().getCompraDAO().delete(compra);
		misCompras.remove(compra);
		misComprasAdapter.notifyDataSetChanged();
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

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
