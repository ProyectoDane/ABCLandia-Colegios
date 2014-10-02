package com.frba.abclandia.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static String DB_PATH = "/data/data/com.frba.abclandia/databases/";
	private static String DB_NAME = "gelemerv1";
	private static Integer DATABASE_VERSION = 1;
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	// Creamos el constructor llamando a 
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		
	}
	
	/**
	 * 	Creamos una base vacia en el sistema y reescribimos las tablas
	 */
	public void createDatabase() throws IOException{
		boolean dbExists =  checkDatabase();
		
		if (dbExists){
			// NO hacemos nada porque ya existe la base.
		} else {
			// Creamos una base vacia en el path del sistema
			// para luego sobreescribirla con la nuestra
		
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException e) {
				throw new Error("Error copiando la base de datos");
			}
		}
	}
	
	/**
	 * Chequea si la base de datos existe para evitar copiar los datos nuevamente.
	 * @return true si existe, falso if no existe.
	 */
	private boolean checkDatabase(){
		SQLiteDatabase checkDB = null;
		
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e){
			// La base de datos no existe todavia
		}
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB!= null ?true:false;
	}
	
	/*
	 *  Copia la base de datos de la carpeta assets a la carpeta del sistema
	 *  desde donde se puede accceder sin problemas
	 *  Se copia transfiriendo un bytestream.
	 */
	private void copyDatabase() throws IOException {
	// Abrimos la db local como un input stream
	InputStream myInput = myContext.getAssets().open(DB_NAME);
	
	// Path a la base que creamos recien
	String outFileName =  DB_PATH + DB_NAME;
	
	// Abrimos la db local vacia como el output stream
	OutputStream myOutput = new FileOutputStream(outFileName);
	
	// Transferimos los bytes desde el inputfile al output file
	
	byte [] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer))>0) {
		myOutput.write(buffer,0, length);
	}
	//Cerramos los streams
	myOutput.flush();
	myOutput.close();
	myInput.close();
	
	
	
	}
	
	public void openDatabase() throws SQLException {
		// Abrimos la DB
		String myPath = DB_PATH + DB_NAME;
		myDataBase =  SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
	}
	
	
	@Override 
	public synchronized void close(){
		if(myDataBase!= null){
			myDataBase.close();
		}
		super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
	
}
