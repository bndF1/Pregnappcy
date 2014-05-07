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

public class MainActivity extends Activity {

	private IController controller;
	private Button btnRegistro;
	private Button btnEntrar;
	private EditText email;
	private EditText password;
	private User myUser;
	private DataBaseConnector dbc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbc = new DataBaseConnector(this);
		dbc.open();
		controller = Controller.the();
		//scontroller.prueba();
		myUser = controller.checkIfUserLogged();
		// Comprobamos si hay usuario ya en local
		if (myUser != null) {
			XL.e("User logged: ", myUser.getEmail());
			Object update = controller.checkIfUpdates();
			if (update != null) {
				// Error al actualizar o datos ya actualizados.
				if (update instanceof Messages) {
					// Error al actualizar
					if (((Messages) update).getResult() == 0) {
						Toast.makeText(getApplicationContext(),
								((Messages) update).getMsg(), Toast.LENGTH_LONG)
								.show();
					}
					// Datos ya actualizados.
					if (((Messages) update).getResult() == 2) {
						Toast.makeText(getApplicationContext(),
								((Messages) update).getMsg(), Toast.LENGTH_LONG)
								.show();
					}
				}

				// Actualiza los datos.
				if (update instanceof User) {
					myUser = (User) update;
					Toast.makeText(getApplicationContext(),
							((User) update).getMessage(), Toast.LENGTH_LONG)
							.show();
				}

			}
			// Carga interfaz correspondiente.
			cargarInterfaz();
		}
		// Si no hay user en local, mostramos interfaz de login, o registro.
		else {
			setContentView(R.layout.activity_main);
			email = (EditText) findViewById(R.id.login_email);
			password = (EditText) findViewById(R.id.login_password);
			btnEntrar = (Button) findViewById(R.id.buttonEntrar);
			btnRegistro = (Button) findViewById(R.id.buttonRegistro);
			// Caso que ya est‡ registrado, hace login
			btnEntrar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Comprobamos que user es correcto
					myUser = controller.checkUser(email.getText().toString(),
							password.getText().toString());
					if (myUser != null) {
						if (myUser.getEmail() != null) {
							Toast.makeText(getApplicationContext(),
									myUser.getMessage(), Toast.LENGTH_LONG)
									.show();
							XL.e(myUser.getEmail(), myUser.getNombre());
							// Cargamos interfaz correspondiente.
							cargarInterfaz();
						} else {
							XL.e("Usuario dice", myUser.getMessage());
							// Indicamos error con toast.
							Toast.makeText(getApplicationContext(),
									myUser.getMessage(), Toast.LENGTH_LONG)
									.show();
						}
					}
				}
			});
			// Iniciamos proceso de registro.
			btnRegistro.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.this,
							RegistroActivity.class);
					startActivity(i);
				}
			});
		}
	}

	@Override
	public void onResume() {
		//dbc.open??
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * MŽtodo para cargar la interfaz correspondiente dependiendo de si el
	 * usuario sigue algœn embarazo o no. En caso que siga 0 embarazos muestra
	 * la interfaz en la que hay que indicar el email del padre o madre para
	 * realizar la petici—n de seguir embarazo. En caso contrario cargamos la
	 * lista con los embarazos que sigue.
	 */
	private void cargarInterfaz() {
		
		/*XL.e("Que tenemos en la BDA", controller.getEmbarazos().get(2).getNombre_padre());
		XL.e("Usuario sigue", String.valueOf(myUser.getEmbarazos().size()));*/
		
		for (int i = 0; i < myUser.getEmbarazos().size(); i++)
			XL.e("Embarazo [" + String.valueOf(i) + "]: ",
					String.valueOf(myUser.getEmbarazos().get(i).getNombre_padre()));
		
		if (myUser.getEmbarazos().size() == 0) {
			// Cargar interfaz para seguir embarazos
			Intent i = new Intent(getApplicationContext(),
					SeguirEmbarazos.class);
			startActivity(i);
			dbc.close();
	
		} else {
			// Cargar interfaz de los embarazos que sigue (La lista)
			Intent i = new Intent(getApplicationContext(),
					MostrarEmbarazos.class);
			startActivity(i);
			dbc.close();
		}
	}

}
