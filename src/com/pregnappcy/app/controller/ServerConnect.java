package com.pregnappcy.app.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pregnappcy.app.XL;
import com.pregnappcy.app.business.JSONParser;
import com.pregnappcy.app.business.RegistroEmbarazo;

public class ServerConnect implements IServerConnect {
	private JSONParser jsonParser;
	private String url = "";

	private String tag_registro = "registro";
	private String tag_login = "login";
	private String tag_lastUpdate = "lastUpdate";
	private String tag_seguir_embarazo = "seguir_embarazo";
	private String tag_registro_embarazo = "registro_embarazo";
	private String nombres_padre[] = { "Paco", "Juan", "Jose", "ComodínP" };
	private String nombres_madre[] = { "Francisca", "Juani", "Josefa",
			"ComodínM" };
	private String nombres_bebe[] = { "Paquito José", "Emiliano Juanito", "Josefina Seguismunda", "Cabra" };
	
	private String nombres_bebe_multiple [] = {"Luisito", "Anita", "Juanito"};
	private String sexo_bebe_multiple [] = {"Niño", "Niña"};
	private String prova[] = { "Paco1", "Juan1", "Jose1" };

	private String error_msg = "Se ha producido un error.";
	private String msg = "Operación realizada con éxito";
	private String msg_datos_ya_actualizados = "Los datos ya estan actualizados.";
	private String msg_no_sigues_emb = "¿Seguir embarazo?, o ¿Registrar tu embarazo?";
	private String msg_seguir_ok = "Petición correcta, a la espera de acptación";
	private String msg_seguir_error_email = "Email incorrecto";
	private String msg_error_registro = "Error interno del servidor.";
	private String msg_registro_ok = "Usuario registrado correctamente.";
	private String msg_registro_embarazo_ok = "Embarazo correctamente añadido.";
	private String msg_registro_embarazo_error = "Error al añadir embarazo.";

	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private String date_emb[] = { "07/12/2013", "03/12/2013", "05/12/2013",
			"07/03/2014" };

	// Error
	private int result_error = 0;
	// ok
	private int result_ok = 1;
	// Error
	private int result_error2 = 2;
	private int numero_embarazos_sigue=3;
	private int numero_bebes =1;

	private int id_usuario = 1;

	public static String nombre_usuario = "nombre_usuario";
	public static String email_usuario = "email";
	public static String id_embarazo = "id_embarazo";
	public static String id_padre = "id_padre";
	public static String id_madre = "id_madre";
	public static String estado = "estado";
	public static String FUR = "FUR";
	public static String fecha_nacimiento = "fecha_nacimiento";
	public static String nombre_padre = "nombre_padre";
	public static String nombre_madre = "nombre_madre";
	public static String nombre_bebe = "nombre_bebe";
	public static String sexo_bebe = "sexo_bebe";
	public static String lastUpdate = "13/04/2018";

	public ServerConnect() {
		jsonParser = new JSONParser();
		
	}
	
