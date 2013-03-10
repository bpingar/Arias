package com.bpingar.arias.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.bpingar.arias.R;

public class PreferenciasActivity extends MenuActivity {

	protected static final int _USUARIO_REGISTRADO = 2;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PreferenciasFragment())
				.commit();
	}

	public static class PreferenciasFragment extends PreferenceFragment {
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferencias);

		}
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_preferencias:
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
