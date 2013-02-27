package com.bpingar.arias.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bpingar.arias.R;
import com.bpingar.arias.model.Compra;
import com.bpingar.arias.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "Arias.db";
	private static final int DATABASE_VERSION = 3;

	private RuntimeExceptionDao<Compra, Integer> compraDAO = null;
	private RuntimeExceptionDao<Usuario, Integer> usuarioDAO = null;

	public DatabaseHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	@Override
	public void onCreate(final SQLiteDatabase db,
			final ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Compra.class);
			TableUtils.createTable(connectionSource, Usuario.class);
		} catch (final SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db,
			final ConnectionSource connectionSource, final int oldVersion,
			final int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Compra.class, true);
			TableUtils.dropTable(connectionSource, Usuario.class, true);
			onCreate(db, connectionSource);
		} catch (final java.sql.SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<Compra, Integer> getCompraDAO() {
		if (compraDAO == null) {
			compraDAO = getRuntimeExceptionDao(Compra.class);
		}
		return compraDAO;
	}

	public RuntimeExceptionDao<Usuario, Integer> getUsuarioDAO() {
		if (usuarioDAO == null) {
			usuarioDAO = getRuntimeExceptionDao(Usuario.class);
		}
		return usuarioDAO;
	}

	@Override
	public void close() {
		super.close();
		compraDAO = null;
		usuarioDAO = null;
	}
}
