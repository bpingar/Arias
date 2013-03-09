package com.bpingar.arias.model;

import java.io.Serializable;

public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;
	private String marca;

	public String getMarca() {
		return marca;
	}

	public void setMarca(final String marca) {
		this.marca = marca;
	}
}
