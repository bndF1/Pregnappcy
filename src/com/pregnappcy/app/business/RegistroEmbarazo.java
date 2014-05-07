package com.pregnappcy.app.business;

public class RegistroEmbarazo {
	
	
	private String nombre_usuario;
	private String email;
	private String FUR;
	private long id_usuario;
	
	private String nombre_bebe;

	public RegistroEmbarazo(String nombre_usuario, String email, String fUR,
			long id_usuario, String nombre_bebe) {
		super();
		this.nombre_usuario = nombre_usuario;
		this.email = email;
		FUR = fUR;
		this.id_usuario = id_usuario;
		this.nombre_bebe = nombre_bebe;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFUR() {
		return FUR;
	}

	public void setFUR(String fUR) {
		FUR = fUR;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre_bebe() {
		return nombre_bebe;
	}

	public void setNombre_bebe(String nombre_bebe) {
		this.nombre_bebe = nombre_bebe;
	}
	
	

}
