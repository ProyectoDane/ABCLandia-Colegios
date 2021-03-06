package com.frba.abclandia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.abclandia.AbcPlayerActivity;
import com.example.abclandia.CardLetterPlayerActivity;
import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Categoria;
import com.frba.abclandia.dtos.Palabra;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActividadesActivity extends Activity {
	
	DataBaseHelper myDbHelper;
	ProgressDialog prgDialog;
	private int unMaestro = 0;
	private int unAlumno = 0;
	private int unaCategoria = 0;
	
	protected void onCreate(Bundle paramBundle){
	super.onCreate(paramBundle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.actividades_activity);
//		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
//		Button btnReproducirAbcdario = (Button) findViewById(R.id.btnReproducirABC);
//		btnReproducirAbcdario.setTypeface(font);
//		
		Intent i = getIntent();
		this.unMaestro = i.getIntExtra("unMaestro", 0);
		this.unAlumno = i.getIntExtra("unAlumno", 0);
		
		// Iniciar DB
		iniciarDB();
		
		// Iniciar ProgressDialog
		iniciarPrgDialog();
		
		if (this.unAlumno != 0 && this.unMaestro != 0  && isNetworkAvailable() != false ){
			// Sincronizamos los datos del alumno
			syncAlumnoDatos();
		} else {
			Log.d("ABCLandia", "No hay conectividad, no sincronizamos.");
		}
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private String getSoundPath() {
		return getFilesDir() +"/sonidos/";
	}
	
	protected String getImagePath() {
		return getFilesDir()  + "/imagenes/";
	}
	
	private boolean fileExists(String string) {
		File unArchivo = new File(string);
		if (unArchivo.exists()){
			return true;
		} else{
			return false;
		}
	}
	
	private void syncAlumnoDatos() {
		// Create AsycHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(10);
		// Http Request Params Object
		RequestParams params = new RequestParams();
		// Show ProgressBar
		prgDialog.show();
		Log.d("Actividades", "Sincronizando informacion del alumno " + unAlumno);
		String server_url = "http://104.200.20.108/abclandia/public/index.php/api/alumnos/"; 
		client.get( server_url  + unAlumno, params, new JsonHttpResponseHandler(){
			
			
//		     @Override
//		     public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {		       
//		          
//		    	 Log.d("Sincro Imagenes", "Status Code: " +statusCode);
//		    	 Log.d("Sincro Imagenes", headers.toString());
//		         Log.d("Sincro Imagenes" , responseBody.toString());
//		         Log.d("Sincro Imagenes", "En la respuesta hay " +responseBody.length);
//		         String respuesta = responseBody.toString();
//		         Log.d("SINCRO", respuesta);
//		         prgDialog.hide();
//		          
//		     }
			
			@Override
			public void onSuccess (int statusCCode, Header [] headers, JSONObject alumnoCategoriaWeb){
			try {
				Integer intervaloEntreTarjetas =  alumnoCategoriaWeb.getInt("intervalo_entre_tarjetas");
				Integer tipoLetra =  alumnoCategoriaWeb.getInt("tipo_letra");
				JSONObject categoria = alumnoCategoriaWeb.getJSONObject("categoria");
				unaCategoria = categoria.getInt("id");
				Categoria nuevaCategoria =  new Categoria(unaCategoria,tipoLetra,intervaloEntreTarjetas, unAlumno);
				myDbHelper.insertCategoria(nuevaCategoria);
				JSONArray palabras = categoria.getJSONArray("palabras");
				final String PATH_TO_SOUNDS = getSoundPath();
				final String PATH_TO_IMAGES = getImagePath();
				try{
					if (palabras != null){
						for (int i=0; i< palabras.length(); i++){
							JSONObject unaPalabra =  palabras.getJSONObject(i);
							Log.d("Palabra bug", unaPalabra.getString("id"));
							Palabra miPalabra = new Palabra(unaPalabra.getString("id"), categoria.getString("id"), unaPalabra.getString("letra"),
									unaPalabra.getString("palabra"), unaPalabra.getString("imagen_id"), unaPalabra.getString("sonido_id"));
							myDbHelper.insertPalabra(miPalabra);
							final String imagenId = unaPalabra.getString("imagen_id");
							final String sonidoId =  unaPalabra.getString("sonido_id");
							
							if (fileExists(PATH_TO_IMAGES+imagenId +".jpg")){
								// Existe
								Log.d("ABCLandia - Server", "La imagen " + imagenId + " ya existe");
							} else {
									Log.d("Imagen", "Buscando imagen " + imagenId);
									AsyncHttpClient imagen = new AsyncHttpClient();
									RequestParams imagenParams = new RequestParams();
									imagen.get("http://104.200.20.108/abclandia/public/index.php/api/imagenes/" + imagenId, imagenParams, new AsyncHttpResponseHandler() {
												public void onSuccess(String response) {
													final String PATH_TO_SOUNDS = getFilesDir() + "/imagenes/";
													String  nuevaImagenNombre = imagenId+".jpg";
													File unaImagen = new File(PATH_TO_SOUNDS,nuevaImagenNombre);
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
													Log.d("Error","Error: " + statusCode);
													prgDialog.hide();
												}
										
											});
							}
							
							if (fileExists(PATH_TO_SOUNDS+sonidoId +".ogg")){
								// Existe
								Log.d("ABCLandia - Server", "El sonido" + sonidoId + "ya existe");
							} else {
								Log.d("Sonido", "Buscando Sonido" + sonidoId);
											AsyncHttpClient sonido =  new AsyncHttpClient();
											RequestParams sonidoParams =  new RequestParams();
											sonido.get("http://104.200.20.108/abclandia/public/index.php/api/sonidos/" + sonidoId, sonidoParams, new AsyncHttpResponseHandler() {
													public void onSuccess(String response) {
														final String PATH_TO_IMAGES = getFilesDir() + "/sonidos/";
														String  nuevaImagenNombre = sonidoId+".ogg";
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
															Log.d("Sonidos", statusCode + " " + sonidoId);
														}
													}												
												});
							}
						}
					}
					prgDialog.hide();
				}catch (JSONException e) {
						e.printStackTrace();
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
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
					Log.d("Sonidos", statusCode + " ");
				}
				prgDialog.hide();
			}
			
            @Override
            public void onFailure(Throwable e, String response) {
				prgDialog.hide();
            }
            
			@Override
            public void onFinish() {
                if(prgDialog.isShowing())
                {
                    prgDialog.hide();
                }
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
            	prgDialog.hide();
            }
		});
		//prgDialog.hide();
	}

	private void iniciarPrgDialog() {
		// Iniciamos las propiedades del Progress Dialog
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Sincronizando la informacion del Alumno");
		prgDialog.setCancelable(false);
		
	}
	

	private void iniciarDB() {
		// Inicializar servicios
		myDbHelper = new DataBaseHelper(this);
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
	
	
	public void btnReproducirAbecedario(View view){
		
		Intent intent = new Intent(this,AbcPlayerActivity.class );
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
	}
	
	public void btnReproducirLetra(View view){
		
		Intent intent = new Intent(this,CardLetterPlayerActivity.class );
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
	}
	
	public void btnJugar(View view){
		//TODO: Llamar a JugarActivity
    	Intent intent = new Intent(this, JugarActivity.class);
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
	}
}
