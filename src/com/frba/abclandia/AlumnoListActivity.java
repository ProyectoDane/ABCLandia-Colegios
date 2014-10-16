package com.frba.abclandia;

import java.io.IOException;
import java.util.ArrayList;
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
import com.frba.abclandia.webserver.ABCLandiaRestClientUsage;

public class AlumnoListActivity extends ListActivity {
	
	private DataBaseHelper myDbHelper;
	private Integer unMaestro = 0;
	private ABCLandiaRestClientUsage server = new ABCLandiaRestClientUsage();
	
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
		
		setListAdapter(new AlumnoListAdapter(this));
		
		try {
			server.getAlumnosFromMaestro(this.unMaestro);
		} catch (JSONException e){
			e.printStackTrace();
		}
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
			return alumno.getLegajo();
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
