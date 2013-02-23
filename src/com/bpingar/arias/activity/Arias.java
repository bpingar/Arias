package com.bpingar.arias.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.bpingar.arias.model.Compra;

public class Arias extends Application {
	public static final String USUARIO = "usuario";
	public static final String PREFERENCIAS = "preferencias";
	public static final String APLICACION = "Arias";
	public static final String COMPRA = "compra";

	private final List<Compra> misCompras = new ArrayList<Compra>();

	public List<Compra> getMisCompras() {
		return misCompras;
	}
}
