package com.bpingar.arias.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.database.DatabaseHelper;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Producto;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class NuevaCompraActivity extends OrmLiteBaseActivity<DatabaseHelper>
		implements OnClickListener {

	private ArrayAdapter<Producto> productos;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_compra);

		establecerTitulo();

		final Spinner spinner = (Spinner) findViewById(R.id.producto);

		productos = new ArrayAdapter<Producto>(this,
				android.R.layout.simple_spinner_item,
				Producto.getListadoProductos());
		productos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(productos);

		final Button boton = (Button) findViewById(R.id.guardar_compra);
		boton.setOnClickListener(this);
	}

	private void establecerTitulo() {
		setTitle(getString(R.string.nueva_compra_usuario,
				((Arias) getApplication()).getUsuario()));
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.activity_nueva_compra, menu);
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
		case R.id.guardar_compra:
			guardarCompra();
			break;
		default:
			break;
		}
	}

	private void guardarCompra() {

		// final EditText nombreProducto = (EditText)
		// findViewById(R.id.nombre_producto);
		final Spinner producto = (Spinner) findViewById(R.id.producto);
		final EditText numeroUnidades = (EditText) findViewById(R.id.numero_unidades);
		final EditText fechaCompra = (EditText) findViewById(R.id.fecha_compra);

		try {
			final Compra miNuevaCompra = new Compra(producto.getSelectedItem()
					.toString().toString(), Float.valueOf(numeroUnidades
					.getText().toString()),
					new SimpleDateFormat("dd/MM/yyyy").parse(fechaCompra
							.getText().toString()));

			getHelper().getCompraDAO().create(miNuevaCompra);

			// ((Arias) getApplication()).getMisCompras().add(miNuevaCompra);

			Toast.makeText(
					this,
					getString(R.string.anadiendo_compra,
							miNuevaCompra.getNombreProducto()),
					Toast.LENGTH_SHORT).show();

			final Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
		} catch (final NumberFormatException e) {
			Log.e(Arias.APLICACION, e.getMessage());
			e.printStackTrace();
			Toast.makeText(this, R.string.error_numero_unidades_entero_decimal,
					Toast.LENGTH_LONG).show();
		} catch (final ParseException e) {
			Log.e(Arias.APLICACION, e.getMessage());
			e.printStackTrace();
			Toast.makeText(this, R.string.error_fecha_compra_fecha_con_formato,
					Toast.LENGTH_LONG).show();
		}
	}
}
