package com.pregnappcy.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pregnappcy.app.business.Messages;
import com.pregnappcy.app.business.User;
import com.pregnappcy.app.controller.Controller;
import com.pregnappcy.app.controller.IController;
import com.pregnappcy.app.database.DataBaseConnector;

public class RegistroActivity extends Activity {

	private IController controller;
	private EditText email;
	private EditText password;
	private EditText nombre;
	private Button btnRegistro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		DataBaseConnector dbc = new DataBaseConnector(this);
		dbc.open();
		controller = Controller.the();
		email = (EditText) findViewById(R.id.registro_email);
		password = (EditText) findViewById(R.id.registro_password);
		nombre = (EditText) findViewById(R.id.registro_nombre);
		btnRegistro = (Button) findViewById(R.id.buttonRegistro);

		btnRegistro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Object o = controller.registrarUsuario(email.getText()
						.toString(), password.getText().toString(), nombre
						.getText().toString());
				if (o instanceof Messages) {
					XL.e("Object dice", ((Messages) o).getMsg());
					Toast.makeText(
							getApplicationContext(),
							"Se ha producido un error durante el registro: '"
									+ ((Messages) o).getMsg() + "'",
							Toast.LENGTH_LONG).show();

				}
				if (o instanceof User) {
					User myUser = (User) o;
					XL.e("Usuario dice", myUser.getMessage());
					Toast.makeText(getApplicationContext(),
							"ÁUsuario correctamente registrado!",
							Toast.LENGTH_LONG).show();
					if (myUser.getEmbarazos().size() == 0) {
						// Cargar interfaz para seguir embarazos
						Intent i = new Intent(getApplicationContext(),
								SeguirEmbarazos.class);
						startActivity(i);
					} else {
						// Cargar interfaz de los embarazos que sigue (La lista)
						startActivity(new Intent(getApplicationContext(),
								MostrarEmbarazos.class));
					}

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

}
