package com.bpingar.arias.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private Long id;
	@DatabaseField
	private String nombreUsuario;

	public Usuario() {
		super();
	}

	public Usuario(final String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(final String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
