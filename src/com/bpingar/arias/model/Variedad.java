package com.bpingar.arias.model;

import java.io.Serializable;

public class Variedad implements Serializable {

	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private String variedad;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

	public String getVariedad() {
		return variedad;
	}

	public void setVariedad(final String variedad) {
		this.variedad = variedad;
	}
}
