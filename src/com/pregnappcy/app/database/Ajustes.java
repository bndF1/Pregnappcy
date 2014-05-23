package com.pregnappcy.app.database;

import android.database.Cursor;

import com.pregnappcy.app.XL;

public class Ajustes {
	private long id;
	private long id_usuario;
	private String nombre;
	private String email;
	private String lastUpdate;
	private boolean nuevo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public Ajustes(Cursor cursor) {
		setAtributes(cursor);
	}

	private void setAtributes(Cursor cursor) {

		if (cursor != null) {
			this.id_usuario = cursor.getLong(cursor
					.getColumnIndex(DataBaseConnector.KEY_ID_USUARIO));
			this.email = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_EMAIL));
			this.nombre = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_NOMBRE_USUARIO));
			this.lastUpdate = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_UPDATE));
			nuevo = false;
		}
	}

	public Ajustes(long id) {
		Cursor c = DataBaseConnector.dbc().getAjustesWhereId(
				DataBaseConnector.KEY_ID + " = " + id);
		if (c != null) {
			XL.e("[Ajustes]ÊEl cursor vale: ", String.valueOf(c.getLong(c
					.getColumnIndex(DataBaseConnector.KEY_ID))));
		} else
			nuevo = true;
		setAtributes(c);
		if (c != null)
			c.close();
	}

	public void save() {
		if (nuevo)
			id = create();
		else
			update();
	}

	private long create() {
		return DataBaseConnector.dbc().insertInAjustes(
				getId_usuario(), getEmail(), getNombre(), getLastUpdate());
	}

	private boolean update() {
		return DataBaseConnector.dbc().updateInAjustes(
				getId_usuario(), getEmail(), getNombre(), getLastUpdate());

	}

}
