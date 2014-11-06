package com.frba.abclandia;

import java.io.IOException;
import java.util.List;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.frba.abclandia.adapters.AlumnoAdapter;
import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Alumno;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AlumnosActivity extends Activity
		implements OnItemClickListener {
	
	private DataBaseHelper myDbHelper;
	private Integer unMaestro = 0;
	ProgressDialog prgDialog;
	private GridView mGridView;
	private AlumnoAdapter mAdapter;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.alumno_activity);
		mGridView = (GridView) findViewById(R.id.gridViewAlumnos);
		
		Intent i = getIntent();
		this.unMaestro = i.getIntExtra("unMaestro", 0);
		
		// Iniciar DB
		iniciarDB();
		
		// Iniciar ProgressDialog
		iniciarPrgDialog();
		
		
		if (this.unMaestro != 0 && isNetworkAvailable() != false){
			//Sincronizamos los Alumnos
			syncAlumnos();
		}else {
			Log.d("ABCLandia", "No hay conectividad, no sincronizamos.");
		}
		
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private void iniciarPrgDialog() {
		// Iniciamos las propiedades del Progress Dialog
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Sincronizando la informacion de los Alumnos para el Maestro " + this.unMaestro);
		prgDialog.setCancelable(false);
		
	}
	
	
	private void syncAlumnos() {
		// TODO Auto-generated method stub
		// Create AsycHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		// Http Request Params Object
		RequestParams params = new RequestParams();
		// Show ProgressBar
		prgDialog.show();
		String server_url = "http://104.200.20.108/abclandia/public/index.php/api/maestros/";
		client.get(server_url + unMaestro + "/alumnos" , params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONObject response){
				// Si en lugar de un array nos responde con un unico JSONObject
				try {
					Alumno unAlumno = new Alumno(response.getInt("id"), response.getString("apellido"), response.getString("nombre"), (Integer) unMaestro);
					myDbHelper.insertAlumno(unAlumno);
					myDbHelper.insertAlumnoMaestroRelationship(unAlumno.getId(), unMaestro);
					initializeAdapter();
					
					} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				prgDialog.hide();
			}

			protected void initializeAdapter() {
				mAdapter = new AlumnoAdapter(AlumnosActivity.this,getAlumnosData());
				mGridView.setAdapter(mAdapter);
				mGridView.setOnItemClickListener(AlumnosActivity.this);
			}
			
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONArray serverAlumnos) {
				try {
					if (serverAlumnos != null){
						for (int i = 0; i < serverAlumnos.length(); i++){
							JSONObject unAlumno = (JSONObject) serverAlumnos.get(i);
							myDbHelper.insertAlumno(new Alumno(unAlumno.getInt("id"), unAlumno.getString("apellido"), unAlumno.getString("nombre"), unMaestro));	
							myDbHelper.insertAlumnoMaestroRelationship(unAlumno.getInt("id"), unMaestro);
							initializeAdapter();						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				prgDialog.hide();
			};
			
			@Override
			public void onFailure(int statusCode, Throwable error, String content) {
				// TODO Auto-generated method stub
				// Hide ProgressBar
				prgDialog.hide();
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
				} else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
				} else {
					Log.d("Alumnos Sync", "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]");
							
				}
				initializeAdapter();
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse){
				// TODO Auto-generated method stub
				// Hide ProgressBar
				prgDialog.hide();
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
				} else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
				} else {
					Log.d("Alumnos Sync", "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]");
							
				}
				initializeAdapter();
			}
			

            @Override
            public void onFailure(Throwable e, String response) {
				prgDialog.hide();
				initializeAdapter();
            }
			
		});
		
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

	
	private List<Alumno> getAlumnosData(){
		List<Alumno> alumnos =  myDbHelper.getAlumnosFromMaestro(this.unMaestro);
		return alumnos;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Alumno alumno =  (Alumno) mAdapter.getItem(position);
		Toast.makeText(this,  alumno.getNombre() + " " + alumno.getApellido() + " Seleccionado", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, ActividadesActivity.class);
		i.putExtra("unMaestro", unMaestro);
		i.putExtra("unAlumno", alumno.getId());
		startActivity(i);
		
	}
	
		

}
