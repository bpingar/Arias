package com.bpingar.arias.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marca implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String marca;

	public Marca() {
		super();
	}

	public Marca(final Long id, final String marca) {
		this.id = id;
		this.marca = marca;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(final String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return marca;
	}

	public static List<Marca> ITEMS = new ArrayList<Marca>();
	public static Map<Long, Marca> ITEM_MAP = new HashMap<Long, Marca>();

	static {
		addItem(new Marca((long) 1, "Burgo de Arias"));
		addItem(new Marca((long) 2, "Mantequilla Arias"));
		addItem(new Marca((long) 3, "Angulo Queso de Autor"));
		addItem(new Marca((long) 5, "San Millán"));
		addItem(new Marca((long) 6, "Casa del Campo"));
		addItem(new Marca((long) 7, "La Cabaña"));
		addItem(new Marca((long) 8, "Boffard"));
		addItem(new Marca((long) 9, "Flor de Ronda"));
		addItem(new Marca((long) 11, "Kidiboo"));
		addItem(new Marca((long) 12, "Coeur de Lion"));
		addItem(new Marca((long) 13, "Caprice Des Dieux"));
		addItem(new Marca((long) 15, "Fol Epi"));
		addItem(new Marca((long) 16, "Chamois d´Or"));
		addItem(new Marca((long) 17, "Saint Agur"));
		addItem(new Marca((long) 18, "Rambol"));
		addItem(new Marca((long) 19, "Gabriel Coulet"));
		addItem(new Marca((long) 22, "Chaumes"));
		addItem(new Marca((long) 23, "Saint Albray"));
	}

	private static void addItem(final Marca item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}
}
