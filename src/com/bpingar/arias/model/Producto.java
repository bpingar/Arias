package com.bpingar.arias.model;

import java.io.Serializable;

public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tipo tipo;
	private String producto;

	public Producto(final Tipo tipo, final String producto) {
		super();
		this.tipo = tipo;
		this.producto = producto;
	}

	@Override
	public String toString() {
		return tipo.getTipo() + " - " + producto;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(final Tipo tipo) {
		this.tipo = tipo;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(final String producto) {
		this.producto = producto;
	}

	public static Producto[] getListadoProductos() {
		return new Producto[] {
				new Producto(new Tipo(new Categoria(), new Variedad(),
						new Marca(), "Angulo Burgos Tradicional Bipack"),
						"2x150 g"),
				new Producto(new Tipo(new Categoria(), new Variedad(),
						new Marca(), "Boffard Reserva"), "1 kg"),
				new Producto(new Tipo(new Categoria(), new Variedad(),
						new Marca(), "Mantequilla Arias Tradicional"), "250g") };
	}
}
