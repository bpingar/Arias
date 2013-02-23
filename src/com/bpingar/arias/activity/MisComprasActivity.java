package com.bpingar.arias.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.adapter.CompraAdapter;
import com.bpingar.arias.model.Compra;

public class MisComprasActivity extends ListActivity implements OnClickListener {

	private static final int _NUEVA_COMPRA_GRABADA = 1;

	// private ArrayAdapter<String> misComprasAdapter;
	private CompraAdapter misComprasAdapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mis_compras);

		final Arias arias = (Arias) getApplication();

		final Button botonAnadirCompra = (Button) findViewById(R.id.anadirCompra);
		botonAnadirCompra.setOnClickListener(this);

		misComprasAdapter = new CompraAdapter(this,
				android.R.layout.simple_list_item_1, arias.getMisCompras());
		setListAdapter(misComprasAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mis_compras, menu);
		return true;
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.anadirCompra:
			final Intent intent = new Intent(this, NuevaCompraActivity.class);
			startActivityForResult(intent, _NUEVA_COMPRA_GRABADA);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		final Arias arias = (Arias) getApplication();
		switch (requestCode) {
		case _NUEVA_COMPRA_GRABADA:
			if (resultCode == RESULT_OK) {
				final Compra miNuevaCompra = (Compra) data.getExtras()
						.getSerializable(Arias.COMPRA);
				arias.getMisCompras().add(miNuevaCompra);
				misComprasAdapter.notifyDataSetChanged();
				Toast.makeText(this, R.string.nueva_compra_guardada_OK,
						Toast.LENGTH_SHORT).show();
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
