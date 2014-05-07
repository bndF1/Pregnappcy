package com.pregnappcy.app;

import java.util.Calendar;

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

public class RegistoPadreMadreActivity extends FragmentActivity {

	private Button btnFur;
	private EditText fechaFur;

	private int year, day, month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registo_padre_madre);
		btnFur = (Button) findViewById(R.id.ButtonFUR);
		fechaFur = (EditText) findViewById(R.id.fechaFUR);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		setFechaFur(new StringBuilder()
				// Month is 0 based so add 1
				.append(day).append("/").append(month + 1).append("/")
				.append(year).append(" "));
		fechaFur.setKeyListener(null);
		btnFur.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getSupportFragmentManager(), "datePicker");

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registo_padre_madre, menu);
		return true;
	}

	public EditText getFechaFur() {
		return fechaFur;
	}

	public void setFechaFur(StringBuilder stringBuilder) {
		this.fechaFur.setText(stringBuilder);
	}

	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, getYear(),
					getMonth(), getDay());
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			Toast.makeText(
					getActivity(),
					String.valueOf(dayOfMonth + "-" + monthOfYear + "-" + year),
					Toast.LENGTH_LONG).show();
			// setFechaFur(String.valueOf(dayOfMonth+"/"+monthOfYear+"/"+year));
			setFechaFur(new StringBuilder()
					// Month is 0 based so add 1

					.append(dayOfMonth).append("/").append(monthOfYear + 1)
					.append("/").append(year).append(" "));
		}
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
