package com.bpingar.arias.model;

import java.io.Serializable;

public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(final String categoria) {
		this.categoria = categoria;
	}
}
