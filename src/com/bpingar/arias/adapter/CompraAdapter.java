package com.bpingar.arias.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bpingar.arias.R;
import com.bpingar.arias.model.Compra;

public class CompraAdapter extends ArrayAdapter<Compra> {

	public CompraAdapter(final Context context, final int textViewResourceId,
			final List<Compra> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = vi.inflate(R.layout.compra_item, null);
		}

		final Compra compra = getItem(position);

		final TextView nombreProducto = (TextView) row
				.findViewById(R.id.nombre_producto);
		nombreProducto.setText(compra.getNombreProducto());

		final TextView numeroUnidades = (TextView) row
				.findViewById(R.id.numero_unidades);
		numeroUnidades.setText(compra.getNumeroUnidades().toString());

		final TextView fechaCompra = (TextView) row
				.findViewById(R.id.fecha_compra);
		fechaCompra.setText(new SimpleDateFormat("dd/MM/yyyy").format(compra
				.getFechaCompra()));

		return row;
	}
}