	@Override
	public JSONObject prueba(){
		
		JSONObject json = new JSONObject();
		try{
			json.put("tag", tag_login);
			json.put("result", result_ok);
			json.put("msg", msg);
			json.put("lastUpdate", lastUpdate);
	
			JSONObject jsonUsuario = new JSONObject();
			jsonUsuario.put("id", id_usuario);
			jsonUsuario.put("nombre", nombre_usuario);
			jsonUsuario.put("email", "mail");
			json.put("usuario", jsonUsuario);
	
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < numero_embarazos_sigue; i++) {
				JSONObject jsonEmbarazo = new JSONObject();
				jsonEmbarazo.put(id_embarazo, i);
				jsonEmbarazo.put(id_padre, i + 3);
				jsonEmbarazo.put(id_madre, i + 4);
				Date date = formatter.parse(date_emb[i]);
				jsonEmbarazo.put(FUR, formatter.parse(formatter.format(date)));
				jsonEmbarazo.put(nombre_padre, nombres_padre[i]);
				jsonEmbarazo.put(nombre_madre, nombres_madre[i]);
				
				if(i==0) numero_bebes =2;
				else numero_bebes =1;
				JSONArray jsonArrayBebe = new JSONArray();
					for(int j =0; j<numero_bebes; j++){
						JSONObject jsonNombreBebe = new JSONObject();
						//JSONObject jsonSexoBebe = new JSONObject();
						jsonNombreBebe.put(nombre_bebe, nombres_bebe_multiple[j]);
						jsonNombreBebe.put(sexo_bebe, sexo_bebe_multiple[j]);
						jsonArrayBebe.put(jsonNombreBebe);
						//jsonArrayBebe.put(jsonSexoBebe);
						jsonEmbarazo.put("bebe", jsonArrayBebe);
					}
				jsonEmbarazo.put(fecha_nacimiento, "");
				jsonEmbarazo.put(estado, 0);
				jsonArray.put(jsonEmbarazo);
			}
			json.put("embarazos_sigue", jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject registrarUsuario(String nombre, String email,
			String password) throws JSONException {
		/*
		 * List<NameValuePair> params = new ArrayList<NameValuePair>();
		 * params.add(new BasicNameValuePair("nombre_usuario", nombre));
		 */
		// jsonParser.getJSONFromUrl(url, params);
		JSONObject json = new JSONObject();
		json.put("tag", tag_registro);
		json.put("result", result_ok);
		json.put("msg", msg);
		json.put("lastUpdate", lastUpdate);
		JSONObject jsonUsuario = new JSONObject();
		jsonUsuario.put("id", id_usuario);
		jsonUsuario.put("nombre", nombre);
		jsonUsuario.put("email", email);
		json.put("usuario", jsonUsuario);
		return json;
	}

	@Override
	public JSONObject checkLastUpdate(Date miFecha) {
		JSONObject json = new JSONObject();
		try {
			// La fecha del server.
			Date serverDate = formatter.parse(lastUpdate);
			if (miFecha.before(serverDate)) {
				// hay que actualizar
				json.put("tag", tag_lastUpdate);
				json.put("result", result_ok);
				json.put("msg", msg);
				json.put("lastUpdate", lastUpdate);

				JSONObject jsonUsuario = new JSONObject();
				jsonUsuario.put("id", id_usuario);
				jsonUsuario.put("nombre", nombre_usuario);
				jsonUsuario.put("email", email_usuario);
				json.put("usuario", jsonUsuario);
				JSONArray jsonArray = new JSONArray();

				for (int i = 0; i < numero_embarazos_sigue; i++) {
					JSONObject jsonEmbarazo = new JSONObject();
					
					jsonEmbarazo.put(id_embarazo, i);
					jsonEmbarazo.put(id_padre, i + 3);
					jsonEmbarazo.put(id_madre, i + 4);
					Date date1 = formatter.parse(date_emb[i]);
					jsonEmbarazo.put(FUR,
							formatter.parse(formatter.format(date1)));
					jsonEmbarazo.put(nombre_padre, prova[i]);
					jsonEmbarazo.put(nombre_madre, prova[i]);
					
					if(i==0) numero_bebes =2;
					else numero_bebes =1;
					JSONArray jsonArrayBebe = new JSONArray();
						for(int j =0; j<numero_bebes; j++){
							JSONObject jsonNombreBebe = new JSONObject();
							//JSONObject jsonSexoBebe = new JSONObject();
							jsonNombreBebe.put(nombre_bebe, nombres_bebe_multiple[j]);
							jsonNombreBebe.put(sexo_bebe, sexo_bebe_multiple[j]);
							jsonArrayBebe.put(jsonNombreBebe);
							//jsonArrayBebe.put(jsonSexoBebe);
							jsonEmbarazo.put("bebe", jsonArrayBebe);
						}

					jsonEmbarazo.put(fecha_nacimiento, "");
					jsonEmbarazo.put(estado, 0);
					jsonArray.put(jsonEmbarazo);
				}
				json.put("embarazos_sigue", jsonArray);
			} else {
				if (miFecha.equals(serverDate)) {
					json.put("tag", tag_lastUpdate);
					json.put("result", result_error2);
					json.put("msg", msg_datos_ya_actualizados);
				} else {
					json.put("tag", tag_lastUpdate);
					json.put("result", result_error);
					json.put("msg", error_msg);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		XL.e("asd", json.toString());
		return json;
	}

	/**
	 * 
	 */
	@Override
	public JSONObject checkUser(String email, String password)
			throws JSONException, ParseException {
		/*
		 * List<NameValuePair> params = new ArrayList<NameValuePair>();
		 * params.add(new BasicNameValuePair("email", email)); params.add(new
		 * BasicNameValuePair("password", password)); JSONObject json =
		 * jsonParser.getJSONFromUrl(url, params);
		 */
		JSONObject json = new JSONObject();
		json.put("tag", tag_login);
		json.put("result", result_ok);
		json.put("msg", msg);
		json.put("lastUpdate", lastUpdate);

		JSONObject jsonUsuario = new JSONObject();
		jsonUsuario.put("id", id_usuario);
		jsonUsuario.put("nombre", nombre_usuario);
		jsonUsuario.put("email", email);
		json.put("usuario", jsonUsuario);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < numero_embarazos_sigue; i++) {
			JSONObject jsonEmbarazo = new JSONObject();
			jsonEmbarazo.put(id_embarazo, i);
			jsonEmbarazo.put(id_padre, i + 3);
			jsonEmbarazo.put(id_madre, i + 4);
			Date date = formatter.parse(date_emb[i]);
			jsonEmbarazo.put(FUR, formatter.parse(formatter.format(date)));
			jsonEmbarazo.put(nombre_padre, nombres_padre[i]);
			jsonEmbarazo.put(nombre_madre, nombres_madre[i]);
			
			if(i==0) numero_bebes =2;
			else numero_bebes =1;
			JSONArray jsonArrayBebe = new JSONArray();
				for(int j =0; j<numero_bebes; j++){
					JSONObject jsonNombreBebe = new JSONObject();
					//JSONObject jsonSexoBebe = new JSONObject();
					jsonNombreBebe.put(nombre_bebe, nombres_bebe_multiple[j]);
					jsonNombreBebe.put(sexo_bebe, sexo_bebe_multiple[j]);
					jsonArrayBebe.put(jsonNombreBebe);
					//jsonArrayBebe.put(jsonSexoBebe);
					jsonEmbarazo.put("bebe", jsonArrayBebe);
				}
			jsonEmbarazo.put(fecha_nacimiento, "");
			jsonEmbarazo.put(estado, 0);
			jsonArray.put(jsonEmbarazo);
		}
		json.put("embarazos_sigue", jsonArray);
		return json;
	}

	public JSONObject checkEmail(String email) {
		JSONObject json = new JSONObject();
		try {
			json.put("tag", tag_seguir_embarazo);
			json.put("result", result_ok);
			json.put("msg", msg_seguir_ok);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject registrarEmbarazo(RegistroEmbarazo re) {
		JSONObject json = new JSONObject();
		try {
			json.put("tag", tag_registro_embarazo);
			json.put("result", result_ok);
			json.put("msg", msg_registro_embarazo_ok);
			json.put("lastUpdate", lastUpdate);
			JSONArray jsonArray = new JSONArray();
			//deuria de saber si l'usuari només té un 'embarazo' o segueix a varios per a que 
			//d'esta forma faja array o sols el seu
			// Aixo ho puc saber gracies al numero embarazos sigue.
			//En cas que gaste el numbero embarazos sigue vol dir que ja seguia embarazos
			// i registra el seu
			
			//en cas que numero embarazo sigue siga =0 vol dir que es el que seu primer que va a registrar
			 //resumen controlar el numero embarazos sigue.
			
			//check user-> contar el array de embarzos
			//si usuario sigueEmbarzos array amb tots
			//si no array amb el seu nou i embarazos sigue +1
			numero_embarazos_sigue+=1;
			
			for (int i = 0; i < numero_embarazos_sigue; i++) {
				JSONObject jsonEmbarazo = new JSONObject();
				jsonEmbarazo.put(id_embarazo, i);
				jsonEmbarazo.put(id_padre, i + 3);
				jsonEmbarazo.put(id_madre, i + 4);
				Date date = formatter.parse(date_emb[i]);
				jsonEmbarazo.put(FUR, formatter.parse(formatter.format(date)));
				jsonEmbarazo.put(nombre_padre, nombres_padre[i]);
				jsonEmbarazo.put(nombre_madre, nombres_madre[i]);
				if(i==0) numero_bebes =2;
				else numero_bebes =1;
				JSONArray jsonArrayBebe = new JSONArray();
					for(int j =0; j<numero_bebes; j++){
						JSONObject jsonNombreBebe = new JSONObject();
						//JSONObject jsonSexoBebe = new JSONObject();
						jsonNombreBebe.put(nombre_bebe, nombres_bebe_multiple[j]);
						jsonNombreBebe.put(sexo_bebe, sexo_bebe_multiple[j]);
						jsonArrayBebe.put(jsonNombreBebe);
						//jsonArrayBebe.put(jsonSexoBebe);
						jsonEmbarazo.put("bebe", jsonArrayBebe);
					}

				jsonEmbarazo.put(fecha_nacimiento, "");
				jsonEmbarazo.put(estado, 0);
				jsonArray.put(jsonEmbarazo);
			}
			json.put("embarazos_sigue", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return json;
	}
}
