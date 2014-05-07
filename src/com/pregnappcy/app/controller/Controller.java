package com.pregnappcy.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pregnappcy.app.XL;
import com.pregnappcy.app.business.Messages;
import com.pregnappcy.app.business.User;
import com.pregnappcy.app.database.Ajustes;
import com.pregnappcy.app.database.DataBaseConnector;
import com.pregnappcy.app.database.Embarazo;

public class Controller implements IController {

	private static Controller the_;

	protected Controller() {
		the_ = this;
	}

	static public Controller the() {
		if (the_ == null)
			the_ = new Controller();
		return the_;
	}

	@Override
	public User checkUser(String email, String password) {
		ServerConnect sc = new ServerConnect();
		User u = null;
		try {
			JSONObject jsonObject = sc.checkUser(email, password);
			// Caso user/password incorrecto
			if (jsonObject.getInt("result") == 0) {
				u = new User();
				u.setMessage(jsonObject.getString("msg"));
			}
			// Caso usuario ok
			if (jsonObject.getInt("result") == 1) {
				u = new User(
						jsonObject.getJSONObject("usuario").getLong("id"),
						jsonObject.getJSONObject("usuario").getString("nombre"),
						jsonObject.getJSONObject("usuario").getString("email"));
				u.setMessage(jsonObject.getString("msg"));

				JSONArray listEmbarazos = jsonObject
						.getJSONArray("embarazos_sigue");
				List<Embarazo> embarazos = new ArrayList<Embarazo>();
				// Si sigue algœn embarazo, cogemos la info.
				if (listEmbarazos.length() > 0) {
					for (int i = 0; i < listEmbarazos.length(); i++) {
						Embarazo e = new Embarazo(i + 1);
						JSONObject object = listEmbarazos.getJSONObject(i);
						JSONArray listBebe = object.getJSONArray("bebe");
						XL.e("Bebes", listBebe.toString());
						e.setId_embarazo(object.getLong("id_embarazo"));
						e.setId_padre(object.getLong("id_padre"));
						e.setId_madre(object.getLong("id_madre"));
						e.setFUR(object.getString("FUR"));
						e.setNombre_padre(object.getString("nombre_padre"));
						e.setNombre_madre(object.getString("nombre_madre"));
						// aci el bucle!!º
						for (int j = 0; j < listBebe.length(); j++) {
							JSONObject nombre_bebe = listBebe.getJSONObject(j);
							XL.e("Nombre_bebe",
									nombre_bebe.getString("nombre_bebe"));
							XL.e("Sexo_bebe",
									nombre_bebe.getString("sexo_bebe"));
							e.addNombre_bebe(nombre_bebe
									.getString("nombre_bebe"));
							e.addSexo_bebe(nombre_bebe.getString("sexo_bebe"));
							if (e.getId() == 2) {
								e.addNombre_bebe("Joselito");
								e.addSexo_bebe("Ni–o");
							}
						}

						e.setFecha_nacimiento(object
								.getString("fecha_nacimiento"));
						e.setEstado(object.getInt("estado"));
						XL.e("Embarazo", e.toString());
						e.save();
						embarazos.add(e);

					}
					u.setEmbarazos(embarazos);
				}
				Ajustes a = new Ajustes(1);
				a.setEmail(u.getEmail());
				a.setId_usuario(u.getId());
				a.setNombre(u.getNombre());
				a.setLastUpdate(jsonObject.getString("lastUpdate"));
				a.save();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public User checkIfUserLogged() {
		User u = null;
		Ajustes a = new Ajustes(1);
		if (a != null) {
			// XL.e(a.getEmail(),a.getNombre() );
			if (a.getEmail() != null) {
				u = new User(a.getId_usuario(), a.getNombre(), a.getEmail());
				u.setEmbarazos(getEmbarazos());
			}
		}

		return u;
	}

	@Override
	public Object checkIfUpdates() {
		ServerConnect sc = new ServerConnect();
		Ajustes a = new Ajustes(1);

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String lastUpdate = a.getLastUpdate();

		XL.e("LastUpdate antes del try", a.getLastUpdate());

		Messages msg = null;
		User u = null;
		try {
			Date miFecha = formatter.parse(lastUpdate);
			JSONObject jsonObject = sc.checkLastUpdate(miFecha);

			XL.e("Resultado de saber si hay actualizaci—n",
					String.valueOf(jsonObject.getInt("result")));
			// Error al actualizar
			if (jsonObject.getInt("result") == 0) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
				return msg;
			}
			// Actualizamos datos
			if (jsonObject.getInt("result") == 1) {
				u = new User(
						jsonObject.getJSONObject("usuario").getLong("id"),
						jsonObject.getJSONObject("usuario").getString("nombre"),
						jsonObject.getJSONObject("usuario").getString("email"));
				u.setMessage(jsonObject.getString("msg"));
				JSONArray listEmbarazos = jsonObject
						.getJSONArray("embarazos_sigue");
				List<Embarazo> embarazos = new ArrayList<Embarazo>();
				// Si sigue algœn embarazo, cogemos la info.
				if (listEmbarazos.length() > 0) {
					for (int i = 0; i < listEmbarazos.length(); i++) {
						Embarazo e = new Embarazo(i + 1);
						JSONObject object = listEmbarazos.getJSONObject(i);
						JSONArray listBebe = object.getJSONArray("bebe");
						XL.e("Bebes", listBebe.toString());

						e.setId_embarazo(object.getLong("id_embarazo"));
						e.setId_padre(object.getLong("id_padre"));
						e.setId_madre(object.getLong("id_madre"));
						e.setFUR(object.getString("FUR"));
						e.setNombre_padre(object.getString("nombre_padre"));
						e.setNombre_madre(object.getString("nombre_madre"));
						// aci el bucle!!º
						for (int j = 0; j < listBebe.length(); j++) {
							JSONObject nombre_bebe = listBebe.getJSONObject(j);
							XL.e("Nombre_bebe",
									nombre_bebe.getString("nombre_bebe"));
							XL.e("Sexo_bebe",
									nombre_bebe.getString("sexo_bebe"));
							e.addNombre_bebe(nombre_bebe
									.getString("nombre_bebe"));
							e.addSexo_bebe(nombre_bebe.getString("sexo_bebe"));
							if (e.getId() == 2) {
								e.addNombre_bebe("Joselito");
								e.addSexo_bebe("Ni–o");
							}
						}

						e.setFecha_nacimiento(object
								.getString("fecha_nacimiento"));
						e.setEstado(object.getInt("estado"));
						XL.e("Embarazo", e.toString());
						e.save();
						embarazos.add(e);

					}

					u.setEmbarazos(embarazos);
				}
				a.setEmail(u.getEmail());
				a.setId_usuario(u.getId());
				a.setNombre(u.getNombre());
				a.setLastUpdate(jsonObject.getString("lastUpdate"));
				XL.e("lastupdate fecha", a.getLastUpdate());
				a.save();
			}
			// Datos ya actualizados.
			if (jsonObject.getInt("result") == 2) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
				return msg;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public Object registrarUsuario(String email, String password, String nombre) {
		Messages msg = null;
		User u = null;
		ServerConnect sc = new ServerConnect();
		try {
			JSONObject jsonObject = sc
					.registrarUsuario(nombre, email, password);
			// Error
			if (jsonObject.getInt("result") == 0) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
				return msg;
			}
			// Resgistro correcto
			if (jsonObject.getInt("result") == 1) {
				u = new User(
						jsonObject.getJSONObject("usuario").getLong("id"),
						jsonObject.getJSONObject("usuario").getString("nombre"),
						jsonObject.getJSONObject("usuario").getString("email"));
				u.setMessage(jsonObject.getString("msg"));
				/*
				 * Revisar si queremos que al registrarse si todo es correcto,
				 * se guarde directamente en la BDA, o si por el contrario mejor
				 * volver a la pantalla de login y que inicie sesi—n con los
				 * datos.
				 */
				Ajustes a = new Ajustes(1);
				a.setEmail(u.getEmail());
				a.setId_usuario(u.getId());
				a.setNombre(u.getNombre());
				a.setLastUpdate(jsonObject.getString("lastUpdate"));
				a.save();
			}
			// Usuario ya registrado
			if (jsonObject.getInt("result") == 2) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
				return msg;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public List<Embarazo> getEmbarazos() {
		Embarazo e = new Embarazo(1);
		return e.getEmbarazos();
	}

	@Override
	public Messages checkEmail(String email) {
		ServerConnect sc = new ServerConnect();
		Messages msg = null;
		try {
			JSONObject jsonObject = sc.checkEmail(email);
			// Error
			if (jsonObject.getInt("result") == 0) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
			}
			// Email ok
			if (jsonObject.getInt("result") == 1) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
			}
			// Usuario ya registrado
			if (jsonObject.getInt("result") == 2) {
				msg = new Messages(jsonObject.getString("msg"),
						jsonObject.getInt("result"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public Object prueba() {
		ServerConnect sc = new ServerConnect();
		User u = null;
		try {
			JSONObject jsonObject = sc.prueba();
			// Caso user/password incorrecto
			if (jsonObject.getInt("result") == 0) {
				u = new User();
				u.setMessage(jsonObject.getString("msg"));
			}
			// Caso usuario ok
			if (jsonObject.getInt("result") == 1) {
				u = new User(
						jsonObject.getJSONObject("usuario").getLong("id"),
						jsonObject.getJSONObject("usuario").getString("nombre"),
						jsonObject.getJSONObject("usuario").getString("email"));
				u.setMessage(jsonObject.getString("msg"));

				JSONArray listEmbarazos = jsonObject
						.getJSONArray("embarazos_sigue");
				List<Embarazo> embarazos = new ArrayList<Embarazo>();
				// Si sigue algœn embarazo, cogemos la info.
				if (listEmbarazos.length() > 0) {
					for (int i = 0; i < listEmbarazos.length(); i++) {
						Embarazo e = new Embarazo(i + 1);
						JSONObject object = listEmbarazos.getJSONObject(i);
						JSONArray listBebe = object.getJSONArray("bebe");
						XL.e("Bebes", listBebe.toString());

						e.setId_embarazo(object.getLong("id_embarazo"));
						e.setId_padre(object.getLong("id_padre"));
						e.setId_madre(object.getLong("id_madre"));
						e.setFUR(object.getString("FUR"));
						e.setNombre_padre(object.getString("nombre_padre"));
						e.setNombre_madre(object.getString("nombre_madre"));
						// aci el bucle!!º
						for (int j = 0; j < listBebe.length(); j++) {
							JSONObject nombre_bebe = listBebe.getJSONObject(j);
							XL.e("Nombre_bebe",
									nombre_bebe.getString("nombre_bebe"));
							XL.e("Sexo_bebe",
									nombre_bebe.getString("sexo_bebe"));
							ArrayList<String> nombres = new ArrayList<String>();
							//nombres.add(
								//	nombre_bebe.getString("nombre_bebe"));
							e.addNombre_bebe(nombre_bebe.getString("nombre_bebe"));
							//e.setNombre_bebe(nombres);
							e.addSexo_bebe(nombre_bebe.getString("sexo_bebe"));
							
						}

						e.setFecha_nacimiento(object
								.getString("fecha_nacimiento"));
						e.setEstado(object.getInt("estado"));
						XL.e("Embarazo", e.toString());
						e.save();
						embarazos.add(e);

					}
					u.setEmbarazos(embarazos);
				}
				Ajustes a = new Ajustes(1);
				a.setEmail(u.getEmail());
				a.setId_usuario(u.getId());
				a.setNombre(u.getNombre());
				a.setLastUpdate(jsonObject.getString("lastUpdate"));
				a.save();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

}