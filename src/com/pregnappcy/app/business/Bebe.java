package com.pregnappcy.app.business;

public class Bebe {
	
	public Bebe(String nombre, String sexo) {
		super();
		this.nombre = nombre;
		this.sexo = sexo;
	}
	private String nombre;
	private String sexo;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	

}
