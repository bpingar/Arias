package com.bpingar.arias.model;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private Long id;
	@DatabaseField
	private String nombreProducto;
	@DatabaseField
	private Float numeroUnidades;
	@DatabaseField
	private Date fechaCompra;

	public Compra() {
		super();
	}

	public Compra(final String nombreProducto, final Float numeroUnidades,
			final Date fechaCompra) {
		this.nombreProducto = nombreProducto;
		this.numeroUnidades = numeroUnidades;
		this.fechaCompra = fechaCompra;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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
