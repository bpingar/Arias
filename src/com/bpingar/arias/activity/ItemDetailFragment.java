package com.bpingar.arias.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bpingar.arias.R;
import com.bpingar.arias.model.Marca;

public class ItemDetailFragment extends Fragment {

	public static final String ARG_ITEM_ID = "item_id";

	private Marca marcaItem;

	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			marcaItem = Marca.ITEM_MAP.get(getArguments().getLong(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);

		if (marcaItem != null) {
			((TextView) rootView.findViewById(R.id.item_detail))
					.setText(marcaItem.getId() + " - " + marcaItem.getMarca());
		}

		return rootView;
	}
}
