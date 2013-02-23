package com.bpingar.arias.model;

public class Tipo {
	private Categoria categoria;
	private Variedad variedad;
	private Marca marca;
	private String tipo;

	public Tipo(final Categoria categoria, final Variedad variedad,
			final Marca marca, final String tipo) {
		super();
		this.categoria = categoria;
		this.variedad = variedad;
		this.marca = marca;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

	public Variedad getVariedad() {
		return variedad;
	}

	public void setVariedad(final Variedad variedad) {
		this.variedad = variedad;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(final Marca marca) {
		this.marca = marca;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}
}
