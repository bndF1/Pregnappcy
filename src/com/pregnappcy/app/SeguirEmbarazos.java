package com.pregnappcy.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.pregnappcy.app.business.Messages;
import com.pregnappcy.app.controller.Controller;
import com.pregnappcy.app.controller.IController;
import com.pregnappcy.app.database.DataBaseConnector;

public class SeguirEmbarazos extends FragmentActivity {

	private IController controller;
	private Button btnEnviarPeticion;
	private Button buttonRegistrarEmb;
	private EditText editTextEmail;
	private String FUR;
	private DatePicker datePicker;
	private int year, day, month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataBaseConnector dbc = new DataBaseConnector(this);
		dbc.open();
		controller = Controller.the();
		setContentView(R.layout.activity_seguir_embarazos);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail_pa_ma);
		buttonRegistrarEmb = (Button) findViewById(R.id.buttonRegistrarEmbarazo);
		btnEnviarPeticion = (Button) findViewById(R.id.buttonEnviar);
		btnEnviarPeticion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!editTextEmail.getText().toString().isEmpty()) {
					Messages msg = controller.checkEmail(editTextEmail
							.getText().toString());
					Toast.makeText(getApplicationContext(), msg.getMsg(),
							Toast.LENGTH_LONG).show();
					if (msg.getResult() == 1) {
						// Notificamos al owner del embarazo.
						notificarPeticion(editTextEmail.getText().toString());
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Introduzaca un emial v‡lido.", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		buttonRegistrarEmb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seguir_embarazos, menu);
		return true;
	}

	private void notificarPeticion(String email) {
		// Supuestamente enviar un email al padre o madre.
		XL.e("Enviando petici—n a", email);
	}

	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			/*
			 * final Calendar c = Calendar.getInstance(); int year =
			 * c.get(Calendar.YEAR); int month = c.get(Calendar.MONTH); int day
			 * = c.get(Calendar.DAY_OF_MONTH); // Create a new instance of
			 * DatePickerDialog and return it
			 */
			int year = datePicker.getYear();
			int month = datePicker.getMonth();
			int day = datePicker.getDayOfMonth();
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					getActivity(), this, year, month, day);
			datePickerDialog.setTitle("Confirma la FUR.");

			return datePickerDialog;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
					"dd/MM/yyyy");
			month+=1;
			FUR = String.valueOf(day + "/" + month + "/" + year);
			/*Date fecha = null;
			try {

				fecha = formatoDelTexto.parse(strFecha);
				FUR = formatoDelTexto.format(fecha);

			} catch (ParseException ex) {

				ex.printStackTrace();

			}*/
			
			XL.e("LA FECHA", FUR);
			registrarEmbarazo();

		}
	}
	
	public void registrarEmbarazo(){
		//controller.registrarEmbarazo(FUR);
	}
	public int getYear() {
		return year;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}
}
