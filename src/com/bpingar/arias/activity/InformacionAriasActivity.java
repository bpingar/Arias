package com.bpingar.arias.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.bpingar.arias.R;

public class InformacionAriasActivity extends MenuActivity {

	protected static final int _USUARIO_REGISTRADO = 2;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacion_arias);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_arias:
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
