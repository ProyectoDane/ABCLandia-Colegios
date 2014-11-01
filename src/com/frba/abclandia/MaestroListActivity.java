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
import com.frba.abclandia.dtos.Maestro;
import com.frba.abclandia.webserver.ABCLandiaRestServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class MaestroListActivity extends ListActivity {

	private DataBaseHelper myDbHelper;
	private ABCLandiaRestServer server;
	ProgressDialog prgDialog;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.maestros_activity);
		//ListView listView = (ListView) findViewById(R.id.list_maestro);
		
		// Iniciamos la BD
		iniciarDB();
		
		// Iniciar ProgressDialog
		iniciarPrgDialog();
		
		if (isNetworkAvailable() != false){
			// Sincroniza  Maestros	
			syncMaestros();
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
		prgDialog.setMessage("Sincronizando la informacion de los Maestros");
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
	
	private List<Maestro> getMaestrosData() {
		
		List<Maestro> maestros = myDbHelper.getAllMaestros();
		return maestros;
	}
	
	private void syncMaestros() {
		// TODO Auto-generated method stub
		// Create AsycHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		// Http Request Params Object
		RequestParams params = new RequestParams();
		// Show ProgressBar
		prgDialog.show();
		client.get("http://104.200.20.108/abclandia/public/index.php/api/maestros", params, new JsonHttpResponseHandler() {
	       
	    	@Override
	        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
	            // If the response is JSONObject instead of expected JSONArray
	    		try {
					Maestro unMaestro = new Maestro(response.getInt("id"), response.getString("apellido"), response.getString("nombre"));
					myDbHelper.insertMaestro(unMaestro);
					prgDialog.hide();
					setListAdapter(new MaestroListAdapter(getApplicationContext()));
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
							prgDialog.hide();
							setListAdapter(new MaestroListAdapter(getApplicationContext()));
							
						}
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
				prgDialog.hide();
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
				} else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
							Toast.LENGTH_LONG).show();
				}
				setListAdapter(new MaestroListAdapter(getApplicationContext()));
			}
	    	});
		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id ) {
		//TODO: Agregar accion cuando se selecciona un profesor.
		
		Maestro maestro =  (Maestro) getListAdapter().getItem(position);
		Toast.makeText(this, maestro.getApellido() + " " +  maestro.getLegajo() + " selected", Toast.LENGTH_LONG).show();
		
		// Tomar el ID del maestro
		// Buscar los alumnos del maestro
		Intent i = new Intent(this, AlumnoListActivity.class);
		i.putExtra("unMaestro", maestro.getLegajo());
		
		startActivity(i);
	}
	
	private class MaestroListAdapter extends BaseAdapter{
		
		private Context mContext;
		private List<Maestro> mMaestros;
		
		public MaestroListAdapter(Context context){
			mContext = context;
			
			// Tenemos que tomar los maestros de la base de datos y/o actualizarla
			mMaestros = getMaestrosData();
		}

		@Override
		public int getCount() {
			return mMaestros.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mMaestros.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			Maestro maestro = mMaestros.get(position);
			
			return maestro.getLegajo();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater) mContext
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView;
			if (convertView == null){
			    rowView = inflater.inflate(R.layout.maestro_row, parent, false);
			} else {
				rowView = convertView;
			}
			    TextView lblMaestroApellido = (TextView) rowView.findViewById(R.id.lblMaestroApellido);
			    TextView lblMaestroNombre =  (TextView) rowView.findViewById(R.id.lblMaestroNombre);
			
			    Maestro maestro = mMaestros.get(position);
			    
			    lblMaestroApellido.setText(maestro.getApellido());
			    lblMaestroNombre.setText(maestro.getNombre());
			    
			    return rowView;
		}
	}
}
