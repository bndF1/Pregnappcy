package com.pregnappcy.app.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseConnector {
	private static final String TAG = " ScheduleDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static DataBaseConnector dbc;
	private final Context mCtx;

	// ID tablas
	public static final String KEY_ID = "id";

	// TABLE_EMBARAZO
	public static final String KEY_ID_EMBARAZO = "id_embarazo";
	public static final String KEY_SEXO_BEBE = "sexo_bebe";
	public static final String KEY_FUR = "fur";
	public static final String KEY_NOMBRE_PADRE = "nombrePadre";
	public static final String KEY_NOMBRE_MADRE = "nombreMadre";
	public static final String KEY_ID_PADRE = "idPadre";
	public static final String KEY_ID_MADRE = "idMadre";
	public static final String KEY_FECHA_NACIMIENTO = "fecha_nacimiento";
	public static final String KEY_NOMBRE_BEBE = "nombre_bebe";
	public static final String KEY_ESTADO = "estado";

	// TABLE_AJUSTES
	public static final String KEY_ID_USUARIO = "id_usuario";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_NOMBRE_USUARIO = "nombre_usuario";
	// Revisar
	public static final String KEY_UPDATE = "last_update";
	// Nota: faltan cosas por a–adir, esta tabla se ir‡ ampliando conforme las
	// necesidades ya que aqu’ vamos a tener que controlar, las
	// notificicaciones, y dem‡s temas
	// de configuraci—n.

	/*
	 * Creaci—n de los nombres de las tablas que vamos a disponer en la BDA.
	 */
	public static final String TABLE_EMBARAZO = "EMBARAZO";
	public static final String TABLE_AJUSTES = "AJUSTES";

	public static final String DATABASE_NAME = "pregnappcyDB.db";
	public static final int DATABASE_VERSION = 2;

	/*
	 * Sentencias creaci—n BD
	 */

	public static final String CREATE_TABLE_EMBARAZO = "Create table IF NOT EXISTS "
			+ TABLE_EMBARAZO
			+ " ("
			+ KEY_ID
			+ " INTEGER primary key AUTOINCREMENT,"
			+ KEY_ID_EMBARAZO
			+ " INTEGER NOT NULL,"
			+ KEY_ID_MADRE
			+ " INTEGER,"
			+ KEY_ID_PADRE
			+ " INTEGER,"
			+ KEY_FUR
			+ " DATE NOT NULL,"
			+ KEY_NOMBRE_MADRE
			+ " TEXT,"
			+ KEY_NOMBRE_PADRE
			+ " TEXT,"
			+ KEY_NOMBRE_BEBE
			+ " TEXT,"
			+ KEY_SEXO_BEBE
			+ " text,"
			+ KEY_FECHA_NACIMIENTO
			+ " TEXT," + KEY_ESTADO + " INTEGER NOT NULL);";

	public static final String CREATE_TABLE_AJUSTES = "Create table IF NOT EXISTS "
			+ TABLE_AJUSTES
			+ " ("
			+ KEY_ID
			+ " INTEGER primary key,"
			+ KEY_ID_USUARIO
			+ " INTEGER NOT NULL,"
			+ KEY_EMAIL
			+ " TEXT NOT NULL,"
			+ KEY_NOMBRE_USUARIO
			+ " TEXT,"
			+ KEY_UPDATE
			+ " DATE);";

	/*
	 * Estructura tablas
	 */
	// TABLA EMBARAZO
	public static final String[] EMBARAZO = new String[] { KEY_ID,
			KEY_ID_EMBARAZO, KEY_ID_MADRE, KEY_ID_PADRE, KEY_FUR,
			KEY_NOMBRE_MADRE, KEY_NOMBRE_PADRE, KEY_NOMBRE_BEBE, KEY_SEXO_BEBE,
			KEY_FECHA_NACIMIENTO, KEY_ESTADO };
	// TABLA AJUSTES
	public static final String[] AJUSTES = new String[] { KEY_ID,
			KEY_ID_USUARIO, KEY_EMAIL, KEY_NOMBRE_USUARIO, KEY_UPDATE };

	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {

			database.execSQL(CREATE_TABLE_EMBARAZO);
			database.execSQL(CREATE_TABLE_AJUSTES);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMBARAZO);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_AJUSTES);
			onCreate(db);
		}

	}

	public DataBaseConnector(Context ctx) {
		this.mCtx = ctx;
		dbc = this;
	}

	public static DataBaseConnector dbc() {
		return dbc;
	}

	/**
	 * Abrimos las conexi—n con la BDA, en caso de no existir crea una nueva y
	 * si no puede lanza una excepci—n.
	 * 
	 * @return this
	 * @throws SQLException
	 *             : andorid.database.SQLException si no se puede crear o abrir
	 *             la conexi—n.
	 */
	public DataBaseConnector open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	// EMBARAZO
	
	/**
	 * 
	 * @param where
	 * @return
	 */
	public List<Embarazo> getEmbarazoWhere(String where) {
		List<Embarazo> listEmbarazo = new ArrayList<Embarazo>();
		Cursor mCursor = mDb.query(TABLE_EMBARAZO, EMBARAZO, where, null, null,
				null, null);
		if (mCursor.moveToFirst()) {
			do {
				Embarazo e = new Embarazo(mCursor);
				listEmbarazo.add(e);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		return listEmbarazo;
	}

	/**
	 * 
	 * @return lista con todos los embarazos.
	 */
	public List<Embarazo> getEmbarazos() {
		List<Embarazo> listEmbarazo = new ArrayList<Embarazo>();
		Cursor mCursor = mDb.query(TABLE_EMBARAZO, EMBARAZO, null, null, null,
				null, null);
		if (mCursor.moveToFirst()) {
			do {
				Embarazo e = new Embarazo(mCursor);
				listEmbarazo.add(e);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		return listEmbarazo;
	}

	/**
	 * 
	 * @param id
	 * @param id_embarazo
	 * @param id_madre
	 * @param id_padre
	 * @param FUR
	 * @param nombre_madre
	 * @param nombre_padre
	 * @param nombre_bebe
	 * @param sexo_bebe
	 * @param fecha_nacimiento
	 * @param estado
	 * @return
	 */

	public long insertInEmbarazo(long id_embarazo, long id_madre,
			long id_padre, String FUR, String nombre_madre,
			String nombre_padre, ArrayList<String> nombre_bebe,
			ArrayList<String> sexo_bebe,
			String fecha_nacimiento, int estado) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ID_EMBARAZO, id_embarazo);
		// owner
		initialValues.put(KEY_ID_MADRE, id_madre);
		// owner
		initialValues.put(KEY_ID_PADRE, id_padre);
		initialValues.put(KEY_FUR, FUR);
		initialValues.put(KEY_NOMBRE_PADRE, nombre_padre);
		initialValues.put(KEY_NOMBRE_MADRE, nombre_madre);
		for(String s: nombre_bebe)
			initialValues.put(KEY_NOMBRE_BEBE, s.toString());
		for(String s: sexo_bebe)
			initialValues.put(KEY_SEXO_BEBE, s.toString());	

		initialValues.put(KEY_FECHA_NACIMIENTO, fecha_nacimiento);
		initialValues.put(KEY_ESTADO, estado);
		return mDb.insert(TABLE_EMBARAZO, null, initialValues);

	}

	/**
	 * 
	 * @param id
	 * @param id_embarazo
	 * @param id_madre
	 * @param id_padre
	 * @param FUR
	 * @param nombre_madre
	 * @param nombre_padre
	 * @param nombre_bebe
	 * @param sexo_bebe
	 * @param fecha_nacimiento
	 * @param estado
	 * @return
	 */
	public boolean updateInEmbarazo(long id_embarazo, long id_madre,
			long id_padre, String FUR, String nombre_madre,
			String nombre_padre, ArrayList<String> nombre_bebe, ArrayList<String> sexo_bebe,
			String fecha_nacimiento, int estado) {
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ID_EMBARAZO, id_embarazo);
		initialValues.put(KEY_ID_MADRE, id_madre);
		initialValues.put(KEY_ID_PADRE, id_padre);
		initialValues.put(KEY_FUR, FUR);
		initialValues.put(KEY_NOMBRE_PADRE, nombre_padre);
		initialValues.put(KEY_NOMBRE_MADRE, nombre_madre);
		//initialValues.put(KEY_NOMBRE_BEBE, nombre_bebe.toString());
	//	initialValues.put(KEY_SEXO_BEBE, sexo_bebe.toString());	
		initialValues.put(KEY_FECHA_NACIMIENTO, fecha_nacimiento);
		initialValues.put(KEY_ESTADO, estado);
		return mDb.update(TABLE_EMBARAZO, initialValues, KEY_ID_EMBARAZO + "="
				+ id_embarazo, null) > 0;

	}
	
	/**
	 * 
	 * @param where
	 * @return
	 */
	public Cursor getEmbarazoWhereId(String where) {
		Cursor mCursor = mDb.query(TABLE_EMBARAZO, EMBARAZO, where, null, null,
				null, null);
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			return mCursor;
		} else
			return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean deteleAllEmbarazos() {
		return mDb.delete(TABLE_EMBARAZO, null, null) > 0;
	}

	/**
	 * 
	 * @param rowID
	 * @return
	 */
	public boolean deteleEmbarazo(int rowID) {
		return mDb.delete(CREATE_TABLE_EMBARAZO, KEY_ID + "=" + rowID, null) > 0;
	}

	// AJUSTES
	/**
	 * 
	 * @param where
	 * @return
	 */
	public Ajustes getAjustes(String where) {
		Ajustes a = null;
		Cursor mCursor = mDb.query(TABLE_AJUSTES, AJUSTES, where, null, null,
				null, null);
		if (mCursor.moveToFirst()) {
			do {
				a = new Ajustes(mCursor);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		return a;
	}

	/**
	 * 
	 * @param id
	 * @param id_ajuste
	 * @param id_usuario
	 * @param email
	 * @param nombre_usuario
	 * @param lastUpdate
	 * @return
	 */

	public long insertInAjustes(long id_usuario, String email,
			String nombre_usuario, String lastUpdate) {
		ContentValues initialValues = new ContentValues();
		// initialValues.put(KEY_ID, id);

		initialValues.put(KEY_ID_USUARIO, id_usuario);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_NOMBRE_USUARIO, nombre_usuario);
		initialValues.put(KEY_UPDATE, lastUpdate);
		return mDb.insert(TABLE_AJUSTES, null, initialValues);

		/*
		 * long l = mDb.update(TABLE_AJUSTES, initialValues, KEY_ID + "=" + id,
		 * null);
		 */

	}

	/**
	 * 
	 * @param id
	 * @param id_usuario
	 * @param email
	 * @param nombre_usuario
	 * @param lastUpdate
	 * @return
	 */
	public boolean updateInAjustes(long id_usuario, String email,
			String nombre_usuario, String lastUpdate) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ID_USUARIO, id_usuario);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_NOMBRE_USUARIO, nombre_usuario);
		initialValues.put(KEY_UPDATE, lastUpdate);

		return mDb
				.update(TABLE_AJUSTES, initialValues, KEY_ID + "= 1", null) > 0;
	}

	/**
	 * 
	 * @param where
	 * @return
	 */
	public Cursor getAjustesWhereId(String where) {
		Cursor mCursor = mDb.query(TABLE_AJUSTES, AJUSTES, where, null, null,
				null, null);
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			return mCursor;
		} else
			return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean deteleAllAjustes() {
		return mDb.delete(TABLE_AJUSTES, null, null) > 0;
	}

	/**
	 * 
	 * @param rowID
	 * @return
	 */
	public boolean deteleAjustes(int rowID) {
		return mDb.delete(CREATE_TABLE_AJUSTES, KEY_ID + "=" + rowID, null) > 0;
	}

	
}