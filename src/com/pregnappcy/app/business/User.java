package com.pregnappcy.app.business;

import java.util.ArrayList;
import java.util.List;

import com.pregnappcy.app.database.Ajustes;
import com.pregnappcy.app.database.Embarazo;

public class User {

	//id usuario
	private long id;
	private String nombre;
	private String email;
	private List<Embarazo> listEmbarazos;
	private String message;
	//private String lastUpdate;
	private Ajustes setting;
	
	public User (long id, String nombre, String email){
		this.id = id;
		this.nombre=nombre;
		this.email = email;
		this.listEmbarazos = new ArrayList<Embarazo>();
	}
	public User(){
		super();
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	public List<Embarazo> getEmbarazos(){
		return this.listEmbarazos;
	}
	public void setEmbarazos(List<Embarazo> listEmbarazos){
		this.listEmbarazos=listEmbarazos;
	}
	public void addEmbarazo(Embarazo e){
		this.listEmbarazos.add(e);
	}
	public void deleteEmbarazo(Embarazo e){
		this.listEmbarazos.remove(e);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String msg) {
		this.message = msg;
	}
	
	
}
