package com.frba.abclandia.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.abclandia.Card;
import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.dtos.Categoria;
import com.frba.abclandia.dtos.Estadistica;
import com.frba.abclandia.dtos.Maestro;
import com.frba.abclandia.dtos.Palabra;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private  String DB_PATH = "/data/data/"; 
	private static String DB_NAME = "gelemerv1.sqlite";
	private static Integer DATABASE_VERSION = 1;
	private SQLiteDatabase myDataBase;
	private final Context mContext;
	private static AbcLandiaContract abcLandia = new AbcLandiaContract();

	// Creamos el constructor llamando a 
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.mContext = context;
		this.DB_PATH = this.DB_PATH + this.mContext.getApplicationContext().getPackageName() +"/databases/";
		
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
			File fdb =  this.mContext.getDatabasePath(DB_NAME);
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
	InputStream myInput = mContext.getAssets().open(DB_NAME);
	
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
		File fdb= this.mContext.getDatabasePath(DB_NAME);
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
		String selectQuery = "Select maestro_id, maestro_apellido, maestro_nombre from maestros";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do {
				Maestro m1 = new Maestro(cursor.getInt(0), 
						getStringFromCharset(cursor.getString(1)),
						getStringFromCharset(cursor.getString(2)));
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
				Alumno a1 = new Alumno(cursor.getInt(0), getStringFromCharset(cursor.getString(1)),
						getStringFromCharset(cursor.getString(2)), 1);
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
				Alumno unAlumno = new Alumno(cursor.getInt(0), getStringFromCharset(cursor.getString(1)), 
						getStringFromCharset(cursor.getString(2)), unMaestro);
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
		String selectQuery =  "Select categoria_id, categoria_nombre, categoria_descripcion from categorias where categoria_id = " 
		+ unAlumno.getId() + " ";
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
	
	
	public Categoria getCagetoriaFromAlumno(int unAlumnoId){
		Categoria unaCategoria;
		String selectQuery = "select categoria_id, categoria_intervalo, categoria_tipo_letra, categoria_alumno_id " +
				"from categorias where categoria_alumno_id = '" + unAlumnoId +"'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			unaCategoria = new Categoria(cursor.getInt(0),cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
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
	public List<Card> getPalabrasFromCategoria(int unaCategoria){
		final String PATH_TO_IMAGES = mContext.getFilesDir() + "/imagenes/";
		final String PATH_TO_SOUNDS = mContext.getFilesDir() + "/sonidos/";
		List<Card> palabras = new ArrayList<Card>();
		String selectQuery = "select palabra_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras where categoria_id = '"
				+ unaCategoria + "' " ;
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do{
				Card unaPalabra = new Card(cursor.getInt(0), getStringFromCharset(cursor.getString(1)), 
						getStringFromCharset(cursor.getString(2)),
						PATH_TO_IMAGES + cursor.getString(3)+ ".jpg", PATH_TO_SOUNDS+cursor.getString(4)+".ogg");
			
				Log.d("Palabras",cursor.getString(2));
				palabras.add(unaPalabra);
			} while(cursor.moveToNext());
		}
		cursor.close();
		return palabras;
	}	
	
	

	
	/**
	 *  Dada una Letra y una Categoria devuelve un objeto Card correspondiente a la palabra que comienza con esa letra para esa Categoria
	 * @param unaLetra
	 * @param unaCategoria
	 * @return
	 */
	public Card getPalabraFromLetraAndCategoria(String unaLetra, Integer unaCategoria) {
		Card unaPalabra;
		final String PATH_TO_IMAGES = mContext.getFilesDir() + "/imagenes/";
		final String PATH_TO_SOUNDS = mContext.getFilesDir() + "/sonidos/";
		String selectQuery = "select palabra_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras where categoria_id = '"
				+ unaCategoria + "'and palabra_letra = '" + unaLetra + "'";

		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			unaPalabra =  new Card(cursor.getInt(0), getStringFromCharset(cursor.getString(1)),
					getStringFromCharset(cursor.getString(2)),
					PATH_TO_IMAGES + cursor.getString(3)+ ".jpg", PATH_TO_SOUNDS+cursor.getString(4)+".ogg");
		} else {
			unaPalabra = new Card(0,  unaLetra, unaLetra, "none", "none" );
		}
		cursor.close();
		return unaPalabra;
	}
	
	/**
	 *  Dada una Letra y una Categoria devuelve un objeto Palabra correspondiente a la palabra que comienza con esa letra para esa Categoria
	 * @param unaLetra
	 * @param unaCategoria
	 * @return Palabra
	 */
	public Palabra getPalabraFromLetraAndCategoria1(String unaLetra, String unaCategoria) {
		Palabra unaPalabra;

		String selectQuery = "select palabra_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras where categoria_id = '"
				+ unaCategoria + "'and palabra_letra = '" + unaLetra + "'";

		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			unaPalabra =  new Palabra(cursor.getString(0), unaCategoria, getStringFromCharset(cursor.getString(1)),
					getStringFromCharset(cursor.getString(2)), cursor.getString(3), cursor.getString(4));
		} else {
			unaPalabra = new Palabra("0",  unaCategoria, unaLetra, unaLetra, "none", "none" );
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
		String selectQuery = "select palabra_palabra from palabras where categoria_id = '"
				+ unaCategoria + "' and palabra_letra = '" + unaLetra + "' ";
		SQLiteDatabase database =  this.getWritableDatabase();
		Cursor cursor =  database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			unaPalabra =  getStringFromCharset(cursor.getString(0));
		}
		cursor.close();
		return unaPalabra;
	}
	

	/**
	 * Inserta un Maestro en la Base de SQLite
	 * @param unMaestro
	 */
	public void insertMaestro(Maestro unMaestro){
		SQLiteDatabase database =  this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("maestro_id", unMaestro.getLegajo());
		values.put("maestro_nombre", unMaestro.getNombre());
		values.put("maestro_apellido", unMaestro.getApellido());
		Cursor cur1= database.query("maestros", null, "maestro_id=" + unMaestro.getLegajo(), null, null, null, null);
	      cur1.moveToLast();
	      int count1=cur1.getCount();
	      if(count1==0)
	      {
	    	  database.insert("maestros",null, values);

	      }
	      else
	      {
	        //Maestro id present
	    	Log.d("Datbase", "Maestro con el ID " + unMaestro.getLegajo() + " ya existe");
	      }
		
		database.close();
	}
	
	/** 
	 * Inserta un Alumno en la Base de SQLite
	 * @param unAlumno
	 */
	public void insertAlumno(Alumno unAlumno){
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues  values = new ContentValues();
		values.put("alumno_id", unAlumno.getId());
		values.put("nombre", unAlumno.getNombre());
		values.put("apellido", unAlumno.getApellido());
		// Chequeamos si el valor existe
		Cursor cur1= database.query("alumnos", null, "alumno_id=" + unAlumno.getId(), null, null, null, null);
	      cur1.moveToLast();
	      int count1=cur1.getCount();
	      if(count1==0)
	      {
	    	  database.insert("alumnos",null, values);

	      }
	      else
	      {
	        //course id present
	    	Log.d("Database", "Alumno con el ID " + unAlumno.getId() + " ya existe");
	      }

		database.close();
	}
	
	/**
	 * Inserta una Palabra en la Base de SQLite
	 * @param unaPalabra
	 */
	public void insertPalabra(Palabra unaPalabra){
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values =  new ContentValues();
		values.put("palabra_id", unaPalabra.getId());
		values.put("categoria_id", unaPalabra.getCategoria());
		values.put("palabra_letra",unaPalabra.getLetra());
		values.put("palabra_palabra",unaPalabra.getPalabra());
		values.put("sonido_id",unaPalabra.getSonido());
		values.put("imagen_id", unaPalabra.getImagen());
		// Chequeamos si el valor existe
		Cursor cur1= database.query("palabras", null, "palabra_id='" + unaPalabra.getId() + "' and  categoria_id = '" + unaPalabra.getCategoria()+"'", null, null, null, null);
	      cur1.moveToLast();
	      int count1=cur1.getCount();
	      if(count1==0)
	      {
	    	  database.insert("palabras",null, values);

	      }
	      else
	      {
	        //course id present
	    	Log.d("Database", "Palabra con el ID " + unaPalabra.getId() + " ya existe");
	      }
		database.close();	
	}
	
	/**
	 * Recibe el ID de un Alumno y el ID de un Maesto y los inserta en la tabla pivot.
	 * @param alumno_id
	 * @param maestro_id
	 */	
	public void insertAlumnoMaestroRelationship(int alumno_id, int maestro_id){
		SQLiteDatabase database =  this.getWritableDatabase();
		ContentValues values =  new ContentValues();
		values.put("maestro_id", maestro_id);
		values.put("alumno_id", alumno_id);
		// Chequeamos si el valor existe
		Cursor cur1= database.query("alumnos_maestros", null, "alumno_id=" + alumno_id + " and maestro_id = " + maestro_id, null, null, null, null);
	      cur1.moveToLast();
	      int count1=cur1.getCount();
	      if(count1==0)
	      {
	    	  database.insert("alumnos_maestros",null, values);

	      }
	      else
	      {
	        //course id present
	    	Log.d("Database", "La relacion entre el alumno " + alumno_id + " y el maestro " + maestro_id + " ya existe.");
	      }
		database.close();
		
	}
	
	
	/**
	 * Recibe un objeto Categoria y lo inserta en la base de datos.
	 * @param nuevaCategoria
	 */
	public void insertCategoria(Categoria nuevaCategoria) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values =  new ContentValues();
		values.put("categoria_id", nuevaCategoria.getCategoriaID());
		values.put("categoria_alumno_id", nuevaCategoria.getCategoriaAlumno());
		values.put("categoria_tipo_letra", nuevaCategoria.getCategoriaTipoLetra());
		values.put("categoria_intervalo",nuevaCategoria.getCategoriaIntervalo());
		// Chequeamos si existe la configuracion
		Cursor cur1 = database.query("categorias", null, "categoria_id = '" + nuevaCategoria.getCategoriaID() +"' and categoria_alumno_id = '" + nuevaCategoria.getCategoriaAlumno() + "'",
				null,null,null,null);
		int count1 =  cur1.getCount();
		if (count1 == 0){
			database.insert("categorias", null, values);
		} else {
			Log.d("Categoria", cur1.toString());
		}
		
	}
	
	
	
	
	/**
	 * Dado un objeto Estadistica la inserta en la base de datos.
	 * @param unaEstadistica
	 */
	public void insertEstadistica(Estadistica unaEstadistica) {
		SQLiteDatabase database =  this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("alumno_id", unaEstadistica.getAlumno_id());
		values.put("maestro_id", unaEstadistica.getMaestro_id());
		values.put("categoria_id", unaEstadistica.getCategoria_id());
		values.put("ejercicio", unaEstadistica.getEjercicio());
		values.put("nivel", unaEstadistica.getNivel());
		values.put("secuencia", unaEstadistica.getSecuencia());
		values.put("tiempo",unaEstadistica.getTiempo());
		values.put("cantidad_aciertos", unaEstadistica.getCantidad_aciertos());
		values.put("cantidad_fallas", unaEstadistica.getCantidad_fallas());
		values.put("timestamp", unaEstadistica.getTimestamp());
		database.insert("estadisticas",null, values);
		database.close();
		
		
	}

	public List<Palabra> getAllPalabras() {
		List<Palabra> palabras = new ArrayList<Palabra>();
		String selectQuery = "select palabra_id, categoria_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do{
				Palabra unaPalabra = new Palabra(cursor.getString(0), cursor.getString(1), getStringFromCharset(cursor.getString(2)), getStringFromCharset(cursor.getString(3)), cursor.getString(4),
						cursor.getString(5));
				Log.d("Palbras",cursor.getColumnName(3));
				palabras.add(unaPalabra);
			} while(cursor.moveToNext());
		}
		cursor.close();
		return palabras;
	}
	
	public List<Palabra> getAllPalabrasUniques() {
		List<Palabra> palabras = new ArrayList<Palabra>();
		String selectQuery = "select palabra_id, categoria_id, palabra_letra, palabra_palabra, imagen_id, sonido_id  from palabras   where imagen_id not like 'null' and sonido_id not like 'null' group by 1,2,3,4,5,6";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do{
				Palabra unaPalabra = new Palabra(cursor.getString(0), getStringFromCharset(cursor.getString(1)), 
						getStringFromCharset(cursor.getString(2)), getStringFromCharset(cursor.getString(3)), 
						getStringFromCharset(cursor.getString(4)),
						cursor.getString(5));
				Log.d("Palbras",cursor.getColumnName(3));
				palabras.add(unaPalabra);
			} while(cursor.moveToNext());
		}
		cursor.close();
		return palabras;
	}
	
	/**
	 * @param charsetName Charset Name eg: UTF-8,US-ASCII
	 * @param encodedString string in encoded format eg:
	 * @return
	 */
	 public static String getStringFromCharset(String encodedString)
	 {
		String charsetName = "UTF-8"; 
		String normal = "";
	 try
	 	{
		 Charset charset = Charset.forName(charsetName);
		 CharsetDecoder charsetDecoder = charset.newDecoder();
		 byte[] array = encodedString.getBytes(charset.displayName());
		 ByteBuffer byteBuffer = ByteBuffer.wrap(array);
		 CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
		 normal = charBuffer.toString();
	 	} catch (Exception e) {
	 		if(e!=null)
	 			e.printStackTrace();
	 	}
	 return normal;
	 }




}
