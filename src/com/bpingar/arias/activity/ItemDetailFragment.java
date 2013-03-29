package com.bpingar.arias.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

			final ImageView imagenMarcaView = (ImageView) rootView
					.findViewById(R.id.imagen_marca);
			imagenMarcaView
					.setTag("http://www.arias.es/public/imagenes/gamas/f118d115f92c1c793646e4ae2097a857.jpg");

			new DownloadImageTask().execute(imagenMarcaView);

			((TextView) rootView.findViewById(R.id.item_titulo))
					.setText(marcaItem.getId() + " - " + marcaItem.getMarca());
			((TextView) rootView.findViewById(R.id.item_descripcion))
					.setText("Queso generoso para descubrir las buenas cosas de la vida a aquellos que amamos.\nBajo su corteza rosada y su forma original, descubrimos un gusto sabroso con una textura cremosa que tendrás el placer de compartir en familia o con invitados.\nDescubre El Placer Fundente.\nSaint Albray, la flor de los quesos.");
		}

		return rootView;
	}

	private class DownloadImageTask extends AsyncTask<ImageView, Float, Bitmap> {
		ImageView imageView = null;

		@Override
		protected Bitmap doInBackground(final ImageView... imageViews) {
			// Toast.makeText(getActivity(), urls[0], Toast.LENGTH_SHORT);
			Log.e("Entra doInBackground", (String) imageViews[0].getTag());
			imageView = imageViews[0];
			return downloadFile((String) imageView.getTag());
		}

		@Override
		protected void onPostExecute(final Bitmap result) {
			Log.e("Entra onPostExecute", "bien");
			imageView.setImageBitmap(result);
			// imageView.setImageResource(R.drawable.logo_arias);
		}

	}

	Bitmap downloadFile(final String imageHttpAddress) {
		URL imageUrl = null;
		Bitmap loadedImage = null;
		try {
			Log.e("Entra downloadFile", imageHttpAddress);
			imageUrl = new URL(imageHttpAddress);
			final HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.connect();
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
			Log.e("Sale downloadFile", imageHttpAddress);
		} catch (final IOException e) {
			Toast.makeText(getActivity().getApplicationContext(),
					"Error cargando la imagen: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return loadedImage;
	}
}
