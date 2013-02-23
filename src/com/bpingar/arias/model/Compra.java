package com.bpingar.arias.model;

import java.io.Serializable;
import java.util.Date;

public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombreProducto;
	private Float numeroUnidades;
	private Date fechaCompra;

	public Compra(final String nombreProducto, final Float numeroUnidades,
			final Date fechaCompra) {
		this.nombreProducto = nombreProducto;
		this.numeroUnidades = numeroUnidades;
		this.fechaCompra = fechaCompra;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(final String numeroUnidades) {
		nombreProducto = numeroUnidades;
	}

	public Float getNumeroUnidades() {
		return numeroUnidades;
	}

	public void setNumeroUnidades(final Float numeroUnidades) {
		this.numeroUnidades = numeroUnidades;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(final Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
}
