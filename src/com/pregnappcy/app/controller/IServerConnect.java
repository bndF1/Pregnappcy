package com.pregnappcy.app.controller;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public interface IServerConnect {
	
	public JSONObject checkUser(String email, String password) throws JSONException, ParseException;

	public JSONObject checkLastUpdate(Date date);

	public JSONObject registrarUsuario(String nombre, String email, String password)
			throws JSONException;

	public JSONObject prueba();

}
