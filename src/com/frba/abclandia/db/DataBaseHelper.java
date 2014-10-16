package com.frba.abclandia.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.dtos.Categoria;
import com.frba.abclandia.dtos.Maestro;
import com.frba.abclandia.dtos.Palabra;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private  String DB_PATH = "/data/data/"; 
	private static String DB_NAME = "gelemerv1.sqlite";
	private static Integer DATABASE_VERSION = 1;
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	private static AbcLandiaContract abcLandia = new AbcLandiaContract();

	// Creamos el constructor llamando a 
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		this.DB_PATH = this.DB_PATH + this.myContext.getApplicationContext().getPackageName() +"/databases/";
		
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
			File fdb =  this.myContext.getDatabasePath(DB_NAME);
			checkDB = SQLiteDatabase.openDatabase(fdb.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
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
		//String myPath = DB_PATH + DB_NAME;
		File fdb= this.myContext.getDatabasePath(DB_NAME);
		myDataBase =  SQLiteDatabase.openDatabase(fdb.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
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
		/** 
		 * No tenemos que ejecutar nada aca ya que la base la creamos desde un archivo en los assets
		 */
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}


	
	// Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
	
	/**
	 * Devuelve el listado de todos los profesores que hay en la Base
	 * @return List<Maestro>
	 */
	public List<Maestro> getAllMaestros() {
		List<Maestro> maestros =  new ArrayList<Maestro>();
		String selectQuery = "Select _id, maestro_apellido, maestro_nombre from maestros";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do {
				Maestro m1 = new Maestro(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				maestros.add(m1);
			} while (cursor.moveToNext());
		
		}
		cursor.close();
		return maestros;
	}
	
	
	/**
	 * Devuelve el listado de todos los alumnos que hay en la base
	 * @return List<Alumno>
	 */
	public List<Alumno> getAllAlumnos(){
		List<Alumno> alumnos =  new ArrayList<Alumno>();
		String selectQuery = "Select _id, nombre, apellido from alumnos";
		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do{
				Alumno a1 = new Alumno(cursor.getInt(0), cursor.getString(1), cursor.getString(2), 1);
				alumnos.add(a1);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return alumnos;
	}

	
	/**
	 * Dado un Maesro devuelve todo el listado de los alumnos de ese maestro.
	 * @param unMaestro
	 * @return List<Alumnos>
	 */
	public List<Alumno> getAlumnosFromMaestro(Integer unMaestro){
		List<Alumno> alumnos =  new ArrayList<Alumno>();
		String selectQuery = "Select A.alumno_id, A.nombre, A.apellido from alumnos A inner join alumnos_maestros AM on (AM.maestro_id =" 
		+ unMaestro + " and A.alumno_id = AM.alumno_id)";

		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do{
				Alumno unAlumno = new Alumno(cursor.getInt(0), cursor.getString(1), cursor.getString(2), unMaestro);
				alumnos.add(unAlumno);
			} while(cursor.moveToNext());
		}
		cursor.close();
		return alumnos;
	}
	
	/**
	 * Un Alumno devuelve la Categoria a la que pertenece el Alumno 
	 * @param alumno_categoria
	 * @return Categoria
	 */
	public Categoria getAlumnoCategoria(Alumno unAlumno){
		//Categoria categoria;
		Categoria unaCategoria;
		String selectQuery =  "Select categoria_id, categoria_nombre, categoria_descripcion from categorias where categoria_id = " + unAlumno.getLegajo() + " ";
		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			unaCategoria = new Categoria(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
		} else {
			unaCategoria = new Categoria(0,"Default", "Default");
		}
		cursor.close();
		return unaCategoria;
	}
	
	
	/**
	 * Dado el id de una Categoria Devuelve una lista de las palabras del Abcdario que pertenecen a esa Categoria
	 * @param unaCategoria
	 * @return List<Palabra>
	 */
	public List<Palabra> getPalabrasFromCategoria(Integer unaCategoria){
		List<Palabra> palabras = new ArrayList<Palabra>();
		String selectQuery = "select palabra_id, categoria_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras where categoria_id = "
				+ unaCategoria + " " ;
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do{
				Palabra unaPalabra = new Palabra(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(3),
						cursor.getString(5));
				Log.d("Palbras",cursor.getColumnName(3));
				palabras.add(unaPalabra);
			} while(cursor.moveToNext());
		}
		cursor.close();
		return palabras;
	}	
	/**
	 *  Dada una Letra y una Categoria devuelve un objeto Palabra correspondiente a la palabra que comienza con esa letra para esa Categoria
	 * @param unaLetra
	 * @param unaCategoria
	 * @return Palabra
	 */
	public Palabra getPalabraFromLetraAndCategoria(String unaLetra, Integer unaCategoria) {
		Palabra unaPalabra;
		String selectQuery = "select palabra_id, categoria_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras where categoria_id = "
				+ unaCategoria + " and palabra_letra = " + unaLetra + " ";
		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			unaPalabra =  new Palabra(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(3),
					cursor.getString(5));
		} else {
			unaPalabra = new Palabra(0, unaCategoria, unaLetra, unaLetra, "none", "none" );
		}
		cursor.close();
		return unaPalabra;
	}
	
	/**
	 * Dada una Letra y un Categoria ID devuelve un String con la Palabra asociada a esa letra para esa Categoria
	 * @param unaLetra
	 * @param unaCategoria
	 * @return String
	 */
	public String getPalabraStringFromLetraAndCategoria(String unaLetra, Integer unaCategoria) {
		String unaPalabra = "Null";
		String selectQuery = "select palabra_palabra from palabras where categoria_id = "
				+ unaCategoria + " and palabra_letra = " + unaLetra + " ";
		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			unaPalabra =  cursor.getString(0);
		}
		cursor.close();
		return unaPalabra;
	}
	
	
}
