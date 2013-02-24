package com.bpingar.arias.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.adapter.CompraAdapter;

public class MisComprasActivity extends ListActivity implements OnClickListener {

	private static final int _NUEVA_COMPRA_GRABADA = 1;

	private CompraAdapter misComprasAdapter;
	private SharedPreferences preferencias;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mis_compras);

		preferencias = getSharedPreferences(Arias.PREFERENCIAS, MODE_PRIVATE);
		guardarUsuario();
		fijarTitulo();

		final Arias arias = (Arias) getApplication();

		final Button botonAnadirCompra = (Button) findViewById(R.id.anadirCompra);
		botonAnadirCompra.setOnClickListener(this);

		misComprasAdapter = new CompraAdapter(this,
				android.R.layout.simple_list_item_1, arias.getMisCompras());
		setListAdapter(misComprasAdapter);
	}

	private void guardarUsuario() {
		final SharedPreferences.Editor editor = preferencias.edit();
		editor.putString(Arias.USUARIO, "bpingar");
		editor.commit();
	}

	private void fijarTitulo() {
		final TextView tituloMisCompras = (TextView) findViewById(R.id.titulo_mis_compras);
		tituloMisCompras.setText(getString(R.string.compras_usuario,
				preferencias.getString(Arias.USUARIO, "-")));
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

		case R.id.menu_settings:
			Toast.makeText(this, R.string.menu_settings, Toast.LENGTH_SHORT)
					.show();
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
