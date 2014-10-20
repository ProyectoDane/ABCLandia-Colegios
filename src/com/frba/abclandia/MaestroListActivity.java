package com.frba.abclandia;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
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
import com.frba.abclandia.dtos.Maestro;
import com.frba.abclandia.dtos.Palabra;
import com.frba.abclandia.webserver.ABCLandiaRestServer;


public class MaestroListActivity extends ListActivity {

	private DataBaseHelper myDbHelper;
	private ABCLandiaRestServer server;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_maestro_list);
		//ListView listView = (ListView) findViewById(R.id.list_maestro);
		
		// Iniciamos la BD
		iniciarDB();
		
		// Iniciamos la conexion con el server
		server =  new ABCLandiaRestServer(getApplicationContext());
		// Buscamos todas las palabras de los Alumnos y las descargamos para popular la base.
//		List<Alumno> alumnos = myDbHelper.getAllAlumnos();
//		for (int i = 0; i< alumnos.size(); i++){
//			try {
//				server.syncCategoriasAndPalabrasFromAlumno(alumnos.get(i).getLegajo());
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		List<Palabra> palabras = myDbHelper.getAllPalabras();
//		for (int i = 0; i< palabras.size(); i++){
//			server.syncImagenFromServer(palabras.get(i).getImagen());
//			Log.d("Buscando Imagen", palabras.get(i).getImagen());
//			server.syncSonidoFromServer(palabras.get(i).getSonido());
//			Log.d("Buscando Sonido", palabras.get(i).getSonido());
//		}
		
		//TODO: Buscar maestros en la DB		
		setListAdapter(new MaestroListAdapter(this));
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
	
	private List<Maestro> getMaestrosData() {
		
		List<Maestro> maestros = myDbHelper.getAllMaestros();
		return maestros;
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
		
		// Chequeamos en el server si hay un nuevo alumno
		try {
			server.syncAlumnosDBForMaestro(maestro.getLegajo());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
