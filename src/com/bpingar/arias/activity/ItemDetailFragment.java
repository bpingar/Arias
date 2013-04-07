package com.bpingar.arias.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpingar.arias.R;
import com.bpingar.arias.database.DatabaseHelper;
import com.bpingar.arias.model.Marca;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class ItemDetailFragment extends Fragment implements OnClickListener {

	public static final String ARG_ITEM_ID = "item_id";
	private DatabaseHelper databaseHelper;
	private Marca marcaItem;

	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(getActivity()
					.getApplicationContext(), DatabaseHelper.class);
		}

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			marcaItem = databaseHelper.getMarcaDAO()
					.queryForEq("id", getArguments().getLong(ARG_ITEM_ID))
					.get(0);
			// marcaItem =
			// Marca.ITEM_MAP.get(getArguments().getLong(ARG_ITEM_ID));
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
			// imagenMarcaView.setImageResource(R.drawable.logo_arias);

			((TextView) rootView.findViewById(R.id.item_titulo))
					.setText(marcaItem.getId() + " - " + marcaItem.getMarca());
			((TextView) rootView.findViewById(R.id.item_descripcion))
					.setText(marcaItem.getDescripcion());

			final Button boton = (Button) rootView
					.findViewById(R.id.actualizar_marcas);
			boton.setOnClickListener(this);
		}

		return rootView;
	}

	private class DownloadImageTask extends AsyncTask<ImageView, Float, Bitmap> {
		ImageView imageView = null;

		@Override
		protected Bitmap doInBackground(final ImageView... imageViews) {
			Log.e("Entra doInBackground", (String) imageViews[0].getTag());
			imageView = imageViews[0];
			return downloadFile((String) imageView.getTag());
		}

		@Override
		protected void onPostExecute(final Bitmap result) {
			Log.e("Entra onPostExecute", "bien");
			imageView.setImageBitmap(result);
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

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.actualizar_marcas:
			new DownloadMarcasTask().execute("1");
			break;
		default:
			break;
		}
	}

	private class DownloadMarcasTask extends AsyncTask<String, Float, Integer> {

		@Override
		protected Integer doInBackground(final String... strings) {
			Log.e("1", "Entra en doInBackground");

			final Map<Long, Marca> marcasExtraidas = extraerMarcas();
			final List<Marca> marcasExistentesBD = databaseHelper.getMarcaDAO()
					.queryForAll();
			final Map<Long, Marca> marcasExistentes = new HashMap<Long, Marca>();
			for (final Marca marcaExistenteBD : marcasExistentesBD) {
				marcasExistentes
						.put(marcaExistenteBD.getId(), marcaExistenteBD);
			}

			Marca marcaExtraida;
			for (final Marca marcaExistente : marcasExistentes.values()) {
				if ((marcaExtraida = marcasExtraidas
						.get(marcaExistente.getId())) == null) {
					marcaExistente.setActivo(false);
				} else {
					marcaExistente.setActivo(true);
					marcaExistente.setMarca(marcaExtraida.getMarca());
					marcaExistente.setDescripcion(marcaExtraida
							.getDescripcion());
				}
				databaseHelper.getMarcaDAO().update(marcaExistente);
				Log.e("Actualizada marca: ", marcaExistente.getId().toString());
			}
			for (final Marca marcaExtraida2 : marcasExtraidas.values()) {
				if (marcasExistentes.get(marcaExtraida2.getId()) == null) {
					databaseHelper.getMarcaDAO().create(marcaExtraida2);
					Log.e("Insertada marca: ", marcaExtraida2.getId()
							.toString());
				}
			}

			Log.e("Entra doInBackground", "1");
			return 1;
		}

		private Map<Long, Marca> extraerMarcas() {
			final Map<Long, Marca> marcasExtraidas = new HashMap<Long, Marca>();
			List<Marca> marcasExtraidasURL;
			int i = 1;
			while (!(marcasExtraidasURL = extraerMarcasURL(obtenerMarcaURLPagina(i)))
					.isEmpty()) {
				for (final Marca marcaExtraida : marcasExtraidasURL) {
					marcasExtraidas.put(marcaExtraida.getId(), marcaExtraida);
				}
				i++;
			}
			return marcasExtraidas;
		}

		private URL obtenerMarcaURLPagina(final int pag) {
			final String URLBase = "http://www.arias.es/public/index.php?p=Marcas";
			try {
				if (pag == 1) {
					return new URL(URLBase);
				} else {
					return new URL(URLBase + "&pag=" + (pag + 1) + "&max=" + 6
							* pag);
				}
			} catch (final MalformedURLException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al construir la URL de marcas: "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			return null;
		}

		private List<Marca> extraerMarcasURL(final URL url) {
			HttpURLConnection conn;
			InputStream inputStream = null;
			final List<Marca> marcasExtraidas = new ArrayList<Marca>();
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				Log.e("2", "");
			} catch (final IOException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al conectarse a la URL de marcas: "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return marcasExtraidas;
			}
			try {
				inputStream = conn.getInputStream();
			} catch (final IOException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al conectarse a la URL de marcas: "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return marcasExtraidas;
			}
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			String s;
			final String URLBase = "http://www.arias.es/public";

			try {
				while ((s = br.readLine()) != null) {
					if (s.contains("<div class=\"marcas\">")) {
						Log.e("datos", s);
						while ((s = br.readLine().trim())
								.contains("<div class=\"marca\">")) {
							s = br.readLine().trim();
							Log.e("datos", s);
							final String marcaURL = s
									.substring(
											s.indexOf("a href=\"") + 8,
											s.indexOf("\"",
													s.indexOf("a href=\"") + 8));
							Log.e("marcaURL", marcaURL);
							final String id = marcaURL.substring(marcaURL
									.indexOf("id=") + 3);
							Log.e("id", id);
							String imagenURL = s.substring(s
									.indexOf("img src=\"") + 9, s.indexOf("\"",
									s.indexOf("img src=\"") + 9));
							Log.e("imagenURL", imagenURL);
							imagenURL = imagenURL.replaceFirst(".", URLBase);
							Log.e("imagenURL", imagenURL);
							final String nombreMarca = s.substring(s
									.indexOf(" de la marca ") + 13, s.indexOf(
									"\"", s.indexOf(" de la marca ") + 13));
							Log.e("nombreMarca", nombreMarca);
							final Marca marcaExtraida = new Marca(
									Long.parseLong(id), nombreMarca, "");
							actualizarInformacionMarca(
									marcaExtraida,
									new URL((URLBase + "/" + marcaURL).replace(
											"&amp;", "&")));
							marcasExtraidas.add(marcaExtraida);

						}
					}
				}
			} catch (final IOException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al extraer la informacion de  la URL de marcas: "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return null;
			}
			return marcasExtraidas;
		}

		private void actualizarInformacionMarca(final Marca marca,
				final URL urlMarca) {
			HttpURLConnection conn;
			InputStream inputStream = null;
			try {
				conn = (HttpURLConnection) urlMarca.openConnection();
				conn.connect();
				Log.e("2", "");
			} catch (final IOException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al conectarse a la URL de la marca"
								+ marca.getMarca() + ": " + e.getMessage(),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return;
			}
			try {
				inputStream = conn.getInputStream();
			} catch (final IOException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al conectarse a la URL de la marca"
								+ marca.getMarca() + ": " + e.getMessage(),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return;
			}
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			String s;
			String infoMarca = "";
			try {
				while ((s = br.readLine()) != null) {
					if (s.contains("<div class=\"descripcion_marca\">")) {
						do {
							infoMarca = infoMarca + s;
						} while (!(s = br.readLine()).contains("</div>"));
						infoMarca = infoMarca + s;
						infoMarca = infoMarca.replace(
								"<div class=\"descripcion_marca\">", "");
						infoMarca = infoMarca.replace("</div>", "");
						infoMarca = infoMarca.replaceAll("<br />", "\n");
						marca.setDescripcion(infoMarca);
					}
				}
			} catch (final IOException e) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Error al extraer la informacion de la URL de la marca"
								+ marca.getMarca() + ": " + e.getMessage(),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
				return;
			}
		}

		@Override
		protected void onPostExecute(final Integer result) {
			Log.e("Entra onPostExecute", result.toString());
		}
	}
}
