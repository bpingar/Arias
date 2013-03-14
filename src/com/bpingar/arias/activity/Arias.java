package com.bpingar.arias.activity;

import android.app.Application;

import com.bpingar.arias.model.Usuario;

public class Arias extends Application {
	public static final String USUARIO = "usuario";
	public static final String PREFERENCIAS = "com.bpingar.arias.preferencias";
	public static final String APLICACION = "Arias";
	public static final String COMPRA = "compra";

	private Usuario usuario = new Usuario();
	private boolean saludoMostrado = false;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(final Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isSaludoMostrado() {
		return saludoMostrado;
	}

	public void setSaludoMostrado(final boolean saludoMostrado) {
		this.saludoMostrado = saludoMostrado;
	}
}
