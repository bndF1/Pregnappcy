package com.pregnappcy.app.controller;

import java.util.List;

import com.pregnappcy.app.business.Messages;
import com.pregnappcy.app.business.User;
import com.pregnappcy.app.database.Embarazo;


public interface IController {
	public User checkUser(String emial, String password);
	public User checkIfUserLogged();
	public Object checkIfUpdates();
	public Object registrarUsuario(String email, String password, String nombre);
	public List<Embarazo> getEmbarazos();
	public Messages checkEmail(String email);
	public Object prueba();
}