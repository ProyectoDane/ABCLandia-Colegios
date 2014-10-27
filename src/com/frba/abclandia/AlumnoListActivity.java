package com.frba.abclandia;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.webserver.ABCLandiaRestServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AlumnoListActivity extends ListActivity {
	
	private DataBaseHelper myDbHelper;
	private Integer unMaestro = 0;
	private ABCLandiaRestServer server = new ABCLandiaRestServer();
	ProgressDialog prgDialog;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_alumno_list);
		
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
		client.get("http://yaars.com.ar/abclandia/public/index.php/api/maestros/"+unMaestro+"/alumnos", params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONObject response){
				// Si en lugar de un array nos responde con un unico JSONObject
				try {
					Alumno unAlumno = new Alumno(response.getInt("id"), response.getString("apellido"), response.getString("nombre"), (Integer) unMaestro);
					myDbHelper.insertAlumno(unAlumno);
					myDbHelper.insertAlumnoMaestroRelationship(unAlumno.getId(), unMaestro);
					prgDialog.hide();
					setListAdapter(new AlumnoListAdapter(getApplicationContext()));
					
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
							myDbHelper.insertAlumno(new Alumno(unAlumno.getInt("id"), unAlumno.getString("apellido"), unAlumno.getString("nombre"), unMaestro));	
							myDbHelper.insertAlumnoMaestroRelationship(unAlumno.getInt("id"), unMaestro);
							prgDialog.hide();
							setListAdapter(new AlumnoListAdapter(getApplicationContext()));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
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
				setListAdapter(new AlumnoListAdapter(getApplicationContext()));
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
			Log.d("POOCHIE", "No se pudo abrir la BD");
			throw sqle;
		}
		
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		//TODO: Inicia la siguiente actividad con los ejercicios y demas....
		
		Alumno alumno =  (Alumno) getListAdapter().getItem(position);
		Toast.makeText(this,  alumno.getNombre() + " " + alumno.getApellido() + " Seleccionado", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, ActividadesActivity.class);
		i.putExtra("unMaestro", unMaestro);
		i.putExtra("unAlumno", alumno.getId());
		startActivity(i);
	}
	
	private List<Alumno> getAlumnosData(){
		
		List<Alumno> alumnos =  myDbHelper.getAlumnosFromMaestro(this.unMaestro);
		return alumnos;
	}
	
	private class AlumnoListAdapter extends BaseAdapter{
		private Context mContext;
		private List<Alumno> mAlumnos;
		
		public AlumnoListAdapter(Context context){
			this.mContext = context;
			this.mAlumnos = getAlumnosData();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAlumnos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAlumnos.get(position);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			Alumno alumno = mAlumnos.get(arg0);
			return alumno.getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView;
			if (convertView == null){
				rowView = inflater.inflate(R.layout.alumno_row, parent,false);
			} else {
				rowView = convertView;
			}
			
			TextView lblAlumnoApellido = (TextView)  rowView.findViewById(R.id.lblAlumnoApellido);
			TextView lblAlumnoNombre = (TextView) rowView.findViewById(R.id.lblAlumnoNombre);
			
			Alumno alumno = this.mAlumnos.get(position);
			
			lblAlumnoApellido.setText(alumno.getApellido());
			lblAlumnoNombre.setText(alumno.getNombre());
			
			return rowView;
			
		}
	}

}
