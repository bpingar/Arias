package com.bpingar.arias.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Producto;

public class NuevaCompraActivity extends Activity implements OnClickListener {

	private ArrayAdapter<Producto> productos;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_compra);

		recoverUser();

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

	private void recoverUser() {
		final SharedPreferences preferences = getSharedPreferences(
				Arias.PREFERENCIAS, MODE_PRIVATE);

		final TextView tituloNuevaCompra = (TextView) findViewById(R.id.titulo_nueva_compra);
		tituloNuevaCompra.setText(getString(R.string.nueva_compra_usuario,
				preferences.getString(Arias.USUARIO, "-")));
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_nueva_compra, menu);
		return true;
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

			((Arias) getApplication()).getMisCompras().add(miNuevaCompra);

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
