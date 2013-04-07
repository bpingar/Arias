package com.bpingar.arias.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.field.DatabaseField;

public class Marca implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(id = true)
	private Long id;
	@DatabaseField
	private boolean activo;
	@DatabaseField
	private String marca;
	@DatabaseField
	private String descripcion;

	public Marca() {
		super();
	}

	public Marca(final Long id, final String marca, final String descripcion) {
		this.id = id;
		activo = true;
		this.marca = marca;
		this.descripcion = descripcion;
	}

	public Marca(final String marca, final String descripcion) {
		activo = true;
		this.marca = marca;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(final String marca) {
		this.marca = marca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return marca;
	}

	public static List<Marca> ITEMS = new ArrayList<Marca>();
	public static Map<Long, Marca> ITEM_MAP = new HashMap<Long, Marca>();

	static {
		addItem(new Marca((long) 1, "Burgo de Arias", "1"));
		addItem(new Marca((long) 2, "Mantequilla Arias", "2"));
		addItem(new Marca((long) 3, "Angulo Queso de Autor", "3"));
		addItem(new Marca((long) 5, "San Millán", "5"));
		addItem(new Marca((long) 6, "Casa del Campo", "6"));
		addItem(new Marca((long) 7, "La Cabaña", "7"));
		addItem(new Marca((long) 8, "Boffard", "8"));
		addItem(new Marca((long) 9, "Flor de Ronda", "9"));
		addItem(new Marca((long) 11, "Kidiboo", "11"));
		addItem(new Marca((long) 12, "Coeur de Lion", "12"));
		addItem(new Marca((long) 13, "Caprice Des Dieux", "13"));
		addItem(new Marca((long) 15, "Fol Epi", "15"));
		addItem(new Marca((long) 16, "Chamois d´Or", "16"));
		addItem(new Marca((long) 17, "Saint Agur", "17"));
		addItem(new Marca((long) 18, "Rambol", "18"));
		addItem(new Marca((long) 19, "Gabriel Coulet", "19"));
		addItem(new Marca((long) 22, "Chaumes", "22"));
		addItem(new Marca(
				(long) 23,
				"Saint Albray",
				"Queso generoso para descubrir las buenas cosas de la vida a aquellos que amamos.\nBajo su corteza rosada y su forma original, descubrimos un gusto sabroso con una textura cremosa que tendrás el placer de compartir en familia o con invitados.\nDescubre El Placer Fundente.\nSaint Albray, la flor de los quesos."));
	}

	private static void addItem(final Marca item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}
}
