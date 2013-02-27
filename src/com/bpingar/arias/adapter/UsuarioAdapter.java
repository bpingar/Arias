package com.bpingar.arias.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bpingar.arias.R;
import com.bpingar.arias.model.Usuario;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

	public UsuarioAdapter(final Context context, final int textViewResourceId,
			final List<Usuario> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = vi.inflate(R.layout.usuario_item, null);
		}

		final Usuario usuario = getItem(position);

		final TextView nombreUsuario = (TextView) row
				.findViewById(R.id.nombre_usuario);
		nombreUsuario.setText(usuario.getNombreUsuario());

		final TextView idUsuario = (TextView) row.findViewById(R.id.id_usuario);
		idUsuario.setText(usuario.getId().toString());

		return row;
	}

}
