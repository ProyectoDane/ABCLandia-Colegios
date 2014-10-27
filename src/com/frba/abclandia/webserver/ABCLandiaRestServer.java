package com.frba.abclandia.webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.dtos.Maestro;
import com.frba.abclandia.dtos.Palabra;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ABCLandiaRestServer {
	private DataBaseHelper myDbHelper;
	private Context mContext;
	
	
	public ABCLandiaRestServer(Context applicationContext) {
		// TODO Auto-generated constructor stub
		this.mContext =  applicationContext;
		iniciarDB();
	}
	public ABCLandiaRestServer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Devuelve todos los Maestros que estan en la base del server.
	 * @return ArrayList<Maestro>
	 * @throws JSONException
	 */
    public ArrayList<Maestro> getMaestros() throws JSONException{
    	final ArrayList<Maestro> maestros = new ArrayList<Maestro>();
	ABCLandiaRestClient.get("api/maestros", null, new JsonHttpResponseHandler() {
        
    	@Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
    		try {
				Maestro unMaestro = new Maestro(response.getInt("id"), response.getString("apellido"), response.getString("nombre"));
				maestros.add(unMaestro);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray serverMaestros) {
            // maestros es un array con todos los maestros que devuelve el server.
			try {
				if (serverMaestros != null){
					for (int i=0; i< serverMaestros.length(); i++){
						JSONObject unMaestro =  (JSONObject) serverMaestros.get(i);
						maestros.add(new Maestro(unMaestro.getInt("id"), unMaestro.getString("apellido"), unMaestro.getString("nombre")));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
    	});
	return maestros;
    }
    

    /**
     *  Dado el ID de un Maestro recupera del Server un listado de todos los Alumnos de ese maestro.
     * @param unMaestroId
     * @throws JSONException
     */
	public ArrayList<Alumno> getAlumnosFromMaestro(final Integer unMaestroId) throws JSONException {
		final ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		ABCLandiaRestClient.get("api/maestros/"+ unMaestroId.toString() + "/alumnos", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONObject response){
				// Si en lugar de un array nos responde con un unico JSONObject
				try {
					Alumno unAlumno = new Alumno(response.getInt("id"), response.getString("apellido"), response.getString("nombre"), (Integer) unMaestroId);
					alumnos.add(unAlumno);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONArray serverAlumnos) {
				String alumnos_cant =  "Hay " + serverAlumnos.length() + " ALUMNOS";
				Log.d("Alumnos", alumnos_cant );
				
		
				try {
					if (serverAlumnos != null){
						for (int i = 0; i < serverAlumnos.length(); i++){
							JSONObject unAlumno = (JSONObject) serverAlumnos.get(i);
							alumnos.add(new Alumno(unAlumno.getInt("id"), unAlumno.getString("apellido"), unAlumno.getString("nombre"), unMaestroId));							
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		});
		return alumnos;
	}

	/**
	 * Dato un Alumno Devuelve la categoria del Alumno y las Palabras correspondientes a esa Categoria.
	 * @param unAlumnoId
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<Palabra> getCategoriaFromAlumno(final Integer unAlumnoId) throws JSONException{
		final ArrayList<Palabra> alumnoPalabras = new ArrayList<Palabra>();
		ABCLandiaRestClient.get("api/alumnos/"+unAlumnoId.toString(), null, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess (int statusCCode, Header [] headers, JSONObject alumnoCategoriaWeb){
			try {
				Integer intervaloEntreTarjetas =  alumnoCategoriaWeb.getInt("intervalo_entre_tarjetas");
				Integer tipoLetra =  alumnoCategoriaWeb.getInt("tipo_letra");
				JSONObject categoria = alumnoCategoriaWeb.getJSONObject("categoria");
				JSONArray palabras = categoria.getJSONArray("palabras");
				try{
					if (palabras != null){
						for (int i=0; i< palabras.length(); i++){
							JSONObject unaPalabra =  palabras.getJSONObject(i);
							alumnoPalabras.add(new Palabra(unaPalabra.getString("id"), categoria.getString("id"), unaPalabra.getString("letra").toUpperCase(),
									unaPalabra.getString("palabra"), unaPalabra.getString("imagen_id"), unaPalabra.getString("sonido_id")));
						}
					}
				}catch (JSONException e) {
						e.printStackTrace();
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		Log.d("Categoria", alumnoPalabras.toString());
		return alumnoPalabras;
	}	
	
	
	
	///////////////////////////////////////////DB Sync////////////////////////////////////////////////
    
	public void syncAll() throws JSONException {
		this.syncDBMaestrosAndAlumnos();
		
		List<Alumno> alumnos = myDbHelper.getAllAlumnos();
		for (int j = 0; j< alumnos.size(); j++){
			this.syncCategoriasAndPalabrasFromAlumno(alumnos.get(j).getId());
		}
		List<Palabra> palabras  = myDbHelper.getAllPalabrasUniques();
		for (int i =0; i< palabras.size(); i++){
			Log.d("Palabra", palabras.get(i).getImagen());
			if (palabras.get(i).getImagen() != "null"){
				this.syncImagenFromServer(palabras.get(i).getImagen());
			}
			if (palabras.get(i).getSonido() != "null"){
				this.syncSonidoFromServer(palabras.get(i).getSonido());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	/**
     * Metodo para sincronizar todos los maestros con el server.
     * @throws JSONException
     */
    public void syncDBMaestros() throws JSONException{    	
	ABCLandiaRestClient.get("api/maestros", null, new JsonHttpResponseHandler() {
        
    	@Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
    		try {
				Maestro unMaestro = new Maestro(response.getInt("id"), response.getString("apellido"), response.getString("nombre"));
				myDbHelper.insertMaestro(unMaestro);
				//syncAlumnosDBForMaestro(unMaestro.getLegajo());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray serverMaestros) {
            // maestros es un array con todos los maestros que devuelve el server.
			try {
				if (serverMaestros != null){
					for (int i=0; i< serverMaestros.length(); i++){
						JSONObject unMaestro =  (JSONObject) serverMaestros.get(i);
						Maestro maestroDb = new Maestro(unMaestro.getInt("id"), unMaestro.getString("apellido"), unMaestro.getString("nombre"));
						myDbHelper.insertMaestro(maestroDb);
						//syncAlumnosDBForMaestro(maestroDb.getLegajo());
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
    	});
    }
    
    /**
     * Sincroniza todos los Maestros y Alumnos que hay en la base de datos.
     * @throws JSONException
     */
    public void syncDBMaestrosAndAlumnos() throws JSONException{    	
	ABCLandiaRestClient.get("api/maestros", null, new JsonHttpResponseHandler() {
        
    	@Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
    		try {
				Maestro unMaestro = new Maestro(response.getInt("id"), response.getString("apellido"), response.getString("nombre"));
				myDbHelper.insertMaestro(unMaestro);
				syncAlumnosDBForMaestro(unMaestro.getLegajo());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray serverMaestros) {
            // maestros es un array con todos los maestros que devuelve el server.
			try {
				if (serverMaestros != null){
					for (int i=0; i< serverMaestros.length(); i++){
						JSONObject unMaestro =  (JSONObject) serverMaestros.get(i);
						Maestro maestroDb = new Maestro(unMaestro.getInt("id"), unMaestro.getString("apellido"), unMaestro.getString("nombre"));
						myDbHelper.insertMaestro(maestroDb);
						syncAlumnosDBForMaestro(maestroDb.getLegajo());
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
    	});
    }
    
    /**
     * Dado el ID de un maestro descarga
     * @param unMaestroId
     * @throws JSONException
     */
	public void syncAlumnosDBForMaestro (final Integer unMaestroId) throws JSONException {
		ABCLandiaRestClient.get("api/maestros/"+ unMaestroId.toString() + "/alumnos", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONObject response){
				// Si en lugar de un array nos responde con un unico JSONObject
				try {
					Alumno unAlumno = new Alumno(response.getInt("id"), response.getString("apellido"), response.getString("nombre"), (Integer) unMaestroId);
					myDbHelper.insertAlumno(unAlumno);
					myDbHelper.insertAlumnoMaestroRelationship(unAlumno.getId(), unMaestroId);
					} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONArray serverAlumnos) {
				try {
					if (serverAlumnos != null){
						for (int i = 0; i < serverAlumnos.length(); i++){
							JSONObject unAlumno = (JSONObject) serverAlumnos.get(i);
							myDbHelper.insertAlumno(new Alumno(unAlumno.getInt("id"), unAlumno.getString("apellido"), unAlumno.getString("nombre"), unMaestroId));	
							myDbHelper.insertAlumnoMaestroRelationship(unAlumno.getInt("id"), unMaestroId);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		});
	}
	
	/**
	 * Dado el ID de una Imagen la descarga y la deja en la carpeta correspondiente.
	 * @param imagenId
	 */
	public void syncImagenFromServer(final String imagenId){
		
		final String PATH_TO_IMAGES = getImagePath();	
		Log.d("Imagen",	 PATH_TO_IMAGES+imagenId+ ".jpg");
		if (fileExists(PATH_TO_IMAGES+imagenId +".jpg")){
			// Existe
			Log.d("ABCLandia - Server", "La imagen " + imagenId + "ya existe");
		} else {
			ABCLandiaRestClient.get("api/imagenes/" + imagenId, null, new AsyncHttpResponseHandler() {
				public void onSuccess(String response) {
					final String PATH_TO_IMAGES = Environment.getExternalStorageDirectory().getPath() + "/imagenes/";
					String  nuevaImagenNombre = imagenId+".jpg";
					File unaImagen = new File(PATH_TO_IMAGES,nuevaImagenNombre);
					// Bajar la Imagen
					byte[] decodedImage = Base64.decode(response, Base64.DEFAULT);
					
					try {						
						FileOutputStream nuevaImagenStream =  new FileOutputStream(unaImagen);
						nuevaImagenStream.write(decodedImage);
						nuevaImagenStream.close();
						
					} catch (FileNotFoundException e) {
			            System.out.println("Image not found" + e);
			        } catch (IOException ioe) {
			            System.out.println("Exception while reading the Image " + ioe);
			        }
				}
				// When error occured
				@Override
				public void onFailure(int statusCode, Throwable error, String content) {
					// TODO Auto-generated method stub
					// Hide ProgressBar
					
					if (statusCode == 404) {
						Log.d("ABCLandia - Server", "Requested resource not found");
					} else if (statusCode == 500) {
						Log.d("ABCLandia - Server", "Something went wrong at server end");
					} else {
						Log.d("ABCLandia - Server", "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]");
					}
				}
			});
		}
	}
	
	/** 
	 * Dado el ID de un Sonido lo descarga y lo deja en la carpeta correspondiente.
	 * @param sonido_id
	 */
	public void syncSonidoFromServer(final String sonido_id){
		
		final String PATH_TO_SOUNDS = getSoundPath();	
		if (fileExists(PATH_TO_SOUNDS+sonido_id +".ogg")){
			// Existe
			Log.d("ABCLandia - Server", "El sonido" + sonido_id + "ya existe");
		} else {
			ABCLandiaRestClient.get("api/sonidos/" + sonido_id, null, new AsyncHttpResponseHandler() {
				public void onSuccess(String response) {
					final String PATH_TO_IMAGES = Environment.getExternalStorageDirectory().getPath() + "/sonidos/";
					String  nuevaImagenNombre = sonido_id+".ogg";
					File unaImagen = new File(PATH_TO_IMAGES,nuevaImagenNombre);
					// Bajar el sonido
					byte[] decodedImage = Base64.decode(response, Base64.DEFAULT);
					
					try {						
						FileOutputStream nuevaImagenStream =  new FileOutputStream(unaImagen);
						nuevaImagenStream.write(decodedImage);
						nuevaImagenStream.close();
						
					} catch (FileNotFoundException e) {
			            System.out.println("Sound not found" + e);
			        } catch (IOException ioe) {
			            System.out.println("Exception while reading the Sound " + ioe);
			        }
				}
				// When error occured
				@Override
				public void onFailure(int statusCode, Throwable error, String content) {
					// TODO Auto-generated method stub
					// Hide ProgressBar
					
					if (statusCode == 404) {
						Log.d("ABCLandia - Server", "Requested resource not found");
					} else if (statusCode == 500) {
						Log.d("ABCLandia - Server", "Something went wrong at server end");
					} else {
						Log.d("ABCLandia - Server", "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]");
						Log.d("Peto", statusCode + " " );
					}
				}
			});
		}
	}
	
	
	public void syncCategoriasAndPalabrasFromAlumno(final Integer unAlumnoId) throws JSONException{
		ABCLandiaRestClient.get("api/alumnos/"+unAlumnoId.toString(), null, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess (int statusCCode, Header [] headers, JSONObject alumnoCategoriaWeb){
			try {
				Integer intervaloEntreTarjetas =  alumnoCategoriaWeb.getInt("intervalo_entre_tarjetas");
				Integer tipoLetra =  alumnoCategoriaWeb.getInt("tipo_letra");
				JSONObject categoria = alumnoCategoriaWeb.getJSONObject("categoria");
				JSONArray palabras = categoria.getJSONArray("palabras");
				try{
					if (palabras != null){
						for (int i=0; i< palabras.length(); i++){
							JSONObject unaPalabra =  palabras.getJSONObject(i);
							//alumnoPalabras.add(new Palabra(unaPalabra.getInt("id"), categoria.getInt("id"), unaPalabra.getString("letra").toUpperCase(),
									//unaPalabra.getString("palabra"), unaPalabra.getString("imagen_id"), unaPalabra.getString("sonido_id")));
							myDbHelper.insertPalabra(new Palabra(unaPalabra.getString("id"), categoria.getString("id"), unaPalabra.getString("letra").toUpperCase(),
									unaPalabra.getString("palabra"), unaPalabra.getString("imagen_id"), unaPalabra.getString("sonido_id")));
						}
					}
				}catch (JSONException e) {
						e.printStackTrace();
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
	}	
	

	private String getSoundPath() {
		return mContext.getFilesDir() + "/sonidos/";
	}
	private boolean fileExists(String string) {
		File unArchivo = new File(string);
		if (unArchivo.exists()){
			return true;
		} else{
			return false;
		}
	}
	protected String getImagePath() {
		// TODO Auto-generated method stub
		return mContext.getFilesDir() + "/imagenes/";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	private void iniciarDB() {
		// Inicializar servicios
		myDbHelper = new DataBaseHelper(this.mContext);
		try {
			myDbHelper.createDatabase();
		} catch (IOException ioe) {
			throw new Error("No se pudo crear la base de datos");
			
		}
		
		try {
			myDbHelper.openDatabase();
		}catch (SQLException sqle){
			Log.d("ABCLandia", "No se pudo abrir la BD");
			throw sqle;
		}
	}

	private class MediaSync extends AsyncTask<List<Alumno>, Void, Void>{

		@Override
		protected Void doInBackground(List<Alumno>... arg0) {
			
			List<Alumno> alumnos = myDbHelper.getAllAlumnos();
			for (int j = 0; j< alumnos.size(); j++){
				try {
					ABCLandiaRestServer.this.syncCategoriasAndPalabrasFromAlumno(alumnos.get(j).getId());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			List<Palabra> palabras  = myDbHelper.getAllPalabras();
			for (int i =0; i< palabras.size(); i++){
				Log.d("Palabra", palabras.get(i).getImagen());
				if (palabras.get(i).getImagen() != "null"){
					ABCLandiaRestServer.this.syncImagenFromServer(palabras.get(i).getImagen());
				}
				if (palabras.get(i).getSonido() != "null"){
					ABCLandiaRestServer.this.syncSonidoFromServer(palabras.get(i).getSonido());
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
