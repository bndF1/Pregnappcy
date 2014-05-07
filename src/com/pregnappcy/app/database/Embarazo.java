package com.pregnappcy.app.database;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.pregnappcy.app.XL;
import com.pregnappcy.app.business.Bebe;

/**
 * 
 * @author bnd360
 * 
 */
public class Embarazo {
	private long id;
	private long id_embarazo;
	private long id_padre;
	private long id_madre;
	private String FUR;
	private String nombre_padre;
	private String nombre_madre;
	private ArrayList<Bebe> bebes;
	private ArrayList<String> nombres_bebe = new ArrayList<String>();
	private ArrayList<String> sexo_bebe = new ArrayList<String>();
	private String fecha_nacimiento;
	private String email_owner;
	//private Date date;
	// 0 para embarazo, 1 para bebé
	private int estado = 0;
	private boolean nuevo;
	private boolean multiple;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFUR() {
		return FUR;
	}

	public void setFUR(String fUR) {
		FUR = fUR;
	}

	public ArrayList<String> getSexo_bebe() {
		return sexo_bebe;
	}
	public void addSexo_bebe(String sexo_bebe){
		this.sexo_bebe.add(sexo_bebe);
	}
	public long getId_embarazo() {
		return id_embarazo;
	}

	public void setId_embarazo(long id_embarazo) {
		this.id_embarazo = id_embarazo;
	}

	public long getId_padre() {
		return id_padre;
	}

	public void setId_padre(long id_padre) {
		this.id_padre = id_padre;
	}

	public long getId_madre() {
		return id_madre;
	}

	public void setId_madre(long id_madre) {
		this.id_madre = id_madre;
	}

	public String getNombre_padre() {
		return nombre_padre;
	}

	public void setNombre_padre(String nombre_padre) {
		this.nombre_padre = nombre_padre;
	}

	public String getNombre_madre() {
		return nombre_madre;
	}

	public void setNombre_madre(String nombre_madre) {
		this.nombre_madre = nombre_madre;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public ArrayList<String> getNombre_bebe() {
		return nombres_bebe;
	}

	public void addNombre_bebe(String nombre_bebe){
		if(!this.nombres_bebe.contains(nombre_bebe))
			this.nombres_bebe.add(nombre_bebe);
	}
	/*public void setNombre_bebe(String nombre_bebe []) {
		this.nombres_bebe = nombre_bebe;
	}*/

	public int getEstado() {
		return estado;
	}

	public int isEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}
	public List<Embarazo> getEmbarazos(){
		return DataBaseConnector.dbc().getEmbarazos();
	}

	public Embarazo(Cursor cursor) {
		setAtributes(cursor);
	}
	public void setNombre_bebe(ArrayList<String> nombres){
		this.nombres_bebe= nombres; 
	}
	public void setSexo_bebe(ArrayList<String> sexo){
		this.sexo_bebe= sexo; 
	}
	private void setAtributes(Cursor cursor) {
		if (cursor != null) {
			
			
			this.id_embarazo = cursor.getLong(cursor
					.getColumnIndex(DataBaseConnector.KEY_ID_EMBARAZO));
			/*setId_embarazo(cursor.getLong(cursor
					.getColumnIndex(DataBaseConnector.KEY_ID_EMBARAZO)));*/
			this.id_padre = cursor.getLong(cursor
					.getColumnIndex(DataBaseConnector.KEY_ID_PADRE));
			this.id_madre = cursor.getLong(cursor
					.getColumnIndex(DataBaseConnector.KEY_ID_MADRE));
			this.FUR = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_FUR));
			this.nombre_padre = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_NOMBRE_PADRE));
			this.nombre_madre = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_NOMBRE_MADRE));
		//revisar aci
			XL.e("this.nombre_bebe", this.nombres_bebe.toString());
			if(!this.nombres_bebe.contains(cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_NOMBRE_BEBE)))){
				this.nombres_bebe.add(cursor.getString(cursor
						.getColumnIndex(DataBaseConnector.KEY_NOMBRE_BEBE)));
				XL.e("ad",cursor.getString(cursor
						.getColumnIndex(DataBaseConnector.KEY_NOMBRE_BEBE)));
				XL.e("this.nombre_bebe", this.nombres_bebe.toString());
				this.sexo_bebe.add(cursor.getString(cursor
						.getColumnIndex(DataBaseConnector.KEY_SEXO_BEBE)));
			}
			this.fecha_nacimiento = cursor.getString(cursor
					.getColumnIndex(DataBaseConnector.KEY_FECHA_NACIMIENTO));
			this.estado = cursor.getInt(cursor
					.getColumnIndex(DataBaseConnector.KEY_ESTADO));
			nuevo = false;
		}
	}

	public Embarazo(long id) {
		this.id=id;
		Cursor c = DataBaseConnector.dbc().getEmbarazoWhereId(
				DataBaseConnector.KEY_ID + " = " + id);

		if (c != null) {
			XL.e("[Embarazo] El cursor vale: ", c.getString(c.getColumnIndex(DataBaseConnector.KEY_ID)));
			XL.e("[Embarazo] El id_embarazo vale: ", c.getString(c.getColumnIndex(DataBaseConnector.KEY_ID_EMBARAZO)));

		} else
			nuevo = true;
		setAtributes(c);
		if (c != null)
			c.close();
	}
	


	public void save() {
		if (nuevo)
			id = create();
		else
			update();
	}

	private long create() {
		return DataBaseConnector.dbc().insertInEmbarazo(
				getId_embarazo(), getId_madre(), getId_padre(), getFUR(),
				getNombre_madre(), getNombre_padre(), getNombre_bebe(),
				getSexo_bebe(), getFecha_nacimiento(), getEstado());
	}

	private boolean update() {
		return DataBaseConnector.dbc().updateInEmbarazo(
				getId_embarazo(), getId_madre(), getId_padre(), getFUR(),
				getNombre_madre(), getNombre_padre(), getNombre_bebe(),
				getSexo_bebe(), getFecha_nacimiento(), getEstado());
	}

	@Override
	public String toString() {
		return "Embarazo [id=" + id + ", id_embarazo=" + id_embarazo
				+ ", id_padre=" + id_padre + ", id_madre=" + id_madre
				+ ", FUR=" + FUR + ", nombre_padre=" + nombre_padre
				+ ", nombre_madre=" + nombre_madre + ", nombre_bebe="
				+ nombres_bebe + ", sexo_bebe=" + sexo_bebe
				+ ", fecha_nacimiento=" + fecha_nacimiento + ", estado="
				+ estado + ", nuevo=" + nuevo + "]";
	}
}
