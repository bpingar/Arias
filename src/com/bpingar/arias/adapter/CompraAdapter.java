package com.bpingar.arias.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.bpingar.arias.R;
import com.bpingar.arias.R.id;
import com.bpingar.arias.R.layout;
import com.bpingar.arias.model.Compra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CompraAdapter extends ArrayAdapter<Compra> {

	public CompraAdapter(final Context context, final int textViewResourceId,
			final List<Compra> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.compra_item, null);
		}

		final Compra compra = getItem(position);

		final TextView nombreProducto = (TextView) convertView
				.findViewById(R.id.nombre_producto_item);
		nombreProducto.setText(compra.getNombreProducto());

		final TextView numeroUnidades = (TextView) convertView
				.findViewById(R.id.numero_unidades_item);
		numeroUnidades.setText(compra.getNumeroUnidades().toString());

		final TextView fechaCompra = (TextView) convertView
				.findViewById(R.id.fecha_compra_item);
		fechaCompra.setText(new SimpleDateFormat("dd/MM/yyyy").format(compra
				.getFechaCompra()));

		return convertView;
	}
}
