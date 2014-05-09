package com.pregnappcy.app;

import java.util.ArrayList;
import java.util.List;

import adapters.EmbarazoAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pregnappcy.app.business.User;
import com.pregnappcy.app.controller.Controller;
import com.pregnappcy.app.controller.IController;
import com.pregnappcy.app.database.DataBaseConnector;
import com.pregnappcy.app.database.Embarazo;

public class MostrarEmbarazos extends Activity {

	private IController controller;
	private ListView listEmbazaros;
	private EmbarazoAdapter listEmbarazoAdapter;
	private User myUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_embarazos);
		DataBaseConnector dbc = new DataBaseConnector(this);
		dbc.open();
		
		controller = Controller.the();
		myUser = controller.checkIfUserLogged();
		//myUser = (User) controller.checkIfUpdates();
		listEmbazaros = (ListView) findViewById(R.id.listViewEmbarazos);
		mostrarEmbarazos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_lista_embarazos, menu);
		return true;
	}

	private void mostrarEmbarazos() {
		//info = myUser.getEmbarazos();
		List<Embarazo> info = new ArrayList<Embarazo>();
		info = myUser.getEmbarazos();
		XL.e(String.valueOf(myUser.getEmbarazos()), "@#@ emb");
		listEmbarazoAdapter = new EmbarazoAdapter(MostrarEmbarazos.this,
				(ArrayList<Embarazo>) info);
		listEmbazaros.setAdapter(listEmbarazoAdapter);
		listEmbazaros.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				Embarazo e = (Embarazo) listEmbazaros.getItemAtPosition(position);
				Intent i = new Intent(getApplicationContext(), MenuActivity.class);
				startActivity(i);
				
			}
		});
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("ÀSalir de la aplicaci—n?")
				.setMessage("ÀSeguro que quieres salir?")
				
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MostrarEmbarazos.super.onBackPressed();
						
					}
				}).setNegativeButton(android.R.string.no, null).show();
	}
}
