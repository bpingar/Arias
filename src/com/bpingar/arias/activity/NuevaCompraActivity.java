package com.bpingar.arias.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.model.Compra;

public class NuevaCompraActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_compra);

		final Button boton = (Button) findViewById(R.id.guardar_compra);
		boton.setOnClickListener(this);
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

			final EditText nombreProducto = (EditText) findViewById(R.id.nombre_producto);
			final EditText numeroUnidades = (EditText) findViewById(R.id.numero_unidades);
			final EditText fechaCompra = (EditText) findViewById(R.id.fecha_compra);

			try {
				final Compra miNuevaCompra = new Compra(nombreProducto
						.getText().toString(), Float.valueOf(numeroUnidades
						.getText().toString()), new SimpleDateFormat(
						"dd/MM/yyyy").parse(fechaCompra.getText().toString()));
				final Intent intent = new Intent();
				intent.putExtra(Arias.COMPRA, miNuevaCompra);
				setResult(RESULT_OK, intent);
				finish();
			} catch (final NumberFormatException e) {
				Log.e(Arias.APLICACION, e.getMessage());
				e.printStackTrace();
				Toast.makeText(this,
						R.string.error_numero_unidades_entero_decimal,
						Toast.LENGTH_LONG).show();
			} catch (final ParseException e) {
				Log.e(Arias.APLICACION, e.getMessage());
				e.printStackTrace();
				Toast.makeText(this,
						R.string.error_fecha_compra_fecha_con_formato,
						Toast.LENGTH_LONG).show();
			}

			break;

		default:
			break;
		}
	}

}
