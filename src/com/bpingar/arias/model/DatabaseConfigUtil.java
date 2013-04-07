package com.bpingar.arias.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	private static final Class<?>[] classes = new Class[] { Compra.class,
			Usuario.class, Marca.class, };

	public static void main(final String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
